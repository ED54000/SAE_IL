import java.awt.*;

public class Palette {

    static NormeCouleurs distanceCouleur;
    static Color black = new Color(0,0,0);
    static Color white = new Color(255,255,255);
    static Color red = new Color(255,0,0);
    static Color lime = new Color(0,255,0);
    static Color blue = new Color(0,0,255);
    static Color yellow = new Color(255,255,0);
    static Color cyan = new Color(0,255,255);
    static Color magenta = new Color(255,0,255);
    static Color silver = new Color(192,192,192);
    static Color gray = new Color(128,128,128);
    static Color marron = new Color(128,0,0);
    static Color olive = new Color(128,128,0);
    static Color green = new Color(0,128,0);
    static Color purple = new Color(128,0,128);
    static Color teal = new Color(0,128,128);
    static Color navy = new Color(0,0,128);
    static Color orange = new Color(255,165,0);
    static Color brown = new Color(165,42,42);
    static Color pink = new Color(255,192,203);
    static Color gold = new Color(255,215,0);
    static Color beige = new Color(245,245,220);
    static Color darkGreen = new Color(0,100,0);
    static Color lightGray = new Color(211,211,211);
    static Color darkGray = new Color(169,169,169);
    static Color skyBlue = new Color(135,206,235);
    static Color violet = new Color(238,130,238);
    static Color salmon = new Color(250,128,114);
    static Color khaki = new Color(240,230,140);
    static Color coral = new Color(255,127,80);
    static Color turquoise = new Color(64,224,208);
    static Color indigo = new Color(75,0,130);

    static Color[] palette = {
            black, white, red, lime, blue, yellow, cyan, magenta, silver, gray,
            marron, olive, green, purple, teal, navy, orange, brown, pink, gold,
            beige, darkGreen, lightGray, darkGray, skyBlue, violet, salmon,
            khaki, coral, turquoise, indigo
    };
    public Palette(NormeCouleurs distanceCouleur){
        Palette.distanceCouleur = distanceCouleur;
    }

    public Color getPlusProche(Color color){

        double min = Palette.distanceCouleur.distanceCouleur(color, palette[0]);
        Color plusProche = palette[0];
        for (Color c : palette){
            double dist = Palette.distanceCouleur.distanceCouleur(color, c);
            if (dist < min){
                min = dist;
                plusProche = c;
            }
        }
        return plusProche;
    }
}
