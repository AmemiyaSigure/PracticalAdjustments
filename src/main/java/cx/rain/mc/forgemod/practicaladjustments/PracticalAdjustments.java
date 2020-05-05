package cx.rain.mc.forgemod.practicaladjustments;

import cx.rain.mc.forgemod.practicaladjustments.block.Blocks;
import cx.rain.mc.forgemod.practicaladjustments.creative.tab.Tabs;
import cx.rain.mc.forgemod.practicaladjustments.event.Events;
import cx.rain.mc.forgemod.practicaladjustments.gui.Guis;
import cx.rain.mc.forgemod.practicaladjustments.item.Items;
import cx.rain.mc.forgemod.practicaladjustments.tile.entity.TileEntities;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.Mod.Instance;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.spongepowered.asm.launch.MixinBootstrap;
import org.spongepowered.asm.mixin.Mixins;

@Mod(modid = PracticalAdjustments.MODID, name = PracticalAdjustments.NAME, version = PracticalAdjustments.VERSION)
public class PracticalAdjustments {
    public static final String MODID = "practicaladjustments";
    public static final String NAME = "Practical Adjustments";
    public static final String VERSION = "1.1.0";

    @Instance(value = PracticalAdjustments.MODID, owner = PracticalAdjustments.MODID)
    public static PracticalAdjustments INSTANCE;

    private Logger logger = LogManager.getLogger(PracticalAdjustments.NAME);

    @EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        logger.info("Hi there! Here is PreInitialization.");
        new Tabs();
        new Items(event);
        new Blocks(event);
        new TileEntities(event);
    }

    @EventHandler
    public void init(FMLInitializationEvent event) {
        new Events();
        new Guis();
    }

    @EventHandler
    public void postInit(FMLPostInitializationEvent event) {
        logger.info("Welcome to potato world!");
    }

    public Logger getLogger() {
        return logger;
    }
}
