package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.sql.*;
import java.util.ResourceBundle;


public class CurrencyController  implements Initializable {

    public Label lblCurrency1;
    public Menu menuCapital;
    public Menu menuCurrency;
    public Menu menuTime;
    public Label lblCurrency2;
    public TextField currencyValue1;
    public Button btnConvert;
    public ComboBox selectCountry2;
    public ComboBox selectCountry1;

    static String s = null;
    static Integer g = null;
    public Label lblConverted;
    public AnchorPane currencyPane;
    public CheckBox chkboxAuto;
    Countries country = null;

    public CurrencyController() throws URISyntaxException {
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        selectCountry1.setItems(CapitalController.list);
        selectCountry2.setItems(CapitalController.list);
    }

    //Establishing Database Connection
    public Connection getConnection(){

        try{
            /*Use the below line of code only if you have a local server with appropriate data. Change the name of the database*/
            Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/test", "root", "root");

            return conn;
        }catch (Exception e){
            System.out.println("Error: " + e.getMessage());
            return null;
        }
    }

    //Fetch Selected Country
    public Countries getSelectedCountry(ActionEvent actionEvent) throws SQLException {

        Connection conn = getConnection();
        String query;
        if (actionEvent.getSource().equals(selectCountry1))
            query = "SELECT * FROM countries WHERE Country='" + selectCountry1.getValue().toString() +"'";
        else
            query = "SELECT * FROM countries WHERE Country='" + selectCountry2.getValue().toString() +"'";
        Statement st = null;
        ResultSet rs = null;

        try {
            st = conn.createStatement();
            rs = st.executeQuery(query);
            rs.next();

            country = new Countries(rs.getInt("id"), rs.getString("country"), rs.getString("capital"), rs.getBlob("flag"), rs.getString("currency"));
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println(country);
        conn.close();
        return country;
    }

    //Currency Conversion
    //Country selection 2
    public void countryChanged1(ActionEvent actionEvent) throws SQLException {
        Countries country = getSelectedCountry(actionEvent);
        System.out.println(country);
        lblCurrency1.setText(country.getCurrencyName());
        setCurrencyValue1(null);
    }

    //Country selection 2
    public void countryChanged2(ActionEvent actionEvent) throws SQLException {
        Countries country = getSelectedCountry(actionEvent);
        System.out.println(country);
        lblCurrency2.setText(country.getCurrencyName());
        setCurrencyValue1(null);
    }

    //Switch to Capital Window
    public void showCapitalWindow(ActionEvent actionEvent) throws IOException {
        AnchorPane pane = FXMLLoader.load(getClass().getResource("capital.fxml"));
        currencyPane.getChildren().setAll(pane);
    }

    public void showCurrencyWindow(ActionEvent actionEvent) {
    }

    //Switch to Time Window
    public void showTimeWindow(ActionEvent actionEvent) throws IOException {
        AnchorPane pane = FXMLLoader.load(getClass().getResource("time.fxml"));
        currencyPane.getChildren().setAll(pane);
    }

    //Currency Converted Label
    public void handleButtonAction(ActionEvent actionEvent) {
        Currency currency = new Currency();
        Double convertedAmount = currency.OtherToOther(selectCountry1.getValue().toString(),selectCountry2.getValue().toString(),currencyValue1.getText());
        lblConverted.setText(convertedAmount.toString());
    }

    //Number Validation
    public void setCurrencyValue1(KeyEvent keyEvent) {
        if (currencyValue1.getText().matches("^([+]?[0-9]+)?([.|,]([0-9]{1,2})?)?$")){
            s = currencyValue1.getText();
            g = currencyValue1.getCaretPosition();
            System.out.println(g);
            if(chkboxAuto.isSelected()){
                if (currencyValue1.getText() == null || currencyValue1.getText().trim().isEmpty())
                    lblConverted.setText(" ");
                else
                    handleButtonAction(null);
            }
        }
        else{
            currencyValue1.setText(s);
            currencyValue1.positionCaret(g);
        }
    }


}

