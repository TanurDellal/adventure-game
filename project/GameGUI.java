import java.awt.*; // Package to help develop the GUI.
import java.awt.event.*; // Deal with all kinds of events fired.

// The class which provides a GUI to the game, allowing the player to interact with a combat dashboard.
// The player can do things like view combat stats and enemies killed.
public class GameGUI extends Frame implements ActionListener { // Allows the GUI to receive action events.

    Player p;

    Button button1;
    Button button2;
    Button button3;
    TextArea text;

    // Instantiate the GUI interface.
    public GameGUI() {

        super("Adventure Game"); // Frame created with specified sizing.
        this.setSize(1000,500);
        this.setVisible(true);

        setLayout(new FlowLayout()); // Display GUI components in a row.

        text = new TextArea("\nWelcome to my Adventure Game!\n\nAre you ready?\n", 20, 50);
        text.setFont(new Font("Serif", Font.ITALIC, 16));
        text.setEditable(false); // TextArea to print stats the player wants to see.
                                    // Font is set and the text is made non-editable.

        button1 = new Button("Yes!"); // Button to respond to welcome message. After clicking, another message is displayed.
        button1.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                
                text.setText("\nAwesome!\n\nContinue playing in the terminal and come back here\nonce you've selected your class or loaded from a save!");
            }
        });

        button2 = new Button("Close"); // Second button closes the GUI window.
        button2.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                
                dispose();
            }
        });

        add(text);
        add(button1);
        add(button2); // Adds the components onto the GUI interface.

        this.setVisible(true); // Sets components visible
    }

    public void setPlayer(Player p) { // Use Player class to access combat stats and kill count.

        this.p = p;
    }

    public void removeActionListeners(Button b) { // Take a button and remove all of its action listeners.

        ActionListener[] listenersList = b.getActionListeners();

        for (ActionListener listener : listenersList) {

            b.removeActionListener(listener);
        }
    }

    public void combatDashboard() { // Display a dashboard for the user to view combat stats and kills.

        text.setText("\nGreat " + p.getPlayerName() + "!\n\nYou can use this window to display your full player stats\nand kill count on demand!\n\nHope you enjoy!");

        removeActionListeners(button1); // Remove action listeners from previous buttons.
        removeActionListeners(button2);

        button1.addActionListener(new ActionListener() { // Button 1 displays player stats.

            public void actionPerformed(ActionEvent e) {
                
                text.setText(p.getCharacterInfo());
            }
        });
        button1.setLabel("Player Stats");

        button2.addActionListener(new ActionListener() { // Button 2 displays player kill count.

            public void actionPerformed(ActionEvent e) {
                
                text.setText("\nEnemies Killed: " + p.getEnemiesKilled());
            }
        });
        button2.setLabel("Enemies Killed");

        button3 = new Button("Close"); // Button 3 closes the GUI window.
        button3.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                
                dispose();
            }
        });
        
        add(button3); // The new button is added to the GUI window and set visible.
        setVisible(true);
    }

    public void actionPerformed(ActionEvent e) { // Abstract method implemented.  
    }
}