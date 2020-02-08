package cx.rain.mc.forgemod.practicaladjustments.gui.container;

import cx.rain.mc.forgemod.practicaladjustments.block.BlockFurnace;
import cx.rain.mc.forgemod.practicaladjustments.utility.enumerates.FurnaceType;
import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.util.math.BlockPos;

public class ContainerFurnace extends Container {
    private BlockFurnace furnace = null;
    private FurnaceType furnaceType = null;
    private int upgradeSlots = 1;
    private boolean isExpended = false;

    public ContainerFurnace(EntityPlayer player, BlockPos pos) {
        super();

        Block block = Minecraft.getMinecraft().world.getBlockState(pos).getBlock();
        if (!(block instanceof BlockFurnace)) {
            throw new IllegalArgumentException("Hey, Potato! It must be a instance of BlockFurnace.");
        }
        furnace = (BlockFurnace) block;
        furnaceType = furnace.getFurnaceType();
        upgradeSlots = furnace.getUpgradeSlots();

        addPlayerInventory(player.inventory);
        addContainerSlots();
    }

    private void addPlayerInventory(IInventory inventory) {
        for (int row = 0; row < 3; ++row) {
            for (int col = 0; col < 9; ++col) {
                int x = col * 18 + 8;
                int y = row * 18 + 84;
                addSlotToContainer(new Slot(inventory, col + row * 9 + 10, x, y));
            }
        }

        for (int row = 0; row < 9; ++row) {
            int x = row * 18 + 8;
            int y = 58 + 84;
            addSlotToContainer(new Slot(inventory, row, x, y));
        }
    }

    private void addContainerSlots() {

    }

    @Override
    public boolean canInteractWith(EntityPlayer playerIn) {
        return playerIn.getDistanceSq(playerIn.getPosition().add(0.5D, 0.5D, 0.5D)) <= 64D;
    }

    @Override
    public void onContainerClosed(EntityPlayer playerIn) {
        super.onContainerClosed(playerIn);

    }

    public int getUpgradeSlots() {
        return upgradeSlots;
    }

    public boolean isExpended() {
        return isExpended;
    }

    public FurnaceType getFurnaceType() {
        return furnaceType;
    }
}
