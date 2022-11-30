import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import utils.DataParsing;
import utils.HttpSetting;

import java.io.IOException;
import java.util.List;
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
        String resultStr = HttpSetting.getKRWTickers("KRW-BTC");
        assertThat(resultStr).isNotNull();
    }

    @Test
    @DisplayName("여러 코인 시세 확인 문자열(쿼리스트링)로 테스트 -> Request-URI Too Large")
    void getTickersByListTest() throws IOException{
        String coinStr = HttpSetting.getKRWCoins();
        String resultStr = HttpSetting.getKRWTickers(coinStr);
        JSONArray jsonArray = new JSONArray(resultStr);
        IntStream.range(0, jsonArray.length()).mapToObj(jsonArray::getJSONObject)
                .forEach(oj -> System.out.println(oj.getDouble("trade_price")));
    }

    @Test
    @DisplayName("KRW 코인 하나씩 시세 확인 테스트")
    void getKRWTickersTest() throws IOException {
        List<String> coinList = DataParsing.getKRWCoinList();
        for(String coin : coinList){
            String resultStr = HttpSetting.getKRWTickers(coin);
            JSONArray jsonArray = new JSONArray(resultStr);
            JSONObject jsonObject = jsonArray.getJSONObject(0);
            assertThat(jsonObject.keySet().size()).isEqualTo(26);
            Double price = jsonObject.getDouble("trade_price");
            System.out.println(coin + " : " + DataParsing.priceFormat(price));
        }
    }
}
