package william.sprite.mixin.client;

import net.minecraft.client.renderer.entity.CopperGolemRenderer;
import net.minecraft.client.renderer.entity.state.CopperGolemRenderState;
import net.minecraft.resources.Identifier;
import net.minecraft.world.entity.animal.golem.CopperGolem;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import william.sprite.client.SpriteRenderStateAccess;

import java.util.function.Function;

@Mixin(CopperGolemRenderer.class)
public class CopperGolemRendererMixin {

    @Unique
    private static final Identifier SPRITE_TEXTURE =
            Identifier.fromNamespaceAndPath("william_sprite", "textures/entity/copper_golem/sprite.png");

    @Unique
    private static final Identifier EYES_TEXTURE = Identifier.fromNamespaceAndPath("william_sprite", "textures/entity/copper_golem/copper_golem_eyes.png");

    @Inject(method = "extractRenderState*", at = @At("TAIL"))
    private void sprite$captureName(CopperGolem entity, CopperGolemRenderState state, float partialTicks, CallbackInfo ci) {
        ((SpriteRenderStateAccess) state).sprite$setIsSprite(
                entity.getCustomName() != null && entity.getCustomName().getString().equals("Sprite")
        );
    }

    @Inject(method = "getTextureLocation*", at = @At("HEAD"), cancellable = true)
    private void sprite$overrideTexture(CopperGolemRenderState state, CallbackInfoReturnable<Identifier> cir) {
        if (((SpriteRenderStateAccess) state).sprite$isSprite()) {
            cir.setReturnValue(SPRITE_TEXTURE);
        }

    }

    @Inject(method = "getEyeTextureLocationProvider", at = @At("RETURN"), cancellable = true)
    private static void sprite$overrideEyesTexture(CallbackInfoReturnable<Function<CopperGolemRenderState, Identifier>> cir) {
        Function<CopperGolemRenderState, Identifier> original = cir.getReturnValue();
        cir.setReturnValue(state ->
                ((SpriteRenderStateAccess) state).sprite$isSprite() ? EYES_TEXTURE : original.apply(state)
        );
    }
}