package program;

public class Individuo {
    double fitness;
    private int x1, x2;

    public Individuo(int x1, int x2) {
        this.x1 = x1;
        this.x2 = x2;
    }

    public int getX1() {
        return x1;
    }

    public int getX2() {
        return x2;
    }

    public void setX1(int x1) {
        this.x1 = x1;
    }

    public void setX2(int x2) {
        this.x2 = x2;
    }

    public double getFitness() {
        return fitness;
    }

    public void setFitness(double fitness) {
        this.fitness = fitness;
    }

    @Override
    public String toString(){
        
        return "X1 e X2, respectivamente: "+ x1 + ", "+ x2 +"\n"
        + "Fitness: " + fitness;

    }
}
