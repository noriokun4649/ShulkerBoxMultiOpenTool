package mod.noriokun4649.shulkerboxmultiopentool;

import mod.noriokun4649.shulkerboxmultiopentool.container.MultiOpenContainer;
import mod.noriokun4649.shulkerboxmultiopentool.event.ClientKeyEventHandler;
import mod.noriokun4649.shulkerboxmultiopentool.gui.MultiOpenToolScreen;
import net.minecraft.block.Block;
import net.minecraft.client.gui.ScreenManager;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.inventory.container.ContainerType;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.extensions.IForgeContainerType;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.awt.event.KeyEvent;


@Mod("shulkerboxmultiopentool")
public class ShulkerBoxMultiOpenToolMod {
    public static final String MOD_ID = "shulkerboxmultiopentool";
    public static final Logger LOGGER = LogManager.getLogger();
    public static KeyBinding keyBindings;

    public static ContainerType<MultiOpenContainer> MULTI_OPEN_TOOL_CONT;

    public ShulkerBoxMultiOpenToolMod() {
        final IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();
        modEventBus.addListener(this::doClientStuff);
        MinecraftForge.EVENT_BUS.register(ClientKeyEventHandler.class);
        MinecraftForge.EVENT_BUS.register(this);
    }

    private void doClientStuff(final FMLClientSetupEvent event) {
        keyBindings = new KeyBinding("key.opengui.desc", KeyEvent.VK_J, "key.momonga.category");
        ClientRegistry.registerKeyBinding(keyBindings);
        ScreenManager.registerFactory(MULTI_OPEN_TOOL_CONT, MultiOpenToolScreen::new);
    }

    @Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
    public static class RegistryEvents {

        @SubscribeEvent
        public static void onContainerRegister(final RegistryEvent.Register<ContainerType<?>> event) {
            MULTI_OPEN_TOOL_CONT = IForgeContainerType.<MultiOpenContainer>create(MultiOpenContainer::new);
            MULTI_OPEN_TOOL_CONT.setRegistryName(new ResourceLocation(MOD_ID, "multi_open_tool"));
            event.getRegistry().register(MULTI_OPEN_TOOL_CONT);
        }

        @SubscribeEvent
        public static void onBlocksRegistry(final RegistryEvent.Register<Block> blockRegistryEvent) {
            // register a new block here
            LOGGER.info("HELLO from Register Block");
        }
    }
}
