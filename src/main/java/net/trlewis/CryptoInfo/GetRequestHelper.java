package net.trlewis.CryptoInfo;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

class GetRequestHelper
{
    static JsonObject getJsonFromGetRequest(String getQuery)
    {
        try
        {
            URL url = new URL(getQuery);
            URLConnection conn = url.openConnection();
            InputStream inStream = conn.getInputStream();

            JsonParser jp = new JsonParser();
            JsonElement root = jp.parse(new InputStreamReader(inStream));
            return root.getAsJsonObject();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }
}
