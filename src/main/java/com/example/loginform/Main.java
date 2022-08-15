package com.example.loginform;

import javafx.animation.FadeTransition;
import javafx.animation.Interpolator;
import javafx.animation.TranslateTransition;
import javafx.application.Application;
import javafx.beans.binding.Bindings;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.ComboBoxListCell;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.effect.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.paint.CycleMethod;
import javafx.scene.paint.LinearGradient;
import javafx.scene.paint.Stop;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.util.Callback;
import javafx.util.Duration;
import javafx.util.StringConverter;
import javafx.util.converter.IntegerStringConverter;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.sql.*;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

import java.util.Date;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Main extends Application {
   private static Stage window;
   private static TextField startWindowUserNameInput;
   private static TextField signUpPassWordInput;
   private static  TextField startWindowPassInput;
   private Timer timer;
   private TimerTask task;
   private static String fromDate;
   private static String toDate;



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
        Label welcomeLabel = new Label("Welcome");
        usernameLabel.setFont(new Font(" Sans Serif",16));
        passwordLabel.setFont(new Font(" Sans Serif",16));
        startWindowUserNameInput = new TextField();
        startWindowUserNameInput.setPromptText("Username");
         startWindowPassInput = new TextField();
        startWindowPassInput.setPromptText("Password  ");

        HBox box0 = new HBox();
        box0.getChildren().add(welcomeLabel);
        HBox box1 = new HBox();
        box1.getChildren().addAll(usernameLabel,startWindowUserNameInput);
        HBox box2 = new HBox();
        box2.getChildren().addAll(passwordLabel,startWindowPassInput);
        box1.setSpacing(17);
        box2.setSpacing(20);
        Button loginButton = new Button("Login");

//        startWindowUserNameInput.setBackground(new Background(new BackgroundFill(Color.BLACK,null,null)));
     startWindowUserNameInput.setStyle("-fx-background-color:#d9d9d9;\n"+
             "-fx-text-fill:rgb(18, 16, 14);");
     startWindowPassInput.setStyle("-fx-background-color:#d9d9d9;\n"+
             "-fx-text-fill:rgb(18, 16, 14);");

        usernameLabel.setStyle("  -fx-font-size: 16px;\n" +
                "    -fx-font-weight: bold;\n" +
                "    -fx-text-fill: #333333;\n" +
                "    -fx-effect: dropshadow( gaussian , rgba(255,255,255,0.5) , 0,0,0,1 );");
        passwordLabel.setStyle("  -fx-font-size: 16px;\n" +
                "    -fx-font-weight: bold;\n" +
                "    -fx-text-fill: #333333;\n" +
                "    -fx-effect: dropshadow( gaussian , rgba(255,255,255,0.5) , 0,0,0,1 );");

        welcomeLabel.setStyle("  -fx-font-size: 32px;\n" +
                "   -fx-font-family: \"Arial Black\";\n" +
                "   -fx-fill: #818181;\n" +
                "   -fx-effect: innershadow( three-pass-box , rgba(0,0,0,0.7) , 6, 0.0 , 0 , 2 );");
        loginButton.styleProperty().bind(Bindings.when(loginButton.hoverProperty())
                        .then("    -fx-background-color:\n" +
                                "            linear-gradient(#e68400, #ffd65b),\n" +
                                "            linear-gradient(#f2ba44, #ffef84),\n" +
                                "            linear-gradient(#efaa22, #ffea6a),\n" +
                                "            linear-gradient(#ffe657 0%, #f8c202 50%, #eea10b 100%),\n" +
                                "            linear-gradient(from 0% 0% to 15% 50%, rgba(255,255,255,0.9), rgba(255,255,255,0));\n" +
                                "    -fx-background-insets: 0,1,2,3,0;\n" +
                                "    -fx-text-fill: #654b00;\n" +
                                "    -fx-font-weight: bold;\n" +
                                "    -fx-font-size: 14px;\n" +
                                "    -fx-padding: 10 20 10 20;\n")
                                .otherwise("-fx-background-color:\n" +
                                        "            linear-gradient(#ffd65b, #e68400),\n" +
                                        "            linear-gradient(#ffef84, #f2ba44),\n" +
                                        "            linear-gradient(#ffea6a, #efaa22),\n" +
                                        "            linear-gradient(#ffe657 0%, #f8c202 50%, #eea10b 100%),\n" +
                                        "            linear-gradient(from 0% 0% to 15% 50%, rgba(255,255,255,0.9), rgba(255,255,255,0));\n" +
                                        "    -fx-background-insets: 0,1,2,3,0;\n" +
                                        "    -fx-text-fill: #654b00;\n" +
                                        "    -fx-font-weight: bold;\n" +
                                        "    -fx-font-size: 14px;\n" +
                                        "    -fx-padding: 10 20 10 20;"));
        loginButton.setPadding(new Insets(5,20,5,20));
        HBox box3 = new HBox();
        box3.getChildren().add(loginButton);
        box3.setAlignment(Pos.CENTER);

        HBox box4 = new HBox();
        Label label = new Label("Not a user?");
//        label.setFont(new Font(" Sans Serif",13));
        label.setStyle("-fx-fill: FIREBRICK;\n" +
                "  -fx-font-weight: bold;\n" +
                "  -fx-effect: dropshadow( gaussian , rgba(255,255,255,0.5) , 0,0,0,1 )");
        Button button = new Button("Sign up");
        button.setStyle("-fx-border-color: transparent;\n" +
                "    -fx-border-width: 0;\n" +
                "    -fx-background-radius: 0;\n" +
                "    -fx-background-color: transparent;\n" +
                "    -fx-font-family:\"Segoe UI\", Helvetica, Arial, sans-serif;\n" +
                "    -fx-font-size: 1em; /* 12 */\n" +
                "    -fx-text-fill: #171411;");
        // you can achieve the same effect with a hiperlink it is like a button but without border or background
//        Hyperlink button = new Hyperlink("Some text");
//        button.setOnAction(e -> System.out.println("Hyperlink clicked"));
        button.setPadding(new Insets(1,10,10,10));
        loginButton.setOnAction(e -> loginButtonClicked(startWindowUserNameInput.getText()));
        //sign up button
        //this button will open a new window to take user info to sign up as a user
        button.setOnAction(e -> signUpButtonClicked());
        box4.setSpacing(10);
        box4.getChildren().addAll(label,button);

        VBox layout = new VBox(box0,box1,box2,box3,box4);
        layout.setAlignment(Pos.CENTER);


        layout.setSpacing(20);
        layout.setMaxSize(300,300);
        InputStream inputStream = null;
        try {
            inputStream = new FileInputStream("src/main/java/Images/loginbackground.jpg");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        Image image = new Image(inputStream);

        BackgroundSize bSize = new BackgroundSize(BackgroundSize.AUTO, BackgroundSize.AUTO, false, false, true, false);
        Background background = new Background(new BackgroundImage(image,
                BackgroundRepeat.NO_REPEAT,
                BackgroundRepeat.NO_REPEAT,
                BackgroundPosition.CENTER,
                bSize));
        layout.setBackground(background);
        Scene scene = new Scene(layout,300,300);

        window.setScene(scene);
        window.show();


    }
    // after you suxessfully login
    public void loginButtonClicked(String name){
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
                 DialogPane dialogPane = alert.getDialogPane();
                 dialogPane.getStylesheets().add(getClass().getResource("/Cart.css").toExternalForm());
                 dialogPane.setId("myDialog");
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
                     alert.setTitle("Alert!!");
                     alert.setHeaderText("Incorrect password");
                     alert.setContentText("incorrect password try again");
                     DialogPane dialogPane = alert.getDialogPane();
                     dialogPane.getStylesheets().add(getClass().getResource("/Cart.css").toExternalForm());
                     dialogPane.getStyleClass().add("dialogRed");
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
        String cssLabels = "  -fx-font-size: 16px;\n" +
                "    -fx-font-weight: bold;\n" +
                "    -fx-text-fill: #333333;\n" +
                "    -fx-effect: dropshadow( gaussian , rgba(255,255,255,0.5) , 0,0,0,1 );";
        Label usernameLabel = new Label("Username");
        usernameLabel.setStyle(cssLabels);
        Label passwordLabel = new Label("Password");
        passwordLabel.setStyle(cssLabels);
        Label favPetLabel = new Label("favorite pet");
        favPetLabel.setStyle(cssLabels);

        ComboBox<String> comboBox = new ComboBox<>();
        comboBox.getItems().addAll("dog","cat","bird","mouse","fish","hamster");
        comboBox.setEditable(true);

        comboBox.setStyle("-fx-max-width:150;");

        // since i cannot change the css to the nodes i have to create my own cell Value factory
        comboBox.setCellFactory(param -> new ComboBoxListCell<String>() {{
            setTextFill(Color.BLACK);

            Background creamBackground = new Background(new BackgroundFill(Color.rgb(183, 184, 180), null, null));
            Background BlueBackground = new Background(new BackgroundFill(Color.AQUAMARINE, null, null));

            setBackground(creamBackground);
            setOnMouseEntered(event -> {
                setBackground(BlueBackground);
            });
            setOnMouseExited(event -> {
                setBackground(creamBackground);
            });
        }});


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
        box1.setSpacing(22);
        box2.setSpacing(25);
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
        signUpButton.styleProperty().bind(Bindings.when(signUpButton.hoverProperty())
                .then("-fx-background-color: \n" +
                        "        linear-gradient(#ffe187, #fc8c03),\n" +
                        "        linear-gradient(#e0d58b, #cc9521),\n" +
                        "        linear-gradient(#e3d478, #c98b10),\n" +
                        "        linear-gradient(#f7e26a 0%, #f7c40c 50%,#ffc803 100%),\n" +
                        "        linear-gradient(from 0% 0% to 15% 50%, rgba(255,255,255,0.9), rgba(255,255,255,0));\n" +
                        "    -fx-background-radius: 30;\n" +
                        "    -fx-background-insets: 0,1,2,3,0;\n" +
                        "    -fx-text-fill: #654b00;\n" +
                        "    -fx-font-weight: bold;\n" +
                        "    -fx-font-size: 14px;\n" +
                        "    -fx-padding: 10 20 10 20;")
                .otherwise("-fx-background-color: \n" +
                        "        linear-gradient(#ffd65b, #e68400),\n" +
                        "        linear-gradient(#ffef84, #f2ba44),\n" +
                        "        linear-gradient(#ffea6a, #efaa22),\n" +
                        "        linear-gradient(#ffe657 0%, #f8c202 50%, #eea10b 100%),\n" +
                        "        linear-gradient(from 0% 0% to 15% 50%, rgba(255,255,255,0.9), rgba(255,255,255,0));\n" +
                        "    -fx-background-radius: 30;\n" +
                        "    -fx-background-insets: 0,1,2,3,0;\n" +
                        "    -fx-text-fill: #654b00;\n" +
                        "    -fx-font-weight: bold;\n" +
                        "    -fx-font-size: 14px;\n" +
                        "    -fx-padding: 10 20 10 20;"));
        HBox box4 = new HBox();
        Label label2 = new Label("Already a member?");
        label2.setStyle("-fx-fill: FIREBRICK;\n" +
                "  -fx-font-weight: bold;\n" +
                "  -fx-effect: dropshadow( gaussian , rgba(255,255,255,0.5) , 0,0,0,1 )");
//     label2.setId("lab2");
        Hyperlink buttonLogin = new Hyperlink("login");
        buttonLogin.setTextFill(Color.BLACK);
        buttonLogin.setOnAction( e -> startWindow());
        box4.setSpacing(10);
        box4.getChildren().addAll(label2,buttonLogin);
        VBox layout = new VBox();
        layout.getChildren().addAll(box1,box2,box3,signUpButton,box4);
        layout.setAlignment(Pos.CENTER);
        layout.setSpacing(20);
        InputStream inputStream = null;
        try {
            inputStream = new FileInputStream("src/main/java/Images/loginbackground.jpg");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        Image image = new Image(inputStream);

        BackgroundSize bSize = new BackgroundSize(BackgroundSize.AUTO, BackgroundSize.AUTO, false, false, true, false);
        Background background = new Background(new BackgroundImage(image,
                BackgroundRepeat.NO_REPEAT,
                BackgroundRepeat.NO_REPEAT,
                BackgroundPosition.CENTER,
                bSize));
        layout.setBackground(background);

        Scene scene = new Scene(layout,300,300);
//        scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
//        String css = this.getClass().getResource("src/main/java/com/example/loginform/application.css").toExternalForm();
//      scene.getStylesheets().add(css);
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
                DialogPane dialogPane = alert.getDialogPane();
                dialogPane.getStylesheets().add(getClass().getResource("/Cart.css").toExternalForm());
                dialogPane.getStyleClass().add("dialogRed");

                alert.show();

            }else{
                insert = connection.prepareStatement("INSERT INTO users (userName,passWord,favPet) VALUES (?,?,?) ");
                insert.setString(1,userName);
                insert.setString(2,password);
                insert.setString(3,favPet);
                insert.executeUpdate();
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("confirmation");
                alert.setHeaderText("Welcome!");
                alert.setContentText("user successfully added!!");
                DialogPane dialogPane = alert.getDialogPane();
                dialogPane.getStylesheets().add(getClass().getResource("/Cart.css").toExternalForm());
                dialogPane.getStyleClass().add("dialogBlue");
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

    // fisrt window
    public void firstWindow(String name){
        User user = new User(name);// create a object of user , to use it to set the out of stock of each user to the proper value
        // firt when this window open i will set a timer to search the databse every given time to find if the item is out of stock
        timer = new Timer();
       task = new TimerTask() {
           @Override
           public void run() {
               checkDatabaseStock(name,user);
           }
       };timer.schedule(task,0,10000);
        BorderPane layout = new BorderPane();

        window.close();// close the login window
        // top box
        // my label is sitting in the top box beside the topRight box which will hold the button and the cart image
        HBox topRightBox = new HBox();
        HBox topBox = new HBox();
        topBox.setPrefHeight(50);
        HBox topMidBox = new HBox();
        topMidBox.setAlignment(Pos.CENTER);
        Label labelApp = new Label("TonyTech");

       labelApp.setFont(Font.font("Brush Script MT",40));
        Stop[] stops = new Stop[] { new Stop(0, Color.ORANGE), new Stop(1, Color.RED)};
        LinearGradient lg1 = new LinearGradient(0, 0, 1, 0, true, CycleMethod.NO_CYCLE, stops);
       labelApp.setTextFill(lg1);

        DropShadow dropShadow = new DropShadow();
        dropShadow.setRadius(5.0);
        dropShadow.setOffsetX(3.0);
        dropShadow.setOffsetY(5.0);
        dropShadow.setColor(Color.rgb(255, 95, 3));
        labelApp.setEffect(dropShadow);
        topMidBox.getChildren().add(labelApp);

        HBox topleftBox = new HBox();
        // one hbox to hold 2 hboxes
        topBox.setStyle(" -fx-background-color: linear-gradient(from 25% 25% to 100% 100%, #040303, #30030a);");
        Label label = new Label("welcome "+name);
        label.setAlignment(Pos.CENTER_LEFT);
        label.setPadding(new Insets(24,0,24,10));
        label.setFont(Font.font("Stencil",23));
      label.setTextFill(Color.rgb(212,15,18));
      // make the label fade
        FadeTransition fade = new FadeTransition();
        fade.setNode(label);
        fade.setDuration(Duration.millis(3800));
        fade.setCycleCount(TranslateTransition.INDEFINITE);
        fade.setInterpolator(Interpolator.LINEAR);
        fade.setFromValue(1);// original opacity between 0 and 1
        fade.setToValue(0.3);
        fade.play();
     topleftBox.getChildren().add(label);
        ImageView cartImageView = null;// the cart image
        try {
            FileInputStream inputStream = new FileInputStream("src/main/java/Images/cart.png");
          Image image = new Image(inputStream);
          cartImageView = new ImageView(image);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        cartImageView.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if(Objects.equals(name, "tony")){
                     cartImageClicked();// this will onlu be activated if the user tony entered otherwise the cart image wont do anything when clicked
                }
                event.consume();
            }
        });



        Button myCartButton = new Button("my cart");
        myCartButton.setOnAction(e -> mycartButtonClicked(name,user));
        HBox.setHgrow(topRightBox, Priority.ALWAYS);//grow with stretching
        topRightBox.getChildren().addAll(cartImageView,myCartButton);
        topRightBox.setAlignment(Pos.CENTER_RIGHT);
        topMidBox.setMinWidth(540);
        topMidBox.setAlignment(Pos.CENTER_RIGHT);
        topBox.getChildren().addAll(topleftBox,topMidBox,topRightBox);

        myCartButton.styleProperty().bind(Bindings.when(myCartButton.hoverProperty())
                .then("-fx-background-color: linear-gradient(#b8283d, #bd112a);\n" +
                        "    -fx-background-radius: 30;\n" +
                        "    -fx-background-insets: 0;\n" +
                        "    -fx-text-fill: white;")
                .otherwise("-fx-background-color: linear-gradient(#800315, #700f1d);\n" +
                        "    -fx-background-radius: 30;\n" +
                        "    -fx-background-insets: 0;\n" +
                        "    -fx-text-fill: white;"));
        // end of top box  #800315, #700f1d
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

//       TreeCell<String> treeCell = new TreeCell<>();
//       treeCell.updateTreeView(treeView);
//


// now the middle box
        //*******************************************************8
        GridPane gridPane= new GridPane();
        gridPane.setStyle("-fx-background-color:#E2DFD2;");


        // first laptop
        InputStream inputStream1 = null;
        try {
            inputStream1 = new FileInputStream("src/main/java/Images/laptop1.PNG");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        Image lap1 = new Image(inputStream1);
        ImageView lap1View = new ImageView(lap1);
        lap1View.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
              imageClicked("laptop","Hp envy",2300,101,name);
                event.consume();
            }
        });


        // second laptop

        InputStream inputStream2 = null;
        try {
            inputStream2 = new FileInputStream("src/main/java/Images/laptop2.PNG");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        Image lap2 = new Image(inputStream2);
        ImageView lap2View = new ImageView(lap2);
        lap2View.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                imageClicked("laptop","lenovo flex",2198.9,102,name);
                event.consume();
            }
        });

        // laptop 3
        InputStream inputStream3 = null;
        try {
            inputStream3 = new FileInputStream("src/main/java/Images/laptop3.PNG");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        Image lap3 = new Image(inputStream3);
        ImageView lap3View = new ImageView(lap3);
        lap3View.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                imageClicked("laptop","LenovoPad",3200.99,103,name);
                event.consume();
            }
        });
        // laptop4

        InputStream inputStream4 = null;
        try {
            inputStream4 = new FileInputStream("src/main/java/Images/laptop4.PNG");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        Image lap4 = new Image(inputStream4);
        ImageView lap4View = new ImageView(lap4);
        lap4View.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                imageClicked("laptop","Lenovo ThinkPad",3100,104,name);
                event.consume();
            }
        });

        // Laptops labels
        Label lap1Label = new Label("HP Envy X360 \n I7-10510U 16GB 512GB SSD \n 32GB Optane MX250 4GB");
        Label lap2Label = new Label("laptop2 Lenovo Flex 5\n 82HU00JPUS Ryzen 7 \n5700U 16GB 512GB SSD");
        Label lap3Label =new Label("LenovoPad X1 Yoga\n G7 I7-1260P 16GB \n512GB SSD");
      Label lap4Label = new Label("Lenovo ThinkPad T14 \n20W000RWAD I5-1135G7 \n8GB 512GB SSD");
       lap1Label.setAlignment(Pos.CENTER);
       lap2Label.setAlignment(Pos.CENTER);
        lap3Label.setAlignment(Pos.CENTER);
        lap4Label.setAlignment(Pos.CENTER);
       Button lap1Button = new Button("buy now");
       lap1Button.setOnAction( e -> {
           buyItem(101,name);
       });
       Button lap2Button = new Button("buy now");
        lap2Button.setOnAction( e -> {
            buyItem(102,name);
        });
        Button lap3Button = new Button("buy now");
        lap3Button.setOnAction( e -> {
            buyItem(103,name);
        });
        Button lap4Button = new Button("buy now");
        lap4Button.setOnAction( e -> {
            buyItem(104,name);
        });


        gridPane.addColumn(1,lap2View);
        gridPane.addRow(1,lap1Label,lap2Label);
        gridPane.addRow(2,lap1Button,lap2Button);
        gridPane.addRow(3,lap3View,lap4View);
        gridPane.addRow(4,lap3Label,lap4Label);
        gridPane.addRow(5,lap3Button,lap4Button);

        GridPane.setHalignment(lap1Label, HPos.CENTER);
        GridPane.setHalignment(lap2Label, HPos.CENTER);
        GridPane.setHalignment(lap3Label, HPos.CENTER);
        GridPane.setHalignment(lap4Label, HPos.CENTER);
        GridPane.setHalignment(lap1Button, HPos.CENTER);
        GridPane.setHalignment(lap2Button, HPos.CENTER);
        GridPane.setHalignment(lap3Button, HPos.CENTER);
        GridPane.setHalignment(lap4Button, HPos.CENTER);

        ArrayList<Button> lapButtons = new ArrayList<>();
        lapButtons.add(lap1Button);
        lapButtons.add(lap2Button);
        lapButtons.add(lap3Button);
        lapButtons.add(lap4Button);
        for(Button b : lapButtons){
            b.styleProperty().bind(Bindings.when(b.hoverProperty())
                    .then("-fx-background-color: \n" +
                            "        linear-gradient(#f2f2f2, #d6d6d6),\n" +
                            "        linear-gradient(#fcfcfc 0%, #d9d9d9 20%, #d6d6d6 100%),\n" +
                            "        linear-gradient(#dddddd 0%, #f6f6f6 50%);\n" +
                            "    -fx-background-radius: 8,7,6;\n" +
                            "    -fx-background-insets: 0,1,2;\n" +
                            "    -fx-text-fill: black;\n" +
                            "    -fx-effect: dropshadow( three-pass-box , rgba(0,0,0,0.6) , 5, 0.0 , 0 , 1 );")
                    .otherwise(
                            " -fx-background-color: \n" +
                                    "        #c3c4c4,\n" +
                                    "        linear-gradient(#d6d6d6 50%, white 100%),\n" +
                                    "        radial-gradient(center 50% -40%, radius 200%, #e6e6e6 45%, rgba(230,230,230,0) 50%);\n" +
                                    "    -fx-background-radius: 30;\n" +
                                    "    -fx-background-insets: 0,1,1;\n" +
                                    "    -fx-text-fill: black;\n" +
                                    "    -fx-effect: dropshadow( three-pass-box , rgba(0,0,0,0.6) , 3, 0.0 , 0 , 1 );"));
        }
         //#9e9898  #424040   #2b2828




        gridPane.setPadding(new Insets(10,20,20,95));

        gridPane.add(lap1View,0,0);
        gridPane.setHgap(200);
        gridPane.setVgap(15);



        // second pane ipads



        GridPane gridPane2= new GridPane();
        gridPane2.setStyle("-fx-background-color:#E2DFD2;");


        // first ipad
        InputStream inputStream5 = null;
        try {
            inputStream5 = new FileInputStream("src/main/java/Images/ipad1.PNG");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        Image ipad1 = new Image(inputStream5);
        ImageView ipad1View = new ImageView(ipad1);
        ipad1View.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                imageClicked("ipad","Magic keyboard",1000,105,name);
                event.consume();
            }
        });

                // second ipad

        InputStream inputStream6 = null;
        try {
            inputStream6 = new FileInputStream("src/main/java/Images/ipad2.PNG");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        Image ipad2 = new Image(inputStream6);
        ImageView ipad2View = new ImageView(ipad2);
        ipad2View.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                imageClicked("ipad","Smart keyboard",1110,106,name);
                event.consume();
            }
        });

        // ipad 3
        InputStream inputStream7 = null;
        try {
            inputStream7 = new FileInputStream("src/main/java/Images/ipad3.PNG");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        Image ipad3 = new Image(inputStream7);
        ImageView ipad3View = new ImageView(ipad3);
        ipad3View.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                imageClicked("ipad","Space graySilver",3276.87,107,name);
                event.consume();
            }
        });
        // ipad 4

        InputStream inputStream8 = null;
        try {
            inputStream8 = new FileInputStream("src/main/java/Images/ipad4.PNG");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        Image ipad4 = new Image(inputStream8);
        ImageView ipad4View = new ImageView(ipad4);
        ipad4View.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                imageClicked("ipad","ipad Air",3108,108,name);
                event.consume();
            }
        });

        // Laptops labels
        Label ipad1Label = new Label("Magic Keyboard for iPad\n Pro 11-inch \n(3rd generation)");
        Label ipad2Label = new Label("Smart Keyboard Folio\n for iPad Pro 11-inch \n(3rd generation) ");
        Label ipad3Label =new Label("Space GraySilver\n iPad Pro 11-inch\nipad 4");
        Label ipad4Label = new Label("iPad Air\nFrom $599or $49.91/mo.per month \nfor 12 mo.monthsFootnote*");
        ipad1Label.setAlignment(Pos.CENTER);
        ipad2Label.setAlignment(Pos.CENTER);
        ipad3Label.setAlignment(Pos.CENTER);
        ipad4Label.setAlignment(Pos.CENTER);
        Button ipad1Button = new Button("buy now");
        ipad1Button.setOnAction( e -> {
            buyItem(105,name);
        });
        Button ipad2Button = new Button("buy now");
        ipad2Button.setOnAction( e -> {
            buyItem(106,name);
        });
        Button ipad3Button = new Button("buy now");
        ipad3Button.setOnAction( e -> {
            buyItem(107,name);
        });
        Button ipad4Button = new Button("buy now");
        ipad4Button.setOnAction( e -> {
            buyItem(108,name);
        });


        gridPane2.addColumn(1,ipad2View);
        gridPane2.addRow(1,ipad1Label,ipad2Label);
        gridPane2.addRow(2,ipad1Button,ipad2Button);
        gridPane2.addRow(3,ipad3View,ipad4View);
        gridPane2.addRow(4,ipad3Label,ipad4Label);
        gridPane2.addRow(5,ipad3Button,ipad4Button);

        GridPane.setHalignment(ipad1Label, HPos.CENTER);
        GridPane.setHalignment(ipad2Label, HPos.CENTER);
        GridPane.setHalignment(ipad3Label, HPos.CENTER);
        GridPane.setHalignment(ipad4Label, HPos.CENTER);
        GridPane.setHalignment(ipad1Button, HPos.CENTER);
        GridPane.setHalignment(ipad2Button, HPos.CENTER);
        GridPane.setHalignment(ipad3Button, HPos.CENTER);
        GridPane.setHalignment(ipad4Button, HPos.CENTER);

        ArrayList<Button> ipadButtons = new ArrayList<>();
        ipadButtons.add(ipad1Button);
        ipadButtons.add(ipad2Button);
        ipadButtons.add(ipad3Button);
        ipadButtons.add(ipad4Button);
        for(Button b : ipadButtons){
            b.styleProperty().bind(Bindings.when(b.hoverProperty())
                    .then("-fx-background-color: \n" +
                            "        linear-gradient(#f2f2f2, #d6d6d6),\n" +
                            "        linear-gradient(#fcfcfc 0%, #d9d9d9 20%, #d6d6d6 100%),\n" +
                            "        linear-gradient(#dddddd 0%, #f6f6f6 50%);\n" +
                            "    -fx-background-radius: 8,7,6;\n" +
                            "    -fx-background-insets: 0,1,2;\n" +
                            "    -fx-text-fill: black;\n" +
                            "    -fx-effect: dropshadow( three-pass-box , rgba(0,0,0,0.6) , 5, 0.0 , 0 , 1 );")
                    .otherwise(
                            " -fx-background-color: \n" +
                                    "        #c3c4c4,\n" +
                                    "        linear-gradient(#d6d6d6 50%, white 100%),\n" +
                                    "        radial-gradient(center 50% -40%, radius 200%, #e6e6e6 45%, rgba(230,230,230,0) 50%);\n" +
                                    "    -fx-background-radius: 30;\n" +
                                    "    -fx-background-insets: 0,1,1;\n" +
                                    "    -fx-text-fill: black;\n" +
                                    "    -fx-effect: dropshadow( three-pass-box , rgba(0,0,0,0.6) , 3, 0.0 , 0 , 1 );"));
        }




        gridPane2.setPadding(new Insets(10,20,20,95));

        gridPane2.add(ipad1View,0,0);
        gridPane2.setHgap(200);
        gridPane2.setVgap(15);



        // now the bottom
        //****************************************************
        HBox buttomBox = new HBox();
        buttomBox.setPrefHeight(50);
        buttomBox.setStyle(" -fx-background-color: #240404;");
       Button downButton1 = new Button("laptops");
       Button downButton2 = new Button("ipads");
        buttomBox.getChildren().addAll(downButton1,downButton2);
        // change the grid with action of button to change from laptops to ipad and vice versa

        downButton2.setOnAction(e -> {
            layout.setCenter(gridPane2);
        });
        downButton1.setOnAction(e -> {
            layout.setCenter(gridPane);
        });
        buttomBox.setAlignment(Pos.CENTER);
        buttomBox.setSpacing(30);
        ArrayList<Button> downButtons = new ArrayList<>();
        downButtons.add(downButton1);
        downButtons.add(downButton2);
        // i am moving the buttons slightly to the left by adding margin
      HBox.setMargin(downButton2,new Insets(0,90,0,0));
        for(Button b : downButtons) {
            b.styleProperty().bind(Bindings.when(b.hoverProperty())
                    .then(" -fx-background-color: \n" +
                            "        linear-gradient(from 0% 93% to 0% 100%, #a34313 0%, #903b12 100%),\n" +
                            "        #9d4024,\n" +
                            "        #d86e3a,\n" +
                            "        radial-gradient(center 50% 50%, radius 100%, #ea7f4b, #c54e2c);")
                    .otherwise("-fx-padding: 8 15 15 15;\n" +
                            "    -fx-background-insets: 0,0 0 5 0, 0 0 6 0, 0 0 7 0;\n" +
                            "    -fx-background-radius: 8;\n" +
                            "    -fx-background-color: \n" +
                            "        linear-gradient(from 0% 93% to 0% 100%, #a34313 0%, #903b12 100%),\n" +
                            "        #9d4024,\n" +
                            "        #d86e3a,\n" +
                            "        radial-gradient(center 50% 50%, radius 100%, #d86e3a, #c54e2c);\n" +
                            "    -fx-effect: dropshadow( gaussian , rgba(0,0,0,0.75) , 4,0,0,1 );\n" +
                            "    -fx-font-weight: bold;\n" +
                            "    -fx-font-size: 1.1em;"));
        }

