package cx.rain.mc.forgemod.practicaladjustments.tile.entity;

import cx.rain.mc.forgemod.practicaladjustments.block.BlockFurnace;
import cx.rain.mc.forgemod.practicaladjustments.gui.GuiFurnace;
import cx.rain.mc.forgemod.practicaladjustments.utility.IContainer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.FurnaceRecipes;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ITickable;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemStackHandler;

public class TileEntityFurnace extends TileEntity implements ITickable, IContainer {
    private boolean isExpend = false;

    private ItemStackHandler items = new ItemStackHandler(9);
    private String customName = null;

    private int burnTime = 0;
    private int currentBurnTime = 0;
    private int cookTime = 0;
    private int totalCookTime = 0;

    public TileEntityFurnace() {
        super();
    }

    @Override
    public void readFromNBT(NBTTagCompound compound) {
        super.readFromNBT(compound);
        items.deserializeNBT((NBTTagCompound) compound.getTag("Items"));

        isExpend = compound.getBoolean("Expend");
        burnTime = compound.getInteger("BurnTime");
        currentBurnTime = compound.getInteger("CurrentBurnTime");
        cookTime = compound.getInteger("CookTime");
        totalCookTime = compound.getInteger("TotalCookTIme");

        if (compound.hasKey("CustomName")) {
            customName = compound.getString("CustomName");
        }
    }

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound compound) {
        compound.setTag("Items", items.serializeNBT());

        compound.setBoolean("Expend", isExpend);
        compound.setInteger("BurnTime", burnTime);
        compound.setInteger("CurrentBurnTime", currentBurnTime);
        compound.setInteger("CookTime", cookTime);
        compound.setInteger("TotalCookTIme", totalCookTime);

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
        boolean prevIsBurning = isBurning();
        boolean nowIsBurning = false;

        if (isBurning()) {
            --burnTime;
        }

        if (isExpend) {
            // Todo
            return;
        } else {
            if (!world.isRemote) {
                ItemStack fuel = items.getStackInSlot(2);

                if (isBurning()
                        || (!fuel.isEmpty() && !items.getStackInSlot(0).isEmpty())) {
                    // Not burning but can burn.
                    if (!isBurning() && canSmelt()) {
                        burnTime = getFuelBurnTime(fuel);
                        currentBurnTime = burnTime;

                        if (isBurning()) {
                            nowIsBurning = true;

                            if (!fuel.isEmpty()) {
                                Item fuelItem = fuel.getItem();
                                fuel.shrink(1);

                                if (fuel.isEmpty()) {
                                    items.setStackInSlot(2, fuelItem.getContainerItem(fuel));
                                }
                            }
                        }
                    }

                    // Burning and inputs can be burnt.
                    if (isBurning() && canSmelt()) {
                        ++cookTime;

                        if (cookTime == totalCookTime) {
                            cookTime = 0;
                            totalCookTime = getCookTime();
                            smelt();
                            nowIsBurning = true;
                        }
                    } else {
                        cookTime = 0;
                    }
                } else if (!isBurning() && cookTime > 0) {
                    // Can't burn and cooking.
                    cookTime = MathHelper.clamp(cookTime - 2, 0, totalCookTime);
                }

                if (prevIsBurning != isBurning()) {
                    BlockFurnace.setState(isBurning(), world, pos);
                }
            }

            if (nowIsBurning) {
                markDirty();
            }
        }

        getUpdateTag();
    }

    public int getCookTime() {
        switch (((BlockFurnace) getBlockType()).getFurnaceType()) {
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

    // Hope no problem.
    // Fixme
    public boolean canSmelt2() {
        // No input.
        if (items.getStackInSlot(0).isEmpty()) {
            return false;
        } else if (isExpend && items.getStackInSlot(1).isEmpty()) {
            return false;
        }

        // No fuel.
        if (items.getStackInSlot(2).isEmpty()) {
            return false;
        } else if (isExpend && items.getStackInSlot(3).isEmpty()) {
            return false;
        }

        // No result.
        ItemStack result = getSmeltingResult(items.getStackInSlot(0));
        ItemStack resultExpend = getSmeltingResult(items.getStackInSlot(1));

        if (!isExpend) {
            if (result.isEmpty()) {
                return false;
            }
        } else {
            if (result.isEmpty() && resultExpend.isEmpty()) {
                return false;
            }
        }

        ItemStack slotOutput = items.getStackInSlot(4);
        ItemStack slotOutputExpend = items.getStackInSlot(5);

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

    // Hope no problem.
    // Todo
    public boolean canSmelt() {
        if (!isExpend) {
            // Check empty.
            ItemStack input = items.getStackInSlot(0);
            ItemStack result = getSmeltingResult(input);
            ItemStack fuel = items.getStackInSlot(3);
            if (input.isEmpty()
                || result.isEmpty()
                || fuel.isEmpty()) {
                return false;
            }

            // Check can add result.
            ItemStack output = items.getStackInSlot(4);
            if (output.isEmpty()) {
                return true;
            }
            if (!output.isItemEqual(result)) {
                return false;
            }
            int additionCount = output.getCount() + result.getCount();
            return additionCount <= getMaxStackSize() && additionCount <= output.getMaxStackSize();


        } else {
            // Check empty.
            ItemStack input = items.getStackInSlot(0);
            ItemStack inputExpend = items.getStackInSlot(1);
            ItemStack result = getSmeltingResult(items.getStackInSlot(0));
            ItemStack resultExpend = getSmeltingResult(items.getStackInSlot(1));
            ItemStack fuel = items.getStackInSlot(3);
            ItemStack fuelExpend = items.getStackInSlot(4);
            if ((input.isEmpty() && inputExpend.isEmpty())
                || (result.isEmpty() && resultExpend.isEmpty())
                || (fuel.isEmpty() && fuelExpend.isEmpty())) {
                return false;
            }

            // Check can add result.
            ItemStack output = items.getStackInSlot(4);
            ItemStack outputExpend = items.getStackInSlot(5);

            // Todo
            return false;
        }
    }

    // God bless it.
    public void smelt() {
        if (canSmelt()) {
            if (!isExpend) {
                ItemStack input = items.getStackInSlot(0);
                ItemStack result = getSmeltingResult(input);
                ItemStack output = items.getStackInSlot(4);

                // Check output.
                if (output.isEmpty()) {
                    items.setStackInSlot(4, result.copy());
                } else if (output.isItemEqual(result)) {
                    output.grow(result.getCount());
                }

                // Decrease count of input items.
                input.shrink(1);
            } else {
                // Todo
                /*
                ItemStack input = items.getStackInSlot(0);
                ItemStack inputExpend = items.getStackInSlot(1);
                ItemStack result = getSmeltingResult(input);
                ItemStack resultExpend = getSmeltingResult(inputExpend);
                ItemStack output = items.getStackInSlot(4);
                ItemStack outputExpend = items.getStackInSlot(5);

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
                 */
            }
        }
    }

    public ItemStack getSmeltingResult(ItemStack input) {
        return FurnaceRecipes.instance().getSmeltingResult(input);
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

    @Override
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

    public ITextComponent getDisplayName() {
        return hasCustomName() ? new TextComponentString(getName()) : new TextComponentTranslation(getName());
    }

    @Override
    public IItemHandler getInventory() {
        return items;
    }

    public int getTotalCookTime() {
        return totalCookTime;
    }

    public int getBurnTime() {
        return burnTime;
    }

    public int getCurrentBurnTime() {
        return currentBurnTime;
    }

    public static int getFuelBurnTime(ItemStack fuel) {
        if (fuel.isEmpty()) {
            return 0;
        }
        return GameRegistry.getFuelValue(fuel);
    }

    public static boolean isFuel(ItemStack itemStack) {
        return getFuelBurnTime(itemStack) > 0;
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
        return items.getStackInSlot(index);
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
