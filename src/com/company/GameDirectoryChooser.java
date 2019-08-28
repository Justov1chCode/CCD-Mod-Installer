package com.company;

import javax.swing.*;

public class GameDirectoryChooser extends JFileChooser {
    public GameDirectoryChooser() {
        this.setFileSelectionMode(DIRECTORIES_ONLY);
        this.setApproveButtonText("ОК");
        this.showDialog(null, "Выберите директорию к игре");
    }
}
