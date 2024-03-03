// Name : Brandon Vei Liang
// Surname : Lim
// Student id: 1430381

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.net.Socket;

public class UserInterface extends JFrame {
    private JPanel mainPanel;
    private JTabbedPane tabbedPane1;
    private JPanel addTab;
    private JPanel queryTab;
    private JPanel updateTab;
    private JPanel removeTab;
    private JButton addWord;
    private JTextField tfWord;
    private JLabel aWord;
    private JLabel aMeaning;
    private JTextArea taMeaning;
    private JLabel qWord;
    private JTextField qTfWord;
    private JLabel qMeaning;
    private JLabel qdMeaning;
    private JButton getMeaning;
    private JLabel uWord;
    private JTextField uTfWord;
    private JLabel uMeaning;
    private JTextArea uTaMeaning;
    private JButton updateWord;
    private JLabel rWord;
    private JTextField rTfWord;
    private JButton removeWord;
    private JTextArea qTaMeaning;

    public UserInterface() throws IOException {

        Socket socket = null;
        try {
            socket = new Socket("localhost", 4444);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream(), "UTF-8"));
        BufferedWriter out = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream(), "UTF-8"));
        setContentPane(mainPanel);
        setTitle("Dictionary");
        setSize(450,300);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setVisible(true);
        qTaMeaning.setEditable(false);

        addWord.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    out.write("ADD\n");
                    out.flush();
                    // Retrieving word and meaning from GUI
                    String word = tfWord.getText();
                    String meaning = taMeaning.getText();
                    // Checking for empty text fields
                    if (word.isBlank() || meaning.isBlank()) {
                        out.write("Blank text field\n");
                        out.flush();
                        JOptionPane.showMessageDialog(null, "Can't leave text fields blank");
                    } else {
                        // Sending word and meaning to server, if word does not already exist, add it
                        out.write(word + "\n");
                        out.flush();
                        out.write(meaning + "\n");
                        out.flush();
                        String response = in.readLine();
                        if (response.equals("Word exists")){
                            JOptionPane.showMessageDialog(null, "Word already exists");
                        } else {
                            JOptionPane.showMessageDialog(null, "Added");
                        }
                    }

                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });

        getMeaning.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    out.write("SEARCH\n");
                    out.flush();
                    // Retrieving search word from GUI
                    String word = qTfWord.getText();
                    // Checking for empty text fields
                    if (word.isBlank()){
                        out.write("Blank text field\n");
                        out.flush();
                        JOptionPane.showMessageDialog(null, "Please enter a word");
                    } else {
                        // Sending in the word to be searched
                        out.write(word + "\n");
                        out.flush();
                        String meaning = in.readLine();
                        // Checking if there is such word, if it exists, display its meaning in the text area
                        if (meaning.equals("Word does not exist")) {
                            JOptionPane.showMessageDialog(null, "Word does not exist in dictionary");
                        } else {
                            qTaMeaning.setText(meaning);
                        }
                    }

                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });

        updateWord.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try{
                    out.write("UPDATE\n");
                    out.flush();
                    // Retrieving word to be updated, and sending it to server
                    String word = uTfWord.getText();

                    // Checking for empty word text field
                    if(word.isBlank()){
                        out.write("Blank text field\n");
                        out.flush();
                        JOptionPane.showMessageDialog(null, "Please enter a word");
                    } else {
                        out.write(word + "\n");
                        out.flush();
                        String response = in.readLine();
                        // Checking if the word exists
                        if (response.equals("Word exists")){
                            String meaning = uTaMeaning.getText();
                            // Checking for empty meaning text field, if not empty, update word's meaning
                            if(meaning.isBlank()){
                                out.write("Blank text field\n");
                                out.flush();
                                JOptionPane.showMessageDialog(null, "Please enter a new meaning for the word");
                            }else {
                                out.write(meaning + "\n");
                                out.flush();
                                JOptionPane.showMessageDialog(null, word + "'s meaning updated to " + meaning);
                            }
                        } else {
                            JOptionPane.showMessageDialog(null, "Word does not exist");
                        }
                    }

                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });


        removeWord.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try{
                    out.write("DELETE\n");
                    out.flush();
                    // Retrieving word to be deleted from text field
                    String word = rTfWord.getText();
                    // Checking for empty text fields
                    if (word.isBlank()) {
                        out.write("Blank text field\n");
                        out.flush();
                        JOptionPane.showMessageDialog(null, "Please enter a word");
                    } else {
                        out.write(word + "\n");
                        out.flush();
                        // Checking to see if word is properly deleted
                        String response = in.readLine();
                        if (response.equals("Word deleted")){
                            JOptionPane.showMessageDialog(null, "Word deleted");
                        } else {
                            JOptionPane.showMessageDialog(null, "Word does not exist");
                        }
                    }

                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });
    }

}
