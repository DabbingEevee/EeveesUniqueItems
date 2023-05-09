package com.existingeevee.uniqueitems;

import org.slf4j.Logger;

import com.existingeevee.uniqueitems.registries.ItemRegistries;
import com.mojang.logging.LogUtils;

import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.network.NetworkRegistry;
import net.minecraftforge.network.simple.SimpleChannel;

@Mod(EeveesUniqueItems.MODID)
public class EeveesUniqueItems {
	// Define mod id in a common place for everything to reference
	public static final String MODID = "eevees_unique_items";
	// Directly reference a slf4j logger
	public static final Logger LOGGER = LogUtils.getLogger();
	
	public static final String PROTOCOL_VERSION = "1.0.0";
	
	public static final SimpleChannel NETWORK_CHANNEL = NetworkRegistry.newSimpleChannel(
			new ResourceLocation(MODID, "channel"),
			() -> PROTOCOL_VERSION,
			PROTOCOL_VERSION::equals,
			PROTOCOL_VERSION::equals);
	
	// The main mod constructor that would be called on startup
	public EeveesUniqueItems() {
		int messageIndex = 0;
		NETWORK_CHANNEL.registerMessage(messageIndex++, SimpleCustomItemMessage.class, SimpleCustomItemMessage::write, SimpleCustomItemMessage::new, SimpleCustomItemMessage::handle);

		IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();

		ItemRegistries.ITEMS.register(modEventBus);
	}

}
