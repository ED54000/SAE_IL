import java.awt.*;

public class Flou_moyenne implements Flou{
    public Color calcule(Color[][] couleurs){
        int sum = couleurs.length * couleurs[0].length;
        int r = 0;
        int g = 0;
        int b = 0;
        for (Color[] list : couleurs) {
            for (Color c : list) {
                r += c.getRed();
                g += c.getGreen();
                b += c.getBlue();
            }
        }
        return new Color((int) r/sum,(int) g/sum,(int) b/sum);
    }

}
