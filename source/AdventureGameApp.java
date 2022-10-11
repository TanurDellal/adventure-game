/*****************************************
 AUTHOR: Tanur Dellal
 DATE: 17 April 2022
 VERSION: 1
 OVERVIEW: This program is purposed with providing entertainment for the user
           via a text-based adventure game. The aim of the game is to defeat all
           of the enemies in each level and excel to the final level containing
           a difficult boss. Resources are crucial and if none are picked up, 
           there will be no chance of survival.

 ****************************************/

import java.io.*;
import java.util.ArrayList;

// The main class where the adventure game is run. 
// Other important objects are instantiated here.
public class AdventureGameApp {

    private Player player;
    private int currentAreaNumber = 1;
    private AreaLevel currentAreaLevel;
    private ArrayList<Resource> allResources;
    private int resourceCounter;
    private boolean startNewGame = true;
    private boolean playerWantsToPlay = true;
    private boolean playerRestarts = false;
    private boolean playerCanSave = true;
    private boolean playerContinuesFromSave = false;
    GameGUI gui;

    // Runs the game and loops.
    public AdventureGameApp() throws ClassNotFoundException, IOException {
        
        gui = new GameGUI(); // Run the mini gui experience for the player and welcome them.
        player = new Player();

        createPlayerResources();
        loadGameMenu();

        if (startNewGame) { // If the player starts a new game, ask for their name and fighting class.

            greetPlayer();
            selectFightingClass();
        }

        gui.setPlayer(player); 
        gui.combatDashboard(); // Allow the player to view combat stats and enemy count.

        while (playerWantsToPlay) { // As long as the user wants to play, keep looping the main game.

            if (playerRestarts) { //If and only if the player restarts the game, ask for their fighting class again.

                selectFightingClass();
                playerRestarts = false;
            }

            enterAllLevels(currentAreaNumber);

            if (!player.getPlayerFightingClass().getCharacterAlive()) { // If the player is not alive, display the retry menu.

                retryMenu();
                playerCanSave = false; // The player shouldn't be able to save as soon as they load the level.
            }

            if (player.getEnemiesKilled() == 13) { // If the player dies and chooses restart, this block won't be run.

                printEndGameResults();
                replayMenu();
            }
        }
    }

    // Instantiate the main programme class to run all necessary methods.
    public static void main(String[] args) throws ClassNotFoundException, IOException {

        new AdventureGameApp();
        System.exit(0);
    }

    // Start the game with a load game menu - if the player wants to load a save or start fresh.
    public void loadGameMenu() throws ClassNotFoundException, IOException {

        System.out.println("My game, Your adventure");
        System.out.println("\n\nDo you want to load from a save or start a new game?");
        System.out.println("Enter '1' for load from most recent save");
        System.out.println("Enter '2' to start a new game");

        int playerDecision = player.getPlayerChoice(2);

        if (playerDecision == 1) {

            File file = new File("saveGame.txt");

            if (file.exists() && !file.isDirectory()) { // If save file exists and pathname is a valid directory.
    
                startNewGame = false;
                playerContinuesFromSave = true;
                player = (Player) loadSaveFile(file); // Loads the saved game and casts the returned Object into Player.
                currentAreaLevel = player.getCurrentAreaLevel(); // Gets information on the level that the player reached
                currentAreaNumber = player.getCurrentAreaNumber(); // and uses it within AdventureGame to load the right level.
            }

            else {

                System.out.println("Save not found!");
            }
        }
    }

    // Greet the user here, take their name and explain the game.
    public void greetPlayer() {

        player.setPlayerName("What is your name?");
        System.out.println("Awesome " + player.getPlayerName() + "!\n");
        System.out.println("To play this game, you must venture into dangerous territories and fight deadly enemies.");
        System.out.println("But please, be careful. There will be an enemy with extraordinary strength in the final area!\n");
    }

    // Display different fighting class options, and let the player select their fighting class.
    public void selectFightingClass() {

        System.out.println("\n.\n.\n.\n");
        System.out.println("To start, please select your class:");
        System.out.println("Enter '1' for Warrior");
        System.out.println("Enter '2' for Mage");

        player.setPlayerFightingClass();
        System.out.println(player.getCharacterInfo()); // Depending on fighting class picked, display their stats.

        System.out.println("\nGood choice, " + player.getFightingClassName() + "!");
    }

