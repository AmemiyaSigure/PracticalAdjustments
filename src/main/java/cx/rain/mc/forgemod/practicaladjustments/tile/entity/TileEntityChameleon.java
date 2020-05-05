package cx.rain.mc.forgemod.practicaladjustments.tile.entity;

import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;

public class TileEntityChameleon extends TileEntity {
    private Block dependBlock = Blocks.AIR;
    private EnumFacing side = EnumFacing.DOWN;

    public TileEntityChameleon() {
        super();
    }

    public void updateDepend() {
        int x = pos.getX();
        int y = pos.getY();
        int z = pos.getZ();

        switch (side) {
            case WEST:
                x--;
                break;
            case EAST:
                x++;
                break;
            case DOWN:
                y--;
                break;
            case UP:
                y++;
                break;
            case NORTH:
                z--;
                break;
            case SOUTH:
                z++;
                break;
        }

        Block oldDependBlock = dependBlock;

    }
}
