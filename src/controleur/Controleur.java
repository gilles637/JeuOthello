package controleur;

import modele.Coup;
import modele.CoupInvalideException;
import modele.Joueur;
import modele.Plateau;
import vue.Ihm;

public abstract class Controleur {
    protected Joueur joueur1;
    protected Joueur joueur2;
    protected Joueur joueurActuel;
    protected Ihm ihm;
    protected Plateau plateau;


    public Controleur(Ihm ihm) {
        this.ihm = ihm;
        this.ihm.setControleur(this);

    }

    public void jouer(){
        this.plateau.initialiser();
        if (joueur2.isIa()){
            this.joueur2.setStrategieIA(ihm.choisirMode());
        }
        this.joueurActuel = this.joueur2;
        while(!this.plateau.partieTerminee()) {
            this.nouvelleManche();
        }
        this.ihm.affichageEtats(this.etatsPartie());

        if (this.plateau.estEgalite()) {
            this.ihm.afficherEgalite();
        } else {
            this.ihm.afficherVaiqueur(getJoueur(plateau.getVainqueur()),plateau.compterScore());
            getJoueur(plateau.getVainqueur()).gagnePartie();
        }
        if (this.ihm.demanderRecommencer()) {
            this.jouer();
        } else {
            this.ihm.afficherNombreVictoires(this.joueur1, this.joueur2);
        }

    }
    private Joueur getJoueur(int numJoueur){
        return numJoueur==1 ? joueur1 : joueur2;
    }


    protected String etatsPartie(){
        return this.plateau.toString();
    }

    protected int getNumJoueurActuel() {
        return this.joueurActuel.equals(this.joueur1) ? 1 : 2;
    }

    protected void changementJoueur() {
        if (this.joueurActuel.equals(this.joueur1)) {
            this.joueurActuel = this.joueur2;
        } else {
            this.joueurActuel = this.joueur1;
        }
    }

    public abstract void gererCoup(Coup coup);


    protected abstract void nouvelleManche();



    protected abstract void affichageJoueurActif(String etatsPartie, Joueur joueurActuel);

}
