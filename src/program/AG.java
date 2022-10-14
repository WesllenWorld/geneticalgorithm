package program;

import java.util.Random;

public class AG {

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

    



}
