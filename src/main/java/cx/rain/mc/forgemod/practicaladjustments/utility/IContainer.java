package cx.rain.mc.forgemod.practicaladjustments.utility;

import net.minecraftforge.items.IItemHandler;

public interface IContainer {
    IItemHandler getInventory();
    int getMaxStackSize();
}
