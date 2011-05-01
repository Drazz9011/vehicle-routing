package de.htwkle.antcoloalg.tsp;


public class Edge {

        private final Vertex target;
        private final double distance;
        private double pheromone = 0;
        private double selectionProbability = 0;

        public Edge(Vertex argTarget, double distance) {
                target = argTarget;
                this.distance = distance;
        }

        public double getPheromone() {
                return pheromone;
        }

        public void setPheromone(double pheromone) {
                this.pheromone = pheromone;
        }

        public double getSelectionProbability() {
                return selectionProbability;
        }

        public void setSelectionProbability(double selectionProbability) {
                this.selectionProbability = selectionProbability;
        }

        public Vertex getTarget() {
                return target;
        }

        public double getDistance() {
                return distance;
        }

        public void addPheromone(double pheromone) {
                this.pheromone += pheromone;
        }

        public void addSelectionProbability(double selectionProbability) {
                this.selectionProbability += selectionProbability;
        }

}