package controleur;

import modele.*;
import vue.Ihm;

public class ControleurAwale extends Controleur{

    private PlateauAwale plateauAwale = new PlateauAwale();


    public ControleurAwale(Ihm ihm){
        super(ihm);
        super.plateau = this.plateauAwale;

        joueur1 = new Joueur(ihm.demanderNomJoueur(1), false);
        boolean contreIa = ihm.demanderJouerContreIA();
        if (!contreIa){
            joueur2 = new Joueur(ihm.demanderNomJoueur(2), false);
        } else {
            this.joueur2 = new Joueur("IA", true);
        }
    }


    protected void nouvelleManche() {
        this.changementJoueur();
        affichageJoueurActif(etatsPartie(),joueurActuel);
    }

    public void gererCoup(Coup coup){
        CoupAwale coupAwale = (CoupAwale) coup;
        try{
            this.plateauAwale.gererCoup(coupAwale);
            if (this.joueurActuel.isIa()){
                this.ihm.afficherCoupIA(coup.toString());
            }
        }catch (CoupInvalideException e) {
            this.ihm.message(e.getMessage());
            this.affichageJoueurActif(this.plateauAwale.toString(), this.joueurActuel);
        }
    }

     protected void affichageJoueurActif(String etatsPartie, Joueur joueurActuel) {
        int numJoueur = getNumJoueurActuel();
        this.ihm.affichageJoueurActifAwale(etatsPartie, joueurActuel, numJoueur);
    }
}
