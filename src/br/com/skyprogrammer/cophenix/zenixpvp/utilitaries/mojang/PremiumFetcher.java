package br.com.skyprogrammer.cophenix.zenixpvp.utilitaries.mojang;

import java.io.Reader;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;
import java.net.URL;
import java.net.HttpURLConnection;

public class PremiumFetcher
{
    public static HttpURLConnection getConnectionFromUrl(final String urlToRequest) throws IOException {
        final HttpURLConnection httpURLConnection = (HttpURLConnection)new URL(urlToRequest).openConnection();
        httpURLConnection.setConnectTimeout(1000);
        httpURLConnection.setReadTimeout(1000);
        httpURLConnection.setRequestProperty("Content-Type", "application/json");
        httpURLConnection.setRequestProperty("User-Agent", "Premium-Checker");
        return httpURLConnection;
    }
    
    public static boolean isPremium(final String accountNameToCheck) {
        try {
            final HttpURLConnection httpURLConnection = getConnectionFromUrl("https://api.mojang.com/users/profiles/minecraft/" + accountNameToCheck);
            if (httpURLConnection.getResponseCode() == 200) {
                final BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(httpURLConnection.getInputStream()));
                final String stringFromBuffered = bufferedReader.readLine();
                if (stringFromBuffered != null && !stringFromBuffered.equals("null")) {
                    return true;
                }
            }
        }
        catch (Exception ex) {}
        return false;
    }
}
