package de.htwkle.antcoloalg.algorithm;

import java.util.ArrayList;

import de.htwkle.antcoloalg.tsp.Vertex;

public class Ant extends ArrayList<Vertex> implements Comparable<Ant> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5861091474529978268L;

	private Vertex current;

	private double distance;

	public double getDistance() {
		return distance;
	}

	public void setDistance(double distance) {
		this.distance = distance;
	}

	public void addDistance(double distance) {
		this.distance += distance;
	}

	public Vertex getCurrent() {

		return current;
	}

	public void setCurrent(Vertex current) {
		this.current = current;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("# of cities: " + this.size() + " full distance: "
				+ String.format("%.2f", this.getDistance()) + ": ");

		return sb.toString();

	}


	@Override
	public int compareTo(Ant o) {
		if (this.distance < o.distance) {
			return -1;
		}
		if (this.distance > o.distance) {
			return 1;
		}
		return 0;
	}
}
