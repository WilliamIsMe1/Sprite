package william.sprite.mixin.client;

import net.minecraft.client.renderer.entity.state.CopperGolemRenderState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import william.sprite.client.SpriteRenderStateAccess;

@Mixin(CopperGolemRenderState.class)
public class CopperGolemRenderStateMixin implements SpriteRenderStateAccess {

    @Unique
    private boolean sprite$isSprite = false;

    @Override
    public boolean sprite$isSprite() {
        return sprite$isSprite;
    }

    @Override
    public void sprite$setIsSprite(boolean value) {
        this.sprite$isSprite = value;
    }
}