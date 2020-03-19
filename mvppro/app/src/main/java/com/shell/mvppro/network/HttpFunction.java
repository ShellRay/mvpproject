package com.shell.mvppro.network;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

/**
 * Created by yuanwei on 2017/9/4.
 */

public class HttpFunction
{
    private static Map<String, String> getHeader()
    {
//        HttpConfig config = NetManager.getInstance().getHttpConfig();
//        return config != null ? config.getHeader() : null;
        return null;
    }

    public static String get(String url) throws Exception
    {

        Map<String, String> header = getHeader();
        HttpURLConnection connection = (HttpURLConnection) new URL(url).openConnection();
        connection.setConnectTimeout(10 * 1000);
        connection.setReadTimeout(10 * 1000);
        connection.setRequestMethod("GET");

        if (header != null)
        {
            Set<Map.Entry<String, String>> set = header.entrySet();
            for (Map.Entry<String, String> entry : set)
            {
                connection.setRequestProperty(entry.getKey(), entry.getValue());
            }
        }

        String result = read(connection);
        connection.disconnect();
        return result;
    }

    public static String post(String url, Map<String, String> params) throws Exception
    {
        Map<String, String> header = getHeader();

        HttpURLConnection connection = (HttpURLConnection) new URL(url).openConnection();
        connection.setConnectTimeout(10 * 1000);
        connection.setReadTimeout(10 * 1000);
        connection.setDoOutput(true);
        connection.setRequestMethod("POST");

        if (header != null)
        {
            Set<Map.Entry<String, String>> set = header.entrySet();
            for (Map.Entry<String, String> entry : set)
            {
                connection.setRequestProperty(entry.getKey(), entry.getValue());
            }
        }

        OutputStreamWriter writer = null;

        if (params != null && params.size() > 0)
        {
            StringBuffer buffer = new StringBuffer();
            Iterator<String> iterator = params.keySet().iterator();

            while (iterator.hasNext())
            {
                String key = iterator.next();
                String value = params.get(key);
                buffer.append(key);
                buffer.append("=");
                buffer.append(value);

                if (iterator.hasNext())
                {
                    buffer.append("&");
                }
            }

            OutputStream fos = connection.getOutputStream();
            writer = new OutputStreamWriter(fos);
            writer.write(buffer.toString());
            writer.flush();
        }

        String result = read(connection);

        if (writer != null)
        {
            writer.close();
        }

        connection.disconnect();

        return result;
    }

    private static String read(HttpURLConnection connection) throws Exception
    {
        int responseCode = connection.getResponseCode();
        StringBuilder builder = new StringBuilder();

        if (responseCode == HttpURLConnection.HTTP_OK)
        {
            InputStream is = connection.getInputStream();

            BufferedReader br = new BufferedReader(new InputStreamReader(is));
            String line = null;

            while ((line = br.readLine()) != null)
            {
                builder.append(line);
            }

            br.close();
        }

        return builder.toString();
    }
}
