package modele;

public class CoupOthello implements Coup {


    private String inputString;
    private int ligne;
    private int colonne;
    private int numJoueur;

    public CoupOthello(String inputString, int numJoueur){
        this.inputString = inputString;
        this.numJoueur = numJoueur;

    }

    public CoupOthello(int[] ligneColonne, int numJoueur){
        this.numJoueur = numJoueur;
        this.ligne = ligneColonne[0];
        this.colonne = ligneColonne[1];
    }

    public int getLigne() {return ligne;}
    public int getColonne() {return colonne;}

    public int getNumJoueur() {return numJoueur;}
    public String getInputString() {return inputString;}


    public String toString(){
        int ligne = this.ligne+1;

        char colonne = (char) (this.colonne+65);
        return  ligne +" "+ colonne;
    }
}
