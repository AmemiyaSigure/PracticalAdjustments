package cx.rain.mc.forgemod.practicaladjustments.block;

import cx.rain.mc.forgemod.practicaladjustments.PracticalAdjustments;
import cx.rain.mc.forgemod.practicaladjustments.creative.tab.Tabs;
import cx.rain.mc.forgemod.practicaladjustments.api.enumerates.FurnaceType;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.registry.ForgeRegistries;
import net.minecraftforge.fml.relauncher.Side;

import java.util.HashMap;
import java.util.Map;

public class Blocks {
    public static Map<String, Block> BLOCKS = new HashMap<>();

    static {
        BLOCKS.put("de_curse_table", new BlockDeCurseTable());
        BLOCKS.put("furnace_iron", new BlockFurnace(FurnaceType.Iron));
        BLOCKS.put("furnace_golden", new BlockFurnace(FurnaceType.Golden));
        BLOCKS.put("furnace_diamond", new BlockFurnace(FurnaceType.Diamond));
        BLOCKS.put("furnace_super", new BlockFurnace(FurnaceType.SuperFurnace));
        BLOCKS.put("chameleon", new BlockChameleon());
    }

    public Blocks(FMLPreInitializationEvent event) {
        PracticalAdjustments.INSTANCE.getLogger().info("Registered blocks.");
        register(event);
    }

    private void register(FMLPreInitializationEvent event) {
        BLOCKS.forEach((name, block) -> {
            register(block, name);
        });

        if (event.getSide() == Side.CLIENT) {
            BLOCKS.forEach((name, block) -> {
                registerModel(block);
            });
        }
    }

    private void register(Block block, String name) {
        ForgeRegistries.BLOCKS.register(block.setRegistryName(PracticalAdjustments.MODID, name)
                .setUnlocalizedName(name)
                .setCreativeTab(Tabs.PRACTICAL_ADJUSTMENTS));
        ForgeRegistries.ITEMS.register(new ItemBlock(block).setRegistryName(name));
    }

    private void registerModel(Block block) {
        ModelResourceLocation model = new ModelResourceLocation(block.getRegistryName(), "inventory");
        ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(block), 0, model);
    }
}
