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
import java.util.LinkedList;
import java.util.Stack;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

/**
 *
 * @author Aweng
 */
public class Peta extends JPanel {

    private boolean completed = false;
    private String map1 = "";
    private ArrayList tembok = new ArrayList();
    private Finish finish;
    private ArrayList map = new ArrayList();
    private ArrayList objekKolom = new ArrayList();
    private Pemain human;
    private LinkedList<String> undo = new LinkedList<>();
    private final char TEMBOK = '#';
    private final char FINISH = 'o';
    private final char HUMAN = '@';
    private final char KOSONG = '.';
    private final char N = 'n';
    private int lebar = 0;
    private int tinggi = 0;
    private int jarak = 20;
    private String isi;

    private File peta = new File("Image/Maze.txt");
    private ArrayList<String> semuaPerintah = new ArrayList();

    public Peta() {

    }

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
                Sel item = (Sel) map.get(i);//map diterjemahkan dalam kelas pixel.
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

    public String getMap1() {
        return map1;
    }

    public void setMap1(String map1) {
        this.map1 = map1;
    }

    public void PerintahGerak(String input) {
        String in[] = input.split("");
        if (in[0].equalsIgnoreCase("z") && in[1].matches("[123456789]")) {
            semuaPerintah.add(input);
            if (!undo.isEmpty()) {
                for (int index = Integer.parseInt(String.valueOf(in[1])); index > 0; index--) {
                    String x = undo.removeLast();
                    String un[] = x.split("");
                    if (un[0].equalsIgnoreCase("u")) {
                        for (int i = 0; i < Integer.parseInt(String.valueOf(un[1])); i++) {
                            if (cekTabrakTembok(human, "u")) {
                                return;
                            } else {
                                human.Gerak(0, jarak);
                                repaint();
                            }
                        }
                    } else if (un[0].equalsIgnoreCase("d")) {
                        for (int i = 0; i < Integer.parseInt(String.valueOf(un[1])); i++) {
                            if (cekTabrakTembok(human, "d")) {
                                return;
                            } else {
                                human.Gerak(0, -jarak);
                                repaint();
                            }
                        }
                    } else if (un[0].equalsIgnoreCase("r")) {
                        for (int i = 0; i < Integer.parseInt(String.valueOf(un[1])); i++) {
                            if (cekTabrakTembok(human, "r")) {
                                return;
                            } else {
                                human.Gerak(-jarak, 0);
                                repaint();
                            }
                        }
                    } else if (un[0].equalsIgnoreCase("l")) {
                        for (int i = 0; i < Integer.parseInt(String.valueOf(un[1])); i++) {
                            if (cekTabrakTembok(human, "l")) {
                                return;
                            } else {
                                human.Gerak(jarak, 0);
                                repaint();
                            }
                        }
                    }
                }
            } else {
                JOptionPane.showMessageDialog(null, "perintah mencapai batas");
            }
        } else if (in[0].matches("[udrl]") && in[1].matches("[123456789]") && in.length == 2) {
            undo.addLast(input);
            semuaPerintah.add(input);
            if (in[0].equalsIgnoreCase("u")) {
                for (int i = 0; i < Integer.parseInt(String.valueOf(in[1])); i++) {
                    if (cekTabrakTembok(human, "u")) {
                        return;
                    } else {
                        human.Gerak(0, -jarak);
                        isCompleted();
                        repaint();
                    }
                    if (completed) {
                        JOptionPane.showMessageDialog(this, "Winner");
                        this.restartLevel();
                        break;
                    }

                }
            } else if (in[0].equalsIgnoreCase("d")) {
                for (int i = 0; i < Integer.parseInt(String.valueOf(in[1])); i++) {
                    if (cekTabrakTembok(human, "d")) {
                        return;
                    } else {
                        human.Gerak(0, jarak);
                        isCompleted();
                        repaint();
                    }
                    if (completed) {
                        JOptionPane.showMessageDialog(this, "Winner");
                        this.restartLevel();
                        break;
                    }
                }
            } else if (in[0].equalsIgnoreCase("r")) {
                for (int i = 0; i < Integer.parseInt(String.valueOf(in[1])); i++) {
                    if (cekTabrakTembok(human, "r")) {
                        return;
                    } else {
                        human.Gerak(jarak, 0);
                        isCompleted();
                        repaint();
                    }
                    if (completed) {
                        JOptionPane.showMessageDialog(this, "Winner");
                        this.restartLevel();
                        break;
                    }
                }
            } else if (in[0].equalsIgnoreCase("l")) {
                for (int i = 0; i < Integer.parseInt(String.valueOf(in[1])); i++) {
                    if (cekTabrakTembok(human, "l")) {
                        return;
                    } else {
                        human.Gerak(-jarak, 0);
                        isCompleted();
                        repaint();
                    }
                    if (completed) {
                        JOptionPane.showMessageDialog(this, "Winner");
                        this.restartLevel();
                        break;
                    }
                }
            }
        } else {
            JOptionPane.showMessageDialog(null, "Input Tidak Valid");
        }
    }

