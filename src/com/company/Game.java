package com.company;

import javax.swing.*;
import java.io.IOException;
import java.io.Serializable;

public class Game implements Serializable {
    private String path;
    private static Game game;
    private String playerCarsPath;
    public void getAndSelectGamePath() {
        JFileChooser gamePathChooser = new GameDirectoryChooser();
        String gamePath = gamePathChooser.getSelectedFile().getAbsolutePath();
        if(gamePath.length() > 0 && gamePath.contains("City Car Driving")) {
            Game.getGame().setPath(gamePath);
        }
    }
    private Game(){}
    public static Game getGame(){
        if(game == null){
            game = new Game();
        }
        return game;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getPlayerCarsPath() {
        return playerCarsPath;
    }

    public void setPlayerCarsPath(String playerCarsPath) {
        this.playerCarsPath = playerCarsPath;
    }
}