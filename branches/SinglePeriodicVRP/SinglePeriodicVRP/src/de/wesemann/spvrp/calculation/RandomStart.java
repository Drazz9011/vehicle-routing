package de.wesemann.spvrp.calculation;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import de.wesemann.spvrp.data.Car;
import de.wesemann.spvrp.data.City;
import de.wesemann.spvrp.data.Period;

/**
 * Klasser zum zufälligen verteilen der Städte auf die Autos
 * Hauptsache die Ladung wird nicht überschritten, wa!
 * 
 * @author spinner0815
 * 
 */
public class RandomStart {

	private List<City>	cities			= new ArrayList<City>();
	private List<City>	drivenCities	= new ArrayList<City>();
	private List<Car>	cars			= new ArrayList<Car>();
	private Period		period;

	public RandomStart() {

	}

	/**
	 * zufällige Aufteilung der STädte auf die Autos damit diese die Befahren können<br>
	 * Einzige einschränkung die hier gemacht wird ist das die Maximal Last nicht überschritten werden darf
	 * 
	 * @param cities
	 *            die StädteListe
	 * @param cars
	 *            Die AutoListe
	 * @param period
	 *            Die Periode (Dauer & Co)
	 */
	public RandomStart(List<City> cities, Period period) {
		super();
		this.cities = cities;
		this.period = period;

	}

	/**
	 * Die neue Autoliste fährt nohc tnich zufällig alle Staädte an sondern so wie die reinkommen
	 * 
	 * @return Die Autoliste mit den Städten die sie befahren
	 */
	public List<Car> start() {

		City oldCity = new City();
		City ci;
		Random rndmCity = new Random();
		for (int i = 0; i < period.getCountCars(); i++) {
			Car c = new Car(i);
			c.setMaxDemand(period.getMaxLoadPerDayPerCar(0)); // Die Maximale Last die ein Auto verträgt

			c.addCityToDrive(cities.get(0));
			oldCity = cities.get(0);
			/*
			 * solange noch nicht alle Städte in der "schon befahrenen" liste drin sind geht einfach nicht weiter!!
			 */
			while (true) {
				ci = cities.get(rndmCity.nextInt(cities.size()));

				/*
				 * prüft ob die Stadt schon in den schon befahrenen städten aufgelistet ist.
				 */
				if ((!(drivenCities.size() == cities.size())) && (!drivenCities.contains(ci))
						&& oldCity.getCityNumber() != ci.getCityNumber()) {
					// System.out.println(ci.getCityNumber());

					c.addToUsedDuration(ci.getDistanceToNeighbour(oldCity)); // Die Distanz zw. alter und neuer Stadt zur Totalen Dauer
																				// hinzufügen
					c.addToUsedDuration(ci.getServiceDuration()); // Die Service Dauer zur Totalen Dauer hinzufügen
					c.addCityToDrive(ci); // Die Stadt diem Auto hinzufügen (damit es weiss wo es noch hinfahren muss)
					c.addToUsedDemand(ci.getDemand()); // Der Bedarf der Stadt wird zum Verbrauchten bedarf des Autos raugerechnet

					drivenCities.add(ci); // Die Stadt die dem Auto hinzugefügt wurde auch der "schonbefahrenden" Liste hinzufügen
					oldCity = ci;
					if (ci.getCityNumber() == 0) {
						System.out.println("Auto Fertig");
						break;
					}
				} else if (drivenCities.size() == cities.size()) {
					break;
				}
			}
			// System.out.println(c.getCarNumber() + " letzte Stadt: " + oldCity.getCityNumber() + " drivenList size: "
			// + drivenCities.size());
			if (oldCity.getCityNumber() != cities.get(0).getCityNumber()) {
				c.addCityToDrive(cities.get(0));
				c.addToUsedDuration(oldCity.getDistanceToNeighbour(cities.get(0)));
			}
			cars.add(c);
		}

		return cars;
	}

	/**
	 * Zufällige verteilen der Städte mit Seperator (-1)
	 * damit erkannt wird wann das nächste auto an der reihe ist
	 * Dabei werden nie mehr Seperatoren als Autos eingesetzt.
	 * Die Seps werden zufällig gesetzt. Sollten alls Seps gesetzt
	 * sein so bekommt das letzte auto die Restlichen Städte.
	 * Des Weiteren wird auch nciht auf die Max Ladung der Autos geachtet. <br>
	 * Die Basis steht nicht mehr in der Indi List drin. Wird aber bei
	 * der Berechnung mit einbezogen. -1 Steht also für neues Auto Startet
	 * von 0 (basis) bzw. endet bei Basis und anderes Auto fährt von Basis los
	 * 
	 * @return Die Liste mit den Städte nr. und Seperatoren
	 */
	public List<Integer> startRndmCityList() {
		List<Integer> genom = new ArrayList<Integer>();
		int countCars = period.getCountCars();
		int countCities = this.cities.size();
		// System.out.println(countCities);
		Random rndm = new Random();
		int currentCity;
		genom.add(-1);
		countCars--;
		double changeCar;
		for (int i = 0; i < countCities - 1; i++) {
			currentCity = rndm.nextInt((countCities - 1) + 1);
			// currentCity = (int)((Math.random()) * (countCities-1) + 1);
			changeCar = rndm.nextDouble();
			// System.out.println("runde: " + i + " zufallstadt " + currentCity + " wkt autowechsel " + changeCar);
			// Marke für das erste Auto hinzufügen:

			if (!genom.contains(currentCity) && currentCity != 0) {

				// wenn wkt des Autowechsels >=0.8 und anz autos noch >0 dann autowechsel
				if (changeCar >= 0.9 && countCars > 0) {
					// System.out.println("Autowechsel");
					genom.add(-1);
					countCars--;
					i--;
				} // füge die Stadt zur städteliste hinzu
				else {
					genom.add(currentCity);

				}
			} else {
				i--;
			}
		}
		if (countCars > 0) {
			// System.out.println("Fehlen noch " + countCars + " Auto(s)");
			for (int i = 0; i < countCars; i++) {
				genom.add(-1);
			}
		}
		// letzen seperator hinzufügen damit das letzte auto auch der autoliste hinzugeüfgt werden kann
		genom.add(-1);
		// List<Integer> sortedCities = new ArrayList<Integer>();
		// sortedCities.addAll(cities);
		// Collections.sort(sortedCities);
		// System.out.println(sortedCities);
		return genom;
	}
}
