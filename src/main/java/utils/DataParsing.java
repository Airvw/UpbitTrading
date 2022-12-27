package utils;

import org.json.JSONArray;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

public class DataParsing {

    public static List<String> getKRWCoinList() throws IOException {
        List<String> coinList = new ArrayList<>();
        String resultStr = HttpSetting.getKRWCoins();
        JSONArray jsonArray = new JSONArray(resultStr);
        IntStream.range(0, jsonArray.length()).mapToObj(jsonArray::getJSONObject)
                .filter(oj -> oj.getString("market").startsWith("KRW"))
                .forEach(oj -> coinList.add(oj.getString("market")));
        return coinList;
    }

    public static String priceFormat(Double price) {
        if(price >= 100) return String.format("%.0f", price);
        if(price >= 0.01)  return String.format("%.2f", price);
        return String.format("%.4f", price);
    }
}
