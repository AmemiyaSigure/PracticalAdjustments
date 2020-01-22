package cx.rain.mc.forgemod.practicaladjustments.gui.container;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;

public class ContainerDeCurseTable extends Container {
    public ContainerDeCurseTable(EntityPlayer player, BlockPos pos) {
        super();

        drawPlayerInventory(player.inventory);
        drawContainerSlots();
    }

    @Override
    public boolean canInteractWith(EntityPlayer playerIn) {
        return playerIn.getDistanceSq(playerIn.getPosition().add(0.5D, 0.5D, 0.5D)) <= 64D;
    }

    private void drawPlayerInventory(IInventory inventory) {
        for (int row = 0; row < 3; ++row) {
            for (int col = 0; col < 9; ++col) {
                int x = col * 18 + 8;
                int y = row * 18 + 90;
                this.addSlotToContainer(new Slot(inventory, col + row * 9 + 10, x, y));
            }
        }

        for (int row = 0; row < 9; ++row) {
            int x = row * 18 + 8;
            int y = 58 + 90;
            this.addSlotToContainer(new Slot(inventory, row, x, y));
        }
    }

    private void drawContainerSlots() {

    }

    @Override
    public ItemStack transferStackInSlot(EntityPlayer playerIn, int index) {
        return super.transferStackInSlot(playerIn, index);
    }
}
