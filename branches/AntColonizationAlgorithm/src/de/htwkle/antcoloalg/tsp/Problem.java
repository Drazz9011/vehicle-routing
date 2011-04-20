package de.htwkle.antcoloalg.tsp;

public class Problem {

	public String url;
	public int bestTourLength;
	public int numberOfCities;

	public Problem(String url, int bestTourLength, int numberOfCities) {
		this.bestTourLength = bestTourLength;
		this.url = url;
		this.numberOfCities = numberOfCities;
	}
}
