package cx.rain.mc.forgemod.practicaladjustments.gui.slot;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.SlotItemHandler;

import javax.annotation.Nonnull;

public class SlotCursedOnly extends SlotItemHandler {
    public SlotCursedOnly(IItemHandler itemHandler, int index, int xPosition, int yPosition) {
        super(itemHandler, index, xPosition, yPosition);
    }

    @Override
    public boolean isItemValid(@Nonnull ItemStack stack) {
        if (!stack.isItemEnchanted()) {
            return false;
        }

        for (NBTBase enchant : stack.getEnchantmentTagList()){
            NBTTagCompound nbt = (NBTTagCompound) enchant;
            Enchantment ench = Enchantment.getEnchantmentByID(nbt.getShort("id"));
            if (ench != null && ench.isCurse()) {
                return true;
            }
        }

        return false;
    }
}
