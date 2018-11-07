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
public class Tembok extends Pixel{

    public Tembok(int x, int y) {
        super(x, y);
        URL loc = this.getClass().getResource("/wall.jpg");
        ImageIcon g = new ImageIcon(loc);
        Image image = g.getImage();
        this.setImage(image);
    }
    
}
