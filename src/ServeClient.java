// Name : Brandon Vei Liang
// Surname : Lim
// Student id: 1430381

import java.io.*;
import java.net.Socket;

public class ServeClient implements Runnable{
    private Socket clientSocket;
    private int clientNumber;

    public ServeClient(Socket clientSocket, int clientNumber){
        this.clientSocket = clientSocket;
        this.clientNumber = clientNumber;
    }


    @Override
    public void run() {
        BufferedReader in = null;
        try {
            in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        BufferedWriter out = null;
        try {
            out = new BufferedWriter(new OutputStreamWriter(clientSocket.getOutputStream()));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        String clientMsg = null;
        try {
            while ((clientMsg = in.readLine()) != null) {
                System.out.println("Message from client " + clientNumber + ": " + clientMsg);
                // Reading the response from client and calling appropriate functions
                switch (clientMsg) {
                    case "ADD":
                        // Read the word
                        String word = in.readLine();
                        if (!word.equals("Blank text field")) {
                            System.out.println("Word blank");
                            // Read the meaning
                            String meaning = in.readLine();
                            System.out.println("Meaning received from client " + clientNumber + ": " + meaning);
                            // Check if word exists and writes a response to the client
                            if (Word.wordExist(word)) {
                                out.write("Word exists\n");
                                out.flush();
                            } else {
                                out.write("Word does not exist!\n");
                                out.flush();

                                // Add word to the dictionary.txt
                                Word.addWord(word, meaning);
                            }
                        }

                        break;
                    case "SEARCH":
                        // Read the search word
                        String search = in.readLine();
                        if (!search.equals("Blank text field")) {
                            // Search for word's meaning
                            out.write(Word.searchWord(search) + "\n");
                            out.flush();
                            System.out.println("Meaning sent");
                        }
                        break;
                    case "UPDATE":
                        // Read the word to be updated
                        String updWord = in.readLine();
                        if (!updWord.equals("Blank text field")) {
                            // Check if word exists, update its meaning if it does
                            if (Word.wordExist(updWord)) {
                                out.write("Word exists\n");
                                out.flush();

                                String meaning = in.readLine();
                                if (!meaning.equals("Blank text field")) {
                                    Word.updateMeaning(updWord, meaning);

                                    System.out.println("Update successful");
                                }
                            } else {
                                out.write("Word does not exist!\n");
                                out.flush();
                            }

                        }
                        break;
                    case "DELETE":
                        // Read the word to be deleted
                        String delWord = in.readLine();
                        if (!delWord.equals("Blank text field")) {
                            // Check if word exists, if it does, delete it.
                            if (Word.wordExist(delWord)) {
                                Word.deleteWord(delWord);
                                out.write("Word deleted\n");
                                out.flush();
                            } else {
                                out.write("Word does not exist\n");
                                out.flush();
                            }
                        }
                        break;
                }
            }
        } catch (IOException e) {
            System.out.println("Client" + clientNumber + " disconnected " );
        }
        try {
            clientSocket.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
