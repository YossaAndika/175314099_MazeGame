/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.awt.Color;
import java.awt.Graphics;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JPanel;
import sun.util.logging.PlatformLogger;

/**
 *
 * @author windows10
 */
public class Peta extends JPanel {
    private boolean complated = false;
    private String map1 = "";
    private ArrayList tembok = new ArrayList();
    private ArrayList finish = new ArrayList();
    private ArrayList map = new ArrayList();
    private ArrayList objekKolom = new ArrayList();
    private Pemain human;
    private final char TEMBOK = '#';
    private final char FINISH = 'o';
    private final char HUMAN = '@';
    private final char KOSONG = '.';
    private final char N = '\n';
    private int lebar = 0;
    private int tinggi = 0;
    private int jarak = 50;
    
    private File peta = new File("/Maze.txt");
    private ArrayList semuaPerintah = new ArrayList();
    
    public Peta(){
        
    }
    
    public void setPeta(File file){
        try {
            if (file != null) {
                FileInputStream input = new FileInputStream(file);
                int posisiX = 0;
                int posisiY = 0;
                Tembok wall;
                Finish fin;
                int data;
                int kolom = 0;
                while((data = input.read()) != -1){
                    char item = (char) data;
                    if (item == N) {
                        posisiY += jarak;
                        if (lebar < posisiX) {
                            lebar = posisiX;
                        }
                        kolom = posisiX;
                        objekKolom.add(kolom);
                        kolom = 0;
                        posisiX = 0;
                    }else if (item == TEMBOK) {
                        wall = new Tembok(posisiX, posisiY);
                        tembok.add(wall);
                        posisiX += jarak;
                    }else if (item == FINISH) {
                        fin = new Finish(posisiX, posisiY);
                        finish.add(fin);
                        posisiX += jarak;
                    }else if (item == HUMAN) {
                        human = new Pemain(posisiX, posisiY);
                        posisiX += jarak;
                    }else if (item == KOSONG) {
                        posisiX += jarak;
                    }
                    tinggi = posisiY;
                }
            }
        } catch (IOException ex) {
            Logger.getLogger(Peta.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void paintComponent(Graphics g){
        super.paintComponent(g);
        g.setColor(new Color(255, 255, 255));
        g.fillRect(0, 0, this.getLebar(), this.getTinggi());
        map.addAll(tembok);
        map.addAll(finish);
        map.add(human);
        for (int i = 0; i < map.size(); i++) {
            if (map.get(i) != null) {
                Pixel item = (Pixel) map.get(i);
                g.drawImage(item.getImage(), item.getPosisiX(), item.getPosisiY(), this);
            }
            
            
        }
        
    }
    
    public int getLebar(){
        return this.lebar;
    }
    
    public int getTinggi(){
        return this.tinggi;
    }
    
    
    
}
