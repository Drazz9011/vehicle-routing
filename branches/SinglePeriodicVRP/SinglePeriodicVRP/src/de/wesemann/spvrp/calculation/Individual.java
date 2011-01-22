package de.wesemann.spvrp.calculation;

import java.util.ArrayList;
import java.util.List;

import de.wesemann.spvrp.data.Car;
import de.wesemann.spvrp.data.City;
import de.wesemann.spvrp.data.Period;

public class Individual implements Cloneable {

	private List<Car>		cars	= new ArrayList<Car>();
	private List<City>		cities	= new ArrayList<City>();
	private Period			period;
	private double			totalDemand;
	private double			totalDuration;
	private int				indiNumber;
	/**
	 * Das genom des Indis (Städte mit -1 für Autowechsel)
	 */
	private List<Integer>	genom	= new ArrayList<Integer>();

	public Individual() {

	}

	/**
	 * @param cities
	 * @param period
	 */
	public Individual(int indiNu, List<City> cities, Period period) {
		super();
		this.indiNumber = indiNu;
		this.cities = cities;
		this.period = period;
	}

	/**
	 * @param e
	 * @return
	 * @see java.util.List#add(java.lang.Object)
	 */
	public boolean add(Car e) {
		return cars.add(e);
	}

	/**
	 * @return the cars
	 */
	public List<Car> getCars() {
		return cars;
	}

	/**
	 * @return the indiNumber
	 */
	public int getIndiNumber() {
		return indiNumber;
	}

	/**
	 * @return the totalDemand
	 */
	public double getTotalDemand() {
		return totalDemand;
	}

	/**
	 * @return the totalDuration
	 */
	public double getTotalDuration() {
		return totalDuration;
	}

	/**
	 * @param cars
	 *            the cars to set
	 */
	public void setCars(List<Car> cars) {
		this.cars = cars;
	}

	/**
	 * @param indiNumber
	 *            the indiNumber to set
	 */
	public void setIndiNumber(int indiNumber) {
		this.indiNumber = indiNumber;
	}

	/**
	 * @param totalDemand
	 *            the totalDemand to set
	 */
	public void setTotalDemand(double totalDemand) {
		this.totalDemand = totalDemand;
	}

	/**
	 * @param totalDuration
	 *            the totalDuration to set
	 */
	public void setTotalDuration(double totalDuration) {
		this.totalDuration = totalDuration;
	}

	/**
	 * Die Liste der Städte mit -1 für Autowechsel
	 */
	public List<Integer> getGenom() {

		return genom;
	}

	public void setGenom(List<Integer> genom) {
		this.genom = genom;
	}

	/**
	 * @return the period
	 */
	public Period getPeriod() {
		return period;
	}

	/**
	 * @param period
	 *            the period to set
	 */
	public void setPeriod(Period period) {
		this.period = period;
	}

	/**
	 * @return the cities
	 */
	public List<City> getCities() {
		return cities;
	}

	/**
	 * @param cities
	 *            the cities to set
	 */
	public void setCities(List<City> cities) {
		this.cities = cities;
	}

	/**
	 * zufälliges erstellen des Genoms (Städte + -1 für Autos)
	 */
	public void randomCityList() {
		RandomStart rStart = new RandomStart(cities, period);

		genom = rStart.startRndmCityList();

		createCityList();
	}

