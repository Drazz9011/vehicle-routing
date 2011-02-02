/**
 * 
 */
package de.wesemann.spvrp.calculation;

/**
 * @author spinner0815
 * 
 */
public class Population {
	/*
	 * TODO Alle Individuen hier rein - hinterlegen des besten/schlechtesten Indis - gesamte Fitness
	 * - .....
	 */
	private int		currentRound	= 0;
	private int		maxRounds		= 0;
	private double	averageFitness	= 0;
	private double	bestFitness		= 0;

	/**
	 * @return the bestFitness
	 */
	public double getBestFitness() {
		return bestFitness;
	}

	/**
	 * @param bestFitness
	 *            the bestFitness to set
	 */
	public void setBestFitness(double bestFitness) {
		this.bestFitness = bestFitness;
	}

	public void setAverageFitness(double averageFitness) {
		this.averageFitness = averageFitness;
	}

	public double getAverageFitness() {
		return this.averageFitness;
	}

	/**
	 * @return the currentRound
	 */
	public int getCurrentRound() {
		return currentRound;
	}

	/**
	 * @param currentRound
	 *            the currentRound to set
	 */
	public void setCurrentRound(int currentRound) {
		this.currentRound = currentRound;
	}

	/**
	 * @return the maxRounds
	 */
	public int getMaxRounds() {
		return maxRounds;
	}

	/**
	 * @param maxRounds
	 *            the maxRounds to set
	 */
	public void setMaxRounds(int maxRounds) {
		this.maxRounds = maxRounds;
	}
}
