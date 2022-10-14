package program;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class AG {
    List<Individuo> populacao = new ArrayList<>();

    public void algoritmoGenetico(){

    }

    public Individuo selecao(Individuo i1, Individuo i2, Individuo i3) {
        Random random = new Random();
        int escolhido = random.nextInt(3) + 1;

        switch (escolhido) {
            case 1:
                return i1;
            case 2:
                return i2;
            case 3:
                return i3;
            default:
                return null;
        }
    }

    public void funcFitness(Individuo ind){
        double fitness;
        //COLOCA A FUNÇÃO AQUI, ANIMAL
        fitness = ind.getX1() - ind.getX2();
        ind.setFitness(fitness);
    }

    public void crossover(Individuo i1, Individuo i2){
        Individuo filho1, filho2;

        filho1 = new Individuo(i1.getX1(), i2.getX2());
        filho2 = new Individuo(i2.getX1(), i1.getX2());
        populacao.add(filho2);
        populacao.add(filho1);

    }



}
