package the_fireplace.commbind;

import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.settings.KeyBinding;
import net.minecraftforge.client.settings.KeyConflictContext;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.InputEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.apache.commons.lang3.ArrayUtils;
import org.lwjgl.input.Keyboard;
import the_fireplace.commbind.config.ConfigValues;

import java.util.ArrayList;
import java.util.List;

/**
 * @author The_Fireplace
 */
public class KeyHandler {
    private static final String desc = "key.comm";
    private List<KeyCommandBinding> keys;

    public KeyHandler(){
        keys = new ArrayList<>();

        for (int i = 0; i < ConfigValues.COMMANDS.length; i++) {
            String command = ConfigValues.COMMANDS[i];
            KeyBinding keyBinding = new KeyBinding(I18n.format(desc, command), KeyConflictContext.IN_GAME, Keyboard.KEY_NONE, "key.commbind.category");

            keys.add(new KeyCommandBinding(keyBinding, command));
            ClientRegistry.registerKeyBinding(keyBinding);
        }
    }

    @SideOnly(Side.CLIENT)
    @SubscribeEvent
    public void onKeyInput(InputEvent.KeyInputEvent event){
        for(KeyCommandBinding keyCommandBinding: keys) {
            if (keyCommandBinding.keyBinding.isPressed()) {
                command(keyCommandBinding.command);
            }
        }
    }

    public void command(String command){
        if(Minecraft.getMinecraft().inGameHasFocus) {
            Minecraft.getMinecraft().player.sendChatMessage("/" + command);
        }
    }

    public void updateKeyBindings() {
        // Delete key bindings that are not there anymore
        for (int i = 0; i < keys.size(); i++) {
            KeyCommandBinding keyCommandBinding = keys.get(i);
            boolean found = false;

            for (String command : ConfigValues.COMMANDS) {
                if (command.equals(keyCommandBinding.command)) {
                    found = true;
                    break;
                }
            }

            if (!found) {
                Minecraft.getMinecraft().gameSettings.keyBindings = ArrayUtils.remove(Minecraft.getMinecraft().gameSettings.keyBindings, ArrayUtils.indexOf(Minecraft.getMinecraft().gameSettings.keyBindings, keyCommandBinding.keyBinding));
                keys.remove(i);
                i--;
            }
        }

        // Add key bindings that are not there yet
        for (String command : ConfigValues.COMMANDS) {
            boolean found = false;

            for (KeyCommandBinding keyCommandBinding : keys) {
                if (command.equals(keyCommandBinding.command)) {
                    found = true;
                    break;
                }
            }

            if (!found) {
                KeyBinding keyBinding = new KeyBinding(I18n.format(desc, command), KeyConflictContext.IN_GAME, Keyboard.KEY_NONE, "key.commbind.category");

                keys.add(new KeyCommandBinding(keyBinding, command));
                ClientRegistry.registerKeyBinding(keyBinding);
            }
        }
    }
}
