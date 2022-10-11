import java.io.Serializable; // Allows the class objects to be serialized.
import java.util.ArrayList;

// The class to set each level into a battleground. 
// The player fights the enemies, collects resources and carries out other important actions.
public class AreaLevel implements Serializable { // Implements serializable to allow the conversion of objects into a byte stream
 
    private String levelName;
    private int areaNumber;
    private ArrayList<Enemy> mobEnemies;
    private CountDown countDown;
    
    // Create an area level with its name and area number.
    public AreaLevel(String levelName, int areaNumber) {

        this.levelName = levelName;
        this.areaNumber = areaNumber;
    }

    // Methods to get values.
    public String getLevelName() {

        return levelName;
    }

    public int getAreaNumber() {

        return areaNumber;
    }

    public ArrayList<Enemy> getMobEnemies() {

        return mobEnemies;
    }

    // Create an array of mob enemies given their details.
    public void mobEnemiesCreation(String enemyName, int enemyHp, int enemyDamage, final int NUMBER_OF_ENEMIES) {

        mobEnemies = new ArrayList<Enemy>();

        for (int i=0; i<NUMBER_OF_ENEMIES; i++) {  // For the duration of the number of enemies given, run the loop.

            mobEnemies.add(new Enemy(enemyName + " " + (i+1), enemyHp, enemyDamage)); // While adding the enemy to the array, also create their instance.
        }
    }

    // Allows the player to fight the mob enemies.
    public void fightMobEnemies(Player p) {

        FightingClass playerFC = p.getPlayerFightingClass();

        for (int i = 0; i < mobEnemies.size() && playerFC.getCharacterAlive() ; i++) { // Fight all enemies in the array

            boolean playerTurn = true; // Keep track of the player's turn.

            Enemy currentMobEnemy = mobEnemies.get(i);

            System.out.println(currentMobEnemy.getEnemyName() + " steps up!\n");

            while (currentMobEnemy.getEnemyHp() > 0 && playerFC.getCharacterAlive() == true) { // Run the (fight) loop until the player or an enemy dies.
                
                if (playerTurn == true) {

                    playerTurn = playerTakesTurn(p, currentMobEnemy); // Player takes their turn and returns false.
                }
            
                else {

                    if (playerFC.checkCounterAttack() == true) { // If the player is eligible for a counter attack, call the method to perform it.
                        
                        performCounterAttack(p, playerFC, currentMobEnemy);
                    }

                    else {

                        enemyAttacks(playerFC, currentMobEnemy); // If the player can't counter attack, the enemy attacks.
                    }

                    playerTurn = true; // Becomes the player's turn after the enemy's turn.

                    if (playerFC.getHp() <= 0) {  // If the player dies, player is no longer alive.

                        playerFC.setCharacterAlive(false);
                        System.out.println("\nYOU ARE DEAD\n\nEND OF GAME!");
                        break;
                    }
                }

                if (currentMobEnemy.getEnemyHp() <= 0) { // If enemy dies.

                    enemyDies(i, p, currentMobEnemy);
                }
            }
        }
    }

    // Allows the player to pick up 2 given resources.
    public void gatherResources(Player p, Resource r1, Resource r2) {

        FightingClass playerFC = p.getPlayerFightingClass();

        System.out.println("\n\nWhilst adventuring you come across a " + r1.getResourceName() + " and a "+ r2.getResourceName() + "!\n\n");
        System.out.println("Enter '1' to pick up resources");
        System.out.println("Enter '2' to move on");

        int pickUpResources = p.getPlayerChoice(2);

        if (pickUpResources == 1) { // Player levels up if they pick up the resources.

            levelUp(playerFC, r1, r2);
        }
    }

    // After picking up a resource, the player levels up their combat level and their weapon level, increasing other attributes.
    public void levelUp(FightingClass playerFC, Resource r1, Resource r2) {
        
        System.out.println("Health has been restored!\n");
        System.out.println("Your stats have increased from:\n\n");
        playerFC.seeCombatStatus(); // See player stats.

        playerFC.upgradeWeaponStats(r1.getLevelIncrease());
        playerFC.upgradeCombatStats(r2.getLevelIncrease()); // Given the level increase, perform the level up.

        System.out.println("\n.\n.\n.\n");
        System.out.println("\nto:\n");

        playerFC.seeCombatStatus();
        System.out.println("\n.\n.\n.\n");

    }

    // Player takes their turn by choosing a combat option then returning false to mark the end of their turn.
    public boolean playerTakesTurn(Player p, Enemy e) {

        System.out.println("\n.\n.\n.\n");
        System.out.println("Your turn!\n");
        p.playerCombatOptions(e);
        
        return false;
    }

    // Allows the player to perform a counter attack within 5 printed seconds.
    public void performCounterAttack(Player p, FightingClass playerFC, Enemy e) {

        System.out.println("\n\nThe enemy is about to attack! You can block this!");
        System.out.println("\n\nEnter '1' for COUNTER ATTACK\ncountdown:");
        playerFC.increaseTurnCount();

        countDown = new CountDown(); // Start the countdown.

        int playerChoice = p.getPlayerChoice(1);
        
        if (playerChoice == 1 && countDown.getCounter() != 0) { // If the player counters the attack within the set time.

            System.out.println("\n.\n.\n.\n");
            System.out.println("\n\nSUCCESSFUL COUNTER! 50% Extra Conditional Damage!\n");

            countDown.stopTimer();

            playerFC.attack(e, 1.5); // Deal 50% extra damage as part of the counter attack.
        }
        
        else { // Player counters the attack after the countdown ends.

            System.out.println("\n.\n.\n.\n");
            System.out.println("\nToo Slow!");

            e.attack(playerFC); // Player gets attacked.
        }
    }

    // Enemy attacks the player and the turn count increases (keeps track of number of enemy turns).
    public void enemyAttacks(FightingClass playerFC, Enemy e) {

        System.out.println("\n.\n.\n.\n");
        playerFC.increaseTurnCount();
        e.attack(playerFC);
    }

    // Method called when the enemy dies. Player kill count increases and 'enemy defeated' message is printed.
    public void enemyDies(int i, Player p, Enemy e) {

        p.increaseKillCount();
        System.out.println("\n\nEnemy " + (i+1)+"/"+mobEnemies.size() + " defeated!!\n\n");

        if (mobEnemies.indexOf(e) == (mobEnemies.size()-1)) { // If the enemy is the last enemy in the array of mob enemies.

            System.out.println("WELL DONE!");
        }
    }
}