import java.awt.*;

public class NormeRedmean implements NormeCouleurs {
    @Override
    public double distanceCouleur(Color c1, Color c2) {
        double deltaR = c1.getRed() - c2.getRed();
        double deltaG = c1.getGreen() - c2.getGreen();
        double deltaB = c1.getBlue() - c2.getBlue();
        double r = (1/2)* (c1.getRed() + c2.getRed());
        return Math.sqrt((2+ r/256)*Math.pow(deltaR,2)+4*Math.pow(deltaG,2)+(2+(225-r/256))*Math.pow(deltaB,2));
    }

    @Override
    public double distanceCouleur(int[] c1, int[] c2) {
        return distanceCouleur(new Color(c1[0], c1[1], c1[2]), new Color(c2[0], c2[1], c2[2]));
    }
}
