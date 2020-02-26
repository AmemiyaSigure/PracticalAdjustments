package cx.rain.mc.forgemod.practicaladjustments.tile.entity;

import cx.rain.mc.forgemod.practicaladjustments.block.BlockFurnace;
import cx.rain.mc.forgemod.practicaladjustments.gui.GuiFurnace;
import cx.rain.mc.forgemod.practicaladjustments.utility.IContainer;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.FurnaceRecipes;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ITickable;
import net.minecraft.util.NonNullList;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextComponentTranslation;

public class TileEntityFurnace extends TileEntity implements ITickable, IContainer {
    private boolean isExpend = false;

    private NonNullList<ItemStack> inventory = NonNullList.withSize(9, ItemStack.EMPTY);
    private String customName = null;

    private int burnTime = 0;
    private int currentBurnTime = 0;
    private int cookTime = 0;
    private int totalCookTIme = 0;

    public TileEntityFurnace() {
        super();
    }

    @Override
    public void readFromNBT(NBTTagCompound compound) {
        super.readFromNBT(compound);
        isExpend = compound.getBoolean("Expend");
        customName = compound.getString("CustomName");
    }

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound compound) {
        compound.setBoolean("Expend", isExpend);
        if (hasCustomName()) {
            compound.setString("CustomName", customName);
        }
        return super.writeToNBT(compound);
    }

    @Override
    public NBTTagCompound getUpdateTag() {
        return writeToNBT(new NBTTagCompound());
    }

    @Override
    public void update() {
        getUpdateTag();
    }

    public void setExpend(boolean expend) {
        isExpend = expend;
    }

    public boolean isExpend() {
        return isExpend;
    }

    public boolean isBurning() {
        return burnTime > 0;
    }

    public int getCookTime(ItemStack item, ItemStack fuel) {
        switch (((BlockFurnace) blockType).getFurnaceType()) {
            case Iron:
                return 150;
            case Golden:
                return 100;
            case Diamond:
                return 50;
            case SuperFurnace:
                return 25;
            default:
                return 200;
        }
    }

    public boolean canSmelt() {
        // No input.
        if (inventory.get(0).isEmpty()) {
            return false;
        } else if (isExpend && inventory.get(1).isEmpty()) {
            return false;
        }

        // No fuel.
        if (inventory.get(2).isEmpty()) {
            return false;
        } else if (isExpend && inventory.get(3).isEmpty()) {
            return false;
        }

        // No result.
        ItemStack result = getSmeltingResult(inventory.get(0));
        ItemStack resultExpend = getSmeltingResult(inventory.get(1));

        if (!isExpend) {
            if (result.isEmpty()) {
                return false;
            }
        } else {
            if (result.isEmpty() && resultExpend.isEmpty()) {
                return false;
            }
        }

        ItemStack slotOutput = inventory.get(4);
        ItemStack slotOutputExpend = inventory.get(5);

        if (isExpend) {
            // Output slot and expand output slot is empty.
            if (slotOutput.isEmpty() || slotOutputExpend.isEmpty()) {
                return true;
            }

            // Result that different with items in output slot and expand output slot is empty.
            // (Try to place the result to two slots.)
            if (!slotOutput.isItemEqual(result)
                    && !slotOutputExpend.isItemEqual(result)
                    && !slotOutput.isItemEqual(resultExpend)
                    && !slotOutputExpend.isItemEqual(resultExpend)) {
                return false;
            }

            // Check if output slots are equals.
            // If equals, check sum remaining space.
            // Otherwise, check each.
            if (slotOutput.isItemEqual(slotOutputExpend)) {
                int outputSpaceAll = slotOutput.getCount() + slotOutputExpend.getCount()
                                        + result.getCount() + resultExpend.getCount();
                return outputSpaceAll <= (getMaxStackSize() * 2)
                        && outputSpaceAll <= (slotOutput.getMaxStackSize() + slotOutputExpend.getMaxStackSize());
            } else {
                if (slotOutput.isItemEqual(result) && slotOutputExpend.isItemEqual(resultExpend)) {
                    int outputRemain = slotOutput.getCount() + result.getCount();
                    int outputExpendRemain = slotOutputExpend.getCount() + resultExpend.getCount();
                    return outputRemain <= getMaxStackSize()
                            && outputRemain <= slotOutput.getMaxStackSize()
                            && outputExpendRemain <= getMaxStackSize()
                            && outputExpendRemain <= slotOutputExpend.getMaxStackSize();
                } else if (slotOutput.isItemEqual(resultExpend) && slotOutputExpend.isItemEqual(result)) {
                    int outputRemain = slotOutput.getCount() + resultExpend.getCount();
                    int outputExpendRemain = slotOutputExpend.getCount() + result.getCount();
                    return outputRemain <= getMaxStackSize()
                            && outputRemain <= slotOutput.getMaxStackSize()
                            && outputExpendRemain <= getMaxStackSize()
                            && outputExpendRemain <= slotOutputExpend.getMaxStackSize();
                } else {
                    return false;
                }
            }
        } else {
            // Output slot is empty.
            if (slotOutput.isEmpty()) {
                return true;
            }

            // Result that different with items in output slot is empty.
            // (Try to place the result to the slot.)
            if (!slotOutput.isItemEqual(result)) {
                return false;
            }

            // Check the output slot remaining space.
            int outputRemain = slotOutput.getCount() + result.getCount();
            return outputRemain <= getMaxStackSize()
                    && outputRemain <= slotOutput.getMaxStackSize();
        }
    }

    public void smelt() {
        if (canSmelt()) {
            if (isExpend) {
                ItemStack input = inventory.get(0);
                ItemStack inputExpend = inventory.get(1);
                ItemStack result = getSmeltingResult(input);
                ItemStack resultExpend = getSmeltingResult(inputExpend);
                ItemStack output = inventory.get(4);
                ItemStack outputExpend = inventory.get(5);

                // Check if items in input slots are equals.
                if (!input.isItemEqual(inputExpend)) {
                    if (output.isEmpty() && outputExpend.isEmpty()) {

                    } else if (output.isItemEqual(result) && outputExpend.isEmpty()) {

                    } else if (output.isEmpty() && outputExpend.isItemEqual(result)) {

                    } else if (output.isItemEqual(result) && outputExpend.isEmpty()) {

                    }

                } else {
                    if (output.isEmpty() && !outputExpend.isEmpty()) {

                    }
                }

                // Decrease count of input items.
                input.shrink(1);
                inputExpend.shrink(1);
            } else {
                ItemStack input = inventory.get(0);
                ItemStack result = getSmeltingResult(input);
                ItemStack output = inventory.get(4);

                // Check output.
                if (output.isEmpty()) {
                    inventory.set(4, result.copy());
                } else if (output.isItemEqual(result)) {
                    output.grow(result.getCount());
                }

                // Decrease count of input items.
                input.shrink(1);
            }
        }
    }

    public ItemStack getSmeltingResult(ItemStack input) {
        return FurnaceRecipes.instance().getSmeltingResult(input);
    }

    public int getMaxStackSize() {
        return 64;
    }

    public String getName() {
        return hasCustomName() ? customName :
                GuiFurnace.getDefaultTitleKey(((BlockFurnace) blockType).getFurnaceType());
    }

    public boolean hasCustomName() {
        return customName != null && !(customName.isEmpty());
    }

    public void setCustomName(String name) {
        customName = name;
    }

    public String getCustomName() {
        return customName;
    }

    public ITextComponent getDisplayName() {
        return hasCustomName() ? new TextComponentString(getName()) : new TextComponentTranslation(getName());
    }

    @Override
    public NonNullList<ItemStack> getInventory() {
        return inventory;
    }

    /*
    public int getSizeInventory() {
        return inventory.size();
    }

    public boolean isEmpty() {
        for (ItemStack i : inventory) {
            if (!i.isEmpty()) {
                return false;
            }
        }
        return true;
    }

    public ItemStack getStackInSlot(int index) {
        return inventory.get(index);
    }

    public ItemStack decrStackSize(int index, int count) {
        return ItemStackHelper.getAndSplit(inventory, index, count);
    }

    public ItemStack removeStackFromSlot(int index) {
        return ItemStackHelper.getAndRemove(inventory, index);
    }

    public int getInventoryStackLimit() {
        return 64;
    }

    public boolean isUsableByPlayer(EntityPlayer player) {
        return false;
    }

    public void openInventory(EntityPlayer player) {

    }

    public void closeInventory(EntityPlayer player) {

    }

    public boolean isItemValidForSlot(int index, ItemStack stack) {
        return false;
    }

    public int getField(int id) {
        return 0;
    }

    public void setField(int id, int value) {

    }

    public int getFieldCount() {
        return 0;
    }

    public void clear() {

    }
    */
}
