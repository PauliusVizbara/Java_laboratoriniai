/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Lab1_B;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JFrame;
import studijosKTU.ScreenKTU;
import java.util.concurrent.TimeUnit;

/**
 *
 * @author Paulius
 */
public class ToH extends ScreenKTU {

    private final int DISK_HEIGHT = 3;
    private final int ROD_HEIGHT = 23;
    private final int ROD_WIDTH = 57;
    private final int DISK_COUNT = 7;
    private ArrayList<Rod> rods = new ArrayList<Rod>();
    private Disk selectedDisk = null;
    private long startTime;
    private int moveCount;

    public ToH() {
        super(5, 5, 125, 240);
    }

    public void start() {
        clearAll(Color.black);
        moveCount = 0;
        long startTime = System.currentTimeMillis();

        setColors(Color.white, Color.green);
        g.setFont(new Font("TimesRoman", Font.PLAIN, 25));
        fillRect(30, 30, 0, 0);
        String countText = "Move count: ";
        char[] c = countText.toCharArray();

        g.drawChars(c, 0, c.length, 10, 30);

        /*setColors(Color.white, Color.green);
        setFontColor(Color.red);
        g.setFont(new Font("TimesRoman", Font.PLAIN, 25)); 
        fillRect(30, 30, 0, 0);
        char[] c = { 'W', 'a', 't', 'c', 'h', ' ', 'i', 't', ' ', 'D', 'u', 'k', 'e', '!' };
     
        g.drawChars(c, 0, c.length, 10, 30);
        refresh();*/
        // Rod building
        Rod rod1 = new Rod(20 + ROD_WIDTH / 2, false);
        Rod rod2 = new Rod(20 + ROD_WIDTH + 15 + ROD_WIDTH / 2, true);
        Rod rod3 = new Rod(20 + ROD_WIDTH + 15 + ROD_WIDTH / 2 + ROD_WIDTH + 15, false);

        rod1.setBounds(118, 200, 375, 620);
        rod2.setBounds(474, 200, 732, 620);
        rod3.setBounds(833, 200, 1100, 620);

        rods.add(rod1);
        rods.add(rod2);
        rods.add(rod3);
        rods.forEach((rod) -> {
            drawRod(rod);
        });

        for (int i = DISK_COUNT * 2 + 1; i >= 3; i -= 2) {
            addDiskToRod(new Disk(i), rod2);
        }

        refresh();
    }

    void UpdateMoveCount() {

        g.setFont(new Font("TimesRoman", Font.PLAIN, 25));

        setColors(Color.white, Color.green);
        fillRect(1, 30, 0, 0);

        StringBuilder sb = new StringBuilder();
        sb.append("");
        sb.append(moveCount);
        String strI = sb.toString();
        char[] c = strI.toCharArray();

        setColors(Color.WHITE, Color.green);
        g.drawChars(c, 0, c.length, 150, 30);
        refresh();

    }

    @Override
    public void mouseReleased(MouseEvent e) {

        long start = startTime;
        long timeElapsed = System.currentTimeMillis() - start;
        System.out.println(timeElapsed);
        int mouseX = e.getX();
        int mouseY = e.getY();

        //System.out.println(rods.get(0).disks.size());
        for (Rod rod : rods) {
            if (rod.hasBeenPressed(mouseX, mouseY)) {
                // System.out.println(rod.disks.size());
                if (selectedDisk == null && rod.disks.size() != 0) {
                    //Gaunam virsutini diska
                    Disk disk = rod.disks.get(rod.disks.size() - 1);
                    selectedDisk = new Disk(disk.getPositionX(), disk.getPositionY(), disk.getWidth());
                    selectedDisk.setPositionY(79);
                    //System.out.println(selectedDisk.positionY);
                    //isimam virsutini diska, perpiesiam ji virsuj
                    setColors(Color.yellow, Color.yellow);
                    fillRect(79, selectedDisk.getPositionX(), 3, disk.getWidth() * 3);
                    //Istrinam diska is rod
                    rod.currentDiskHeight += 3;

                    // A
                    if (rod.disks.size() == 1) {
                        //System.out.println("hey");
                        fillRect(disk.getPositionY(), disk.getPositionX(), 3, disk.getWidth() * 3, Color.black);
                        rod.disks.clear();
                    }
                    rod.disks.remove(disk);
                    // Uzpiesiam pasirinkta diska
                    fillRect(disk.getPositionY(), disk.getPositionX(), 3, disk.getWidth() * 3, Color.black);
                    //perpiesiam stulpa
                    drawRod(rod);
                    //perpiesiam d
                    for (Disk d : rod.disks) {
                        drawDisk(d);
                    }

                    refresh();
                } else if (selectedDisk != null) {
                    resetMoveCountDisplay();
                    moveCount++;
                    setColors(Color.white, Color.white);
                    UpdateMoveCount();

                    if (rod.disks.size() == 0 || rod.disks.get(rod.disks.size() - 1).getWidth() > selectedDisk.getWidth()) {
                        fillRect(selectedDisk.getPositionY(), selectedDisk.getPositionX(), 3, selectedDisk.getWidth() * 3, Color.black);
                        addDiskToRod(selectedDisk, rod);
                        refresh();
                        selectedDisk = null;
                        displayWinText();
                        if (rod.disks.size() == DISK_COUNT && rod.isCentre == false) {
                            System.out.println("Laimėjai");
                        }
                    }
                }
            }

        }

    }

    void displayWinText(){
        g.setFont(new Font("TimesRoman", Font.PLAIN, 40));

        setColors(Color.GREEN, Color.green);
        fillRect(1, 30, 0, 0);

        StringBuilder sb = new StringBuilder();
        sb.append("");
        sb.append("LAIMĖJOTE");
        String strI = sb.toString();
        char[] c = strI.toCharArray();

        setColors(Color.GREEN, Color.green);
        g.drawChars(c, 0, c.length, 475, 150);
        refresh();
    }
    
    void resetMoveCountDisplay() {
        setColors(Color.black, Color.green);
        fillRect(1, 30, 10, 10);

    }

    void drawDisk(Disk disk) {
        setColors(Color.red, Color.yellow);
        fillRect(disk.getPositionY(), disk.getPositionX(), 3, disk.getWidth() * 3);
    }

    void addDiskToRod(Disk disk, Rod rod) {
        setColors(Color.red, disk.getColor());
        fillRect(rod.currentDiskHeight, rod.centreX - (disk.getWidth()) * 3 / 2, DISK_HEIGHT, disk.getWidth() * DISK_HEIGHT);
        disk.setPositionX(rod.centreX - (disk.getWidth()) * 3 / 2);
        disk.setPositionY(rod.currentDiskHeight);
        rod.disks.add(disk);
        rod.currentDiskHeight -= DISK_HEIGHT;
    }

    void drawRod(Rod rod) {
        setColors(Color.white, Color.white);
        int centre = rod.centreX;
        fillRect(120, centre - ROD_WIDTH / 2, DISK_HEIGHT, ROD_WIDTH);
        fillRect(120 - ROD_HEIGHT, centre, ROD_HEIGHT, 1);

    }

    public static void main(String[] args) {
        ToH tohGame = new ToH();

        new Thread(tohGame::start).start();

    }
}
