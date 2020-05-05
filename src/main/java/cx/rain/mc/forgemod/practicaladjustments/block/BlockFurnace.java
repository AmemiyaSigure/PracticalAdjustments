package cx.rain.mc.forgemod.practicaladjustments.block;

import cx.rain.mc.forgemod.practicaladjustments.PracticalAdjustments;
import cx.rain.mc.forgemod.practicaladjustments.gui.Guis;
import cx.rain.mc.forgemod.practicaladjustments.item.Items;
import cx.rain.mc.forgemod.practicaladjustments.tile.entity.TileEntityFurnace;
import cx.rain.mc.forgemod.practicaladjustments.utility.ContainerHelper;
import cx.rain.mc.forgemod.practicaladjustments.api.enumerates.FurnaceType;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.BlockHorizontal;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import javax.annotation.Nullable;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class BlockFurnace extends BlockContainer {
    public static final PropertyDirection FACING = BlockHorizontal.FACING;
    public static final PropertyBool BURNING = PropertyBool.create("burning");

    private FurnaceType furnaceType = null;

    public BlockFurnace(FurnaceType type) {
        super(Material.IRON);
        furnaceType = type;
        setDefaultState(getBlockState().getBaseState()
                        .withProperty(FACING, EnumFacing.NORTH)
                        .withProperty(BURNING, false));
        setSoundType(SoundType.METAL);
        setHardness(3.0F);
        setHarvestLevel("pickaxe", 1);
    }

    public static void setState(boolean isBurning, World world, BlockPos pos) {
        IBlockState state = world.getBlockState(pos);
        TileEntity tileEntity = world.getTileEntity(pos);

        world.setBlockState(pos, state.getBlock().getDefaultState()
                                    .withProperty(FACING, state.getValue(FACING))
                                    .withProperty(BURNING, isBurning));

        if (tileEntity != null) {
            tileEntity.validate();
            world.setTileEntity(pos, tileEntity);
        }
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
    public Item getItemDropped(IBlockState state, Random rand, int fortune) {
        return Item.getItemFromBlock(state.getBlock());
    }

    @Override
    public EnumBlockRenderType getRenderType(IBlockState state) {
        return EnumBlockRenderType.MODEL;
    }

    @Override
    protected BlockStateContainer createBlockState() {
        return new BlockStateContainer(this, FACING, BURNING);
    }

    @Override
    public int getMetaFromState(IBlockState state) {
        EnumFacing facing = state.getValue(FACING);
        return facing.getIndex();
    }

    @Override
    public IBlockState getStateFromMeta(int meta) {
        EnumFacing facing = EnumFacing.getFront(meta);
        if (facing.getAxis() == EnumFacing.Axis.Y) {
            facing = EnumFacing.NORTH;
        }
        return getDefaultState().withProperty(FACING, facing);
    }

    @Override
    public IBlockState getStateForPlacement(World world, BlockPos pos, EnumFacing facing,
                                            float hitX, float hitY, float hitZ, int meta, EntityLivingBase placer,
                                            EnumHand hand) {
        return getDefaultState().withProperty(FACING, placer.getHorizontalFacing().getOpposite());
    }

    @Override
    public void onBlockPlacedBy(World worldIn, BlockPos pos, IBlockState state,
                                EntityLivingBase placer, ItemStack stack) {
        worldIn.setBlockState(pos,
                getDefaultState().withProperty(FACING, placer.getHorizontalFacing().getOpposite()), 2);
    }

    @Override
    public void breakBlock(World worldIn, BlockPos pos, IBlockState state) {
        TileEntityFurnace furnace = (TileEntityFurnace) worldIn.getTileEntity(pos);
        if (furnace != null) {
            ContainerHelper.dropInventoryItems(worldIn, pos, furnace);
            if (furnace.isExpend()) {
                ContainerHelper.spawnItemStack(worldIn, pos.getX(), pos.getY(), pos.getZ(),
                        new ItemStack(Items.ITEMS.get("furnace_upgrade_expend")));
            }
        }

        super.breakBlock(worldIn, pos, state);
    }

    @Override
    public IBlockState withRotation(IBlockState state, Rotation rot) {
        return state.withProperty(FACING, rot.rotate((EnumFacing) state.getValue(FACING)));
    }

    @Override
    public IBlockState withMirror(IBlockState state, Mirror mirrorIn) {
        return state.withRotation(mirrorIn.toRotation((EnumFacing) state.getValue(FACING)));
    }

    @Override
    public BlockStateContainer getBlockState() {
        return super.getBlockState();
    }

    @Override
    public void onBlockAdded(World worldIn, BlockPos pos, IBlockState state) {
        if (!worldIn.isRemote) {
            IBlockState north = worldIn.getBlockState(pos.north());
            IBlockState south = worldIn.getBlockState(pos.south());
            IBlockState west = worldIn.getBlockState(pos.west());
            IBlockState east = worldIn.getBlockState(pos.east());
            EnumFacing facing = state.getValue(FACING);

            if (facing == EnumFacing.NORTH && north.isFullBlock() && !south.isFullBlock()) {
                facing = EnumFacing.SOUTH;
            }
            else if (facing == EnumFacing.SOUTH && south.isFullBlock() && !north.isFullBlock()) {
                facing = EnumFacing.NORTH;
            }
            else if (facing == EnumFacing.WEST && west.isFullBlock() && !east.isFullBlock()) {
                facing = EnumFacing.EAST;
            }
            else if (facing == EnumFacing.EAST && east.isFullBlock() && !west.isFullBlock()) {
                facing = EnumFacing.WEST;
            }
            worldIn.setBlockState(pos, state.withProperty(FACING, facing), 2);
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
