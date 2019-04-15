package the_fireplace.commbind;

import net.minecraft.client.settings.KeyBinding;

public class KeyCommandBinding {
    public KeyBinding keyBinding;
    public String command;

    public KeyCommandBinding(KeyBinding keyBinding, String command) {
        this.keyBinding = keyBinding;
        this.command = command;
    }
}
