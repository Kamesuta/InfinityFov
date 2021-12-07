package com.kamesuta.infinityfov.mixin;

import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.option.OptionsScreen;
import net.minecraft.client.gui.widget.ClickableWidget;
import net.minecraft.client.gui.widget.DoubleOptionSliderWidget;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Optional;

@Mixin(OptionsScreen.class)
public class OptionsScreenMixin extends Screen {
    private Optional<DoubleOptionSliderWidget> fovOption;
    private int fovOptionWidth;

    protected OptionsScreenMixin(Text title) {
        super(title);
    }

    @Inject(method = "init", at = @At(value = "TAIL"))
    public void init(CallbackInfo ci) {
        fovOption = drawables.stream()
                .filter(e -> e instanceof DoubleOptionSliderWidget)
                .map(DoubleOptionSliderWidget.class::cast)
                .filter(e -> "options.fov".equals(((TranslatableText)e.option.key).getKey()))
                .findFirst();
        fovOptionWidth = fovOption.map(ClickableWidget::getWidth).orElse(0);
    }

    @Inject(method = "render", at = @At(value = "HEAD"), cancellable = true)
    public void render(MatrixStack matrices, int mouseX, int mouseY, float delta, CallbackInfo ci) {
        fovOption.ifPresent(widget -> {
            if (widget.isMouseOver(mouseX, mouseY)) {
                ci.cancel();
                widget.setWidth(fovOptionWidth * 2 + 10);
                widget.render(matrices, mouseX, mouseY, delta);
            } else {
                widget.setWidth(fovOptionWidth);
            }
        });
    }

//    @Redirect(method = "render", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/screen/option/OptionsScreen;renderBackground(Lnet/minecraft/client/util/math/MatrixStack;)V"))
//    public void render(OptionsScreen instance, MatrixStack matrixStack) {
//        instance.renderb
//    }
}
