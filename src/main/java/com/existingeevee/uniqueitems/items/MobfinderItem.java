package com.existingeevee.uniqueitems.items;

import com.existingeevee.uniqueitems.CustomCreativeTab;

import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;

public class MobfinderItem extends Item {
	
	public MobfinderItem() {
		//Sets the max stack size to one
		super(new Properties().stacksTo(1).tab(CustomCreativeTab.TAB));
	}

	@Override
	public InteractionResultHolder<ItemStack> use(Level pLevel, Player pPlayer, InteractionHand pUsedHand) {
		ItemStack itemstack = pPlayer.getItemInHand(pUsedHand);

		LivingEntity foundEntity = null;
		double smallestSqDistance = Integer.MAX_VALUE;
		
		// Makes a bounding box centered at the players position with the square radius
		// of 10
		AABB boundingBox = new AABB(pPlayer.getPosition(0), pPlayer.getPosition(0)).inflate(10);

		// For loops through all entities that are visible and within the boundingBox
		// excluding the player
		for (Entity e : pLevel.getEntities(pPlayer, boundingBox, e -> !e.isInvisible() && e instanceof LivingEntity)) {
			double sqDistance = e.getPosition(0).distanceToSqr(pPlayer.getPosition(0));
			if (sqDistance < smallestSqDistance) {
				foundEntity = (LivingEntity) e;
				smallestSqDistance = sqDistance;
			}
		}
		
		if (foundEntity != null) {
			//Make them glow for 10 seconds
			foundEntity.addEffect(new MobEffectInstance(MobEffects.GLOWING, 10 * 20, 0, false, false));
		}

		// consuming the action so it dosen't cause any more right click stuff to happen
		return InteractionResultHolder.success(itemstack);
	}

}
