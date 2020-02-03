package cx.rain.mc.forgemod.practicaladjustments.event;

import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.passive.EntityPig;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraftforge.event.entity.EntityMountEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class EventSaddlePick {
    @SubscribeEvent
    public void onEntityMount(EntityMountEvent event) {
        if (event.getEntityMounting() instanceof EntityPlayer
                && event.getEntityBeingMounted() instanceof EntityPig
                && ((EntityPig) event.getEntityBeingMounted()).getSaddled()
                && event.getEntityMounting().isSneaking()
                && !event.getEntityBeingMounted().isBeingRidden()
                && !event.getWorldObj().isRemote) {
            event.setCanceled(true);
            EntityItem entityItem = new EntityItem(event.getWorldObj(), event.getEntityMounting().posX,
                    event.getEntityMounting().posY, event.getEntityMounting().posZ, new ItemStack(Items.SADDLE));
            event.getWorldObj().spawnEntity(entityItem);
            ((EntityPig) event.getEntityBeingMounted()).setSaddled(false);
        }
    }
}
