import java.awt.*;
import java.io.IOException;

public class Main {
    public static void main(String[] args) {
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
        image.flouter_gaussienne("src/", "img.jpg", "image_flou_gaussien","png");
    }
}