package vue;
import controleur.Controleur;
import modele.CoupOthello;
import modele.CoupAwale;
import modele.Joueur;

import java.util.Scanner;


public class Ihm {
    private Scanner input;
    private Controleur controleur;
    public Ihm() {
        this.input = new Scanner(System.in);
    }
    public void setControleur(Controleur controleur) {
        this.controleur = controleur;
    }



    public void affichageJoueurActifOthello(String etat, Joueur joueurActuel, int numJoueur) {
        while (true) {
            this.affichageEtats(etat);
            System.out.println(joueurActuel.getNom() + " :  à vous de jouer. Saisir une ligne entre 1 et 8 suivie d’une " +
                    "lettre entre A et H (ex : 3 D) ou P pour passer son tour.");
            if (this.input.hasNext()) {
                if (this.input.hasNextLine()) {
                    String n = this.input.nextLine().toUpperCase();
                    if (n.split(" ").length==1 && n.length()==1){
                        if (n.equals("P")){
                            this.controleur.gererCoup(new CoupOthello(n, numJoueur));
                            return;
                        }
                    } else if (n.split(" ").length == 2 && n.split(" ")[0].length() == 1 && n.split(" ")[1].length() == 1) {
                        if (estconversionPossible(n)) {
                            int[] nInt = conversionCoup(n);
                            if (nInt[0] > -1 && nInt[0] < 8 && nInt[1] > -1 && nInt[1] < 8) {
                                this.controleur.gererCoup(new CoupOthello(nInt, numJoueur));
                                return;
                            }
                        }
                    }
                }

                this.message("valeur invalide, entrer une ligne entre 1 et 8 suivie d’une " +
                        "lettre entre A et H (ex : 3 D) ou P pour passer son tour.");
            }
        }
    }

    public void affichageJoueurActifAwale(String etat, Joueur joueurActuel, int numJoueur){
        while(true) {
            this.affichageEtats(etat);
            System.out.println(joueurActuel.getNom() + " :  à vous de jouer. Saisir une colonne entre 1 et 6");
            if (this.input.hasNext()) {
                if (this.input.hasNextInt()) {
                    int n = this.input.nextInt() - 1;
                    if (n > -1 && n < 6) {
                        this.input.nextLine();
                        this.controleur.gererCoup(new CoupAwale(n, numJoueur));
                        return;
                    }
                }
                this.message("valeur invalide, entrer un entier entre 1 et 6");
                this.input.nextLine();

            }
        }
    }

    private int[] conversionCoup (String ligneColonne){

        String[] splitStr = ligneColonne.split(" ");
        String ligneTemporaire = splitStr[0];
        String colonneTemporaire = splitStr[1];
        char colonneChar = colonneTemporaire.charAt(0);
        int ligne = Integer.parseInt(ligneTemporaire) - 1;
        int colonne = Character.getNumericValue(colonneChar) - 10;
        return new int[]{ligne, colonne};
    }

    private boolean estconversionPossible (String ligneColonne){
        String[] splitStr = ligneColonne.split(" ");
        String ligneTemporaire = splitStr[0];
        String colonneTemporaire = splitStr[1];
        if (ligneTemporaire.matches("-?\\d+")) {
            char colonneChar = colonneTemporaire.charAt(0);
            int ligne = Integer.parseInt(ligneTemporaire) - 1;
            int colonne = Character.getNumericValue(colonneChar) - 10;
            return ligne > -1 && ligne < 8 && colonne > -1 && colonne < 8;
        }
        return false;

    }
    public void afficherJoueurPeutPasPasserTour(){
        message("Vous ne pouvez pas passer votre tour car il existe des possibilitées de coups légaux, réessayer");
    }

    public void afficherIaPasseTour(){
        message("L'IA à passé son tour car il ne peut pas jouer");
    }





    public String demanderNomJoueur(int numJoueur) {
        do {
            System.out.println("Entrer un nom pour le joueur numero " + numJoueur);
        } while(!this.input.hasNext());

        String reponse = this.input.next();
        this.input.nextLine();
        return reponse;
    }

    public void affichageEtats(String etat) {
        System.out.println("Etats de la partie :\n" + etat);
    }

    public void message(String message) {System.out.println(message);}


    public void afficherVaiqueur(Joueur vainqueur,int[] score) {
        System.out.println("Le joueur " + vainqueur.getNom() + " à gagner la partie \n" + "Score du joueur 1 : " + score[0]
                +"      Score du joueur 2 : " + score[1]);

    }

    public boolean demanderRecommencer() {
        while (true) {
            System.out.println("Voulez vous recommencer");
            if (input.hasNext()) {
                String reponse = input.next().toLowerCase();
                if (reponse.equals("oui") || reponse.equals("o") || reponse.equals("yes") || reponse.equals("y")) {
                    return true;
                } else if (reponse.equals("non") || reponse.equals("n") || reponse.equals("no")) {
                    return false;
                }
            }
            this.message("Erreur, veuillez choisir 'oui' ou 'non'");
            input.nextLine();

        }
    }

    public void afficherNombreVictoires(Joueur joueur1, Joueur joueur2) {
        System.out.println("Nombre de victoires :\n" + joueur1.getNom() + " : " + joueur1.getNbPartiesGagnees() + "\n" + joueur2.getNom() + " : " + joueur2.getNbPartiesGagnees());
        if (joueur1.getNbPartiesGagnees() == joueur2.getNbPartiesGagnees()) {
            System.out.println("Ex aequo \n ");
        } else if (joueur1.getNbPartiesGagnees() < joueur2.getNbPartiesGagnees()) {
            System.out.println("Le vainqueur est " + joueur2.getNom());
        } else {
            System.out.println("Le vainqueur est " + joueur1.getNom());
        }

    }

    public void afficherEgalite() {
        System.out.println("ex aequo");
    }




    public boolean demanderJouerContreIA() {
        while (true) {
            System.out.println("Voulez vous faire une partie :\n" +
                    "1 - A deux Joueurs\n"+
                    "2 - Contre une IA");
            if (input.hasNext()) {
                if (input.hasNextInt()){
                    int n = input.nextInt();
                    if (n>0 && n<3) {
                        input.nextLine();
                        return n == 2;
                    }
                }
                message("valeur invalide, veuillez entrer soit 1 soit 2");
                input.nextLine();
            }

        }
    }
    public void afficherCoupIA(String coup) {

        System.out.println("L'IA à jouer le coup : " + coup);
    }

    public int choisirMode() {
        while(true) {
            System.out.println("Choisir un mode de jeu:\n1 - Aléatoire\n2 - Minimax");
            if (this.input.hasNext()) {
                if (this.input.hasNextInt()) {
                    int n = this.input.nextInt();
                    if (n > 0 && n < 3) {
                        this.input.nextLine();
                        return n;
                    }
                }

                this.message("valeur invalide, veuillez entrer soit 1 soit 2");
                this.input.nextLine();
            }
        }
    }




    public int choixJeu() {
        while(true) {
            System.out.println("Choisir un jeu:\n1 - Othello\n2 - Awale");
            if (this.input.hasNext()) {
                if (this.input.hasNextInt()) {
                    int n = this.input.nextInt();
                    if (n > 0 && n < 3) {
                        this.input.nextLine();
                        return n;
                    }
                }

                this.message("valeur invalide, veuillez entrer soit 1 soit 2");
                this.input.nextLine();
            }
        }
    }
}

