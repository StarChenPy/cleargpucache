package xyz.starchenpy.cleargpucache.mixin;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.LevelRenderer;
import org.lwjgl.opengl.GL11;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(LevelRenderer.class)
public class ClearGpuCacheMixin {
    @Final
    @Shadow
    private Minecraft minecraft;

    @Inject(at = @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/LevelRenderer;compileChunks(Lnet/minecraft/client/Camera;)V", shift = At.Shift.BY, by = 2), method = "renderLevel")
    public void renderLevelMixin(CallbackInfo ci){
        this.minecraft.getProfiler().popPush("finish");
        GL11.glFinish();
        this.minecraft.getProfiler().popPush("terrain");
    }
}
