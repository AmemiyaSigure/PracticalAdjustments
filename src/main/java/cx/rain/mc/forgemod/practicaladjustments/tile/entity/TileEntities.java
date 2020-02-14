package cx.rain.mc.forgemod.practicaladjustments.tile.entity;

import cx.rain.mc.forgemod.practicaladjustments.PracticalAdjustments;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class TileEntities {
    public TileEntities(FMLPreInitializationEvent event) {
        GameRegistry.registerTileEntity(TileEntityFurnace.class, getResourceLocation("furnace"));
    }

    private ResourceLocation getResourceLocation(String name) {
        return new ResourceLocation(PracticalAdjustments.MODID, name);
    }
}
