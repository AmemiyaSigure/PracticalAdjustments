package cx.rain.mc.forgemod.practicaladjustments.api.base;

import cx.rain.mc.forgemod.practicaladjustments.PracticalAdjustments;
import cx.rain.mc.forgemod.practicaladjustments.gui.Guis;
import cx.rain.mc.forgemod.practicaladjustments.gui.inventory.SeedPlanterInventory;
import cx.rain.mc.forgemod.practicaladjustments.utility.InventoryHelper;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.Arrays;
import java.util.List;

public abstract class SeedPlanterBase extends Item {
    private int level = 0;
    private int lines = 0;
    private int range = 0;

    public SeedPlanterBase(int lvl, int guiLines) {
        super();

        setMaxStackSize(1);

        level = lvl;
        lines = guiLines;
        range = 2 * lvl + 1;
    }

    public int getGuiSize() {
        return lines * 9;
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer playerIn, EnumHand handIn) {
        if (playerIn.isSneaking()) {
            if (!worldIn.isRemote) {
                List<String> guis = Arrays.asList(Guis.GUIS.keySet().toArray(new String[Guis.GUIS.keySet().size()]));
                playerIn.openGui(PracticalAdjustments.INSTANCE, guis.indexOf("seed_planter_basic"), worldIn,
                        (int) playerIn.posX, (int) playerIn.posY, (int) playerIn.posZ);
            }
            return new ActionResult<>(EnumActionResult.SUCCESS, playerIn.getHeldItem(handIn));
        }
        return super.onItemRightClick(worldIn, playerIn, handIn);
    }

    @Override
    public EnumActionResult onItemUse(EntityPlayer player, World worldIn, BlockPos pos, EnumHand hand,
                                      EnumFacing facing, float hitX, float hitY, float hitZ) {
        if (player.isSneaking()) {
            return EnumActionResult.PASS;
        }

        ItemStack item = player.getHeldItem(hand);
        if (!(item.getItem() instanceof SeedPlanterBase)) {
            return  EnumActionResult.PASS;
        }

        IInventory inventory = new SeedPlanterInventory(item);
        if (canPlant()) {
            plant();
        }

        return EnumActionResult.SUCCESS;
    }

    public boolean canPlant(IInventory inv, World world, int x, int y, int z, EnumFacing.AxisDirection direction) {
        int nextSlot = InventoryHelper.getFirstSlot(inv, getFirstSlot(inv));
        if (nextSlot >= 0) {
            ItemStack targetItem = inv.func_70301_a(nextSlot);
            assert targetItem != null;
            assert targetItem.func_77973_b() instanceof IPlantable;
            IPlantable targetPlantable = (IPlantable)targetItem.func_77973_b();

            return PlantingLogic.targetedSuitableFarmland(world, x, y, z, direction, targetPlantable);
        }
        return false;
    }

}
