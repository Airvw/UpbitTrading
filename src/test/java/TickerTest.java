import org.json.JSONArray;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import utils.HttpSetting;

import java.io.IOException;
import java.util.stream.IntStream;

import static org.assertj.core.api.Assertions.assertThat;

public class TickerTest {

    @BeforeEach
    void setUp(){

    }

    @Test
    @DisplayName("시세 확인 테스트")
    void tickerNow() throws IOException {
//        OkHttpClient client = new OkHttpClient();
//        Request request = new Request.Builder()
//                .url("https://api.upbit.com/v1/ticker?markets=KRW-BTC")
//                .get()
//                .addHeader("accept", "application/json")
//                .build();
//
//        Response response = client.newCall(request).execute();
//        System.out.println(response.body().string());
        String resultStr = HttpSetting.getKRWTicker("KRW-BTC");
        assertThat(resultStr).isNotNull();
    }

    @Test
    @DisplayName("여러 코인 시세 확인 테스트 -> 414 Request-URI Too Large 오류")
    void getTickers() throws IOException{
        String coinStr = HttpSetting.getKRWCoinList();
        String resultStr = HttpSetting.getKRWTicker(coinStr);
        System.out.println(resultStr);
        JSONArray jsonArray = new JSONArray(resultStr);
        IntStream.range(0, resultStr.length()).mapToObj(jsonArray::getJSONObject)
                .forEach(oj -> System.out.println(oj.getString("trade_price")));
    }
}
