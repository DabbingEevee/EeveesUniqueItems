package com.existingeevee.uniqueitems.registries;

import com.existingeevee.uniqueitems.EeveesUniqueItems;
import com.existingeevee.uniqueitems.items.DoubleJumpBootsItem;
import com.existingeevee.uniqueitems.items.MobfinderItem;

import net.minecraft.world.item.Item;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

// Literally a class that holds all of the items to keep organized
public class ItemRegistries {

	public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, EeveesUniqueItems.MODID);

	public static final RegistryObject<Item> DOUBLE_JUMP_BOOTS = ITEMS.register("double_jump_boots", DoubleJumpBootsItem::new);
	public static final RegistryObject<Item> MOBFINDER = ITEMS.register("mobfinder", MobfinderItem::new);
	
}
