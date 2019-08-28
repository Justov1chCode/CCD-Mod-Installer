package com.company;

import javax.swing.*;

public class ModDirectoryChooser extends JFileChooser {
    public ModDirectoryChooser() {
        this.setFileSelectionMode(DIRECTORIES_ONLY);
        this.setApproveButtonText("ОК");
        this.showDialog(null, "Выбрать директорию к моду");
    }
}
