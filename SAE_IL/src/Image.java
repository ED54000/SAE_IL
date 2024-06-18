import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;


public class Image {

    public void copy_image(String path,String name,String newName, String format ) throws IOException {
        File file = new File(path+name);
        BufferedImage img = ImageIO.read(file);
        ImageIO.write(img, format, new File(path+newName+"."+format));
    }

    public void copy_by_pixel(String path, String name, String newName, String format){
try {
            File file = new File(path+name);
            BufferedImage img = ImageIO.read(file);
            BufferedImage new_img = new BufferedImage(img.getWidth(), img.getHeight(), BufferedImage.TYPE_INT_ARGB);
            for (int y = 0; y < img.getHeight(); y++) {
                for (int x = 0; x < img.getWidth(); x++) {
                    int pixel = img.getRGB(x, y);
                    new_img.setRGB(x, y, pixel);
                }
            }
            ImageIO.write(new_img, format, new File(path+newName+"."+format));
        } catch (IOException e) {
            System.out.println(e);
        }

    }

    public void copy_black_and_white(String path, String name, String newName, String format) {
        try{
            File file = new File(path+name);
            BufferedImage img = ImageIO.read(file);
            BufferedImage new_img = new BufferedImage(img.getWidth(), img.getHeight(), BufferedImage.TYPE_3BYTE_BGR);
            for (int y = 0; y < img.getHeight(); y++) {
                for (int x = 0; x < img.getWidth(); x++) {
                    int pixel = img.getRGB(x, y);
                    int[] tab = OutilCouleur.getTabColor(pixel);
                    int moy = (tab[0] + tab[1] + tab[2]) / 3;
                    int new_pixel = moy| (moy << 8) | (moy << 16) ;
                    new_img.setRGB(x, y, new_pixel);
                }
            }
            ImageIO.write(new_img, format, new File(path+newName+"."+format));
        }catch (IOException e){
            System.out.println(e);
        }
    }

    public void copy_red(String path, String name, String newName, String format) {
        try{
            File file = new File(path+name);
            BufferedImage img = ImageIO.read(file);
            BufferedImage new_img = new BufferedImage(img.getWidth(), img.getHeight(), BufferedImage.TYPE_3BYTE_BGR);
            for (int y = 0; y < img.getHeight(); y++) {
                for (int x = 0; x < img.getWidth(); x++) {
                    int pixel = img.getRGB(x, y);
                    int[] tab = OutilCouleur.getTabColor(pixel);
                    int moy = (tab[0] + tab[1] + tab[2]) / 3;
                    int new_pixel = 0 | (moy << 16);
                    new_img.setRGB(x, y, new_pixel);
                }
            }
            ImageIO.write(new_img, format, new File(path+newName+"."+format));
        }catch (IOException e){
            System.out.println(e);
        }
    }
    public void copy_blue_green(String path, String name, String newName, String format) {
        try{
            File file = new File(path+name);
            BufferedImage img = ImageIO.read(file);
            BufferedImage new_img = new BufferedImage(img.getWidth(), img.getHeight(), BufferedImage.TYPE_3BYTE_BGR);
            for (int y = 0; y < img.getHeight(); y++) {
                for (int x = 0; x < img.getWidth(); x++) {
                    int pixel = img.getRGB(x, y);
                    int[] tab = OutilCouleur.getTabColor(pixel);
                    int moy = (tab[0] + tab[1] + tab[2]) / 3;
                    int new_pixel = moy| (moy << 8)  ;
                    new_img.setRGB(x, y, new_pixel);
                }
            }
            ImageIO.write(new_img, format, new File(path+newName+"."+format));
        }catch (IOException e){
            System.out.println(e);
        }
    }

    public void modif_color(String path, String name, String newName, String format)  {
        try{
            File file = new File(path+name);
            BufferedImage img = ImageIO.read(file);
            BufferedImage new_img = new BufferedImage(img.getWidth(), img.getHeight(), BufferedImage.TYPE_3BYTE_BGR);
            for (int y = 0; y < img.getHeight(); y++) {
                for (int x = 0; x < img.getWidth(); x++) {
                    int pixel = img.getRGB(x, y);
                    int[] tab = OutilCouleur.getTabColor(pixel);

                    Color c = new Color(tab[0],tab[1],tab[2]);
                    Palette p = new Palette(new NormeCIELAB());
                    Color color = p.getPlusProche(c);
                        new_img.setRGB(x, y, color.getRGB());
                    }
                }

            ImageIO.write(new_img, format, new File(path+newName+"."+format));
        }catch (IOException e){
            System.out.println(e);
        }
    }
    public void modif_oeil(String path, String name, String newName, String format)  {
        try{
            File file = new File(path+name);
            BufferedImage img = ImageIO.read(file);
            BufferedImage new_img = new BufferedImage(img.getWidth(), img.getHeight(), BufferedImage.TYPE_3BYTE_BGR);
            for (int y = 0; y < img.getHeight(); y++) {
                for (int x = 0; x < img.getWidth(); x++) {
                    int pixel = img.getRGB(x, y);
                    int[] tab = OutilCouleur.getTabColor(pixel);

                    Color c = new Color(tab[0],tab[1],tab[2]);
                    Palette p = new Palette(new NormeOeil());
                    Color color = p.getPlusProche(c);
                    new_img.setRGB(x, y, color.getRGB());
                }
            }

            ImageIO.write(new_img, format, new File(path+newName+"."+format));
        }catch (IOException e){
            System.out.println(e);
        }
    }
}
