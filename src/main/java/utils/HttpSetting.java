package utils;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClientBuilder;

import java.io.IOException;

public class HttpSetting {

    private static final String SERVER_URL = "https://api.upbit.com/v1";
    private static final String[] HEADER_TYPES = new String[]{"content-type", "accept", "authorization"};

    private static final String[] CONTENT_TYPE_VALUES = new String[]{"application/json", "multipart/formed-data"};
    private static final String MARKET_ALL_URL = "/market/all";
    private static final String ACCOUNT_URL = "/accounts";
    private static final String TICKER_URL = "/ticker?markets=";

    private static final String CANDLE_URL = "/candles";

    private static final String DAY_PARAM = "/days?count=200&market=";


    public static HttpEntity getAccounts() throws IOException {
        HttpClient client = HttpClientBuilder.create().build();
        HttpGet request = new HttpGet(SERVER_URL + ACCOUNT_URL);
        request.setHeader(HEADER_TYPES[0], CONTENT_TYPE_VALUES[0]);
        request.addHeader(HEADER_TYPES[2], Token.getToken());
        HttpResponse response = client.execute(request);
        return response.getEntity();
    }

    public static String getKRWCoins() throws IOException{
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url(SERVER_URL + MARKET_ALL_URL + "?isDetails=true")
                .get()
                .addHeader(HEADER_TYPES[1], CONTENT_TYPE_VALUES[0])
                .build();
        Response response = client.newCall(request).execute();
        return response.body().string();
    }

    public static String getKRWTickers(String queryString) throws IOException{
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url(SERVER_URL + TICKER_URL + queryString)
                .get()
                .addHeader(HEADER_TYPES[1], CONTENT_TYPE_VALUES[0])
                .build();
        Response response = client.newCall(request).execute();
        return response.body().string();
    }


    public static String getDayTradePrices(String coin) throws IOException {
        OkHttpClient client = new OkHttpClient();

        Request request = new Request.Builder()
                .url(SERVER_URL + CANDLE_URL + DAY_PARAM + coin)
                .get()
                .addHeader(HEADER_TYPES[1], CONTENT_TYPE_VALUES[0])
                .build();

        Response response = client.newCall(request).execute();
        return response.body().string();
    }

    public static String getDayTradePrices(String coin, String to) throws IOException {
        OkHttpClient client = new OkHttpClient();

        Request request = new Request.Builder()
                .url(SERVER_URL + CANDLE_URL + DAY_PARAM + coin + "&to=" + to)
                .get()
                .addHeader(HEADER_TYPES[1], CONTENT_TYPE_VALUES[0])
                .build();

        Response response = client.newCall(request).execute();
        return response.body().string();
    }
}
