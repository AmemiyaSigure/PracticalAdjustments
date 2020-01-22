package cx.rain.mc.forgemod.practicaladjustments.block;

import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;

public class BlockDeCurseTable extends Block {
    public BlockDeCurseTable() {
        super(Material.IRON);
        this.setSoundType(SoundType.STONE);
        this.setHardness(3.0F);
        this.setHarvestLevel("pickaxe", 2);
    }
}
