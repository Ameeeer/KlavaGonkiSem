package com.sem.logic;

import java.awt.event.KeyEvent;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Random;
import java.util.Scanner;

public class KeyLogic {

    public String text = "";

    public KeyLogic(){
        Random random = new Random();
        int[] textNumbers = {1,2};
        int num = random.nextInt(textNumbers.length);
        try {
            Scanner scannerFile = new Scanner(new File("src/main/resources/text_"+textNumbers[num]+".txt"));
            while (scannerFile.hasNextLine()){
                text += scannerFile.nextLine();
            }
//            KeyListener keyListener = new KeyListener();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
}
