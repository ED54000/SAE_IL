import java.awt.*;

public class Flou_gaussien implements Flou{

    @Override
    public Color calcule(Color[][] couleurs) {
        double sum = 0;
        double r = 0;
        double g = 0;
        double b = 0;
        int sigma = 10;
        for(int i = 0; i < couleurs.length; i++){
            for(int j = 0; j < couleurs[0].length; j++){
                sum += calculIndice(i, j, sigma);
            }
        }
        for(int i = 0; i < couleurs.length; i++){
            for(int j = 0; j < couleurs[0].length; j++){
                r += couleurs[i][j].getRed()*calculIndice(i, j, sigma)/sum;
                g += couleurs[i][j].getGreen()*calculIndice(i, j, sigma)/sum;
                b += couleurs[i][j].getBlue()*calculIndice(i, j, sigma)/sum;
            }
        }
        return new Color((int) r, (int) g, (int) b);
    }
    // fonction gaussienne G(x, y) = 1
    //2πσ2 e− x2+y2
    //2σ2
    private double calculIndice(int x, int y, int sigma){
        return 1/(2*Math.PI*sigma*sigma)*Math.exp(-x*x +(double) (y * y) /(2*sigma*sigma));
    }
}
