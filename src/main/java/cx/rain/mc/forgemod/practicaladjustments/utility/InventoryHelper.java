package cx.rain.mc.forgemod.practicaladjustments.utility;

import net.minecraft.init.Items;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.IPlantable;

public class InventoryHelper {
    public static int getFirstSlot(IInventory inv, int initialSlot) {
        if (initialSlot < 0) {
            return -1;
        }

        ItemStack initialTarget = inv.getStackInSlot(initialSlot);

        if (initialTarget != ItemStack.EMPTY && initialTarget.getCount() > 0) {
            return initialSlot;
        }

        int slot = 0;

        while (inv.getStackInSlot(slot) == ItemStack.EMPTY
                || inv.getStackInSlot(slot).getItem() == Items.AIR
                || !(inv.getStackInSlot(slot).getItem() instanceof IPlantable)
                || ((inv.getStackInSlot(slot)).getCount() <= 0 && slot < inv.getFieldCount())) {
            slot++;
            if (slot >= inv.getFieldCount()) {
                return -1;
            }
        }

        return slot;
    }
}
