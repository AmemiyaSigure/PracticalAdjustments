package cx.rain.mc.forgemod.practicaladjustments.item;

import cx.rain.mc.forgemod.practicaladjustments.creative.tab.Tabs;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.registry.ForgeRegistries;
import net.minecraftforge.fml.relauncher.Side;

import java.util.HashMap;
import java.util.Map;

public class Items {
    public static Map<String, Item> ITEMS = new HashMap<>();

    static {
        ITEMS.put("pestle", new ItemPestle());
        ITEMS.put("mortar_and_pestle", new ItemMortarAndPestle());
    }

    public Items(FMLPreInitializationEvent event) {
        register(event);
    }

    private void register(FMLPreInitializationEvent event) {
        ITEMS.forEach((name, item) -> {
            register(item, name);
        });

        if (event.getSide() == Side.CLIENT) {
            ITEMS.forEach((name, item) -> {
                registerModel(item);
            });
        }
    }

    private void register(Item item, String name) {
        item.setRegistryName(name).setUnlocalizedName(name).setCreativeTab(Tabs.PRACTICAL_ADJUSTMENTS);
        ForgeRegistries.ITEMS.register(item);
    }

    private void registerModel(Item item) {
        ModelResourceLocation model = new ModelResourceLocation(item.getRegistryName(), "inventory");
        ModelLoader.setCustomModelResourceLocation(item, 0, model);
    }
}
