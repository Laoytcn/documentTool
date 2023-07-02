package utils;

import java.awt.Toolkit;

import javax.swing.ImageIcon;

public class Imgs {
    public static ImageIcon getImage(String filename){
        return new ImageIcon(Toolkit.getDefaultToolkit().getImage(Imgs.class.getResource(filename)));
    }
}
