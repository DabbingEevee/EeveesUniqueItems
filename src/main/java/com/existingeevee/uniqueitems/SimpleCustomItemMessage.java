package com.existingeevee.uniqueitems;

import java.util.function.Supplier;

import com.existingeevee.uniqueitems.items.DoubleJumpBootsItem;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.network.NetworkDirection;
import net.minecraftforge.network.NetworkEvent;

// Simple networking message for when no additional stuff is required
public class SimpleCustomItemMessage {
	public SimpleCustomItemMessage() {

	}

	public SimpleCustomItemMessage(int id) {
		this.id = id;
	}

	public SimpleCustomItemMessage(FriendlyByteBuf buffer) {
		this.id = buffer.readInt();
	}

	public static void write(SimpleCustomItemMessage msg, FriendlyByteBuf buf) {
		buf.writeInt(msg.id);
	}

	private int id;

	public static void handle(SimpleCustomItemMessage msg, Supplier<NetworkEvent.Context> ctx) {
		boolean handled = true;
		switch (msg.id) {
		case 0: {
			if (ctx.get().getDirection() == NetworkDirection.PLAY_TO_SERVER) {
				ItemStack stack = ctx.get().getSender().getInventory().getArmor(0);
				if (stack.getOrCreateTag().getBoolean("JustJumped")) {
					stack.getOrCreateTag().putBoolean("JustJumped", false);
				} else if (!ctx.get().getSender().isOnGround() && stack.getItem() instanceof DoubleJumpBootsItem && !stack.getOrCreateTag().getBoolean("HasJumped")) {
					ctx.get().getSender().jumpFromGround();
					stack.getOrCreateTag().putBoolean("HasJumped", true);
					ctx.get().getSender().hurtMarked = true;
				}
			}
			break;
		}

		default:
			handled = false;

		}

		ctx.get().setPacketHandled(handled);
	}
}
