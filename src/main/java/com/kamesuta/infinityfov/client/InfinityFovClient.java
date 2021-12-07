package com.kamesuta.infinityfov.client;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.option.Option;

@Environment(EnvType.CLIENT)
public class InfinityFovClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        Option.FOV.min = -100;
        Option.FOV.setMax(1000);
    }
}
