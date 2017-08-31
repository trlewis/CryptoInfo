package net.trlewis.CryptoInfo;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

class GetRequestHelper
{
    /**
     * Makes a GET call to the url passed in and converts the returned result into a JsonObject.
     * @param getQuery The complete url of the GET query (including parameters)
     * @return The Json returned by the GET request.
     */
    static JsonObject getJsonFromGetRequest(String getQuery)
    {
        try {
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

    /**
     * Gets the raw string representation of the value returned by a GET request.
     * @param getString The url (including parameters) of the GET request.
     * @return The raw contents returned by the GET request.
     */
    static String getRawGetRequest(String getString)
    {
        try {
            URL url = new URL(getString);
            URLConnection conn = url.openConnection();
            return inputStreamToString(conn.getInputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Takes the input stream from a URLConnection.getInputStream() call and returns the contents
     * from the connection. Good for simple API calls that don't use JSON.
     * @param stream The stream to read the data from.
     * @return The data returned by the URL call.
     */
    private static String inputStreamToString(InputStream stream)
    {
        StringBuilder content = new StringBuilder();

        try (BufferedReader reader = new BufferedReader(new InputStreamReader(stream))) {
            String line;
            while ((line = reader.readLine()) != null)
                content.append(line).append("\n");
        } catch (IOException e) {
            e.printStackTrace();
        }

        return content.toString().trim();
    }
}
