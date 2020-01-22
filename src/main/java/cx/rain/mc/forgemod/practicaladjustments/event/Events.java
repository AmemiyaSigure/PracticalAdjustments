package cx.rain.mc.forgemod.practicaladjustments.event;

import cx.rain.mc.forgemod.practicaladjustments.PracticalAdjustments;
import net.minecraftforge.common.MinecraftForge;

public class Events {
    public Events() {
        PracticalAdjustments.INSTANCE.getLogger().info("Registered events.");
        MinecraftForge.EVENT_BUS.register(new EventEntityMount());
    }
}
