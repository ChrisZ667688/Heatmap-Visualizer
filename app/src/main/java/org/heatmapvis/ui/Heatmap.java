package org.heatmapvis.ui;

import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

import edu.wpi.first.math.geometry.Translation3d;
import edu.wpi.first.wpilibj.util.Color;

public class Heatmap extends ImagePanel {
    private Map<String, ArrayList<ArrayList<Translation3d>>> data = null;
    private Map<String, ArrayList<Float>> hottestPoint = new HashMap<>();
    private Map<String, ArrayList<BufferedImage>> images = new HashMap<>();

    private static final int IMAGE_WIDTH = 1200;
    private static final int IMAGE_HEIGHT = 630;
    private static final double FIELD_WIDTH =  26.3867;// in feet
    private static final double FIELD_LENGTH = 54.1767; // in feet


    public Heatmap() {
        super("map.png");
    }
    
    public void setData(Map<String, ArrayList<ArrayList<Translation3d>>> data) {
        this.data = data;
        updateHottestPoint();
        // updateImages();
        Windows.mInstance.revalidate();
        Windows.mInstance.repaint();
    }

    private void updateHottestPoint() {
        if (data == null) {
            return;
        }

        for (String name : data.keySet()) {
            for (ArrayList<Translation3d> entry : data.get(name)) {
                float hottest = 0.0f;
                for (Translation3d point : entry) {
                    if (point.getZ() > hottest) {
                        hottest = (float) point.getZ();
                    }
                }
                hottestPoint.computeIfAbsent(name, k -> new ArrayList<>()).add(hottest);
            }
        }
    }

    // private void updateImages() {
    //     if (data == null) {
    //         return;
    //     }

    //     data.forEach((k, v) -> {
    //         for (ArrayList<Translation3d> entry : data.get(k)) {
    //             float hottest = hottestPoint.get(k).get(data.get(k).indexOf(entry));
    //             images.computeIfAbsent(k, key -> new ArrayList<>()).add(getImageFromData(entry, hottest));
    //         }
    //     });
    // }

    // public BufferedImage getImageFromData(ArrayList<Translation3d> data, float hottest) {
    //     if (data == null || data.isEmpty() || hottest <= 0){
    //         return new BufferedImage(IMAGE_WIDTH, IMAGE_HEIGHT, BufferedImage.TYPE_INT_RGB);
    //     }

    //     for (Translation3d point : data) {

    //         int x = (int) ((point.getX() / 8.0) * IMAGE_WIDTH);
    //         int y = (int) ((point.getY() / 4.0) * IMAGE_HEIGHT);
    //         int intensity = (int) ((point.getZ() / hottest) * 255);

    //         if (x < 0 || x >= IMAGE_WIDTH || y < 0 || y >= IMAGE_HEIGHT) continue;

    //         float ratio = (float) (point.getZ() / hottest);
    //         ratio = Math.max(0, Math.min(1, ratio));  
    //         int color = Color.hsvToRgb(0.7f * (1.0f - ratio), 1.0f, 1.0f);

    //     images.setRGB(x, y, color);
    //     }
        
    // }

    public Map<String, ArrayList<ArrayList<Translation3d>>> getData() {
        return data;
    }

    public ArrayList<String> getDataNames() {
        return data.keySet().stream().collect(Collectors.toCollection(ArrayList::new));
    }
}
