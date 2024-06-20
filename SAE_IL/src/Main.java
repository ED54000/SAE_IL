import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Main {
    public static void main(String[] args) throws IOException {
        Image image = new Image();
        //try {
        // image.copy_image("src/", "img.jpg", "image","png");
        //  } catch (IOException e) {
        //     System.out.println(e);
        //}
        //image.copy_by_pixel("src/", "img.jpg", "image_pixel","png");
        //image.copy_black_and_white("src/", "img.jpg", "image_bw","png");
        // image.copy_red("src/", "img.jpg", "image_red","png");
        //image.copy_blue_green("src/", "img.jpg", "image_blue_green","png");

        //image.modif_color("src/", "img.jpg", "image_modif3","png");
        //  image.modif_oeil("src/", "img.jpg", "image_modif4","png");
        //image.flouter_moyenne("src/", "img.jpg", "image_flou","png");
        //image.flouter_gaussienne("img/", "Planete 2.jpg", "Planete_Floue","png");
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
    }
}