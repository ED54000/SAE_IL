import java.awt.*;
import java.io.IOException;
import java.util.Arrays;

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
        String[] images = {"Planete 1", "Planete 2", "Planete 3", "Planete 4", "Planete 5"};
        String[] normes = {"Redmean", "CIELAB", "Oeil"};
        int flou = 2;
        int[][] paramSCAN = {{10, 75}, {10, 10}, {10, 10}};
        String ext = "jpg";
        NormeCouleurs[] normes_couleurs = {new NormeRedmean(), new NormeCIELAB(), new NormeOeil()};
        for(int i = 0; i < normes.length; i++){
            for(String img : images){
                int[] dim = image.getDim("C:/Users/user/Desktop/Cours/SAE-Planet/SAE_IL/SAE_IL/img/", img + "." + ext);
                image.flouter_moyenne("C:/Users/user/Desktop/Cours/SAE-Planet/SAE_IL/SAE_IL/img/", img + "." + ext, "Planete_Floue","png", flou);
                DBScan scan = new DBScan(paramSCAN[i][0],paramSCAN[i][1], normes_couleurs[i],(dim[0]+ flou - 1) / flou);
                int[][] param = image.image_to_param_flou("C:/Users/user/Desktop/Cours/SAE-Planet/SAE_IL/SAE_IL/img/", "Planete_Floue.png", flou);
                int[] clusters = scan.algoClust(param);
                String[] biomes = image.convertCluster(param, clusters);
                image.imagebiomeflou(param, biomes, clusters, "C:/Users/user/Desktop/Cours/SAE-Planet/SAE_IL/SAE_IL/img/dbscan/"+normes[i]+"/", img+" DBSCAN " + normes[i], "png", dim[0], dim[1], flou);
            }
        }
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