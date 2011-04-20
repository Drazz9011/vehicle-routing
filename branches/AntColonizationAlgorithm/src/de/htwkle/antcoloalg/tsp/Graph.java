package de.htwkle.antcoloalg.tsp;

import java.util.ArrayList;

public class Graph extends ArrayList<Vertex> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String egdeWeightType;
	private String name;
	private int verticesSize;

	/**
	 * @return the egdeWeightType
	 */
	public String getEgdeWeightType() {
		return egdeWeightType;
	}

	/**
	 * @param egdeWeightType
	 *            the egdeWeightType to set
	 */
	public void setEgdeWeightType(String egdeWeightType) {
		this.egdeWeightType = egdeWeightType;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name
	 *            the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the verticesSize
	 */
	public int getVerticesSize() {
		return verticesSize;
	}

	/**
	 * @param verticesSize
	 *            the verticesSize to set
	 */
	public void setVerticesSize(int verticesSize) {
		this.verticesSize = verticesSize;
	}

}
