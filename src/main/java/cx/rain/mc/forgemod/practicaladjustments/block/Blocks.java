package cx.rain.mc.forgemod.practicaladjustments.block;

import net.minecraft.block.Block;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.registry.ForgeRegistries;
import net.minecraftforge.fml.relauncher.Side;

import java.util.HashMap;
import java.util.Map;

public class Blocks {
    public static Map<String, Block> BLOCKS = new HashMap<>();

    static {

    }

    public Blocks(FMLPreInitializationEvent event) {
        register(event);
    }

    private void register(FMLPreInitializationEvent event) {
        BLOCKS.forEach((name, block) -> {
            register(block);
        });

        if (event.getSide() == Side.CLIENT) {
            BLOCKS.forEach((name, block) -> {
                registerModel(block);
            });
        }
    }

    private void register(Block block) {
        ForgeRegistries.BLOCKS.register(block);
        ForgeRegistries.ITEMS.register(Item.getItemFromBlock(block));
    }

    private void registerModel(Block block) {
        ModelResourceLocation model = new ModelResourceLocation(block.getRegistryName(), "inventory");
        ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(block), 0, model);
    }
}
