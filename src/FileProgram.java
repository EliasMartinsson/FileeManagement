import java.io.*;
import java.io.File;
import java.util.Arrays;
import java.util.Collections;
import java.util.Scanner;
import java.io.IOException;
import java.io.FileNotFoundException;
import java.io.FileWriter;

public class FileProgram {
    Scanner scanner = new Scanner(System.in);

    File file;

    String fileName;
    public void runProgram(){

        int input;
        int answer;
        System.out.println("1. Write to a file");
        System.out.println("2. List files ");
        System.out.println("3. Save and sort stuff");
        System.out.println("4. Find stuff");
        System.out.println("5. Create a file");

        switch (integerInput(1 , 5)){
            case 1:
                System.out.println("Enter 1 to write over file, 2 to start from the end");
                input = integerInput(1 , 2);
                if(input == 1){
                    fileWriter(false,askForFileName(), stringInput());
                }
                else if(input == 2){
                    fileWriter(true,askForFileName(), stringInput());
                }
                break;
            case 2:
                System.out.println("List all files in a map or list all files of a type? 1 or 2");
                input = integerInput(1 , 2);
                if(input == 1){
                    System.out.println("Path to directory? ");
                    String directory = scanner.next();
                    listFiles(directory);
                }
                else if(input == 2){
                    System.out.println("Path to directory? ");
                    String directory = scanner.next();
                    System.out.println("Filetype? ");
                    String fileType = scanner.next();
                    listFilesOfType(directory, fileType);
                }
                break;
            case 3:
                int numberOf = 0;
                System.out.println("How many people do you want to store in a list?");
                numberOf = integerInput(0, Integer.MAX_VALUE );
                String people[] = new String[numberOf];
                for(int i = 0; i < numberOf; i++){
                    System.out.println("Person " + "nr " + (i+1) + " name? ");
                    people[i] = scanner.next();
                }
                try {
                    fileName = askForFileName();
                    FileWriter writer = new FileWriter(fileName, true);
                    for(int i = 0; i < numberOf; i++){
                        writer.write(people[i] +"\n");
                    }
                    writer.close();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                System.out.println("Do you want to sort the people? 1 for yes or 2 for no");
                input = integerInput(1, 2);
                if(input == 1){
                    Scanner scan = new Scanner(fileName);
                    while (scan.hasNextLine()) {
                        int i = 0;
                        String data = scan.next();
                        people[i] = data;
                        i++;
                    }
                    Arrays.sort(people);
                    for(int i = 0; i < people.length; i++){
                        System.out.println(people[i]);
                    }
                }
                else{
                    break;
                }
                break;
            case 4:
                System.out.println("1. Find out if its possible to read from a file.");
                System.out.println("2. Find the longest word in the file words.txt");
                System.out.println("3. Find the most common letter in a file ");
                System.out.println("4. Find all mailadresses on a website.");
                input = integerInput(1, 4);
                if(input == 1){
                    fileCheck();
                }
                else if(input == 2){
                    String longestWord = "";
                    try {
                        File file = new File("words.txt");
                        Scanner wordscan = new Scanner(file);

                        while(wordscan.hasNextLine()){
                            String data = wordscan.next();
                            if(data.length() > longestWord.length()){
                                longestWord = data;
                            }
                        }
                        System.out.println("The longest word is: " + longestWord);
                        wordscan.close();
                    } catch (FileNotFoundException e) {
                        System.out.println("File not found!");
                    }
                }
                else if(input == 3){
                }
                break;
            case 5:
                createFile();
                break;
        }
    }

    //Creates a file
    public void createFile() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Filename? ");
        fileName = scanner.nextLine();
        File myFile = new File(fileName);

        // Try to create a file
        try {
            //If the file does not exist it will be created
            if (myFile.createNewFile()) {
                System.out.println("file created");
            } else {
                System.out.println("file already exists");
            }
        } catch (IOException e) {
            System.out.println("NOPE!");
            e.printStackTrace();
        }
    }

    //Returns user input in the form of integers
    public static int integerInput(int lowerLimit, int upperLimit ){ //Limits the answer range
        Scanner scanner = new Scanner(System.in);
        int input;
        while (true) {
            try {
                System.out.println("Pick between presented alternatives, " + lowerLimit + " - " + upperLimit + "\n");
                input = scanner.nextInt();
                    if(input < lowerLimit || input > upperLimit){
                    System.out.println("Please enter an integer between " + lowerLimit + " - " + upperLimit + "\n");
                }
                    else{
                        break;
                    }
            }
            catch (Exception e) {
                System.out.println("Error!");
            }
        }
        return input;
    }

//Returns user input in form of text
    public static String stringInput(){
        Scanner scanner = new Scanner(System.in);
        String text = "";
        System.out.println("Enter text: ");
        text = scanner.next();
        return text;
    }
//Returns filenames in Strings
    public static String askForFileName(){
        Scanner scanner = new Scanner(System.in);
        System.out.println("Filename? ");
        String enteredFileName = scanner.next();
        return enteredFileName;
    }
//Method to write in files
    public void fileWriter(boolean append, String fileName, String text ){
        try {
            FileWriter writer = new FileWriter(fileName, append);
            writer.write(text);
            //Stops writing
            writer.close();
            System.out.println("\nSuccess\n");

        } catch (IOException e) { //In case file is missing or other errors
            throw new RuntimeException(e);
        }
    }
    //Checks if the file is readable, writable or both
    public void fileCheck() {
        Scanner scan = new Scanner(System.in);
        System.out.println("Filename? ");
        fileName =  scan.next();
        file = new File(fileName); //Creates an instance of the file
        //Gets filePath + separator of directories and the filename
        String filePath = (new File("")).getAbsolutePath() + File.separator + fileName;
           if (file.canRead() && file.canWrite()) {
            System.out.println("The file is readable and writable.");
        } else if (file.canRead()) {
            System.out.println("The file is only readable.");
        } else if (file.canWrite()) {
            System.out.println("The file is only writable.");
        } else {
            System.out.println("The file is not readable or writable.");
        }
    }

    //Lists all files in a given directory
    static void listFiles(String directory) {
        File dir = new File(directory);
        File[] files = dir.listFiles();
        //Goes through and prints all the directories
        for (File file : files) {
            if (file.isFile()) {
                System.out.println(file.getName());
            }
        }
    }
    //Lists all files of given type in a given directory
    public static void listFilesOfType(String directory, String fileType) {
        File dir = new File(directory);
        File[] files = dir.listFiles();
        //Goes through and prints directories while checking if its the given filetype
        for (File file : files) {
            if (file.isFile() && file.getName().endsWith(fileType)) {
                System.out.println(file.getName());
            }
        }
    }
}
