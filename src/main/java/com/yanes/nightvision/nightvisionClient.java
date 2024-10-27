package com.yanes.nightvision;

import com.yanes.nightvision.utils.ModKeyBindings;
import net.fabricmc.api.ClientModInitializer;

public class nightvisionClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        ModKeyBindings.register();
    }
}
