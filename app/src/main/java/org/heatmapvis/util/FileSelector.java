package org.heatmapvis.util;

import java.awt.FileDialog;
import java.awt.Frame;

public class FileSelector {
    private String choosenPath = "";
    private boolean didSelect = false;

    public FileSelector() {
        // Detect platform root
        String rootDir = getRootDirectory();

        FileDialog fileDialog = new FileDialog((Frame) null, "Select a file", FileDialog.LOAD);
        fileDialog.setDirectory(rootDir); // start at root
        fileDialog.setVisible(true);

        String file = fileDialog.getFile();
        String dir = fileDialog.getDirectory();

        if (file != null && dir != null) {
            choosenPath = dir + file;
            didSelect = true;
        } else {
            didSelect = false;
        }
    }

    private String getRootDirectory() {
        String os = System.getProperty("os.name").toLowerCase();
        if (os.contains("win")) {
            return "C:\\"; // Windows root drive
        } else {
            return "/";    // macOS/Linux root
        }
    }

    public String getChoosenPath() {
        return choosenPath;
    }

    public boolean getDidSelect() {
        return didSelect;
    }
}
