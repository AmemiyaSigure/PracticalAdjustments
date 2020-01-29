package cx.rain.mc.forgemod.practicaladjustments.utility;

import net.minecraft.item.ItemStack;

public class ItemStackMerger {
    public static void doMerge(ItemStack origin, ItemStack newStack) {
        if (canMerge(origin, newStack)) {
            if (origin.getCount() + newStack.getCount() <= origin.getMaxStackSize()) {
                origin.setCount(origin.getCount() + newStack.getCount());
                newStack.setCount(0);
            } else {
                origin.setCount(origin.getMaxStackSize());
                newStack.setCount(newStack.getCount() - origin.getMaxStackSize() + origin.getCount());
            }
        }
    }

    public static boolean canMerge(ItemStack origin, ItemStack newStack) {
        if (origin.isItemEqual(newStack)) {
            return origin.getCount() < origin.getMaxStackSize();
        } else {
            return false;
        }
    }
}
