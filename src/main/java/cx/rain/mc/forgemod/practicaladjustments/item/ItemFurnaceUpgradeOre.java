package cx.rain.mc.forgemod.practicaladjustments.item;

import cx.rain.mc.forgemod.practicaladjustments.api.enumerates.FurnaceUpgradeLevel;

public class ItemFurnaceUpgradeOre extends ItemFurnaceUpgrade {
    private FurnaceUpgradeLevel level = FurnaceUpgradeLevel.Primary;

    public ItemFurnaceUpgradeOre(FurnaceUpgradeLevel lvl) {
        super();
        level = lvl;
    }

    public FurnaceUpgradeLevel getLevel() {
        return level;
    }
}
