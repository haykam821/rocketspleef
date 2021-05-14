package supercoder79.rocketspleef.mixin;

import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import supercoder79.rocketspleef.RocketSpleef;
import xyz.nucleoid.plasmid.game.ManagedGameSpace;
import xyz.nucleoid.plasmid.game.rule.RuleResult;

@Mixin(ItemEntity.class)
public abstract class MixinItemEntity {
    @Shadow
    public abstract ItemStack getStack();

    @Inject(method = "onPlayerCollision", at = @At("HEAD"), cancellable = true)
    private void rejectPlayersWithItem(PlayerEntity player, CallbackInfo ci) {
        ManagedGameSpace gameSpace = ManagedGameSpace.forWorld(player.getEntityWorld());

        if (gameSpace != null && gameSpace.testRule(RocketSpleef.REJECT_ITEMS) == RuleResult.ALLOW) {
            // TODO: some sort of registry something for this
            Item item = this.getStack().getItem();

            if (item == Items.GOLDEN_HOE && player.inventory.count(Items.GOLDEN_HOE) > 0) {
                ci.cancel();
                return;
            }

            if (item == Items.DIAMOND_HOE && player.inventory.count(Items.DIAMOND_HOE) > 0) {
                ci.cancel();
                return;
            }
        }
    }
}
