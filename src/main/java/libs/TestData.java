package libs;

import api.apiPrivatB.CurrencyDTO;

public class TestData {
    public final static String VALID_LOGIN = "auto456";
    public final static String VALID_PASSWORD = "123456qwerty";

    private static CurrencyDTO[] apiCurrencyDTO;

    public static CurrencyDTO[] getApiCurrencyDTO() {
        return apiCurrencyDTO;
    }

    public void setApiCurrencyDTO(CurrencyDTO[] apiCurrencyDTO) {
        TestData.apiCurrencyDTO = apiCurrencyDTO;
    }
}
