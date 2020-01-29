package cx.rain.mc.forgemod.practicaladjustments.item;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class ItemMortarAndPestle extends Item {
    public ItemMortarAndPestle() {
        super();
        setMaxStackSize(1);
        setMaxDamage(60);
        setNoRepair();
    }

    @Override
    public boolean hasContainerItem(ItemStack stack) {
        return true;
    }

    @Override
    public ItemStack getContainerItem(ItemStack itemStack) {
        return new ItemStack(itemStack.getItem(), 1, itemStack.getItemDamage() + 1);
    }
}
