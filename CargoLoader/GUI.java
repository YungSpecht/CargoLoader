package CargoLoader;

import java.util.ArrayList;

import javafx.application.*;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Point3D;
import javafx.scene.*;
import javafx.scene.control.*;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.ScrollEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.*;
import javafx.scene.transform.Rotate;
import javafx.scene.transform.Transform;
import javafx.stage.*;

public class GUI extends Application {
    public static final int WIDTH = 1400;
    public static final int HEIGHT = 750;
    public static int cubeSize = 25;
    public static int gap = 0;

    public static int depth;
    public static int height;
    public static int width;

    public static int[][][] endMatrix;

    public static int depthMiddle;
    public static int heightMiddle;
    public static int widthMiddle;
    public static int boxesCreated;

    public static int SelectedGroup = 3;

    public static boolean showPopup = false;
    public static boolean partialCover = true;
    public static boolean exactCover = false;

    private double AnchorX, AnchorY;
    private double anchorAngleX = 0;
    private double anchorAngleY = 0;
    private final DoubleProperty angleX = new SimpleDoubleProperty(0);
    private final DoubleProperty angleY = new SimpleDoubleProperty(0);

    public SmartGroup group1 = new SmartGroup();
    public SmartGroup group2 = new SmartGroup();
    public SmartGroup group3 = new SmartGroup();
    SmartGroup group = new SmartGroup();
    public Group total = new Group();

    public static ComboBox<String> movementOptions;
    public static ComboBox<String> packingOptions;
    public static ComboBox<String> coverMode;

    public TextField textone = new TextField();
    public TextField texttwo = new TextField();
    public TextField textthree = new TextField();

    public Label value = new Label("Total value = ");
    public Label amount = new Label("Total used parcels = ");

    public static int[] values = new int[3];

    ArrayList<SmartGroup> allGroups = new ArrayList<SmartGroup>();
    ArrayList<Box> allBoxes = new ArrayList<Box>();
    ArrayList<PhongMaterial> allMaterials = new ArrayList<PhongMaterial>();

    public VBox mainVbox = makeVBox();

    public static void call() {
        launch();
    }

