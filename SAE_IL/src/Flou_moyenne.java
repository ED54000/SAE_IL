import java.awt.*;

public class Flou_moyenne implements Flou{
    public Color calcule(Color[][] couleurs){
        int sum = couleurs.length * couleurs[0].length;
        int r = 0;
        int g = 0;
        int b = 0;
        for(int i = 0; i < couleurs.length; i++){
            for(int j = 0; j < couleurs[0].length; j++){
                r += couleurs[i][j].getRed()/sum;
                g += couleurs[i][j].getGreen()/sum;
                b += couleurs[i][j].getBlue()/sum;
            }
        }
        return new Color(r,g,b);
    }

}
