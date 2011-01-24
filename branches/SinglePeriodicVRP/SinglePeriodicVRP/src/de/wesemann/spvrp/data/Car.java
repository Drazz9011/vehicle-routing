package de.wesemann.spvrp.data;

import java.util.ArrayList;
import java.util.List;

/**
 * Das Auto fährt eine Tour ab indem es die Distanz berechnet
 * 
 * @author spinner0815
 * 
 */
public class Car {

	/**
	 * noch zu befahrende Städte
	 */
	private List<City>	citiesToDrive;
	private int			carNumber;

	private int			maxDemand	= 0;	// max Bedarf (Load) (wenn 0 losfahren wenn 200 zum depot und neu beladen)
	private int			usedDemand	= 0;

	private double		maxDuration;		// Die Maximal Zeit pro Periode
	private double		usedDuration;		// Die verbrauchte Zeit

	public Car() {

	}

	/**
	 * 
	 * @param maxDemand
	 *            Der maximale Service
	 */
	public Car(int carNumber) {
		this.carNumber = carNumber;
	}

	/**
	 * Eine Stadt zur "nochzurbefahren"-Liste hinzufügen
	 * 
	 * @param city
	 */
	public void addCityToDrive(City city) {
		if (citiesToDrive == null) {
			citiesToDrive = new ArrayList<City>();
		}
		this.citiesToDrive.add(city);
	}

	/**
	 * Die Ladung (Bedarf) die die Stadt dem Auto abgeknüpft hat
	 * 
	 * @param demand
	 */
	public void addToUsedDemand(int demand) {
		this.usedDemand = this.usedDemand + demand;
	}

	/**
	 * Die Dauer die noch hinzukommt (Einmal die Fahrtzeit und zum zweiten die Service-Dauer)
	 * 
	 * @param duration
	 */
	public void addToUsedDuration(double duration) {
		this.usedDuration = this.usedDuration + duration;
	}

	/**
	 * @return the carNumber
	 */
	public int getCarNumber() {
		return carNumber;
	}

	/**
	 * @return the citiesToDrive
	 */
	public List<City> getCitiesToDrive() {
		return citiesToDrive;
	}

	/**
	 * @return the maxDemand
	 */
	public int getMaxDemand() {
		return maxDemand;
	}

	/**
	 * @return the maxDuration
	 */
	public double getMaxDuration() {
		return maxDuration;
	}

	/**
	 * Die Ladung (Bedarf) die schon verbraucht wurde
	 * 
	 * @return the usedDemand
	 */
	public int getUsedDemand() {
		return usedDemand;
	}

	/**
	 * Die Dauer d.h. Wie lang das Auto schon unterwegs ist
	 * 
	 * @return the usedDuration
	 */
	public double getUsedDuration() {
		return usedDuration;
	}

	/**
	 * @param carNumber
	 *            the carNumber to set
	 */
	public void setCarNumber(int carNumber) {
		this.carNumber = carNumber;
	}

	/**
	 * @param citiesToDrive
	 *            the citiesToDrive to set
	 */
	public void setCitiesToDrive(List<City> citiesToDrive) {
		if (citiesToDrive == null) {
			citiesToDrive = new ArrayList<City>();
		}
		this.citiesToDrive = citiesToDrive;
	}

	/**
	 * Die Ladung (Bedarf) die das Auto maximal verbrauchen Darf!
	 * 
	 * @param maxDemand
	 *            the maxDemand to set
	 */
	public void setMaxDemand(int maxDemand) {
		this.maxDemand = maxDemand;
	}

	/**
	 * Die Maximal Zeit die ein Auto unterwegs sein darf <br>
	 * Dran denken dass die Zentrale zum schluss auch noch mit reingerechnet werden muss!!
	 * 
	 * @param maxDuration
	 *            the maxDuration to set
	 */
	public void setMaxDuration(double maxDuration) {
		this.maxDuration = maxDuration;
	}

	/**
	 * 
	 * @param usedDemand
	 *            the usedDemand to set
	 */
	public void setUsedDemand(int usedDemand) {
		this.usedDemand = usedDemand;
	}

	/**
	 * @param usedDuration
	 *            the usedDuration to set
	 */
	public void setUsedDuration(double usedDuration) {
		this.usedDuration = usedDuration;
	}

}
