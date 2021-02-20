package sample;


import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.sql.*;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class CapitalController implements Initializable {

    public ComboBox<String> selectCountry1;
    public ImageView imgFlag1;
    public ComboBox<String> selectCountry2;
    public ImageView imgFlag2;
    public Label lblCapital1;
    public Label lblCapital2;
    public Menu menuCapital;
    public Menu menuCurrency;
    public Menu menuTime;


    static ObservableList<String> list;
    public AnchorPane capitalPane;

    // ObservableList<String> list = FXCollections.observableArrayList("Australia", "Brazil", "England", "India", "Japan", "Nepal", "Russia", "South Africa", "South Korea", "United States of America");

    public CapitalController() throws URISyntaxException {
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            getAllCountry();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        selectCountry1.setItems(list);
        selectCountry2.setItems(list);

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


    //Country selection 1
    public void countryChanged1(ActionEvent actionEvent) throws IOException, SQLException {
        Countries country1 = getSelectedCountry(actionEvent);
        Image flag = blobToImage(country1.getFlag());
        System.out.println(country1);
        lblCapital1.setText(country1.getCapital());
        imgFlag1.setImage(flag);

    }

    //Country selection 2
    public void countryChanged2(ActionEvent actionEvent) throws IOException, SQLException {
        Countries country2 = getSelectedCountry(actionEvent);
        Image flag = blobToImage(country2.getFlag());
        System.out.println(country2);
        lblCapital2.setText(country2.getCapital());
        imgFlag2.setImage(flag);
    }

    //Get All Countries for comboBox
    public void getAllCountry() throws SQLException {
        ArrayList<String> countryList = new ArrayList<>();
        String strList;
        Connection conn = getConnection();
        String query = "SELECT country FROM countries" ;
        Statement st = conn.createStatement();
        ResultSet rs = st.executeQuery(query);
        while (rs.next()) {
            strList = rs.getString("country");
            countryList.add(strList);
        }
        list = FXCollections.observableArrayList(countryList);
        conn.close();
    }

    ////Fetch Selected Country
    public Countries getSelectedCountry(ActionEvent actionEvent) throws SQLException {
        Countries country = null;
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
        conn.close();
        return country;
    }


    //Convert Blob To Image
    public Image blobToImage(Blob blob) throws SQLException, IOException {
        Image image = new Image(blob.getBinaryStream());
        return image;
    }

    public void showCapitalWindow(ActionEvent actionEvent) throws IOException {

    }

    public void showCurrencyWindow(ActionEvent actionEvent) throws IOException {
        AnchorPane pane = FXMLLoader.load(getClass().getResource("currency.fxml"));
        capitalPane.getChildren().setAll(pane);

            /*Stage secondaryStage = new Stage();
            Parent root = FXMLLoader.load(getClass().getResource("currency.fxml"));
            secondaryStage.setTitle("Time & Currency Calculator");
            secondaryStage.setScene(new Scene(root));
            secondaryStage.show();*/
    }

    public void showTimeWindow(ActionEvent actionEvent) throws IOException {
        AnchorPane pane = FXMLLoader.load(getClass().getResource("time.fxml"));
        capitalPane.getChildren().setAll(pane);
    }
}
