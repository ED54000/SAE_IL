import java.util.*;

public class BiomeRGBMap {
    // Création de la map
    Map<String, int[]> biomeMap = new HashMap<>();
    public BiomeRGBMap() {

        // Ajout des biomes et leurs valeurs RGB
        biomeMap.put("Désert", new int[]{152, 140, 120});
        biomeMap.put("Forêt tropicale", new int[]{46, 64, 34});
//        biomeMap.put("Océan", new int[]{0, 105, 148});
        biomeMap.put("Tundra", new int[]{71, 70, 61});
        biomeMap.put("Savane", new int[]{84, 106, 70});
        biomeMap.put("Forêt tempérée", new int[]{59, 66, 43});
        biomeMap.put("Eau peu profonde", new int[]{49, 83, 100});
        biomeMap.put("Eau profonde", new int[]{12, 31, 47});
//        biomeMap.put("Montagnes", new int[]{169, 169, 169});
        biomeMap.put("Prairie", new int[]{104, 95, 82});
//        biomeMap.put("Zone urbaine", new int[]{169, 169, 169});
//        biomeMap.put("Marais", new int[]{85, 107, 47});
        biomeMap.put("Taïga", new int[]{43, 50, 35});
//        biomeMap.put("Plage", new int[]{238, 214, 175});
//        biomeMap.put("Volcan", new int[]{255, 69, 0});
//        biomeMap.put("Récif corallien", new int[]{255, 127, 80});
//        biomeMap.put("Désert de sel", new int[]{248, 248, 255});
//        biomeMap.put("Forêt de conifères", new int[]{34, 139, 34});
//        biomeMap.put("Mangrove", new int[]{139, 69, 19});
//        biomeMap.put("Steppe", new int[]{184, 134, 11});
//        biomeMap.put("Forêt de feuillus", new int[]{34, 139, 34});
//        biomeMap.put("Savane arborée", new int[]{154, 205, 50});
//        biomeMap.put("Rivière", new int[]{70, 130, 180});
//        biomeMap.put("Marécage", new int[]{85, 107, 47});
//        biomeMap.put("Désert rocailleux", new int[]{139, 69, 19});
//        biomeMap.put("Lagune", new int[]{32, 178, 170});
//        biomeMap.put("Canyon", new int[]{205, 92, 92});
//        biomeMap.put("Vallée", new int[]{34, 139, 34});
//        biomeMap.put("Forêt mixte", new int[]{107, 142, 35});
//        biomeMap.put("Plaines inondables", new int[]{176, 196, 222});
//        biomeMap.put("Steppe froide", new int[]{119, 136, 153});
        biomeMap.put("Glacier", new int[]{200, 200, 200});
//        biomeMap.put("Calotte glaciaire", new int[]{240, 248, 255});
//        biomeMap.put("Forêt boréale", new int[]{34, 139, 34});
//        biomeMap.put("Forêt méditerranéenne", new int[]{143, 188, 143});
//        biomeMap.put("Maquis", new int[]{107, 142, 35});
//        biomeMap.put("Savane herbeuse", new int[]{189, 183, 107});
//        biomeMap.put("Prairie humide", new int[]{124, 252, 0});
//        biomeMap.put("Fjord", new int[]{70, 130, 180});
//        biomeMap.put("Dune", new int[]{238, 214, 175});
//        biomeMap.put("Tourbière", new int[]{85, 107, 47});
//        biomeMap.put("Forêt pluviale tempérée", new int[]{34, 139, 34});
//        biomeMap.put("Vigne", new int[]{139, 69, 19});
//        biomeMap.put("Île tropicale", new int[]{0, 255, 127});
//        biomeMap.put("Archipel", new int[]{0, 105, 148});
//        biomeMap.put("Forêt de mangroves", new int[]{139, 69, 19});
//        biomeMap.put("Forêt subtropicale", new int[]{34, 139, 34});
//        biomeMap.put("Forêt primaire", new int[]{0, 100, 0});
//        biomeMap.put("Désert polaire", new int[]{248, 248, 255});
//        biomeMap.put("Forêt de nuages", new int[]{0, 100, 0});
//        biomeMap.put("Plateau", new int[]{160, 82, 45});
//        biomeMap.put("Delta", new int[]{32, 178, 170});
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
        return biome;
    }

    private double distance(int[] p1, int[] p2){
        double res = 0;
        for(int i = 0 ; i < p1.length; i++){
            res += (p1[i] - p2[i])*(p1[i] - p2[i]);
        }
        return Math.sqrt(res);
    }
}