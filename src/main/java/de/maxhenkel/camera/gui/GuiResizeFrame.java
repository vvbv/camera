package de.maxhenkel.camera.gui;

import de.maxhenkel.camera.Main;
import de.maxhenkel.camera.net.MessageResizeFrame;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.TextComponentTranslation;
import java.util.UUID;

public class GuiResizeFrame extends GuiContainer {

    private static final ResourceLocation CAMERA_TEXTURE = new ResourceLocation(Main.MODID, "textures/gui/camera.png");
    private static final int FONT_COLOR = 4210752;

    private GuiButton buttonUp;
    private GuiButton buttonDown;
    private GuiButton buttonLeft;
    private GuiButton buttonRight;

    private UUID uuid;

    public GuiResizeFrame(UUID uuid) {
        super(new ContainerResizeFrame());
        this.uuid = uuid;
        xSize = 248;
        ySize = 109;
    }

    @Override
    protected void initGui() {
        super.initGui();

        buttons.clear();
        int left = (width - xSize) / 2;
        int padding = 10;
        int buttonWidth = 50;
        int buttonHeight = 20;
        buttonLeft = addButton(new GuiButton(0, left + padding, height / 2 - buttonHeight / 2, buttonWidth, buttonHeight, new TextComponentTranslation("button.left").getFormattedText()) {
            @Override
            public void onClick(double x, double y) {
                super.onClick(x, y);
                sendMoveImage(MessageResizeFrame.Direction.LEFT);
            }
        });

        buttonRight = addButton(new GuiButton(0, left + xSize - buttonWidth - padding, height / 2 - buttonHeight / 2, buttonWidth, buttonHeight, new TextComponentTranslation("button.right").getFormattedText()) {
            @Override
            public void onClick(double x, double y) {
                super.onClick(x, y);
                sendMoveImage(MessageResizeFrame.Direction.RIGHT);
            }
        });

        buttonUp = addButton(new GuiButton(0, width / 2 - buttonWidth / 2, guiTop + padding, buttonWidth, buttonHeight, new TextComponentTranslation("button.up").getFormattedText()) {
            @Override
            public void onClick(double x, double y) {
                super.onClick(x, y);
                sendMoveImage(MessageResizeFrame.Direction.UP);
            }
        });

        buttonDown = addButton(new GuiButton(0, width / 2 - buttonWidth / 2, guiTop + ySize - padding - buttonHeight, buttonWidth, buttonHeight, new TextComponentTranslation("button.down").getFormattedText()) {
            @Override
            public void onClick(double x, double y) {
                super.onClick(x, y);
                sendMoveImage(MessageResizeFrame.Direction.DOWN);
            }
        });

    }

    private void sendMoveImage(MessageResizeFrame.Direction direction) {
        Main.SIMPLE_CHANNEL.sendToServer(new MessageResizeFrame(uuid, direction, !GuiScreen.isShiftKeyDown()));
    }

    @Override
    protected void drawGuiContainerForegroundLayer(int x, int y) {
        super.drawGuiContainerForegroundLayer(x, y);

        String title = new TextComponentTranslation("gui.frame.resize").getFormattedText();

        int titleWidth = fontRenderer.getStringWidth(title);
        fontRenderer.drawString(title, xSize / 2 - titleWidth / 2, ySize / 2 - fontRenderer.FONT_HEIGHT / 2, FONT_COLOR);
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {
        drawDefaultBackground();
        GlStateManager.color4f(1.0F, 1.0F, 1.0F, 1.0F);
        mc.getTextureManager().bindTexture(CAMERA_TEXTURE);
        drawTexturedModalRect(guiLeft, guiTop, 0, 0, xSize, ySize);
    }
}