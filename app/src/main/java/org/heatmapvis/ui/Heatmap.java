package org.heatmapvis.ui;

import java.util.ArrayList;
import java.util.Map;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import edu.wpi.first.math.geometry.Translation3d;

public class Heatmap extends ImagePanel {
    private Map<String, ArrayList<ArrayList<Translation3d>>> data = null;

    public Heatmap() {
        super("map.png");
    }
    
    public void setData(Map<String, ArrayList<ArrayList<Translation3d>>> data) {
        this.data = data;
        Windows.mInstance.revalidate();
        Windows.mInstance.repaint();
    }

    public Map<String, ArrayList<ArrayList<Translation3d>>> getData() {
        return data;
    }

    public ArrayList<String> getDataNames() {
        return data.keySet().stream().collect(Collectors.toCollection(ArrayList::new));
    }
}
