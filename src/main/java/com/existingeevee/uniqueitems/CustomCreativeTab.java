package com.existingeevee.uniqueitems;

import com.existingeevee.uniqueitems.registries.ItemRegistries;

import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;

public final class CustomCreativeTab extends CreativeModeTab {

	public static final CustomCreativeTab TAB = new CustomCreativeTab();
	
	private CustomCreativeTab() {
		super("unique_items");
	}

	@Override
	public ItemStack makeIcon() {
		return new ItemStack(ItemRegistries.MOBFINDER.get());
	}

}
