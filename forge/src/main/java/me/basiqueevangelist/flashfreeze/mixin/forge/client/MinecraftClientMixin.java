package me.basiqueevangelist.flashfreeze.mixin.forge.client;

import com.mojang.datafixers.util.Function4;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.BackupPromptScreen;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.world.EditWorldScreen;
import net.minecraft.resource.DataPackSettings;
import net.minecraft.resource.ResourceManager;
import net.minecraft.text.LiteralText;
import net.minecraft.util.registry.DynamicRegistryManager;
import net.minecraft.world.SaveProperties;
import net.minecraft.world.level.storage.LevelStorage;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.function.Function;

@Mixin(MinecraftClient.class)
public abstract class MinecraftClientMixin {
    @Shadow public abstract void setScreen(@Nullable Screen screen);

    @Shadow @Final private LevelStorage levelStorage;

    @Shadow protected abstract void doLoadLevel(String string, DynamicRegistryManager.Impl arg, Function<LevelStorage.Session, DataPackSettings> function, Function4<LevelStorage.Session, DynamicRegistryManager.Impl, ResourceManager, DataPackSettings, SaveProperties> function4, boolean bl, MinecraftClient.WorldLoadAction arg2, boolean creating);

    @Inject(method = "startIntegratedServer(Ljava/lang/String;)V", at = @At("HEAD"), cancellable = true)
    private void showAlphaWarning(String worldName, CallbackInfo ci) {
        var title = new LiteralText("You are running FlashFreeze Alpha!");
        var subtitle = new LiteralText("While everything should work OK™, it is recommended to back up your world.");

        setScreen(new BackupPromptScreen(null, (shouldBackup, eraseCache) -> {
            if (shouldBackup) {
                EditWorldScreen.onBackupConfirm(levelStorage, worldName);
            }

            doLoadLevel(worldName, DynamicRegistryManager.create(), MinecraftClient::loadDataPackSettings, MinecraftClient::createSaveProperties, false, MinecraftClient.WorldLoadAction.BACKUP, false);
        }, title, subtitle, false));
        ci.cancel();
    }
}
