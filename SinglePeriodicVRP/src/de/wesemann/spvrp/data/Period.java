package de.wesemann.spvrp.data;

import java.util.Arrays;

/**
 * Die Periode und die Beschränkungen die während einer Periode gelten
 * 
 * @author spinner0815
 * 
 */
public class Period {

	/**
	 * Die Anzahl der Tage einer Periode
	 */
	private int			days;
	/**
	 * Die Maximal Dauer pro Tag
	 */
	private int[]		maxDurationPerDay;

	private int[]		maxLoadPerDayPerCar;
	/**
	 * The maximum service duration for all costumer and per day
	 */
	private double[]	maxServiceDurationPerDay;

	private int			countCars;
	private double		totalDuration;				// ohne Service Zeit
	private double		totalDemand;

	/**
	 * @return the totalDemand
	 */
	public double getTotalDemand() {
		return totalDemand;
	}

	/**
	 * @param totalDemand the totalDemand to set
	 */
	public void setTotalDemand(double totalDemand) {
		this.totalDemand = totalDemand;
	}

	public Period(int days) {
		maxDurationPerDay = new int[days];
		maxLoadPerDayPerCar = new int[days];
		this.days = days;
	}

	/**
	 * Setzen der Maximalen Dauer (Zeiteinheiten) für einen Tag für alle Autos zusammen
	 * 
	 * @param day
	 *            Der Tag an dem die Dauer gesetzt werden soll
	 * @param maxDuration
	 *            Die dauer
	 */
	public void addMaxDurationForTheDay(int day, int maxDuration) {
		this.maxDurationPerDay[day] = maxDuration;
	}

	/**
	 * Fügt eine MaximalVerfügbarkeit den Tag hinzu (gilt für für alle Autos)
	 * 
	 * @param day
	 *            Den Tag der MaxVerfügbarkeit
	 * @param maxLoad
	 *            Die MaxVerfügbarkeit
	 */
	public void addMaxLoadPerDayPerCar(int day, int maxLoad) {
		this.maxLoadPerDayPerCar[day] = maxLoad;
	}

	/**
	 * @return the countCars
	 */
	public int getCountCars() {
		return countCars;
	}

	/**
	 * @return the days
	 */
	public int getDays() {
		return days;
	}

	/**
	 * Die Maximal Dauer (Zeiteinheiten) an einem Tag für alle Autos zusammen
	 * 
	 * @param day
	 *            der Tag der dauer
	 * @return die Dauer
	 */
	public int getMaxDurationForTheDay(int day) {
		return this.maxDurationPerDay[day];
	}

	/**
	 * @return the maxDurationPerPeriod
	 */
	public int[] getMaxDurationPerDay() {
		return maxDurationPerDay;
	}

	/**
	 * Maximal Tragkraft pro Auto pro Tag (steht so in der Dummy Datei drin)<br>
	 * ist der Eintrag nach der Maximalen Dauer (Zeile 2 - (2+days)
	 * 
	 * @return the maxLoadPerDayPerCar
	 */
	public int[] getMaxLoadPerDayPerCar() {
		return maxLoadPerDayPerCar;
	}

	/**
	 * Die Maximale Verfügbarkeit eines Autos an einem bestimmt Tag <br>
	 * gilt für alle Autos
	 * 
	 * @param day
	 *            der Tag der Verfügbarkeit
	 * @return die Verfügbarkeit (Maximal Bedarf an einem Tag)
	 */
	public int getMaxLoadPerDayPerCar(int day) {
		return this.maxLoadPerDayPerCar[day];
	}

	/**
	 * @return the maxServiceDurationPerDay
	 */
	public double[] getMaxServiceDurationPerDay() {
		return maxServiceDurationPerDay;
	}

	/**
	 * @param countCars
	 *            the countCars to set
	 */
	public void setCountCars(int countCars) {
		this.countCars = countCars;
	}

	/**
	 * @param days
	 *            the days to set
	 */
	public void setDays(int days) {

		this.days = days;
	}

	/**
	 * Die Maximale Dauer für alle Autos pro Periode
	 * 
	 * @param maxDurationPerPeriod
	 *            the maxDurationPerPeriod to set
	 */
	public void setMaxDurationPerDay(int[] maxDurationPerPeriod) {
		this.maxDurationPerDay = maxDurationPerPeriod;
	}

	/**
	 * Maximal Tragkraft pro Auto pro Tag (steht so in der Dummy Datei drin)<br>
	 * ist der Eintrag nach der Maximalen Dauer (Zeile 2 - (2+days)
	 * 
	 * @param maxLoadPerDayPerCar
	 *            the maxLoadPerDayPerCar to set
	 */
	public void setMaxLoadPerDayPerCar(int[] maxLoadPerDayPerCar) {

		this.maxLoadPerDayPerCar = maxLoadPerDayPerCar;
	}

	/**
	 * @param maxServiceDurationPerDay
	 *            the maxServiceDurationPerDay to set
	 */
	public void setMaxServiceDurationPerDay(double[] maxServiceDurationPerDay) {
		this.maxServiceDurationPerDay = maxServiceDurationPerDay;
	}

	/*
	 * (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Period [days=" + days + ", maxDurationPerDay=" + Arrays.toString(maxDurationPerDay)
				+ ", maxLoadPerDayPerCar=" + Arrays.toString(maxLoadPerDayPerCar) + "]";
	}

}
