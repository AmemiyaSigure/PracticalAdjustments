package cx.rain.mc.forgemod.practicaladjustments.gui.container;

import cx.rain.mc.forgemod.practicaladjustments.block.BlockFurnace;
import cx.rain.mc.forgemod.practicaladjustments.gui.slot.SlotFuel;
import cx.rain.mc.forgemod.practicaladjustments.gui.slot.SlotOutput;
import cx.rain.mc.forgemod.practicaladjustments.gui.slot.SlotUpgrade;
import cx.rain.mc.forgemod.practicaladjustments.tile.entity.TileEntityFurnace;
import cx.rain.mc.forgemod.practicaladjustments.utility.enumerates.FurnaceType;
import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.items.ItemStackHandler;
import net.minecraftforge.items.SlotItemHandler;

public class ContainerFurnace extends Container {
    private BlockFurnace furnace = null;
    private TileEntityFurnace tileFurnace = null;
    private FurnaceType furnaceType = null;
    private int upgradeSlots = 1;
    private boolean isExpended = false;
    private IInventory playerInv = null;

    private ItemStackHandler items = new ItemStackHandler(6);
    private Slot slotInput = null;
    private Slot slotInputExpend = null;
    private Slot slotFuel = null;
    private Slot slotFuelExpend = null;
    private Slot slotOutput = null;
    private Slot slotOutputExpend = null;

    private ItemStackHandler upgrades = new ItemStackHandler(3);
    private Slot slotUpgrade1 = null;
    private Slot slotUpgrade2 = null;
    private Slot slotUpgrade3 = null;

    public ContainerFurnace(EntityPlayer player, BlockPos pos) {
        super();

        Block block = player.world.getBlockState(pos).getBlock();
        if (!(block instanceof BlockFurnace)) {
            throw new IllegalArgumentException("Hey, Potato! It must be a instance of BlockFurnace.");
        }

        TileEntity tile = player.world.getTileEntity(pos);
        if (!(tile instanceof TileEntityFurnace)) {
            throw new IllegalArgumentException("Hey, Potato! It must be a instance of TileEntityFurnace.");
        }

        playerInv = player.inventory;

        furnace = (BlockFurnace) block;
        tileFurnace = (TileEntityFurnace) tile;

        furnaceType = furnace.getFurnaceType();
        upgradeSlots = furnace.getUpgradeSlots();
        isExpended = tileFurnace.isExpend();

        addPlayerInventory(playerInv);
        setSlots(isExpended, upgradeSlots);
        addContainerSlots(isExpended, upgradeSlots);
    }

    private void addPlayerInventory(IInventory inventory) {
        for (int row = 0; row < 3; ++row) {
            for (int col = 0; col < 9; ++col) {
                int x = col * 18 + 8;
                int y = row * 18 + 84;
                addSlotToContainer(new Slot(inventory, col + row * 9 + 9, x, y));
            }
        }

        for (int row = 0; row < 9; ++row) {
            int x = row * 18 + 8;
            int y = 58 + 84;
            addSlotToContainer(new Slot(inventory, row, x, y));
        }
    }

    private void setSlots(boolean expend, int upgradeSlotsCount) {
        if (!expend) {
            slotInput = new SlotItemHandler(items, 0, 62, 17);
            slotFuel = new SlotFuel(items, 2, 62, 53);
            slotOutput = new SlotOutput(items, 4, 122, 35);
        } else {
            slotInput = new SlotItemHandler(items, 0, 53, 17);
            slotInputExpend = new SlotItemHandler(items, 1, 71, 17);

            slotFuel = new SlotFuel(items, 2, 53, 53);
            slotFuelExpend = new SlotFuel(items, 3, 71, 53);

            slotOutput = new SlotOutput(items, 4, 122, 22);
            slotOutputExpend = new SlotOutput(items, 5, 122, 48);
        }

        if (upgradeSlotsCount == 1) {
            slotUpgrade1 = new SlotUpgrade(upgrades, 0, 8, 35);
        } else if (upgradeSlotsCount == 2) {
            slotUpgrade1 = new SlotUpgrade(upgrades, 0, 8, 26);
            slotUpgrade2 = new SlotUpgrade(upgrades, 1, 8, 44);
        } else if (upgradeSlotsCount == 3) {
            slotUpgrade1 = new SlotUpgrade(upgrades, 0, 8, 17);
            slotUpgrade2 = new SlotUpgrade(upgrades, 1, 8, 35);
            slotUpgrade3 = new SlotUpgrade(upgrades, 2, 8, 53);
        }
    }

    private void addContainerSlots(boolean expend, int upgradeSlotsCount) {
        addSlotToContainer(slotInput);
        addSlotToContainer(slotFuel);
        addSlotToContainer(slotOutput);
        if (expend) {
            addSlotToContainer(slotInputExpend);
            addSlotToContainer(slotFuelExpend);
            addSlotToContainer(slotOutputExpend);
        }

        if (upgradeSlotsCount == 1) {
            addSlotToContainer(slotUpgrade1);
        } else if (upgradeSlotsCount == 2) {
            addSlotToContainer(slotUpgrade1);
            addSlotToContainer(slotUpgrade2);
        } else if (upgradeSlotsCount == 3) {
            addSlotToContainer(slotUpgrade1);
            addSlotToContainer(slotUpgrade2);
            addSlotToContainer(slotUpgrade3);
        }
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
