/**
 * 
 */
package de.wesemann.spvrp.calculation;

import java.util.Random;

/**
 * @author spinner0815
 * 
 */
public class Mutations {

	/**
	 * Verschiebe Mutation wie es im Weicker steht XD
	 * 
	 * @param child
	 *            das kind das mutiert werden soll
	 * @return das fertig mutierte Kind
	 */
	public static Individual verschiebeMutation(Individual child) {

		// mutChild.setCities(child.getCities());
		Random rndm = new Random();

		// -----------entfernen des ersten und letzten -1 -----------------------
		child.getGenom().remove(0);
		child.getGenom().remove(child.getGenom().size() - 1);

		// --------------------zufallszahlen für die Positionen die getauscht werden sollen ------------------------
		int u1 = rndm.nextInt(child.getGenom().size() - 1);
		int u2 = rndm.nextInt(child.getGenom().size() - 1);
		// System.out.println("oldchild: " + child.getGenom().toString());
		// System.out.println("u1: " + u1 + " u2: " + u2);

		// ----------------Tauschen der Positionen -----------------------------------
		if (u1 > u2) {
			int zw = child.getGenom().get(u2);
			child.getGenom().remove(u2);
			child.getGenom().add(u1, zw);
			// for(int j=u2;j<=u1-1;j++){
			// child.getGenom().set(j+1, child.getGenom().get(j));
			// }
		} else {
			int zw = child.getGenom().get(u1);
			child.getGenom().remove(u1);
			child.getGenom().add(u2, zw);
			// for(int j=u1+1;j<=u2;j++){
			// child.getGenom().set(j-1, child.getGenom().get(j));
			// }
		}
		// --------------------- hinzufügen des ersten und letzten -1 -----------------------
		child.getGenom().add(0, -1);
		child.getGenom().add(-1);

		// System.out.println("newchild: " + child.getGenom().toString());
		child.createCityList(); // berechnen des ganzen spasses

		return child;

	}
}
