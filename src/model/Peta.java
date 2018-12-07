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
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

/**
 *
 * @author Aweng
 */
public class Peta extends JPanel {

    private boolean complated = false;
    private String map1 = "";
    private ArrayList tembok = new ArrayList();
    private Finish finish;
    private ArrayList map = new ArrayList();
    private ArrayList objekKolom = new ArrayList();
    private Pemain human;
    private final char TEMBOK = '#';
    private final char FINISH = 'o';
    private final char HUMAN = '@';
    private final char KOSONG = '.';
    private final char N = 'n';
    private int lebar = 0;
    private int tinggi = 0;
    private int jarak = 20;

    private File peta;
    private ArrayList semuaPerintah = new ArrayList();

    public Peta(File file) {
        setPeta(file);
    }

    public void setPeta(File file) {
        try {
            if (file != null) {
                FileInputStream input = new FileInputStream(file);
                peta = file;
                int posisiX = 0;
                int posisiY = 0;
                Tembok wall;
                Finish a;
                int data;
                while ((data = input.read()) != -1) {
                    char item = (char) data;
                    if (item == N) {
                        posisiY += jarak;
                        lebar = posisiX;
                        posisiX = 0;
                    } else if (item == TEMBOK) {
                        wall = new Tembok(posisiX, posisiY);
                        tembok.add(wall);
                        posisiX += jarak;
                    } else if (item == FINISH) {
                        a = new Finish(posisiX, posisiY);
                        finish = new Finish(posisiX, posisiY);
                        posisiX += jarak;
                    } else if (item == HUMAN) {
                        human = new Pemain(posisiX, posisiY);
                        posisiX += jarak;
                    } else if (item == KOSONG) {
                        posisiX += jarak;
                    }
                    tinggi = posisiY;
                }
            }

        } catch (IOException ex) {
            Logger.getLogger(Peta.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);	   // Hapus background
        // Tempat Gambar:
        g.setColor(new Color(255, 255, 255));//set panel warna putih
        g.fillRect(0, 0, this.getLebar(), this.getTinggi());// set tinggi lebar sesuai konfigurasi
        map.addAll(tembok);
        map.add(finish);
        map.add(human);
        for (int i = 0; i < map.size(); i++) {
            if (map.get(i) != null) {
                Pixel item = (Pixel) map.get(i);//map diterjemahkan dalam kelas pixel.
                g.drawImage(item.getImage(), item.getPosisiX(), item.getPosisiY(), this);//proses gambar di panel
            }
        }
    }

    public int getLebar() {
        return this.lebar;
    }

    public int getTinggi() {
        return this.tinggi;
    }

    public void PerintahGerak(String input) {
        String in[] = input.split(" ");
        if (in.length > 2) {
            JOptionPane.showMessageDialog(null, "Jumlah kata lebih dari 2");
        } else if (in.length == 2) {
            if (in[0].matches("[udrlz]")) {
                semuaPerintah.add(input);
                if (in[0].equalsIgnoreCase("u")) {
                    for (int i = 0; i < Integer.parseInt(String.valueOf(in[1])); i++) {
                        if (cekObjekNabrakTembok(human, "u")) {
                            return;
                        } 
                        if (cekPemainTembok("u")) {
                            return;
                        } else {
                            human.Gerak(0, -jarak);
                            repaint();
                        }

                    }
                } else if (in[0].equalsIgnoreCase("d")) {
                    for (int i = 0; i < Integer.parseInt(String.valueOf(in[1])); i++) {
                        if (cekObjekNabrakTembok(human, "d")) {
                            return;
                        }  
                        if (cekPemainTembok("d")) {
                            return;
                        } else {
                            human.Gerak(0, jarak);
                            repaint();
                        }
                    }
                } else if (in[0].equalsIgnoreCase("r")) {
                    for (int i = 0; i < Integer.parseInt(String.valueOf(in[1])); i++) {
                        if (cekObjekNabrakTembok(human, "r")) {
                            return;
                        } 
                        if (cekPemainTembok("r")) {
                            return;
                        } else {
                            human.Gerak(jarak, 0);
                            repaint();
                        }
                    }
                } else if (in[0].equalsIgnoreCase("l")) {
                    for (int i = 0; i < Integer.parseInt(String.valueOf(in[1])); i++) {
                        if (cekObjekNabrakTembok(human, "l")) {
                            return;
                        } 
                        if (cekPemainTembok("l")) {
                            return;
                        } else {
                            human.Gerak(-jarak, 0);
                            repaint();
                        }
                    }
                } else if (in[0].equalsIgnoreCase("z")) {
//                    return "undo " + in[1];
                } else {
                    JOptionPane.showMessageDialog(null, "Kata Tidak Dikenal");
                }
            } else {
                JOptionPane.showMessageDialog(null, "Kata Tidak Dikenal");
            }
        } else {
            JOptionPane.showMessageDialog(null, "Jumlah kata hanya satu");
        }
    }

    private boolean cekObjekNabrakTembok(Pixel pemain, String input) {
        boolean bantu = false;
        if (input.equalsIgnoreCase("l")) {
            for (int i = 0; i < tembok.size(); i++) {
                Tembok wall = (Tembok) tembok.get(i);//ambil posisi tembok
                if (pemain.PosisiKiriObjek(wall)) {
                    bantu = true;
                    break;
                }
//                else if (pemain.PosisiKiriObjek(finish)) {
//                    isCompleted();
//                }
            }

        } else if (input.equalsIgnoreCase("r")) {
            for (int i = 0; i < tembok.size(); i++) {
                Tembok wall = (Tembok) tembok.get(i);//ambil posisi tembok
                if (pemain.PosisiKananObjek(wall)) {
                    bantu = true;
                    break;
                }
//                else if(human.PosisiKananObjek(finish)){
//                    isCompleted();
//                }
            }
        } else if (input.equalsIgnoreCase("u")) {
            for (int i = 0; i < tembok.size(); i++) {
                Tembok wall = (Tembok) tembok.get(i);//ambil posisi tembok
                if (pemain.PosisiAtasObjek(wall)) {
                    bantu = true;
                    break;
                }
//                else if(pemain.PosisiAtasObjek(finish)){
//                    isCompleted();
//                }
            }
        } else if (input.equalsIgnoreCase("d")) {
            for (int i = 0; i < tembok.size(); i++) {
                Tembok wall = (Tembok) tembok.get(i);//ambil posisi tembok
                if (pemain.PosisiBawahObjek(wall)) {
                    bantu = true;
                    break;
                }
//                else if(pemain.PosisiBawahObjek(finish)){
//                    isCompleted();
//                }
            }
        }
        return bantu;//default return false
    }
    
    private boolean cekPemainTembok(String input) {
        boolean bantu = false;
        if (input.equalsIgnoreCase("l")) {
                if (human.PosisiKiriObjek(finish)) {//cek apakah pemain sebelah kiri bola ke 
                        isCompleted();
                        bantu = true;
                }
            }
             else if (input.equalsIgnoreCase("r")) {
                if (human.PosisiKananObjek(finish)) {//cek apakah pemain sebelah kanan bola ke i 
                        isCompleted();
                        bantu = true;
               
              }        
        } else if (input.equalsIgnoreCase("u")) {
                if (human.PosisiAtasObjek(finish)) {//cek apakah bola 1 di atas pemain
                        isCompleted();
                        bantu = true;
            }
        } else if (input.equalsIgnoreCase("d")) {
                if (human.PosisiBawahObjek(finish)) {//cek apakah bola 1 di bawah pemain
                        isCompleted();
                        bantu = true;
            }
        }
        return bantu;
    }

    public void isCompleted() {
        Pixel pixel = new Pixel(human.getPosisiX(), human.getPosisiY());
        if (pixel.equals(finish)) {
            JOptionPane.showMessageDialog(this, "Winner");
        }
    }

    public void restartLevel() {
        semuaPerintah.clear();//hapus semua perintah yang tersimpan
        tembok.clear();//hapus tembok
        map.clear();//hapus map
        setPeta(peta);//set ulang gambar peta
        repaint();//gambar ulang
    }

    public String getTeksPerintah() {
        String bantu = "";
        for (int i = 0; i < semuaPerintah.size(); i++) {
            bantu = bantu + semuaPerintah.get(i) + " ";
        }
        return bantu;
    }
     public int getPoin() {
        int bantu = semuaPerintah.size();
         if (bantu<20) {
             JOptionPane.showMessageDialog(this, map);
         }
        return bantu;//085245843743
    }
     
     public String Perintah() {
        String bantu = " ";
        for (int i = 0; i < semuaPerintah.size(); i++) {
            bantu = bantu + semuaPerintah.get(i) + " ";
        }
        return bantu;
    }
     
     public void simpan() {
        boolean isi = true;
        int x = 0;
        int poisiX = 0;
        int posisiY = 0;
        int fileZise = (int) peta.length();
        for (int i = 0; i < fileZise; i++) {
            if (isi) {
                for (int j = 0; j < tembok.size(); j++) {
                    model.Tembok wall = (model.Tembok) tembok.get(i);
                    if (wall.getPosisiX() == poisiX && wall.getPosisiY() == posisiY) {
                        map1 = map1 + "#";
                        isi = true;
                        poisiX += jarak;
                        break;
                    } else {
                        isi = false;
                    }
                }
            }
            if (isi = false) {
                if (finish.getPosisiX() == poisiX && finish.getPosisiY() == posisiY) {
                    map1 = map1 + "o";
                    isi = true;
                    poisiX += jarak;
                }
            }
            if (isi = false) {
                if (human.getPosisiX() == poisiX && human.getPosisiY() == posisiY) {
                    map1 = map1 + "@";
                    isi = true;
                    poisiX += jarak;
                }
            }
            
            if (isi == false && poisiX < (int) objekKolom.get(x)) {
                map1 = map1 + ".";
                isi = true;
                poisiX += jarak;
            }
            if (isi == false) {
                map1 = map1 + "n";
                map1 = map1 + System.lineSeparator();
                poisiX = 0;
                posisiY += jarak;
                fileZise = fileZise - 2;
                isi = true;
                x++;
            }
        }
    }
    
    public void saveNote(File file){
        FileOutputStream fos = null;
        try{
            fos = new FileOutputStream(file);
            byte[] array = map1.getBytes();
            fos.write(array);
            fos.close();
        }catch(FileNotFoundException ex){
            Logger.getLogger(model.Peta.class.getName()).log(Level.SEVERE, null, ex);
        }catch(IOException ex){
            Logger.getLogger(model.Peta.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
