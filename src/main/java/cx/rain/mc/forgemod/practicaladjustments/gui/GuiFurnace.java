package cx.rain.mc.forgemod.practicaladjustments.gui;

import cx.rain.mc.forgemod.practicaladjustments.PracticalAdjustments;
import cx.rain.mc.forgemod.practicaladjustments.gui.container.ContainerFurnace;
import cx.rain.mc.forgemod.practicaladjustments.utility.enumerates.FurnaceType;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.resources.I18n;
import net.minecraft.inventory.Container;
import net.minecraft.util.ResourceLocation;

public class GuiFurnace extends GuiContainer {
    private ResourceLocation texture = null;

    private FurnaceType furnaceType = null;
    private int upgradeSlots = 1;
    private boolean isExpended = false;

    public GuiFurnace(Container inventorySlotsIn) {
        super(inventorySlotsIn);

        if (!(inventorySlotsIn instanceof ContainerFurnace)) {
            throw new IllegalArgumentException("Hey, Potato! My argument must be a ContainerFurnace.");
        }

        ContainerFurnace furnace = (ContainerFurnace) inventorySlotsIn;
        furnaceType = furnace.getFurnaceType();
        upgradeSlots = furnace.getUpgradeSlots();
        isExpended = furnace.isExpended();


        switch (upgradeSlots) {
            case 1:
                if (!isExpended) {
                    texture = new ResourceLocation(PracticalAdjustments.MODID,
                            "textures/guis/furnace_one_slot.png");
                } else {
                    texture = new ResourceLocation(PracticalAdjustments.MODID,
                            "textures/guis/furnace_one_slot_expend.png");
                }
                break;
            case 2:
                if (!isExpended) {
                    texture = new ResourceLocation(PracticalAdjustments.MODID,
                            "textures/guis/furnace_two_slots.png");
                } else {
                    texture = new ResourceLocation(PracticalAdjustments.MODID,
                            "textures/guis/furnace_two_slots_expend.png");
                }
                break;
            case 3:
                if (!isExpended) {
                    texture = new ResourceLocation(PracticalAdjustments.MODID,
                            "textures/guis/furnace_three_slots.png");
                } else {
                    texture = new ResourceLocation(PracticalAdjustments.MODID,
                            "textures/guis/furnace_three_slots_expend.png");
                }
                break;
        }

        xSize = 176;
        ySize = 172;
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        drawDefaultBackground();
        super.drawScreen(mouseX, mouseY, partialTicks);
        renderHoveredToolTip(mouseX, mouseY);
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {
        GlStateManager.color(1.0F, 1.0F, 1.0F);
        mc.getTextureManager().bindTexture(texture);
        int offsetX = (this.width - this.xSize) / 2;
        int offsetY = (this.height - this.ySize) / 2;
        drawTexturedModalRect(offsetX, offsetY, 0, 0, this.xSize, this.ySize);
    }

    @Override
    protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY) {
        super.drawGuiContainerForegroundLayer(mouseX, mouseY);
        drawTitle();
    }

    private void drawTitle() {
        String title = null;
        switch (furnaceType) {
            case Iron:
                title = I18n.format("container.furnace_iron.title");
                break;
            case Golden:
                title = I18n.format("container.furnace_golden.title");
                break;
            case Diamond:
                title = I18n.format("container.furnace_diamond.title");
                break;
            case SuperFurnace:
                title = I18n.format("container.furnace_super.title");
                break;
        }

        fontRenderer.drawString(title, (xSize - fontRenderer.getStringWidth(title)) / 2, 6, 0x404040);
    }
}
