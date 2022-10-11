import java.util.Random;

// The type of weapon all mages can equip. They provide a distinct fighting style with unique abilities.
public class Staff extends Weapon { // A staff is a type of weapon.

    private int doubleCastChance = 20;
    private int healHealthChance = 10;


    // Create a staff given its name and damage.
    public Staff(String weaponName, int weaponDamage) {

        super(weaponName, weaponDamage);
    }

    // Methods to get values.
    public int getDoubleCastChance() {

        return doubleCastChance;
    }

    public int getHealHealthChance() {

        return healHealthChance;
    }

    // Set the double cast chance to increase by 3 every additional level from 1.
    public void setDoubleCastChance() {

        doubleCastChance = ((getWeaponLevel()-1)*3)+20;
    }

    // Set the heal health chance to increase by 2 every additional level from 1.
    public void setHealHealthChance() {

        healHealthChance = ((getWeaponLevel()-1)*2)+10;
    }

    // Return the attack description of a staff's fighting style.
    public String getAttackDescription() {

        return "You cast your spell on the enemy";
    }

    // See if the player is eligible to trigger one of the rare spells based on the spell chance given.
    public boolean getSpellChanceOutcome(int spellChance) {

        boolean triggerSpell = false; 

        Random rand = new Random();

        final int UPPER_BOUND = 100;
        int chance = rand.nextInt(UPPER_BOUND+1);

        if (chance <= spellChance) { // If the number generated out of 100 is less than the value given.

            triggerSpell = true; // Player triggers one of the rare spells.
        }
        return triggerSpell;
    }
}