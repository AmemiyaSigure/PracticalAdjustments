package cx.rain.mc.forgemod.practicaladjustments.gui.container;

import cx.rain.mc.forgemod.practicaladjustments.block.BlockFurnace;
import cx.rain.mc.forgemod.practicaladjustments.gui.slot.SlotFuel;
import cx.rain.mc.forgemod.practicaladjustments.gui.slot.SlotOutput;
import cx.rain.mc.forgemod.practicaladjustments.gui.slot.SlotUpgrade;
import cx.rain.mc.forgemod.practicaladjustments.tile.entity.TileEntityFurnace;
import cx.rain.mc.forgemod.practicaladjustments.utility.enumerates.FurnaceType;
import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IContainerListener;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.SlotItemHandler;

public class ContainerFurnace extends Container {
    private BlockFurnace furnace = null;
    private TileEntityFurnace tileFurnace = null;
    private int upgradeSlots = 1;
    private boolean isExpended = false;
    private EntityPlayer player = null;

    private int cookTime = 0;
    private int totalCookTime = 0;
    private int burnTime = 0;
    private int currentBurnTime = 0;

    public ContainerFurnace(EntityPlayer playerIn, BlockPos pos) {
        super();

        Block block = playerIn.world.getBlockState(pos).getBlock();
        if (!(block instanceof BlockFurnace)) {
            throw new IllegalArgumentException("Hey, Potato! It must be a instance of BlockFurnace.");
        }

        TileEntity tile = playerIn.world.getTileEntity(pos);
        if (!(tile instanceof TileEntityFurnace)) {
            throw new IllegalArgumentException("Hey, Potato! It must be a instance of TileEntityFurnace.");
        }

        player = playerIn;

        furnace = (BlockFurnace) block;
        tileFurnace = (TileEntityFurnace) tile;

        upgradeSlots = furnace.getUpgradeSlots();
        isExpended = tileFurnace.isExpend();

        addPlayerInventory(player.inventory);
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

    private void addContainerSlots(boolean expend, int upgradeSlotsCount) {
        IItemHandler items = tileFurnace.getInventory();

        if (!expend) {
            addSlotToContainer(new SlotItemHandler(items, 0, 62, 17));
            addSlotToContainer(new SlotFuel(items, 2, 62, 53));
            addSlotToContainer(new SlotOutput(items, 4, 122, 35));
        } else {
            addSlotToContainer(new SlotItemHandler(items, 0, 53, 17));
            addSlotToContainer(new SlotItemHandler(items, 1, 71, 17));
            addSlotToContainer(new SlotFuel(items, 2, 53, 53));
            addSlotToContainer(new SlotFuel(items, 3, 71, 53));
            addSlotToContainer(new SlotOutput(items, 4, 122, 22));
            addSlotToContainer(new SlotOutput(items, 5, 122, 48));
        }

        if (upgradeSlotsCount == 1) {
            addSlotToContainer(new SlotUpgrade(items, 6, 8, 35));
        } else if (upgradeSlotsCount == 2) {
            addSlotToContainer(new SlotUpgrade(items, 6, 8, 26));
            addSlotToContainer(new SlotUpgrade(items, 7, 8, 44));
        } else if (upgradeSlotsCount == 3) {
            addSlotToContainer(new SlotUpgrade(items, 6, 8, 17));
            addSlotToContainer(new SlotUpgrade(items, 7, 8, 35));
            addSlotToContainer(new SlotUpgrade(items, 8, 8, 53));
        }
    }

    @Override
    public void addListener(IContainerListener listener) {
        super.addListener(listener);
        listener.sendWindowProperty(this, cookTime, tileFurnace.getCookTime());
        listener.sendWindowProperty(this, totalCookTime, tileFurnace.getTotalCookTime());
        listener.sendWindowProperty(this, burnTime, tileFurnace.getBurnTime());
        listener.sendWindowProperty(this, currentBurnTime, tileFurnace.getCurrentBurnTime());
    }

    @Override
    public void detectAndSendChanges() {
        super.detectAndSendChanges();

        for (IContainerListener listener : listeners) {
            if (cookTime != tileFurnace.getCookTime()) {
                listener.sendWindowProperty(this, cookTime, tileFurnace.getCookTime());
            }
            if (totalCookTime != tileFurnace.getTotalCookTime()) {
                listener.sendWindowProperty(this, totalCookTime, tileFurnace.getTotalCookTime());
            }
            if (burnTime != tileFurnace.getCookTime()) {
                listener.sendWindowProperty(this, burnTime, tileFurnace.getBurnTime());
            }
            if (currentBurnTime != tileFurnace.getCookTime()) {
                listener.sendWindowProperty(this, currentBurnTime, tileFurnace.getCurrentBurnTime());
            }
        }

        cookTime = tileFurnace.getCookTime();
        totalCookTime = tileFurnace.getTotalCookTime();
        burnTime = tileFurnace.getBurnTime();
        currentBurnTime = tileFurnace.getCurrentBurnTime();
    }

    @Override
    public boolean canInteractWith(EntityPlayer playerIn) {
        return playerIn.getDistanceSq(playerIn.getPosition().add(0.5D, 0.5D, 0.5D)) <= 64D;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void updateProgressBar(int id, int data) {
        // Todo
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
        return furnace.getFurnaceType();
    }

    public String getName() {
        return tileFurnace.getName();
    }

    public boolean hasCustomName() {
        return tileFurnace.hasCustomName();
    }

    public EntityPlayer getPlayer() {
        return player;
    }

    public TileEntityFurnace getTile() {
        return tileFurnace;
    }
}
