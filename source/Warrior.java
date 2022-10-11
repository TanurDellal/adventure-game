// The fighting class in which the player can pick. A warrior has attacks differing from a mage.
public class Warrior extends FightingClass { // A mage is a type of fighting class.

    public Warrior(String fightingClassName) {

        super(fightingClassName);
    }

    // Equip a new weapon into the character's weapon slot. A new type of weapon is created and the total damage is set.
    public void equipNewWeapon(String weaponName, int weaponDamage) {

        this.weaponSlot = new Sword(weaponName, weaponDamage);
        this.setTotalDamage();
    }

    // If the normal attack is not blocked by the enemy (90% success rate) then the warrior can deal extra bleed damage.
    public void attack(Enemy e, double d) {

        int bleedDamage = weaponSlot.getBleedDamage(); // Value of bleed damage is randomised.
        boolean attackSuccess = e.takeDamage(getTotalDamage()*d, getWeaponSlot());
        
        if (attackSuccess == true && bleedDamage != 0) { // Can only deal extra damage if the normal attack is successful.

            e.takeExtraDamage(bleedDamage);
            System.out.println("Your weapon caused an extra " + bleedDamage + " bleed damage!"); 
        }
    }

    // Set the weapon level to the given value. Attributes are adjusted according to the current weapon level.
    public void setWeaponStats(int newWeaponLevel) {

        weaponSlot.setWeaponLevel(newWeaponLevel);
        weaponSlot.setWeaponDamage();
        weaponSlot.setMaxBleedDamage();
    }

    // Upgrade the weapon level by a given amount. Attributes are adjusted.
    public void upgradeWeaponStats(int weaponLevelIncrease) {

        weaponSlot.increaseWeaponLevel(weaponLevelIncrease);
        weaponSlot.setWeaponDamage();
        weaponSlot.setMaxBleedDamage();
    }

    // Print details of the current weapon equipped.
    public String seeWeaponStatus() {
        
        String weaponStats1 = "Weapon Name: " + weaponSlot.getWeaponName() + "\nWeapon Damage: " + weaponSlot.getWeaponDamage();
        String weaponStats2 = "\nWeapon Max Bleed Damage: " + weaponSlot.getMaxBleedDamage() + "\nWeapon Level: " + weaponSlot.getWeaponLevel();

        return weaponStats1 + weaponStats2;
    }
}
