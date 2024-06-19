import java.util.*;

public class BiomeRGBMap {
    // Création de la map
    Map<String, int[]> biomeMap = new HashMap<>();
    public BiomeRGBMap() {

        // Ajout des biomes et leurs valeurs RGB
        biomeMap.put("Ocean profond", new int[]{0, 0, 139});
        biomeMap.put("Ocean", new int[]{0, 0, 255});
        biomeMap.put("Eau peu profonde", new int[]{0, 191, 255});
        biomeMap.put("Plage", new int[]{238, 214, 175});
    }

    public String getBiome(int[] x){
        int[] meilleurRGB = {237, 201, 175};
        double meilleurDist = distance(meilleurRGB,x);
        double distanceP;
        String biome = "Désert";
        for (Map.Entry<String, int[]> entry : biomeMap.entrySet()) {
            int[] value = entry.getValue();
            String key = entry.getKey();
            distanceP = distance(x,value);
            if(distanceP < meilleurDist) {
                meilleurRGB[0] = value[0];
                meilleurRGB[1] = value[1];
                meilleurRGB[2] = value[2];
                meilleurDist = distanceP;
                biome = key;
            }
        }
        System.out.println(biome);
        return biome;
    }

    private double distance(int[] p1, int[] p2){
        double res = 0;
        for(int i = 0 ; i < p1.length; i++){
            res += (p1[i] - p2[i])*(p1[i] - p2[i]);
        }
        return Math.sqrt(res);
    }

    public int[] getRGB(String biome){
        return biomeMap.get(biome);
    }
}