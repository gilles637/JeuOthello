package modele;

public class CoupAwale implements Coup{

    private int colonne;
    private int numJoueur;
    public CoupAwale(int colonne, int numJoueur){
        this.colonne = colonne;
        this.numJoueur = numJoueur;
    }

    public int getColonne() {
        return colonne;
    }

    public int getNumJoueur() {
        return numJoueur;
    }

    @Override
    public String toString() {
        return "Colonne : " + colonne;
    }
}
