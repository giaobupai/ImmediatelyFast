package net.raphimc.immediatelyfast;

import com.llamalad7.mixinextras.MixinExtrasBootstrap;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.loader.api.FabricLoader;
import net.fabricmc.loader.api.entrypoint.PreLaunchEntrypoint;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import sun.misc.Unsafe;

import java.lang.reflect.Field;

public class ImmediatelyFast implements ClientModInitializer, PreLaunchEntrypoint {

    public static final Logger LOGGER = LoggerFactory.getLogger("ImmediatelyFast");
    public static final Unsafe UNSAFE = getUnsafe();

    @Override
    public void onInitializeClient() {
        FabricLoader.getInstance().getModContainer("immediatelyfast").ifPresent(modContainer -> {
            LOGGER.info("Loading ImmediatelyFast " + modContainer.getMetadata().getVersion().getFriendlyString());
        });
        //System.load("C:\\Program Files\\RenderDoc\\renderdoc.dll");
    }

    @Override
    public void onPreLaunch() {
        MixinExtrasBootstrap.init();
    }

    private static Unsafe getUnsafe() {
        try {
            for (Field field : Unsafe.class.getDeclaredFields()) {
                if (field.getType().equals(Unsafe.class)) {
                    field.setAccessible(true);
                    return (Unsafe) field.get(null);
                }
            }
        } catch (Throwable ignored) {
        }
        throw new IllegalStateException("Unable to get Unsafe instance");
    }

}