//                Style style1 = new Style(butm ,"jj" );
        // now the right box
        VBox rightBox = new VBox();
        rightBox.setAlignment(Pos.CENTER);
        rightBox.setStyle("-fx-background-color: #e6e6e6;");
        InputStream saleInputStream = null;
        try {
           saleInputStream = new FileInputStream("src/main/java/Images/sale.jpg");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        Image saleImage = new Image(saleInputStream);
        ImageView saleImageView = new ImageView(saleImage);
      // second image on the right menu
        InputStream sale2InputStream = null;
        try {
            sale2InputStream = new FileInputStream("src/main/java/Images/sale2.png");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        Image sale2Image = new Image(sale2InputStream);
        ImageView sale2ImageView = new ImageView(sale2Image);

        InputStream contactUsInputStream = null;
        try {
            contactUsInputStream = new FileInputStream("src/main/java/Images/contactUs.jpg");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        Image image = new Image(contactUsInputStream);
        ImageView contactUsImageView = new ImageView(image);
        contactUsImageView.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
               contactUsWindow(name);
                event.consume();
            }
        });




        // translation and fade for first image
        TranslateTransition translate = new TranslateTransition();
        translate.setNode(saleImageView);
        translate.setDuration(Duration.millis(4000));// so i set the duration of the transition to be 3000ms ie 3 sec
