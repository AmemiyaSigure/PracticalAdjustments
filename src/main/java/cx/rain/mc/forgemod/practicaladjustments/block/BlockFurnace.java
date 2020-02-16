package cx.rain.mc.forgemod.practicaladjustments.block;

import cx.rain.mc.forgemod.practicaladjustments.PracticalAdjustments;
import cx.rain.mc.forgemod.practicaladjustments.gui.Guis;
import cx.rain.mc.forgemod.practicaladjustments.tile.entity.TileEntityFurnace;
import cx.rain.mc.forgemod.practicaladjustments.utility.enumerates.FurnaceType;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.BlockHorizontal;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumBlockRenderType;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import javax.annotation.Nullable;
import java.util.Arrays;
import java.util.List;

public class BlockFurnace extends BlockContainer implements ITileEntityProvider {
    public static final PropertyDirection FACING = BlockHorizontal.FACING;

    private FurnaceType furnaceType = null;

    public BlockFurnace(FurnaceType type) {
        super(Material.IRON);
        furnaceType = type;
        setDefaultState(getBlockState().getBaseState().withProperty(FACING, EnumFacing.NORTH));
        setSoundType(SoundType.STONE);
        setHardness(3.0F);
        setHarvestLevel("pickaxe", 1);
    }

    public FurnaceType getFurnaceType() {
        return furnaceType;
    }

    public int getUpgradeSlots() {
        switch (furnaceType) {
            case Iron:
                return 1;
            case Golden:
                return 2;
            case Diamond:
            case SuperFurnace:
                return 3;
        }
        return 0;
    }

    @Override
    public EnumBlockRenderType getRenderType(IBlockState state) {
        return EnumBlockRenderType.MODEL;
    }

    @Override
    protected BlockStateContainer createBlockState() {
        return new BlockStateContainer(this, FACING);
    }

    @Override
    public int getMetaFromState(IBlockState state) {
        EnumFacing facing = state.getValue(FACING);
        return facing.getIndex();
    }

    @Override
    public IBlockState getStateFromMeta(int meta) {
        EnumFacing enumfacing = EnumFacing.getFront(meta);
        if (enumfacing.getAxis() == EnumFacing.Axis.Y) {
            enumfacing = EnumFacing.NORTH;
        }
        return getDefaultState().withProperty(FACING, enumfacing);
    }

    @Override
    public IBlockState getStateForPlacement(World world, BlockPos pos, EnumFacing facing,
                                            float hitX, float hitY, float hitZ, int meta, EntityLivingBase placer,
                                            EnumHand hand) {
        return getDefaultState().withProperty(FACING, placer.getHorizontalFacing().getOpposite());
    }

    @Override
    public BlockStateContainer getBlockState() {
        return super.getBlockState();
    }

    @Override
    public void onBlockAdded(World worldIn, BlockPos pos, IBlockState state) {
        setDefaultFacing(worldIn, pos, state);
    }

    private void setDefaultFacing(World worldIn, BlockPos pos, IBlockState state) {
        if (!worldIn.isRemote) {
            IBlockState iblockstate = worldIn.getBlockState(pos.north());
            IBlockState iblockstate1 = worldIn.getBlockState(pos.south());
            IBlockState iblockstate2 = worldIn.getBlockState(pos.west());
            IBlockState iblockstate3 = worldIn.getBlockState(pos.east());
            EnumFacing enumfacing = state.getValue(FACING);

            if (enumfacing == EnumFacing.NORTH && iblockstate.isFullBlock() && !iblockstate1.isFullBlock()) {
                enumfacing = EnumFacing.SOUTH;
            }
            else if (enumfacing == EnumFacing.SOUTH && iblockstate1.isFullBlock() && !iblockstate.isFullBlock()) {
                enumfacing = EnumFacing.NORTH;
            }
            else if (enumfacing == EnumFacing.WEST && iblockstate2.isFullBlock() && !iblockstate3.isFullBlock()) {
                enumfacing = EnumFacing.EAST;
            }
            else if (enumfacing == EnumFacing.EAST && iblockstate3.isFullBlock() && !iblockstate2.isFullBlock()) {
                enumfacing = EnumFacing.WEST;
            }
            worldIn.setBlockState(pos, state.withProperty(FACING, enumfacing), 2);
        }
    }

    @Override
    public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn,
                                    EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
        if (!worldIn.isRemote) {
            List<String> guis = Arrays.asList(Guis.GUIS.keySet().toArray(new String[Guis.GUIS.keySet().size()]));
            playerIn.openGui(PracticalAdjustments.INSTANCE, guis.indexOf("furnace"), worldIn,
                    pos.getX(), pos.getY(), pos.getZ());
        }
        return true;
    }

    @Nullable
    @Override
    public TileEntity createNewTileEntity(World worldIn, int meta) {
        return new TileEntityFurnace();
    }
}
