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
                2, "Bi-linear Heatmap example", "X Axis ", "Y Axis", false, false );

        HeatMapPanel heatmapPanel = new HeatMapPanel();
        heatmapPanel.setModel(model);
        add(heatmapPanel,BorderLayout.CENTER);
    }

      public static void main(String[] args) {
  
        final double[] x = { 10, 20, 30, 40, 50};
        final double[] y = { 100, 200, 300, 400, 500, 600, 700, 800, 900, 1000 };

        final double[][] testData = {        
            {100.0, 50.0, 25.0, 0.0, 0.0, 0.0, 0.0, 0.0, 50.0, 100.0 },
            {0.0, 100.0, 50.0, 25.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0 },
            {0.0, 0.0, 100.0, 50.0, 25.0, 0.0, 0.0, 0.0, 0.0, 0.0 },
            {0.0, 0.0, 0.0, 100.0, 50.0, 25.0, 0.0, 0.0, 0.0, 0.0 },
            {100.0, 50.0, 0.0, 0.0, 100.0, 50.0, 25.0, 0.0, 0.0, 0.0}
        };

  
        BilinearHeatMapExample example = new BilinearHeatMapExample(x, y, testData, 0.0, 100.0);
        example.setVisible(true);
    }
}
