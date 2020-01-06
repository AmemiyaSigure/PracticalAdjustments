package cx.rain.mc.forgemod.practicaladjustments.creative.tab;

import cx.rain.mc.forgemod.practicaladjustments.item.Items;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;

public class TabPracticalAdjustments extends CreativeTabs {
    public TabPracticalAdjustments() {
        super("practical_adjustments");
    }

    @Override
    public ItemStack getTabIconItem() {
        return new ItemStack(Items.ITEMS.get("MortarAndPestle"));
    }
}
