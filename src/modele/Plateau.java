package modele;

public interface Plateau {

    void initialiser();

    boolean partieTerminee();

    int getVainqueur();

    boolean estEgalite();

    int[] compterScore();

    boolean peutJouer(int numJoueur);

}
