package de.htwkle.antcoloalg.tsp;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Vertex {

	protected int id;
	/**
	 * X coordinate of the city.
	 */
	protected double x;

	/**
	 * Y coordinate of the city.
	 */
	protected double y;

	private List<Edge> adjacencies = new ArrayList<Edge>();

	public Vertex(int id) {
		this.id = id;
	}

	public double getDistance(Vertex to) {
		double xd = this.x - to.x;
		double yd = this.y - to.y;

		return Math.sqrt(((xd * xd) + (yd * yd)));
	}

	public void addAdjancy(Vertex to) {
		if (null == this.getEdgeByTarget(to)) {
			this.adjacencies.add(new Edge(to, getDistance(to)));
		}else{
			System.out.println("allready exists");
		}
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
		for (Edge e : this.adjacencies) {
			sumOther += (e.getPheromone() <= 0.0001) ? (0.0) : (e
					.getPheromone() * (Math.pow((1 / e.getDistance()),
					distanceWeight)));
		}
		edge.setSelectionProbability((edge.getPheromone() * (Math.pow(
				(1 / edge.getDistance()), distanceWeight)))
				/ (sumOther));

	}

	public double getSelectionProbability(Vertex toTarget) {
		return getEdgeByTarget(toTarget).getSelectionProbability();
	}

	public void setPheromoneByTarget(Vertex x, double pheromone) {
		getEdgeByTarget(x).addPheromone(pheromone);
	}

	public double getPheromoneByTarget(Vertex x) {
		return getEdgeByTarget(x).getPheromone();
	}

	public Edge getEdgeByTarget(Vertex target) {
		Iterator<Edge> edgeIt = this.adjacencies.iterator();
		Edge e;
		while (edgeIt.hasNext()) {
			e = edgeIt.next();
			if (e.getTarget().equals(target)) {

				return e;
			}
		}
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return id + " (" + x + " " + y + ")";
	}

	/**
	 * @return the id
	 */
	public int getId() {
		return id;
	}

	/**
	 * @param id
	 *            the id to set
	 */
	public void setId(int id) {
		this.id = id;
	}

	/**
	 * @return the x
	 */
	public double getX() {
		return x;
	}

	/**
	 * @param x
	 *            the x to set
	 */
	public void setX(double x) {
		this.x = x;
	}

	/**
	 * @return the y
	 */
	public double getY() {
		return y;
	}

	/**
	 * @param y
	 *            the y to set
	 */
	public void setY(double y) {
		this.y = y;
	}

	public void setLocation(Double x, Double y) {
		this.x = x;
		this.y = y;

	}

}
