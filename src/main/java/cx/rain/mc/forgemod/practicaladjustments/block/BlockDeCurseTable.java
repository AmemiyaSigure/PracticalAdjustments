package cx.rain.mc.forgemod.practicaladjustments.block;

import cx.rain.mc.forgemod.practicaladjustments.PracticalAdjustments;
import cx.rain.mc.forgemod.practicaladjustments.gui.Guis;
import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.internal.FMLNetworkHandler;

import javax.annotation.Nullable;
import java.util.Arrays;
import java.util.List;

public class BlockDeCurseTable extends Block {
    public BlockDeCurseTable() {
        super(Material.IRON);
        setSoundType(SoundType.STONE);
        setHardness(3.0F);
        setHarvestLevel("pickaxe", 2);
    }

    @Override
    public void addInformation(ItemStack stack, @Nullable World player, List<String> tooltip, ITooltipFlag advanced) {
        super.addInformation(stack, player, tooltip, advanced);
        tooltip.add(I18n.format("tile.de_curse_table.lore"));
    }

    @Override
    public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn,
                                    EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
        if (!worldIn.isRemote) {
            List<String> guis = Arrays.asList(Guis.GUIS.keySet().toArray(new String[Guis.GUIS.keySet().size()]));
            FMLNetworkHandler.openGui(playerIn, PracticalAdjustments.INSTANCE, guis.indexOf("de_curse_table"), worldIn,
                    pos.getX(), pos.getY(), pos.getZ());
        }
        return true;
    }
}
