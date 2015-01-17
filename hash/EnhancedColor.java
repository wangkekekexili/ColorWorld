import java.awt.Color;

public class EnhancedColor extends Color
{
    private static final int whiteRgbMin = 200;
    private static final int blackRgbMax = 55;
    private static final int indiviualColorDiffMin = 50;
    
    public EnhancedColor(int rgb)
    {
        super(rgb);     
    }
    
    public EnhancedColor(int r, int g, int b, int a)
    {
        super(r, g, b, a);
    }

    public EnhancedColor(int r, int g, int b)
    {
        super(r, g, b);
    }
    
    public boolean isBlackOrWhite()
    {
        int color1 = getRGB();
        int r1 = (color1 >> 16) & 0xFF;
        int g1 = (color1 >> 8) & 0xFF;
        int b1 = (color1 >> 0) & 0xFF;
        
        return (r1 >= whiteRgbMin && g1 >= whiteRgbMin && b1 >= whiteRgbMin) || (r1 <= blackRgbMax && g1 <= blackRgbMax && b1 <= blackRgbMax);
    }
    /* 
    public float[] getHSB()
    {
        float[] hsv = new float[3];
        Color.RGBtoHSB(r,g,b,hsv);
        return float[];
    }
    */

    public boolean isBlack()
    {
        int color1 = getRGB();
        int r = (color1 >> 16) & 0xFF;
        int g = (color1 >> 8) & 0xFF;
        int b = (color1 >> 0) & 0xFF;
        float[] hsv = new float[3];
        Color.RGBtoHSB(r,g,b,hsv);
        return (hsv[2] < 0.010);
    }

    public boolean isWhite()
    {
        int color1 = getRGB();
        int r = (color1 >> 16) & 0xFF;
        int g = (color1 >> 8) & 0xFF;
        int b = (color1 >> 0) & 0xFF;
        float[] hsv = new float[3];
        Color.RGBtoHSB(r,g,b,hsv);
        return (hsv[1] < 0.09 && hsv[2] > 0.850);
    }

    public boolean isGrey()
    {
        int color1 = getRGB();
        int r = (color1 >> 16) & 0xFF;
        int g = (color1 >> 8) & 0xFF;
        int b = (color1 >> 0) & 0xFF;
        float[] hsv = new float[3];
        Color.RGBtoHSB(r,g,b,hsv);
        return (hsv[1] < 0.25 && hsv[1] > 0.09 && hsv[2] > 0.700);
    }

    public boolean isRed()
    {
        int color1 = getRGB();
        int r = (color1 >> 16) & 0xFF;
        int g = (color1 >> 8) & 0xFF;
        int b = (color1 >> 0) & 0xFF;
        float[] hsv = new float[3];
        Color.RGBtoHSB(r,g,b,hsv);
        return ( (hsv[0] >= 0.960 || hsv[0] < 0.040) && hsv[1] > 0.3 && hsv[2] > 0.5 );
    }//1

    /*
    public boolean isPink()
    {
        int color1 = getRGB();
        int r = (color1 >> 16) & 0xFF;
        int g = (color1 >> 8) & 0xFF;
        int b = (color1 >> 0) & 0xFF;
        float[] hsv = new float[3];
        Color.RGBtoHSB(r,g,b,hsv);
        return (hsv[0] >= 0.060 && hsv[0] < 0.167);
    }//2
    */

    /*
    public boolean isMagenta()
    {
        int color1 = getRGB();
        int r = (color1 >> 16) & 0xFF;
        int g = (color1 >> 8) & 0xFF;
        int b = (color1 >> 0) & 0xFF;
        float[] hsv = new float[3];
        Color.RGBtoHSB(r,g,b,hsv);
        return (hsv[0] >= 0.167 && hsv[0] < 0.20);
    }//3
    */

    public boolean isPurple()
    {
        int color1 = getRGB();
        int r = (color1 >> 16) & 0xFF;
        int g = (color1 >> 8) & 0xFF;
        int b = (color1 >> 0) & 0xFF;
        float[] hsv = new float[3];
        Color.RGBtoHSB(r,g,b,hsv);
        return (hsv[0] >= 0.75 && hsv[0] < 0.9);
    }//4

    public boolean isBlue()
    {
        int color1 = getRGB();
        int r = (color1 >> 16) & 0xFF;
        int g = (color1 >> 8) & 0xFF;
        int b = (color1 >> 0) & 0xFF;
        float[] hsv = new float[3];
        Color.RGBtoHSB(r,g,b,hsv);
        return (hsv[0] >= 0.62 && hsv[0] < 0.72);
    }//5

    
    public boolean isAqua()
    {
        int color1 = getRGB();
        int r = (color1 >> 16) & 0xFF;
        int g = (color1 >> 8) & 0xFF;
        int b = (color1 >> 0) & 0xFF;
        float[] hsv = new float[3];
        Color.RGBtoHSB(r,g,b,hsv);
        return (hsv[0] >= 0.46 && hsv[0] < 0.540);
    }//6
    

