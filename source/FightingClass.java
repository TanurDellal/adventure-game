import java.io.Serializable;

// Class allowing the player to do most combat related things. 
// We keep track of hp, damage, levels and other combat concepts here.
public abstract class FightingClass implements Serializable {

    private String fightingClassName;
    private boolean characterAlive = true;
    private int hp = 100;
    protected int baseDamage = 50;
    private int totalDamage;
    private int combatLevel = 1;
    
    private int turnCount = 0;
    private final int COUNTER_ATTACK_FREQUENCY = 4;
    protected Weapon weaponSlot;

    
    public FightingClass(String fightingClassName) {

        this.fightingClassName = fightingClassName;
    }

    public abstract void attack(Enemy e, double d); // Attack an enemy given the damage multiplier.

    public abstract String seeWeaponStatus(); // Print weapon attributes.
    
    public abstract void equipNewWeapon(String weaponName, int weaponDamage); // Create a new weapon and assign it to the weaponSlot.

    public abstract void setWeaponStats(int newWeaponLevel); // Set the weapon level to a specific value and change weapon stats.

    public abstract void upgradeWeaponStats(int WeaponLevelIncrease); // Increase the weapon level and upgrade weapon stats.

    public String getFightingClassName() {

        return fightingClassName;
    }

    // Methods to get values.
    public boolean getCharacterAlive() {

        return characterAlive;
    }

    public int getHp() {

        return hp;
    }

    public int getHpMax() {
        
        return (( combatLevel-1 ) * 50 ) + 100;
    }

    public int getBaseDamage() {

        return baseDamage;
    }

    public int getTotalDamage() {
        
        return totalDamage;
    }

    public int getCombatLevel() {
        
        return combatLevel;
    }

    public int getTurnCount() {

        return turnCount;
    }

    public Weapon getWeaponSlot() {

        return weaponSlot;
    }

    // Methods to set values.
    public void setCharacterAlive(boolean characterAlive) {

        this.characterAlive = characterAlive;
    }

    // Set the hp to increase by 50 every additional level from 1.
    public void setHpMax() {

        hp = (( combatLevel-1 ) * 50 ) + 100;
    }
    
    // Set the base damage to increase by 15 every additional level from 1.
    public void setBaseDamage() {

        baseDamage = (( combatLevel-1 ) * 15 ) + 50;
    }

    public void setTotalDamage() {

        totalDamage = baseDamage + weaponSlot.getWeaponDamage();
    }

    public void increaseTurnCount() {
        
        turnCount++;
    }

    public void resetTotalDamage() {

        totalDamage -= weaponSlot.getWeaponDamage();
    }

    // The player takes damage from an enemy, given their name and damage.
    public void takeDamage(String enemyName, int enemyDamage) {

        System.out.println("\n" + enemyName + " attacks!\n");
        hp -= enemyDamage;
        System.out.println("They deal " + enemyDamage + " damage\n");
    }

    // Every 4 (enemy) turn counts, the player is able to counter attack.
    public boolean checkCounterAttack() {

        if (turnCount % COUNTER_ATTACK_FREQUENCY == 0) {

            return true;
        }
        return false;
    }

    // Print the main player attributes.
    public void seeCombatStatus() {

        System.out.println("Combat Level: " + getCombatLevel());
        System.out.println("HP: " + getHp());
        System.out.println("Base Damage: " + getBaseDamage());
    }

    // Set the player combat level, given the level. Other attributes are adjusted accordingly.
    public void setCombatStats(int newCombatLevel) {
        
        combatLevel = newCombatLevel;
        setHpMax();
        setBaseDamage();
        setTotalDamage();
    }

    // Upgrades the player combat level given the level increase.
    public void upgradeCombatStats(int levelIncrease) {

        combatLevel += levelIncrease;
        setHpMax();
        setBaseDamage();
        setTotalDamage();
    }
}
