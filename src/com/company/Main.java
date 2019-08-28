package com.company;

import javax.swing.*;
import java.io.File;
import java.io.IOException;

public class Main {

    public static void main(String[] args) throws Exception{
        Game.getGame().getAndSelectGamePath();

        Mod mod = new Mod();
        mod.getAndSelectModPath();

        Installer installer = new Installer(mod);
        installer.install(Game.getGame(), mod);

        ConsoleHelper.write("CityCarDriving Path - " + Game.getGame().getPath());
        System.out.println("Mod Path - " + mod.getConfigPath());

    }
}
