package sample;

import java.text.DecimalFormat;
import java.util.HashMap;


public class Currency {
    HashMap<String, Double> exchangeRateForDollar = new HashMap<String, Double>();
    HashMap<String, Double> exchangeRateForOther = new HashMap<String, Double>();
    Currency(){
        mappingToDollarandOther();
    }

    public void mappingToDollarandOther() {
        exchangeRateForDollar.put("Australia", 0.73);
        exchangeRateForDollar.put("Brazil", 0.19 );
        exchangeRateForDollar.put("England", 1.34);
        exchangeRateForDollar.put("India", 0.014);
        exchangeRateForDollar.put("Japan", 0.0096);
        exchangeRateForDollar.put("Nepal", 0.0084);
        exchangeRateForDollar.put("Russia", 0.013);
        exchangeRateForDollar.put("South Africa", 0.065);
        exchangeRateForDollar.put("South Korea", 0.00090);
        exchangeRateForDollar.put("United States of America", 1.0);

        exchangeRateForOther.put("Australia", 1.37);
        exchangeRateForOther.put("Brazil", 5.38);
        exchangeRateForOther.put("England", 0.75);
        exchangeRateForOther.put("India", 74.16);
        exchangeRateForOther.put("Japan", 103.86);
        exchangeRateForOther.put("Nepal", 118.58);
        exchangeRateForOther.put("Russia", 76.21);
        exchangeRateForOther.put("South Africa", 15.41);
        exchangeRateForOther.put("South Korea", 1112.58);
        exchangeRateForOther.put("United States of America", 1.0);

    }

    //ex. country1 == Australia, country2 = America, enteredAmount = 50;
    public Double OtherToOther(String country1, String country2, String enteredAmount) {
        if (country1 == country2){
            return Double.parseDouble(enteredAmount);
        }else{
            Double amount = Double.parseDouble(enteredAmount);
            //Other to Dollar
            amount = amount * exchangeRateForDollar.get(country1);
            amount = amount * exchangeRateForOther.get(country2);
            DecimalFormat df = new DecimalFormat("#.00");
            amount = Double.parseDouble(df.format(amount));
            return amount;
        }
    }
}
