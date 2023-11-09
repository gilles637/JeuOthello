package modele;


import java.util.Random;

public class StrategieIAAleatoire implements StrategieIA{

    public StrategieIAAleatoire(){

    }


    public CoupOthello getCoupIa(Damier damier){

        Random random = new Random();
        return damier.getListeCoups(2).get(random.nextInt(damier.getListeCoups(2).size()));
    }
}
