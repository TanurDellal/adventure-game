import java.io.Serializable;

// The weapon class in which fighting classes equip. 
// Different weapon types exist and so different fighting classes can equip specific weapons.
// Each weapon has unique strengths.
public abstract class Weapon implements Serializable{

    protected String weaponName;
    protected int weaponDamage;
    protected int weaponLevel = 1;

    // Create a weapon given its name and strength.
    public Weapon(String weaponName, int weaponDamage) {

        this.weaponName = weaponName;
        this.weaponDamage = weaponDamage;
    }

    public abstract String getAttackDescription(); // A String description of a weapon's attack style.

    // Methods to get values.
    public String getWeaponName() {

        return weaponName;
    }

    public int getWeaponDamage() {

        return weaponDamage;
    }

    public int getWeaponLevel() {

        return weaponLevel;
    }

    // Set the weapon damage to increase by 30 every additional level from 1.
    public void setWeaponDamage() {

        weaponDamage = ((getWeaponLevel()-1)*30)+50;
    }

    // Methods to set values.
    public void setWeaponLevel(int weaponLevel) {

        this.weaponLevel = weaponLevel;
    }

    // Increase the weapon level by a given value.
    public void increaseWeaponLevel(int weaponLevelIncrease) {
        
        weaponLevel += weaponLevelIncrease;
    }

    // Overridden methods.
    public void setMaxBleedDamage() {
    }

    public void setDoubleCastChance() {
    }

    public void setHealHealthChance() {
    }

    public int getMaxBleedDamage() {

        return 0;
    }

    public int getBleedDamage() {

        return 0;
    }

    public int getDoubleCastChance() {

        return 0;
    }

    public int getHealHealthChance() {

        return 0;
    }

    public boolean getSpellChanceOutcome(int spellChance) {
        
        return false;
    }
}
