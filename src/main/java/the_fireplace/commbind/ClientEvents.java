package the_fireplace.commbind;

import net.minecraftforge.fml.client.event.ConfigChangedEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

/**
 * @author The_Fireplace
 */
public class ClientEvents {
    @SubscribeEvent
    public void onConfigChanged(ConfigChangedEvent.OnConfigChangedEvent eventArgs) {
        if(eventArgs.getModID().equals(CommBind.MODID)) {
            System.out.println("Config change event");
            CommBind.syncConfig();
            CommBind.keyHandler.updateKeyBindings();
        }
    }
}