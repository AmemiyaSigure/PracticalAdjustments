package cx.rain.mc.forgemod.practicaladjustments.tile.entity;

import cx.rain.mc.forgemod.practicaladjustments.PracticalAdjustments;
import net.minecraft.tileentity.TileEntity;

public class TileEntities {
    public TileEntities() {
        TileEntity.register(getId("furnace"), TileEntityFurnace.class);
    }

    private String getId(String name) {
        return PracticalAdjustments.MODID + ":" + name;
    }
}