//      translate.setCycleCount(2);// do the translation 2 times
        translate.setCycleCount(TranslateTransition.INDEFINITE);// this will be now continues
        translate.setAutoReverse(true);// when the transition end it will go back and forth now ie(not reset from the starting point)
//        translate.setByY(-100); // now it will move to the down and right together
        translate.setByX(55);// how far you want to move it on the x access
        translate.play();

        FadeTransition fade2 = new FadeTransition();
        fade2.setNode(saleImageView);
        fade2.setDuration(Duration.millis(4000));
        fade2.setCycleCount(TranslateTransition.INDEFINITE);
        fade2.setInterpolator(Interpolator.LINEAR);
        fade2.setFromValue(1);// original opacity between 0 and 1
        fade2.setToValue(0);
        fade2.play();




        rightBox.getChildren().addAll(saleImageView,sale2ImageView,contactUsImageView);
        rightBox.setSpacing(20);
        rightBox.setPrefWidth(240);
//


        layout.setTop(topBox);
        layout.setLeft(treeView);
        layout.setCenter(gridPane);
        layout.setBottom(buttomBox);
        layout.setRight(rightBox);

        Rectangle2D screenBounds = Screen.getPrimary().getBounds();
        Double height = screenBounds.getHeight();
        Double width = screenBounds.getWidth();
        Scene scene = new Scene(layout,width,height-80);
       // get the width and hight of the screen then set the window to open at those values

        window.setOnCloseRequest( e->{
            e.consume(); // stopps the close event when you hit x, it means we are taking it from here (using the close method)
            boolean answear = ConfirmBox.display("confirm","are you sure you want to exit buy window?");
            if(answear) {
                pauseTimer();
                window.close();
            }
        });
        window.getIcons().add(new Image("file:src/main/java/Images/darkImage.jpg"));
         window.setScene(scene);
        window.show();

    }

    // this function will be called from the buy buttons on the items
    public void buyItem(int id,String name){
        Stage buyWindow = new Stage();
        GridPane layout = new GridPane();
        Label nameLabel = new Label("Name");
        TextField nameInput = new TextField();
        nameInput.setPromptText("enter your name");
        Label cityLabel = new Label("city");
        TextField cityInput = new TextField();
        cityInput.setPromptText("enter your city");
        Label dateLabel = new Label();
        dateLabel.setText("Date");
        DatePicker datePicker = new DatePicker();
        datePicker.setPromptText("Enter your date");
        Label emailLabel = new Label("Email");
        TextField emailInput = new TextField();
        emailInput.setPromptText("example@hotmail.com");

        Label cartNumLabel = new Label("Cart num");
        TextField cartNumInput = new TextField();
        cartNumInput.setPromptText("Visa card number");
        ObservableList<Info> infoObservableList = getInfoFromDB(name);// i am storing the data comming from this method call as an observable list(data is comming from the database) and i will give this data to the fields
        if(!infoObservableList.isEmpty()){// if i have a list it means the user already saved his info so then i will fill those fields with the saved info
            nameInput.setText(infoObservableList.get(0).getName());
            emailInput.setText(infoObservableList.get(0).getEmail());
            cityInput.setText(infoObservableList.get(0).getCity());
            datePicker.getEditor().setText(infoObservableList.get(0).getDob());

        }
        // this will force the user to enter a number
        // you cannot even enter anything else
        // in this method i have an observable value which is like a arraylist
        // it extends string cz a am taking a user text input
        // i have old vallue (the field is emty) then the new value the one i entered
        // if the new value matches a decimal set the text to a decimal

        forceNumber(cartNumInput);


        Label pinCodeLabel = new Label("Pin code");
        PasswordField pinCodeInput = new PasswordField();
        pinCodeInput.setPromptText("enter pin code");

        forceNumber(pinCodeInput);


        Button button1 = new Button("buy");
        Button button2 = new Button("exit");
        Button button3 = new Button("Save info");
        button1.setId("bevel-grey");
        button2.setId("bevel-grey");
        button3.setId("bevel-grey");

        layout.addRow(0,nameLabel,nameInput);
        layout.addRow(1,cityLabel,cityInput);
        layout.addRow(2,dateLabel,datePicker);
        layout.addRow(3,emailLabel,emailInput);
        layout.addRow(4,cartNumLabel,cartNumInput);
        layout.addRow(5,pinCodeLabel,pinCodeInput);
        HBox buttons = new HBox();
        buttons.setAlignment(Pos.CENTER);
        buttons.setSpacing(10);
        buttons.getChildren().addAll(button1,button2,button3);
        layout.setHgap(20);
        layout.setVgap(30);
        layout.setAlignment(Pos.CENTER);
        VBox box = new VBox();
        box.setSpacing(20);
        box.setAlignment(Pos.CENTER);
        box.setMaxSize(300,400);
        box.getChildren().addAll(layout,buttons);
//        box.setBackground(new Background(new BackgroundFill(Color.RED,null,null)));
        InputStream inputStream = null;
        try {
            inputStream = new FileInputStream("src/main/java/Images/darkImage.jpg");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        Image image = new Image(inputStream);

        BackgroundSize bSize = new BackgroundSize(BackgroundSize.AUTO, BackgroundSize.AUTO, false, false, true, true);//double width, double height, boolean widthAsPercentage, boolean heightAsPercentage, boolean contain, boolean cover
        Background background = new Background(new BackgroundImage(image,
                BackgroundRepeat.NO_REPEAT,
                BackgroundRepeat.NO_REPEAT,
                BackgroundPosition.CENTER,
                bSize));
        box.setBackground(background);
        ArrayList<Label> labels = new ArrayList<>();
        labels.add(nameLabel);
        labels.add(cartNumLabel);
        labels.add(cityLabel);
        labels.add(dateLabel);
        labels.add(emailLabel);
        labels.add(pinCodeLabel);



        for(Label label : labels){
            label.setFont(Font.font("Copperplate Gothic Bold",15));
            label.setStyle("-fx-fill: red;\n" +
                    "  -fx-font-weight: bold;\n" +
                    "  -fx-effect: dropshadow( gaussian , rgb(255, 255, 255) , 0,0,0,1 )");

        }

      // i wanted to center my buttons as i like
        // if i put them in any place inside the grid i cannot center as i like
        // i used instead a vbox where i put the grid then under it an hbox containing both of the buttons
        // this way i will be more free to center as i like
        button1.setOnAction( e -> {
//
            ArrayList<TextField> fields = new ArrayList<>();
            fields.add(nameInput);
            fields.add(cityInput);
            fields.add(emailInput);
            fields.add(cartNumInput);
            fields.add(pinCodeInput);
            boolean empty = false;
            for(TextField t : fields){;
                if(Objects.equals(t.getText(), "")){
                   empty = true;
                   break;
                }
            }

            // continue here
//            for(TextField t : fields){
//            t.setFont(Font.font("Consolas", FontPosture.ITALIC,22));
//            }
            // i put all fields in an arraylist so that i can search if anyh of those is empty
            // than i add the date picker codition alone because its diffrent to access it than the normal fields
            // in this if i check in any of the fields is emty and i launch a alert
            if(empty || Objects.equals(datePicker.getEditor().getText(), "")){
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setContentText("empty field");
                alert.setHeaderText(null);
                alert.setContentText("you cannot have an empty field");
                DialogPane dialogPane = alert.getDialogPane();
                dialogPane.getStylesheets().add(getClass().getResource("/Cart.css").toExternalForm());
                dialogPane.setId("myDialog");

                alert.showAndWait();
            }else{
                if(!validEmail(emailInput.getText())){
                    System.out.println("false email");
                    emailInput.setText("");
                }else {
                    buyItemButtonClicked(id);// this will call the method that will change the number of items in my database based on this id(of what item is clicked)
                    nameInput.setText("");
                    cityInput.setText("");
                    datePicker.getEditor().setText("");
                    emailInput.setText("");
                    cartNumInput.setText("");
                    pinCodeInput.setText("");

                    buyWindow.close();
                }
            }


        });
        // end of action on button 1

        // actions for exit button
        button2.setOnAction(e -> {
            // call the static method in confirm box class where a window will
            // appear and this method will return a boolean value if its true we close the buy window
            boolean answear = ConfirmBox.display("confirm","are you sure you want to exit buy window?");
            if(answear)
                buyWindow.close();
        });
        button3.setOnAction( e -> {
            ArrayList<TextField> fields = new ArrayList<>();
            fields.add(nameInput);
            fields.add(cityInput);
            fields.add(emailInput);
            fields.add(cartNumInput);
            fields.add(pinCodeInput);
            boolean empty = false;
            for(TextField t : fields){
                if(Objects.equals(t.getText(), "")){
                    empty = true;
                    break;
                }
            }
            // i put all fields in an arraylist so that i can search if anyh of those is empty
            // than i add the date picker codition alone because its diffrent to access it than the normal fields
            // in this if i check in any of the fields is emty and i launch a alert
            if(empty || Objects.equals(datePicker.getEditor().getText(), "")){
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setContentText("empty field");
                alert.setHeaderText(null);
                alert.setContentText("you cannot have an empty field");
                DialogPane dialogPane = alert.getDialogPane();
                dialogPane.getStylesheets().add(getClass().getResource("/Cart.css").toExternalForm());
                dialogPane.setId("myDialog");
                alert.showAndWait();
            }else {
                if (!validEmail(emailInput.getText())) {
                    System.out.println("false email");
                    emailInput.setText("");
                } else {
                    boolean ans = ConfirmBox.display("Save", "do you want to save your info for future use?");
                    if (ans) {
                        saveInfoToDB(nameInput.getText(),cityInput.getText(),emailInput.getText(),datePicker.getEditor().getText(),name);// call of a method that saves the user info in the database (table info) reson: fo rfuture use instead of putting the info again every time
                    }
                }
            }
        });
        // this method will force the close by clicking x to ask also if you are sure you want to quit
        buyWindow.setOnCloseRequest(e -> {
                    e.consume(); // stopps the close event when you hit x, it means we are taking it from here (using the close method)
            boolean answear = ConfirmBox.display("confirm","are you sure you want to exit buy window?");
            if(answear)
                buyWindow.close();
                }
        );
        TextField[] texts = new TextField[]{nameInput,emailInput,cityInput,cartNumInput,pinCodeInput};

        Scene scene = new Scene(box,300,400);
        scene.getStylesheets().add(getClass().getResource("/Cart.css").toExternalForm());
       for(TextField f: texts){
           f.setId("glassField");
       }
//       datePicker.setId("glassPicker");
        buyWindow.setScene(scene);
        buyWindow.show();
    }
    // email verification
    // first you write a regex (a pattern)
    // then you create a pattern you add the regex to it them you
    // add this pattern to a  matcher
    public boolean validEmail(String input){
        String regex = "^[A-Za-z0-9+_.-]+@(.+)$";
        //Compile regular expression to get the pattern
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(input);
        if(matcher.find() && matcher.group().equals(input)){
            return true;
        }else{
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("valid email");
            alert.setHeaderText(null);
            alert.setContentText("please enter a valid email");
            DialogPane dialogPane = alert.getDialogPane();
            dialogPane.getStylesheets().add(getClass().getResource("/Cart.css").toExternalForm());
            dialogPane.setId("myDialog");
            alert.showAndWait();

        }
        return false;
    }
    // HERE I AM ACCESSING THE DATABASE and updating the quantity of an item when we click buy on it
    // from this method also i will be updating the database for the table (report) where i will be adding to the quantity of items baught for the specific item
     public  void  buyItemButtonClicked(int id){
        PreparedStatement insertToDateReport = null;
     PreparedStatement change = null;
     Connection connection = null;
     PreparedStatement checkAvailability = null;
     ResultSet availableSet = null;
     // first checkl if the item is in stock if yes then update
         try {
             connection = DriverManager.getConnection(url,username,mypassword);
           checkAvailability = connection.prepareStatement("SELECT quantity FROM items WHERE id = ?");
           checkAvailability.setInt(1,id);
           availableSet = checkAvailability.executeQuery();
             int ans = 0;
             if(availableSet.next()){
                 ans = availableSet.getInt(1);
             }


          if(ans > 0) {
              // first i have to update the items and change the quantity then i have to add to report where i add 1 to the quantity sold since this is a button where you only buy one item
              change = connection.prepareStatement("UPDATE items SET quantity = quantity -1 WHERE id = ?;");
              change.setString(1, String.valueOf(id));
              change.executeUpdate();


             // add to dateReport here
              java.sql.Date sqlDate = new java.sql.Date(new java.util.Date().getTime());
              insertToDateReport = connection.prepareStatement("INSERT INTO dateReport VALUES (?,?,?)");
             insertToDateReport.setInt(1,id);
             insertToDateReport.setDate(2, sqlDate);
            insertToDateReport.setInt(3,1);
            insertToDateReport.executeUpdate();
             
              Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
              alert.setHeaderText(null);
              alert.setTitle("Confirmation");
              alert.setHeaderText("Confirmation");
              alert.setContentText("Item successfully purchased thank you for shopping with TonyTech");
              DialogPane dialogPane = alert.getDialogPane();
              dialogPane.getStylesheets().add(getClass().getResource("/Cart.css").toExternalForm());
              dialogPane.getStyleClass().add("dialogBlue");
              alert.showAndWait();
          }else{
              Alert alert = new Alert(Alert.AlertType.WARNING);
              alert.setTitle("not available");
              alert.setHeaderText("sorry");
              alert.setContentText("item is not available in stock!!");
              DialogPane dialogPane = alert.getDialogPane();
              dialogPane.getStylesheets().add(getClass().getResource("/Cart.css").toExternalForm());
              dialogPane.setId("myDialog");
              alert.showAndWait();
          }
         } catch (SQLException e) {
             e.printStackTrace();
         }finally {
             if(change != null){
                 try {
                     change.close();
                 } catch (SQLException e) {
                     e.printStackTrace();
                 }
             }if(checkAvailability != null){
                 try {
                     checkAvailability.close();
                 } catch (SQLException e) {
                     e.printStackTrace();
                 }
             }if(availableSet != null){
                 try {
                     availableSet.close();
                 } catch (SQLException e) {
                     e.printStackTrace();
                 }

             }if(insertToDateReport != null){
                 try {
                     insertToDateReport.close();
                 } catch (SQLException e) {
                     e.printStackTrace();
                 }
             }
             if(connection != null){
                 try {
                     connection.close();
                 } catch (SQLException e) {
                     e.printStackTrace();
                 }
             }
         }
     }
     // here i am adding a listener to the images , if the image is clicked
    // you get a window asking for confirmation to add to cart
    // if the user pressed yes i will fill a table in database called cart
    // where i have the info of the item plus the name of the user who buy it
     // the first thing this method will do is check if the item is already in the users
    // cart if it is it will show a alert message then it will check if the item is
    // available and it will also show an alert if it is not avalable in stock
    // if the item is avalable i will insert the values in the datbase
    // this will happen for each image

    public void imageClicked(String type , String name , double price,int id,String userName){
         Connection connection = null;
         PreparedStatement isAvailable = null;
         PreparedStatement insert = null;
         ResultSet availableSet = null;
         PreparedStatement isItemExistInCart = null;
         ResultSet isItemExistSet = null;
         try {
             connection = DriverManager.getConnection(url, username, mypassword);
             isItemExistInCart = connection.prepareStatement("SELECT * FROM cart WHERE cartId = ? AND userName = ?");
            isItemExistInCart.setInt(1,id);
            isItemExistInCart.setString(2,userName);
            isItemExistSet = isItemExistInCart.executeQuery();
            if(isItemExistSet.isBeforeFirst()){
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("already in cart");
                alert.setHeaderText(null);
                alert.setContentText("item already in your cart");
                DialogPane dialogPane = alert.getDialogPane();
                dialogPane.getStylesheets().add(getClass().getResource("/Cart.css").toExternalForm());
                dialogPane.setId("myDialog");
                alert.showAndWait();
            }else {

                isAvailable = connection.prepareStatement("SELECT quantity FROM items WHERE id = ?");
                isAvailable.setInt(1, id);
                availableSet = isAvailable.executeQuery();
                int ans = 0;
                if (availableSet.next()) {
                    ans = availableSet.getInt(1);
                }
                if (ans > 0) {
                    boolean answerAddCart = ConfirmBox.display("add to cart", "do you want to add this item to cart?");
                    if (answerAddCart) {
                        insert = connection.prepareStatement("INSERT INTO cart VALUES (?,?,?,?,?,?)");
                        insert.setString(1, type);
                        insert.setString(2, name);
                        insert.setDouble(3, price);
                        insert.setInt(4, id);
                        insert.setString(5, userName);
                        insert.setInt(6,1);
                        insert.executeUpdate();
                        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                        alert.setHeaderText(null);
                        alert.setTitle("confirmation");
                        alert.setContentText("successfully added the item to your cart");
                        DialogPane dialogPane = alert.getDialogPane();
                        dialogPane.getStylesheets().add(getClass().getResource("/Cart.css").toExternalForm());
                        dialogPane.getStyleClass().add("dialogBlue");
                        alert.showAndWait();

                    }
                } else {
                    Alert alert = new Alert(Alert.AlertType.WARNING);
                    alert.setHeaderText(null);
                    alert.setTitle("not available");
                    alert.setContentText("item out of stock cannot add to cart!!");
                    DialogPane dialogPane = alert.getDialogPane();
                    dialogPane.getStylesheets().add(getClass().getResource("/Cart.css").toExternalForm());
                    dialogPane.setId("myDialog");
                    alert.showAndWait();
                }
            }
         }catch (SQLException e) {
               e.printStackTrace();
           }finally{
               if(insert != null){
                   try {
                       insert.close();
                   } catch (SQLException e) {
                       e.printStackTrace();
                   }
               }if(isAvailable != null){
                 try {
                     isAvailable.close();
                 } catch (SQLException e) {
                     e.printStackTrace();
                 }
             }if(isItemExistInCart != null){
                 try {
                     isItemExistInCart.close();
                 } catch (SQLException e) {
                     e.printStackTrace();
                 }
             }if(isItemExistSet != null){
                 try {
                     isItemExistSet.close();
                 } catch (SQLException e) {
                     e.printStackTrace();
                 }
             }
               if(availableSet != null){
                 try {
                     availableSet.close();
                 } catch (SQLException e) {
                     e.printStackTrace();
                 }
             }
               if(connection != null){
                   try {
                       connection.close();
                   } catch (SQLException e) {
                       e.printStackTrace();
                   }
               }
           }

       }
       // this method will check the databse if an item becomes 0 (out of stock )
    // a alert directly will be shown to user while the window is open that an item became out of stock
    // the item will be removed from the user cart (in the databse) in this method as well
      // this method will be called from a timer (created in the first window )
    // ps: remember to close the timer when the first window is closed -- for later when you add are you sure you want to close window
    public void checkDatabaseStock(String name ,User user){
           System.out.println("connection started");
           String outOfStockNames = "";
          Connection connection = null;
          PreparedStatement checkNames = null;
          ResultSet resultSet = null;
          PreparedStatement deleteFromCart = null;
           try {
               connection = DriverManager.getConnection(url,username,mypassword);
               checkNames = connection.prepareStatement("SELECT name FROM items WHERE id IN(SELECT cartId FROM cart WHERE userName = ?) AND quantity = 0;");
            checkNames.setString(1,name);
              resultSet = checkNames.executeQuery();
               outOfStockNames = "";
              while(resultSet.next()){
                  outOfStockNames += resultSet.getString(1)+" ";
              }
              if(!outOfStockNames.equals("")){
               deleteFromCart = connection.prepareStatement("DELETE FROM cart WHERE userName = ? AND cartId IN (SELECT id FROM items WHERE quantity = 0);");
              deleteFromCart.setString(1,name);
               deleteFromCart.executeUpdate();

              }

           } catch (SQLException e) {
               e.printStackTrace();
           }finally {
               if(checkNames != null){
                   try {
                       checkNames.close();
                   } catch (SQLException e) {
                       e.printStackTrace();
                   }
               }if(deleteFromCart != null){
                   try {
                       deleteFromCart.close();
                   } catch (SQLException e) {
                       e.printStackTrace();
                   }
               }if(resultSet != null){
                   try {
                       resultSet.close();
                   } catch (SQLException e) {
                       e.printStackTrace();
                   }
               }if(connection != null){
                   try {
                       connection.close();
                   } catch (SQLException e) {
                       e.printStackTrace();
                   }
               }
           }// i gave the string of names that are out of stock to a global static string to use it when i click on cart to chow what items are out of stock
          // if outOfStockNames inside user Object is not null then add to it new items that might became out of stock
           if(!outOfStockNames.equals("")){
               if(user.getNamesOutOfStock() != null)
               user.setNamesOutOfStock(user.getNamesOutOfStock()+ outOfStockNames);
               else
                   user.setNamesOutOfStock(outOfStockNames);// else just set the outof stock to the outofstock string taken from the database resultset
           }
       }

public void resumeTimer(String name,User user) {
    this.timer = new Timer();
    task = new TimerTask() {
        @Override
        public void run() {
            checkDatabaseStock(name,user);
        }
    };
    this.timer.schedule( task, 0, 10000 );
}


public void pauseTimer() {
    this.timer.cancel();
}

// when you first click to this button it will stop the timer , thats the first step
    //then manually call the method to search in the database if an item is missing
    //after that i will access the user.getnames outofstock (ps: this
    // is a string filled with checkdatabase stock method where we will fill the names from the database

public void mycartButtonClicked(String name,User user){

        // first thing i need to do it pose the timer why? i dont want to wait untill the time is up to search in database once i am inside the
//cart button i want to manually search if an item is out of stock
    pauseTimer();
    Stage stage = new Stage();
    stage.initModality(Modality.APPLICATION_MODAL);
    checkDatabaseStock(name , user);// here i manually make a check and delete from my cart if an item is out of stock
    // now all i have to do is fill the table with my cart values
   if(user.getNamesOutOfStock() != null){
       Alert alert = new Alert(Alert.AlertType.WARNING);
       alert.setHeaderText("Out Of Stock");
       alert.setTitle("out of stock");
       alert.setContentText("Items removed");
       alert.setContentText("some items where removed from your cart:\n "+user.getNamesOutOfStock()+"\n Reason: out of stock");
       DialogPane dialogPane = alert.getDialogPane();
       dialogPane.getStylesheets().add(getClass().getResource("/Cart.css").toExternalForm());
       dialogPane.getStyleClass().add("dialogRed");
       alert.showAndWait();
       user.setNamesOutOfStock(null);

   }
    TableView<Products> tableView = new TableView<>();

    TableColumn<Products,String> typeCol = new TableColumn<>("Type");
    typeCol.setMinWidth(100);
    typeCol.setCellValueFactory(new PropertyValueFactory<>("type"));

    TableColumn<Products,String> nameCol = new TableColumn<>("Name");
    nameCol.setMinWidth(100);
    nameCol.setCellValueFactory(new PropertyValueFactory<>("name"));

    TableColumn<Products,Double> priceCol = new TableColumn<>("Price");
    priceCol.setMinWidth(100);
    priceCol.setCellValueFactory(new PropertyValueFactory<>("price"));

    TableColumn<Products, Integer> idCol = new TableColumn<>("ID");
    idCol.setMinWidth(100);
    idCol.setCellValueFactory(new PropertyValueFactory<>("id"));

    TableColumn<Products, Integer> quantityCol = new TableColumn<>("Quantity");
    quantityCol.setMinWidth(100);
    quantityCol.setCellValueFactory(new PropertyValueFactory<>("cartQuantity"));


    ObservableList<Products> observableListProducts = FXCollections.observableArrayList();
   getCartData(observableListProducts,name);
   tableView.setItems(observableListProducts);
tableView.setEditable(true);
    quantityCol.setCellFactory(TextFieldTableCell.forTableColumn(new IntegerStringConverter()));// very important this interger converter sinse initially the value int cannot be edited this way when i click on the cell i can update

   
        quantityCol.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<Products, Integer>>() {
            @Override
            public void handle(TableColumn.CellEditEvent<Products, Integer> event) {

                Products product = event.getRowValue();
                int availability = checkAvailableOnEdit(product.getId());

                if (availability < event.getNewValue()) {
                    Alert alert = new Alert(Alert.AlertType.WARNING);
                    alert.setTitle("availability of items");
                    alert.setHeaderText(null);
                    alert.setContentText("Sorry your chosen value exceed our availability in stock\n" +
                            "please try to enter another quantity for the  selected item");
                    DialogPane dialogPane = alert.getDialogPane();
                    dialogPane.getStylesheets().add(getClass().getResource("/Cart.css").toExternalForm());
                    dialogPane.setId("myDialog");
                    alert.showAndWait();
                    tableView.refresh();
                } else {
                    product.setCartQuantity(event.getNewValue());
                    changeQuantityInCart(name, product.getId(), event.getNewValue());
                    tableView.refresh();
                }

            }

        });



   tableView.getColumns().addAll(typeCol,nameCol,priceCol,idCol,quantityCol);

   addButtonToTable(tableView,name);
   stage.setOnCloseRequest(e -> {
           e.consume(); // stopps the close event when you hit x, it means we are taking it from here (using the close method)
               resumeTimer(name,user);
               stage.close();

       });

   VBox layout = new VBox();
   layout.setId("lay1");
   layout.setSpacing(4);

   HBox topBox = new HBox();
   HBox buttomBox = new HBox();
   buttomBox.setAlignment(Pos.BOTTOM_LEFT);
   buttomBox.setSpacing(20);

   Text text = new Text("Customer Cart");
   text.setFont(Font.font("Bauhaus 93",32));
   text.setFill(Color.BLACK);
   topBox.getChildren().addAll(text);

   Button deleteButton = new Button("Delete");
   Button buyButton = new Button("Buy items");

   deleteButton.setOnAction( e -> {
       Products product = tableView.getSelectionModel().getSelectedItem();
       boolean ans = false;
       if(product != null)
        ans = ConfirmBox.display("delete item","Are you sure you want to delete "+product.getName()+" from your cart?");
      if(ans) {
          tableView.getItems().remove(product);
          deleteFromCartDB(name,product.getId());// call the function that delete the specific item from the database (cart table)
      }
   });buyButton.setOnAction( e -> {
      buyButtonFromCartWindow(tableView,name);// open window for buy items to take user detailes
   });
   buyButton.setId("blue");
   deleteButton.setId("blue");

   buttomBox.setMinWidth(90);
   buttomBox.getChildren().addAll(buyButton,deleteButton);
  layout.getChildren().addAll(topBox,tableView,buttomBox);

   Scene scene = new Scene(layout,600,480);
//    System.out.println("classpath=" + System.getProperty("java.class.path"));
    scene.getStylesheets().add(getClass().getResource("/Cart.css").toExternalForm());
    stage.setScene(scene);
   stage.show();
    }
