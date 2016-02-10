package homework1;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;
// @author Kevin Li

public class Homework1 {

    static ArrayList<Integer> keyArray = new ArrayList<Integer>();
    static ArrayList<Integer> messageAscii = new ArrayList<Integer>();

    public static Boolean checkAlpha(String text) {
        //Checks if the string contains all letters
        return text.matches("[a-zA-Z]+");
    }

    public static void fillArray(String key) {
        //Convert to ascii and subtract 97 to get range 0-25
        for (char i : key.toCharArray()) {
            if ((int) i >= 97 && (int) i <= 122) {
                keyArray.add((int) (i - 97));
            } else {
                keyArray.add((int) i);
            }
        }
    }

    public static void encrypt(String inName, String outName) throws FileNotFoundException, IOException {
        int counter = 0;
        int holder;
        Scanner fileReader = new Scanner(new File(inName));
        if (fileReader.hasNextLine()) {
            String message = fileReader.nextLine().toLowerCase();
            while (fileReader.hasNextLine()) {
                message += fileReader.nextLine().toLowerCase();
            }

            for (char i : message.toCharArray()) {
                holder = (int) i;  // set holder to be first char ascii
                if ((int) i >= 97 && (int) i <= 122) {
                    holder = (int) i + keyArray.get(counter);
                    if (holder > 122) {
                        holder -= 25;     // If ascii past 'z'go back to 'a'
                    }
                    counter++;
                    //reset loop for the key array if message is longer than key
                    if (counter >= keyArray.size()) {
                        counter = 0;
                    }
                }
                messageAscii.add(holder);
            }
            writeToFile(outName);
        } else {
            System.out.println("File was Empty. Exiting");
        }
        fileReader.close();
    }

    public static void writeToFile(String fileName) throws IOException {
        FileWriter writeFile = new FileWriter(fileName);
        for (int s : messageAscii) {
            writeFile.write((char) s);
        }
        writeFile.close();
    }

    public static void decrypt(String inName, String outName) throws FileNotFoundException, IOException {
        int counter = 0;
        int holder;
        Scanner fileReader = new Scanner(new File(inName));
        if (fileReader.hasNextLine()) {
            String message = fileReader.nextLine().toLowerCase();
            while (fileReader.hasNextLine()) {
                message += fileReader.nextLine().toLowerCase();
            }    
            for (char i : message.toCharArray()) {
                holder = (int) i;  // set holder to be first char ascii
                if ((int) i >= 97 && (int) i <= 122) {
                    holder = (int) i - keyArray.get(counter);
                    if (holder < 97 ) {
                        holder += 25;     // If ascii before 'a'go back to 'z'
                    }
                    counter++;
                    //reset loop for the key array if message is longer than key
                    if (counter >= keyArray.size()) {
                        counter = 0;
                    }
                }
                messageAscii.add(holder);
            }
            writeToFile(outName);
        } else {
            System.out.println("File was Empty. Exiting");
        }
        fileReader.close();

    }
    static String getFilename() {
        Scanner sin = new Scanner(System.in);
        String filename = sin.nextLine();
        while (!new File(filename).canRead()) {
            System.out.println("Filename does not exist in directory. Try again:");
            filename = sin.nextLine();
        }
        return filename;
    }

    public static void userDisplay() throws FileNotFoundException, IOException {
        Scanner input = new Scanner(System.in);
        System.out.println("What would you like to do? \n "
                + "1 - Encrypt a file \n 2 - Decrypt a file \n"
                + " 0 - Exit \n");
        int i = input.nextInt();
        while (i < 0 || i > 2) {
            System.out.println("Invalid Input, Try again");
            i = input.nextInt();
        }
        if (i == 1 || i == 2) {
            Scanner newInput = new Scanner(System.in);
            System.out.println("Enter the key (string or character) \n");
            String key = newInput.nextLine();

            while (!checkAlpha(key)) {
                System.out.println("Key was invalid. Enter the key (string or character) \n");
                key = newInput.nextLine();
            }
            System.out.print("Please enter the name of the input file: ");
            String inFile = getFilename();
            System.out.print("Please enter the name of the output file: ");
            String outFile = getFilename();
            fillArray(key);
            switch (i) {
                case 1:
                    encrypt(inFile, outFile);
                    break;
                case 2:
                    decrypt(inFile, outFile);
                    break;
            }
            newInput.close();
        } else {
            System.out.println("Proceeding to exit the program");
          
        }
        input.close();
    }

    public static void main(String[] args) throws FileNotFoundException, IOException {
        // TODO code application logic here
        /*
        ArrayList <Integer>keyArray = new ArrayList<Integer>();
        String x = "a z !";
        for(char i:x.toCharArray()){
            if((int)i >= 97 && (int)i <= 122){
                keyArray.add((int)(i-97));
            }
            
             else{
                keyArray.add((int)i);
        
                }
        }
        for(int i:keyArray){
            System.out.println(i);
        }
        System.out.println(String.valueOf((char)(65)));   
         */
                 userDisplay();
    }
}
