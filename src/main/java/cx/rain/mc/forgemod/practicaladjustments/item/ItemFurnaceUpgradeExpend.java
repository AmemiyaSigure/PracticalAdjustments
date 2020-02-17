package cx.rain.mc.forgemod.practicaladjustments.item;

import cx.rain.mc.forgemod.practicaladjustments.creative.tab.Tabs;
import cx.rain.mc.forgemod.practicaladjustments.tile.entity.TileEntityFurnace;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class ItemFurnaceUpgradeExpend extends Item {
    public ItemFurnaceUpgradeExpend() {
        super();
        setCreativeTab(Tabs.PRACTICAL_ADJUSTMENTS);
        setMaxStackSize(1);
    }

    @Override
    public EnumActionResult onItemUse(EntityPlayer player, World worldIn, BlockPos pos, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
        TileEntity tile =  worldIn.getTileEntity(pos);
        if (tile instanceof TileEntityFurnace) {
            TileEntityFurnace furnace = (TileEntityFurnace) tile;
            if (!furnace.isExpend()) {
                furnace.setExpend(true);
                return EnumActionResult.SUCCESS;
            } else {
                return EnumActionResult.FAIL;
            }
        }
        return EnumActionResult.PASS;
    }

    @Override
    public ItemStack onItemUseFinish(ItemStack stack, World worldIn, EntityLivingBase entityLiving) {
        return ItemStack.EMPTY;
    }
}
