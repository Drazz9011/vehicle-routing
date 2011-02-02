/**
 * 
 */
package de.wesemann.spvrp.calculation;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import de.wesemann.spvrp.data.Period;

/**
 * @author spinner0815
 * 
 */
public class Recombinations {
	// private static Random rndm = new Random();

	/**
	 * Ordnungsrekombination
	 */
	public static Individual ordnungsRekombination(Individual indA, Individual indB) {
		Random rndm = new Random();
		Individual indC = new Individual();
		double totalDemand = indA.getTotalDemand();
		double totalDuration = indA.getTotalDuration();
		Period period = indA.getPeriod();
		List<Integer> genomA = new ArrayList<Integer>();
		List<Integer> genomB = new ArrayList<Integer>();
		indC.setPop(indA.getPop());
		indC.setTotalDemand(totalDemand);
		indC.setTotalDuration(totalDuration);
		indC.setPeriod(period);
		indC.setCities(indA.getCities());
		int maxCars = indA.getCars().size() - 1;

		// Die Genome der Individuen
		List<Integer> genomC = new ArrayList<Integer>();
		genomA.addAll(indA.getGenom());
		genomB.addAll(indB.getGenom());

		// erste Stelle hinzufügen da es immer eine -1 ist. (für das erste Auto)
		genomC.add(-1);

		// bei den ElternIndis die erste Stelle streichen. Da es für das erste Auto steht und das schon übernommen wurde
		genomA.remove(0);
		genomB.remove(0);
		genomA.remove(genomA.size() - 1);
		genomB.remove(genomB.size() - 1);

		// start der Rekombination
		int j = rndm.nextInt(genomA.size() - 1); // Bis zum j. Punkt alle Genome aus Elter A

		// Teil 0 bis j von GenomA wird an GenomC drangehängt
		for (int i = 0; i < j; i++) {
			genomC.add(genomA.get(i));
			if (genomA.get(i) == -1) {
				maxCars--;
			}
		}

		// Restliche Teile von GenomB nehmen und wenn noch nciht in GenomC vorhanden dann dranhängen
		for (int i = 0; i < genomB.size(); i++) {
			// Wenn es sich um eine Stadt handelt
			if (genomB.get(i) != -1) {
				// Prüfen ob die Stadt schon in GenomC vorhanden ist
				if (!genomC.contains(genomB.get(i))) {
					genomC.add(genomB.get(i)); // wenn nicht vorhanden hinzufügen
				}
			}
			// prüfen ob schon alle Autos verbraucht sind
			else {
				if (maxCars > 0) {
					maxCars--;
					genomC.add(-1);
				}
			}
		}
		genomC.add(-1); // das letzte -1 damit das letzte auto weiss das es nach hause fahren muss
		indC.setGenom(genomC);
		indC.createCityList();
		return indC;
	}

	/**
	 * Abbildungrekombination zweier Indis
	 * Funktioniert noch nicht richtig
	 * 
	 * @param indA
	 *            Das erste Indi
	 * @param indB
	 *            Das zweite Indi
	 * @return Das Indi das dann da rauskommt
	 */
	public Individual abbildungsRekombination(Individual indA, Individual indB) {
		Individual indC = new Individual();

		List<Integer> genomA = indA.getGenom(); // Genome des Indis A
		List<Integer> genomB = indB.getGenom(); // Genome des Indis B
		List<Integer> genomC = new ArrayList<Integer>();
		List<Integer> benutzt = new ArrayList<Integer>();
		int genomlength = genomA.size(); // Die länge der Max genome
		int maxCars = indA.getCars().size() + 1;
		// generieren der beiden Zufallszahlen für die Schnittmenge
		int u1 = new Random().nextInt((genomlength - 2) + 2);
		int u2 = new Random().nextInt((genomlength - 2) + 2);
		// Die eigentlich Rekombination beginnt hier

		// wenn u2 kleiner u1 dann tauschen der beiden zahlen
		if (u2 < u1) {
			int temp = u2;
			u2 = u1;
			u1 = temp;
		}
		// System.out.println("Anz Genome: " + genomlength + " u1: " + u1 + " u2: " + u2);

		// Füllen des IndisC mit -2 damit die Länge der normelaen genom länge entspricht
		for (int i = 0; i < genomlength; i++) {
			genomC.add(-2);
		}
		int benutztAutoscnt = 0;

		// die Gene zw. den Schnittpunkten übernehmen
		for (int j = u1; j < u2; j++) {
			genomC.set(j, genomB.get(j)); // hinzufügen der Schnittgenome von B zu C an der richtigen Stelle j
			if (genomB.get(j).equals(-1)) {
				// es handelt sich um einen Autowechsel
				benutztAutoscnt++;
			} else {
				benutzt.add(genomB.get(j)); // das vergebene Genom zu der benutzt liste hinzufügen
			}
		}
		// System.out.println(genomC.toString());
		int currentPos = 0;
		// hinzufügen der restlichen genome zu Indi C
		/*
		 * TODO if (genomC.get(i).equals(-2)) ist unsinn da er ja auch gleich die Chromosomen der Eltern an der Position überspringt. und das will ich
		 * ja nicht unbedingt
		 */
		for (int i = 0; i < genomlength; i++) {
			currentPos = i;
			if (genomC.get(i).equals(-2)) {// wenn an der Position i noch nichts steht (-2 ist nichts)
				int x = genomA.get(i); // Das aktuelle Chromosom
				if (x == -1 && !(benutztAutoscnt == maxCars)) {
					// wenn das genom ein Auto ist muss erst noch geprüft werdenob schon alle autos vergeben sind
					// wenn nicht dann kann es eigentlich mit dazu genommen werden
					benutztAutoscnt++;
					// hinzufügen des genoms
					genomC.set(i, x);

				} else if (x != -1) {

					while (benutzt.contains(x)) {// haut noch nicht so ganz hin.... Das mit der -1..daran muss cih noch arbeiten
					// System.out.println(x + " bei pos " + currentPos);
						currentPos = genomA.indexOf(x);

						while (genomB.get(currentPos).equals(x)) {
							currentPos++;
						}
						x = genomB.get(currentPos); // Die Abbildung vom GenomA zu genomB

						// System.out.println(currentPos);
					}
					// System.out.println("genommen: " + x);
					if (x != -1) { // wenn x kein auto dann kann es mit zu den benutzten hinzugefügt
									// werden
						genomC.set(i, x);

						benutzt.add(x);

					} else if (x == -1 && !(benutztAutoscnt == maxCars)) {
						genomC.set(i, x);
						benutztAutoscnt++;
					}
					// else if (benutztAutoscnt == maxCars) {
					// benutzt.add(-1);
					// }
				} else if (x == -1 && (benutztAutoscnt == maxCars)) {
					// hier rein das er das Chromosom überspringen soll da scon alle autos vorhanden sind.
					// ab da an nur nur die städte

				}
				// else if (benutztAutoscnt == maxCars) {
				// // benutzt.add(-1);
				// }
			}
		}
		// System.out.println(genomC.toString() + " anz: " + genomC.size());
		Collections.sort(genomC);
		// System.out.println(genomC);
		return indC;
	}

	/**
	 * Kantenrekombination
	 */

	/**
	 * Die KantenRekombination von zwei individuen
	 * 
	 * @return
	 */
	public Individual kantenRekomb() {

		return new Individual();
	}
}
