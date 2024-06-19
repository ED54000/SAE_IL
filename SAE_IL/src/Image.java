import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.List;


public class Image {

    public void copy_image(String path,String name,String newName, String format ) throws IOException {
        File file = new File(path+name);
        BufferedImage img = ImageIO.read(file);
        ImageIO.write(img, format, new File(path+newName+"."+format));
    }

    public String[] convertCluster(int[][] param, int[] clusters){
        BiomeRGBMap b= new BiomeRGBMap();
        if (param.length != clusters.length) {
            throw new IllegalArgumentException("Le nombre de pixels et le nombre de clusters doivent être identiques.");
        }

        // Initialiser une map pour stocker les clusters et leurs pixels associés
        Map<Integer, List<int[]>> clusterMap = new HashMap<>();

        // Parcourir chaque pixel et son assignation de cluster
        for (int i = 0; i < param.length; i++) {
            int[] pixel = param[i];
            int cluster = clusters[i];
            if(cluster != 0){
                // Ajouter le pixel au cluster correspondant
                clusterMap.computeIfAbsent(cluster, k -> new ArrayList<>()).add(pixel);
            }
        }

        int[][] moy = new int[clusterMap.size()+1][3];
        String[] biomes = new String[clusterMap.size()+1];

        biomes[0] = "bruit";

        for (Map.Entry<Integer, List<int[]>> entry : clusterMap.entrySet()) {
            int cluster = entry.getKey();
            List<int[]> pixelsInCluster = entry.getValue();

            int sumR = 0, sumG = 0, sumB = 0;

            for (int[] pixel : pixelsInCluster) {
                sumR += pixel[0];
                sumG += pixel[1];
                sumB += pixel[2];
            }

            int count = pixelsInCluster.size();
            int avgR = sumR / count;
            int avgG = sumG / count;
            int avgB = sumB / count;

            biomes[cluster] =  b.getBiome(new int[]{avgR, avgG, avgB});
        }

        return biomes;
    }

    public int[][] image_to_param(String path, String name){
        File file = new File(path+name);
        BufferedImage img = null;
        try {
            img = ImageIO.read(file);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        int[][] param = new int[img.getHeight()*img.getWidth()][5];
        for (int y = 0; y < img.getHeight(); y++) {
            for (int x = 0; x < img.getWidth(); x++) {
                int pixel = img.getRGB(x, y);
                int[] tab = OutilCouleur.getTabColor(pixel);
                param[y*img.getHeight() + x][0] = tab[0];
                param[y*img.getHeight() + x][1] = tab[1];
                param[y*img.getHeight() + x][2] = tab[2];
                param[y*img.getHeight() + x][3] = x;
                param[y*img.getHeight() + x][4] = y;
            }
        }
        return param;
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

    public void flouter_moyenne(String path, String name, String newName, String format){
        Flou_moyenne f = new Flou_moyenne();
        int flou = 8;
        try{
            File file = new File(path+name);
            BufferedImage img = ImageIO.read(file);
            Color[][] tab = new Color[flou][flou];
            BufferedImage new_img = new BufferedImage(img.getWidth(), img.getHeight(), BufferedImage.TYPE_3BYTE_BGR);
            for (int y = 0; y < img.getHeight(); y+=flou) {
                for (int x = 0; x < img.getWidth(); x+=flou) {
                    for(int k = 0; k < Math.min(flou,img.getHeight()-y); k++){
                        for(int i = 0; i < Math.min(flou,img.getWidth()-x); i++){
                            int pixel = img.getRGB(x+i, y+k);
                            tab[k][i] = new Color(pixel);
                        }
                    }
                    Color color = f.calcule(tab);
                    for(int k = 0; k < Math.min(flou,img.getHeight()-y); k++){
                        for(int i = 0; i <  Math.min(flou,img.getWidth()-x); i++){
                            new_img.setRGB(x+i, y+k, color.getRGB());
                        }
                    }
                }
            }

            ImageIO.write(new_img, format, new File(path+newName+"."+format));
        }catch (IOException e){
            System.out.println(e);
        }
    }

    public void flouter_gaussienne(String path, String name, String newName, String format){
        Flou_gaussien f = new Flou_gaussien();
        int flou = 8;
        try{
            File file = new File(path+name);
            BufferedImage img = ImageIO.read(file);
            Color[][] tab = new Color[flou][flou];
            BufferedImage new_img = new BufferedImage(img.getWidth(), img.getHeight(), BufferedImage.TYPE_3BYTE_BGR);
            for (int y = 0; y < img.getHeight(); y+=flou) {
                for (int x = 0; x < img.getWidth(); x+=flou) {
                    for(int k = 0; k < Math.min(flou,img.getHeight()-y); k++){
                        for(int i = 0; i < Math.min(flou,img.getWidth()-x); i++){
                            int pixel = img.getRGB(x+i, y+k);
                            tab[k][i] = new Color(pixel);
                        }
                    }
                    Color color = f.calcule(tab);
                    for(int k = 0; k < Math.min(flou,img.getHeight()-y); k++){
                        for(int i = 0; i <  Math.min(flou,img.getWidth()-x); i++){
                            new_img.setRGB(x+i, y+k, color.getRGB());
                        }
                    }
                }
            }
            ImageIO.write(new_img, format, new File(path+newName+"."+format));
        }catch (IOException e){
            System.out.println(e);
        }
    }

    public void afficherBiomes(String path, String name, String newName, String format){
        try{
            File file = new File(path+name);
            BufferedImage img = ImageIO.read(file);
            BufferedImage new_img = new BufferedImage(img.getWidth(), img.getHeight(), BufferedImage.TYPE_INT_ARGB);
            for (int y = 0; y < img.getHeight(); y++) {
                for (int x = 0; x < img.getWidth(); x++) {
                    int pixel = img.getRGB(x, y);
                    double nouveau = Math.round(pixel + 75.0/100.0 * (255 - pixel));
                    new_img.setRGB(x, y, (int)nouveau);
                }
            }
            ImageIO.write(new_img, format, new File(path+newName+"."+format));
        } catch (IOException e) {
            System.err.println(e);
        }
    }

}
