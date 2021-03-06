package me.basiqueevangelist.flashfreeze.mixin.client;

import me.basiqueevangelist.flashfreeze.FlashFreeze;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.BackupPromptScreen;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.world.EditWorldScreen;
import net.minecraft.server.SaveLoader;
import net.minecraft.text.Text;
import net.minecraft.world.level.storage.LevelStorage;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(MinecraftClient.class)
public abstract class MinecraftClientMixin {
    /*
    @Shadow public abstract void setScreen(@Nullable Screen screen);

    @Shadow @Final private LevelStorage levelStorage;

    @Shadow protected abstract void startIntegratedServer(String worldName, Function<LevelStorage.Session, SaveLoader.DataPackSettingsSupplier> dataPackSettingsSupplierGetter, Function<LevelStorage.Session, SaveLoader.SavePropertiesSupplier> savePropertiesSupplierGetter, boolean safeMode, MinecraftClient.WorldLoadAction worldLoadAction);

    @Inject(method = "startIntegratedServer(Ljava/lang/String;)V", at = @At("HEAD"), cancellable = true)
    private void showAlphaWarning(String worldName, CallbackInfo ci) {
        if (!FlashFreeze.CONFIG.showWarningScreen()) return;

        var title = Text.of("You are running FlashFreeze Alpha!");
        var subtitle = Text.of("While everything should work OK™, it is recommended to back up your world.");

        setScreen(new BackupPromptScreen(null, (shouldBackup, eraseCache) -> {
            if (shouldBackup) {
                EditWorldScreen.onBackupConfirm(levelStorage, worldName);
            }

            this.startIntegratedServer(worldName, SaveLoader.DataPackSettingsSupplier::loadFromWorld, SaveLoader.SavePropertiesSupplier::loadFromWorld, false, MinecraftClient.WorldLoadAction.BACKUP);
        }, title, subtitle, false));
        ci.cancel();
    }
    */
}
