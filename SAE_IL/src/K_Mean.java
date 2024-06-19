import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class K_Mean implements InterfaceClustering{

    private int nbGroupes;
    private int[][] centroides;

    public K_Mean(int nbGroupes){
        this.nbGroupes = nbGroupes;
        this.centroides = new int[nbGroupes][];
    }

    private double distance(int[] point1, int[] point2) {
        double sum = 0;
        for (int i = 0; i < point1.length; i++) {
            sum += Math.pow(point1[i] - point2[i], 2);
        }
        return Math.sqrt(sum);
    }

    private int closestCentroidIndex(int[] point) {
        double minDistance = Double.MAX_VALUE;
        int closestIndex = -1;
        for (int i = 0; i < centroides.length; i++) {
            double dist = distance(point, centroides[i]);
            if (dist < minDistance) {
                minDistance = dist;
                closestIndex = i;
            }
        }
        return closestIndex;
    }

    private int[] computeCentroid(int[][] data, int[] assignments, int clusterIndex) {
        int[] centroide = new int[data[0].length];
        int count = 0;

        for (int i = 0; i < data.length; i++) {
            if (assignments[i] == clusterIndex) {
                for (int j = 0; j < data[i].length; j++) {
                    centroide[j] += data[i][j];
                }
                count++;
            }
        }

        if (count > 0) {
            for (int j = 0; j < centroide.length; j++) {
                centroide[j] /= count;
            }
        }
        return centroide;
    }

    private void updateCentroids(int[][] data, int[] assignments) {
        for (int i = 0; i < nbGroupes; i++) {
            centroides[i] = computeCentroid(data, assignments, i);
        }
    }

    public int[] algoClust(int[][] param){

        // on initialise les centroides
        Random random = new Random();
        for (int i = 0; i < nbGroupes; i++) {
            centroides[i] = param[random.nextInt(param.length)];
        }

        boolean changed = true;
        int[] results = new int[param.length];
        Arrays.fill(results, -1);

        // boucle principale
        while (changed) {
            changed = false;

            // on assigne le centroide le plus proche a chaque point
            for (int i = 0; i < param.length; i++) {
                int closestIndex = closestCentroidIndex(param[i]);
                if (results[i] != closestIndex) {
                    results[i] = closestIndex;
                    changed = true;
                }
            }

            // on conserve la position des centroides
            int[][] oldCentroids = new int[centroides.length][];
            for (int i = 0; i < centroides.length; i++) {
                oldCentroids[i] = centroides[i].clone();
            }

            // on modifie la position des centroides
            updateCentroids(param, results);

            // on verifie que les centroides ont bouge
            for (int i = 0; i < nbGroupes; i++) {
                if (!Arrays.equals(oldCentroids[i], centroides[i])) {
                    changed = true;
                    break;
                }
            }
        }

        return results;
    }
}


