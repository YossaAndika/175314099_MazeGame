/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.awt.Image;
import java.net.URL;
import javax.swing.ImageIcon;

/**
 *
 * @author windows10
 */
public class Finish extends Pixel{

    public Finish(int x, int y) {
        super(x,y);
        URL loc = this.getClass().getResource("/finish.jpg");
        ImageIcon g = new ImageIcon(loc);
        Image image = g.getImage();
        this.setImage(image);
    }
    
}