    // Create all discoverable resources and place into an ArrayList.
    public void createPlayerResources() {

        allResources = new ArrayList<Resource>();
        allResources.add(new Resource("Snowy Flower", 1));
        allResources.add(new Resource("Silver Ore", 1));
        allResources.add(new Resource("Deathroot Flower", 2));
        allResources.add(new Resource("Black Ore", 2));
        allResources.add(new Resource("Crimson Flower", 2));
        allResources.add(new Resource("Magma Ore", 2));
        allResources.add(new Resource("Withered Flower", 3));
        allResources.add(new Resource("Crackled Ore", 3));
        allResources.add(new Resource("Blue Lily", 4));
    }

    // This is a generalised method for a single level - each level is generated through this method.
    public void SingleLevel(String levName, int areaNumber, String mobName, int mobHp, int mobDamage, int mobCount, String description, String enemyDescription) throws ClassNotFoundException, IOException {

        FightingClass playerFC = player.getPlayerFightingClass();

        if (playerFC.getCharacterAlive()) { // Only run the level if the player is still alive.

            currentAreaLevel = new AreaLevel(levName, areaNumber);

            currentAreaNumber = areaNumber; // Make sure AdventureGame keeps track of the area the player is in.
            player.setCurrentAreaLevel(currentAreaLevel); // Make sure this is reflected inside Player class too.
            player.setCurrentAreaNumber(currentAreaNumber);

            if (areaNumber != 1 && playerCanSave && !playerContinuesFromSave) {
                saveBeforeStartLevel();
            }

            playerContinuesFromSave = false;
            playerCanSave = true;
            currentAreaLevel.mobEnemiesCreation(mobName, mobHp, mobDamage, mobCount);

            System.out.println("\n.\n.\n.\n");
            System.out.println("\nWelcome to " + levName + description + "\n\n"); // Introduce the level.
            System.out.println("LOOK OUT\n");

            if (mobCount > 1) { // Grammar differentiation depending on how many enemies are in the level.

                System.out.println(enemyDescription + " approach you...\n\n");
            }
            else {

                System.out.println(enemyDescription + " approaches you...\n\n");
            }

            currentAreaLevel.fightMobEnemies(player); // Start the fight between the player and the mob enemies.

            // The player can only gather resources if they are alive and not on the last level.
            if (playerFC.getCharacterAlive() && !currentAreaLevel.getLevelName().equals("Final Level - Pit of Hollows")) {

                currentAreaLevel.gatherResources(player, allResources.get(resourceCounter++), allResources.get(resourceCounter++));
            }
        }
    }

    // Play through all levels, given the level number to start at a certain level.
    public void enterAllLevels(int i) throws ClassNotFoundException, IOException {

        String level1Description = ". The noise of howling wolves break all silence while the chilling frost falls aggressively.";
        String level2Description = ". With no sight of light, you traverse further into a deep, dark forest. The sound of dripping saliva startles you...";
        String level3Description = ". Surrounded by pits of lava and escaping steam, you smell an ominous burning scent ahead.";
        String level4Description = ". Traversing the deserted sands, the back of your throat runs dry. Sudden movement in the sand reveals...";
        String level5Description = ". Walking steadily through the caves of an ancient lair, a gigantic, hollowed pit reveals itself.\nSo hollow, that the dragon staring deep into your soul was nothing but moving bones...";

        if (i == 1) {

            SingleLevel("Level 1 - Snowy Mountains", 1,"Wolf", 200, 15, 3, level1Description, "A pack of wolves");
            i++; // After playing the first level, increase the count so the second level can begin.
        }
        if (i == 2) {

            SingleLevel("Level 2 - Black Forest", 2,"Demon", 300, 25, 3, level2Description, "A group of blood-thirsty demons");
            i++;
            }
        if (i == 3) {

            SingleLevel("Level 3 - Lava Land", 3,"Flaming Slime", 400, 40, 3, level3Description, "A pool of flaming slimes");
            i++;
        }
        if (i == 4) {

            SingleLevel("Level 4 - Deserted Sands", 4,"Scorpion", 500, 50, 3, level4Description, "A group of poisonous scorpions");
            i++;
        }
        if (i == 5) {
            
            SingleLevel("Final Level - Pit of Hollows", 5,"Ancient Dragon Makar", 4000, 90, 1, level5Description, "Ancient Dragon Makar");
        }
    }

