package com.adarshsiva.heatmap;
import com.adarshsiva.heatmap.ui.HeatMapPanel;
import com.adarshsiva.heatmap.model.BicubicInterpolationHeatMapModel;
import com.adarshsiva.heatmap.colors.HeatMapColors;
import java.awt.BorderLayout;
import javax.swing.JFrame;
/**
 *
 * @author Adarsh
 */
public class BicubicHeatMapExample extends JFrame {

    public BicubicHeatMapExample(double[] x,double[] y,double[][] data, double dataMin, double dataMax) {
        setTitle("Bicubic Interpolation Heatmap");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 800);
        setLocationRelativeTo(null);
        BicubicInterpolationHeatMapModel model = new BicubicInterpolationHeatMapModel(x,y,data, dataMin, dataMax, HeatMapColors.INFERNO_COLORS,
                2, "Bi-cubic Heatmap example", "X Axis", "Y Axis", false, false );
        
        HeatMapPanel heatmapPanel = new HeatMapPanel();
        heatmapPanel.setModel(model);
        add(heatmapPanel,BorderLayout.CENTER);
    }

      public static void main(String[] args) {
        int gridSize = 32;

        final double[][] testData = new double[gridSize][gridSize]; // Replace this with your actual data
        final double[] x = new double[gridSize];
        final double[] y = new double[gridSize];
        double dataMin = Double.MAX_VALUE;
        double dataMax = -Double.MAX_VALUE;

        for(int i = 0; i < testData.length; i++){
            x[i] = i - ((gridSize-1)/2);
            y[i] = i - ((gridSize-1)/2);
            for(int j = 0; j < testData[0].length; j++)
            {
                testData[i][j] =  Math.sin(Math.sqrt(Math.pow(i-((gridSize-1)/2), 2) + Math.pow(j-((gridSize-1)/2), 2))) * 1000;
                if(testData[i][j] < dataMin )
                    dataMin = testData[i][j];
                if(testData[i][j] > dataMax )
                    dataMax = testData[i][j];
            }
        }
  
        BicubicHeatMapExample example = new BicubicHeatMapExample(x, y, testData, Math.floor(dataMin), Math.ceil(dataMax));
        example.setVisible(true);
    }
}
