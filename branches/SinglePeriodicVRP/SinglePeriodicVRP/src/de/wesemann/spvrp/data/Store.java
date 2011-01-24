package de.wesemann.spvrp.data;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class Store {
	/**
	 * TODO - Datei name: Zeitstempel_AnzIndi_AnzPops_rekombwkt_mutwkt_Name der geladenen Datei
	 * - Runde;best indi(fitness); worst indi(fitness); Rekomb(bool); indi1(fitness); indi2(fitness); indiNeu; Mut(bool);
	 * - nach durchlaufen der pops:
	 * Vehicle; Last; Streckedauer (ohne Kunden Zeit); a->b->c->
	 */

	File		file;
	FileWriter	writer;
	String		dir;

	public Store() {

	}

	/**
	 * creates the dir
	 * 
	 * @param dir
	 *            the dir to create
	 */
	public Store(String dir) {

		this.dir = dir;
		File file = new File(dir);
		file.mkdir();
	}

	public void closeWriter() {
		try {
			writer.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * speichert die aktuelle Population
	 * Runde;best indi(fitness); worst indi(fitness); Rekomb(bool); indi1(fitness); indi2(fitness); indiNeu; Mut(bool);
	 */
	public void storeData(String aktuelleDaten, String fileName) {
		File file = new File(dir, fileName);
		if (!file.exists()) {
			try {
				file.createNewFile();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		try {
			writer = new FileWriter(file);

			writer.write(aktuelleDaten);
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

}
