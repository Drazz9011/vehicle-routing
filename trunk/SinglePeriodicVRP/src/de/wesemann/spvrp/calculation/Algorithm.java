package de.wesemann.spvrp.calculation;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

import de.wesemann.spvrp.data.City;
import de.wesemann.spvrp.data.Period;
import de.wesemann.spvrp.data.Store;

/**
 * Der eigentliche Algorithmuss
 * Hier werden die Indis erstellt und die Population überwacht<br>
 * auch der Evolutionäre Alg sowie die Mutation und Rekombination kommt hier zusammen
 * 
 * @author spinner0815
 * @see Individual
 * 
 */
public class Algorithm {
	/*
	 * -TODO Den Evo Alg noch basteln + mutation + Rekombination (siehe startEvolution)
	 */

	private List<City>			cities				= new ArrayList<City>();
	private Period				period;
	/**
	 * Die Liste der Individuen die zur Population gehören
	 */
	private List<Individual>	indis				= new ArrayList<Individual>();
	private double				px					= 0.6;							// Kombinationswkt
	private int					anzWählendeIndis	= 4;							// Anzahl der Individuen die bei der FitnessSelektion zu wählen
																					// sind
	private double				mutWkt				= 0.1;
	/**
	 * Anzahl der Individuen einer Population
	 */
	private int					anzIndividuen;
	private int					anzPopulations;

	private Random				rndm				= new Random();

	private String				geladeneDatei;

	/**
	 * Der Algorithmuss der die Individuen und Population verwaltet und
	 * den Evo Alg
	 * 
	 * @param cities
	 *            Die StädteListe
	 * @param period
	 *            Die Perioden Klasse
	 * @param anzIndividuen
	 *            Die Anzahl der Individuen einer Population
	 * @param anzPopulations
	 *            Wieviel Populationen gebildet werden sollen bevor der Evo Alg abbricht
	 * @see Period
	 * @see City
	 */
	public Algorithm(List<City> cities, Period period, int anzIndividuen, int anzPopulations) {

		this.cities = cities;
		this.period = period;
		this.anzIndividuen = anzIndividuen;
		this.anzPopulations = anzPopulations;
		// indis = new Individual[this.anzIndividuen];
	}

	public Algorithm() {

	}

	/**
	 * Rückgabe der Städteliste
	 * 
	 * @return the cities Die Städteliste
	 * @see City
	 */
	public List<City> getCities() {
		return cities;
	}

	/**
	 * Setzen der StädteListe
	 * 
	 * @param cities
	 *            the cities to set
	 * @see City
	 */
	public void setCities(List<City> cities) {
		this.cities = cities;
	}

	/**
	 * Holen der Periode
	 * 
	 * @return the period
	 * @see Period
	 */
	public Period getPeriod() {
		return period;
	}

	/**
	 * Setzen der Periode
	 * 
	 * @param period
	 *            the period to set
	 * @see Period
	 */
	public void setPeriod(Period period) {
		this.period = period;
	}

	/**
	 * Die Rekombinatinoswkt px
	 * 
	 * @return the px
	 */
	public double getPx() {
		return px;
	}

	/**
	 * Die Rekombinatinoswkt px
	 * 
	 * @param px
	 *            Die höhe Rekombinationswahrscheinlichkeit
	 */
	public void setPx(double px) {
		this.px = px;
	}

	/**
	 * @return the anzWählendeIndis
	 */
	public int getAnzWählendeIndis() {
		return anzWählendeIndis;
	}

	/**
	 * @param anzWählendeIndis
	 *            the anzWählendeIndis to set
	 */
	public void setAnzWählendeIndis(int anzWählendeIndis) {
		this.anzWählendeIndis = anzWählendeIndis;
	}

	/**
	 * @return the anzIndividuen
	 */
	public int getAnzIndividuen() {
		return anzIndividuen;
	}

	/**
	 * @param anzIndividuen
	 *            the anzIndividuen to set
	 */
	public void setAnzIndividuen(int anzIndividuen) {
		this.anzIndividuen = anzIndividuen;
	}

	/**
	 * @return the anzPopulations
	 */
	public int getAnzPopulations() {
		return anzPopulations;
	}

	/**
	 * @param anzPopulations
	 *            the anzPopulations to set
	 */
	public void setAnzPopulations(int anzPopulations) {
		this.anzPopulations = anzPopulations;
	}

	/**
	 * @return the mutWkt
	 */
	public double getMutWkt() {
		return mutWkt;
	}

	/**
	 * @param mutWkt
	 *            the mutWkt to set
	 */
	public void setMutWkt(double mutWkt) {
		this.mutWkt = mutWkt;
	}

	/**
	 * @return the geladeneDatei
	 */
	public String getGeladeneDatei() {
		return geladeneDatei;
	}

	/**
	 * @param geladeneDatei
	 *            the geladeneDatei to set
	 */
	public void setGeladeneDatei(String geladeneDatei) {
		this.geladeneDatei = geladeneDatei;
	}

	/**
	 * erste Runde
	 * Erzeugen aller Individuen
	 * 
	 * @see Algorithm#anzIndividuen
	 * @see Individual
	 * 
	 */
	private void startRandom() {
		// this.anzIndividuen = popGröße;
		// long start = System.currentTimeMillis();

		for (int i = 0; i < anzIndividuen; i++) {
			indis.add(new Individual(i, cities, period));
			indis.get(i).randomCityList();
			// indis[i].printCars();
			System.out.println(indis.get(i).getIndiNumber() + "	" + indis.get(i).getFitness() + "	"
					+ indis.get(i).getGenom().toString());
		}

		// System.out.println("Dauer: " + (System.currentTimeMillis() - start) + "ms");
	}

