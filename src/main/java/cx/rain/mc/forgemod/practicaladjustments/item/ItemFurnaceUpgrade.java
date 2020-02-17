package cx.rain.mc.forgemod.practicaladjustments.item;

import cx.rain.mc.forgemod.practicaladjustments.creative.tab.Tabs;
import net.minecraft.item.Item;

public class ItemFurnaceUpgrade extends Item {
    public ItemFurnaceUpgrade() {
        super();
        setCreativeTab(Tabs.PRACTICAL_ADJUSTMENTS);
        setMaxStackSize(1);
    }
}
