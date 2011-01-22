package de.wesemann.spvrp.data;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;

public class Store {
	/**
	 * TODO - Datei name: Zeitstempel_AnzIndi_AnzPops_rekombwkt_mutwkt_Name der geladenen Datei
	 * - Runde;best indi(fitness); worst indi(fitness); Rekomb(bool); indi1(fitness); indi2(fitness); indiNeu; Mut(bool);
	 * - nach durchlaufen der pops:
	 * Vehicle; Last; Streckedauer (ohne Kunden Zeit); a->b->c->
	 */

	String		fileName;
	File		file;
	PrintWriter	writer;

	public Store() {

	}

	public void createFileAndHead(String fileName) {
		this.fileName = fileName;
		file = new File(fileName + ".csv");
		try {
			writer = new PrintWriter(file);
//			writer.write("Runde;best indi(fitness); worst indi(fitness); Rekomb(bool); indi1(fitness); indi2(fitness); indiNeu; Mut(bool);");
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * speichert die aktuelle Population
	 * Runde;best indi(fitness); worst indi(fitness); Rekomb(bool); indi1(fitness); indi2(fitness); indiNeu; Mut(bool);
	 */
	public void storeDataPopulation(String aktuelleDaten) {

		writer.append("\n" + aktuelleDaten);

	}

	public void closeWriter() {
		writer.close();
	}

	/**
	 * speichert das beste indi. Dies geschieht nachdem der alg mit allen Generationen fertig ist
	 * Dauer des gesamten Durchgangs
	 */
	public void storeBestIndi() {

	}
}
