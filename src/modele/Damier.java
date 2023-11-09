package modele;


import java.util.ArrayList;
import java.util.List;

public class Damier implements Plateau{

    private final String[][] lesJetons = new String[8][8];
    private final String VIDE = "\uD83D\uDFE9";
    private final String NOIR = "⚫";
    private final String BLANC = "⚪";



    public Damier(){

    }

    public void initialiser(){
        for(int i = 0; i < lesJetons.length; ++i) {
            for(int j = 0; j < lesJetons.length; ++j) {
                this.lesJetons[i][j] = VIDE;
            }
        }

        lesJetons[lesJetons.length/2-1][lesJetons.length/2-1] = BLANC;
        lesJetons[lesJetons.length/2-1][lesJetons.length/2] = NOIR;
        lesJetons[lesJetons.length/2][lesJetons.length/2-1] = NOIR;
        lesJetons[lesJetons.length/2][lesJetons.length/2] = BLANC;


    }


    public String toString(){
        String res = "  ";
        for (int i = 0; i< lesJetons.length; ++i){
            res= res + "    " + (char) (i+65);
        }
        res = res +"\n";
        for (int i = 0; i < lesJetons.length; ++i) {
            res = res + (i + 1);
            for (int j = 0; j < lesJetons.length; ++j) {
                res = res + this.lesJetons[i][j];
            }
            res = res + "\n";
        }
        return res;
    }

    public boolean partieTerminee() {
        return !peutJouer(1) && !peutJouer(2);
    }


    public boolean estEgalite() {
        return compterScore()[0] == compterScore()[1];
    }
    public int[] compterScore(){
        int scoreNoirs = 0;
        int scoreBlancs = 0;

        for (int i = 0; i < lesJetons.length; i++) {
            for (int j = 0; j < lesJetons.length; j++) {
                if (NOIR.equals(lesJetons[i][j])) {
                    scoreNoirs++;
                } else if (BLANC.equals(lesJetons[i][j])) {
                    scoreBlancs++;
                }
            }
        }
        return new int[]{scoreNoirs,scoreBlancs};
    }


    public int getVainqueur(){
        int scoreJoueur1 = compterScore()[0];
        int scoreJoueur2 = compterScore()[1];
        return scoreJoueur1>scoreJoueur2 ? 1 : 2;
    }

    public void gererCoup(CoupOthello coup) throws CoupInvalideException {
        boolean retourner = true;
        int colonne = coup.getColonne();
        int ligne = coup.getLigne();
        int numJoueur = coup.getNumJoueur();
        String couleur = getCouleur(numJoueur);

        if (estCoupValide(ligne,colonne,numJoueur, retourner)){
            this.lesJetons[ligne][colonne] = couleur;

        }else {
            throw new CoupInvalideException("Le coup est invalide, rejouez");
        }
    }





    private boolean checkDirection(int ligne, int colonne, int deltaLigne, int deltaColonne, int numJoueur, boolean retourner) {
        int l = ligne + deltaLigne;
        int c = colonne + deltaColonne;

        while (lesJetons[l][c].equals(getCouleur(getJoueurOppose(numJoueur))) && c < lesJetons.length) {
            l += deltaLigne;
            c += deltaColonne;

            if (l > -1 && l < lesJetons.length && c > -1 && c < lesJetons.length){
                if (lesJetons[l][c].equals(getCouleur(numJoueur))){
                    if (retourner){
                        retournerJetons(ligne, colonne, deltaLigne, deltaColonne, numJoueur);
                    }

                    return true;
                }
            }else {
                return false;
            }
        }
        return false;
    }



    private boolean estCoupValide(int ligne, int colonne, int numJoueur, boolean retourner){
        boolean estValide=false;


        if (!lesJetons[ligne][colonne].equals(VIDE)) {
            return false;
        }

        for (int deltaLigne = -1; deltaLigne <= 1; deltaLigne++) {
            for (int deltaColonne = -1; deltaColonne <= 1; deltaColonne++) {
                if (deltaLigne == 0 && deltaColonne == 0) {
                    continue;
                }
                int ligneDecale = ligne + deltaLigne;
                int colonneDecale = colonne + deltaColonne;
                if (ligneDecale > -1 && ligneDecale < lesJetons.length && colonneDecale > -1 && colonneDecale < lesJetons.length){
                    if (lesJetons[ligneDecale][colonneDecale]!=null){
                        if (lesJetons[ligneDecale][colonneDecale].equals(getCouleur(getJoueurOppose(numJoueur)))){
                            if (checkDirection(ligne, colonne, deltaLigne, deltaColonne, numJoueur, retourner)) {
                                estValide = true;
                            }
                        }
                    }
                }
            }
        }
        return estValide;
    }



    private void retournerJetons(int ligne, int colonne, int deltaLigne, int deltaColonne, int numJoueur) {
        int l = ligne + deltaLigne;
        int c = colonne + deltaColonne;
        while (!lesJetons[l][c].equals(getCouleur(numJoueur))) {
            lesJetons[l][c] = getCouleur(numJoueur);
            l += deltaLigne;
            c += deltaColonne;
        }
    }

    public boolean peutJouer(int numJoueur) {
        boolean retourner = false;
        boolean peutJouer = false;
        for (int i = 0; i < lesJetons.length; i++) {
            for (int j = 0; j < lesJetons.length; j++) {
                if (lesJetons[i][j].equals(VIDE)){
                    if (estCoupValide(i, j, numJoueur, retourner)) {
                        peutJouer=true;
                    }
                }
            }
        }
        return peutJouer;
    }


    public List<CoupOthello> getListeCoups(int numJoueur){
        boolean retourner = false;
        List<CoupOthello> listeCoupOthellos = new ArrayList<>();
        for (int i = 0; i < lesJetons.length; i++) {
            for (int j = 0; j < lesJetons.length; j++) {
                if (lesJetons[i][j].equals(VIDE)){
                    if (estCoupValide(i, j, numJoueur, retourner)) {
                        int[] ligneColonne = new int[2];
                        ligneColonne[0]=i;
                        ligneColonne[1]=j;
                        listeCoupOthellos.add(new CoupOthello(ligneColonne, numJoueur));
                    }
                }

            }
        }
        return listeCoupOthellos;
    }

    public Damier cloner() {
        Damier copie = new Damier();
        for (int i = 0; i < lesJetons.length; i++) {
            for (int j = 0; j < lesJetons[i].length; j++) {
                copie.lesJetons[i][j] = lesJetons[i][j];
            }
        }
        return copie;
    }

    public String[][] getLesJetons() {
        return lesJetons;
    }

    public int getJoueurOppose(int numJoueur){ return numJoueur == 1 ? 2 : 1;}

    public String getCouleur(int numJoueur) {
        return numJoueur == 1 ? NOIR : BLANC;
    }
}
