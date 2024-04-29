# Introduction
This is a Java Swing Heatmap component. Support Java 8 or higher.
It uses [Bicubic Interpolation](https://en.wikipedia.org/wiki/Bilinear_interpolation) and [Bilinear interpolation](https://en.wikipedia.org/wiki/Bicubic_interpolation) to generate a continous heatmap based on a grid data.
This repo has its own implementation of Bilinear interpolation. For Bicubic Interpolation, the [Apache Math Commons library](https://commons.apache.org/proper/commons-math/) is used.

# Usage
To generate a heatmap, create a heatmap model using a X vector, Y vector and the Heat Matrix. 
Create a HeatMapPanel which extends JPanel and set the model. For more details see the examples in the source repo.
Here is a summary of the Java example code.
```
  
        BilinearInterpolationHeatMapModel model = new BilinearInterpolationHeatMapModel(x,y,data, dataMin, dataMax, HeatMapColors.INFERNO_COLORS,
                2, "Title", "X Axis ", "Y Axis", false, false );

        HeatMapPanel heatmapPanel = new HeatMapPanel();
        heatmapPanel.setModel(model);
        add(heatmapPanel,BorderLayout.CENTER);
		
```
