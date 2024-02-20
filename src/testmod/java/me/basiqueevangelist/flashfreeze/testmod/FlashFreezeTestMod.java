package me.basiqueevangelist.flashfreeze.testmod;

import dev.onyxstudios.cca.api.v3.component.ComponentKey;
import dev.onyxstudios.cca.api.v3.component.ComponentRegistryV3;
import net.fabricmc.api.ModInitializer;
import net.minecraft.util.Identifier;

public class FlashFreezeTestMod implements ModInitializer {
    public static final ComponentKey<TestComponent> TEST_COMPONENT = ComponentRegistryV3.INSTANCE.getOrCreate(new Identifier("flashfreeze:test"), TestComponent.class);

    @Override
    public void onInitialize() {
    }
}
