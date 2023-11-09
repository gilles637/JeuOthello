package controleur;

import modele.*;
import vue.Ihm;

public class ControleurOthello extends Controleur{

    private Damier damier = new Damier();

    public ControleurOthello(Ihm ihm){
        super(ihm);
        super.plateau = this.damier;

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
        if (joueurActuel.isIa()){
            if (damier.peutJouer(2)){
                gererCoup(joueurActuel.getCoupIa(damier));
            }else {
                ihm.afficherIaPasseTour();
            }

        }else{
            affichageJoueurActif(etatsPartie(),joueurActuel);
        }
    }

    public void gererCoup(Coup coup){
        CoupOthello coupOthello = (CoupOthello) coup;
        if (coupOthello.getInputString()==null){
            try{
                this.damier.gererCoup(coupOthello);
                if (this.joueurActuel.isIa()){
                    this.ihm.afficherCoupIA(coup.toString());
                }
            }catch (CoupInvalideException e) {
                this.ihm.message(e.getMessage());
                this.affichageJoueurActif(this.damier.toString(), this.joueurActuel);
            }
        } else if (coupOthello.getInputString().equals("P")) {
            if (!damier.peutJouer(getNumJoueurActuel())){
                nouvelleManche();
            } else if (damier.peutJouer(getNumJoueurActuel())) {
                changementJoueur();
                ihm.afficherJoueurPeutPasPasserTour();
            }
        }
    }

    protected void affichageJoueurActif(String etatsPartie, Joueur joueurActuel) {
        int numJoueur = getNumJoueurActuel();
        this.ihm.affichageJoueurActifOthello(etatsPartie, joueurActuel, numJoueur);
    }
}
