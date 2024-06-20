import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class DBScan implements InterfaceClustering {
    private final int minPts;
    private final double eps;

    private int area = 3;
    private final int width ;

    private final NormeCouleurs norme;

    public DBScan(int minPts, double eps, NormeCouleurs norme, int width) {
        this.minPts = minPts;
        this.eps = eps;
        this.norme = norme;
        this.width = width;
    }

    @Override
    public int[] algoClust(int[][] param) {
        int cId = 0;
        int num_pixel = param.length;
        int[] clusters = new int[num_pixel];
        boolean[] visite = new boolean[num_pixel];
        Arrays.fill(clusters, -1);

        for (int i = 0; i < num_pixel; i++) {
            if (!visite[i]) {
                visite[i] = true;
                List<Integer> voisins = regionQuery(param, i, clusters);
                if (voisins.size() >= minPts) {
                    cId++;
                    expandCluster(param, clusters, visite, i, voisins, cId);
                    System.out.println("Cluster " + cId + " : " + voisins.size() + " points");
                } else {
                    clusters[i] = 0;  // mark as noise
                }
            }
        }

        return clusters;
    }

    private void expandCluster(int[][] param, int[] clusters, boolean[] visite, int index, List<Integer> voisins, int clusterId) {
        clusters[index] = clusterId;

        boolean[] estVoisin = new boolean[param.length];
        for (int voisin : voisins) {
            estVoisin[voisin] = true;
        }

        int idx = 0;
        while (idx < voisins.size()) {
            int i = voisins.get(idx);

            if (!visite[i]) {
                visite[i] = true;
                List<Integer> nouveauVoisins = regionQuery(param, i, clusters);
                if (nouveauVoisins.size() >= minPts) {
                    for (Integer nouveauVoisin : nouveauVoisins) {
                        if (!estVoisin[nouveauVoisin]) {
                            voisins.add(nouveauVoisin);
                            estVoisin[nouveauVoisin] = true;
                        }
                    }
                }
            }

            if (clusters[i] == -1 || clusters[i] == 0) {
                clusters[i] = clusterId;
            }

            idx++;
        }
    }

    private List<Integer> regionQuery(int[][] param, int index, int[] clusters) {
        List<Integer> voisins = new ArrayList<>();
        int[] point = param[index];
        int pointX = point[3];
        int pointY = point[4];

        // Regarde seulement les voisins a moins de eps pixels de distance
        for (int x = Math.max(0, pointX - area); x <= Math.min(width - 1, pointX + area); x++) {
            for (int y = Math.max(0, pointY - area); y <= Math.min(param.length / width - 1, pointY + area); y++) {
                int voisinIndex = x + y * width;
                if (voisinIndex != index && clusters[voisinIndex] == -1 || clusters[voisinIndex] == 0) {
                    int[] voisinPoint = param[voisinIndex];
                    int distance = (int) (norme.distanceCouleur(voisinPoint, point)*1.5 + distanceCood(voisinPoint, point));
                    if (distance <= eps) {
                        voisins.add(voisinIndex);
                    }
                }
            }
        }
        return voisins;
    }

    private double distanceCood(int[] p1, int[] p2){
        return Math.sqrt(Math.pow(p1[3]-p2[3],2)+Math.pow(p1[4]-p2[4],2));
    }

}