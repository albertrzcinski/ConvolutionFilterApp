package ui;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Convolution{

    static double[][] filterTab = new double[3][];

    public static void setFilterTab(double[][] filterTab) {
        Convolution.filterTab = filterTab;
    }

    static double checkValue(double value)
    {
        value = Math.round(value);
        if(value<0.0) value=0.0;
        if(value>255.0) value=255.0;
        return value;
    }

    public static BufferedImage transform(BufferedImage image, int height, int width, int Y, int X){

            int[][] redTab = new int[height+Y][width+X];
            int[][] greenTab = new int[height+Y][width+X];
            int[][] blueTab = new int[height+Y][width+X];

            int[][] wyRedTab = new int[height+Y][width+X];
            int[][] wyGreenTab = new int[height+Y][width+X];
            int[][] wyBlueTab = new int[height+Y][width+X];

            double n=0;

            for(int i=0; i<3; i++)
                for (int j = 0; j < 3; j++)
                    n+=filterTab[i][j];

            if(n==0) n++;

            for(int y=Y; y<height+Y; y++){
                for(int x=X; x<width+X; x++){
                    Color color = new Color(image.getRGB(x,y));
                    int red = color.getRed();
                    redTab[y][x]=red;
                    int green = color.getGreen();
                    greenTab[y][x]=green;
                    int blue = color.getBlue();
                    blueTab[y][x] = blue;
                }
            }

            double sumR, sumG, sumB;

            for(int y=Y; y<height+Y; y++){
                for(int x=X; x<width+X; x++) {
                    sumR = sumG = sumB = 0;
                    for(int w=0; w<3; w++) {
                        for (int k = 0; k < 3; k++) {
                            try{
                                sumR += redTab[y - 1 + w][x - 1 + k] * filterTab[w][k];
                                sumG += greenTab[y - 1 + w][x - 1 + k] * filterTab[w][k];
                                sumB += blueTab[y - 1 + w][x - 1 + k] * filterTab[w][k];
                            }catch (ArrayIndexOutOfBoundsException e){
                                sumR += redTab[y][x] * filterTab[w][k];
                                sumG += greenTab[y][x] * filterTab[w][k];
                                sumB += blueTab[y][x] * filterTab[w][k];
                            }
                        }
                        wyRedTab[y][x] = (int) checkValue(sumR/n);
                        wyGreenTab[y][x] = (int) checkValue(sumG/n);
                        wyBlueTab[y][x] = (int) checkValue(sumB/n);
                    }
                }
            }

            for(int y=Y; y<height+Y; y++) {
                for (int x = X; x < width+X; x++) {
                    Color color = new Color(wyRedTab[y][x], wyGreenTab[y][x], wyBlueTab[y][x]);
                    image.setRGB(x, y, color.getRGB());
                }
            }
            return image;
    }
}
