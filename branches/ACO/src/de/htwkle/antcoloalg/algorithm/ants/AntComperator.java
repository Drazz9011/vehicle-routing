package de.htwkle.antcoloalg.algorithm.ants;

import java.util.Comparator;

import de.htwkle.antcoloalg.algorithm.Ant;

public class AntComperator implements Comparator<Ant>{
	@Override
	public int compare(Ant o1, Ant o2) {
		if (o1.getDistance() < o2.getDistance()) {
			return -1;
		}
		if (o1.getDistance() > o2.getDistance()) {
			return 1;
		}
		return 0;
	}
}
