package cx.rain.mc.forgemod.practicaladjustments.items;

import cx.rain.mc.forgemod.practicaladjustments.PracticalAdjustments;
import net.minecraft.item.Item;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;

@EventBusSubscriber(modid = PracticalAdjustments.MODID, bus = EventBusSubscriber.Bus.MOD)
public final class Items {
    public static final Item PESTLE = new Pestle();

    @SubscribeEvent
    public static void onRegisterItems(RegistryEvent.Register<Item> event) {
        event.getRegistry().register(PESTLE);
    }
}
