package br.com.skyprogrammer.cophenix.zenixpvp.api.basecomponent;

import net.minecraft.server.v1_7_R4.ChatClickable;
import net.minecraft.server.v1_7_R4.ChatHoverable;
import net.minecraft.server.v1_7_R4.ChatMessage;
import net.minecraft.server.v1_7_R4.ChatModifier;
import net.minecraft.server.v1_7_R4.EnumClickAction;
import net.minecraft.server.v1_7_R4.EnumHoverAction;
import net.minecraft.server.v1_7_R4.IChatBaseComponent;

public class ChatBaseComponentAPI {
	public static IChatBaseComponent createHoverShowTextMessage(final String stringInChat,
			final String... stringsToBeDisplayed) {
		final String[] arrayOfStringMessage = stringsToBeDisplayed.clone();
		final StringBuilder stringBuilder = new StringBuilder();
		if (arrayOfStringMessage.length >= 1) {
			for (int i = 0; i < arrayOfStringMessage.length; ++i) {
				String skip = "\n";
				if (i >= arrayOfStringMessage.length - 1) {
					skip = "";
				}
				stringBuilder.append(String.valueOf(arrayOfStringMessage[i]) + skip);
			}
		}
		final IChatBaseComponent iChatBaseComponent = (IChatBaseComponent) new ChatMessage(stringInChat, new Object[0]);
		iChatBaseComponent.setChatModifier(new ChatModifier());
		iChatBaseComponent.getChatModifier().a(new ChatHoverable(EnumHoverAction.SHOW_TEXT,
				(IChatBaseComponent) new ChatMessage(stringBuilder.toString(), new Object[0])));
		return iChatBaseComponent;
	}

	public static IChatBaseComponent createHoverCommandMessage(final String stringInChat, final String commandToRun,
			final String... stringsToBeDisplayed) {
		final String[] arrayOfStringMessage = stringsToBeDisplayed.clone();
		final StringBuilder stringBuilder = new StringBuilder();
		if (arrayOfStringMessage.length >= 1) {
			for (int i = 0; i < arrayOfStringMessage.length; ++i) {
				String skip = "\n";
				if (i >= arrayOfStringMessage.length - 1) {
					skip = "";
				}
				stringBuilder.append(String.valueOf(arrayOfStringMessage[i]) + skip);
			}
		}
		final IChatBaseComponent iChatBaseComponent = (IChatBaseComponent) new ChatMessage(stringInChat, new Object[0]);
		iChatBaseComponent.setChatModifier(new ChatModifier());
		iChatBaseComponent.getChatModifier()
				.setChatClickable(new ChatClickable(EnumClickAction.RUN_COMMAND, commandToRun));
		iChatBaseComponent.getChatModifier().a(new ChatHoverable(EnumHoverAction.SHOW_TEXT,
				(IChatBaseComponent) new ChatMessage(stringBuilder.toString(), new Object[0])));
		return iChatBaseComponent;
	}

	public static IChatBaseComponent createHoverOpenUrlMessage(final String stringInChat,
			final String stringToBeDisplayed, final String urlStringToOpen) {
		final IChatBaseComponent iChatBaseComponent = (IChatBaseComponent) new ChatMessage(stringInChat, new Object[0]);
		iChatBaseComponent.setChatModifier(new ChatModifier());
		iChatBaseComponent.getChatModifier()
				.setChatClickable(new ChatClickable(EnumClickAction.OPEN_URL, urlStringToOpen));
		iChatBaseComponent.getChatModifier().a(new ChatHoverable(EnumHoverAction.SHOW_TEXT,
				(IChatBaseComponent) new ChatMessage(stringToBeDisplayed, new Object[0])));
		return iChatBaseComponent;
	}
}
