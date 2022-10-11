// Resource objects which help with levelling the player's combat stats and weapon stats.
public class Resource {

    private String resourceName;
    private int levelIncrease;

    // Create a resource given its name and level increase.
    public Resource(String resourceName, int levelIncrease) {
        
        this.resourceName = resourceName;
        this.levelIncrease = levelIncrease;
    }

    // Methods to get values.
    public String getResourceName() {

        return resourceName;
    }

    public int getLevelIncrease() {

        return levelIncrease;
    }

}
