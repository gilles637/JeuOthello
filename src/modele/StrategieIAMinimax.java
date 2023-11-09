package modele;


import java.util.Map;

public class StrategieIAMinimax implements StrategieIA{

    public StrategieIAMinimax(){

    }
    public CoupOthello getCoupIa(Damier damier){



        return meilleurCoup(damier,2, 3);
    }
    private CoupOthello meilleurCoup(Damier damier, int numJoueur, int profondeur) {
        Noeud noeud = new Noeud(damier.cloner());
        Map<Noeud, CoupOthello> listeEnfants = noeud.getListeEnfant(numJoueur);
        CoupOthello meilleurCoupOthello = null;



        int meilleurScore = Integer.MIN_VALUE;
        for (Noeud enfant : listeEnfants.keySet()) {
            int score = noeud.minimax(enfant, profondeur - 1, false, damier.getJoueurOppose(numJoueur));
            if (score > meilleurScore) {
                meilleurScore = score;
                meilleurCoupOthello = listeEnfants.get(enfant);
            }
        }
        return meilleurCoupOthello;
    }
}
