package com.example.loginform;

import javafx.animation.FadeTransition;
import javafx.animation.Interpolator;
import javafx.animation.TranslateTransition;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.*;
import java.util.Objects;

public class Main extends Application {
   private static Stage window;
   private static TextField startWindowUserNameInput;
   private static TextField signUpPassWordInput;
   private static  TextField startWindowPassInput;
   private static String userName;

    private final String url="jdbc:mysql://localhost:3306/login";
    private final String username ="root";
    private final String mypassword="MyT0ny333123.";



    @Override
    public void start(Stage stage) throws Exception {
        window = stage;
        startWindow();

    }
    // here you fill ur username and password if you are signed up
   // this is the first window for this app
    public void startWindow(){
         Label usernameLabel = new Label("Username");
        Label passwordLabel = new Label("Password");
        usernameLabel.setFont(new Font(" Sans Serif",16));
        passwordLabel.setFont(new Font(" Sans Serif",16));
        startWindowUserNameInput = new TextField();
        startWindowUserNameInput.setPromptText("Username");
         startWindowPassInput = new TextField();
        startWindowPassInput.setPromptText("Password  ");
        HBox box1 = new HBox();
        box1.getChildren().addAll(usernameLabel,startWindowUserNameInput);
        HBox box2 = new HBox();
        box2.getChildren().addAll(passwordLabel,startWindowPassInput);
        box1.setSpacing(17);
        box2.setSpacing(20);
        Button loginButton = new Button("Login");
        loginButton.setStyle("-fx-background-color:#FFFF00");
        loginButton.setPadding(new Insets(5,20,5,20));
        HBox box3 = new HBox();
        box3.getChildren().add(loginButton);
        box3.setAlignment(Pos.CENTER);

        HBox box4 = new HBox();
        Label label = new Label("Not a user?");
        label.setFont(new Font(" Sans Serif",13));
        Button button = new Button("Sign up");
        button.setStyle("-fx-border-color: transparent;\n" +
                "    -fx-border-width: 0;\n" +
                "    -fx-background-radius: 0;\n" +
                "    -fx-background-color: transparent;\n" +
                "    -fx-font-family:\"Segoe UI\", Helvetica, Arial, sans-serif;\n" +
                "    -fx-font-size: 1em; /* 12 */\n" +
                "    -fx-text-fill: #828282;");
        // you can achieve the same effect with a hiperlink it is like a button but without border or background
//        Hyperlink button = new Hyperlink("Some text");
//        button.setOnAction(e -> System.out.println("Hyperlink clicked"));
        button.setPadding(new Insets(1,10,10,10));
        loginButton.setOnAction(e -> loginButtonClicked(startWindowUserNameInput.getText(),startWindowPassInput.getText()));
        //sign up button
        //this button will open a new window to take user info to sign up as a user
        button.setOnAction(e -> signUpButtonClicked());
        box4.setSpacing(10);
        box4.getChildren().addAll(label,button);

        VBox layout = new VBox(box1,box2,box3,box4);
        layout.setAlignment(Pos.CENTER);

        layout.setSpacing(20);
        Scene scene = new Scene(layout,300,300);
        window.setScene(scene);
        window.show();


    }
    // after you suxessfully login
    public void loginButtonClicked(String name ,String password){
        ResultSet nameResultSet = null;
        ResultSet passResultSet = null;
        PreparedStatement checkUserExists = null;
        PreparedStatement checkPassword = null;



        Connection connection = null;
         try{
             connection = DriverManager.getConnection(url,username,mypassword);
             checkUserExists = connection.prepareStatement("SELECT * FROM users WHERE userName =?");
             checkUserExists.setString(1,name);
             nameResultSet = checkUserExists.executeQuery();
             if(!nameResultSet.isBeforeFirst()){
                 Alert alert = new Alert(Alert.AlertType.WARNING);
                 alert.setTitle("login alert");
                 alert.setHeaderText(null);
                 alert.setContentText("username does not exist feel free to sign up");
                 alert.show();
             }else {
                 checkPassword = connection.prepareStatement("SELECT passWord FROM users WHERE userName = ?");
                 checkPassword.setString(1, name);
                 passResultSet = checkPassword.executeQuery();
                 String result = "";
                 while (passResultSet.next()) {
                     result = passResultSet.getString(1);

                 }
                 // if the result from database matches the input of user
                 if (result.equals(startWindowPassInput.getText())) {
                      // first window in the app
                     firstWindow(name);
                 } else {
                     Alert alert = new Alert(Alert.AlertType.WARNING);
                     alert.setTitle("wrong password");
                     alert.setHeaderText(null);
                     alert.setContentText("incorrect password try again");
                     alert.show();
                 }
             }

         }catch(SQLException e){
             e.printStackTrace();
         }finally {


             if(nameResultSet != null){
                 try{
                     nameResultSet.close();
                 }catch (SQLException e){
                     e.printStackTrace();
                 }
             }
             if(passResultSet != null){
                 try{
                     passResultSet.close();
                 }catch (SQLException e){
                     e.printStackTrace();
                 }
             }
             if(checkUserExists != null){
                 try{
                     checkUserExists.close();
                 }catch (SQLException e){
                     e.printStackTrace();
                 }
             }
             if(checkPassword != null){
                 try{
                     checkPassword.close();
                 }catch (SQLException e){
                     e.printStackTrace();
                 }
             }


             if(connection != null){
                 try{
                     connection.close();
                 }catch (SQLException e){
                     e.printStackTrace();
                 }
             }
         }


    }
    // take user info to sign up
    public void signUpButtonClicked(){
        Label usernameLabel = new Label("Username");
        usernameLabel.setFont(new Font(" Sans Serif",16));
        Label passwordLabel = new Label("Password");
        passwordLabel.setFont(new Font(" Sans Serif",16));
        Label favPetLabel = new Label("favorite pet");
        favPetLabel.setFont(new Font(" Sans Serif",16));
        ComboBox<String> comboBox = new ComboBox<>();
        comboBox.getItems().addAll("dog","cat","bird","mouse","fish","hamster");
        comboBox.setEditable(true);
        comboBox.setStyle("-fx-max-width:150;");
        TextField signUpUserNameInput = new TextField();
        signUpUserNameInput.setPromptText("Username");
        signUpPassWordInput = new TextField();
        signUpPassWordInput.setPromptText("Password  ");
        HBox box1 = new HBox();
        box1.getChildren().addAll(usernameLabel,signUpUserNameInput);
        HBox box2 = new HBox();
        box2.getChildren().addAll(passwordLabel,signUpPassWordInput);
        HBox box3 = new HBox();
        box3.getChildren().addAll(favPetLabel,comboBox);
        box1.setSpacing(19);
        box2.setSpacing(22);
        box3.setSpacing(9);

        Button signUpButton = new Button("Sign up");
        signUpButton.setOnAction(e -> {

            if(Objects.equals(signUpUserNameInput.getText(), "")
                    || Objects.equals(signUpPassWordInput.getText(), "")){
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("empty value");
                alert.setHeaderText(null);
                alert.setContentText("you cannot have an empty field");
                alert.show();
            }else {
                signUpUser( signUpUserNameInput.getText(), signUpPassWordInput.getText(),comboBox.getValue());
                signUpUserNameInput.clear();
                signUpPassWordInput.clear();
                comboBox.setValue("");

            }


        });
        signUpButton.setStyle("-fx-background-color:#FFFF00");
        HBox box4 = new HBox();
        Label label2 = new Label("Already a member?");
        label2.setFont(new Font(" Sans Serif",13));
        Hyperlink buttonLogin = new Hyperlink("login");
        buttonLogin.setOnAction( e -> startWindow());
        box4.setSpacing(10);
        box4.getChildren().addAll(label2,buttonLogin);
        VBox layout = new VBox();
        layout.getChildren().addAll(box1,box2,box3,signUpButton,box4);
        layout.setAlignment(Pos.CENTER);
        layout.setSpacing(20);
        Scene scene = new Scene(layout,300,300);
        window.setScene(scene);
        window.show();


    }
    // sign up a new user into my database
    public void signUpUser(String userName , String password , String favPet){
        ResultSet resultSet = null;
        PreparedStatement checkUserExists = null;
        PreparedStatement insert = null;
        Connection connection = null;
        try {
             connection = DriverManager.getConnection(url,username,mypassword);
             checkUserExists = connection.prepareStatement("SELECT * FROM users WHERE userName =?");
            checkUserExists.setString(1,userName);
             resultSet = checkUserExists.executeQuery();
            if(resultSet.isBeforeFirst()){// is before first will check if the resultset already exist in the database
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("user name alert");
                alert.setHeaderText(null);
                alert.setContentText("username already exists");
                alert.show();

            }else{
                insert = connection.prepareStatement("INSERT INTO users (userName,passWord,favPet) VALUES (?,?,?) ");
                insert.setString(1,userName);
                insert.setString(2,password);
                insert.setString(3,favPet);
                insert.executeUpdate();
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("confirmation");
                alert.setHeaderText(null);
                alert.setContentText("user successfully added!!");
                alert.show();

            }
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            if(resultSet != null){
                try{
                    resultSet.close();
                }catch (SQLException e){
                    e.printStackTrace();
                }
            }
            if(checkUserExists != null){
                try{
                    checkUserExists.close();
                }catch (SQLException e){
                    e.printStackTrace();
                }
            }
            if(insert != null){
                try{
                    insert.close();
                }catch (SQLException e){
                    e.printStackTrace();
                }
            }
            if(connection != null){
                try{
                    connection.close();
                }catch (SQLException e){
                    e.printStackTrace();
                }
            }

        }
    }
    // ***************************************************
    // end of login and sign up windows

