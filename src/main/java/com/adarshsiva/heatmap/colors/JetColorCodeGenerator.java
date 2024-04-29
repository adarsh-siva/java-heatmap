package com.adarshsiva.heatmap.colors;

/**
 *
 * @author Adarsh
 */
public class JetColorCodeGenerator
{

    public static final void main(String[] args) {
        double vmin = 0;
        double vmax = 255;
        double dv = vmax - vmin;
        for(double v=0; v<256; v++) {
            double r = 1.0, g = 1.0, b = 1.0; 

            if (v < vmin)
                v = vmin;
            if (v > vmax)
                v = vmax;

            if (v < (vmin + 0.25 * dv)) {
                r = 0;
                g = 4 * (v - vmin) / dv;
            } else if (v < (vmin + 0.5 * dv)) {
                r = 0;
                b = 1 + 4 * (vmin + 0.25 * dv - v) / dv;
            } else if (v < (vmin + 0.75 * dv)) {
                r = 4 * (v - vmin - 0.5 * dv) / dv;
                b = 0;
            } else {
                g = 1 + 4 * (vmin + 0.75 * dv - v) / dv;
                b = 0;
            }

            System.out.printf("new Color( %.6ff, %.6ff, %.6ff),\n",r,g,b);
        }

    }
    
}
