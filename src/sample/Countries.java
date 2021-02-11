package sample;

import java.sql.Blob;

public class Countries {

    private final int id;
    private final Object country;
    private final String capital;
    private final Blob flag;
    private final String currencyName;

    public Countries(int id, String country, String capital, Blob flag, String currencyName) {
        this.id = id;
        this.country = country;
        this.capital = capital;
        this.flag = flag;
        this.currencyName = currencyName;
    }

    public int getId() {
        return id;
    }

    public Object getCountry() {
        return country;
    }

    public String getCapital() {
        return capital;
    }

    public Blob getFlag() {
        return flag;
    }

    public String getCurrencyName() { return currencyName; }
}
