package org.heatmapvis.ui;

import javax.swing.*;

import org.heatmapvis.loader.Loader;
import org.heatmapvis.util.FileSelector;

import java.awt.*;

public class Windows extends JFrame {
    public static Windows mInstance;

    private JPanel topPanel = new JPanel();
    private JPanel mainPanel = new JPanel();
    private JPanel bottomPanel = new JPanel();
    private Heatmap heatmap = new Heatmap();

    public Windows() {
        mInstance = this;
        initBasics();
        initPanels();
        this.setVisible(true);
    }

    private void initBasics() {
        this.setExtendedState(JFrame.MAXIMIZED_BOTH);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLayout(new BorderLayout());
        this.getContentPane().setBackground(Color.BLACK);
    }

    private void initPanels() {
        initTopPanelsObjects();
        initMainPanelsObjects();
        initBottomPanelsObjects();

        // Add panels to the frame
        this.add(topPanel, BorderLayout.NORTH);
        this.add(mainPanel, BorderLayout.CENTER);
        this.add(bottomPanel, BorderLayout.SOUTH);
    }

    private void initTopPanelsObjects() {
        JButton loadNewButton = new JButton("Load New");
        loadNewButton.addActionListener(e -> loadNewWPILOG());
        loadNewButton.setForeground(Color.BLACK);
        loadNewButton.setBackground(Color.GRAY);
        loadNewButton.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1, true));
        loadNewButton.setPreferredSize(new Dimension(200, 30));
        topPanel.add(loadNewButton);
        topPanel.setPreferredSize(new Dimension(this.getWidth(), 40));
        topPanel.setBackground(Color.DARK_GRAY);
        topPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));
    }

    private void initMainPanelsObjects() {
        mainPanel.add(heatmap);
        mainPanel.setBackground(Color.GRAY);
        mainPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));
    }

    private void initBottomPanelsObjects() {
        bottomPanel.setPreferredSize(new Dimension(this.getWidth(), 50));
        bottomPanel.setBackground(Color.DARK_GRAY);
        bottomPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));
    }

    private void loadNewWPILOG() {
        FileSelector fs = new FileSelector();

        if (fs.getDidSelect() == false)
            return;

        Loader.Load();
    }
}
