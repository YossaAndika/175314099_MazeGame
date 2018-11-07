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
public class Pemain extends Pixel{

    public Pemain(int x, int y) {
        super(x,y);
        URL loc = this.getClass().getResource("/mario.jpg");
        ImageIcon iia = new ImageIcon(loc);
        Image image = iia.getImage();
    }
    
    public void Gerak(int x, int y){
        int nx = this.getPosisiX() + x;
        int ny = this.getPosisiY() + y;
        this.setPosisiX(nx);
        this.setPosisiY(ny);
        if (x < 0) {
            URL loc = this.getClass().getResource("/mario.jpg");
            ImageIcon inverse = new ImageIcon(loc);
            this.setImage(null);
            Image image = inverse.getImage();
            this.setImage(image);
        }else if (x > 0) {
            URL loc = this.getClass().getResource("/mario.jpg");
            ImageIcon iia = new ImageIcon(loc);
            this.setImage(null);
            Image image = iia.getImage();
            this.setImage(image);
        }
    }

}
