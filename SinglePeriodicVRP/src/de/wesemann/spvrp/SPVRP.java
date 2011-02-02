package de.wesemann.spvrp;

import java.awt.Point;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import de.wesemann.spvrp.calculation.Algorithm;
import de.wesemann.spvrp.calculation.Population;
import de.wesemann.spvrp.data.City;
import de.wesemann.spvrp.data.Period;

public class SPVRP {

	/**
	 * @param args
	 */
	public static void main(String[] args) {

		long start = System.currentTimeMillis();

		SPVRP spvrp = new SPVRP();
		try {
			URL url = new URL("http://neumann.hec.ca/chairedistributique/data/vrp/old/" + spvrp.dataName);
			spvrp.loadProblem(url);
		} catch (MalformedURLException e) {

			e.printStackTrace();
		}
		
		
		// Algorithm alg = new Algorithm();
		Algorithm alg = new Algorithm(spvrp.cities, spvrp.period, 60, 50000);
		alg.setPop(new Population());
		alg.setStorePath("scalingFitness");
		alg.setGeladeneDatei(spvrp.dataName);
		alg.setPx(0.6); // setzen der Rekombwkt
		alg.setMutWkt(0.1); // Mutationswkt
		alg.steadyStateGA();

		System.out.println("GesDauer: " + (System.currentTimeMillis() - start) + "ms");
	}

	private List<City>	cities					= new ArrayList<City>();
	private Period		period;
	private double		totalServiceDuration[]	= null;					// Wie lang sich die autos insg. in den Städten aufhalten

	private double		maxDemand				= 0;						// max. Demand of all Costumers

	private String		dataName				= "p14";

	
	public void addNeighbours() {
		for (City c : cities) {
			for (City cn : cities) {
				if (cn.getCityNumber() != c.getCityNumber()) {
					c.addDistanceToNeighbour(c.getCoords().distance(cn.getCoords()), cn);
				}

			}
		}
	}

