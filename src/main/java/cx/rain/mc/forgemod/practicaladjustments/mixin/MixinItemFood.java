package cx.rain.mc.forgemod.practicaladjustments.mixin;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.init.Items;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ItemFood.class)
public abstract class MixinItemFood {
    @Inject(at = @At(value = "TAIL"), method = "onItemUseFinish")
    private void onItemUseFinish(ItemStack stack, World world, EntityLivingBase entity, CallbackInfoReturnable<ItemStack> ci) {
        if (stack.isItemEqual(new ItemStack(Items.MELON))
                && !world.isRemote) {
            EntityItem entityItem =
                    new EntityItem(world, entity.posX, entity.posY, entity.posZ, new ItemStack(Items.MELON_SEEDS));
            world.spawnEntity(entityItem);
        }
    }
}
