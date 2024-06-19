import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

public class DBScan implements InterfaceClustering {
    private final int minPts;
    private final double eps;

    public DBScan(int minPts, double eps) {
        this.minPts = minPts;
        this.eps = eps;
    }


    public int[] algoClust(int[][] param){

        //initialisation
        int cId = 0;
        int num_pixel = param.length;
        int[] clusters = new int[num_pixel];
        boolean[] visite = new boolean[num_pixel];
        Arrays.fill(clusters, -1);

        for(int i = 0; i<num_pixel; i++){
            if(!visite[i]){
                visite[i] = true;
            }
            List<Integer> voisins = regionQuery(param,i);
            if(voisins.size()>=minPts){
                cId++;
                expandCluster(param,clusters,visite,i,voisins,cId);
            }else{
                clusters[i] = 0;
            }

        }

        return clusters;
    }

    private void expandCluster(int[][] param, int[] clusters, boolean[] visite, int index, List<Integer> voisins, int clusterId){
        clusters[index] = clusterId;

        // Utilisation d'un index pour itérer sur la liste voisins
        int idx = 0;
        while (idx < voisins.size()) {
            System.out.println(voisins.size());
            int i = voisins.get(idx);

            if (!visite[i]) {
                visite[i] = true;
                List<Integer> nouveauVoisins = regionQuery(param, i);
                if (nouveauVoisins.size() > minPts) {
                    // Ajouter les nouveaux voisins à la liste principale
                    voisins.addAll(nouveauVoisins);
                }
            }

            if (clusters[i] == -1) {
                clusters[i] = clusterId;
            }

            idx++; // Passer à l'élément suivant dans la liste voisins
        }
    }
    private List<Integer> regionQuery(int[][] param, int index){
        List<Integer> voisin = new ArrayList<>();
        int[] point = param[index];
        for(int i = 0; i<param.length; i++){
            if(i!=index && distance(point,param[i])<=eps){
                voisin.add(i);
            }
        }
        return voisin;
    }
    private double distance(int[] p1, int[] p2){
        double res = 0;
        for(int i = 0 ; i < p1.length; i++){
            res += (p1[i] - p2[i])*(p1[i] - p2[i]);
        }
        return Math.sqrt(res);
    }


}