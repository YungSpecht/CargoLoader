package CargoLoader;

import javafx.application.*;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
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
import java.util.ArrayList;

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

    private double AnchorX, AnchorY;
    private double anchorAngleX = 0;
    private double anchorAngleY = 0;
    private final DoubleProperty angleX = new SimpleDoubleProperty(0);
    private final DoubleProperty angleY = new SimpleDoubleProperty(0);

    public SmartGroup group1 = new SmartGroup();
    public SmartGroup group2 = new SmartGroup();
    public SmartGroup group3 = new SmartGroup();
    SmartGroup group = new SmartGroup();

    public ToggleGroup colorOption = new ToggleGroup();
    public ToggleGroup groupOption = new ToggleGroup();

    ArrayList<SmartGroup> allGroups = new ArrayList<SmartGroup>();
    ArrayList<Box> allBoxes = new ArrayList<Box>();
    ArrayList<PhongMaterial> allMaterials = new ArrayList<PhongMaterial>();

    public static void call(int[][][] in) {
        endMatrix = in;
        launch();
    }

    public void start(Stage primaryStage) throws Exception {

        Group endGroup = MakeBigCube(endMatrix);

        group.getChildren().addAll(endGroup);

        allGroups.add(group);

        VBox vbox = makeVBox();

        Group total = new Group();
        total.getChildren().add(vbox);
        total.getChildren().add(group);

        Scene scene = new Scene(total, WIDTH, HEIGHT, true);
        scene.setFill(Color.SILVER);
        Camera camera = new PerspectiveCamera();
        scene.setCamera(camera);

        group.translateXProperty().set(WIDTH / 2);
        group.translateYProperty().set(HEIGHT / 2);
        group.translateZProperty().set(-500);

        moveBlock(primaryStage);

        initMouseControl(group, scene, primaryStage);

        colorOption.selectedToggleProperty().addListener(new ChangeListener<Toggle>() {
            public void changed(ObservableValue<? extends Toggle> ov,
                    Toggle old_toggle, Toggle new_toggle) {
                if (colorOption.getSelectedToggle() != null) {
                    changeColor(Integer.parseInt(colorOption.getSelectedToggle().getUserData().toString()));
                }
            }
        });

        primaryStage.setTitle("ENDPRODUCT");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

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

    public void relocate() {

        for (int i = 0; i < allGroups.size() - 1; i++) {
            allGroups.get(i).translateXProperty().set(0);
            allGroups.get(i).translateYProperty().set(0);
            allGroups.get(i).translateZProperty().set(0);
        }

        group.translateXProperty().set(WIDTH / 2);
        group.translateYProperty().set(HEIGHT / 2);
        group.translateZProperty().set(-250);

    }

    public void splitUp() {
        group1.translateZProperty().set(cubeSize * depth );
        group3.translateZProperty().set(-cubeSize * depth * 1.3);
    }

    public VBox makeVBox() {
        VBox vbox = new VBox();
        HBox hbox = makeHBox();
        RadioButton box1 = new RadioButton("Color");
        box1.setUserData(0);
        box1.setSelected(true);
        RadioButton box2 = new RadioButton("No Color");
        box2.setUserData(1);
        RadioButton box3 = new RadioButton("Piece A");
        box3.setUserData(0);
        RadioButton box4 = new RadioButton("Piece B");
        box4.setUserData(1);
        RadioButton box5 = new RadioButton("Piece C");
        box5.setUserData(2);
        RadioButton box6 = new RadioButton("Whole Box");
        box6.setUserData(3);
        box6.setSelected(true);

        box1.setToggleGroup(colorOption);
        box2.setToggleGroup(colorOption);
        box3.setToggleGroup(groupOption);
        box4.setToggleGroup(groupOption);
        box5.setToggleGroup(groupOption);
        box6.setToggleGroup(groupOption);

        vbox.getChildren().addAll(hbox, box1, box2, box3, box4, box5, box6);
        return vbox;
    }

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
        return hbox;
    }

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

    private void moveBlock(Stage stage) {
        stage.addEventHandler(KeyEvent.KEY_PRESSED, event -> {
            SelectedGroup = Integer.parseInt(groupOption.getSelectedToggle().getUserData().toString());
            switch (event.getCode()) {
                case A:
                    allGroups.get(SelectedGroup).translateXProperty()
                            .set(allGroups.get(SelectedGroup).getTranslateX() - 10);
                    break;
                case D:
                    allGroups.get(SelectedGroup).translateXProperty()
                            .set(allGroups.get(SelectedGroup).getTranslateX() + 10);
                    break;
                case S:
                    allGroups.get(SelectedGroup).translateYProperty()
                            .set(allGroups.get(SelectedGroup).getTranslateY() + 10);
                    break;
                case W:
                    allGroups.get(SelectedGroup).translateYProperty()
                            .set(allGroups.get(SelectedGroup).getTranslateY() - 10);
                    break;
                case Q:
                    allGroups.get(SelectedGroup).translateZProperty()
                            .set(allGroups.get(SelectedGroup).getTranslateZ() - 10);
                    break;
                case E:
                    allGroups.get(SelectedGroup).translateZProperty()
                            .set(allGroups.get(SelectedGroup).getTranslateZ() + 10);
                    break;
                default:
                    break;
            }
        });
    }

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