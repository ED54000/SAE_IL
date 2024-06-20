import java.util.*;

public class BiomeRGBMap {
    // Création de la map
    Map<String, int[]> biomeMap = new HashMap<>();
    public BiomeRGBMap() {

        // Ajout des biomes et leurs valeurs RGB
        biomeMap.put("Désert", new int[]{152, 140, 120});
        biomeMap.put("Forêt tropicale", new int[]{46, 64, 34});
        biomeMap.put("Tundra", new int[]{71, 70, 61});
        biomeMap.put("Savane", new int[]{84, 106, 70});
        biomeMap.put("Forêt tempérée", new int[]{59, 66, 43});
        biomeMap.put("Eau peu profonde", new int[]{49, 83, 100});
        biomeMap.put("Eau profonde", new int[]{12, 31, 47});
        biomeMap.put("Prairie", new int[]{104, 95, 82});
        biomeMap.put("Taïga", new int[]{43, 50, 35});
        biomeMap.put("Glacier", new int[]{200, 200, 200});
    }


    public String getBiome(int[] x){
        int[] meilleurRGB = {152, 140, 120};
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