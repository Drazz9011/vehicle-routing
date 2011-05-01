package de.htwkle.antcoloalg;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import de.htwkle.antcoloalg.algorithm.ACAlgorithm;
import de.htwkle.antcoloalg.tsp.Problem;

public class Starter {

	private static Map<String, Problem> PROBLEMS_BKV = new HashMap<String, Problem>();
	static {
		PROBLEMS_BKV
				.put("China", new Problem(
						"http://www.tsp.gatech.edu//world/ch71009.tsp",
						4566506, 71009));
		PROBLEMS_BKV.put("Argentina", new Problem(
				"http://www.tsp.gatech.edu/world/ar9152.tsp", 837479, 9152));
		PROBLEMS_BKV.put("Luxembourg", new Problem(
				"http://www.tsp.gatech.edu/world/lu980.tsp", 11340, 980));
		PROBLEMS_BKV.put("Oman", new Problem(
				"http://www.tsp.gatech.edu/world/mu1979.tsp", 86891, 1979));
		PROBLEMS_BKV.put("Djibouti", new Problem(
				"http://www.tsp.gatech.edu/world/dj38.tsp", 6656, 38));
		//http://www.tsp.gatech.edu/data/ml/mona-lisa100K.tsp
		PROBLEMS_BKV.put("MonaLisa", new Problem("http://www.tsp.gatech.edu/data/ml/mona-lisa100K.tsp", 5757191, 100000));
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {

		TSPReader tspr = new TSPReader();

		try {
			System.out.println("start");
			
			long start = System.currentTimeMillis();
			tspr.readProblem(new URL(PROBLEMS_BKV.get("Luxembourg").url));
			System.out.println(System.currentTimeMillis() - start + " ms");
//			System.out.println(tspr.getGraph().getVerticesSize());
//			 tspr.printVertices();
			// tspr.getAdjanciesSizePerVertex();
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		ACAlgorithm acAlg = new ACAlgorithm(tspr.getGraph());
		acAlg.startAlg();
	}
}
