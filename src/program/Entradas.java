package program;

import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

public class Entradas {
    ArrayList<Individuo> populacao;

    public Entradas() {
        this.populacao = new ArrayList<Individuo>();
    }

    public void call() {
        Random gerador = new Random();
        Scanner entrada = new Scanner(System.in);
        int intervalo, numeroDeGeracoes;
        double taxaMutacao;

        gerarPopulacao(entrada, gerador);

        intervalo = intervalo(entrada);

        taxaMutacao = taxaMutacao(entrada);

        numeroDeGeracoes = numeroDeGeracoes(entrada);

        AG algGen = new AG(populacao, intervalo, taxaMutacao, numeroDeGeracoes);
        entrada.close();
        algGen.executar();
    }

    private void gerarPopulacao(Scanner entrada, Random gerador) {
        int nIndividuos;
        System.out.println("Opa, diz quantos indivíduos tu quer, meu patrão. Lembra de botar no mínimo 3:");
        while(true){
            nIndividuos = entrada.nextInt();
            if(nIndividuos >= 3){
                break;
            }
            System.out.println("Lembra de botar no mínimo 3:");
        }

        for (int i = 0; i < nIndividuos; i++) {
            Individuo novo = new Individuo(gerador.nextInt(100) + 1, gerador.nextInt(100) + 1);
            populacao.add(novo);
        }
    }

    private int intervalo(Scanner entrada) {
        int intervalo = 0;

        System.out.println("Agora manda o intervalo, parça (quantos vão ser substituídos)."+'\n'+
        "Lembra que como tem elitismo de 2, os dois não estão inclusos, então tem que ser menor que "+(populacao.size()-2)+" :");
        while (true) {
            intervalo = entrada.nextInt();
            if (intervalo < 1 || intervalo > populacao.size() - 2) {
                System.out.println("Respeite a lógica e dê um número dentro do intervalo!");
                System.out.println("Tenta de novo:");
            } else {
                return intervalo;
            }
        }
    }

    private double taxaMutacao(Scanner entrada) {
        double taxa;
        System.out.println("Beleza, mas diz aí a taxa de mutação pra eu testar um negóço aqui:");
        while (true) {
            taxa = entrada.nextDouble();
            if (taxa < 0 || taxa > 1) {
                System.out.println("Respeite a lógica e dê um número decimal entre 0 e 1!");
                System.out.println("Tenta de novo:");
            } else {
                return taxa;
            }
        }
    }

    public int numeroDeGeracoes(Scanner entrada) {
        int geracoes = 0;

        System.out.println("Chefia, manda o número de gerações:");
        while (true) {
            geracoes = entrada.nextInt();
            if (geracoes < 1) {
                System.out.println("Pelo menos um, cara...");
                System.out.println("Tenta de novo:");
            } else {
                return geracoes;
            }
        }
    }
}
