package me.basiqueevangelist.flashfreeze;

import eu.pb4.polymer.block.BlockHelper;
import eu.pb4.polymer.item.ItemHelper;
import me.basiqueevangelist.flashfreeze.block.FakeBlock;
import me.basiqueevangelist.flashfreeze.block.UnknownBlockState;
import me.basiqueevangelist.flashfreeze.entity.FakeEntity;
import me.basiqueevangelist.flashfreeze.item.FakeEntityTemplateItem;
import me.basiqueevangelist.flashfreeze.item.FakeItem;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricEntityTypeBuilder;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.MapColor;
import net.minecraft.block.Material;
import net.minecraft.entity.EntityDimensions;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.text.LiteralText;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import org.apache.logging.log4j.LogManager;

public class FlashFreeze implements ModInitializer {
    public static final FakeBlock FAKE_BLOCK = new FakeBlock(FabricBlockSettings.of(Material.BARRIER, MapColor.BLACK).strength(-1.0F, 3600000.0F).dropsNothing().allowsSpawning((state, world, pos, type) -> false));
    public static final FakeItem FAKE_ITEM = new FakeItem(new FabricItemSettings());
    public static final FakeEntityTemplateItem FAKE_ENTITY_TEMPLATE = new FakeEntityTemplateItem(new FabricItemSettings());
    public static final EntityType<FakeEntity> FAKE_ENTITY = FabricEntityTypeBuilder.<FakeEntity>create(SpawnGroup.MISC, FakeEntity::new).dimensions(EntityDimensions.fixed(0.5F, 1.975F)).build();

    @Override
    public void onInitialize() {
        LogManager.getLogger("FlashFreeze").info("Flash freezing content since 2021");

        Registry.register(Registry.BLOCK, id("fake_block"), FAKE_BLOCK);
        Registry.register(Registry.ITEM, id("fake_item"), FAKE_ITEM);
        Registry.register(Registry.ITEM, id("fake_entity_template"), FAKE_ENTITY_TEMPLATE);
        Registry.register(Registry.ENTITY_TYPE, id("fake_entity"), FAKE_ENTITY);

        // ExampleTestingContent.registerBiome();
    }

    public static Identifier id(String path) {
        return new Identifier("flashfreeze", path);
    }

    public static BlockState getForUnknown(UnknownBlockState state) {
        // Returns bedrock for now.
        return FAKE_BLOCK.getDefaultState();
    }

    public static ItemStack makeFakeStack(NbtCompound tag, byte count) {
        ItemStack stack = new ItemStack(FAKE_ITEM, count);
        stack.getOrCreateTag().put("OriginalData", tag);
        stack.setCustomName(new LiteralText("Unknown item: " + tag.getString("id")));
        return stack;
    }
}
