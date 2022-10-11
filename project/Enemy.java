import java.io.Serializable;
import java.util.Random;

// The class to create an enemy in which the player is destined to defeat. 
// The enemy will have functionalities like attacking and taking damage.
public class Enemy implements Serializable{

    private String enemyName;
    protected int enemyHp;
    protected int enemyDamage;


    //Create an enemy given its name, hp and damage.
    public Enemy(String enemyName, int enemyHp, int enemyDamage) {

        this.enemyName = enemyName;
        this.enemyHp = enemyHp;
        this.enemyDamage = enemyDamage;
    }

    // Methods to get values.
    public String getEnemyName() {

        return enemyName;
    }

    public int getEnemyHp() {

        return enemyHp;
    }

    public int getEnemyDamage() {

        return enemyDamage;
    }

    // In order for the player to attack the enemy, a take damage method is called.
    public void attack(FightingClass playerFC) {

        playerFC.takeDamage(enemyName, enemyDamage);
    }

    // Given the player's damage and weapon, deal damage to the enemy.
    // There's a success rate of 90% for each attack.
    // A boolean value indicating the success is returned.
    public boolean takeDamage(double playerDamage, Weapon w) {

        boolean tookDamage = false;

        Random rand = new Random();

        final int UPPER_BOUND = 100;
        final int ATTACK_SUCCESS = 90;

        int chance = rand.nextInt(UPPER_BOUND);

        if (chance < ATTACK_SUCCESS) {

            tookDamage = true;
            enemyHp -= playerDamage;
            System.out.println(w.getAttackDescription());
            System.out.println("You dealt " + (int)playerDamage + " damage!");
        } 
        else {

            System.out.println("Enemy blocked your attack!! 0 damage dealt ");
        }
        return tookDamage;
    }

    public void takeExtraDamage(int playerDamage) { // Extra damage is dealt when unique fighting classes deal more damage.

        enemyHp -=playerDamage;

    }
}