	/**
	 * verteilen der STädte auf die Autos und berechnen der Fitness
	 */
	public void createCityList() {
		// RandomStart rStart = new RandomStart(cities, period);

		List<Integer> rndmCityNumbers = genom;
		// System.out.println(rndmCityNumbers);
		City oldCity = null;
		Car c = null;
		cars.clear();
		int i = 0;
		// System.out.println("größe: " + rndmCityNumbers.size());
		// System.out.println(rndmCityNumbers);
		for (Integer cNumber : rndmCityNumbers) {
			/*
			 * Wenn Stadtnr == -1 und Anz. autos < max Anz. Autos dann neues auto
			 * Neues Auto fährt von Stadt 0 los und endet auch dort
			 */
			if (cNumber == -1) {
				if (c == null) {
					c = createCar(i++ + 1); // Neues Auto erstellen wenn noch keins da
					c.addCityToDrive(cities.get(0)); // Zentrale hinzufügen
					oldCity = cities.get(0);
				} else {

					c.addCityToDrive(cities.get(0));
					if (oldCity != cities.get(0)) {
						c.addToUsedDuration(oldCity.getDistanceToNeighbour(cities.get(0)));// Zeit hinzufügen von alte Stadt bis zur zentrale
					}
					cars.add(c);
					c = null;
					c = createCar(i++ + 1); // Neues Auto erstellen wenn noch keins da
					c.addCityToDrive(cities.get(0)); // Zentrale hinzufügen
					oldCity = cities.get(0);

				}
			} else {
				if (c == null) {
					System.out.println("Auto ist NULL");
				}
				c.addCityToDrive(cities.get(cNumber));
				c.addToUsedDuration(oldCity.getDistanceToNeighbour(cities.get(cNumber)));// Zeit hinzufügen von alte Stadt bis zur zentrale
				c.addToUsedDuration(cities.get(cNumber).getServiceDuration()); // hinzufügen der Service Zeit
				c.addToUsedDemand(cities.get(cNumber).getDemand()); // Die benötigte Kapa hinzufügen
				oldCity = cities.get(cNumber);
			}

		}
		// //Sortiert die Liste (um zu schauen ob alle städte vorhanden sind)
		// Collections.sort(rndmCityNumbers);
		// System.out.println("\nsortierte Liste:"+rndmCityNumbers);
	}

	/**
	 * Neues Auto Basteln und die nötigen werte dem auto hinzufügen
	 * 
	 * @param id
	 * @return
	 */
	private Car createCar(int id) {
		Car car = new Car(id);
		car.setMaxDemand(period.getMaxLoadPerDayPerCar(0));
		return car;
	}

	/**
	 * Berechnet die Fitness des Individuums
	 * für alle Autos:<br>
	 * <code>
	 * fitness = fitness + <br>((c.getUsedDemand() / c.getMaxDemand()) * 1000); <br>
	 * fitness = fitness + c.getUsedDuration();
	 * </code>
	 * 
	 * @return die Fitness des Individuums
	 */
	public double getFitness() {
		double fitness = 0.0;
		int emptyCars = 0;
		int overloadedCars = 0;
		for (Car c : cars) {
			// Wenn die verbrauchte Last über der maximal erlaubten ist dann
			// adde eine wert zur fitness hinzu damit die größer und damit schlechter wird
			// ABER: keinen festen sondern einen Prozentualen Mehrwert je weiter die MaxLast über schritten wurde!!!
			// System.out.println("Verbrauchte Ladung: " + c.getUsedDemand() + " Max. Ladung" + c.getMaxDemand());
			if (c.getUsedDemand() > c.getMaxDemand()) {
				fitness = fitness + (((double) c.getUsedDemand() / c.getMaxDemand()) * 1000);
				overloadedCars++;
			}
			if (c.getUsedDemand() == 0.0) {
				emptyCars++;
			}
			fitness = fitness + c.getUsedDuration();

		}
		if (overloadedCars > 0 || emptyCars > 0)
			fitness = fitness + ((overloadedCars + emptyCars) * 1000);
		return fitness;
	}

	public void printCars() {
		System.out.println("InidNR.: " + this.indiNumber);
		System.out.println("Fitness: " + this.getFitness());
		double totalServiceDuration = 0;
		for (Car c : cars) {
			totalDemand = totalDemand + c.getUsedDemand();
			totalDuration = totalDuration + c.getUsedDuration(); // Wie lang sind alle autow unterwegs

			System.out.print("auto " + c.getCarNumber() + ": ");
			for (City ci : c.getCitiesToDrive()) {
				System.out.print("->" + ci.getCityNumber());
				totalServiceDuration = totalServiceDuration + ci.getServiceDuration();
			}
			System.out.println(" Last " + c.getUsedDemand() + " Dist: " + c.getUsedDuration());
		}
		System.out.println("demand: " + totalDemand + " duration: " + totalDuration + " ohne: "
				+ (totalDuration - totalServiceDuration));
	}
}
