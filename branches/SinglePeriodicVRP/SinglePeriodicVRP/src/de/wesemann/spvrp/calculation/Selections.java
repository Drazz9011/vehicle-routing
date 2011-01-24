/**
 * 
 */
package de.wesemann.spvrp.calculation;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Random;

/**
 * @author spinner0815
 * 
 */
public class Selections {

	/**
	 * TODO - funktioniert noch nciht so ganz.
	 * Fitness Proportionale Eltern Selection
	 * Auswahlwkt aus den Fitnesswerten des Indis. (S. 71 Weicker)
	 * Die Fitness ist lediglich die Auswahlwkt. für ein Individuum
	 * Fitness == Güte
	 */
	public static List<Individual> fitnessProportionalParentSelection(List<Individual> indis, int anzWählendeIndis) {
		Random rndm = new Random();
		double gesFitness = 0;
		double bestFitness = indis.get(0).getFitness();
		double worstFitness = indis.get(0).getFitness();
		List<Individual> selektionsIndis = new ArrayList<Individual>();

		for (Individual ind : indis) {
			gesFitness += ind.getFitness();
			if (bestFitness < ind.getFitness()) {
				bestFitness = ind.getFitness();
			}
			if (worstFitness > ind.getFitness()) {
				worstFitness = ind.getFitness();
			}
		}
		System.out.println("Gesamte Fitness aller Indis: " + gesFitness + " best: " + bestFitness + " worst: "
				+ worstFitness);
		/*
		 * Wählen der Eltern (bis max anzWählendeIndis)
		 */
		for (int i = 0; i < anzWählendeIndis; i++) {
			int j = 0; // weil das da so steht
			double sum = 1 - (indis.get(j).getFitness() - worstFitness) / (bestFitness - worstFitness);
			double u = rndm.nextDouble();
			System.out.println("sum: " + sum + " rndm: " + u);
			while (sum < u) {
				j++;
				sum += 1 - (indis.get(j).getFitness() - worstFitness) / (bestFitness - worstFitness);

			}
			System.out.println("neue Summe: " + sum);
			System.out.println("nr: " + indis.get(j).getIndiNumber() + " Fitness: " + indis.get(j).getFitness());
			selektionsIndis.add(indis.get(j));

		}
		for (Individual in : selektionsIndis) {
			System.out.println("indi nr: " + in.getIndiNumber() + " Fitness: " + in.getFitness());
		}
		return selektionsIndis;
	}

	/**
	 * qFacheTurnierselektion: es werden zufällig 3 ausgewählt und diese
	 * treten gegeneinander an. anschließend werden die beiden besten
	 * übergeben.
	 * Wird bei SSGA verwendet
	 * 
	 * @param q
	 *            Anzahl der Gegner
	 * @return die liste mit den besten indis
	 */
	public static Individual qFacheTurnierSelection(List<Individual> indis, int q) {
		Random rndm = new Random();
		// System.out.println("****selektiert******");
		int uIndex = rndm.nextInt(indis.size()); // zufällige zahl für den index des indis
		for (int j = 0; j < q; j++) {
			int u = rndm.nextInt(indis.size()); // zufällige zahl für den index des indis
			if (indis.get(u).getFitness() < indis.get(uIndex).getFitness()) {
				uIndex = u;
			}
		}
		return indis.get(uIndex);
	}

	/**
	 * Q-stufige Turnierselektion<br>
	 * wahl der Indis die am meisten verloren haben <br>
	 * Die mit der schlechtesten Fitness <br>
	 * Die ist eine Selektion für die Umweltselektion. Es wird
	 * an dieser Stelle das Individuum mit den meisten verlorenen Kämpfen gewählt
	 * damit es später durch das Kind ersetzt werden kann.<br>
	 * Achtung: Nicht bei der Elternselektion verwenden!! hier wird das schlechtest Indi
	 * ausgwewählt!!
	 * 
	 * @param indis
	 *            Die Liste aller Indis
	 * @param anzTurniere
	 *            anzahl der Turniere die jedes Indi durchführen soll
	 * @param anzZuWählendeIndis
	 *            Die Anzahl der Indis die gewählt werden sollen
	 * @return Die Liste der Gewählten Indis
	 */
	public static List<Individual> qStufigeTurnierSelection(List<Individual> indis, int anzTurniere,
			int anzZuWählendeIndis) {
		Random rndm = new Random();
		List<Individual> selectedIndi = new ArrayList<Individual>();
		Map<Individual, Integer> scores = new HashMap<Individual, Integer>();
		for (int i = 0; i < indis.size(); i++) { // alle Indis durchgehen
			int loss = 0;
			for (int j = 0; j < anzTurniere; j++) { // Anzahl der Turniere des gewählten Indis gg zwei beliebige
				int u = rndm.nextInt(indis.size());
				if (indis.get(i).getFitness() > indis.get(u).getFitness()) { // Prüfen ob die Fitness des gewählten besser ist als die des zufälligen
																				// anderen
					loss++;
				}
			}
			scores.put(indis.get(i), loss);
		}
		selectedIndi.clear();
		// sorten der scroes Liste nach den Indis mit den meisten gewinnen
		Map<Individual, Integer> sorted = sortByValue(scores);

		// hinzufügen der besten indis zur Liste der gewählten
		Iterator<Individual> i = sorted.keySet().iterator();
		for (int j = 0; j < anzZuWählendeIndis; j++) {
			selectedIndi.add(i.next());
		}
		return selectedIndi;
	}

	/**
	 * Sortieren einer Map nach die Werten (nur Integer!!)<br> {@link http
	 * ://stackoverflow.com/questions/109383/how-to-sort-a-mapkey-value-on-the-values-in-java}
	 * 
	 * @param map
	 *            Die Map die sortiert werden soll
	 * @return Die sortierte Map
	 */
	@SuppressWarnings("unchecked")
	public static Map<Individual, Integer> sortByValue(Map<Individual, Integer> map) {

		List list = new LinkedList(map.entrySet());
		Collections.sort(list, new Comparator() {
			@Override
			public int compare(Object o1, Object o2) {
				return ((Comparable) ((Map.Entry<Individual, Integer>) (o2)).getValue())
						.compareTo(((Map.Entry<Individual, Integer>) (o1)).getValue());
			}
		});

		Map<Individual, Integer> result = new LinkedHashMap<Individual, Integer>();
		for (Iterator it = list.iterator(); it.hasNext();) {
			Map.Entry<Individual, Integer> entry = (Map.Entry<Individual, Integer>) it.next();
			result.put(entry.getKey(), entry.getValue());
		}
		return result;
	}

	public Selections() {

	}
}
