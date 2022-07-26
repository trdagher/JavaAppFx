package com.example.loginform;

import javafx.animation.FadeTransition;
import javafx.animation.Interpolator;
import javafx.animation.TranslateTransition;
import javafx.application.Application;
import javafx.beans.binding.Bindings;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.css.Style;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.paint.CycleMethod;
import javafx.scene.paint.LinearGradient;
import javafx.scene.paint.Stop;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.sql.*;
import java.text.NumberFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Main extends Application {
   private static Stage window;
   private static TextField startWindowUserNameInput;
   private static TextField signUpPassWordInput;
   private static  TextField startWindowPassInput;


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

       labelApp.setFont(new Font("Sans Serif",40));
        Stop[] stops = new Stop[] { new Stop(0, Color.ORANGE), new Stop(1, Color.RED)};
        LinearGradient lg1 = new LinearGradient(0, 0, 1, 0, true, CycleMethod.NO_CYCLE, stops);
       labelApp.setTextFill(lg1);
        topMidBox.getChildren().add(labelApp);

        HBox topleftBox = new HBox();
        // one hbox to hold 2 hboxes
        topBox.setStyle(" -fx-background-color: linear-gradient(from 25% 25% to 100% 100%, #040303, #30030a);");
        Label label = new Label("welcome "+name);
        label.setAlignment(Pos.CENTER_LEFT);
        label.setPadding(new Insets(24,0,24,10));
        label.setFont(new Font("Sans Serif",23));
      label.setStyle("-fx-font-style: italic;");
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

        // laptop 3
        InputStream inputStream3 = null;
        try {
            inputStream3 = new FileInputStream("src/main/java/Images/laptop3.PNG");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        Image lap3 = new Image(inputStream3);
        ImageView lap3View = new ImageView(lap3);
        // laptop4

        InputStream inputStream4 = null;
        try {
            inputStream4 = new FileInputStream("src/main/java/Images/laptop4.PNG");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        Image lap4 = new Image(inputStream4);
        ImageView lap4View = new ImageView(lap4);

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
           buyItem(101);
       });
       Button lap2Button = new Button("buy now");
        lap2Button.setOnAction( e -> {
            buyItem(102);
        });
        Button lap3Button = new Button("buy now");
        lap3Button.setOnAction( e -> {
            buyItem(103);
        });
        Button lap4Button = new Button("buy now");
        lap4Button.setOnAction( e -> {
            buyItem(104);
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
        System.out.println("Tile pressed ");
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

        // ipad 3
        InputStream inputStream7 = null;
        try {
            inputStream7 = new FileInputStream("src/main/java/Images/ipad3.PNG");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        Image ipad3 = new Image(inputStream7);
        ImageView ipad3View = new ImageView(ipad3);
        // ipad 4

        InputStream inputStream8 = null;
        try {
            inputStream8 = new FileInputStream("src/main/java/Images/ipad4.PNG");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        Image ipad4 = new Image(inputStream8);
        ImageView ipad4View = new ImageView(ipad4);

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
            buyItem(105);
        });
        Button ipad2Button = new Button("buy now");
        ipad2Button.setOnAction( e -> {
            buyItem(106);
        });
        Button ipad3Button = new Button("buy now");
        ipad3Button.setOnAction( e -> {
            buyItem(107);
        });
        Button ipad4Button = new Button("buy now");
        ipad4Button.setOnAction( e -> {
            buyItem(108);
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
         window.setScene(scene);
        window.show();

    }

    // this function will be called from the buy buttons on the items
    public void buyItem(int id){
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
        layout.addRow(0,nameLabel,nameInput);
        layout.addRow(1,cityLabel,cityInput);
        layout.addRow(2,dateLabel,datePicker);
        layout.addRow(3,emailLabel,emailInput);
        layout.addRow(4,cartNumLabel,cartNumInput);
        layout.addRow(5,pinCodeLabel,pinCodeInput);
        HBox buttons = new HBox();
        buttons.setAlignment(Pos.CENTER);
        buttons.setSpacing(10);
        buttons.getChildren().addAll(button1,button2);
        layout.setHgap(20);
        layout.setVgap(30);
        layout.setAlignment(Pos.CENTER);
        VBox box = new VBox();
        box.setSpacing(30);
        box.setAlignment(Pos.CENTER);
        box.getChildren().addAll(layout,buttons);

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

        Scene scene = new Scene(box,400,600);
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
            alert.showAndWait();

        }
        return false;
    }
    // HERE I AM ACCESSING THE DATABASE and updating the quantity of an item when we click buy on it
     public  void  buyItemButtonClicked(int id){
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
              change = connection.prepareStatement("UPDATE items SET quantity = quantity -1 WHERE id = ?;");
              change.setString(1, String.valueOf(id));
              change.executeUpdate();
          }else{
              Alert alert = new Alert(Alert.AlertType.WARNING);
              alert.setTitle("not available");
              alert.setHeaderText(null);
              alert.setContentText("item is not available in stock!!");
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
     public void imageClicked(String type , String name , double price,int id,String userName){
       boolean ans = ConfirmBox.display("add to cart","do you want to add this item to cart?");
       if(ans){
           Connection connection = null;
           PreparedStatement insert = null;
           try {
               connection = DriverManager.getConnection(url,userName,mypassword);
              insert = connection.prepareStatement("INSERT INTO cart VALUES ? ? ? ? ?");
              insert.setString(1,type);
              insert.setString(2,name);
              insert.setDouble(3,price);
              insert.setInt(4,id);
              insert.setString(5,userName);
              insert.executeUpdate();
              // continu the connection here

           } catch (SQLException e) {
               e.printStackTrace();
           }

       }

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



// set a baclground image


//        FileInputStream inputStream2 = null;
//        try {
//            inputStream2 = new FileInputStream("src/main/java/Images/background3.jpg");
//        } catch (FileNotFoundException e) {
//            e.printStackTrace();
//        }
//        Image image = new Image(inputStream2);
//            BackgroundImage myBI= new BackgroundImage(image,BackgroundRepeat.NO_REPEAT,BackgroundRepeat.NO_REPEAT,BackgroundPosition.DEFAULT,BackgroundSize.DEFAULT);
//            midBox.setBackground(new Background(myBI));