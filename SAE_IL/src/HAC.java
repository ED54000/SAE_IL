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
            System.arraycopy(C1[i], 0, C[i], 0, C1[0].length);
        }
        for (int i = 0; i < C2.length; i++) {
            System.arraycopy(C2[i], 0, C[i + C1.length], 0, C2[0].length);
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

        // Initialiser la matrice de distances
        double[][] distances = new double[n][n];
        for (int i = 0; i < n; i++) {
            for (int j = i + 1; j < n; j++) {
                distances[i][j] = linkage(L.get(i), L.get(j));
                distances[j][i] = distances[i][j];
            }
        }

        /* Boucle principale */
        while (L.size() > 1) {
            double min = Double.MAX_VALUE;
            int indexC1 = 0;
            int indexC2 = 0;

            // Trouver la paire de clusters la plus proche
            for (int i = 0; i < L.size(); i++) {
                for (int j = i + 1; j < L.size(); j++) {
                    if (distances[i][j] < min) {
                        min = distances[i][j];
                        indexC1 = i;
                        indexC2 = j;
                    }
                }
            }

            // Fusionner les deux clusters les plus proches
            int[][] C1 = L.get(indexC1);
            int[][] C2 = L.get(indexC2);
            int[][] C = fusionner(C1, C2);

            // Mise à jour de la matrice de distances
            for (int i = 0; i < L.size(); i++) {
                if (i != indexC1 && i != indexC2) {
                    distances[i][indexC1] = linkage(L.get(i), C);
                    distances[indexC1][i] = distances[i][indexC1];
                }
            }

            // Enlever les distances des anciens clusters fusionnés
            for (int i = 0; i < n; i++) {
                distances[i][indexC2] = Double.MAX_VALUE;
                distances[indexC2][i] = Double.MAX_VALUE;
            }

            // Enlever les anciens clusters et ajouter le nouveau cluster fusionné
            L.remove(indexC2);
            L.set(indexC1, C);
        }

        // Retourner le résultat du clustering
        int[] result = new int[n];
        Arrays.fill(result, 0);
        return result;
    }
}
