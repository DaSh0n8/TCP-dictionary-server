// Name : Brandon Vei Liang
// Surname : Lim
// Student id: 1430381

import java.io.*;

public class Word {

    public static String fileName = "dictionary.txt";
    public synchronized static void addWord(String name, String meaning){
        name = name.replaceAll("\\s+","");
        try {
            // Adding word and its meaning to dictionary
            BufferedWriter myWriter = new BufferedWriter(new FileWriter(fileName, true));
            myWriter.write(name + "-" + meaning);
            myWriter.newLine();
            myWriter.close();
            System.out.println("Successfully added word to dictionary.");

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static String searchWord(String word){
        //BufferedReader myReader = null;
        word = word.replaceAll("\\s+","");
        try {
            String currentLine;
            BufferedReader myReader = new BufferedReader(new FileReader(fileName));
            // Iterating through every line to find word, then return its meaning
            while((currentLine = myReader.readLine()) != null){
                String[] parts = currentLine.split("-");
                if (parts[0].equalsIgnoreCase(word)){
                    myReader.close();
                    return parts[1];
                }
            }
            myReader.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return "Word does not exist";
    }

    public synchronized static boolean wordExist(String word){
        word = word.replaceAll("\\s+","");
        try {
            String currentLine;
            BufferedReader myReader = new BufferedReader(new FileReader(fileName));
            // Iterating through every line to see if word exists
            while((currentLine = myReader.readLine()) != null){
                String[] parts = currentLine.split("-");
                if (parts[0].equalsIgnoreCase(word)){
                    myReader.close();
                    return true;
                }
            }
            myReader.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return false;
    }

    public synchronized static void updateMeaning(String name, String meaning) {
        name = name.replaceAll("\\s+","");
        try {
            File newFile = new File(fileName);
            // Creating a temporary text file
            File tempFile = new File("temp.txt");
            BufferedReader myReader = new BufferedReader(new FileReader(newFile));
            BufferedWriter myWriter = new BufferedWriter(new FileWriter(tempFile));

            String currentLine;
            // Iterating through every line
            while((currentLine = myReader.readLine()) != null){
                String[] parts = currentLine.split("-");
                // If the target word is found, write the word and its new meaning to temp.txt
                if (parts[0].equalsIgnoreCase(name)){
                     myWriter.write(name + "-" + meaning);
                }
                // If the current line is not the target word, just write the line to temp.txt
                else{
                    myWriter.write(currentLine);
                }
                myWriter.newLine();
            }

            myReader.close();
            myWriter.close();

            File f = new File("dictionary.txt");
            // Delete the current dictionary.txt and rename temp.txt to dictionary.txt
            if (f.delete()){
                Word.renameFile("temp.txt","dictionary.txt");
            } else {
                System.out.println("Failed to delete file");
            }

        }  catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public synchronized static void deleteWord(String word) {
        word = word.replaceAll("\\s+","");
        try {
            File newFile = new File(fileName);
            // Creating a temporary text file
            File tempFile = new File("temp.txt");
            BufferedReader myReader = new BufferedReader(new FileReader(newFile));
            BufferedWriter myWriter = new BufferedWriter(new FileWriter(tempFile));

            String currentLine;
            // Iterating through every line and writing every word that's not the word to be deleted into temp.txt
            while((currentLine = myReader.readLine()) != null){
                String[] parts = currentLine.split("-");
                if (!parts[0].equalsIgnoreCase(word)){
                    myWriter.write(currentLine);
                    myWriter.newLine();
                }
            }

            myReader.close();
            myWriter.close();

            File f = new File(fileName);
            // Delete the current dictionary.txt and rename temp.txt to dictionary.txt
            if (f.delete()){
                renameFile("temp.txt", fileName);
                System.out.println("Successfully deleted the word " + word + " from the dictionary");
            } else {
                System.out.println("Failed to delete the word " + word + " from the dictionary");
            }

        }  catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static void renameFile(String oldName, String newName) throws IOException {
        File oldFile = new File(oldName);
        File newFile = new File(newName);
        if (newFile.exists()) throw new java.io.IOException("file exists");


        boolean success = oldFile.renameTo(newFile);

        if (!success) {
            System.out.println("no success");
        }
    }


}
