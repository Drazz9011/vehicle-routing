package de.htwkle.antcoloalg;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;

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
			if (line.contains("EOF")) {
				break;
			}
			if (nodes) {
				split = line.split(" ");
				Vertex vertex = new Vertex(Integer.valueOf(split[0]));
				vertex.setLocation(Double.valueOf(split[1]),
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
		
	}

	public void printVertices() {

		for (Vertex vert : graph) {
			System.out.println(vert.toString());
		}

	}

	public Graph getGraph() {
		return this.graph;
	}
}
