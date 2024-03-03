// Name : Brandon Vei Liang
// Surname : Lim
// Student id: 1430381

import java.io.IOException;


public class TCPInteractiveClient {
    public static void main(String[] args) {

        try {
            // Creating user interface
            UserInterface myFrame = new UserInterface();
            myFrame.setVisible(true);

        }  catch (IOException e) {
            e.printStackTrace();
        }

    }

}
