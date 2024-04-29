/*
 * Copyright 2022 Griffin Open Systems LLC
 */
package com.adarshsiva.heatmap.model;

import java.util.Arrays;

/**
 *
 * @author Siva
 */
public class BilinearInterpolation
{
    double[] xAxis;
    double[] yAxis;
    double[][] gridMatrix;

    public BilinearInterpolation(double[] x, double y[], double[][] grid)
    {
        this.xAxis = x;
        this.yAxis = y;
        this.gridMatrix = grid;
    }

    int prevXIndex;
    int prevYIndex;

    public double interpolate(double x, double y) throws Exception
    {
        if(x < xAxis[0] || x > xAxis[xAxis.length-1])
            throw new Exception("BilinearInterpolation: x value outside the range");

        if(y < yAxis[0] || y > yAxis[yAxis.length-1])
            throw new Exception("BilinearInterpolation: y value outside the range");

        int xIndex;
        int yIndex;

        if(xAxis[prevXIndex] <= x && x< xAxis[prevXIndex+1] )
            xIndex = prevXIndex;
        else if ((prevXIndex+1) < xAxis.length && xAxis[prevXIndex+1] <= x
                && ( prevXIndex+2 == xAxis.length || x < xAxis[prevXIndex+2]) )
            xIndex = prevXIndex + 1;
        else
        {
            xIndex = Arrays.binarySearch(xAxis, x);
            if(xIndex < 0)
                xIndex = Math.abs(xIndex)-1;
        }

        if(yAxis[prevYIndex] <= y && y< yAxis[prevYIndex+1] )
            yIndex = prevYIndex;
        else if ((prevYIndex+1) < yAxis.length && yAxis[prevYIndex+1] <= y
                && ( prevYIndex+2 == yAxis.length || x < yAxis[prevYIndex+2]) )
            yIndex = prevYIndex + 1;
        else
        {
            yIndex = Arrays.binarySearch(yAxis, y);
            if(yIndex < 0)
                yIndex = Math.abs(yIndex)-1;
        }

        prevXIndex = xIndex;
        prevYIndex = yIndex;

        int x0 = xIndex;
        int x1 = xIndex + 1;
        int y0 = yIndex;
        int y1 = yIndex + 1;

        // Ensure the coordinates are within the grid boundaries
        x0 = Math.min(x0, gridMatrix.length - 1);
        x1 = Math.min(x1, gridMatrix.length - 1);
        y0 = Math.min(y0, gridMatrix[0].length - 1);
        y1 = Math.min(y1, gridMatrix[0].length - 1);


        // Bilinear interpolation formula
        double q11 = gridMatrix[x0][y0];
        double q21 = gridMatrix[x1][y0];
        double q12 = gridMatrix[x0][y1];
        double q22 = gridMatrix[x1][y1];

        double xFraction = (x - xAxis[x0])/(xAxis[x1] - xAxis[x0]);
        if(x1 == x0)
            xFraction = 0;

        double yFraction = (y - yAxis[y0])/(yAxis[y1] - yAxis[y0]);
        if(y1 == y0)
            yFraction = 0;

        double topInterpolation = (1 - xFraction) * q11 + xFraction * q21;
        double bottomInterpolation = (1 - xFraction) * q12 + xFraction * q22;

        return (1 - yFraction) * topInterpolation + yFraction * bottomInterpolation;
    }

     public static void main(String[] args)
     {
        double[][] grid = {
            {1, 2, 3, 4},
            {2, 3, 4, 5},
            {3, 4, 5, 6}
        };

        double[] x = { -1, 0, 1 };
        double[] y = { 0, 25, 50, 75 };

        try
        {
            BilinearInterpolation bi = new BilinearInterpolation(x, y, grid);
            System.out.println("x = -1  y = 0  value = " + bi.interpolate(-1, 0));
            System.out.println("x = -0.25  y = 10  value = " + bi.interpolate(-0.25, 10));
            System.out.println("x = 0  y = 30  value = " + bi.interpolate(0, 30));
            System.out.println("x = 0.75  y = 60  value = " + bi.interpolate(0.75, 60));
            System.out.println("x = 1  y = 75  value = " + bi.interpolate(1, 75));
            System.out.println("x = -1  y = 50  value = " + bi.interpolate(-1, 50));
        }
        catch(Exception ex)
        {
            System.out.println("Error: "+ex.getMessage());
            ex.printStackTrace(System.err);
        }
    }
}

