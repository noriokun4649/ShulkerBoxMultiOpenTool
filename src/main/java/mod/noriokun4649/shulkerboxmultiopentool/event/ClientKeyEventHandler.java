package mod.noriokun4649.shulkerboxmultiopentool.event;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ScreenManager;
import net.minecraft.util.text.ITextComponent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.InputEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import static mod.noriokun4649.shulkerboxmultiopentool.ShulkerBoxMultiOpenToolMod.MULTI_OPEN_TOOL_CONT;
import static mod.noriokun4649.shulkerboxmultiopentool.ShulkerBoxMultiOpenToolMod.keyBindings;

@Mod.EventBusSubscriber(Dist.CLIENT)
public class ClientKeyEventHandler {
    @SubscribeEvent
    public static void onEvent(final InputEvent.KeyInputEvent event) {
        if (Minecraft.getInstance().player != null) {
            Minecraft minecraft = Minecraft.getInstance();
            if (keyBindings.isPressed()) {
                ScreenManager.openScreen(MULTI_OPEN_TOOL_CONT, minecraft, 5, ITextComponent.func_244388_a("ddsfdd"));

            }
        }
    }
}
