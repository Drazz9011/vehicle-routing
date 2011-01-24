package de.wesemann.spvrp.data;

import java.awt.Point;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Die städte<br>
 * mit Nachbarstädten und den Strassen dazwischen
 * 
 * @author spinner0815
 * 
 */
public class City {

	private int					cityNumber;
	private Point				coords						= new Point();
	private Map<City, Double>	distanceToNeighbour			= new HashMap<City, Double>();

	private String				name;

	private int					demand						= 0;							// Der Bedarf der Stadt
	private int					frequenzyOfVisit;											// Wie oft die Stadt angesteuert werden will
	private List<Integer>		listOfAllVisitCombinations	= new ArrayList<Integer>();	// Liste der Besuchskombinationen (Achtung!! das ist als
																							// Ganzzahl Codiert) Muss binär gelesen werden
	private int					beginningTimeWindow;										// Früheste Start des Services
	private int					endOfTimeWindow;											// latest Time for start Service
	private int					serviceDuration				= 0;							// Zeit in der der Handelsmann in einer Stadt bleiben muss
																							// damit er seine Arbeit machen kann
	DecimalFormat				f							= new DecimalFormat("#0.00");

	public City() {

	}

	/**
	 * Hinzufügen der Distanz zum Nachbarn
	 * 
	 * @param street
	 *            Die Distanz
	 * @param neighbour
	 *            Der Nachbar
	 */
	public void addDistanceToNeighbour(Double distance, City neighbour) {
		this.distanceToNeighbour.put(neighbour, distance);
	}

	/**
	 * Eine Mögliche Besuchskombination hinzufügen (Ganzzahlig!!!)
	 * 
	 * @param visitCombination
	 */
	public void addVisitCombination(int visitCombination) {
		this.listOfAllVisitCombinations.add(visitCombination);
	}

	/**
	 * @return the beginningTimeWindow
	 */
	public int getBeginningTimeWindow() {
		return beginningTimeWindow;
	}

	/**
	 * @return the cityNumber
	 */
	public int getCityNumber() {
		return cityNumber;
	}

	/**
	 * @return the coords
	 */
	public Point getCoords() {
		return coords;
	}

	/**
	 * Die Kapazität die die Stadt benötigt<br>
	 * default = 200;
	 * 
	 * @return the demand
	 */
	public int getDemand() {
		return demand;
	}

	/**
	 * Die komplette Map der Distanzen, mit Nachbarn
	 * 
	 * @return the distance ToNeighbour Map
	 */
	public Map<City, Double> getDistanceToNeighbour() {
		return distanceToNeighbour;
	}

	/**
	 * Gibt die Distanz zurück
	 * 
	 * @param neighbourCity
	 *            Die Nachbarstadt
	 * @return Die Distanz zwischen der STadt/Kunden und dem Nachbarn
	 */
	public Double getDistanceToNeighbour(City neighbourCity) {
		return distanceToNeighbour.get(neighbourCity);
	}

	/**
	 * @return the endOfTimeWindow
	 */
	public int getEndOfTimeWindow() {
		return endOfTimeWindow;
	}

	/**
	 * Wie oft er die Stadt Periode angefahren werden möchte
	 * 
	 * @return the frequenzyOfVisit
	 */
	public int getFrequenzyOfVisit() {
		return frequenzyOfVisit;
	}

	/**
	 * Liste der möglichen Besuchskombinationen<br>
	 * Dabei ist zu beachten das es sich um einen Ganzzahl handelt <br>
	 * die dann binär codiert und von links nach rechts gelesen werden muss<br>
	 * 1 = Stadt muss an dem Tag befahren werden <bR>
	 * 0= an dem Tag möchte die Stadt nicht befahren werden<br>
	 * Bsp.:
	 * 10 = 1010 => am ersten und am dritten Tag muss die Stadt befahren werden
	 * 
	 * @return the listOfAllVisitCombinations
	 */
	public List<Integer> getListOfAllVisitCombinations() {
		return listOfAllVisitCombinations;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * Die Dauer die der Handelsmann in der STadt bleiben muss <br>
	 * default = 90;
	 * 
	 * @return the serviceDuration
	 */
	public int getServiceDuration() {
		return serviceDuration;
	}

	public String printNeighbours() {
		StringBuilder sb = new StringBuilder();
		for (City c : distanceToNeighbour.keySet()) {
			sb.append(this.cityNumber + " -> " + f.format(distanceToNeighbour.get(c)) + "ZE -> " + c.getCityNumber()
					+ ", ");
		}
		return sb.toString();
	}

	/**
	 * @param beginningTimeWindow
	 *            the beginningTimeWindow to set
	 */
	public void setBeginningTimeWindow(int beginningTimeWindow) {
		this.beginningTimeWindow = beginningTimeWindow;
	}

	/**
	 * @param cityNumber
	 *            the cityNumber to set
	 */
	public void setCityNumber(int cityNumber) {
		this.cityNumber = cityNumber;
	}

	/**
	 * @param coords
	 *            the coords to set
	 */
	public void setCoords(Point coords) {
		this.coords = coords;
	}

	/**
	 * Setzer der Kapazität die die Stadt benötigt<br>
	 * default = 200;
	 * 
	 * @param demand
	 *            the demand to set
	 */
	public void setDemand(int demand) {
		this.demand = demand;
	}

	/**
	 * @param distanceToNeighbour
	 *            the distanceToNeighbour to set
	 */
	public void setDistanceToNeighbour(Map<City, Double> distanceToNeighbour) {
		this.distanceToNeighbour = distanceToNeighbour;
	}

	/**
	 * @param endOfTimeWindow
	 *            the endOfTimeWindow to set
	 */
	public void setEndOfTimeWindow(int endOfTimeWindow) {
		this.endOfTimeWindow = endOfTimeWindow;
	}

	/**
	 * Wie oft er die Stadt Periode angefahren werden möchte
	 * 
	 * @param frequenzyOfVisit
	 *            the frequenzyOfVisit to set
	 */
	public void setFrequenzyOfVisit(int frequenzyOfVisit) {
		this.frequenzyOfVisit = frequenzyOfVisit;
	}

	/**
	 * @param listOfAllVisitCombinations
	 *            the listOfAllVisitCombinations to set
	 */
	public void setListOfAllVisitCombinations(List<Integer> listOfAllVisitCombinations) {
		this.listOfAllVisitCombinations = listOfAllVisitCombinations;
	}

	/**
	 * @param name
	 *            the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * Setzen der Dauer die der Handelsmann in der Stadt bleiben muss<br>
	 * default = 90;
	 * 
	 * @param serviceDuration
	 *            the serviceDuration to set
	 */
	public void setServiceDuration(int serviceDuration) {
		this.serviceDuration = serviceDuration;
	}

	/*
	 * (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "City [cityNumber=" + cityNumber + ", coords=" + coords + ", name=" + name + ", demand=" + demand
				+ ", frequenzyOfVisit=" + frequenzyOfVisit + ", listOfAllVisitCombinations="
				+ listOfAllVisitCombinations + ", beginningTimeWindow=" + beginningTimeWindow + ", endOfTimeWindow="
				+ endOfTimeWindow + ", serviceDuration=" + serviceDuration + "]";
	}
}