    // initiates scene, stage and camera
    public void start(Stage primaryStage) throws Exception {

        allGroups.add(group);

        total.getChildren().add(mainVbox);
        total.getChildren().add(group);

        Scene scene = new Scene(total, WIDTH, HEIGHT, true);
        scene.setFill(Color.SILVER);
        Camera camera = new PerspectiveCamera();
        scene.setCamera(camera);

        group.translateXProperty().set(WIDTH / 2);
        group.translateYProperty().set(HEIGHT / 2);
        group.translateZProperty().set(-250);

        moveBlock(primaryStage);

        initMouseControl(group, scene, primaryStage);

        primaryStage.setTitle("ENDPRODUCT");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    // changes the color of the box
    public void changeColor(int x) {
        if (x == 0) {
            for (int i = 0; i < allBoxes.size(); i++) {
                allBoxes.get(i).setMaterial(allMaterials.get(i));
            }
        } else if (x == 1) {
            PhongMaterial origin = new PhongMaterial();
            origin.setDiffuseColor(Color.AQUAMARINE);
            for (int i = 0; i < allBoxes.size(); i++) {
                allBoxes.get(i).setMaterial(origin);
            }
        }
    }

    // relocate the container to the middle of the screen
    public void relocate() {

        for (int i = 0; i < allGroups.size(); i++) {
            allGroups.get(i).translateXProperty().set(0);
            allGroups.get(i).translateYProperty().set(0);
            allGroups.get(i).translateZProperty().set(0);
            ;
        }

        group.translateXProperty().set(WIDTH / 2);
        group.translateYProperty().set(HEIGHT / 2);
        group.translateZProperty().set(-250);

    }

    // splits the container in to three groups of parcels
    public void splitUp() {
        group1.translateXProperty().set(cubeSize * width * 2);
        group3.translateXProperty().set(-cubeSize * width * 2);
    }

    // adds the info of the algorithm
    public void addInfo(int parcelAmount, int parcelValue) {
        value.setText("Total value = " + parcelValue);
        amount.setText("Total used parcels = " + parcelAmount);
    }

    // creates the VBox were the information will be displayed
    public VBox InformationVBox() {
        VBox vbox = new VBox();
        vbox.setPadding(new Insets(5, 5, 5, 5));
        vbox.setBackground(new Background(new BackgroundFill(Color.DARKGRAY, CornerRadii.EMPTY, Insets.EMPTY)));
        vbox.setMaxWidth(195);
        vbox.setSpacing(10);
        vbox.getChildren().addAll(amount, value);
        return vbox;
    }

    // construct the main VBox that consists of multiple HBox's and vbox
    public VBox makeVBox() {
        VBox vbox = new VBox();
        HBox hbox = makeHBox();
        VBox info = InformationVBox();

        Label labelOne = new Label("Amount of piece A ");
        Label labeltwo = new Label("Amount of piece B ");
        Label labelThree = new Label("Amoutn of piece C ");

        HBox hbox1 = smallHbox(labelOne, textone);
        HBox hbox2 = smallHbox(labeltwo, texttwo);
        HBox hbox3 = smallHbox(labelThree, textthree);

        textone.setMaxWidth(50);
        texttwo.setMaxWidth(50);
        textthree.setMaxWidth(50);

        vbox.getChildren().addAll(hbox, hbox1, hbox2, hbox3, info);
        return vbox;
    }

    // makes a Horizontal box to were you can enter the amount of each piece
    public HBox smallHbox(Label label, TextField textfield) {
        HBox hbox = new HBox();
        hbox.setPadding(new Insets(5, 5, 5, 5));
        hbox.setSpacing(10);
        hbox.setBackground(new Background(new BackgroundFill(Color.DARKGRAY, CornerRadii.EMPTY, Insets.EMPTY)));
        hbox.setMaxWidth(195);
        hbox.getChildren().addAll(label, textfield);
        return hbox;
    }

    // makes the comboBox for the color options
    public ComboBox<String> ColorBox() {
        String[] options = { "Color", "No color" };
        ComboBox<String> colors = new ComboBox<String>(FXCollections.observableArrayList(options));
        colors.getSelectionModel().selectFirst();

        // adds a eventhandler to act when a different options is choosen
        EventHandler<ActionEvent> event = new EventHandler<ActionEvent>() {
            public void handle(ActionEvent e) {
                String str = colors.getValue();
                if (str.equals("Color")) {
                    changeColor(0);
                } else
                    changeColor(1);
            }
        };
        colors.setOnAction(event);
        return colors;

    }

    // makes the combobox for picken parcel or pentomino
    public ComboBox<String> chooseBox() {
        String[] options = { "Parcels", "Pentominoes" };
        ComboBox<String> packing = new ComboBox<String>(FXCollections.observableArrayList(options));
        packing.getSelectionModel().selectFirst();
        return packing;
    }

    // makes the combobox of choosing which part will be moving
    public ComboBox<String> moveBox() {
        String[] options = { "Container", "Piece A", "Piece B", "Piece C" };
        ComboBox<String> moveoptions = new ComboBox<String>(FXCollections.observableArrayList(options));
        moveoptions.getSelectionModel().selectFirst();
        return moveoptions;
    }

    // makes the comboboc of partial cover or exact cover
    public ComboBox<String> coverBox() {
        String[] options = { "Partial cover", "Exact cover" };
        ComboBox<String> coverOptions = new ComboBox<String>(FXCollections.observableArrayList(options));
        coverOptions.getSelectionModel().selectFirst();

        // adds a eventhandler for when there is activity on this combobox
        EventHandler<ActionEvent> event = new EventHandler<ActionEvent>() {
            public void handle(ActionEvent e) {
                String str = coverMode.getValue();
                if (str.equals("Partial cover")) {
                    partialCover = true;
                    exactCover = false;
                    System.out.println("partialCover");
                } else {
                    partialCover = false;
                    exactCover = true;
                    System.out.println("exactCover");
                }
            }
        };
        coverOptions.setOnAction(event);
        return coverOptions;
    }

    // return the amount of
    public static int[] getvalues() {
        return values;
    }

    // will be activated when the run Program button is clicked,
    // it will call the algotihm and it will call a method to draw the container
    public void RUN() {
        group1.getChildren().clear();
        group2.getChildren().clear();
        group3.getChildren().clear();
        relocate();
        Group endGroup = new Group();
        Program.finalAmount = 0;
        Program.finalValue = 0;
        if (packingOptions.getValue().equals("Parcels")) {

            values[0] = Integer.parseInt(textone.getText());
            values[1] = Integer.parseInt(texttwo.getText());
            values[2] = Integer.parseInt(textthree.getText());

            Program.parcelAmounts = getvalues();

            Program.parcelMode = 'p';

            if (partialCover) {
                Program.coverMode = 'p';
                endMatrix = Program.solveBox();
                addInfo(Program.finalAmount, Program.finalValue);
                // endMatrix = ...
                // addinfo(parcelAmount, totalValue)

            } else if (exactCover) {
                Program.coverMode = 'e';
                endMatrix = new int[33][8][5];
                addInfo(0, 0);
                // endMatrix = ...
                // addinfo(parcelAmount, totalValue)
            }

        } else if (packingOptions.getValue().equals("Pentominoes")) {

            values[0] = Integer.parseInt(textone.getText());
            values[1] = Integer.parseInt(texttwo.getText());
            values[2] = Integer.parseInt(textthree.getText());

            Program.parcelAmounts = getvalues();

            if (partialCover) {
                Program.coverMode = 'p';
                endMatrix = Program.solvePento();
                addInfo(Program.finalAmount, Program.finalValue);
                // endMatrix = ...
                // addinfo(parcelAmount, totalValue)

            } else if (exactCover) {
                Program.coverMode = 'e';
                endMatrix = Program.solvePento();
                
                for(int i = 0; i < endMatrix[0].length; i++){
                    for(int j = 0; j < endMatrix[0][0].length; j++){
                        for(int k = 0; k < endMatrix.length; k++){
                            System.out.print(endMatrix[k][i][j]);
                        }
                        System.out.println();
                    }
                    System.out.println();
                }

                addInfo(Program.finalAmount, Program.finalValue);
                // endMatrix = ...
                // addinfo(parcelAmount, totalValue)
            }
        }

        endGroup = MakeBigCube(endMatrix);
        group.getChildren().add(endGroup);
    }

    // makes the top horizontal box with all the buttons and comboboxes
    public HBox makeHBox() {
        HBox hbox = new HBox();
        hbox.setSpacing(10);
        hbox.setPadding(new Insets(10, 25, 10, 5));

        Button buttonRelocate = new Button("Relocate");
        hbox.getChildren().add(buttonRelocate);

        buttonRelocate.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent e) {
                relocate();
            }
        });

        Button buttonSeparate = new Button("Separate");
        hbox.getChildren().add(buttonSeparate);
        buttonSeparate.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent e) {
                splitUp();
            }
        });

        Button buttonActivate = new Button("Run Program");
        hbox.getChildren().add(buttonActivate);
        buttonActivate.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent e) {
                RUN();
            }
        });
        packingOptions = chooseBox();
        hbox.getChildren().add(packingOptions);
        coverMode = coverBox();
        hbox.getChildren().add(coverMode);
        ComboBox<String> colorss = ColorBox();
        hbox.getChildren().add(colorss);
        movementOptions = moveBox();
        hbox.getChildren().add(movementOptions);
        hbox.setBackground(new Background(new BackgroundFill(Color.DARKGRAY, CornerRadii.EMPTY, Insets.EMPTY)));
        return hbox;
    }

    // this method goes through the 3d array that has been created and gives order
    // where to draw wich box
    public Group MakeBigCube(int[][][] in) {
        Group group = new Group();

        depth = in.length;
        height = in[0].length;
        width = in[0][0].length;

        depthMiddle = (int) depth / 2;
        for (int i = 0; i < depth; i++) {
            heightMiddle = (int) -height / 2;
            for (int j = 0; j < height; j++) {
                widthMiddle = (int) -width / 2;
                for (int k = 0; k < width; k++) {
                    if (in[i][j][k] != 0) {
                        Box box = createBox(i, j, k, in[i][j][k]);
                        switch (in[i][j][k]) {
                            case 1:
                                group1.getChildren().addAll(box);
                                break;
                            case 2:
                                group2.getChildren().addAll(box);
                                break;
                            case 3:
                                group3.getChildren().addAll(box);
                                break;
                            default:
                                break;
                        }
                    }
                    widthMiddle++;
                }
                heightMiddle++;
            }
            depthMiddle--;
        }
        allGroups.add(group1);
        allGroups.add(group2);
        allGroups.add(group3);
        group.getChildren().addAll(group1, group2, group3);
        return group;
    }

    // this method draws the box on comman of the previous method
    Box createBox(int i, int j, int k, int l) {
        int xOff = widthMiddle * (cubeSize + gap);
        int yOff = heightMiddle * (cubeSize + gap);
        int zOff = depthMiddle * (cubeSize + gap);
        PhongMaterial blue = new PhongMaterial();
        blue.setDiffuseColor(Color.NAVY);
        PhongMaterial red = new PhongMaterial();
        red.setDiffuseColor(Color.DARKRED);
        PhongMaterial yellow = new PhongMaterial();
        yellow.setDiffuseColor(Color.GOLDENROD);
        Box make = new Box(cubeSize, cubeSize, cubeSize);
        make.translateXProperty().set(xOff);
        make.translateYProperty().set(yOff);
        make.translateZProperty().set(zOff);
        switch (l) {
            case 1:
                make.setMaterial(blue);
                allMaterials.add(blue);
                break;
            case 2:
                make.setMaterial(red);
                allMaterials.add(red);
                break;
            case 3:
                make.setMaterial(yellow);
                allMaterials.add(yellow);
                break;
            default:
                break;
        }
        allBoxes.add(make);
        boxesCreated += 1;
        return make;
    }

    // this method installs an eventhandler for when a key is pressed so the
    // container can move
    private void moveBlock(Stage stage) {
        stage.addEventHandler(KeyEvent.KEY_PRESSED, event -> {
            String str = movementOptions.getValue();
            if (str.equals("Container"))
                SelectedGroup = 0;
            else if (str.equals("Piece A"))
                SelectedGroup = 1;
            else if (str.equals("Piece B"))
                SelectedGroup = 2;
            else if (str.equals("Piece C"))
                SelectedGroup = 3;
            switch (event.getCode()) {
                case A:
                    allGroups.get(SelectedGroup).translateXProperty()
                            .set(allGroups.get(SelectedGroup).getTranslateX() - (cubeSize + gap));
                    break;
                case D:
                    allGroups.get(SelectedGroup).translateXProperty()
                            .set(allGroups.get(SelectedGroup).getTranslateX() + (cubeSize + gap));
                    break;
                case S:
                    allGroups.get(SelectedGroup).translateYProperty()
                            .set(allGroups.get(SelectedGroup).getTranslateY() + (cubeSize + gap));
                    break;
                case W:
                    allGroups.get(SelectedGroup).translateYProperty()
                            .set(allGroups.get(SelectedGroup).getTranslateY() - (cubeSize + gap));
                    break;
                case Q:
                    allGroups.get(SelectedGroup).translateZProperty()
                            .set(allGroups.get(SelectedGroup).getTranslateZ() - (cubeSize + gap));
                    break;
                case E:
                    allGroups.get(SelectedGroup).translateZProperty()
                            .set(allGroups.get(SelectedGroup).getTranslateZ() + (cubeSize + gap));
                    break;
                default:
                    break;
            }
        });
    }

    // this method installs a mouse listener so you can move the block around with
    // your mouse
    private void initMouseControl(SmartGroup group, Scene scene, Stage stage) {
        Rotate xRotate;
        Rotate yRotate;
        group.getTransforms().addAll(
                xRotate = new Rotate(0, new Point3D(1, 0, 0)),
                yRotate = new Rotate(0, new Point3D(0, 1, 0)));
        xRotate.angleProperty().bind(angleX);
        yRotate.angleProperty().bind(angleY);

        scene.setOnMousePressed(event -> {
            AnchorX = event.getSceneX();
            AnchorY = event.getSceneY();
            anchorAngleX = angleX.get();
            anchorAngleY = angleY.get();
        });

        scene.setOnMouseDragged(event -> {
            angleX.set(anchorAngleX - (AnchorY - event.getSceneY()));
            angleY.set(anchorAngleY + (AnchorX - event.getSceneX()));

        });

        stage.addEventHandler(ScrollEvent.SCROLL, event -> {
            double movement = event.getDeltaY();
            if (movement > 0)
                group.translateZProperty().set(group.getTranslateZ() - 30);
            else if (movement < 0)
                group.translateZProperty().set(group.getTranslateZ() + 30);

        });
    }

    // this makes a group able to turn in every direction
    class SmartGroup extends Group {
        Rotate r;
        Transform t = new Rotate();

        void rotateByX(int ang) {
            r = new Rotate(ang, Rotate.X_AXIS);
            t = t.createConcatenation(r);
            this.getTransforms().clear();
            this.getTransforms().addAll(t);

        }

        void rotateByY(int ang) {
            r = new Rotate(ang, Rotate.Y_AXIS);
            t = t.createConcatenation(r);
            this.getTransforms().clear();
            this.getTransforms().addAll(t);
        }

    }

}