// this method is called from the button cart where i will be getting the data of the cart from database
// this data will be added to an oobserrvable list wich will be user to fill the tableview in my cart
    public void getCartData(ObservableList<Products> observableListProducts, String name) {
    Connection connection = null;
    PreparedStatement getItems = null;
    ResultSet resultSet = null;
    try {
        connection = DriverManager.getConnection(url, username, mypassword);
        getItems = connection.prepareStatement("SELECT cartType,cartName,cartPrice,cartId,cartQuantity FROM cart WHERE userName = ?;");
        getItems.setString(1, name);
        resultSet = getItems.executeQuery();
        // loop while resultset has more rows add to the observable list the object with those colomns
        while (resultSet.next()) {

            observableListProducts.add(new Products(resultSet.getString(1), resultSet.getString(2),
                    resultSet.getDouble(3), resultSet.getInt(4),resultSet.getInt(5)));
        }
    } catch (SQLException e) {
        e.printStackTrace();
    } finally {
        if (getItems != null) {
            try {
                getItems.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        if (resultSet != null) {
            try {
                resultSet.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        if (connection != null) {
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }


    }

}
// this method will add button to the table
    // inside this method directly i added the window to open when the user press
    // the edit button , the window contains a spinner and will take its value
    // then check the database for the availability of the item before changing the number

    private void addButtonToTable(TableView table,String name) {
        TableColumn<Products, Void> colBtn = new TableColumn("Edit quantity");

        Callback<TableColumn<Products, Void>, TableCell<Products, Void>> cellFactory = new Callback<TableColumn<Products, Void>, TableCell<Products, Void>>() {
            @Override
            public TableCell<Products, Void> call(final TableColumn<Products, Void> param) {
                final TableCell<Products, Void> cell = new TableCell<Products, Void>() {

                    private final Button btn = new Button("Edit");

                    {
                        btn.styleProperty().bind(Bindings.when(btn.hoverProperty())
                                .then(" -fx-background-color:\n" +
                                        "        linear-gradient(#4977f5, #023dde),\n" +
                                        "        radial-gradient(center 50% -40%, radius 200%, #3c5cfa 45%,#0422b5 50%);\n" +
                                        "    -fx-background-radius: 6, 5;\n" +
                                        "    -fx-background-insets: 0, 1;\n" +
                                        "    -fx-effect: dropshadow( three-pass-box , rgba(0,0,0,0.4) , 5, 0.0 , 0 , 1 );\n" +
                                        "   -fx-text-fill: white;\n" +
                                        "    -fx-font-size: 12px;\n" +
                                        "    -fx-font-weight:bold;")
                                .otherwise(" -fx-background-color:\n" +
                                        "        linear-gradient(#4977f5, #023dde),\n" +
                                        "        radial-gradient(center 50% -40%, radius 200%, #3c5cfa 45%,#0422b5 50%);\n" +
                                        "    -fx-background-radius: 6, 5;\n" +
                                        "    -fx-background-insets: 0, 1;\n" +
                                        "    -fx-effect: dropshadow( three-pass-box , rgba(0,0,0,0.4) , 5, 0.0 , 0 , 1 );\n" +
                                        "    -fx-text-fill: #ebf5d7;"));
                        btn.setOnAction((ActionEvent event) -> {
                            Products data = getTableView().getItems().get(getIndex());
                           Stage stage = new Stage();
                           stage.initModality(Modality.APPLICATION_MODAL);
                           VBox layout = new VBox();
                           layout.setBackground(new Background(new BackgroundFill(Color.ORANGE,null,null)));
                           Label label = new Label("set Quantity for \n"+data.getName());
                           label.setStyle("-fx-font-family:Copperplate Gothic Bold;\n"+
                                   "-fx-text-fill:white;\n"+
                                   "-fx-font-style:italic;\n"+
                                   "-fx-text-alignment: center;\n"+
                                   "-fx-font-weight:bold");
                           Button button1 = new Button("save");
                            Button button2 = new Button("exit");


                               button2.styleProperty().bind(Bindings.when(button1.hoverProperty()).
                                       then(" -fx-background-color: \n" +
                                               "        #090a0c,\n" +
                                               "        linear-gradient(#3f505e 0%, #14181c 20%, #20262e 100%),\n" +
                                               "        linear-gradient(#222a30, #14171c),\n" +
                                               "        radial-gradient(center 50% 0%, radius 100%, rgba(131, 150, 168,0.9), rgba(255,255,255,0));\n" +
                                               "    -fx-background-radius: 5,4,3,5;\n" +
                                               "    -fx-background-insets: 0,1,2,0;\n" +
                                               "    -fx-text-fill: white;\n" +
                                               "    -fx-effect: dropshadow( three-pass-box , rgba(0,0,0,0.7) , 5, 0.0 , 0 , 1 );\n" +
                                               "    -fx-font-family: \"Arial\";\n" +
                                               "    -fx-text-fill: linear-gradient(white, #d0d0d0);\n" +
                                               "    -fx-font-size: 12px;\n" +
                                               "    -fx-padding: 6 15 6 15;\n" +
                                               "-fx-effect: dropshadow( one-pass-box , rgba(0,0,0,0.9) , 1, 0.0 , 0 , 1 );").
                                       otherwise(" -fx-background-color: \n" +
                                               "        #090a0c,\n" +
                                               "        linear-gradient(#38424b 0%, #1f2429 20%, #191d22 100%),\n" +
                                               "        linear-gradient(#20262b, #191d22),\n" +
                                               "        radial-gradient(center 50% 0%, radius 100%, rgba(114,131,148,0.9), rgba(255,255,255,0));\n" +
                                               "    -fx-background-radius: 5,4,3,5;\n" +
                                               "    -fx-background-insets: 0,1,2,0;\n" +
                                               "    -fx-text-fill: white;\n" +
                                               "    -fx-effect: dropshadow( three-pass-box , rgba(0,0,0,0.6) , 5, 0.0 , 0 , 1 );\n" +
                                               "    -fx-font-family: \"Arial\";\n" +
                                               "    -fx-text-fill: linear-gradient(white, #d0d0d0);\n" +
                                               "    -fx-font-size: 12px;\n" +
                                               "    -fx-padding: 10 20 10 20;"));


                            button1.styleProperty().bind(Bindings.when(button2.hoverProperty()).
                                    then(" -fx-background-color: \n" +
                                            "        #090a0c,\n" +
                                            "        linear-gradient(#3f505e 0%, #14181c 20%, #20262e 100%),\n" +
                                            "        linear-gradient(#222a30, #14171c),\n" +
                                            "        radial-gradient(center 50% 0%, radius 100%, rgba(131, 150, 168,0.9), rgba(255,255,255,0));\n" +
                                            "    -fx-background-radius: 5,4,3,5;\n" +
                                            "    -fx-background-insets: 0,1,2,0;\n" +
                                            "    -fx-text-fill: white;\n" +
                                            "    -fx-effect: dropshadow( three-pass-box , rgba(0,0,0,0.7) , 5, 0.0 , 0 , 1 );\n" +
                                            "    -fx-font-family: \"Arial\";\n" +
                                            "    -fx-text-fill: linear-gradient(white, #d0d0d0);\n" +
                                            "    -fx-font-size: 12px;\n" +
                                            "    -fx-padding: 6 15 6 15;\n" +
                                            "-fx-effect: dropshadow( one-pass-box , rgba(0,0,0,0.9) , 1, 0.0 , 0 , 1 );").
                                    otherwise(" -fx-background-color: \n" +
                                            "        #090a0c,\n" +
                                            "        linear-gradient(#38424b 0%, #1f2429 20%, #191d22 100%),\n" +
                                            "        linear-gradient(#20262b, #191d22),\n" +
                                            "        radial-gradient(center 50% 0%, radius 100%, rgba(114,131,148,0.9), rgba(255,255,255,0));\n" +
                                            "    -fx-background-radius: 5,4,3,5;\n" +
                                            "    -fx-background-insets: 0,1,2,0;\n" +
                                            "    -fx-text-fill: white;\n" +
                                            "    -fx-effect: dropshadow( three-pass-box , rgba(0,0,0,0.6) , 5, 0.0 , 0 , 1 );\n" +
                                            "    -fx-font-family: \"Arial\";\n" +
                                            "    -fx-text-fill: linear-gradient(white, #d0d0d0);\n" +
                                            "    -fx-font-size: 12px;\n" +
                                            "    -fx-padding: 10 20 10 20;"));

                            button2.setOnAction( e-> stage.close());
                           Spinner<Integer> spinner = new Spinner<>(1,20,1);
                           spinner.setEditable(false);
                            button1.setOnAction( e -> {
                                int ans = checkAvailableOnEdit(data.getId());
                                if(ans < spinner.getValue()){
                                    Alert alert = new Alert(Alert.AlertType.WARNING);
                                    alert.setTitle("availability of items");
                                    alert.setHeaderText(null);
                                    alert.setContentText("Sorry your chosen value exceed our availability in stock\n" +
                                            "please try to enter another quantity for the  selected item");
                                    DialogPane dialogPane = alert.getDialogPane();
                                    dialogPane.getStylesheets().add(getClass().getResource("/Cart.css").toExternalForm());
                                    dialogPane.setId("myDialog");
                                    alert.showAndWait();
                                }else{
                                    Products product = getTableView().getItems().get(getIndex());
                                    product.setCartQuantity(spinner.getValue());
                                    getTableView().refresh();
                                   changeQuantityInCart(name,product.getId(),spinner.getValue());
                                   stage.close();
                                }
                            });
                            btn.setId("smallBlue");
                           HBox hBox = new HBox();
                           hBox.setAlignment(Pos.CENTER);
                           hBox.setSpacing(10);
                           hBox.getChildren().addAll(button1,button2);
                           layout.getChildren().addAll(label,spinner,hBox);
                           layout.setSpacing(10);
                           layout.setAlignment(Pos.CENTER);
                           layout.setStyle("-fx-background-color:#040b1c");
                           Scene scene = new Scene(layout,200,200);
                            scene.getStylesheets().add(getClass().getResource("/Cart.css").toExternalForm());
                            stage.initModality(Modality.APPLICATION_MODAL);
                           stage.setScene(scene);
                           stage.show();





                        });
                    }

                    @Override
                    public void updateItem(Void item, boolean empty) {
                        super.updateItem(item, empty);
                        if (empty) {
                            setGraphic(null);
                        } else {
                            setGraphic(btn);
                        }
                    }
                };
                return cell;
            }
        };

        colBtn.setCellFactory(cellFactory);

        table.getColumns().add(colBtn);

    }

    // in this method it will be called when the user edit the quantity in thecart table
    // this method will be called from 2 places , either directly when the user change the value
    //ie: on edit commit (when  the user press on the numer change it then press enter
    // or when the user use the edit button
    // this method will return an int (the resultset ) of the available number of items for this specific selected item

    public int checkAvailableOnEdit( int id){
        Connection connection = null;
        PreparedStatement checkDatabase = null;
        PreparedStatement deleteData = null;
        ResultSet resultSet = null;

        int ans =0;
        try {
            connection = DriverManager.getConnection(url,username,mypassword);
           checkDatabase = connection.prepareStatement("SELECT quantity FROM items WHERE id =?");
          checkDatabase.setInt(1,id);
           resultSet = checkDatabase.executeQuery();
           if(resultSet.next())
           ans = resultSet.getInt(1);
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            if(checkDatabase != null){
                try {
                    checkDatabase.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }if(resultSet != null){
                try {
                    resultSet.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }if(connection != null){
                try {
                    connection.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
return ans;
    }
    // after invoking the method above( checkAvailableOnEdit) now i will change the quantity
    // of the item in the cart table in database so it will be saved for the specific user

    public void  changeQuantityInCart(String name,int id ,int newValue){
        Connection connection = null;
        PreparedStatement update = null;
        try {
            connection = DriverManager.getConnection(url,username,mypassword);
          update = connection.prepareStatement("UPDATE cart SET cartQuantity = ? WHERE userName = ? AND cartId = ? ");
       update.setInt(1,newValue);
       update.setString(2,name);
       update.setInt(3,id);
          update.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            if(update != null){
                try {
                    update.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }if(connection != null){
                try {
                    connection.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }

    }
    // this method will delete a specific item , this method is called from
    // the cart window when the user want to delete an item from his cart
    // the method takes a name ( name of the user) and an id( the id of the specific item selected from the table)
    //this item will be delted from the datbase (cart table for this specific user cart)

    public void  deleteFromCartDB(String name , int id){
        Connection connection = null;
        PreparedStatement delete = null;
        try {
            connection = DriverManager.getConnection(url,username,mypassword);
           delete = connection.prepareStatement("DELETE FROM cart WHERE userName = ? AND cartId = ?");
           delete.setString(1,name);
            delete.setInt(2,id);
            delete.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            if(delete != null){
                try {
                    delete.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }if(connection != null){
                try {
                    connection.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }


    }
    // this method is called from the buy button inside the cart
    // in this method we are first searching the database to find
    // if the quantity that the user chose is available in our store (items table)
    // if the result is < 0 then the items' quantity is less than the quanity
    // asked for by the user if this happens the query will
    //  find the names of items that exceed the quantity asked for
    // those names will be returned from this function as a string
    //in order to  send a alert message for the user telling him what
    // items where more than the available quantity, if the items does not
    // exeed the quantity another query will be introduced where the items from
    // items table will be updated and the quantity will be changed - quantity in cart
    // if everything is okay only one step left is to delete data from cart table for the specific user
    // becauise items where baught successfully;
    public String buyFromCartButtonClicked(String name){
       Connection connection = null;
       PreparedStatement checkAvailability = null;// in one query i will search  for availability and return the names of items that are not available in the quantity ordered
       PreparedStatement updateData = null;
       ResultSet getItemsSet = null;
        PreparedStatement insertDateReport = null;
       String ans = "";// this will hold the name / if there is of the items that are not available
        try {
            connection = DriverManager.getConnection(url,username,mypassword);
            checkAvailability = connection.prepareStatement("SELECT name FROM items WHERE id IN(\n" +
                    "\n" +
                    "SELECT id FROM (SELECT t.quantity - t.cartQuantity AS \"isAvailable\",t.id FROM \n" +
                    "(\n" +
                    "    SELECT * FROM items,cart\n" +
                    "    WHERE items.id = cart.cartId\n" +
                    "    AND userName = ?\n" +
                    ") AS t\n" +
                    ") AS t2 WHERE isAvailable < 0);");
            checkAvailability.setString(1,name);
            getItemsSet = checkAvailability.executeQuery();
            StringBuilder resultText = new StringBuilder();

                   while (getItemsSet.next()) {
                       resultText.append(getItemsSet.getString(1)).append(" ,");
                   }
                   if(!resultText.isEmpty())
                   ans = resultText.toString();

           if(ans.equals("")) {//  if the result set was empty that means that no items exeed the quantity asked for by the user therefore i can use this other query to update now the items table where i will be updating all the quantiy in items - quantity asked by the user for the specific items in his card
               ObservableList<Products> observableListProducts = FXCollections.observableArrayList();
               getCartData(observableListProducts,name);
               // those two lines are to get the data from cart and store it in this observable list
               insertDateReport = connection.prepareStatement("INSERT INTO dateReport VALUES(?,?,?)");

               java.sql.Date sqlDate = new java.sql.Date(new java.util.Date().getTime());

               for(Products p : observableListProducts){
                   insertDateReport.setInt(1,p.getId());
                   insertDateReport.setDate(2,sqlDate);
                   insertDateReport.setInt(3,p.getCartQuantity());
                   insertDateReport.executeUpdate();
               }



               updateData = connection.prepareStatement("UPDATE items INNER JOIN cart ON (items.id = cart.cartId)\n" +
                        "SET items.quantity = items.quantity-cart.cartQuantity\n" +
                        "WHERE cart.userName = ? ;");
            updateData.setString(1,name);
            updateData.executeUpdate();



                updateData = connection.prepareStatement("DELETE FROM cart WHERE userName = ?;");
                updateData.setString(1,name);
                updateData.executeUpdate();// after doing all my updates now i have to delete the data from cart table that are related to the user because he actually purchased those items
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            if(checkAvailability != null){
                try {
                    checkAvailability.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }if(updateData != null){
                try {
                    updateData.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }if(getItemsSet != null){
                try {
                    getItemsSet.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }if(insertDateReport != null){
                try {
                    insertDateReport.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if(connection != null){
                try {
                    connection.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }

        return  ans;
    }
    public void buyButtonFromCartWindow(TableView<Products> tableView ,String name){
        Stage buyWindow = new Stage();
        buyWindow.initModality(Modality.APPLICATION_MODAL);
        GridPane layout = new GridPane();
        Label nameLabel = new Label("Name");
        TextField nameInput = new TextField();
        nameInput.setPromptText("enter your name");
        Label cityLabel = new Label("city");
        TextField cityInput = new TextField();
        cityInput.setPromptText("enter your city");
        Label dateLabel = new Label();
        dateLabel.setText("Date");
        DatePicker datePicker = new DatePicker();
        datePicker.setPromptText("Enter your date");
        Label emailLabel = new Label("Email");
        TextField emailInput = new TextField();
        emailInput.setPromptText("example@hotmail.com");
        Label cartNumLabel = new Label("Cart num");
        TextField cartNumInput = new TextField();
        cartNumInput.setPromptText("Visa card number");
        ObservableList<Info> infoObservableList = getInfoFromDB(name);// i am storing the data comming from this method call as an observable list(data is comming from the database) and i will give this data to the fields

        if(!infoObservableList.isEmpty()){// if i have a list it means the user already saved his info so then i will fill those fields with the saved info
            nameInput.setText(infoObservableList.get(0).getName());
            emailInput.setText(infoObservableList.get(0).getEmail());
            cityInput.setText(infoObservableList.get(0).getCity());
            datePicker.getEditor().setText(infoObservableList.get(0).getDob());

        }
        // this will force the user to enter a number
        // you cannot even enter anything else
        // in this method i have an observable value which is like a arraylist
        // it extends string cz a am taking a user text input
        // i have old vallue (the field is emty) then the new value the one i entered
        // if the new value matches a decimal set the text to a decimal
        cartNumInput.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue,
                                String newValue) {
                if (!newValue.matches("\\d*")) {
                    cartNumInput.setText(newValue.replaceAll("[^\\d]", ""));
                }
            }

        });
        Label pinCodeLabel = new Label("Pin code");
        PasswordField pinCodeInput = new PasswordField();
        pinCodeInput.setPromptText("enter pin code");
        pinCodeInput.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue,
                                String newValue) {
                if (!newValue.matches("\\d*")) {
                    pinCodeInput.setText(newValue.replaceAll("[^\\d]", ""));
                }
            }
        });
        Button button1 = new Button("buy");
        Button button2 = new Button("exit");
        Button button3 = new Button("Save info");
        button1.setId("bevel-grey");
        button2.setId("bevel-grey");
        button3.setId("bevel-grey");
        layout.addRow(0,nameLabel,nameInput);
        layout.addRow(1,cityLabel,cityInput);
        layout.addRow(2,dateLabel,datePicker);
        layout.addRow(3,emailLabel,emailInput);
        layout.addRow(4,cartNumLabel,cartNumInput);
        layout.addRow(5,pinCodeLabel,pinCodeInput);
        HBox buttons = new HBox();
        buttons.setAlignment(Pos.CENTER);
        buttons.setSpacing(10);
        buttons.getChildren().addAll(button1,button2,button3);
        layout.setHgap(20);
        layout.setVgap(30);
        layout.setAlignment(Pos.CENTER);
        VBox box = new VBox();
        box.setSpacing(30);
        box.setAlignment(Pos.CENTER);
        box.getChildren().addAll(layout,buttons);

       //buy button in cart window
        button1.setOnAction( e -> {
//
            ArrayList<TextField> fields = new ArrayList<>();
            fields.add(nameInput);
            fields.add(cityInput);
            fields.add(emailInput);
            fields.add(cartNumInput);
            fields.add(pinCodeInput);
            boolean empty = false;
            for(TextField t : fields){
                if(Objects.equals(t.getText(), "")){
                    empty = true;
                    break;
                }
            }
            // i put all fields in an arraylist so that i can search if anyh of those is empty
            // than i add the date picker codition alone because its diffrent to access it than the normal fields
            // in this if i check in any of the fields is emty and i launch a alert
            if(empty || Objects.equals(datePicker.getEditor().getText(), "")){
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setContentText("empty field");
                alert.setHeaderText(null);
                alert.setContentText("you cannot have an empty field");
                DialogPane dialogPane = alert.getDialogPane();
                dialogPane.getStylesheets().add(getClass().getResource("/Cart.css").toExternalForm());
                dialogPane.setId("myDialog");
                alert.showAndWait();
            }else{
                if(!validEmail(emailInput.getText())){
                    System.out.println("false email");
                    emailInput.setText("");
                }else {
                    String ans = buyFromCartButtonClicked(name); // this will call a method that search the database for quantity and return a string of items that are unavailable (if there is such items)
                   if(ans.equals("")){// then no items are unavailable (no items returned)(check the method)
                      Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                      alert.setTitle("confirm");
                      alert.setHeaderText("Thank you for using TonyTech.");
                      alert.setContentText("items successfully purchased. Shipment to your place might take from 2 to 7 days");
                       DialogPane dialogPane = alert.getDialogPane();
                       dialogPane.getStylesheets().add(getClass().getResource("/Cart.css").toExternalForm());
                       dialogPane.getStyleClass().add("dialogBlue");
                     alert.showAndWait();
                     tableView.getItems().clear();
                     buyWindow.close();

                   }else{
                       Alert alert = new Alert(Alert.AlertType.WARNING);
                       alert.setTitle("items unavailable");
                       alert.setHeaderText(null);
                       alert.setContentText("Items below exceed our availability in store:\n"+ans+"\n"+"" +
                               "please change the quantity for those items");
                       DialogPane dialogPane = alert.getDialogPane();
                       dialogPane.getStylesheets().add(getClass().getResource("/Cart.css").toExternalForm());
                       dialogPane.setId("myDialog");
                       alert.showAndWait();
                   }
                    nameInput.setText("");
                    cityInput.setText("");
                    datePicker.getEditor().setText("");
                    emailInput.setText("");
                    cartNumInput.setText("");
                    pinCodeInput.setText("");
                }
            }


        });
        // end of action on button 1

        // actions for exit button
        button2.setOnAction(e -> {
            // call the static method in confirm box class where a window will
            // appear and this method will return a boolean value if its true we close the buy window
            boolean answear = ConfirmBox.display("confirm","are you sure you want to exit buy window?");
            if(answear)
                buyWindow.close();
        });

        button3.setOnAction( e -> {
            ArrayList<TextField> fields = new ArrayList<>();
            fields.add(nameInput);
            fields.add(cityInput);
            fields.add(emailInput);
            fields.add(cartNumInput);
            fields.add(pinCodeInput);
            boolean empty = false;
                    for(TextField t : fields){
                        if(Objects.equals(t.getText(), "")){
                            empty = true;
                            break;
                        }
                    }
                    // i put all fields in an arraylist so that i can search if anyh of those is empty
                    // than i add the date picker codition alone because its diffrent to access it than the normal fields
                    // in this if i check in any of the fields is emty and i launch a alert
                    if(empty || Objects.equals(datePicker.getEditor().getText(), "")){
                        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                        alert.setContentText("empty field");
                        alert.setHeaderText(null);
                        alert.setContentText("you cannot have an empty field");
                        DialogPane dialogPane = alert.getDialogPane();
                        dialogPane.getStylesheets().add(getClass().getResource("/Cart.css").toExternalForm());
                        dialogPane.setId("myDialog");
                        alert.showAndWait();
                    }else {
                        if (!validEmail(emailInput.getText())) {
                            System.out.println("false email");
                            emailInput.setText("");
                        } else {
                            boolean ans = ConfirmBox.display("Save", "do you want to save your info for future use?");
                            if (ans) {
                                saveInfoToDB(nameInput.getText(),cityInput.getText(),emailInput.getText(),datePicker.getEditor().getText(),name);// call of a method that saves the user info in the database (table info) reson: fo rfuture use instead of putting the info again every time
                            }
                        }
                    }
        });
        // this method will force the close by clicking x to ask also if you are sure you want to quit
        buyWindow.setOnCloseRequest(e -> {
                    e.consume(); // stopps the close event when you hit x, it means we are taking it from here (using the close method)
                    boolean answear = ConfirmBox.display("confirm","are you sure you want to exit buy window?");
                    if(answear)
                        buyWindow.close();
                }
        );

        ArrayList<Label> labels = new ArrayList<>();
        labels.add(nameLabel);
        labels.add(cartNumLabel);
        labels.add(cityLabel);
        labels.add(dateLabel);
        labels.add(emailLabel);
        labels.add(pinCodeLabel);



        for(Label label : labels){
            label.setFont(Font.font("Copperplate Gothic Bold",15));
            label.setStyle("-fx-fill: red;\n" +
                    "  -fx-font-weight: bold;\n" +
                    "  -fx-effect: dropshadow( gaussian , rgb(255, 255, 255) , 0,0,0,1 )");

        }

        InputStream inputStream = null;
        try {
            inputStream = new FileInputStream("src/main/java/Images/darkImage2.jpg");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        Image image = new Image(inputStream);

        BackgroundSize bSize = new BackgroundSize(BackgroundSize.AUTO, BackgroundSize.AUTO, false, false, true, true);//double width, double height, boolean widthAsPercentage, boolean heightAsPercentage, boolean contain, boolean cover
        Background background = new Background(new BackgroundImage(image,
                BackgroundRepeat.NO_REPEAT,
                BackgroundRepeat.NO_REPEAT,
                BackgroundPosition.CENTER,
                bSize));
        box.setBackground(background);
        TextField[] texts = new TextField[]{nameInput,emailInput,cartNumInput,cityInput,pinCodeInput};


        Scene scene = new Scene(box,300,400);
        scene.getStylesheets().add(getClass().getResource("/Cart.css").toExternalForm());
        for(TextField t: texts){
            t.setId("glassField");
        }

        buyWindow.setScene(scene);
        buyWindow.show();
    }
    // this method is used to save data of the user when the button save info is clicked from inside the buy button
    // in both the main window buy button and the buy button inside the cart
    // first this method will check the data in the info table is the user have already saved
    // a data before the new info saved will override the ones before
    // else we will simply insert new data in the table
    public void saveInfoToDB(String nameInput,String cityInput,String emailInput,String dob,String userName){
        Connection connection = null;
        PreparedStatement save = null;
        PreparedStatement checkPrevData = null;
        ResultSet getPrevData = null;
        PreparedStatement updateData = null;
        try {
            connection = DriverManager.getConnection(url,username,mypassword);
            checkPrevData = connection.prepareStatement("SELECT * FROM info WHERE userName = ?");
            checkPrevData.setString(1,userName);
           getPrevData = checkPrevData.executeQuery();
           if(!getPrevData.isBeforeFirst()) { // if the data does not already exits then insert into table , else then override the first result (because every user can occupy one row in the info table ) we cannot save diffrent info for one user
               save = connection.prepareStatement("INSERT INTO info VALUES(?,?,?,?,?)");
               save.setString(1, nameInput);
               save.setString(2, cityInput);
               save.setString(3, dob);
               save.setString(4, emailInput);
               save.setString(5, userName);
               save.executeUpdate();
           }else{
               updateData = connection.prepareStatement("UPDATE info set name = ?,city = ?,dob = ?,email = ? WHERE userName = ?;");
               updateData.setString(1,nameInput);
               updateData.setString(2,cityInput);
               updateData.setString(3, dob);
               updateData.setString(4, emailInput);
               updateData.setString(5, userName);
               updateData.executeUpdate();
           }
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            if(save != null){
                try {
                    save.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }if(checkPrevData != null){
                try {
                    checkPrevData.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }if(getPrevData != null){
                try {
                    getPrevData.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }if(updateData != null){
                try {
                    updateData.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }if(connection != null){
                try {
                    connection.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }

    }

    // this method is calledwhen we open the buy window , if the user saved his data befoe
    // we will get this data in this method and store it in a observable list
    // to give those info to the fields in our buy window
    public ObservableList<Info> getInfoFromDB(String name){
      ObservableList<Info> observableList =  FXCollections.observableArrayList();
      Connection connection = null;
      PreparedStatement fetchData= null;
      ResultSet resultSet = null;
        try {
            connection = DriverManager.getConnection(url,username,mypassword);
            fetchData = connection.prepareStatement("SELECT name,city,dob,email FROM info WHERE userName =?");
            fetchData.setString(1,name);
           resultSet = fetchData.executeQuery();
           if(resultSet.next()){
               observableList.add(new Info(resultSet.getString(1),resultSet.getString(2),resultSet.getString(3),resultSet.getString(4)));
           }
        } catch (SQLException e) {
            e.printStackTrace();
        }finally{
            if(fetchData != null){
                try {
                    fetchData.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }if(resultSet != null){
                try {
                    resultSet.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }if(connection != null){
                try {
                    connection.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
        return observableList;

    }

    // this window will be called when the user click on the contact us pic
    // i will open a window showing the contact numbers
    public void contactUsWindow(String name){
        Stage stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);
        GridPane gridPane = new GridPane();
        Label city = new Label("City");
        Label landLine = new Label("Land line");
        Label other = new Label("other");
        Label dora = new Label("Dora");
        Label landLineDora = new Label("01/314111");
        Label otherDora = new Label("76/535434");
        Label beirut = new Label("Beirut");
        Label landLineBeirut = new Label("01/342111");
        Label otherBeirut = new Label("70/708763");
        Label junieh = new Label("Junieh");
        Label landLinejunieh = new Label("01/331111");
        Label otherjunieh = new Label("79/796546");
        gridPane.addRow(1,city,landLine,other);
        gridPane.addRow(2,dora,landLineDora,otherDora);
        gridPane.addRow(3,beirut,landLineBeirut,otherBeirut);
        gridPane.addRow(4,junieh,landLinejunieh,otherjunieh);
        gridPane.setAlignment(Pos.CENTER);
        gridPane.setHgap(30);
        gridPane.setVgap(20);
        HBox hbox = new HBox();
        VBox layout = new VBox();
       Label label = new Label("Or email us at:");
       Hyperlink link = new Hyperlink("TonyTech@outlook.com");
        hbox.getChildren().addAll(label,link);
        layout.setSpacing(70);
        layout.setStyle("-fx-background-color:#010e24;");
        Label[] labels = new Label[]{city,landLine,other,dora,landLineDora,otherDora,beirut,landLineBeirut,otherBeirut,junieh,landLinejunieh,otherjunieh,label};
        for(Label l : labels){
            l.setFont(Font.font("Copperplate Gothic Bold",15));
            l.setStyle("-fx-text-fill: white;\n" +
                    "  -fx-font-weight: bold;\n" +
                    "  -fx-effect: dropshadow( gaussian , rgb(8, 7, 7) , 0,0,0,1 )");

        }
        layout.getChildren().addAll(gridPane,hbox);

        Scene scene = new Scene(layout,350,300);
        stage.setScene(scene);
        stage.showAndWait();

    }

    // this table view will be a lot similar to the one i am using for the user to get cart data
    // the only diffrence here is i will be showing the data from all items not from the cart table
    //this will only be shown to the user tony who is admin
    // this is a hidden feature that only tony can acess the stock
    public void cartImageClicked(){
        Stage stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);
        TableView<Stock> stockTableView = new TableView<>();

        TableColumn<Stock,String> typeCol = new TableColumn<>("Type");
        typeCol.setMinWidth(100);
        typeCol.setCellValueFactory(new PropertyValueFactory<>("type"));

        TableColumn<Stock,String> nameCol = new TableColumn<>("Name");
        nameCol.setMinWidth(100);
        nameCol.setCellValueFactory(new PropertyValueFactory<>("name"));

        TableColumn<Stock,Double> priceCol = new TableColumn<>("Price");
        priceCol.setMinWidth(100);
        priceCol.setCellValueFactory(new PropertyValueFactory<>("price"));

        TableColumn<Stock, Integer> idCol = new TableColumn<>("ID");
        idCol.setMinWidth(100);
        idCol.setCellValueFactory(new PropertyValueFactory<>("id"));

        TableColumn<Stock, Integer> quantityCol = new TableColumn<>("Quantity");
        quantityCol.setMinWidth(100);
        quantityCol.setCellValueFactory(new PropertyValueFactory<>("stockQuantity"));
        stockTableView.getColumns().addAll(typeCol,nameCol,priceCol,idCol,quantityCol);
        ObservableList<Stock> observableListStock = FXCollections.observableArrayList();
        getStockData(observableListStock);// this method will interract with the database and return the resultset as a observable list holding the data of all the items in the stock
       stockTableView.setItems(observableListStock);
        stockTableView.setEditable(true);
        quantityCol.setCellFactory(TextFieldTableCell.forTableColumn(new IntegerStringConverter()));// very important this interger converter sinse initially the value int cannot be edited this way when i click on the cell i can update
        quantityCol.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<Stock, Integer>>() {
                                        @Override
                                        public void handle(TableColumn.CellEditEvent<Stock, Integer> event) {
                                            Stock stock = event.getRowValue();
                                             stock.setStockQuantity(event.getNewValue());// this to update the quantity column in the table when commit
                                            updateQuantityInStock(stock.getId(),event.getNewValue());// update the quantity of item in the items table in the database
                                        }
        });

       Label label = new Label("Stock items");
        label.setFont(Font.font("Bauhaus 93",32));
        label.setTextFill(Color.BLACK);
       HBox topleftBox = new HBox();
       topleftBox.getChildren().add(label);
       topleftBox.setAlignment(Pos.CENTER_LEFT);
       HBox topRightBox = new HBox();
       Button getReportButton = new Button("report");
      getReportButton.setId("smallBlue");


       topRightBox.getChildren().add(getReportButton);
       topRightBox.setAlignment(Pos.CENTER_RIGHT);
     topRightBox.setMinWidth(340);

       HBox topBox= new HBox();
       topBox.getChildren().addAll(topleftBox,topRightBox);

       VBox layout = new VBox();
       layout.getChildren().addAll(topBox,stockTableView);

       GridPane gridPane = new GridPane();
       Label typeLabel = new Label("Type");
       typeLabel.setId("labels");
       TextField typeField = new TextField();
       typeField.setId("field");

       Label nameLabel = new Label("Name");
        nameLabel.setId("labels");
       TextField nameField = new TextField();
        nameField.setId("field");

        Label priceLabel = new Label("Price");
        priceLabel.setId("labels");
        TextField priceField = new TextField();
        priceField.setId("field");

        Label idLabel = new Label("Id");
        idLabel.setId("labels");
        TextField idField = new TextField();
        idField.setId("field");

        Label quantityLabel = new Label("Quantity");
        quantityLabel.setId("labels");
        TextField quantityField = new TextField();
        quantityField.setId("field");

        forceNumber(idField);// those method call till call a method that will force the input to be only numbers
        forceNumber(quantityField);
        forceNumber(priceField);



        Button addButton = new Button("Add item");


        addButton.setId("blue");


        addButton.setOnAction( e -> {
            if(typeField.getText().isEmpty() || nameField.getText().isEmpty()
            ||priceField.getText().isEmpty()||idField.getText().isEmpty()||typeField.getText().isEmpty()||quantityField.getText().isEmpty()){
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setHeaderText(null);
                alert.setTitle("empty field");
                alert.setContentText("You cannot have an empty field!!");
                DialogPane dialogPane = alert.getDialogPane();
                dialogPane.getStylesheets().add(getClass().getResource("/Cart.css").toExternalForm());
                dialogPane.setId("myDialog");
                alert.showAndWait();
            }else {
                Stock stock = new Stock(typeField.getText(),nameField.getText(),Double.parseDouble(priceField.getText()),Integer.parseInt(idField.getText()),Integer.parseInt(quantityField.getText()));
                stockTableView.getItems().add(stock);
                // i am only doing this above to update the table in real time for the user to see the update
                // this will be only for the user to see . an alternative way would be to call the method that search the database and give me back an observable list
                // after updating the database and adding this item , however this is totally unessesary
                // and i would be calling a database method for only the purpose of making the user to see the update in real time , therefore i updated the table on click of the add button then the databse will be called when opening the cart pic again so the table will be then updating from the databse itself
                addItemToStock(typeField.getText(), nameField.getText(),
                        priceField.getText(), idField.getText(), quantityField.getText());
                 typeField.clear();
                 nameField.clear();
                 idField.clear();
                 priceField.clear();
                 quantityField.clear();
            }
            });

        gridPane.addRow(1,typeLabel,typeField,nameLabel,nameField);
        gridPane.addRow(2,priceLabel,priceField,idLabel,idField);
        gridPane.addRow(3,quantityLabel,quantityField);
        GridPane.setConstraints(addButton,3,3);
        gridPane.getChildren().addAll(addButton);

        gridPane.setHgap(10);
        gridPane.setVgap(20);
        layout.setSpacing(20);

        layout.setStyle("-fx-background-color:darkblue;");
        layout.getChildren().add(gridPane);



        Scene scene = new Scene(layout,502,480);
        scene.getStylesheets().add(getClass().getResource("/Cart.css").toExternalForm());

        stage.setScene(scene);
       stage.show();

        getReportButton.setOnAction( e -> {
           reportButtonClicked(scene,stage);// this method will return a scene so it will be used to switch between it and the current scene( whish is the table of items)

        });

    }
    public void getStockData(ObservableList<Stock> list){
        Connection connection = null;
        PreparedStatement fetch = null;
        ResultSet resultSet = null;

        try {
            connection = DriverManager.getConnection(url,username,mypassword);
            fetch = connection.prepareStatement("SELECT * FROM items;");
            resultSet = fetch.executeQuery();
            while(resultSet.next()){
                list.add(new Stock(resultSet.getString(1),resultSet.getString(2),resultSet.getDouble(3),resultSet.getInt(4),resultSet.getInt(5)));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            if(fetch != null){
                try {
                    fetch.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }if(resultSet != null){
                try {
                    resultSet.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }if(connection != null){
                try {
                    connection.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }

    }

    // this method is used to force a field to be numbers only
    //if the input ie: new value does not match a number then the text is replaced with empty string hense you are obliged to enter a number in the given field
    public void forceNumber(TextField t){
        t.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue,
                                String newValue) {
                if (!newValue.matches("\\d*")) {
                    t.setText(newValue.replaceAll("[^\\d]", ""));
                }
            }

        });
    }

    // this method is called when the addItem button on the cartImage is clicked (by the admin)
    // we will use this method to access the database and add items to it(items table)

    public void addItemToStock(String type , String name ,String price, String id , String quantity  ){
        Connection connection = null;
        PreparedStatement addItem = null;

        try {
            connection = DriverManager.getConnection(url,username,mypassword);
            addItem = connection.prepareStatement("INSERT INTO items VALUES (?,?,?,?,?)");
            addItem.setString(1,type);
            addItem.setString(2,name);
            addItem.setDouble(3,Double.parseDouble(price));
            addItem.setInt(4,Integer.parseInt(id));
            addItem.setDouble(5,Double.parseDouble(quantity));
            addItem.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            if(addItem != null){
                try {
                    addItem.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }if(connection != null){
                try {
                    connection.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }

    }
    public  void updateQuantityInStock(int id , int newValue){
        Connection connection =null;
        PreparedStatement updateItem = null;

        try {
            connection = DriverManager.getConnection(url,username,mypassword);
           updateItem = connection.prepareStatement("UPDATE items SET quantity = ? WHERE id = ?");
           updateItem.setInt(1,newValue);
           updateItem.setInt(2,id);
           updateItem.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            if(updateItem != null){
                try {
                    updateItem.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }if(connection != null){
                try {
                    connection.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }


    }
    public  void reportButtonClicked(Scene scene1,Stage stage){

        TableView<Report> reportTableView = new TableView<>();

        TableColumn<Report,String> typeCol = new TableColumn<>("Type");
        typeCol.setMinWidth(100);
        typeCol.setCellValueFactory(new PropertyValueFactory<>("type"));

        TableColumn<Report,String> nameCol = new TableColumn<>("Name");
        nameCol.setMinWidth(100);
        nameCol.setCellValueFactory(new PropertyValueFactory<>("name"));

        TableColumn<Report,Double> priceCol = new TableColumn<>("Price");
        priceCol.setMinWidth(100);
        priceCol.setCellValueFactory(new PropertyValueFactory<>("price"));

        TableColumn<Report, Integer> idCol = new TableColumn<>("ID");
        idCol.setMinWidth(100);
        idCol.setCellValueFactory(new PropertyValueFactory<>("id"));

        TableColumn<Report, Integer> quantityCol = new TableColumn<>("Quantity Sold");
        quantityCol.setMinWidth(100);
        quantityCol.setCellValueFactory(new PropertyValueFactory<>("quantitySold"));

        reportTableView.getColumns().addAll(typeCol,nameCol,priceCol,idCol,quantityCol);
        ObservableList<Report> observableListDateReport = FXCollections.observableArrayList();


       Button returnButton = new Button("Return");
       Button printButton = new Button("Print");
       printButton.setId("blue");
       returnButton.setId("blue");
       HBox topBox = new HBox();
       Label label = new Label("Report for sold items");
        label.setFont(Font.font("Bauhaus 93",32));
       label.setTextFill(Color.BLACK);

       topBox.getChildren().add(label);

       HBox secondTopBox = new HBox();// this is the box where ill put the dates to pick from
     secondTopBox.setSpacing(10);
     secondTopBox.setAlignment(Pos.CENTER);
        DatePicker fromPicker = new DatePicker();
        DatePicker toPicker = new DatePicker();
        Label fromlabel = new Label("From");
        Label toLabel = new Label("To");
        Label[] fromTo = new Label[]{fromlabel,toLabel};
        for(Label l : fromTo){
            l.setFont(Font.font("Copperplate Gothic Bold",10));
            l.setStyle("-fx-text-fill: white;\n" +
                    "  -fx-font-weight: bold;\n" +
                    "  -fx-effect: dropshadow( gaussian , rgb(8, 7, 7) , 0,0,0,1 )");

        }

        Button goButton = new Button("GO");
        goButton.setId("smallBlue");

       goButton.setOnAction( e ->{
           if(fromPicker.getValue() == null || toPicker.getValue() == null){
               Alert alert = new Alert(Alert.AlertType.WARNING);
               alert.setHeaderText(null);
               alert.setContentText("Cannot have an empty date!!");
               DialogPane dialogPane = alert.getDialogPane();
               dialogPane.getStylesheets().add(getClass().getResource("/Cart.css").toExternalForm());
               dialogPane.getStyleClass().add("dialog-pane");
               alert.showAndWait();
           }else {
               // i am using a formatter to get a string from the date picker with a specific format that i shoose
               if(!observableListDateReport.isEmpty()) {
                   observableListDateReport.clear();
               }
               DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
               LocalDate from = fromPicker.getValue();
               fromDate = formatter.format(from);
               LocalDate to = toPicker.getValue();
                toDate = formatter.format(to);
               getDateReportData(observableListDateReport, fromDate, toDate);// this method will interract with the database and return the resultset as a observable list holding the data of all the items in the report table
               reportTableView.setItems(observableListDateReport);
          
           }


       });

        secondTopBox.getChildren().addAll(fromlabel,fromPicker,toLabel,toPicker,goButton);




       HBox buttomBox = new HBox();
       buttomBox.getChildren().addAll(returnButton,printButton);
       VBox layout = new VBox();
       layout.setStyle("-fx-background-color:darkblue;");
       layout.getChildren().addAll(topBox,secondTopBox,reportTableView,buttomBox);
       Scene scene = new Scene(layout,515,480);
        scene.getStylesheets().add(getClass().getResource("/Cart.css").toExternalForm());

        returnButton.setOnAction( e -> {
         stage.setScene(scene1);
         stage.show();
      });
        printButton.setOnAction( e -> {
            if(observableListDateReport.isEmpty()){
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setHeaderText(null);
                alert.setContentText("No information in table to print!!");
                DialogPane dialogPane = alert.getDialogPane();
                dialogPane.getStylesheets().add(getClass().getResource("/Cart.css").toExternalForm());
                dialogPane.getStyleClass().add("dialog-pane");
                alert.showAndWait();
            }else {
                printReport(observableListDateReport,fromDate,toDate);
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setHeaderText("confirm");
                alert.setContentText("Report successfully printed check the file on your pc");
                DialogPane dialogPane = alert.getDialogPane();
                dialogPane.getStylesheets().add(getClass().getResource("/Cart.css").toExternalForm());
                dialogPane.getStyleClass().add("dialogBlue");
                alert.showAndWait();
            }
        });
    stage.setScene(scene);
    stage.show();

    }


    public void  printReport(ObservableList<Report> observableList ,String from , String to){
      StringBuilder reportResult = new StringBuilder();
       for(Report r: observableList) {
            reportResult.append("TYPE : ").append(r.getType()).append("/ NAME : ").append(r.getName()).append("/ PRICE : ").append(r.getPrice()).append("/ ID : ").append(r.getId()).append("/ QUANTITY SOLD: ").append(r.getQuantitySold()).append("\r\n");
       }

     String ans = "this is a report of sold items from: " +from +" to: "+to+ "\r\n"+ reportResult;
      // now i will print this result to the file on my pc
     // using the fileWriter method


      try (FileWriter f = new FileWriter("/users/Toshiba/Desktop/report.txt", true);
           BufferedWriter b = new BufferedWriter(f);
           PrintWriter p = new PrintWriter(b);) {
          p.println(ans);

      } catch (IOException i) {
          i.printStackTrace();
      }

  }
  public void getDateReportData(ObservableList<Report> observableList ,String fromDate , String toDate){
        Connection connection = null;
        PreparedStatement viewStatement = null;
        PreparedStatement getData = null;
        ResultSet resultSet = null;
        PreparedStatement dropView = null;

      try {
          connection = DriverManager.getConnection(url,username,mypassword);
          viewStatement = connection.prepareStatement("CREATE VIEW t1 AS (\n" +
                  "    SELECT repId , SUM(repQuantity) AS sum\n" +
                  "FROM dateReport\n" +
                  "WHERE repDate BETWEEN ? AND ?\n" +
                  "GROUP BY repId \n" +
                  ");");
          viewStatement.setString(1,fromDate);
          viewStatement.setString(2,toDate);
          viewStatement.execute();
          // first step creating the view
          // now the query to get the data
          getData = connection.prepareStatement("SELECT type,name,price ,id , sum FROM items inner JOIN t1\n" +
                  "on items.id = t1.repId;");
          resultSet = getData.executeQuery();
          while (resultSet.next()){
              observableList.add(new Report(resultSet.getString(1),resultSet.getString(2),resultSet.getDouble(3),resultSet.getInt(4),resultSet.getInt(5)));
          }// now that i added the result to the observable list i can close the table t1 so when i use the go method again it will not make a problem when i open another table t1
          dropView = connection.prepareStatement("DROP VIEW  t1;");
          dropView.execute();



      } catch (SQLException e) {
          e.printStackTrace();
      }finally {
          if(viewStatement != null){
              try {
                  viewStatement.close();
              } catch (SQLException e) {
                  e.printStackTrace();
              }
          }if(getData != null){
              try {
                  getData.close();
              } catch (SQLException e) {
                  e.printStackTrace();
              }
          }if(resultSet != null){
              try {
                  resultSet.close();
              } catch (SQLException e) {
                  e.printStackTrace();
              }
          }if(dropView != null){
              try {
                  dropView.close();
              } catch (SQLException e) {
                  e.printStackTrace();
              }
          }
          if(connection != null){
              try {
                  connection.close();
              } catch (SQLException e) {
                  e.printStackTrace();
              }
          }
      }

  }
    public static void main(String[] args) {

        launch();
    }

}



//

// learn anchor pane




