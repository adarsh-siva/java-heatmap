package com.adarshsiva.heatmap.model;

import java.awt.Color;

/**
 * Class that implements the HeatMapModel interface using Bicubic interpolation
 * @author Adarsh
 */
public class BilinearInterpolationHeatMapModel implements HeatMapModel {
    double maxX, minX, maxY, minY;
    double minHeat;
    double maxHeat;
    String title;
    String xAxisLabel;
    String yAxisLabel;
    Color[] heatColors;
    int heatPixelSize;
    boolean flipX;
    boolean flipY;

    BilinearInterpolation function;

    public BilinearInterpolationHeatMapModel(double[] xcoords, double[] ycoords, double[][] data, double dataMin, double dataMax,
            Color[] heatColors, int heatPixelSize, String title, String xAxisLabel, String yAxisLabel, boolean flipX, boolean flipY)
    {
        minX = xcoords[0];
        maxX = xcoords[xcoords.length - 1];
        minY = ycoords[0];
        maxY = ycoords[ycoords.length - 1];
        function = new BilinearInterpolation(xcoords, ycoords, data);
        minHeat = dataMin;
        maxHeat = dataMax;
        this.xAxisLabel = xAxisLabel;
        this.yAxisLabel = yAxisLabel;
        this.heatColors = heatColors;
        this.heatPixelSize = heatPixelSize;
        this.title = title;
        this.flipX = flipX;
        this.flipY = flipY;
    }

    @Override
    public double getHeatValue(double x, double y)
    {
        try
        {
            return function.interpolate(x, y);
        }
        catch(Exception ex)
        {
            return Double.NaN;
        }
    }

    @Override
    public double getMinX() {
        return minX;
    }

    @Override
    public double getMaxX() {
        return maxX;
    }

    @Override
    public double getMinY() {
        return minY;
    }

    @Override
    public double getMaxY() {
        return maxY;
    }

    @Override
    public double getMinHeatValue() {
        return minHeat;
    }

    @Override
    public double getMaxHeatValue() {
        return maxHeat;
    }

    @Override
    public Color[] getHeatColors() {
        return heatColors;
    }

    @Override
    public String getXAxisLabel() {
        return xAxisLabel;
    }

    @Override
    public String getYAxisLabel() {
       return yAxisLabel;
    }

    @Override
    public String getTitle() {
        return title;
    }

    @Override
    public int getHeatPixelSize() {
        return heatPixelSize;
    }

    @Override
    public boolean isFlipX()
    {
        return flipX;
    }

    @Override
    public boolean isFlipY()
    {
        return flipY;
    }
}
