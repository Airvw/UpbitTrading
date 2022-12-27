import org.json.JSONArray;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import utils.DataParsing;
import utils.HttpSetting;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class IndicatorsTest {

    private Indicator indicator;

    @BeforeEach
    void setUp(){
        indicator = new Indicator();
    }

    @Test
    @DisplayName("변화 금액 count 테스트")
    void countTest() throws IOException {
        List<String> coinList = DataParsing.getKRWCoinList();
        for(String coin : coinList) {
            String resultStr = HttpSetting.getDayTradePrices(coin);
            JSONArray jsonArray = new JSONArray(resultStr);
            List<Double> priceList = indicator.getCoinChangePrice(jsonArray);
            assertThat(priceList.size()).isLessThanOrEqualTo(200);
        }
    }

    @Test
    @DisplayName("RMI 지표 테스트")
    void rmiTest() throws IOException {
        List<String> coinList = DataParsing.getKRWCoinList();
        for(String coin : coinList) {
                String resultStr = HttpSetting.getDayTradePrices(coin);
                JSONArray jsonArray = new JSONArray(resultStr);
                List<Double> priceList = indicator.getCoinChangePrice(jsonArray);
                Double rsi = indicator.calculate(priceList, coin) ;
                System.out.println(coin + " : " + rsi);
        }
    }

    @Test
    @DisplayName("KRW-NEO의 change_price null 값 확인")
    void NEOTest() throws IOException {
        String coin = "KRW-NEO";
        String resultStr = HttpSetting.getDayTradePrices(coin);
        JSONArray jsonArray = new JSONArray(resultStr);
        List<Double> priceList = indicator.getCoinChangePrice(jsonArray);
    }

    @Test
    @DisplayName("KRW-BTC의 to(쿼리스트링)에 대한 테스트")
    void toTest() throws IOException {
        String coin = "KRW-BTC";
        String resultStr = HttpSetting.getDayTradePrices(coin);
        JSONArray jsonArray = new JSONArray(resultStr);
        List<String> dateList = indicator.getLastDate(jsonArray);
        String LastDate = dateList.get(dateList.size() - 1);
        LocalDateTime date = LocalDateTime.parse(LastDate);
        LocalDateTime now = date.minusDays(1);
        resultStr = HttpSetting.getDayTradePrices(coin, now.toString());
        jsonArray = new JSONArray(resultStr);
        List<String> nextDateList = indicator.getLastDate(jsonArray);
        assertThat(dateList.size()).isLessThanOrEqualTo(200);
        assertThat(nextDateList.size()).isLessThanOrEqualTo(200);
    }
}
