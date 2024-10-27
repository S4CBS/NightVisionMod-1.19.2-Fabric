package com.yanes.nightvision.utils;

import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayConnectionEvents;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.text.Text;
import org.lwjgl.glfw.GLFW;

public class ModKeyBindings {
    public static KeyBinding MAGIC_KEYBINDING = KeyBindingHelper.registerKeyBinding(new KeyBinding(
            "key.nightvision",
            InputUtil.Type.KEYSYM,
            GLFW.GLFW_KEY_G,
            "key.category.nightvision"
    ));

    private static boolean nightVisionOn = false; // Переключатель ON - OFF

    public static void register(){
        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            if (MAGIC_KEYBINDING.wasPressed()) {
                nightVisionOn = !nightVisionOn;
                if (nightVisionOn) {
                    // Включение Ночного зрения
                    client.player.sendMessage(Text.literal("NightVision On"), true);
                    client.player.addStatusEffect(new StatusEffectInstance(StatusEffect.byRawId(16), 9999999));
                } else {
                    // Выключение ночного зрения
                    client.player.sendMessage(Text.literal("NightVision OFF"), true);
                    client.player.removeStatusEffect(StatusEffect.byRawId(16));
                }

            }
        });
        ClientPlayConnectionEvents.DISCONNECT.register(((handler, client) -> {
            if (nightVisionOn && client.player != null) {
                client.player.removeStatusEffect(StatusEffect.byRawId(16));
                client.player.sendMessage(Text.literal("NightVision OFF"), true);
                nightVisionOn = false;
            }
        }));
    }
}
