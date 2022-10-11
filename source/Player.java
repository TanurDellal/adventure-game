import java.io.Serializable;
import java.util.ArrayList;
import java.util.Scanner;

// The player class keeping track of player scores and every decision they make (like choosing their fighting class).
// Their progress throughout the game is continually tracked.
// Loading and saving becomes easy when only the player class has to be saved.
public class Player implements Serializable {

    private String playerName = "";
    private int enemiesKilled = 0;
    private FightingClass playerFightingClass;
    private AreaLevel currentAreaLevel;
    private int currentAreaNumber;


    public Player() {
    }

    // Methods to get values.
    public String getPlayerName() {

        return playerName;
    }

    public int getEnemiesKilled() {

        return enemiesKilled;
    }

    public FightingClass getPlayerFightingClass() {

        return playerFightingClass;
    }

    public String getFightingClassName() {

        return playerFightingClass.getFightingClassName();
    }

    public AreaLevel getCurrentAreaLevel() {

        return currentAreaLevel;
    }

    public int getCurrentAreaNumber() {

        return currentAreaNumber;
    }

    // Methods to set values.
    public void setEnemiesKilled(int enemiesKilled) {

        this.enemiesKilled = enemiesKilled;
    }

    public void setCurrentAreaLevel(AreaLevel currentAreaLevel) {

        this.currentAreaLevel = currentAreaLevel;
    }
    
    public void setCurrentAreaNumber(int currentAreaNumber) {

        this.currentAreaNumber = currentAreaNumber;
    }

    // Increase the count of the number of enemies killed.
    public void increaseKillCount(){

        enemiesKilled++;
    }

    // Get String input from the player and pass the question as a parameter.
    public String getAlphabeticInput(String question) {

        Scanner input = new Scanner(System.in);

        System.out.println(question);
        String playerText = input.nextLine(); // Scan keyboard strokes of their next line of input.

        while (playerText.isEmpty() || playerText.length() > 30) { // Input must not be empty and less than 30 characters.

            System.out.println("Please enter a valid answer.\n" + question);
            playerText = input.nextLine();
        }
        return playerText;
    }

    // Allow the player to make a choice between any given options. The user can only enter 1, 2, 3... n for any n number of given options.
    public int getPlayerChoice(final int NUMBER_OF_OPTIONS) { // Number of options possible given as a parameter.

        ArrayList<String> optionsList = new ArrayList<String>(); // Array list of possible String answers 1 to n.

        for (int i = 0; i < NUMBER_OF_OPTIONS; i++) { // Store each possible option as String versions ('1', '2', '3'...)

            optionsList.add(String.valueOf(i+1));
        }

        String playerChoice = getAlphabeticInput("");

        while (!optionsList.contains(playerChoice)) { // The player's input can only be one of the answers inside the array list.

            System.out.println("Please select a valid option.");
            playerChoice = getAlphabeticInput("");
        }
        return (optionsList.indexOf(playerChoice) + 1); // The numeric value of the option they picked is returned using index notation
                                                        // If the player enters 1, this will be in the 0th position. Index + 1 gives us this value.
    }

    // Ask for the player's name but only allow non-numeric input. Store the name as part of the player's object.
    public void setPlayerName(String question) {

        String playerName = getAlphabeticInput(question);

        for (int i = 0; i < playerName.length(); i++) { // For the length of the player's input.

            if (Character.isDigit(playerName.charAt(i))) { // If any character triggers this if branch (if any character is a digit).

                System.out.println("Please enter letters (max 30 characters)\n");
                playerName = getAlphabeticInput(question); // Ask for input again.
                i = -1; // Since new input is taken, the first character needs to be checked again. 
                        // So -1 + 1 sets i to 0, ready to read from the start of the String input.
            }
        }
        this.playerName = playerName;
    }

    // Based on the fighting class that the player selects, assign their fighting class to be mage or warrior.
    // Warriors and mages wield different weapons, so different weapons are also instantiated.
    public void setPlayerFightingClass() {

        int playerChoice = getPlayerChoice(2);

        if (playerChoice == 1) {

            playerFightingClass = new Warrior("Berserker Warrior");
            playerFightingClass.equipNewWeapon("Long Sword", 50);
        }
        else if (playerChoice == 2) {

            playerFightingClass = new Mage("Glintstone Mage");
            playerFightingClass.equipNewWeapon("Crystal Staff", 50);
        }
    }

    // Print a summary of the player's combat stats and weapon stats.
    public String getCharacterInfo() {

        String summary = "\nHere is a summary of your character!\n\n   FIGHTING CLASS\n\n";

        String playerStats1 = "Health: " + playerFightingClass.getHp() + "\nBase Damage: " + playerFightingClass.getBaseDamage();
        String playerStats2 = "\nTotal Damage: " + playerFightingClass.getTotalDamage() + "\nCombat Level: " + playerFightingClass.getCombatLevel();

        String weaponStats = "\n\n   WEAPON \n\n" + playerFightingClass.seeWeaponStatus();

        return summary + playerStats1 + playerStats2 + weaponStats;
    }

    // Print the combat options the player can pick from.
    public void printCombatOptions() {

        System.out.println("-------------------------------------------------------------");
        System.out.println("Enter '1' for Attack");
        System.out.println("Enter '2' to See HP");
        System.out.println("Enter '3' to See Enemy HP");
    }

    // The combat options in which the player can pick from. They can only attack, see their hp or see the enemy's hp.
    // The end of the player's turn is only marked when they attack.
    public void playerCombatOptions(Enemy e) {

        printCombatOptions();

        int playerCombatChoice = getPlayerChoice(3);

        boolean endOfTurn = false;

        while (!endOfTurn) { // While it's not the end of the player's turn, allow the player to pick options.

            if (playerCombatChoice == 1) {

                playerFightingClass.attack(e, 1);
                endOfTurn = true; // Mark the end of the player's turn and exit the loop.
            }
            else if (playerCombatChoice == 2) {

                System.out.println("Your Health: " + playerFightingClass.getHp() + "\n\n");
                printCombatOptions();
                playerCombatChoice = getPlayerChoice(3);
            }
            else if (playerCombatChoice == 3) {
                
                System.out.println("Enemy Health " + e.getEnemyHp() + "\n\n");
                printCombatOptions();
                playerCombatChoice = getPlayerChoice(3);
            }
        }
    }
}
