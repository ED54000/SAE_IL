import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.List;


public class Image {

    public void copy_image(String path, String name, String newName, String format) throws IOException {
        File file = new File(path + name);
        BufferedImage img = ImageIO.read(file);
        ImageIO.write(img, format, new File(path + newName + "." + format));
    }

    public String[] convertCluster(int[][] param, int[] clusters) {
        BiomeRGBMap b = new BiomeRGBMap();
        if (param.length != clusters.length) {
            throw new IllegalArgumentException("Le nombre de pixels et le nombre de clusters doivent être identiques.");
        }

        // Initialiser une map pour stocker les clusters et leurs pixels associés
        Map<Integer, List<int[]>> clusterMap = new HashMap<>();

        // Parcourir chaque pixel et son assignation de cluster
        for (int i = 0; i < param.length; i++) {
            int[] pixel = param[i];
            int cluster = clusters[i];
            if (cluster != 0) {
                // Ajouter le pixel au cluster correspondant
                clusterMap.computeIfAbsent(cluster, k -> new ArrayList<>()).add(pixel);
            }
        }

        String[] biomes = new String[clusterMap.size() + 1];

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


            biomes[cluster] = b.getBiome(new int[]{avgR, avgG, avgB});
        }

        return biomes;
    }

    public void imagebiome(int[][] param, String[] biomes, int[] clusters, String path, String newName, String ext, int width, int height) {
        BiomeRGBMap b = new BiomeRGBMap();
        // reforme une nouvelle image en fonction des couleurs des biomes attribués
        BufferedImage img = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        for (int i = 0; i < param.length; i++) {
            int cluster = clusters[i];
            int color;
            int x = param[i][3];
            int y = param[i][4];
            if (cluster != 0) {
                int[] rgb = b.getRGB(biomes[cluster]);
                color = new Color(rgb[0], rgb[1], rgb[2]).getRGB();
            } else {
                color = new Color(0, 0, 0).getRGB();
            }
            img.setRGB(x, y, color);
        }
        try {
            ImageIO.write(img, ext, new File(path + newName + "." + ext));
        } catch (IOException e) {
            System.err.println(e);
        }
    }

    public void imagebiomeflou(int[][] param, String[] biomes, int[] clusters, String path, String newName, String ext, int width, int height, int flou) {
        BiomeRGBMap b = new BiomeRGBMap();
        // reforme une nouvelle image en fonction des couleurs des biomes attribués
        BufferedImage img = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        for (int i = 0; i < param.length; i++) {
            int cluster = clusters[i];
            int color;
            int x = param[i][3];
            int y = param[i][4];
            if (cluster != 0) {
                int[] rgb = b.getRGB(biomes[cluster]);
                color = new Color(rgb[0], rgb[1], rgb[2]).getRGB();
            } else {
                color = new Color(0, 0, 0).getRGB();
            }
            for (int k = 0; k < flou; k++) {
                for (int j = 0; j < flou; j++) {
                    img.setRGB(x + j, y + k, color);
                }
            }
        }
        try {
            ImageIO.write(img, ext, new File(path + newName + "." + ext));
        } catch (IOException e) {
            System.err.println(e);
        }
    }

    public int[][] image_to_param(String path, String name) throws IOException {
        File file = new File(path + name);
        BufferedImage img = ImageIO.read(file);

        int[][] param = new int[img.getHeight() * img.getWidth()][5];
        for (int y = 0; y < img.getHeight(); y++) {
            for (int x = 0; x < img.getWidth(); x++) {
                int pixel = img.getRGB(x, y);
                int[] tab = OutilCouleur.getTabColor(pixel);
                param[y * img.getHeight() + x][0] = tab[0];
                param[y * img.getHeight() + x][1] = tab[1];
                param[y * img.getHeight() + x][2] = tab[2];
                param[y * img.getHeight() + x][3] = x;
                param[y * img.getHeight() + x][4] = y;
            }
        }
        return param;
    }

    public int[][] image_to_param_flou(String path, String name, int flou) throws IOException {
        File file = new File(path + name);
        BufferedImage img = ImageIO.read(file);

        int[][] param = new int[img.getHeight() / flou * img.getWidth() / flou][5];
        for (int y = 0; y < img.getHeight(); y += flou) {
            for (int x = 0; x < img.getWidth(); x += flou) {
                int pixel = img.getRGB(x, y);
                int[] tab = OutilCouleur.getTabColor(pixel);
                param[y * img.getHeight() / flou + x][0] = tab[0];
                param[y * img.getHeight() / flou + x][1] = tab[1];
                param[y * img.getHeight() / flou + x][2] = tab[2];
                param[y * img.getHeight() / flou + x][3] = x;
                param[y * img.getHeight() / flou + x][4] = y;
            }
        }
        return param;
    }

    public void copy_by_pixel(String path, String name, String newName, String format) {
        try {
            File file = new File(path + name);
            BufferedImage img = ImageIO.read(file);
            BufferedImage new_img = new BufferedImage(img.getWidth(), img.getHeight(), BufferedImage.TYPE_INT_ARGB);
            for (int y = 0; y < img.getHeight(); y++) {
                for (int x = 0; x < img.getWidth(); x++) {
                    int pixel = img.getRGB(x, y);
                    new_img.setRGB(x, y, pixel);
                }
            }
            ImageIO.write(new_img, format, new File(path + newName + "." + format));
        } catch (IOException e) {
            System.out.println(e);
        }

    }

    public void copy_black_and_white(String path, String name, String newName, String format) {
        try {
            File file = new File(path + name);
            BufferedImage img = ImageIO.read(file);
            BufferedImage new_img = new BufferedImage(img.getWidth(), img.getHeight(), BufferedImage.TYPE_3BYTE_BGR);
            for (int y = 0; y < img.getHeight(); y++) {
                for (int x = 0; x < img.getWidth(); x++) {
                    int pixel = img.getRGB(x, y);
                    int[] tab = OutilCouleur.getTabColor(pixel);
                    int moy = (tab[0] + tab[1] + tab[2]) / 3;
                    int new_pixel = moy | (moy << 8) | (moy << 16);
                    new_img.setRGB(x, y, new_pixel);
                }
            }
            ImageIO.write(new_img, format, new File(path + newName + "." + format));
        } catch (IOException e) {
            System.out.println(e);
        }
    }

    public void copy_red(String path, String name, String newName, String format) {
        try {
            File file = new File(path + name);
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
            ImageIO.write(new_img, format, new File(path + newName + "." + format));
        } catch (IOException e) {
            System.out.println(e);
        }
    }

    public void copy_blue_green(String path, String name, String newName, String format) {
        try {
            File file = new File(path + name);
            BufferedImage img = ImageIO.read(file);
            BufferedImage new_img = new BufferedImage(img.getWidth(), img.getHeight(), BufferedImage.TYPE_3BYTE_BGR);
            for (int y = 0; y < img.getHeight(); y++) {
                for (int x = 0; x < img.getWidth(); x++) {
                    int pixel = img.getRGB(x, y);
                    int[] tab = OutilCouleur.getTabColor(pixel);
                    int moy = (tab[0] + tab[1] + tab[2]) / 3;
                    int new_pixel = moy | (moy << 8);
                    new_img.setRGB(x, y, new_pixel);
                }
            }
            ImageIO.write(new_img, format, new File(path + newName + "." + format));
        } catch (IOException e) {
            System.out.println(e);
        }
    }

    public void modif_color(String path, String name, String newName, String format) {
        try {
            File file = new File(path + name);
            BufferedImage img = ImageIO.read(file);
            BufferedImage new_img = new BufferedImage(img.getWidth(), img.getHeight(), BufferedImage.TYPE_3BYTE_BGR);
            for (int y = 0; y < img.getHeight(); y++) {
                for (int x = 0; x < img.getWidth(); x++) {
                    int pixel = img.getRGB(x, y);
                    int[] tab = OutilCouleur.getTabColor(pixel);

                    Color c = new Color(tab[0], tab[1], tab[2]);
                    Palette p = new Palette(new NormeCIELAB());
                    Color color = p.getPlusProche(c);
                    new_img.setRGB(x, y, color.getRGB());
                }
            }

            ImageIO.write(new_img, format, new File(path + newName + "." + format));
        } catch (IOException e) {
            System.out.println(e);
        }
    }

    public int[] getDim(String path, String name) throws IOException {
        File file = new File(path + name);
        BufferedImage img = ImageIO.read(file);
        int[] dim = new int[2];
        dim[0] = img.getWidth();
        dim[1] = img.getHeight();
        return dim;
    }

    public void modif_oeil(String path, String name, String newName, String format) {
        try {
            File file = new File(path + name);
            BufferedImage img = ImageIO.read(file);
            BufferedImage new_img = new BufferedImage(img.getWidth(), img.getHeight(), BufferedImage.TYPE_3BYTE_BGR);
            for (int y = 0; y < img.getHeight(); y++) {
                for (int x = 0; x < img.getWidth(); x++) {
                    int pixel = img.getRGB(x, y);
                    int[] tab = OutilCouleur.getTabColor(pixel);

                    Color c = new Color(tab[0], tab[1], tab[2]);
                    Palette p = new Palette(new NormeOeil());
                    Color color = p.getPlusProche(c);
                    new_img.setRGB(x, y, color.getRGB());
                }
            }

            ImageIO.write(new_img, format, new File(path + newName + "." + format));
        } catch (IOException e) {
            System.out.println(e);
        }
    }

    public void flouter_moyenne(String path, String name, String newName, String format) {
        Flou_moyenne f = new Flou_moyenne();
        int flou = 8;
        try {
            File file = new File(path + name);
            BufferedImage img = ImageIO.read(file);
            Color[][] tab = new Color[flou][flou];
            BufferedImage new_img = new BufferedImage(img.getWidth(), img.getHeight(), BufferedImage.TYPE_3BYTE_BGR);
            for (int y = 0; y < img.getHeight(); y += flou) {
                for (int x = 0; x < img.getWidth(); x += flou) {
                    for (int k = 0; k < Math.min(flou, img.getHeight() - y); k++) {
                        for (int i = 0; i < Math.min(flou, img.getWidth() - x); i++) {
                            int pixel = img.getRGB(x + i, y + k);
                            tab[k][i] = new Color(pixel);
                        }
                    }
                    Color color = f.calcule(tab);
                    for (int k = 0; k < Math.min(flou, img.getHeight() - y); k++) {
                        for (int i = 0; i < Math.min(flou, img.getWidth() - x); i++) {
                            new_img.setRGB(x + i, y + k, color.getRGB());
                        }
                    }
                }
            }

            ImageIO.write(new_img, format, new File(path + newName + "." + format));
        } catch (IOException e) {
            System.out.println(e);
        }
    }

    public void flouter_gaussienne(String path, String name, String newName, String format) {
        Flou_gaussien f = new Flou_gaussien();
        int flou = 8;
        try {
            File file = new File(path + name);
            BufferedImage img = ImageIO.read(file);
            Color[][] tab = new Color[flou][flou];
            BufferedImage new_img = new BufferedImage(img.getWidth(), img.getHeight(), BufferedImage.TYPE_3BYTE_BGR);
            for (int y = 0; y < img.getHeight(); y += flou) {
                for (int x = 0; x < img.getWidth(); x += flou) {
                    for (int k = 0; k < Math.min(flou, img.getHeight() - y); k++) {
                        for (int i = 0; i < Math.min(flou, img.getWidth() - x); i++) {
                            int pixel = img.getRGB(x + i, y + k);
                            tab[k][i] = new Color(pixel);
                        }
                    }
                    Color color = f.calcule(tab);
                    for (int k = 0; k < Math.min(flou, img.getHeight() - y); k++) {
                        for (int i = 0; i < Math.min(flou, img.getWidth() - x); i++) {
                            new_img.setRGB(x + i, y + k, color.getRGB());
                        }
                    }
                }
            }
            ImageIO.write(new_img, format, new File(path + newName + "." + format));
        } catch (IOException e) {
            System.out.println(e);
        }
    }

    public void afficherBiomes(String path, String name, String newName, String format) {
        try {
            File file = new File(path + name);
            BufferedImage img = ImageIO.read(file);
            BufferedImage new_img = new BufferedImage(img.getWidth(), img.getHeight(), BufferedImage.TYPE_INT_ARGB);
            for (int y = 0; y < img.getHeight(); y++) {
                for (int x = 0; x < img.getWidth(); x++) {
                    int pixel = img.getRGB(x, y);
                    int alpha = (pixel >> 24) & 0xff;
                    int red = (pixel >> 16) & 0xff;
                    int green = (pixel >> 8) & 0xff;
                    int blue = pixel & 0xff;
                    red = OutilCouleur.increaseValue(red, 75);
                    green = OutilCouleur.increaseValue(green, 75);
                    blue = OutilCouleur.increaseValue(blue, 75);
                    int newPixel = (alpha << 24) | (red << 16) | (green << 8) | blue;
                    new_img.setRGB(x, y, newPixel);
                }
            }
            ImageIO.write(new_img, format, new File(path + newName + "." + format));
        } catch (IOException e) {
            System.err.println(e);
        }
    }

}
