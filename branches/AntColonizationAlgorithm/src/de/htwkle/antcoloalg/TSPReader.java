package de.htwkle.antcoloalg;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;

import de.htwkle.antcoloalg.tsp.Edge;
import de.htwkle.antcoloalg.tsp.Graph;
import de.htwkle.antcoloalg.tsp.Vertex;

public class TSPReader {

	private boolean nodes = false;
	private Graph graph = new Graph();

	public void readProblem(URL url) throws IOException {

		InputStreamReader isR = new InputStreamReader(url.openConnection()
				.getInputStream());

		BufferedReader br = new BufferedReader(isR);
		String line;
		String[] split = new String[3];
		while ((line = br.readLine()) != null) {
			// System.out.println(line);
			if (line.contains("EOF")) {
				break;
			}
			if (nodes) {
				split = line.split(" ");
				Vertex vertex = new Vertex(split[0]);
				vertex.coords.setLocation(Double.valueOf(split[1]),
						Double.valueOf(split[2]));
				graph.add(vertex);
			}

			if (line.contains("EDGE_WEIGHT_TYPE")) {

				graph.setEgdeWeightType(line.split(" ")[line.split(" ").length - 1]);

			} else if (line.contains("DIMENSION")) {

				graph.setVerticesSize(Integer.valueOf(line.split(" ")[line
						.split(" ").length - 1]));

			} else if (line.contains("NAME")) {
				graph.setName(line.split(" ")[1]);

			} else if (line.contains("NODE_COORD_SECTION")) {
				nodes = true;
			}

		}
		setAdjacencies();

	}

	private void setAdjacencies() {

		for (Vertex vertOuter : graph) {

			for (Vertex vertInner : graph) {

				if (!vertInner.equals(vertOuter)) {

					double distance = 0;
					if (graph.getEgdeWeightType().equals("EUC_2D")) {
						distance = vertInner.coords.distance(vertOuter.coords);
					} else {
						System.err.println("Calculation not listet: "
								+ graph.getEgdeWeightType());
					}
					vertOuter.adjacency.add(new Edge(vertInner, distance));

				}
			}

		}
	}

	public void printVertices() {

		for (Vertex vert : graph) {
			System.out.println(vert.toString());
		}

	}

	public void getAdjanciesSizePerVertex() {

		for (Vertex vert : graph) {
			System.out.println(vert.name + ": " + vert.adjacency.size());

		}
	}

	public Graph getGraph() {
		return this.graph;
	}
}
