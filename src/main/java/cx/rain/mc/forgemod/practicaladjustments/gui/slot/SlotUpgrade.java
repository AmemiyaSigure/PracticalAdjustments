package cx.rain.mc.forgemod.practicaladjustments.gui.slot;

import cx.rain.mc.forgemod.practicaladjustments.item.ItemFurnaceUpgrade;
import net.minecraft.item.ItemStack;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.SlotItemHandler;

import javax.annotation.Nonnull;

public class SlotUpgrade extends SlotItemHandler {
    public SlotUpgrade(IItemHandler itemHandler, int index, int xPosition, int yPosition) {
        super(itemHandler, index, xPosition, yPosition);
    }

    @Override
    public boolean isItemValid(@Nonnull ItemStack stack) {
        return stack.getItem() instanceof ItemFurnaceUpgrade;
    }
}
