package com.adarshsiva.heatmap.ui;

import com.adarshsiva.heatmap.model.HeatMapModel;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.geom.Rectangle2D;
import java.text.DecimalFormat;
import javax.swing.JPanel;


/**
 *
 * @author Adarsh
 */
public class HeatMapPanel extends JPanel {
    HeatMapModel model;

    public HeatMapPanel() {
    }

    public void setModel(HeatMapModel model) {
        this.model = model;
        repaint();
    }

    private final int inset = 3;

    private final float titleFontSizeFactor = 0.05f;
    private final int minTitleFontSize = 10;
    private final String titleFontFamily = "SansSerif";

    private final int tickLength = 4;
    private final float numTickFactor = 0.02f;
    private final String tickFontFamily = "SansSerif";
    private final float tickFontSizeFactor = 0.03f;
    private final int minTickFontSize = 6;

    private final float axisLabelFontSizeFactor = 0.03f;
    private final int minAxisLabelFontSize = 8;

    private final float heatScaleWidthFactor = 0.05f;
    private final int minHeatScaleWidth = 5;
    private final int maxHeatScaleWidth = 25;
    private final float heatScaleHeightFactor = 0.7f;
    private final int minHeatScaleHeight = 20;

    @Override
    public void printAll(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        g2d.setColor(Color.white);
        g2d.fillRect(0, 0, getWidth(), getHeight());
        paintHeatMap(g2d);
    }


    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        paintHeatMap(g2d);
    }


    private void paintHeatMap(Graphics2D g2d) {

        if(model == null)
            return;

        g2d.setRenderingHint( RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.setRenderingHint( RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);

        int width = getWidth();
        int height = getHeight();
        Color[] heatColors = model.getHeatColors();

        // Paint the title
        int titleHeight = paintTitle(g2d);

        // Calculate Axis label font size 
        int axisLabelFontSize = (int)(Math.min(height, width) * axisLabelFontSizeFactor);
        if(axisLabelFontSize < minAxisLabelFontSize)
            axisLabelFontSize = minAxisLabelFontSize;
        Font axisLabelFont = new Font(titleFontFamily, Font.PLAIN, axisLabelFontSize);

        //Paint the X and Y Axis labels
        int yAxisLabelWidth = paintYAxisLabel(g2d, axisLabelFont);
        int xAxisLabelHeight = paintXAxisLabel(g2d, axisLabelFont);

        // Calculate Tick font size 
        int tickFontSize = (int)(Math.min(height, width) * tickFontSizeFactor);
        if(tickFontSize < minTickFontSize)
            tickFontSize = minTickFontSize;
        Font tickFont = new Font(tickFontFamily, Font.PLAIN, tickFontSize);
        FontMetrics tickFontMetrics = g2d.getFontMetrics(tickFont);

        //Calculate the max height required to display X axis ticks
        int maxXTicksHeight = tickFontMetrics.getMaxAscent() + tickFontMetrics.getMaxDescent() + tickLength + inset * 2;

        // Calculate max number of ticks to display on Y Axis 
        int maxYTicksWidth = calculateMaxYAxisTickWidth(g2d, tickFont, height - titleHeight - maxXTicksHeight);

        /* Draw heat scale */
        int heatScaleAreaWidth = paintHeatMapScale(g2d, heatColors, tickFont);


        // calculate heatmap bounding box 
        int heatBoxX = yAxisLabelWidth + maxYTicksWidth;
        int heatBoxY = titleHeight;
        int heatBoxWidth = width - yAxisLabelWidth - maxYTicksWidth - heatScaleAreaWidth;
        int heatBoxHeight = height - titleHeight - xAxisLabelHeight - maxXTicksHeight;

        // Draw heatmap bounding reactable 
        g2d.drawRect(heatBoxX, heatBoxY, heatBoxWidth, heatBoxHeight);

        // Draw X Axis tick marks
        paintXTickMarks(g2d, tickFont, heatBoxX, heatBoxX + heatBoxWidth, heatBoxY + heatBoxHeight);

        // Draw Y Axis tick marks
        paintYTickMarks(g2d, tickFont, heatBoxY + heatBoxHeight, heatBoxY, heatBoxX);


        // Draw heat map
        paintHeatMap(g2d, heatBoxX, heatBoxY, heatBoxWidth, heatBoxHeight);
    }


    // Paint the title and return the height used up for the title
    private int paintTitle(Graphics2D g2d) {
        int titleHeight = inset;
        String title = model.getTitle();
        double width = getWidth();
        double height = getHeight();

        g2d.setColor(Color.black);

        if(title != null && !title.trim().isEmpty())
        {
            int fontSize = (int)(height * titleFontSizeFactor);
            if(fontSize < minTitleFontSize)
                fontSize = minTitleFontSize;
            g2d.setFont(new Font(titleFontFamily, Font.PLAIN, fontSize));
            FontMetrics fm = g2d.getFontMetrics();
            Rectangle2D titleRect = fm.getStringBounds(title, g2d);
            g2d.drawString(title,(int)((width - titleRect.getWidth())/2.0), inset + fm.getMaxAscent());
            titleHeight = fm.getHeight() + fm.getDescent() + inset * 2;
        }
    
        return titleHeight;
    }

    // Paint the Y Axis Label and return the width used up for the Y axis label
    private int paintYAxisLabel(Graphics2D g2d, Font axisLabelFont) {
        int yAxisLabelWidth = inset;

        String yAxisLabel = model.getYAxisLabel();
        int height = getHeight();

        if(yAxisLabel != null && !yAxisLabel.trim().isEmpty())
        {
            g2d.setFont(axisLabelFont);
            FontMetrics fm = g2d.getFontMetrics();
            Rectangle2D labelRect  = fm.getStringBounds(yAxisLabel, g2d);
            int yAxisLabelY = (int)((height + labelRect.getWidth()) / 2);
            g2d.rotate(-Math.PI / 2);
            g2d.drawString(yAxisLabel, -yAxisLabelY, inset + fm.getAscent());
            g2d.rotate(Math.PI / 2);
            yAxisLabelWidth = fm.getMaxAscent()+ fm.getMaxDescent() + inset * 2;
        }

        return yAxisLabelWidth;
    }

    // Paint the X Axis Label and return the height used up for the X axis label
    private int paintXAxisLabel(Graphics2D g2d, Font axisLabelFont) {
        int xAxisLabelHeight = inset;
        int width = getWidth();
        int height = getHeight();
        String xAxisLabel = model.getXAxisLabel();
        if(xAxisLabel != null && !xAxisLabel.trim().isEmpty())
        {
            g2d.setFont(axisLabelFont);
            FontMetrics fm = g2d.getFontMetrics();
            Rectangle2D labelRect  = fm.getStringBounds(xAxisLabel, g2d);
            g2d.drawString(xAxisLabel,
                    (int)((width - labelRect.getWidth())/2),
                    (int)(height - fm.getDescent() - inset));
            xAxisLabelHeight = fm.getMaxAscent()+ fm.getMaxDescent() + inset * 2;
        }

        return xAxisLabelHeight;
    }

    // Draw X-axis tick marks and labels
    private void paintXTickMarks(Graphics2D g2d, Font tickLabelFont, int xStart, int xEnd, int y) {
        boolean flipX = model.isFlipX();
        int maxNumTicks = (int)((xEnd - xStart) * numTickFactor);
        if(maxNumTicks < 3)
            maxNumTicks = 3;
        double minX = model.getMinX();
        double maxX = model.getMaxX();
        NiceScale numScaleX = new NiceScale(minX, maxX, maxNumTicks);
        g2d.setFont(tickLabelFont);
        FontMetrics fm = g2d.getFontMetrics();
        for (double x = numScaleX.niceMin; x <= numScaleX.niceMax; x += numScaleX.tickSpacing) {
            String tickLabel = formatDouble(x);
            Rectangle2D tickRect = fm.getStringBounds(tickLabel, g2d);
            int xCoord;
            if(flipX)
                xCoord = xEnd - (int) ((x - minX) / (maxX - minX) * (xEnd - xStart)) ;
            else
                xCoord = xStart  + (int) ((x - minX) / (maxX - minX) * (xEnd - xStart));
            g2d.setColor(Color.BLACK);
            g2d.drawLine(xCoord, y, xCoord, y + tickLength);
            g2d.drawString(tickLabel, xCoord - (int)(tickRect.getWidth()/2.0),
                    y + tickLength + fm.getAscent() + inset);
        }
    }

    private int calculateMaxYAxisTickWidth(Graphics2D g2d, Font tickLabelFont, int yAxisHeight) {
        // Calculate max number of ticks to display on Y Axis 
        int maxYTicks = (int)(yAxisHeight * numTickFactor);
        if(maxYTicks < 3)
            maxYTicks = 3;
        double minY = model.getMinY();
        double maxY = model.getMaxY();
        NiceScale numScaleY = new NiceScale(minY, maxY, maxYTicks);

        FontMetrics fm = g2d.getFontMetrics(tickLabelFont);
        int maxYTickLabelWidth = 0;
        for (double y = numScaleY.niceMin; y <= numScaleY.niceMax; y += numScaleY.tickSpacing) {
            Rectangle2D tickLabelBounds = fm.getStringBounds(formatDouble(y), g2d);
            if(maxYTickLabelWidth < tickLabelBounds.getWidth())
                maxYTickLabelWidth = (int)Math.ceil(tickLabelBounds.getWidth());
        }
        maxYTickLabelWidth += inset * 2 + tickLength;

        return maxYTickLabelWidth;
    }

    // Draw Y-axis tick marks and labels
    private void paintYTickMarks(Graphics2D g2d, Font tickLabelFont, int yStart, int yEnd, int x) {
        double minY = model.getMinY();
        double maxY = model.getMaxY();
        boolean flipY = model.isFlipY();
        int maxNumTicks = (int)((yStart - yEnd) * numTickFactor);
        if(maxNumTicks < 3)
            maxNumTicks = 3;
        NiceScale numScaleY = new NiceScale(minY, maxY, maxNumTicks);
        g2d.setFont(tickLabelFont);
        g2d.setColor(Color.BLACK);
        FontMetrics fm = g2d.getFontMetrics();
        for (double y = numScaleY.niceMin; y <= numScaleY.niceMax; y += numScaleY.tickSpacing) {
            int yCoord;
            if(flipY)
                yCoord = (int)(yEnd + (int) ((y - minY) / (maxY - minY) * (yStart - yEnd)));
            else
                yCoord = (int)(yStart - (int) ((y - minY) / (maxY - minY) * (yStart - yEnd)));
            g2d.drawLine(x - tickLength, yCoord, x, yCoord);
            String tickLabel = formatDouble(y);
            g2d.drawString(tickLabel, (int)(x - fm.getStringBounds(tickLabel, g2d).getWidth() - tickLength - inset), (int)(yCoord + fm.getAscent()/2.0 - fm.getDescent()/2.0));
        }
    }

    //Draw the heat map
    private void paintHeatMap(Graphics2D g2d, int topX, int topY, int width, int height) {
        double minX = model.getMinX();
        double maxX = model.getMaxX();
        double minY = model.getMinY();
        double maxY = model.getMaxY();
        double heatMin = model.getMinHeatValue();
        double heatMax = model.getMaxHeatValue();
        boolean flipX = model.isFlipX();
        boolean flipY = model.isFlipY();
        Color[] heatColors = model.getHeatColors();

        double xPixelFactor = (maxX - minX) / width;
        double yPixelFactor = (maxY - minY) / height;

        int heatPixelSize = model.getHeatPixelSize();
        for (int i = 0; i < width; i += heatPixelSize)
        {
            for (int j = 0; j < height; j += heatPixelSize)
            {
                double xVal = i * xPixelFactor + minX;
                double yVal = j * yPixelFactor + minY;
                double value = model.getHeatValue(xVal, yVal);
                Color color = getColor(value, heatMin, heatMax, heatColors);
                g2d.setColor(color);

                int xCoord;
                int yCoord;

                if(flipX)
                    xCoord = topX + width - i - heatPixelSize;
                else
                    xCoord = topX + i;

                if(flipY)
                    yCoord = topY + j;
                else
                    yCoord = topY + height - j - heatPixelSize;

                g2d.fillRect(xCoord, yCoord , heatPixelSize, heatPixelSize);
            }
        }
    }

    // Paint the heatmap scale with ticks
    private int paintHeatMapScale(Graphics2D g2d, Color[] heatColors, Font tickFont) {
        double width = getWidth();
        double height = getHeight();

        int heatScaleWidth = (int) (width * heatScaleWidthFactor);
        if (heatScaleWidth < minHeatScaleWidth)
            heatScaleWidth = minHeatScaleWidth;

        if (heatScaleWidth > maxHeatScaleWidth)
            heatScaleWidth = maxHeatScaleWidth;

        int heatScaleHeight = (int) (height * heatScaleHeightFactor);
        if (heatScaleHeight < minHeatScaleHeight)
            heatScaleHeight = minHeatScaleHeight;

        int heatScaleInc = (int) ((float) heatScaleHeight / (float) heatColors.length);
        int heatScaleColorInc = 1;
        if (heatScaleInc == 0)
        {
            heatScaleInc = 1;
            heatScaleColorInc = (int) Math.ceil(1.0 / ((double) heatScaleHeight / (double) heatColors.length));
        }
        heatScaleHeight = (int) (heatScaleInc * heatColors.length / heatScaleColorInc);

        FontMetrics fm = g2d.getFontMetrics(tickFont);

        String maxLabel = formatDouble(model.getMaxHeatValue());
        Rectangle2D maxLabelBounds = fm.getStringBounds(maxLabel, g2d);

        String minLabel = formatDouble(model.getMinHeatValue());
        Rectangle2D minLabelBounds = fm.getStringBounds(minLabel, g2d);

        int scaleLabelWidth = (int)Math.max(minLabelBounds.getWidth(), maxLabelBounds.getWidth());

        int totalWidth = scaleLabelWidth + inset * 4;
        if((heatScaleWidth + inset * 2 ) > totalWidth)
            totalWidth  = heatScaleWidth +  inset * 4;

        int heatScaleY = (int) ((height - heatScaleHeight) / 2);
        int heatScaleX = (int) (width - ((totalWidth + heatScaleWidth)/2.0));

        g2d.setFont(tickFont);
        g2d.setColor(Color.black);
        g2d.drawString(maxLabel, (int)(width - ((totalWidth + maxLabelBounds.getWidth())/2.0)),
               (int)(heatScaleY - fm.getDescent() - inset));

        g2d.drawRect(heatScaleX - 1, heatScaleY - 1, heatScaleWidth + 1, heatScaleHeight + 1);
        for (int i = heatColors.length - 1; i >= 0; i -= heatScaleColorInc)
        {
            g2d.setColor(heatColors[i]);
            g2d.fillRect(heatScaleX, heatScaleY, heatScaleWidth, heatScaleInc);
            heatScaleY += heatScaleInc;
        }
        g2d.setColor(Color.black);

        g2d.drawString(minLabel, (int)(width - ((totalWidth + minLabelBounds.getWidth())/2.0)),
               (int)(heatScaleY + fm.getAscent() + inset));

        return totalWidth;
    }

    private Color getColor(double value, double min, double max, Color[] colors) {
        if(value < min)
            value = min;
        if(value > max)
            value = max;
        double normalizedValue = (value - min) / (max - min);
        int colorIdx = (int) (normalizedValue * (colors.length - 1));
        return colors[colorIdx];
    }

    private static DecimalFormat[] decimalFormats = new DecimalFormat[] {
		new DecimalFormat("0.#####"),
		new DecimalFormat("0.####"),
		new DecimalFormat("0.###"),
		new DecimalFormat("0.##"),
		new DecimalFormat("0")
	};
    
    private static String formatDouble(double val) {
		double absVal = Math.abs(val);
		if(Math.round(val) == val)
			return decimalFormats[4].format(val);
		if(absVal >= 100 && absVal < 10000)
			return decimalFormats[3].format(val);
		else if(absVal >= 10 && absVal < 100)
			return decimalFormats[2].format(val);
		else if(absVal >= 0.001 && absVal < 10)
			return decimalFormats[1].format(val);
		else if(absVal > 0.0009 && absVal < 0.001)
			return decimalFormats[0].format(val);
		else
        {
            if(val < 0.000001 && val > -0.000001)
                return "0";
            else
			    return Double.toString(val);
        }
	}

    public class NiceScale {
        private final double minPoint;
        private final double maxPoint;
        private final double tickSpacing;
        private final double range;
        private final double niceMin;
        private final double niceMax;


        public NiceScale(double min, double max, int maxTicks) {
            this.minPoint = min;
            this.maxPoint = max;
            this.range = niceNum(maxPoint - minPoint, false);
            this.tickSpacing = niceNum(range / (maxTicks - 1), true);
            this.niceMin = Math.ceil(minPoint / tickSpacing) * tickSpacing;
            this.niceMax = Math.floor(maxPoint / tickSpacing) * tickSpacing;
        }

        //https://stackoverflow.com/a/16363437
        private double niceNum(double range, boolean round) {
            double exponent;
            double fraction;
            double niceFraction;
      
            exponent = Math.floor(Math.log10(range));
            fraction = range / Math.pow(10, exponent);

            if (round) {
                if (fraction < 1.5) {
                    niceFraction = 1;
                } else if (fraction < 3) {
                    niceFraction = 2;
                } else if (fraction < 7) {
                    niceFraction = 5;
                } else {
                    niceFraction = 10;
                }
            } else {
                if (fraction <= 1) {
                    niceFraction = 1;
                } else if (fraction <= 2) {
                    niceFraction = 2;
                } else if (fraction <= 5) {
                    niceFraction = 5;
                } else {
                    niceFraction = 10;
                }
            }

            return niceFraction * Math.pow(10, exponent);
        }
    }

}
