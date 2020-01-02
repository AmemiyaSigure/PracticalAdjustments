package cx.rain.mc.forgemod.practicaladjustments;

import cx.rain.mc.forgemod.practicaladjustments.items.Items;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Mod(PracticalAdjustments.MODID)
public class PracticalAdjustments {
    public static final String MODID = "practicaladjustments";

    private static final Logger LOGGER = LogManager.getLogger();

    public PracticalAdjustments() {
        MinecraftForge.EVENT_BUS.register(Items.class);
    }

    public static Logger getLogger() {
        return LOGGER;
    }
}
