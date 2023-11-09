package modele;

import java.util.Objects;

public class Joueur {
    private final String nom;
    private int nbPartiesGagnees;
    private final boolean ia;
    protected StrategieIA strategieIA;


    public Joueur(String nom, boolean ia) {

        this.nom = nom;
        this.ia = ia;
    }

    public String getNom() {return this.nom;}

    public int getNbPartiesGagnees() {
        return this.nbPartiesGagnees;
    }

    public void gagnePartie() {
        ++this.nbPartiesGagnees;
    }

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        } else if (o != null && this.getClass() == o.getClass()) {
            Joueur joueur = (Joueur)o;
            return this.nbPartiesGagnees == joueur.nbPartiesGagnees && Objects.equals(this.nom, joueur.nom);
        } else {
            return false;
        }
    }

    public boolean isIa() {
        return ia;
    }

    public void setStrategieIA(int choix) {
        if (this.isIa()) {
            if (choix == 1){
                this.strategieIA = new StrategieIAAleatoire();
            }else if (choix == 2){
                this.strategieIA = new StrategieIAMinimax();
            }
        }
    }

    public CoupOthello getCoupIa(Damier damier) {
        return strategieIA.getCoupIa(damier);
    }


}