	/**
	 * holt ein bestimmtes Problem aus dem www
	 * Momentan noch Single Period
	 * 
	 * für Single Period siehe: http://neumann.hec.ca/chairedistributique/data/vrp/old/p14
	 * 
	 * @param problem
	 *            das Problem im Form eines Strings
	 * 
	 */
	@SuppressWarnings("unused")
	private void loadProblem(String problem) {

		// String url = System.getProperty("user.dir") + "src/de/wesemann/vehicleroutinig/specialproblems/pr01.txt";
		try {
			URL url = new URL("http://neumann.hec.ca/chairedistributique/data/pvrp/new/" + problem);
			InputStreamReader isR = new InputStreamReader(url.openConnection().getInputStream());
			parseProblem(isR);
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// File file = new File(url);
		// System.out.println("Problem " + file.exists());

	}

	/**
	 * läd ein bestimmtes Problem
	 * 
	 * @param url
	 *            Der Pfad zum Problem
	 */
	private void loadProblem(URL url) {
		// String url = System.getProperty("user.dir") + "src/de/wesemann/vehicleroutinig/specialproblems/pr01.txt";
		try {
			InputStreamReader isR = new InputStreamReader(url.openConnection().getInputStream());
			parseProblem(isR);

		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * der Parser der das Problem richtig interpretiert und einordnet
	 * 
	 * @param isr
	 *            Der Stream mit dem Problem
	 * @throws IOException
	 */
	private void parseProblem(InputStreamReader isr) throws IOException {
		/*
		 * TODO - Die STadtparameter erweitern wenn es z.b. mehrere Perioden gibt oder Zeitfenster hinzukommen
		 */
		BufferedReader br = new BufferedReader(isr);
		String line;
		List<String> linesplit = new ArrayList<String>();

		int countCars = 0;
		int i = 0;
		int periods = 0;
		int[] maxDuration = null;
		int[] maxLoad = null;
		int linesplitsize = 0;

		while ((line = br.readLine()) != null) {

			linesplit.addAll(Arrays.asList(line.split(" ")));
			linesplitsize = linesplit.size();
			// System.out.println(linesplitsize);
			if (linesplitsize <= 1) {
				break;
			}
			// alle leeren Strings aus der Split Liste entfernen
			List<String> noSign = new ArrayList<String>();
			noSign.add("");
			linesplit.removeAll(noSign);

			// Der header
			if (i == 0) {
				// je nachdem um welches Problem es sich handelt, dementsprechend müssen die Stadt Parameter gesetzt werden
				System.out.print("Problem: ");
				switch (Integer.valueOf(linesplit.get(0))) {
					case 0:
						System.out.println("vrp");
						break;
					case 1:
						System.out.println("pvrp");
						break;
					case 2:
						System.out.println("mdvrp");
						break;
					case 3:
						System.out.println("sdvrp");
						break;
					case 4:
						System.out.println("vrptw");
						break;
					case 5:
						System.out.println("pvrptw");
						break;
					case 6:
						System.out.println("mdvrptw");
						break;
					case 7:
						System.out.println("sdvrptw");
						break;
					default:
						break;
				}
				System.out.println("Autos " + linesplit.get(1));
				countCars = Integer.valueOf(linesplit.get(1)); // Anzahl der Autos wird gespeichert

				System.out.println("Kunden " + linesplit.get(2));
				System.out.println("Perioden " + linesplit.get(3));
				periods = Integer.valueOf(linesplit.get(3));
				period = new Period(periods);
				period.setCountCars(countCars);
				maxDuration = new int[periods];
				maxLoad = new int[periods];
				totalServiceDuration = new double[periods];
			}
			// Die Perioden (maxduration und vehicleload)
			else if (i <= periods) {
				maxDuration[i - 1] = Integer.valueOf(linesplit.get(0));
				maxLoad[i - 1] = Integer.valueOf(linesplit.get(1));
				period.addMaxDurationForTheDay(i - 1, maxDuration[i - 1]);
				period.addMaxLoadPerDayPerCar(i - 1, maxLoad[i - 1]);
				totalServiceDuration[i - 1] = 0;
			}

			/*
			 * Die Kunden
			 * linesplit[0] = Customer Number
			 * linesplit[1] = x coord
			 * linesplit[2] = y coord
			 * linesplit[3] = Service Duration
			 * linesplit[4] = demand
			 * linesplit[5] = frequency of visit
			 * linesplit[6] = number of possible visit combinations
			 * linesplit[7] bis linesplit[7 + linesplit[6]] = = Listen der möglichen Visit Combinations (Dezimal aber Binär lese!!!)
			 * linesplit[8] = beginning of time window (earliest time for start of service), if any
			 * linesplit[9] = end of time window (latest time for start of service), if any
			 */
			else {

				City city = new City();
				cities.add(city);
				city.setCityNumber(Integer.valueOf(linesplit.get(0))); // Nummer
				city.setCoords(new Point(Integer.valueOf(linesplit.get(1)), Integer.valueOf(linesplit.get(2)))); // Coords
				city.setServiceDuration(Integer.valueOf(linesplit.get(3))); // Service Dauer

				city.setDemand(Integer.valueOf(linesplit.get(4))); // Der Bedarf (wird vom maxBedarf des Autos abgezogen oder raufgerechnet (weiss ich
																	// noch nciht so genau))
				maxDemand = maxDemand + Integer.valueOf(linesplit.get(4));
				city.setFrequenzyOfVisit(Integer.valueOf(linesplit.get(5))); // wie oft muss die Stadt angefahren werden
				for (int j = 0; j < Integer.valueOf(linesplit.get(6)); j++) {
					city.addVisitCombination(Integer.valueOf(linesplit.get(7 + j)));
					totalServiceDuration[j] = totalServiceDuration[j] + Integer.valueOf(linesplit.get(3));
				}

			}
			linesplit.clear();
			i++;
		}
		period.setMaxServiceDurationPerDay(totalServiceDuration);
		period.setTotalDemand(maxDemand);
		System.out.println("GesLast der Kunden: " + maxDemand);
		addNeighbours();
	}
}