    /*
    public boolean isLime()
    {
        int color1 = getRGB();
        int r = (color1 >> 16) & 0xFF;
        int g = (color1 >> 8) & 0xFF;
        int b = (color1 >> 0) & 0xFF;
        float[] hsv = new float[3];
        Color.RGBtoHSB(r,g,b,hsv);
        return (hsv[0] >= 0.5 && hsv[0] < 0.667);
    }//7
    */

    public boolean isGreen()
    {
        int color1 = getRGB();
        int r = (color1 >> 16) & 0xFF;
        int g = (color1 >> 8) & 0xFF;
        int b = (color1 >> 0) & 0xFF;
        float[] hsv = new float[3];
        Color.RGBtoHSB(r,g,b,hsv);
        return (hsv[0] >= 0.25 && hsv[0] < 0.44);
    }//8

    public boolean isYellow()
    {
        int color1 = getRGB();
        int r = (color1 >> 16) & 0xFF;
        int g = (color1 >> 8) & 0xFF;
        int b = (color1 >> 0) & 0xFF;
        float[] hsv = new float[3];
        Color.RGBtoHSB(r,g,b,hsv);
        return (hsv[0] >= 0.15 && hsv[0] < 0.18 && hsv[1] > 0.6);
    }//9

    public boolean isOrange()
    {
        int color1 = getRGB();
        int r = (color1 >> 16) & 0xFF;
        int g = (color1 >> 8) & 0xFF;
        int b = (color1 >> 0) & 0xFF;
        float[] hsv = new float[3];
        Color.RGBtoHSB(r,g,b,hsv);
        return (hsv[0] >= 0.09 && hsv[0] < 0.14 && hsv[1] > 0.3 && hsv[2] > 0.5);
    }//10
    
    /*
    public boolean isBrown()
    {
        int color1 = getRGB();
        int r = (color1 >> 16) & 0xFF;
        int g = (color1 >> 8) & 0xFF;
        int b = (color1 >> 0) & 0xFF;
        float[] hsv = new float[3];
        Color.RGBtoHSB(r,g,b,hsv);
        return (hsv[0] >= 0.931 && hsv[0] < 0.983);
    }//11
    */
    
    public boolean isDistinct(EnhancedColor color, int indiviualColorDiffMin)
    {
        int color1 = getRGB();
        int r1 = (color1 >> 16) & 0xFF;
        int g1 = (color1 >> 8) & 0xFF;
        int b1 = (color1 >> 0) & 0xFF;
        
        int color2 = color.getRGB();
        int r2 = (color2 >> 16) & 0xFF;
        int g2 = (color2 >> 8) & 0xFF;
        int b2 = (color2 >> 0) & 0xFF;

        int rDiff = Math.abs(r1 - r2);
        int gDiff = Math.abs(g1 - g2);
        int bDiff = Math.abs(b1 - b2);
        
        return (rDiff > indiviualColorDiffMin) || (gDiff > indiviualColorDiffMin) || (bDiff > indiviualColorDiffMin);
    }
    public boolean isDistinctR(EnhancedColor color, int indiviualColorDiffMin)
    {
        int color1 = getRGB();
        int r1 = (color1 >> 16) & 0xFF;
        int g1 = (color1 >> 8) & 0xFF;
        int b1 = (color1 >> 0) & 0xFF;
        
        int color2 = color.getRGB();
        int r2 = (color2 >> 16) & 0xFF;
        int g2 = (color2 >> 8) & 0xFF;
        int b2 = (color2 >> 0) & 0xFF;

        int rDiff = Math.abs(r1 - r2);
        int gDiff = Math.abs(g1 - g2);
        int bDiff = Math.abs(b1 - b2);
        
        return (rDiff > indiviualColorDiffMin);
    }
    public boolean isDistinctG(EnhancedColor color, int indiviualColorDiffMin)
    {
        int color1 = getRGB();
        int r1 = (color1 >> 16) & 0xFF;
        int g1 = (color1 >> 8) & 0xFF;
        int b1 = (color1 >> 0) & 0xFF;
        
        int color2 = color.getRGB();
        int r2 = (color2 >> 16) & 0xFF;
        int g2 = (color2 >> 8) & 0xFF;
        int b2 = (color2 >> 0) & 0xFF;

        int rDiff = Math.abs(r1 - r2);
        int gDiff = Math.abs(g1 - g2);
        int bDiff = Math.abs(b1 - b2);
        
        return (gDiff > indiviualColorDiffMin);
    }
    public boolean isDistinctB(EnhancedColor color, int indiviualColorDiffMin)
    {
        int color1 = getRGB();
        int r1 = (color1 >> 16) & 0xFF;
        int g1 = (color1 >> 8) & 0xFF;
        int b1 = (color1 >> 0) & 0xFF;
        
        int color2 = color.getRGB();
        int r2 = (color2 >> 16) & 0xFF;
        int g2 = (color2 >> 8) & 0xFF;
        int b2 = (color2 >> 0) & 0xFF;

        int rDiff = Math.abs(r1 - r2);
        int gDiff = Math.abs(g1 - g2);
        int bDiff = Math.abs(b1 - b2);
        
        return (bDiff > indiviualColorDiffMin);
    }
}
