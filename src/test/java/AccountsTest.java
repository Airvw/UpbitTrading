import org.apache.http.HttpEntity;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import utils.HttpSetting;

import java.io.IOException;
import java.util.stream.IntStream;

import static org.assertj.core.api.Assertions.assertThat;

public class AccountsTest {

    private final String[] keyArray = new String[]{"currency", "balance", "locked", "avg_buy_price", "avg_buy_price_modified", "unit_currency"};

    @BeforeEach
    @DisplayName("key 파일 읽기 테스트")
    void setUp(){
    }

    @Test
    @DisplayName("코인리스트 테스트")
    void coinListTest() throws IOException {
        HttpEntity entity = HttpSetting.getAccounts();
        System.out.println(EntityUtils.toString(entity, "UTF-8"));
        assertThat(entity).isNotNull();
    }

    @Test
    @DisplayName("코인리스트 파싱 테스트")
    void coinListParseTest() throws IOException {
        String targetKey = keyArray[0];
        HttpEntity entity = HttpSetting.getAccounts();
        String resultStr = EntityUtils.toString(entity);
        JSONArray jsonArray = new JSONArray(resultStr);
        IntStream.range(0, jsonArray.length())
                .mapToObj(jsonArray::getJSONObject)
                .forEach(oj -> assertThat(oj.length()).isEqualTo(keyArray.length));
        IntStream.range(0, jsonArray.length())
                .mapToObj(jsonArray::getJSONObject)
                .forEach(oj -> System.out.println(oj.get(targetKey)));
    }
}
