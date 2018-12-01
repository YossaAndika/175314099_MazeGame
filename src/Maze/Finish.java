/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Maze;

import java.awt.Image;
import java.net.URL;
import javax.swing.ImageIcon;

/**
 *
 * @author admin
 */
public class Finish extends Pixel {

    public Finish(int x, int y) {
        super(x, y);//Mengakses constructor superclass (pixel) oleh subclass (Finish) dan lsg di set nilai xy Finish 
        URL loc = this.getClass().getResource("/Image/finish.jpg");
        ImageIcon g = new ImageIcon(loc);
        Image image = g.getImage();
        this.setImage(image);
    }
}
