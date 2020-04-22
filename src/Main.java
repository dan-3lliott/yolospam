import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.io.*;
import java.util.InputMismatchException;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

public class Main {
    public static int textX;
    public static int textY;
    public static int buttonX;
    public static int buttonY;
    public static void menu() {
        Scanner sc = new Scanner(System.in);
        System.out.println("YOLOSPAM V1.0 - DANIEL ELLIOTT 2020");
        System.out.println("-----------------------------------");
        System.out.println("1: SPAM | 2: CALIBRATE | 3: EXIT");
        System.out.print("user@y͟o͟l͟o͟s͟p͟a͟m͟> ");
        try {
            int choice = sc.nextInt();
            switch (choice) {
                case 1:
                    spam();
                    break;
                case 2:
                    calibrate();
                    break;
                case 3:
                    save();
                    System.exit(0);
                    break;
                default:
                    System.out.println("INVALID OPTION");
                    break;
            }
        }
        catch (InputMismatchException ime) {
            System.out.println("INVALID OPTION");
        }
    }
    public static void load() {
        try {
            BufferedReader br = new BufferedReader(new FileReader("coords.txt"));
            textX = Integer.parseInt(br.readLine());
            textY = Integer.parseInt(br.readLine());
            buttonX = Integer.parseInt(br.readLine());
            buttonY = Integer.parseInt(br.readLine());
            br.close();
            System.out.println("SAVE FILE LOADED SUCCESSFULLY");
        }
        catch (Exception e) {
            System.out.println(e.getMessage());
            System.out.println("ERROR READING SAVE FILE");
        }
    }
    public static void save() {
        try {
            BufferedWriter bw = new BufferedWriter(new FileWriter("coords.txt"));
            bw.write(String.valueOf(textX));
            bw.newLine();
            bw.write(String.valueOf(textY));
            bw.newLine();
            bw.write(String.valueOf(buttonX));
            bw.newLine();
            bw.write(String.valueOf(buttonY));
            bw.close();
            System.out.println("SAVE FILE WRITTEN SUCCESSFULLY");
        }
        catch (Exception e) {
            System.out.println(e.getMessage());
            System.out.println("ERROR WRITING TO SAVE FILE");
        }
    }
    public static void calibrate() {
        //calibrate input field
        System.out.println("HOVER MOUSE BUTTON OVER THE 'Type your message here' INPUT FIELD WITHIN 5 SECONDS. LEAVE IT THERE FOR 5 SECONDS");
        try {
            //wait 5 seconds for hover
            TimeUnit.SECONDS.sleep(5);
        }
        catch (Exception e) {
            System.out.println(e.getMessage());
        }
        Point textPoint = MouseInfo.getPointerInfo().getLocation();
        textX = textPoint.x;
        textY = textPoint.y;
        System.out.println("INPUT FIELD RECORDED AT (" + textX + ", " + textY + ")");
        System.out.print("PRESS ENTER TO CONTINUE");
        try {
            System.in.read();
        }
        catch (Exception e) {
            System.out.println(e.getMessage());
        }
        //calibrate submit button
        System.out.println("HOVER MOUSE BUTTON OVER THE BLUE 'Send anonymously' BUTTON WITHIN 5 SECONDS. LEAVE IT THERE FOR 5 SECONDS");
        try {
            //wait 5 seconds for hover
            TimeUnit.SECONDS.sleep(5);
        }
        catch (Exception e) {
            System.out.println(e.getMessage());
        }
        Point buttonPoint = MouseInfo.getPointerInfo().getLocation();
        buttonX = buttonPoint.x;
        buttonY = buttonPoint.y;
        System.out.println("SUBMIT BUTTON RECORDED AT (" + buttonX + ", " + buttonY + ")");
        System.out.println("CALIBRATION COMPLETE");
    }
    public static String[] getText() {
        Scanner sc = new Scanner(System.in);
        System.out.println("ENTER TEXT TO SPAM");
        System.out.print("user@y͟o͟l͟o͟s͟p͟a͟m͟> ");
        String text = sc.nextLine();
        System.out.println("ENTER # OF TIMES TO SPAM");
        System.out.print("user@y͟o͟l͟o͟s͟p͟a͟m͟> ");
        String count = sc.next();
        return new String[]{text, count};
    }
    public static void spam() {
        String[] get = getText();
        System.out.print("SPAMMING '" + get[0] + "' " + get[1] + " TIMES, PRESS ENTER TO CONTINUE");
        try {
            System.in.read();
        }
        catch (Exception e) {
            System.out.println(e.getMessage());
        }
        System.out.println("BEGINNING SPAM, SWITCH TO BROWSER WINDOW WITHIN 5 SECONDS");
        try {
            //wait 5 seconds for window switch
            TimeUnit.SECONDS.sleep(5);
            Robot r = new Robot();
            for (int count = 1; count <= Integer.parseInt(get[1]); count++) {
                //move mouse to text field,  click, and type text
                r.mouseMove(textX, textY);
                TimeUnit.MILLISECONDS.sleep(50);
                r.mousePress(InputEvent.BUTTON1_DOWN_MASK);
                r.mouseRelease(InputEvent.BUTTON1_DOWN_MASK);
                TimeUnit.MILLISECONDS.sleep(50);
                type(get[0], r);
                TimeUnit.MILLISECONDS.sleep(50);
                //move mouse to button and click
                r.mouseMove(buttonX, buttonY);
                TimeUnit.MILLISECONDS.sleep(50);
                r.mousePress(InputEvent.BUTTON1_DOWN_MASK);
                r.mouseRelease(InputEvent.BUTTON1_DOWN_MASK);
                //wait for form to submit
                TimeUnit.SECONDS.sleep(2);
                //refresh page
                r.keyPress(KeyEvent.VK_F5);
                r.keyRelease(KeyEvent.VK_F5);
                TimeUnit.SECONDS.sleep(2);
                System.out.println(count + " / " + get[1]);
            }
            System.out.println("EXECUTION COMPLETED");
        }
        catch (Exception e) {
            System.out.println(e.getMessage());
            System.out.println("EXECUTION TERMINATED PREMATURELY");
        }
    }
    public static void type(String text, Robot robot) {
        //copy text to clipboard
        StringSelection stringSelection = new StringSelection(text);
        Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
        clipboard.setContents(stringSelection, stringSelection);
        //paste text
        robot.keyPress(KeyEvent.VK_CONTROL);
        robot.keyPress(KeyEvent.VK_V);
        robot.keyRelease(KeyEvent.VK_V);
        robot.keyRelease(KeyEvent.VK_CONTROL);
    }
    public static void main(String[] args) {
        load();
        while (true) {
            menu();
            System.out.println();
        }
    }
}
