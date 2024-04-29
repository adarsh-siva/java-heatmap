package com.adarshsiva.heatmap;
import com.adarshsiva.heatmap.ui.HeatMapPanel;
import com.adarshsiva.heatmap.model.BilinearInterpolationHeatMapModel;
import com.adarshsiva.heatmap.colors.HeatMapColors;
import java.awt.BorderLayout;
import javax.swing.JFrame;
/**
 *
 * @author Adarsh
 */
public class BilinearHeatMapExample extends JFrame {

    public BilinearHeatMapExample(double[] x,double[] y,double[][] data, double dataMin, double dataMax) {
        setTitle("Bicubic Interpolation Heatmap");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 800);
        setLocationRelativeTo(null);
        
        BilinearInterpolationHeatMapModel model = new BilinearInterpolationHeatMapModel(x,y,data, dataMin, dataMax, HeatMapColors.INFERNO_COLORS,
                2, "Title", "X Axis ", "Y Axis", false, false );

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
  
        BilinearHeatMapExample example = new BilinearHeatMapExample(x, y, testData, dataMin, dataMax);
        example.setVisible(true);
    }
}
