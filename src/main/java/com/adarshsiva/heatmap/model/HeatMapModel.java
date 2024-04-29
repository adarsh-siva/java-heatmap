package com.adarshsiva.heatmap.model;

import java.awt.Color;

/**
 *
 * @author Adarsh
 */
public interface HeatMapModel {

    /**
     * Returns the heat value at the given x and y value.
     * @param x X coordinate value
     * @param y Y coordinate value
     * @return heat value
     */
    double getHeatValue(double x, double y);
    
    /**
     * Minimum value for X coordinate
     * @return Minimum value for X
     */
    double getMinX();

    /**
     * Maximum value for X coordinate
     * @return Maximum value for X
     */
    double getMaxX();

    /**
     * Minimum value for Y coordinate
     * @return Minimum value for Y
     */
    double getMinY();

    /**
     * Maximum value for Y coordinate
     * @return Maximum value for Y
     */
    double getMaxY();
    
    /**
     * Minimum value for heat value
     * @return Minimum value for heat
     */
    double getMinHeatValue();

    /**
     * Maximum value for heat value
     * @return Maximum value for heat
     */
    double getMaxHeatValue();
    
    /**
     * Provide the range of colors for the heat value. The fist color in the 
     * array will be the color for the minimum heat value. The last color in the
     * array will be the color for the maximum heat value.
     * @return 
     */
    Color[]  getHeatColors();
    
    /**
     * Provide a label for the X Axis. Null if no label has to be displayed
     * @return X Axis label
     */
    String getXAxisLabel();
    
    
    /**
     * Provide a label for the Y Axis. Null if no label has to be displayed
     * @return Y Axis label
     */
    String getYAxisLabel();


    /**
     * Provide a title for the Heatmap
     * @return Title for the heatmap
     */
    String getTitle();


    /**
     * Provide the pixel size for each heat value 
     * @return 
     */
    int getHeatPixelSize();


    /**
     * Whether X Axis should be flipped
     * @return True if X Axis needs to be flipped
     */
    boolean isFlipX();

    /**
     * Whether Y Axis should be flipped
     * @return True if Y Axis needs to be flipped
     */
    boolean isFlipY();
}