    // firt window
    public void firstWindow(String name){
        window.close();// close the login window
        // top box
        // my label is sitting in the top box beside the topRight box which will hold the button and the cart image
        HBox topRightBox = new HBox();
        HBox topBox = new HBox();
        topBox.setPrefHeight(50);
        // one hbox to hold 2 hboxes
        topBox.setStyle("-fx-background-color:black;");
        Label label = new Label("welcome "+name);
        label.setAlignment(Pos.CENTER_LEFT);
        label.setPadding(new Insets(24,0,24,10));
        label.setFont(new Font("Sans Serif",19));
      label.setStyle("-fx-font-style: italic;");
      label.setTextFill(Color.rgb(167,23,26));
      // make the label fade
        FadeTransition fade = new FadeTransition();
        fade.setNode(label);
        fade.setDuration(Duration.millis(4000));
        fade.setCycleCount(TranslateTransition.INDEFINITE);
        fade.setInterpolator(Interpolator.LINEAR);
        fade.setFromValue(1);// original opacity between 0 and 1
        fade.setToValue(0);
        fade.play();

        ImageView imageView = null;// the cart image
        try {
            FileInputStream inputStream = new FileInputStream("src/main/java/Images/cart.png");
          Image image = new Image(inputStream);
          imageView = new ImageView(image);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        Button myCartButton = new Button("my cart");
        HBox.setHgrow(topRightBox, Priority.ALWAYS);//grow with stretching
        topRightBox.getChildren().addAll(imageView,myCartButton);
        topRightBox.setAlignment(Pos.CENTER_RIGHT);
        topBox.getChildren().addAll(label,topRightBox);
        // end of top box
        // now the tree view on the left side
        //***********************************
        TreeView<String> treeView ;
        TreeItem<String> root = new TreeItem<>();
        root.setExpanded(true); // this is the root of the tree ie: it doesn't have to be closed

        TreeItem<String> Branches = new TreeItem<>("Branches");
        TreeItem<String> aboutUs = new TreeItem<>("about us");
        TreeItem<String> help = new TreeItem<>("help");
        TreeItem<String> Info = new TreeItem<>("more info");
        // those are the main branches under root

        root.getChildren().addAll(Branches,aboutUs,help,Info);

        // now the sub branches

        // children of branches tree item
        TreeItem<String> Dora = new TreeItem<>("Dora");
        TreeItem<String> Beirut = new TreeItem<>("Beirut");
        TreeItem<String> Junieh = new TreeItem<>("Junieh");

        Branches.getChildren().addAll(Dora,Beirut,Junieh);
       // children of each branch
        TreeItem<String> DoraPhone = new TreeItem<>("76565434");
        TreeItem<String> BeirutPhone = new TreeItem<>("70708763");
        TreeItem<String> JuniehPhone = new TreeItem<>("79796546");

        Dora.getChildren().add(DoraPhone);
        Beirut.getChildren().add(BeirutPhone);
        Junieh.getChildren().add(JuniehPhone);

        // children of about us
        TreeItem<String> LearnMore = new TreeItem<>("Learn more");
        aboutUs.getChildren().add(LearnMore);


       treeView = new TreeView<>(root);
       treeView.setShowRoot(false);// the root will be hiden its not a usefull node
       treeView.setMaxWidth(150);










        BorderPane layout = new BorderPane();
        layout.setTop(topBox);
        layout.setLeft(treeView);

        Rectangle2D screenBounds = Screen.getPrimary().getBounds();
        Double height = screenBounds.getHeight();
        Double width = screenBounds.getWidth();
        Scene scene = new Scene(layout,width,height-80);
       // get the width and hight of the screen then set the window to open at those values
         window.setScene(scene);
        window.show();

    }

    public static void main(String[] args) {

        launch();
    }
}





// learn anchor pane

//img.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
//
//@Override
//public void handle(MouseEvent event) {
//        System.out.println("Tile pressed ");
//        event.consume();
//        }
//        });
