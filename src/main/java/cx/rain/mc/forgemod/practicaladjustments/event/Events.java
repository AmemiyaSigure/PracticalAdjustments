package cx.rain.mc.forgemod.practicaladjustments.event;

import net.minecraftforge.common.MinecraftForge;

public class Events {
    public Events() {
        MinecraftForge.EVENT_BUS.register(new EventEntityMount());
    }
}