    // Conclude game and print player results. 
    public void printEndGameResults() {

        FightingClass playerFC = player.getPlayerFightingClass();

        System.out.println("\nThank you for playing, " + player.getPlayerName() + "!\n\nHere are your results:\n");
        System.out.println("Class Picked: " + playerFC.getFightingClassName());
        System.out.println("Combat Level Reached: " + playerFC.getCombatLevel());
        System.out.println("Max Health Reached: " + playerFC.getHpMax());
        System.out.println("Total Damage Reached: " + playerFC.getTotalDamage());
        System.out.println("Total Enemies Killed: " + player.getEnemiesKilled());
    }

    // If the player dies, display the retry menu. The player can load from save, restart or quit.
    public void retryMenu() throws ClassNotFoundException, IOException {

        FightingClass playerFC = player.getPlayerFightingClass();

        System.out.println("\n\nDo you want to load from a save, restart or quit?");
        System.out.println("Enter '1' for load from most recent save");
        System.out.println("Enter '2' to restart");
        System.out.println("Enter '3' to quit");

        int playerDecision = player.getPlayerChoice(3);

        if (playerDecision == 1) {

            File file = new File("saveGame.txt");

            if (file.exists() && !file.isDirectory()) { 

                player = (Player) loadSaveFile(file);
                currentAreaLevel = player.getCurrentAreaLevel();
                currentAreaNumber = player.getCurrentAreaNumber();
                gui.setPlayer(player);

                playerFC.setCombatStats(playerFC.getCombatLevel()+1); // Give the player a bonus level to help.
                System.out.println("+1 Level for free!"); 
            }
            else {

                System.out.println("Save not found!");
            }
        }
        else if (playerDecision == 2) { // Player restarts the whole game so values are reset.

            resetValues();
            currentAreaNumber = 1;
            playerRestarts = true;
        }

        else if (playerDecision == 3) { // Termination condition set to exit game loop.

            playerWantsToPlay = false;
        }
    }

    // Display the replay menu after the player defeats the game.
    public void replayMenu() {

        System.out.println("\n\nDo you wish to play again?\n");
        System.out.println("Enter '1' for yes");
        System.out.println("Enter '2' for no");

        int playAgain = player.getPlayerChoice(2);

        if (playAgain == 1) { // If player chooses to replay, reset all values.

            resetValues();
            currentAreaNumber = 1;
            gui.setPlayer(player);
        }
        else if (playAgain == 2) { // If player does not want to replay, set termination condition to exit game loop.

            playerWantsToPlay = false;
        }
    }

    // Allows the player to save once they reach a new level.
    public void saveBeforeStartLevel() {
        
        System.out.println("\n\nBefore continuing to the next level, do you wish to save?\n");
        System.out.println("Enter '1' for yes");
        System.out.println("Enter '2' for no");
        
        int saveGame = player.getPlayerChoice(2);

        if (saveGame == 1) { // If the player saves, the saveGame method is run.

            saveGame("saveGame.txt");
        }
    }

    // Allows the player to load from saved data and continue playing.
    public Object loadSaveFile(File fileName) throws IOException, ClassNotFoundException { // Necessary exceptions are thrown

        Object objectSave = null; // Initialise object to null.
        
        try (FileInputStream fileInput = new FileInputStream(fileName); // Set the file input stream to read from the given file name.
            ObjectInputStream objectInput = new ObjectInputStream(fileInput)) { // Allow the object input stream to save the object found from the file input stream.

            objectSave = objectInput.readObject(); // Save the object read from the saved file into objectSave and later return it.
        }
        return objectSave;
    }

    // Save game method to save the player object and load it at another point in time or when the player dies (retry menu).
    public void saveGame(String fileName) {

        try {

            FileOutputStream fileOutput = new FileOutputStream(fileName); // Set the file output stream to save data into the given file.
            ObjectOutputStream objectOutput = new ObjectOutputStream(fileOutput); // Allow an object to be saved via the file output stream into the given file.

            objectOutput.writeObject(player); // Save the player object into the given file.
            objectOutput.close(); // Close the output stream.

        } 
        catch (IOException e) { // Catch any IO exceptions.

            e.printStackTrace(); // Prints any throwable errors and their stack trace.
        }
    }

    // Reset key attributes when the player chooses to restart the game.
    public void resetValues() {

        FightingClass playerFC = player.getPlayerFightingClass();

        resourceCounter = 0;
        player.setEnemiesKilled(0);
        playerFC.setCombatStats(1);
        playerFC.setWeaponStats(1);
        playerFC.setTotalDamage();
    }
}

