import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Main {
    public static void main(String[] args) throws IOException {
        Image image = new Image();
        String  path = "C:/Users/grand/OneDrive/Documents/cours/2eme_annee/S4/SAE/SAE_IL/SAE_IL/img/";
        // algo K-Mean
        K_Mean scan = new K_Mean(200);
        int[][] param = image.image_to_param("C:/Users/flori/Documents/Travail IUT/S4/SAE/SAE - Détection de biomes sur des exoplanètes/SAE_IL/SAE_IL/img/", "test2.jpg");
        int[] clusters = scan.algoClust(param);
        System.out.println(Arrays.toString(clusters));
        String[] biomes = image.convertCluster(param, clusters);

        // liste des biomes présents
        List<String> biomePresents = new ArrayList<>();
        for (String biome : biomes) {
            if (!biomePresents.contains(biome)) {
                biomePresents.add(biome);
            }
        }
        System.out.println("Liste des biomes présents : " + biomePresents);

        int[] dim = image.getDim("C:/Users/flori/Documents/Travail IUT/S4/SAE/SAE - Détection de biomes sur des exoplanètes/SAE_IL/SAE_IL/img/", "test2.jpg");
        image.imagebiome(param, biomes, clusters, "C:/Users/flori/Documents/Travail IUT/S4/SAE/SAE - Détection de biomes sur des exoplanètes/SAE_IL/SAE_IL/img/", "test2",  "png", dim[0], dim[1]);
        //image.afficherBiomes("img/", "Planete 2.jpg", "Fond","png");
        */
/*
        String[] images = {"Planete 1", "Planete 2", "Planete 3", "Planete 4", "Planete 5"};
        String[] normes = {"Redmean", "CIELAB", "Oeil"};
        int flou = 5;
        int[][] paramSCAN = {{40, 12}, {40, 12}, {40, 13}};
        String ext = "jpg";
        NormeCouleurs[] normes_couleurs = {new NormeRedmean(), new NormeCIELAB(), new NormeOeil()};
        for(String img : images){
            int[] dim = image.getDim(path, img + "." + ext);
            image.flouter(path, img + "." + ext, "Planete_Floue","png", flou, new Flou_gaussien());
            for(int i = 0; i < normes.length; i++){
                DBScan scan = new DBScan(paramSCAN[i][0],paramSCAN[i][1], normes_couleurs[i],dim[0]);
                int[][] param = image.image_to_param(path, "Planete_Floue.png");
                int[] clusters = scan.algoClust(param);
                String[] biomes = image.convertCluster(param, clusters);
                //image.imagebiome(param, biomes, clusters, path+"/dbscan", img+ "DBSCAN "+ normes[i], ".png", dim[0], dim[1]);
                image.biomeFondBlanc(param, biomes, clusters, path, img+".jpg",img+ "FondBiomes", "png", dim[0], dim[1]);
                System.out.println("Image " + img + " avec la norme " + normes[i] + " a été traitée");
            }
        }
        */
        int[] dim = image.getDim(path, "Planete 1.jpg");
        DBScan scan = new DBScan(40,12, new NormeCIELAB(),dim[0]);
        int[][] param = image.image_to_param(path, "Planete_Floue.png");
        int[] clusters = scan.algoClust(param);
        String[] biomes = image.convertCluster(param, clusters);
        //image.imagebiome(param, biomes, clusters, path+"/dbscan", img+ "DBSCAN "+ normes[i], ".png", dim[0], dim[1]);
        image.biomeFondBlanc(param, biomes, clusters, path, "Planete 1.jpg","Planete 1 FondBiomes", "png", dim[0], dim[1]);

        /*DBScan scan = new DBScan(3, 5);
        int[][] param = image.image_to_param("C:/Users/user/Desktop/Cours/SAE-Planet/SAE_IL/SAE_IL/img/", "Planete_Floue.png");
        int[] clusters = scan.algoClust(param);
        System.out.println(Arrays.toString(clusters));
        String[] biomes = image.convertCluster(param, clusters);
        int[] dim = image.getDim("C:/Users/user/Desktop/Cours/SAE-Planet/SAE_IL/SAE_IL/img/", "test2.jpg");
        image.imagebiome(param, biomes, clusters, "C:/Users/user/Desktop/Cours/SAE-Planet/SAE_IL/SAE_IL/img/", "test2",  "png", dim[0], dim[1]);
        //image.afficherBiomes("img/", "Planete 2.jpg", "Fond","png");*/
    }
}