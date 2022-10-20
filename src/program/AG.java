package program;

import java.util.ArrayList;
import java.util.Random;

public class AG {
    private ArrayList<Individuo> populacao;
    private int numeroDeGeracoes, intervalo;
    private double taxaMutacao;

    public AG(ArrayList<Individuo> populacao, int intervalo, double taxaMutacao, int numeroDeGeracoes) {
        this.populacao = populacao;
        this.intervalo = intervalo;
        this.numeroDeGeracoes = numeroDeGeracoes;
        this.taxaMutacao = taxaMutacao;
    }

    public void executar(){

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
        mutacao(filho1);
        filho2 = new Individuo(i2.getX1(), i1.getX2());
        mutacao(filho2);

        populacao.add(filho2);
        populacao.add(filho1);
    }

    public void mutacao(Individuo i){
        Random random = new Random();
        double mutacao = random.nextDouble() * 100.0;
        if(mutacao < taxaMutacao){
            int x = random.nextInt(2)+1;
            switch(x){
                case 1:
                    i.setX1(random.nextInt(100)+1);
                    funcFitness(i);
                    break;
                case 2:
                i.setX2(random.nextInt(100)+1);
                funcFitness(i);
                break;
            }
        }
    }

}