    private boolean cekTabrakTembok(Sel pemain, String input) {
        boolean bantu = false;
        if (input.equalsIgnoreCase("l")) {
            for (int i = 0; i < tembok.size(); i++) {
                Tembok wall = (Tembok) tembok.get(i);//ambil posisi tembok
                if (pemain.PosisiKiriObjek(wall)) {
                    bantu = true;
                    break;
                }
            }

        } else if (input.equalsIgnoreCase("r")) {
            for (int i = 0; i < tembok.size(); i++) {
                Tembok wall = (Tembok) tembok.get(i);//ambil posisi tembok
                if (pemain.PosisiKananObjek(wall)) {
                    bantu = true;
                    break;
                }
            }
        } else if (input.equalsIgnoreCase("u")) {
            for (int i = 0; i < tembok.size(); i++) {
                Tembok wall = (Tembok) tembok.get(i);//ambil posisi tembok
                if (pemain.PosisiAtasObjek(wall)) {
                    bantu = true;
                    break;
                }
            }
        } else if (input.equalsIgnoreCase("d")) {
            for (int i = 0; i < tembok.size(); i++) {
                Tembok wall = (Tembok) tembok.get(i);//ambil posisi tembok
                if (pemain.PosisiBawahObjek(wall)) {
                    bantu = true;
                    break;
                }
            }
        }
        return bantu;//default return false
    }

    public void isCompleted() {
        if (human.getPosisiX() == finish.getPosisiX()) {
            if (human.getPosisiY() == finish.getPosisiY()) {
                completed = true;
            }
        }
    }

    public void restartLevel() {
        semuaPerintah.clear();//hapus semua perintah yang tersimpan
        tembok.clear();//hapus tembok
        map.clear();//hapus map
        completed = false;
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

    public String Perintah() {
        String bantu = " ";
        for (int i = 0; i < semuaPerintah.size(); i++) {
            bantu = bantu + semuaPerintah.get(i) + " ";
        }
        return bantu;
    }

    public void GerakAuto() {
        String[] pintas = {"r3", "d3", "r2", "d1"};
        for (int i = 0; i < pintas.length; i++) {
            PerintahGerak(pintas[i]);
        }
    }

    public void saveNote(File file) {
        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(file);
            for (int i = 0; i < this.semuaPerintah.size(); i++) {
                String data = this.semuaPerintah.get(i) + ",";
                fos.write(data.getBytes());
            }
            fos.close();
        } catch (FileNotFoundException ex) {
            Logger.getLogger(model.Peta.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(model.Peta.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void load(File file) throws IOException {
        FileInputStream fis = null;
        try {
            String hasilBaca = "";
            fis = new FileInputStream(file);
            int dataInt;

            while ((dataInt = fis.read()) != -1) {
                if ((char) dataInt == ',') {
                    this.semuaPerintah.add(hasilBaca);
                    hasilBaca = "";
                } else {
                    hasilBaca = hasilBaca + (char) dataInt;
                }
            }
        } catch (FileNotFoundException ex) {
            Logger.getLogger(Peta.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(Peta.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void save() {
        this.saveNote(new File("save.dat"));
    }

    public void loadData() {
        this.semuaPerintah.clear();
        try {
            Peta map = new Peta(peta);
            map.load(new File("save.dat"));
            for (int i = 0; i < map.semuaPerintah.size(); i++) {
                PerintahGerak(map.semuaPerintah.get(i));
            }
            this.saveNote(new File("save.dat"));
        } catch (IOException ex) {
            Logger.getLogger(Peta.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
