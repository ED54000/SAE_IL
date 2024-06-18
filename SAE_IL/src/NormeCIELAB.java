import java.awt.*;

public class NormeCIELAB implements NormeCouleurs {
    @Override
    public double distanceCouleur(Color c1, Color c2) {
        int[] lab1 = OutilCouleur.rgb2lab(c1.getRed(), c1.getGreen(), c1.getBlue());
        int[] lab2 = OutilCouleur.rgb2lab(c2.getRed(), c2.getGreen(), c2.getBlue());
        return Math.sqrt(Math.pow(lab1[0] - lab2[0], 2) + Math.pow(lab1[1] - lab2[1], 2) + Math.pow(lab1[2] - lab2[2], 2));
    }

}
