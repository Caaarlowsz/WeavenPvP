package br.com.skyprogrammer.cophenix.zenixpvp.utilitaries.strings;

public class StringMessage {
	public static String createMessageFromArrayOfString(final int valueOfFirstArgsIndex,
			final String[] arrayOfStringToCreate, final String defaultMessageString) {
		final StringBuilder localStringBuilder = new StringBuilder();
		for (int i = valueOfFirstArgsIndex; i < arrayOfStringToCreate.length; ++i) {
			String spaceBetweenMessages = " ";
			if (i >= arrayOfStringToCreate.length - 1) {
				spaceBetweenMessages = "";
			}
			localStringBuilder.append(String.valueOf(arrayOfStringToCreate[i]) + spaceBetweenMessages);
		}
		if (localStringBuilder.length() == 0) {
			localStringBuilder.append(defaultMessageString);
		}
		return localStringBuilder.toString();
	}
}
