package cx.rain.mc.forgemod.practicaladjustments.gui;

import cx.rain.mc.forgemod.practicaladjustments.PracticalAdjustments;
import cx.rain.mc.forgemod.practicaladjustments.gui.container.ContainerFurnace;
import cx.rain.mc.forgemod.practicaladjustments.tile.entity.TileEntityFurnace;
import cx.rain.mc.forgemod.practicaladjustments.api.enumerates.FurnaceType;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.resources.I18n;
import net.minecraft.inventory.Container;
import net.minecraft.util.ResourceLocation;

public class GuiFurnace extends GuiContainer {
    private ResourceLocation texture = null;

    private ContainerFurnace furnace = null;
    private FurnaceType furnaceType = null;

    public GuiFurnace(Container inventorySlotsIn) {
        super(inventorySlotsIn);

        if (!(inventorySlotsIn instanceof ContainerFurnace)) {
            throw new IllegalArgumentException("Hey, Potato! My argument must be a ContainerFurnace.");
        }

        furnace = (ContainerFurnace) inventorySlotsIn;
        furnaceType = furnace.getFurnaceType();
        boolean isExpended = furnace.isExpended();


        switch (furnace.getUpgradeSlots()) {
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

        TileEntityFurnace tile = furnace.getTile();
        if (tile.isBurning()) {
            int fireHeight = getBurnLeftScaled(14);
            drawTexturedModalRect(offsetX + 62, offsetY + 35 + 13 - fireHeight,
                    176, 13 - fireHeight, 14, fireHeight + 1);
        }

        int processLength = getCookProgressScaled(24);
        drawTexturedModalRect(offsetX + 85, offsetY + 34,
                176, 15, processLength + 1, 16);
    }

    @Override
    protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY) {
        super.drawGuiContainerForegroundLayer(mouseX, mouseY);
        drawTitle();
    }

    private void drawTitle() {
        String title = null;
        if (furnace.hasCustomName()) {
            title = furnace.getName();
        } else {
            title = I18n.format(getDefaultTitleKey(furnaceType));
        }

        fontRenderer.drawString(title, (xSize - fontRenderer.getStringWidth(title)) / 2, 6, 0x404040);
    }

    public static String getDefaultTitleKey(FurnaceType type) {
        switch (type) {
            case Iron:
                return "container.furnace_iron.title";
            case Golden:
                return "container.furnace_golden.title";
            case Diamond:
                return "container.furnace_diamond.title";
            case SuperFurnace:
            default:
                return "container.furnace_super.title";
        }
    }

    private int getBurnLeftScaled(int pixels) {
        TileEntityFurnace tile = furnace.getTile();

        int i = tile.getCurrentBurnTime();
        if (i == 0) {
            i = 200;
        }
        return tile.getBurnTime() * pixels / i;
    }

    private int getCookProgressScaled(int pixels) {
        TileEntityFurnace tile = furnace.getTile();

        int i = tile.getCookTime();
        int j = tile.getTotalCookTime();
        return j != 0 && i != 0 ? i * pixels / j : 0;
    }
}
