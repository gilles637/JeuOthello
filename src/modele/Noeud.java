package modele;
import java.util.HashMap;
import java.util.Map;

public class Noeud {

    private Damier damier;

    public Noeud(Damier damier){
        this.damier = damier;
    }

    public Map<Noeud, CoupOthello> getListeEnfant(int numJoueur){
        Map<Noeud, CoupOthello> listeEnfant = new HashMap<>();
        for (CoupOthello coupOthello : damier.getListeCoups(numJoueur)){
            Damier nouveauDamier = damier.cloner();

            try {
                nouveauDamier.gererCoup(coupOthello);

            } catch (CoupInvalideException e) {
                throw new RuntimeException(e);
            }

            Noeud enfant = new Noeud(nouveauDamier);
            listeEnfant.put(enfant, coupOthello);
        }
        return listeEnfant;
    }

    public int minimax(Noeud noeud, int profondeur, boolean estMaximise, int numJoueur) {
        if (profondeur == 0 || noeud.damier.partieTerminee()){
            return evaluation(noeud, numJoueur);
        }

        if (estMaximise){
            int maxEval = Integer.MIN_VALUE;
            for (Noeud enfant : noeud.getListeEnfant(numJoueur).keySet()){
                int eval = minimax(enfant, profondeur - 1, false, damier.getJoueurOppose(numJoueur));
                maxEval = Math.max(maxEval, eval);
            }
            return maxEval;
        }
        else {
            int minEval = Integer.MAX_VALUE;
            for (Noeud enfant : noeud.getListeEnfant(numJoueur).keySet()){
                int eval = minimax(enfant, profondeur - 1, true, numJoueur);
                minEval = Math.min(minEval, eval);
            }
            return minEval;
        }

    }

    private int evaluation(Noeud noeud, int numJoueur) {
        if (noeud.damier.partieTerminee() && noeud.damier.getVainqueur()==2) {
            return 1000;
        } else if (noeud.damier.partieTerminee()) {
            return -1000;
        }

        int totalePoints = 0;

        for (int i = 0; i < damier.getLesJetons().length; i++) {
            for (int j = 0; j < damier.getLesJetons().length; j++) {
                if (noeud.damier.getLesJetons()[i][j].equals(damier.getCouleur(numJoueur))){
                    int points=0;
                    if ((i==0 || i==damier.getLesJetons().length-1) && (j==0 || j==damier.getLesJetons().length-1)){
                        points = 11;
                    } else if (i==0 || i==damier.getLesJetons().length-1 || j==0 || j==damier.getLesJetons().length-1) {
                        points = 6;
                    } else {
                        points = 1;
                    }
                    totalePoints += points;
                }
            }
        }
        return totalePoints;
    }



}
