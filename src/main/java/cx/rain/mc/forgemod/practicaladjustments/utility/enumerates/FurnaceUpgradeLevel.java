package cx.rain.mc.forgemod.practicaladjustments.utility.enumerates;

public enum FurnaceUpgradeLevel {
    Primary(1),
    Advanced(2),
    Super(3);

    int level = 0;

    FurnaceUpgradeLevel(int lvl) {
        level = lvl;
    }

    public int getLevel() {
        return level;
    }
}
