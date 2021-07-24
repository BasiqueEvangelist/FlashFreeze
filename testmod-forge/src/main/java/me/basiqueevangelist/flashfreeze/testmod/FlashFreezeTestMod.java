package me.basiqueevangelist.flashfreeze.testmod;

import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.Entity;
import net.minecraft.item.ItemStack;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.Identifier;
import net.minecraft.world.World;
import net.minecraft.world.chunk.Chunk;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;

@Mod(FlashFreezeTestMod.MODID)
@Mod.EventBusSubscriber(modid = FlashFreezeTestMod.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class FlashFreezeTestMod {
    public static final String MODID = "flashfreeze-testmod";
    @CapabilityInject(TestComponent.class)
    public static Capability<TestComponent> TEST_COMPONENT = null;

    public FlashFreezeTestMod() {
        MinecraftForge.EVENT_BUS.register(this);
    }

    @SubscribeEvent
    public static void onCommonSetup(FMLCommonSetupEvent event) {
        CapabilityManager.INSTANCE.register(TestComponent.class, new TestComponent.Storage(), TestComponentImpl::new);
    }

    @SubscribeEvent
    public void onAttachCapabilitiesEntity(AttachCapabilitiesEvent<Entity> event) {
        event.addCapability(new Identifier(MODID, "test"), new TestComponentImpl());
    }

    @SubscribeEvent
    public void onAttachCapabilitiesBlockEntity(AttachCapabilitiesEvent<BlockEntity> event) {
        event.addCapability(new Identifier(MODID, "test"), new TestComponentImpl());
    }

    @SubscribeEvent
    public void onAttachCapabilitiesItem(AttachCapabilitiesEvent<ItemStack> event) {
        event.addCapability(new Identifier(MODID, "test"), new TestComponentImpl());
    }

    @SubscribeEvent
    public void onAttachCapabilitiesWorld(AttachCapabilitiesEvent<World> event) {
        event.addCapability(new Identifier(MODID, "test"), new TestComponentImpl());
    }

    @SubscribeEvent
    public void onAttachCapabilitiesChunk(AttachCapabilitiesEvent<Chunk> event) {
        event.addCapability(new Identifier(MODID, "test"), new TestComponentImpl());
    }
}
