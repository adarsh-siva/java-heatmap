package com.adarshsiva.bicubicinterpolation;

import java.awt.Color;
import org.apache.commons.math3.analysis.interpolation.PiecewiseBicubicSplineInterpolatingFunction;

/**
 * Class that implements the HeatMapModel interface using bicubic interpolation
 * @author Adarsh
 */
public class BicubicInterpolationHeatMapModel implements HeatMapModel {
    double maxX, minX, maxY, minY;
    double minHeat;
    double maxHeat;
    String title;
    String xAxisLabel;
    String yAxisLabel;
    Color[] heatColors;

    PiecewiseBicubicSplineInterpolatingFunction function;

    public BicubicInterpolationHeatMapModel(double[] xcoords, double[] ycoords, double[][] data, double dataMin, double dataMax,
            Color[] heatColors, String title, String xAxisLabel, String yAxisLabel) {
        minX = xcoords[0];
        maxX = xcoords[xcoords.length - 1];
        minY = ycoords[0];
        maxY = ycoords[ycoords.length - 1];
        function = new PiecewiseBicubicSplineInterpolatingFunction(xcoords, ycoords, data);
        minHeat = dataMin;
        maxHeat = dataMax;
        this.xAxisLabel = xAxisLabel;
        this.yAxisLabel = yAxisLabel;
        this.heatColors = heatColors;
        this.title = title;
    }

    @Override
    public double getHeatValue(double x, double y) {
        
        return function.value(x, y);
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
    public String getTitle()
    {
        return title;
    }
}
