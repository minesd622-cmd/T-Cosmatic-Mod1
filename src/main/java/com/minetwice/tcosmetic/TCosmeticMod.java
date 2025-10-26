package com.minetwice.tcosmetic;

import com.minetwice.tcosmetic.cosmetics.CosmeticManager;
import com.minetwice.tcosmetic.gui.CosmeticMenuScreen;
import com.minetwice.tcosmetic.utils.FileUtils;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import org.lwjgl.glfw.GLFW;

public class TCosmeticMod implements ClientModInitializer {
    public static final String MOD_ID = "tcosmetic";
    private static TCosmeticMod instance;
    private CosmeticManager cosmeticManager;
    private KeyBinding openMenuKey;
    
    @Override
    public void onInitializeClient() {
        instance = this;
        this.cosmeticManager = new CosmeticManager();
        
        this.openMenuKey = KeyBindingHelper.registerKeyBinding(new KeyBinding(
            "key.tcosmetic.open_menu", InputUtil.Type.KEYSYM, GLFW.GLFW_KEY_C, "category.tcosmetic.general"
        ));
        
        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            while (openMenuKey.wasPressed()) {
                if (client.player != null) {
                    client.setScreen(new CosmeticMenuScreen());
                }
            }
        });
        
        FileUtils.createCosmeticDirectories();
        System.out.println("TCosmetic Mod initialized!");
    }
    
    public static TCosmeticMod getInstance() { return instance; }
    public CosmeticManager getCosmeticManager() { return cosmeticManager; }
}
