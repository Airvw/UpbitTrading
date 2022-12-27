import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

public class Indicator {
    public Double calculate(List<Double> priceList) throws IOException {
        int n = priceList.size();
        Double[] values = getFirstAUAD(priceList);
        Double AU = values[0];
        Double AD = values[1];
        for(int i = 14; i < n; i++){
            Double nowPrice = priceList.get(i);
            if(nowPrice > 0) {
                AU = (AU * 13 + priceList.get(i)) / 14;
                AD = AD * 13 / 14;
            }
            else{
                AU = AU * 13 / 14;
                AD = (AD * 13 + Math.abs(priceList.get(i))) / 14;
            }
        }
        double RS = AU / AD;
        return Math.round(RS / (1 + RS) * 100) / 100.0;
    }

    public Double[] getFirstAUAD(List<Double> priceList) {
        double gainPrice = 0D;
        double declinePrice = 0D;
        for(int i = 0; i < 14; i++){
            Double num = priceList.get(i);
            if(num >= 0) gainPrice += num;
            if(num < 0) declinePrice += Math.abs(num);
        }
        double AU = gainPrice / 14;
        double AD = declinePrice / 14;
        return new Double[]{AU, AD};
    }

    public List<Double> getCoinChangePrice(JSONArray jsonArray) {
        List<Double> result = new ArrayList<>();
        for(int i = 0; i < jsonArray.length(); i++){
            JSONObject jsonObject = jsonArray.getJSONObject(i);
            System.out.println("utc : " + jsonObject.getString("candle_date_time_utc") + " kst : " + jsonObject.getString("candle_date_time_kst"));
            if(jsonObject.getDouble("change_rate") == 0D){
                result.add(0D);
                continue;
            }
            result.add(jsonObject.getDouble("change_price"));
        }
        return result;
    }

    public List<String> getLastDate(JSONArray jsonArray) {
        List<String> result = new ArrayList<>();
        IntStream.range(0, jsonArray.length()).mapToObj(jsonArray::getJSONObject)
                .forEach(oj -> result.add(oj.getString("candle_date_time_kst")));
        return result;
    }
}
