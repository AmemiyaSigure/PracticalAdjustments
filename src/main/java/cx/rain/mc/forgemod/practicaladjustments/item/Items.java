package cx.rain.mc.forgemod.practicaladjustments.item;

import cx.rain.mc.forgemod.practicaladjustments.utility.enumerates.FurnaceUpgradeLevel;
import cx.rain.mc.forgemod.practicaladjustments.PracticalAdjustments;
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
        ITEMS.put("read_will_die_book", new ItemReadWillDieBook());
        ITEMS.put("furnace_upgrade_ore_primary", new ItemFurnaceUpgradeOre(FurnaceUpgradeLevel.Primary));
        ITEMS.put("furnace_upgrade_ore_advanced", new ItemFurnaceUpgradeOre(FurnaceUpgradeLevel.Advanced));
        ITEMS.put("furnace_upgrade_ore_super", new ItemFurnaceUpgradeOre(FurnaceUpgradeLevel.Super));
        ITEMS.put("furnace_upgrade_fuel_primary", new ItemFurnaceUpgradeFuel(FurnaceUpgradeLevel.Primary));
        ITEMS.put("furnace_upgrade_fuel_advanced", new ItemFurnaceUpgradeFuel(FurnaceUpgradeLevel.Advanced));
        ITEMS.put("furnace_upgrade_fuel_super", new ItemFurnaceUpgradeFuel(FurnaceUpgradeLevel.Super));
        ITEMS.put("furnace_upgrade_speed", new ItemFurnaceUpgrade());
        ITEMS.put("furnace_upgrade_expend", new ItemFurnaceUpgradeExpend());
    }

    public Items(FMLPreInitializationEvent event) {
        PracticalAdjustments.INSTANCE.getLogger().info("Registered items.");
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
        item.setRegistryName(PracticalAdjustments.MODID, name).setUnlocalizedName(name)
                .setCreativeTab(Tabs.PRACTICAL_ADJUSTMENTS);
        ForgeRegistries.ITEMS.register(item);
    }

    private void registerModel(Item item) {
        ModelResourceLocation model = new ModelResourceLocation(item.getRegistryName(), "inventory");
        ModelLoader.setCustomModelResourceLocation(item, 0, model);
    }
}
