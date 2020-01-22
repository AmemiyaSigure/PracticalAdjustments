package cx.rain.mc.forgemod.practicaladjustments.creative.tab;

import cx.rain.mc.forgemod.practicaladjustments.PracticalAdjustments;
import net.minecraft.creativetab.CreativeTabs;

public class Tabs {
    public static CreativeTabs PRACTICAL_ADJUSTMENTS;

    public Tabs() {
        PracticalAdjustments.INSTANCE.getLogger().info("Registered creative tabs.");
        PRACTICAL_ADJUSTMENTS = new TabPracticalAdjustments();
    }
}
