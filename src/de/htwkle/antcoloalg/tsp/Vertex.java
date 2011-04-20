package de.htwkle.antcoloalg.tsp;

import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Vertex {

	public final String name;
	public List<Edge> adjacency = new ArrayList<Edge>();
	public Point2D coords = new Point2D.Double();

	public Vertex(String argName) {
		name = argName;
	}

	public int getAdjanciesSize() {
		return adjacency.size();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder("Vertex [name=" + name
				+ ", adjacencies=");
		for (int i = 0; i < adjacency.size(); i++) {
			sb.append("Target: " + adjacency.get(i).getTarget().name
					+ " dist: "
					+ String.format("%.2f", adjacency.get(i).getDistance())
					+ ", ");
		}

		sb.append(", coords=" + coords + "]");

		return sb.toString();
	}

	public double getDistanceByTarget(Vertex x) {
		return getEdgeByTarget(x).getDistance();
	}

	public void setPheromoneByTarget(Vertex x, double pheromone) {
		getEdgeByTarget(x).addPheromone(pheromone);
	}

	public double getPheromoneByTarget(Vertex x) {
		return getEdgeByTarget(x).getPheromone();
	}

	/**
	 * Fill the selection probability for each! edge from this Vertex to any!
	 * another Vertex which depends on this
	 * 
	 * @param toTarget
	 * @param distanceWeight
	 */
	public void setSelectionProbability(Vertex toTarget, double distanceWeight) {
		Edge edge = getEdgeByTarget(toTarget);

		double sumOther = 0;
		for (Edge e : this.adjacency) {
			sumOther += (e.getPheromone()<=0.0001)?(0.0):(e.getPheromone()
					* (Math.pow((1 / e.getDistance()), distanceWeight)));
		}
		edge.setSelectionProbability((edge.getPheromone() * (Math.pow(
				(1 / edge.getDistance()), distanceWeight)))
				/ (sumOther));

	}

	public double getSelectionProbability(Vertex toTarget) {
		return getEdgeByTarget(toTarget).getSelectionProbability();
	}

	public Edge getEdgeByTarget(Vertex target) {
		Iterator<Edge> edgeIt = this.adjacency.iterator();
		Edge e;
		while (edgeIt.hasNext()) {
			e = edgeIt.next();
			if (e.getTarget().equals(target)) {

				return e;
			}
		}
		return null;
	}

}
