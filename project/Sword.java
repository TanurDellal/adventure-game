import java.util.Random;

// The type of weapon all warriors can equip. They provide a distinct fighting style with unique abilities.
public class Sword extends Weapon { // A sword is a type of weapon.

    private int maxBleedDamage = 60;

    // Create a sword given its name and damage.
    public Sword(String weaponName, int weaponDamage) {
        
        super(weaponName, weaponDamage);
    }

    public int getMaxBleedDamage() {

        return maxBleedDamage;
    }

    // Generate a random value between 0 and the max bleed damage.
    // The sword will deal this additional damage when attacking an enemy.
    public int getBleedDamage() {

        Random rand = new Random();

        final int UPPER_BOUND = maxBleedDamage;
        int bleedDamage = rand.nextInt(UPPER_BOUND+1);

        return bleedDamage;
    }

    // Set the max bleed damage to increase by 30 every additional level from 1.
    public void setMaxBleedDamage() {

        maxBleedDamage = ((getWeaponLevel()-1)*30) + 60;
    }

    // Return the attack description of a sword's fighting style.
    public String getAttackDescription() {
        
        return "You slash the enemy";
    }



}
