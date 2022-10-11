// The class in which the player can pick. A mage has attacks differing from a warrior.
public class Mage extends FightingClass { // A mage is a type of fighting class.

    public Mage(String fightingClassName) {

        super(fightingClassName);
    }

    // Equip a new weapon into the character's weapon slot. A new type of weapon is created and the total damage is set.
    public void equipNewWeapon(String weaponName, int weaponDamage) {

        this.weaponSlot = new Staff(weaponName, weaponDamage);
        this.setTotalDamage();
    }

    // If the normal attack is not blocked by the enemy (90% success rate) then the mage has a chance of casting special abilities.
    // There's a small chance to double cast a spell and deal double damage.
    // There is also a small chance of casting a rare ability which heals the player's health to full.
    public void attack(Enemy e, double d) {

        boolean attackSuccess = e.takeDamage(getTotalDamage()*d, getWeaponSlot());
        
        if (attackSuccess == true) { // Can only deal extra damage if the normal attack is successful.

            if (weaponSlot.getSpellChanceOutcome(weaponSlot.getDoubleCastChance())) { // If mage can double cast.

                e.takeExtraDamage(getTotalDamage());
                System.out.println("You double casted with your staff weapon! You dealt an extra " + getTotalDamage() + " damage!");
            }

            else if (weaponSlot.getSpellChanceOutcome(weaponSlot.getHealHealthChance())) { // If mage can heal to full health.

                setHpMax();
                System.out.println("Outstanding skills! You healed yourself back to your Maximum Health!");
            }
        }
    }

    // Set the weapon level to the given value. Attributes are adjusted according to the current weapon level.
    public void setWeaponStats(int newWeaponLevel) {

        weaponSlot.setWeaponLevel(newWeaponLevel);
        weaponSlot.setWeaponDamage();
        weaponSlot.setDoubleCastChance();
        weaponSlot.setHealHealthChance();
    }

    // Upgrade the weapon level by a given amount. Attributes are adjusted.
    public void upgradeWeaponStats(int weaponLevelIncrease) {

        weaponSlot.increaseWeaponLevel(weaponLevelIncrease);
        weaponSlot.setWeaponDamage();
        weaponSlot.setDoubleCastChance();
        weaponSlot.setHealHealthChance();
    }

    // Print details of the current weapon equipped.
    public String seeWeaponStatus() {
        
        String weaponStats1 = "Weapon Name: " + weaponSlot.getWeaponName() + "\nWeapon Damage: " + weaponSlot.getWeaponDamage();
        String weaponStats2 = "\nWeapon Level: " + weaponSlot.getWeaponLevel() + "\nWeapon Double Cast Chance: " + weaponSlot.getDoubleCastChance() + "%";
        String weaponStats3 = "\nWeapon Heal Health Chance: " + weaponSlot.getHealHealthChance() + "%";

        return weaponStats1 + weaponStats2 + weaponStats3;
    }
}
