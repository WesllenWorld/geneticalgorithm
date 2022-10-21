package program;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Random;
import java.util.concurrent.TimeUnit;

public class AG {
    private ArrayList<ArrayList<Individuo>> geracoes;
    private ArrayList<Individuo> populacao;
    private int numeroDeGeracoes, intervalo;
    private double taxaMutacao;

    public AG(ArrayList<Individuo> populacao, int intervalo, double taxaMutacao, int numeroDeGeracoes) {
        this.populacao = populacao;
        this.intervalo = intervalo;
        this.numeroDeGeracoes = numeroDeGeracoes;
        this.taxaMutacao = taxaMutacao;
    }

    public void executar() {
        ArrayList<Individuo> torneio = new ArrayList<>(), novaPopulacao = new ArrayList<>();
        geracoes = new ArrayList<ArrayList<Individuo>>();

        // gerando o fitness de cada indivíduo da população
        for (Individuo ind : populacao) {
            funcFitness(ind);
        }

        for (int i = 0; i < numeroDeGeracoes; i++) {
            populacao.sort(Comparator.comparing(Individuo::getFitness).reversed());
            novaPopulacao.add(populacao.get(0));
            novaPopulacao.add(populacao.get(1));
            Individuo melhor = populacao.get(0);

            for (int j = 0; j < populacao.size(); j++) {

                // torneios
                montarTorneio(torneio);
                Individuo pai1 = selecao(torneio);
                torneio.clear();

                montarTorneio(torneio);
                Individuo pai2 = selecao(torneio);
                torneio.clear();

                crossover(pai1, pai2, novaPopulacao);
                
                

            }

            populacao.stream().forEach(p -> System.out.println(p.toString()));

            System.out.println("MELHOR:");
            System.out.println(melhor.toString());
            
          //  try {
           //     TimeUnit.SECONDS.sleep(5);
            //} catch (InterruptedException e) {
                // TODO Auto-generated catch block
           //     e.printStackTrace();
          //  }
            geracoes.add(populacao);
            populacao = novaPopulacao;
        }
    }

    public void montarTorneio(ArrayList<Individuo> torneio) {

        Individuo primeiro, segundo, terceiro;
        Random candidatosDoTorneio = new Random();

        primeiro = populacao.get(candidatosDoTorneio.nextInt(populacao.size()));
        torneio.add(primeiro);

        segundo = populacao.get(candidatosDoTorneio.nextInt(populacao.size()));
        while (torneio.contains(segundo)) {
            segundo = populacao.get(candidatosDoTorneio.nextInt(populacao.size()));
        }
        torneio.add(segundo);

        terceiro = populacao.get(candidatosDoTorneio.nextInt(populacao.size()));
        while (torneio.contains(terceiro)) {
            terceiro = populacao.get(candidatosDoTorneio.nextInt(populacao.size()));
        }
        torneio.add(terceiro);

    }

    public Individuo selecao(ArrayList<Individuo> torneio) {
        Individuo aux, maior;
        Individuo i1 = torneio.get(0),
                i2 = torneio.get(1),
                i3 = torneio.get(2);

        if (i1.getFitness() == i2.getFitness() && i2.getFitness() == i3.getFitness()) {
            Random random = new Random();
            int escolhido = random.nextInt(3) + 1;
            switch (escolhido) {
                case 1:
                    return i1;
                case 2:
                    return i2;
                case 3:
                    return i3;
            }
        }

        if (i1.getFitness() == i2.getFitness() && i1.getFitness() > i3.getFitness()) {
            maior = torneioDoisIguais(i1, i2);
            return maior;
        }
        if (i1.getFitness() == i3.getFitness() && i1.getFitness() > i2.getFitness()) {
            maior = torneioDoisIguais(i1, i3);
            return maior;
        }
        if (i2.getFitness() == i3.getFitness() && i1.getFitness() > i1.getFitness()) {
            maior = torneioDoisIguais(i2, i3);
            return maior;
        }

        aux = i1.getFitness() > i2.getFitness() ? i1 : i2;
        maior = aux.getFitness() > i3.getFitness() ? aux : i3;
        return maior;
    }

    private Individuo torneioDoisIguais(Individuo um, Individuo dois) {
        // compara e retorna maior
        return um.getFitness() > dois.getFitness() ? um : dois;
    }

    public void funcFitness(Individuo ind) {
        double fitness;
        // COLOCA A FUNÇÃO AQUI, ANIMAL
        fitness = ind.getX1() - ind.getX2();
        ind.setFitness(fitness);
    }

    public void crossover(Individuo i1, Individuo i2, ArrayList<Individuo> novaPopulacao) {
        Individuo filho1, filho2;

        filho1 = new Individuo(i1.getX1(), i2.getX2());
        mutacao(filho1);
        filho2 = new Individuo(i2.getX1(), i1.getX2());
        mutacao(filho2);

        novaPopulacao.add(filho2);
        novaPopulacao.add(filho1);
    }

    public void mutacao(Individuo i) {
        Random random = new Random();
        double mutacao = random.nextDouble();
        if (mutacao < taxaMutacao) {
            int x = random.nextInt(2) + 1;
            switch (x) {
                case 1:
                    i.setX1(random.nextInt(100) + 1);
                    funcFitness(i);
                    break;
                case 2:
                    i.setX2(random.nextInt(100) + 1);
                    funcFitness(i);
                    break;
            }
        }
    }

}
