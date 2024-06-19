import java.util.ArrayList;
import java.util.Arrays;

public class HAC implements InterfaceClustering {

    private double moyenneCluster(int[][] C) {
        double sum = 0;
        for (int[] point : C) {
            for (int value : point) {
                sum += value;
            }
        }
        return sum / (C.length * C[0].length);
    }

    private int[][] fusionner(int[][] C1, int[][] C2) {
        int[][] C = new int[C1.length + C2.length][C1[0].length];
        for (int i = 0; i < C1.length; i++) {
            for (int j = 0; j < C1[0].length; j++) {
                C[i][j] = C1[i][j];
            }
        }
        for (int i = 0; i < C2.length; i++) {
            for (int j = 0; j < C2[0].length; j++) {
                C[i + C1.length][j] = C2[i][j];
            }
        }
        return C;
    }

    private double linkage(int[][] C1, int[][] C2) {
        return Math.abs(moyenneCluster(C1) - moyenneCluster(C2));
    }


    @Override
    public int[] algoClust(int[][] param) {
        int n = param.length;
        ArrayList<int[][]> L = new ArrayList<>();

        // Initialiser L avec n clusters, chacun contenant un point de donnée.
        for (int i = 0; i < n; i++) {
            int[][] singlePointCluster = {param[i]};
            L.add(singlePointCluster);
        }
        /* Boucle principale */
        while (L.size() > 1) {
            double min = Double.MAX_VALUE;
            int indexC1 = 0;
            int indexC2 = 0;
            // Trouver la paire de clusters la plus proche
            for (int i = 0; i < L.size(); i++) {
                for (int j = i + 1; j < L.size(); j++) {
                    double d = linkage(L.get(i), L.get(j));
                    if (d < min) {
                        min = d;
                        indexC1 = i;
                        indexC2 = j;
                    }
                }
            }
            /* On fusionne C1 et C2 */
            // Fusionner les deux clusters les plus proches
            int[][] C1 = L.get(indexC1);
            int[][] C2 = L.get(indexC2);
            int[][] C = fusionner(C1, C2);
            /* On enlève C1 et C2 de L */
            if (indexC1 > indexC2) {
                L.remove(indexC1);
                L.remove(indexC2);
            } else {
                L.remove(indexC2);
                L.remove(indexC1);
            }
            for (int i = 0; i < L.size(); i++) {
                if (i != indexC1 && i != indexC2) {
                    double d = linkage(C, L.get(i));
                    param[i][L.size() - 1] = (int) d;
                    param[L.size() - 1][i] = (int) d;
                }

                /*On enlève les distances concernant C1 et C2 de D*/
                for (int j = 0; j < param.length; j++) {
                    for (int k = 0; k < param[j].length; k++) {
                        if (param[j][k] == C1[j][k] || param[j][k] == C2[j][k]) {
                            param[j][k] = 0;
                        }
                    }
                }
                /*Ajouter C à L*/
                L.add(C);
            }

        }
        /* Retourner le dendrogramme à coupe*/
        int[] result = new int[n];
        Arrays.fill(result, 1);
        return result;
    }
}
