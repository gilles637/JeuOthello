package main;
import controleur.ControleurAwale;
import controleur.ControleurOthello;
import vue.Ihm;
import controleur.Controleur;

public class Main {
    public static void main(String[] args) {
        Ihm ihm = new Ihm();
        Controleur controleur;
        if (ihm.choixJeu() == 1) {
            controleur = new ControleurOthello(ihm);
        } else {
            controleur = new ControleurAwale(ihm);
        }
        controleur.jouer();
    }
}
