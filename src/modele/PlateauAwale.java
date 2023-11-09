package modele;

public class PlateauAwale implements Plateau{

    private int[][] lesTrous = new int[2][6];
    private int grenierJ1;
    private int grenierJ2;

    public PlateauAwale(){

    }
    public void initialiser(){
        for(int i = 0; i < lesTrous.length; ++i) {
            for(int j = 0; j < lesTrous[0].length; ++j) {
                this.lesTrous[i][j] = 4;
            }
        }
    }

    public boolean partieTerminee() {
        if (!peutJouer(1) || !peutJouer(2)){
            captureToutesLesGraines();
            return true;
        }else {
            return false;
        }
    }

    public String toString() {
        String res ="GrenierJ2 : "+grenierJ2 + "\n   1     2     3     4     5     6     \n" +"|  ";

        for(int i = 0; i < lesTrous.length; ++i) {
            for(int j = 0; j < lesTrous[0].length; ++j) {
                res = res + this.lesTrous[i][j] + "  |  ";
            }
            res =  res + "\n" + "|  " ;
        }
        res=res.substring(0, res.length() - 3);
        res = res + "GrenierJ1 : " + grenierJ1;
        return res;
    }

    public void gererCoup(CoupAwale coup) throws CoupInvalideException{

        int colonne = coup.getColonne();
        int numJoueur = coup.getNumJoueur();
        int position = getPositionJoueur(numJoueur);

        if (estCoupValide(position, colonne)){
            placerGraines(position, colonne, this.lesTrous);
        } else {
            throw new CoupInvalideException("Le coup est invalide, rejouez");
        }
    }
    private int getPositionOppose(int position){
        if (position==1){
            return 0;
        }else {
            return 1;
        }
    }
    private boolean estAffamer(int position, int[][] lesTrous){
        int positionOppose = getPositionOppose(position);
        boolean estAffamer=true;
        for (int j = 0; j < lesTrous[0].length; j++){
            if (lesTrous[positionOppose][j] != 0) {
                estAffamer = false;
                break;
            }
        }
        return estAffamer;
    }

    private int getPositionJoueur(int numJoueur){
        if (numJoueur==1){
            return 1;
        } else if (numJoueur==2) {
            return 0;
        }
        return -1;
    }
    private void placerGraines(int position, int colonne, int[][] lesTrous) {
        int nbGraines = lesTrous[position][colonne];
        lesTrous[position][colonne] = 0;
        int i = position;
        int j = position == 1 ? colonne + 1 : colonne -1;
        int direction = position == 1 ? 1:-1; // 1 = de gauche à droite, -1 = de droite à gauche

        while (nbGraines > 0) {
            if (i == position && j == colonne) {
                j += direction;
            } else if (i==0 && j==-1) {
                i=1;
                j=0;
                direction=1;
            } else if (i==1 && j==6) {
                i=0;
                j=5;
                direction=-1;
            } else {
                lesTrous[i][j]++;
                nbGraines--;
                if (nbGraines!=0){
                    if (i == 0 && j == 0) {
                        i = 1;
                        direction = 1;
                    } else if (i == 1 && j == 5) {
                        i = 0;
                        direction = -1;
                    }else {
                        j += direction;
                    }
                }
            }
        }

        int grenier = captureGraines(i,j,position, lesTrous);

        if (lesTrous==this.lesTrous){
            if (position == 1) {
                this.grenierJ1 += grenier;
            }
            else{
                this.grenierJ2 += grenier;
            }
        }
    }

    private int captureGraines(int i, int j, int position, int[][] lesTrous){
        int grenier=0;
        while (lesTrous[i][j]==2 || lesTrous[i][j]==3){
            if (position!=i){
                grenier+= lesTrous[i][j];
                lesTrous[i][j]=0;
                if (i==0 && j==5){
                    i=1;
                } else if (i==1 && j==0) {
                    i=0;
                } else if (i==0){
                    j++;
                } else if (i==1) {
                    j--;
                }
            }else {
                break;
            }
        }
        return grenier;
    }


    private boolean estCoupValide(int position, int colonne){
        if (lesTrous[position][colonne]==0){
            return false;
        }else {
            int[][] lesTrousTest = new int[2][6];
            for(int x = 0; x < lesTrous.length; x++) {
                for(int y = 0; y < lesTrous[0].length; y++) {
                    lesTrousTest[x][y] = lesTrous[x][y];
                }
            }

            placerGraines(position, colonne, lesTrousTest);
            return !estAffamer(position, lesTrousTest);
        }
    }


    private void captureToutesLesGraines(){
        for(int i = 0; i < lesTrous.length; ++i) {
            for(int j = 0; j < lesTrous[0].length; ++j) {
                if (lesTrous[i][j]!=0){
                    if (i==1){
                        this.grenierJ1+=lesTrous[i][j];
                    }else {
                        this.grenierJ2+=lesTrous[i][j];
                    }
                    lesTrous[i][j]=0;
                }
            }
        }
    }

    public boolean peutJouer(int numJoueur){
        int position = getPositionJoueur(numJoueur);
        for (int j=0;j<lesTrous[0].length;j++){
            if (estCoupValide(position,j)){
                return true;
            }
        }
        return false;
    }
    public int getVainqueur(){
        if (grenierJ1>grenierJ2){
            return 1;
        }else {
            return 2;
        }
    }
    public boolean estEgalite(){
        return grenierJ1 == grenierJ2;
    }

    public int[] compterScore(){

        return new int[]{grenierJ1,grenierJ2};
    }
}
