import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.json.JSONArray;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.stream.IntStream;

import static org.assertj.core.api.Assertions.assertThat;

public class CoinAllTest {
    @BeforeEach
    void setUp(){

    }


    @Test
    @DisplayName("코인 리스트 불러오기")
    void getCoinListTest() throws IOException {
        OkHttpClient client = new OkHttpClient();

        Request request = new Request.Builder()
                .url("https://api.upbit.com/v1/market/all?isDetails=true")
                .get()
                .addHeader("accept", "application/json")
                .build();

        Response response = client.newCall(request).execute();
        System.out.println(response.body().string());
        assertThat(response.body()).isNotNull();
    }

    @Test
    @DisplayName("코인 리스트 파싱 테스트")
    void getCoinListParseTest() throws IOException {
        OkHttpClient client = new OkHttpClient();

        Request request = new Request.Builder()
                .url("https://api.upbit.com/v1/market/all?isDetails=true")
                .get()
                .addHeader("accept", "application/json")
                .build();

        Response response = client.newCall(request).execute();
        String resultStr = response.body().string();
        JSONArray jsonArray = new JSONArray(resultStr);
        IntStream.range(0, jsonArray.length()).mapToObj(jsonArray::getJSONObject)
                .filter(oj -> oj.getString("market_warning").equals("NONE"))
                .filter(oj -> oj.getString("market").startsWith("KRW"))
                .forEach(oj -> System.out.println(oj.getString("market").substring(4) + " : " + oj.getString("korean_name")));
    }
}
