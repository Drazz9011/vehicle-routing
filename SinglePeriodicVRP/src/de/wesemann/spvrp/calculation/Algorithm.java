package de.wesemann.spvrp.calculation;

import java.util.ArrayList;
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

	private Store				store;												// Ordner in dem die Daten gespeichert werden sollen
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
	private Population			pop;
	/**
	 * Anzahl der Individuen einer Population
	 */
	private int					anzIndividuen;
	private int					anzPopulations;

	private Random				rndm				= new Random();

	private String				geladeneDatei;

	
	
	public Algorithm() {

	}

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

	/**
	 * @return the anzIndividuen
	 */
	public int getAnzIndividuen() {
		return anzIndividuen;
	}

	/**
	 * @return the anzPopulations
	 */
	public int getAnzPopulations() {
		return anzPopulations;
	}

	/**
	 * @return the anzWählendeIndis
	 */
	public int getAnzWählendeIndis() {
		return anzWählendeIndis;
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
	 * @return the geladeneDatei
	 */
	public String getGeladeneDatei() {
		return geladeneDatei;
	}

	/**
	 * @return the mutWkt
	 */
	public double getMutWkt() {
		return mutWkt;
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
	 * Die Rekombinatinoswkt px
	 * 
	 * @return the px
	 */
	public double getPx() {
		return px;
	}

	/**
	 * @param anzIndividuen
	 *            the anzIndividuen to set
	 */
	public void setAnzIndividuen(int anzIndividuen) {
		this.anzIndividuen = anzIndividuen;
	}

	/**
	 * @param anzPopulations
	 *            the anzPopulations to set
	 */
	public void setAnzPopulations(int anzPopulations) {
		this.anzPopulations = anzPopulations;
	}

	/**
	 * @param anzWählendeIndis
	 *            the anzWählendeIndis to set
	 */
	public void setAnzWählendeIndis(int anzWählendeIndis) {
		this.anzWählendeIndis = anzWählendeIndis;
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
	 * @param geladeneDatei
	 *            the geladeneDatei to set
	 */
	public void setGeladeneDatei(String geladeneDatei) {
		this.geladeneDatei = geladeneDatei;
	}

	/**
	 * @param mutWkt
	 *            the mutWkt to set
	 */
	public void setMutWkt(double mutWkt) {
		this.mutWkt = mutWkt;
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
	 * @param px
	 *            Die höhe Rekombinationswahrscheinlichkeit
	 */
	public void setPx(double px) {
		this.px = px;
	}

	/**
	 * the path where the data should be stored
	 * 
	 * @param path
	 *            the path
	 */
	public void setStorePath(String path) {
		store = new Store(path);
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

		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < anzIndividuen; i++) {
			indis.add(new Individual(i, cities, period));
			indis.get(i).randomCityList();
			indis.get(i).setPop(getPop());
			sb.append(indis.get(i).getIndiNumber() + "	" + indis.get(i).getDefaultFitness() + "	"
					+ indis.get(i).getGenom().toString() + "\n");
		}
		Individual bestIndi = null;

		for (Individual ind : indis) {
			if (bestIndi == null) {
				bestIndi = ind;
			}
			if (ind.getDefaultFitness() < bestIndi.getDefaultFitness()) {
				bestIndi = ind;
			}
		}
		pop.setBestFitness(bestIndi.getDefaultFitness());
		store.storeData(sb.toString(), "startIndis.csv");
	}

	/**
	 * Steady State GA da ich glaube das eine Überlappende Pop
	 * am besten für das VRP geeignet ist
	 */
	public void steadyStateGA() {
		pop.setMaxRounds(anzPopulations);
		startRandom(); // erste population wird zufällig generiert

		Individual indC = new Individual(); // das Kind indi

		StringBuffer sbAverage = new StringBuffer();
		StringBuffer sbBest = new StringBuffer();
		StringBuffer sbRecomb = new StringBuffer("IndA;IndB;Child\n");

		double u;// wkt für die Rekomb
		Individual indi1; // erste indi für die rekomb
		Individual indi2; // zweite indi für die rekomb
		Individual bestIndi = new Individual();
		Individual worstIndi = new Individual();

		// terminierungsbedingung ist (erstmal) die anzahl der Populationen
		for (int i = 0; i < anzPopulations; i++) {
			pop.setCurrentRound(i);
			// average Individual
			// with the Default Fitness value
			double averageFitness = 0;
			for (Individual ind : indis) {
				averageFitness = averageFitness + ind.getFitness();
			}
			averageFitness = averageFitness / indis.size();
			pop.setAverageFitness(averageFitness);
			// pop.setBestFitness(bestIndi.getFitness());

			indi1 = null;
			indi2 = null;
			// System.out.println("--------------Runde " + i + "-----------------------");

			// ----------------------------- Liste der Selektierten Individuen -----------------------------------
			indi1 = Selections.qFacheTurnierSelection(indis, 2);
			indi2 = Selections.qFacheTurnierSelection(indis, 2);
			// ---------------------------------- Rekombination ------------------------------------------------
			u = rndm.nextDouble(); // wkt für die Rekomb
			if (u < px) {
				// System.out.println("Rekomb");
				indC = Recombinations.ordnungsRekombination(indi1, indi2);

			} else {
				// System.out.println("keine rekomb");
				indC = new Individual();
				indC.setPop(indi1.getPop());

				indC.setGenom(indi1.getGenom());
				indC.setCars(indi1.getCars());
				indC.setTotalDemand(indi1.getTotalDemand());
				indC.setTotalDuration(indi1.getTotalDuration());
				indC.setPeriod(indi1.getPeriod());
				indC.setCities(indi1.getCities());
				indC.createCityList();

			}
			// ------------------------------ Mutation ---------------------------------
			u = rndm.nextDouble(); // wkt für die Rekomb
			if (u < mutWkt) {
				// System.out.println("Mutat");
				Mutations.verschiebeMutation(indC);
			}

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
			pop.setBestFitness(bestIndi.getFitness());
			sbAverage.append(averageFitness + "\n");
			sbBest.append(bestIndi.getFitness() + "\n");
			sbRecomb.append(indi1.getFitness() + ";" + indi2.getFitness() + ";" + indC.getFitness() + "\n");

			// *********************Umweltselektion************************
			// Individual worst = Selections.qStufigeTurnierSelection(indis, 2, 1).get(0);
			// indC.setIndiNumber(worst.getIndiNumber());
			// indis.remove(worst);
			// indis.add(worst.getIndiNumber(), indC);

			// schlechteste aus der aktuellen Pop mit neuem Indi ersetzen
			indC.setIndiNumber(worstIndi.getIndiNumber());
			indis.remove(worstIndi);// vielleciht zu hart
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
		System.out.println("****************best*******************");
		bestIndi.printCars();

		store.storeData(bestIndi.indiToString(), "bestIndiWithCars");
		store.storeData(sbAverage.toString(), "average.csv");
		store.storeData(sbBest.toString(), "best.csv");
		store.storeData(sbRecomb.toString(), "recomb.csv");
		store.closeWriter();
	}

	/**
	 * @return the pop
	 */
	public Population getPop() {
		return pop;
	}

	/**
	 * @param pop
	 *            the pop to set
	 */
	public void setPop(Population pop) {
		this.pop = pop;
	}

}
