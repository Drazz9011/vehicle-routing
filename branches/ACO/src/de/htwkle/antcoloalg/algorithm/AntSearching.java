package de.htwkle.antcoloalg.algorithm;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import de.htwkle.antcoloalg.tsp.Edge;
import de.htwkle.antcoloalg.tsp.Graph;
import de.htwkle.antcoloalg.tsp.Vertex;

public class AntSearching extends Thread {

	Graph g;
	List<Ant> ants = new ArrayList<Ant>();
	Ant ant;

	Random rndm = new Random();

	public AntSearching(ThreadGroup tGroup, String threadName, Graph g) {
		super(tGroup, threadName);
		this.g = g;
		this.ant = new Ant();
	}

	@Override
	public void run() {
		Graph g = this.g;
		Vertex choose = null;
		// final Ant ant = new Ant(); // creates a new ant

		ant.add(g.get(0)); // adds the first vertex to the way\
		ant.setCurrent(g.get(0)); // sets the current vertex the
									// ant is
									// ants.add(ant); // adds the ant to the
									// other

		// let the ant choose and walk
		for (int j = 0; j < g.size() - 1; j++) {

			// checks the ant was on the vertex

			// if (rndm.nextDouble() < EXPLORATIONS_CONTROLLER)
			// {
			choose = vertexChooser(ant);
			// } else {
			// choose = vertexChooser(ant);
			// }
			ant.add(choose);
			ant.addDistance(ant.getCurrent().getDistanceByTarget(choose));
			ant.setCurrent(choose);

		}
		ant.add(g.get(0));
		ant.addDistance(ant.getCurrent().getDistanceByTarget(g.get(0)));
		// System.out.println(ant.toString());

	}

	/**
	 * choose the vertex with the alg in Evo Alg [Weicker] page 173 formula 4.1
	 * 
	 * @return the choosen vertex
	 */
	private Vertex vertexChooser(Ant ant) {
		double selectionProbability = 0;
		Vertex target = null;
		// System.out.print("current: " + ant.getCurrent().name);
		for (int i = 0; i < ant.getCurrent().getAdjanciesSize(); i++) {
			Edge e = ant.getCurrent().adjacencies.get(rndm.nextInt(ant
					.getCurrent().getAdjanciesSize()));
			if (!ant.contains(e.getTarget())) {
				// System.out.print(" | possible: " + e.getTarget().name);
				if (e.getSelectionProbability() >= selectionProbability) {
					selectionProbability = e.getSelectionProbability();
					target = e.getTarget();
				}

			} else {
				i--;
			}
		}
		// System.out.println(" Choosed: " + target.name );
		return target;
	}

	public Ant getAnt() {
		return ant;
	}
}
