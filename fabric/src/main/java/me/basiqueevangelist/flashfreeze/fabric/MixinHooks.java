package me.basiqueevangelist.flashfreeze.fabric;

import net.minecraft.resources.ResourceLocation;

import java.util.ArrayList;
import java.util.List;

public class MixinHooks {
    public static final ThreadLocal<List<String>> unknownAttachmentIds = ThreadLocal.withInitial(ArrayList::new);
}
