package cx.rain.mc.forgemod.practicaladjustments.utility;

import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;

public interface IContainer {
    NonNullList<ItemStack> getInventory();
}