	/**
	 * Der Standard genetische Algorithmus
	 */
	public void standardGA() {
		startRandom();
		/*
		 * TODO qstufige Selektion nehmen
		 */

		// terminierungsbedingung ist (erstmal) die anzahl an populationen
		for (int i = 0; i < anzPopulations; i++) {

		}

	}

	/**
	 * Steady State GA da ich glaube das eine Überlappende Pop
	 * am besten für das VRP geeignet ist
	 */
	public void steadyStateGA() {
		startRandom(); // erste population wird zufällig generiert

		Individual indC = new Individual(); // das Kind indi
		Selections sel = new Selections(anzWählendeIndis); // die Selektion
		Recombinations recomb = new Recombinations(); // Rekombination
		Store store = new Store();

		// Zeitstempel_AnzIndi_AnzPops_rekombwkt_mutwkt_Name der geladenen Datei
//		String fileName = new Date(System.currentTimeMillis()) + "_" + anzIndividuen + "_" + anzPopulations + "_"
//				+ getPx() + "_" + mutWkt + "_" + geladeneDatei;
		String fileName = "test";
		store.createFileAndHead(fileName);
		double u;// wkt für die Rekomb
		boolean isRecomb = false;
		boolean isMut = false;
		Individual indi1; // erste indi für die rekomb
		Individual indi2; // zweite indi für die rekomb
		Individual bestIndi = new Individual();
		Individual worstIndi = new Individual();

		// terminierungsbedingung ist (erstmal) die anzahl der Populationen
		for (int i = 0; i < anzPopulations; i++) {

			indi1 = null;
			indi2 = null;
			// System.out.println("--------------Runde " + i + "-----------------------");

			// ----------------------------- Liste der Selektierten Individuen -----------------------------------

			indi1 = sel.qFacheTurnierSelection(indis, 3);
			indi2 = sel.qFacheTurnierSelection(indis, 3);
			// System.out.println(indi1.getIndiNumber() + ": " + indi1.getFitness());
			// System.out.println(indi2.getIndiNumber() + ": " + indi2.getFitness());

			// ---------------------------------- Rekombination ------------------------------------------------
			u = rndm.nextDouble(); // wkt für die Rekomb
			if (u < px) {
				// System.out.println("Rekombination");
				isRecomb = true;
				indC = recomb.ordnungsRekombination(indi1, indi2);
			} else {
				// System.out.println("keine Rekombination");
				isRecomb = false;
				double totalDemand = indi1.getTotalDemand();
				double totalDuration = indi1.getTotalDuration();
				Period period = indi1.getPeriod();
				indC = new Individual();
				indC.setGenom(indi1.getGenom());
				indC.setCars(indi1.getCars());
				indC.setTotalDemand(totalDemand);
				indC.setTotalDuration(totalDuration);
				indC.setPeriod(period);
				indC.setCities(indi1.getCities());
				indC.createCityList();

			}
			// ------------------------------ Mutation ---------------------------------
			u = rndm.nextDouble(); // wkt für die Rekomb
			if (u < mutWkt) {
				// System.out.println("Mutation");
				isMut = true;
				Mutations.verschiebeMutation(indC);
			} else {
				isMut = false;
			}

			// ----------------- Beste und schlechteste Indi raussuchen ------------------------------
			bestIndi = null;
			worstIndi = null;
			for (Individual ind : indis) {
				if (bestIndi == null && worstIndi == null) {
					worstIndi = bestIndi = ind;
				}
				if (ind.getFitness() > worstIndi.getFitness()) {
					worstIndi = ind;
				}
				if (ind.getFitness() < bestIndi.getFitness()) {
					bestIndi = ind;
				}
			}
			// System.out.println("beste Indi: " + bestIndi.getIndiNumber() + " Fitness: " + bestIndi.getFitness());
			// System.out
			// .println("schlechteste Indi: " + worstIndi.getIndiNumber() + " Fitness " + worstIndi.getFitness());

			// Speichern der aktuellen Runde
			// Runde;best indi(fitness); worst indi(fitness); Rekomb(bool); indi1(fitness); indi2(fitness); indiNeu; Mut(bool);
//			store.storeDataPopulation(i + ";" + bestIndi.getFitness() + ";" + worstIndi.getFitness() + ";" + isRecomb
//					+ ";" + indi1.getFitness() + ";" + indi2.getFitness() + ";" + indC.getFitness() + ";" + isMut);
			store.storeDataPopulation(""+bestIndi.getFitness());
			// schlechteste aus der aktuellen Pop mit neuem Indi ersetzen
			indC.setIndiNumber(worstIndi.getIndiNumber());
			// System.out.println("GenomC Nr.:" + indC.getIndiNumber() + " Fitness:" + indC.getFitness());

			// entfernen des schlechtesten und hinzufügen des kindes
			indis.remove(worstIndi);
			indis.add(worstIndi.getIndiNumber(), indC);

		}
		System.out.println("###############alle#####################");
		for (Individual ind : indis) {
			System.out.println(ind.getIndiNumber() + ": " + ind.getFitness() + "	" + ind.getGenom().toString());

		}

		bestIndi = null;

		for (Individual ind : indis) {
			if (bestIndi == null || ind.getFitness() < bestIndi.getFitness()) {
				bestIndi = ind;
			}
		}
		System.out.println(indis.size());
		System.out.println("****************best*******************");
		bestIndi.printCars();
		// Speichern des besten Indis
	}
}
