package sample;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.layout.AnchorPane;
import javafx.util.Duration;

import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.sql.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.ResourceBundle;
import java.util.TimeZone;


public class TimeController  implements Initializable {

    public Menu menuCapital;
    public ComboBox selectCountry2;
    public ComboBox selectCountry1;

    static String s = null;
    static Integer g = null;
    public Label lblConverted;
    public AnchorPane currencyPane;
    public AnchorPane timePane;
    public Label lblTime1;
    public Label lblTime2;
    Countries country = null;
    boolean flag1 = false;
    boolean flag2 = false;
    static Timeline clock1;
    static Timeline clock2;


    public TimeController() throws URISyntaxException {
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        selectCountry1.setItems(CapitalController.list);
        selectCountry2.setItems(CapitalController.list);

    }

    //Establishing Database Connection
    public Connection getConnection(){

        try{
            /*Use the below line of code only if you have a local server with appropriate data*/
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

    public void countryChanged1(ActionEvent actionEvent) throws SQLException {
        Countries country = getSelectedCountry(actionEvent);
        System.out.println(country);
        if (flag1)
            clock1.stop();
        initClock1(country);

    }

    //Country selection 2
    public void countryChanged2(ActionEvent actionEvent) throws SQLException {
        Countries country = getSelectedCountry(actionEvent);
        System.out.println(country);
        if (flag2)
            clock2.stop();
        initClock2(country);
    }

    private void initClock1(Countries country) {
            clock1 = new Timeline(new KeyFrame(Duration.ZERO, e -> {
                lblTime1.setText(requiredTime(country.getCountry().toString()));
                flag1 = true;
            }), new KeyFrame(Duration.seconds(1)));
            clock1.setCycleCount(Animation.INDEFINITE);
            clock1.play();
    }
    private void initClock2(Countries country) {
         clock2 = new Timeline(new KeyFrame(Duration.ZERO, e -> {
            lblTime2.setText(requiredTime(country.getCountry().toString()));
            flag2 = true;
        }), new KeyFrame(Duration.seconds(1)));
        clock2.setCycleCount(Animation.INDEFINITE);
        clock2.play();
    }

    private String requiredTime(String zone) {
        Date today = new Date();
        DateFormat df = new SimpleDateFormat("dd-MM-yy HH:mm:ss z");
        String IST;
        switch (zone){

            case "Australia":
                df.setTimeZone(TimeZone.getTimeZone("Australia/Canberra"));
                IST= df.format(today);break;
            case "Brazil":
                df.setTimeZone(TimeZone.getTimeZone("Brazil/Acre"));
                IST= df.format(today);break;
            case "England":
                df.setTimeZone(TimeZone.getTimeZone("Europe/London"));
                IST= df.format(today);break;
            case "India":
                df.setTimeZone(TimeZone.getTimeZone("Asia/Kolkata"));
                IST= df.format(today);break;
            case "Japan":
                df.setTimeZone(TimeZone.getTimeZone("Japan"));
                IST= df.format(today);break;
            case "Nepal":
                df.setTimeZone(TimeZone.getTimeZone("Asia/Katmandu"));
                IST= df.format(today);break;
            case "Russia":
                df.setTimeZone(TimeZone.getTimeZone("Europe/Kaliningrad"));
                IST= df.format(today);break;
            case "South Africa":
                df.setTimeZone(TimeZone.getTimeZone("Africa/Johannesburg"));
                IST= df.format(today);break;
            case "South Korea":
                df.setTimeZone(TimeZone.getTimeZone("Asia/Seoul"));
                IST= df.format(today);break;
            case "United States of America":
                df.setTimeZone(TimeZone.getTimeZone("America/New_York"));
                IST= df.format(today);break;
            default: IST = "Unknown Timezone";
        }
        return IST;
    }

    //Switch to Capital Window
    public void showCapitalWindow(ActionEvent actionEvent) throws IOException {
        AnchorPane pane = FXMLLoader.load(getClass().getResource("capital.fxml"));
        timePane.getChildren().setAll(pane);
    }

    public void showCurrencyWindow(ActionEvent actionEvent) throws IOException {
        AnchorPane pane = FXMLLoader.load(getClass().getResource("currency.fxml"));
        timePane.getChildren().setAll(pane);
    }

    //Switch to Time Window
    public void showTimeWindow(ActionEvent actionEvent) throws IOException {
    }

}

