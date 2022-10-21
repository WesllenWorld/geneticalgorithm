package program;

import java.util.ArrayList;
import java.util.Comparator;
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

    public void executar() {
        ArrayList<Individuo> torneio = new ArrayList<>(), novaPopulacao = new ArrayList<>();

        for (int i = 0; i < numeroDeGeracoes + 1; i++) {

            for (Individuo ind : populacao) {
                double fitnessResultado = funcFitness(ind);
                ind.setFitness(fitnessResultado);
            }

            populacao.sort(Comparator.comparing(Individuo::getFitness).reversed());
            
            Individuo melhor = populacao.get(0);
            Individuo segundoMelhor = populacao.get(1);

            for (int j = 0; j < populacao.size(); j++) {
                Individuo pai1, pai2;
                // torneios
                montarTorneio(torneio);
                pai1 = selecao(torneio);
                torneio.clear();

                //garantir que o mesmo pai não seja pai1 e pai2
                boolean paiRepetido = true;
                while (paiRepetido) {
                    montarTorneio(torneio);
                    if (!torneio.contains(pai1)) {
                        break;
                    }
                    torneio.clear();
                }

                pai2 = selecao(torneio);
                torneio.clear();
                crossover(pai1, pai2, novaPopulacao);
            }
            populacao.stream().forEach(p -> System.out.println(p.toString()));
            System.out.println('\n' + "MELHOR:");
            System.out.println(melhor.toString() + '\n');
            gerarNovaPopulacao(novaPopulacao, melhor, segundoMelhor);
        }
    }

    public void gerarNovaPopulacao(ArrayList<Individuo> novaPopulacao, Individuo elite1, Individuo elite2) {

        for (Individuo ind : novaPopulacao) {
            double fitnessResultado = funcFitness(ind);
            ind.setFitness(fitnessResultado);
        }
        
        populacao.remove(elite1);
        populacao.remove(elite2);

        ArrayList<Individuo> temp = new ArrayList<>();
        temp.add(elite1);
        temp.add(elite2);

        for(int i = 0; i < intervalo - 2; i++){
            temp.add(populacao.get(i));
        }

        novaPopulacao.sort(Comparator.comparing(Individuo::getFitness).reversed());
       
        for (int i = 0; i < intervalo; i++) {
            temp.add(novaPopulacao.get(i));
        }

        //geracoes.add(populacao);
        novaPopulacao = temp;
        populacao.clear();
        populacao.addAll(novaPopulacao);
        novaPopulacao.clear();
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
        // sorteia um dos dois iguais
        Random escolha = new Random();
        return escolha.nextInt(2) + 1 == 1 ? um : dois;
    }

    public double funcFitness(Individuo ind) {
        int x1 = ind.getX1(), x2 = ind.getX2();
        // COLOCA A FUNÇÃO AQUI, ANIMAL
        return (x1 - Math.log(x2))/(Math.pow(x1, 2) - 3 * x2);
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
                    // funcFitness(i);
                    break;
                case 2:
                    i.setX2(random.nextInt(100) + 1);
                    // funcFitness(i);
                    break;
            }
        }
    }

}
