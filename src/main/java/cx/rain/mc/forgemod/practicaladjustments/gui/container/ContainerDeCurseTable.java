package cx.rain.mc.forgemod.practicaladjustments.gui.container;

import cx.rain.mc.forgemod.practicaladjustments.gui.slot.SlotCursedOnly;
import cx.rain.mc.forgemod.practicaladjustments.gui.slot.SlotOblation;
import cx.rain.mc.forgemod.practicaladjustments.gui.slot.SlotOutput;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.items.ItemStackHandler;

import java.util.Iterator;

public class ContainerDeCurseTable extends Container {
    private ItemStackHandler items = new ItemStackHandler(3);
    private Slot slotCursed = null;
    private Slot slotOblation = null;
    private Slot slotOutput = null;

    public ContainerDeCurseTable(EntityPlayer player, BlockPos pos) {
        super();

        slotCursed = new SlotCursedOnly(items, 0, 44, 32) {
            @Override
            public void onSlotChanged() {
                if (!getItemHandler().getStackInSlot(0).isEmpty()
                        && !getItemHandler().getStackInSlot(1).isEmpty()) {

                    ItemStack output = getItemHandler().getStackInSlot(0).copy();
                    Iterator<NBTBase> i = output.getEnchantmentTagList().iterator();
                    while (i.hasNext()) {
                        NBTTagCompound enchant = (NBTTagCompound) i.next();
                        if (enchant.getShort("id") == 10
                                || enchant.getShort("id") == 71) {
                            i.remove();
                        }
                    }
                    items.setStackInSlot(2, output);
                } else {
                    items.setStackInSlot(2, ItemStack.EMPTY);
                }
                super.onSlotChanged();
            }
        };
        slotOblation = new SlotOblation(items, 1, 80, 50) {
            @Override
            public void onSlotChanged() {
                if (!getItemHandler().getStackInSlot(0).isEmpty()
                        && !getItemHandler().getStackInSlot(1).isEmpty()) {

                    ItemStack output = getItemHandler().getStackInSlot(0).copy();
                    Iterator<NBTBase> i = output.getEnchantmentTagList().iterator();
                    while (i.hasNext()) {
                        NBTTagCompound enchant = (NBTTagCompound) i.next();
                        if (enchant.getShort("id") == 10
                                || enchant.getShort("id") == 71) {
                            i.remove();
                        }
                    }
                    items.setStackInSlot(2, output);
                } else {
                    items.setStackInSlot(2, ItemStack.EMPTY);
                }
                super.onSlotChanged();
            }
        };
        slotOutput = new SlotOutput(items, 2, 116, 32) {
            @Override
            public ItemStack onTake(EntityPlayer thePlayer, ItemStack stack) {
                items.setStackInSlot(0, ItemStack.EMPTY);
                ItemStack oblation = getItemHandler().getStackInSlot(1).copy();
                oblation.setCount(oblation.getCount() - 1);
                items.setStackInSlot(1, oblation);

                thePlayer.playSound(new SoundEvent(new ResourceLocation("block.anvil.use")),
                        1.0F, 1.0F);
                return super.onTake(thePlayer, stack);
            }
        };

        addPlayerInventory(player.inventory);
        addContainerSlots();
    }

    private void addPlayerInventory(IInventory inventory) {
        for (int row = 0; row < 3; ++row) {
            for (int col = 0; col < 9; ++col) {
                int x = col * 18 + 8;
                int y = row * 18 + 90;
                addSlotToContainer(new Slot(inventory, col + row * 9 + 9, x, y));
            }
        }

        for (int row = 0; row < 9; ++row) {
            int x = row * 18 + 8;
            int y = 58 + 90;
            addSlotToContainer(new Slot(inventory, row, x, y));
        }
    }

    private void addContainerSlots() {
        addSlotToContainer(slotCursed);
        addSlotToContainer(slotOblation);
        addSlotToContainer(slotOutput);
    }

    @Override
    public boolean canInteractWith(EntityPlayer playerIn) {
        return playerIn.getDistanceSq(playerIn.getPosition().add(0.5D, 0.5D, 0.5D)) <= 64D;
    }

    @Override
    public ItemStack transferStackInSlot(EntityPlayer playerIn, int index) {
        return ItemStack.EMPTY;
    }

    @Override
    public void onContainerClosed(EntityPlayer playerIn) {
        World world = playerIn.world;
        if (!world.isRemote) {
            ItemStack i = slotCursed.getStack();
            EntityItem entityItem = new EntityItem(world, playerIn.posX, playerIn.posY, playerIn.posZ, i);
            world.spawnEntity(entityItem);
            i = slotOblation.getStack();
            entityItem = new EntityItem(world, playerIn.posX, playerIn.posY, playerIn.posZ, i);
            world.spawnEntity(entityItem);
        }
        super.onContainerClosed(playerIn);
    }
}
