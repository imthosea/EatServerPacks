package me.thosea.eatserverpacks.mixin;

import com.llamalad7.mixinextras.sugar.Local;
import me.thosea.eatserverpacks.EatServerPacks;
import net.minecraft.client.network.ServerInfo;
import net.minecraft.client.network.ServerInfo.ResourcePackPolicy;
import net.minecraft.nbt.NbtCompound;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

@Mixin(ServerInfo.class)
public class MixinServerInfo {
	@Shadow private ResourcePackPolicy resourcePackPolicy;

	@Inject(method = "toNbt",
            at = @At("TAIL"))
	private void onSerialize(CallbackInfoReturnable<NbtCompound> cir, @Local NbtCompound tag) {
		if(resourcePackPolicy == EatServerPacks.PACK_POLICY) {
			tag.putBoolean("eatserverpacks_eatpack", true);
		}
	}

	@Inject(method = "fromNbt",
            at = @At("TAIL"))
	private static void onDeserialize(NbtCompound root, CallbackInfoReturnable<ServerInfo> cir,
                                      @Local ServerInfo serverInfo) {
		if(root.getBoolean("eatserverpacks_eatpack")) {
			serverInfo.setResourcePackPolicy(EatServerPacks.PACK_POLICY);
		}
	}
}