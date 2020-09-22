package mod.noriokun4649.shulkerboxmultiopentool.gui;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;
import mod.noriokun4649.shulkerboxmultiopentool.container.MultiOpenContainer;
import net.minecraft.client.gui.screen.inventory.ContainerScreen;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.ClickType;
import net.minecraft.inventory.container.Slot;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import static mod.noriokun4649.shulkerboxmultiopentool.ShulkerBoxMultiOpenToolMod.MOD_ID;

@OnlyIn(Dist.CLIENT)
public class MultiOpenToolScreen extends ContainerScreen<MultiOpenContainer> {

    private static final ResourceLocation GUITEXTURE = new ResourceLocation(MOD_ID, "textures/gui/multiui.png");

    public MultiOpenToolScreen(final MultiOpenContainer multiOpenContainer, final PlayerInventory playerInventory, final ITextComponent iTextComponent) {
        super(multiOpenContainer, playerInventory, iTextComponent);
    }

    @Override
    public void func_230430_a_(final MatrixStack matrixStack, final int i, final int i1, final float v) {
        this.func_230446_a_(matrixStack);
        super.func_230430_a_(matrixStack, i, i1, v);
        this.func_230459_a_(matrixStack, i, i1);
    }

    @Override
    protected void func_230450_a_(final MatrixStack matrixStack, final float v, final int i, final int i1) {
        RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0F);
        this.field_230706_i_.getTextureManager().bindTexture(GUITEXTURE);
        int lvt_5_1_ = (this.field_230708_k_ - this.xSize) / 2;
        int lvt_6_1_ = (this.field_230709_l_ - this.ySize) / 2;
        this.func_238464_a_(matrixStack, lvt_5_1_ - 80, lvt_6_1_, 0, 0, 0, this.xSize * 2, this.ySize, 256, 512);

    }

}
