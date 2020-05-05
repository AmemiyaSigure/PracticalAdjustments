package cx.rain.mc.forgemod.practicaladjustments.utility;

import cx.rain.mc.forgemod.practicaladjustments.api.interfaces.IContainer;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.Random;

public class ContainerHelper {
    private static final Random RANDOM = new Random();

    public static void dropInventoryItems(World worldIn, BlockPos pos, IContainer inventory)
    {
        dropInventoryItems(worldIn, (double)pos.getX(), (double)pos.getY(), (double)pos.getZ(), inventory);
    }

    public static void dropInventoryItems(World worldIn, Entity entityAt, IContainer inventory)
    {
        dropInventoryItems(worldIn, entityAt.posX, entityAt.posY, entityAt.posZ, inventory);
    }

    private static void dropInventoryItems(World worldIn, double x, double y, double z, IContainer inventory)
    {
        for (int i = 0; i < inventory.getInventory().getSlots(); i++)
        {
            ItemStack itemStack = inventory.getInventory().getStackInSlot(i);
            if (!itemStack.isEmpty())
            {
                spawnItemStack(worldIn, x, y, z, itemStack);
            }
        }
    }

    public static void spawnItemStack(World worldIn, double x, double y, double z, ItemStack stack)
    {
        float f = RANDOM.nextFloat() * 0.8F + 0.1F;
        float f1 = RANDOM.nextFloat() * 0.8F + 0.1F;
        float f2 = RANDOM.nextFloat() * 0.8F + 0.1F;

        while (!stack.isEmpty())
        {
            EntityItem entityitem = new EntityItem(worldIn, x + (double)f, y + (double)f1, z + (double)f2, stack.splitStack(RANDOM.nextInt(21) + 10));
            float f3 = 0.05F;
            entityitem.motionX = RANDOM.nextGaussian() * 0.05000000074505806D;
            entityitem.motionY = RANDOM.nextGaussian() * 0.05000000074505806D + 0.20000000298023224D;
            entityitem.motionZ = RANDOM.nextGaussian() * 0.05000000074505806D;
            worldIn.spawnEntity(entityitem);
        }
    }
}
