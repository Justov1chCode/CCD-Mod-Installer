package com.company;

import javax.swing.*;
import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

public class Mod {
    private String path;
    private String dataPath;
    private String exportPath;
    private String configPath;

    public String getCarProperties() {
        return carProperties;
    }

    public void setCarProperties(String carProperties) {
        this.carProperties = carProperties;
    }

    private String carProperties;


    public void getAndSelectModPath() throws IOException {
        JFileChooser modeChooser = new ModDirectoryChooser();
        String modPath = modeChooser.getSelectedFile().getAbsolutePath();
        if(modPath.length() > 0) {
            this.setPath(modPath);
        }
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }


    public String getDataPath() {
        return dataPath;
    }

    public void setDataPath(String dataPath) {
        this.dataPath = dataPath;
    }

    public String getExportPath() {
        return exportPath;
    }

    public void setExportPath(String sourcePath) {
        this.exportPath = sourcePath;
    }

    public String getConfigPath() {
        return configPath;
    }

    public void setConfigPath(String configPath) {
        this.configPath = configPath;
    }
}
