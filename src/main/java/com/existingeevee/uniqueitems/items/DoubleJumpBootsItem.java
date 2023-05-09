package com.existingeevee.uniqueitems.items;

import javax.annotation.Nullable;

import com.existingeevee.uniqueitems.EeveesUniqueItems;
import com.existingeevee.uniqueitems.SimpleCustomItemMessage;

import net.minecraft.client.Minecraft;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.Level;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.InputEvent.Key;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.living.LivingEvent.LivingJumpEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public class DoubleJumpBootsItem extends ArmorItem {

	public DoubleJumpBootsItem() {
		super(DoubleJumpBootsArmorMaterial.INSTANCE, EquipmentSlot.FEET, new Properties().stacksTo(1));
		MinecraftForge.EVENT_BUS.register(this);
	}

	@OnlyIn(Dist.CLIENT) // Makes this method only there on the client side since thats where keys are
							// handled
	@SubscribeEvent(receiveCanceled = true) // subscribes this event so the event bus knows where to find it
	public void onKeyEvent(Key event) {
		if (Minecraft.getInstance().screen == null && Minecraft.getInstance().player != null && !Minecraft.getInstance().player.isOnGround()) {
			if (Minecraft.getInstance().options.keyJump.consumeClick()) {
				EeveesUniqueItems.NETWORK_CHANNEL.sendToServer(new SimpleCustomItemMessage(0));
			}
		}
	}

	@SubscribeEvent
	public void onJumpEvent(LivingJumpEvent event) {
		if (event.getEntity() instanceof Player player) {
			ItemStack stack = player.getInventory().getArmor(0);
			if (player.isOnGround() && stack.getItem() instanceof DoubleJumpBootsItem) {
				stack.getOrCreateTag().putBoolean("JustJumped", true);
			}
		}
	}

	@Override
	public CompoundTag getShareTag(ItemStack stack) {
		return stack.serializeNBT();
	}

	@Override
	public void onArmorTick(ItemStack stack, Level world, Player entity) {
		if (entity.isOnGround()) {
			if (stack.getOrCreateTag().getBoolean("HasJumped")) {
				stack.getOrCreateTag().putBoolean("HasJumped", false);
			}
		}
	}

	@Override
	public void readShareTag(ItemStack stack, @Nullable CompoundTag nbt) {
		stack.deserializeNBT(nbt);
	}

	public static class DoubleJumpBootsArmorMaterial implements ArmorMaterial {
		public static final DoubleJumpBootsArmorMaterial INSTANCE = new DoubleJumpBootsArmorMaterial();

		private DoubleJumpBootsArmorMaterial() {
		}

		@Override
		public int getDurabilityForSlot(EquipmentSlot pSlot) {
			return 0; // 0 makes it unbreakable
		}

		@Override
		public int getDefenseForSlot(EquipmentSlot pSlot) {
			return -1; // Makes it reduce your defense to balance things
		}

		@Override
		public int getEnchantmentValue() {
			return 10;
		}

		@Override
		public SoundEvent getEquipSound() {
			return SoundEvents.ARMOR_EQUIP_ELYTRA;
		}

		@Override
		public Ingredient getRepairIngredient() {
			return Ingredient.EMPTY; // Unrepairable
		}

		@Override
		public String getName() {
			return "double_jump_boots";
		}

		@Override
		public float getToughness() {
			return 0;
		}

		@Override
		public float getKnockbackResistance() {
			return 0;
		}
	}

}
