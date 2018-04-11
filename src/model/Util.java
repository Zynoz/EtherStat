package model;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.Charset;
import java.util.List;

public class Util {

    public static String getJson(String string) {
        StringBuilder sb = new StringBuilder();
        URLConnection urlConn;
        InputStreamReader in = null;
        try {
            URL url = new URL(string);
            urlConn = url.openConnection();
            urlConn.addRequestProperty("User-Agent", "Mozilla/4.76");
            urlConn.setReadTimeout(60 * 1000);
            if (urlConn.getInputStream() != null) {
                in = new InputStreamReader(urlConn.getInputStream(),
                        Charset.defaultCharset());
                BufferedReader bufferedReader = new BufferedReader(in);
                int cp;
                while ((cp = bufferedReader.read()) != -1) {
                    sb.append((char) cp);
                }
                bufferedReader.close();
            }
            if (in != null) {
                in.close();
            }
        } catch (Exception e) {
            throw new RuntimeException("Exception while calling URL:"+ string, e);
        }
        return sb.toString();
    }

    public static List<JsonWorker> getWorkers(String minerAddress) {
        String fullJSON = getJson("https://api.ethermine.org/miner/" + minerAddress + "/workers");
        String withoutStart = fullJSON.replace("{\"status\":\"OK\",\"data\":", "");
        String withoutEnd = withoutStart.substring(0, withoutStart.length() - 1);
        Gson gson = new Gson();
        Type collectionType = new TypeToken<List<JsonWorker>>(){}.getType();
        return gson.fromJson(withoutEnd, collectionType);
    }
}