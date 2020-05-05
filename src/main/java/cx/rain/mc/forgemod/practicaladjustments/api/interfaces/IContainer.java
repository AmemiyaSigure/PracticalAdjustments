package cx.rain.mc.forgemod.practicaladjustments.api.interfaces;

import net.minecraftforge.items.IItemHandler;

public interface IContainer {
    IItemHandler getInventory();
    int getMaxStackSize();
}
