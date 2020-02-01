package cx.rain.mc.forgemod.practicaladjustments.ingame;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.DamageSource;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentTranslation;

public class DamageSourceBook extends DamageSource {
    public DamageSourceBook() {
        super("magic");
        setDamageBypassesArmor();
        setMagicDamage();
    }

    @Override
    public ITextComponent getDeathMessage(EntityLivingBase entityLivingBaseIn) {
        return new TextComponentTranslation("death.attack.read_will_die_book",
                entityLivingBaseIn.getName());
    }
}
