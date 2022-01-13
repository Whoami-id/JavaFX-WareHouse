
package aleon;

import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;

import i18n.I18nUISupport;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.Separator;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.StringConverter;

public class Stocktaking {
    private final I18nUISupport i18nui = I18nUISupport.getInstance();
    /**
     * creates ChoiceBox, Spinner and DataPcker.
     */
    ChoiceBox<Product> choiceBox = new ChoiceBox<>();
    Spinner<Integer> spinnerCount = new Spinner<>();
    DatePicker datePicker = new DatePicker();

    /**
     * includes the entire stage and start it
     *
     * @param primaryStage the entire stage
     */
    public void start(final Stage primaryStage) {

        // creates BorderPane, Scene, Label and Separator.

        final BorderPane root = new BorderPane();
        final Scene scene = new Scene(root, 530, 640);

        final Label label = i18nui.newLabel("inventory");
        label.setFont(new Font("Arial", 20));
        label.setStyle("-fx-font-weight: Bold");

        final Separator separator = new Separator();
        separator.setPrefWidth(530);

        // crates a VBox and add the elements to it.

        final VBox vBoxTop = new VBox();
        vBoxTop.getChildren().addAll(label, separator);
        root.setTop(vBoxTop);
        vBoxTop.setAlignment(Pos.CENTER);
        vBoxTop.setPadding(new Insets(30, 0, 0, 0));

        // Creates a GridPane and position it.

        final GridPane gridPane = new GridPane();
        root.setCenter(gridPane);
        gridPane.setAlignment(Pos.TOP_CENTER);
        gridPane.setPadding(new Insets(100, 0, 0, 0));
        gridPane.setVgap(60);
        gridPane.setHgap(110);

        // crates few Text.

        final Text textProduct = i18nui.newText("product");
        final Text textCount = i18nui.newText("count");
        final Text textDate = i18nui.newText("date");
        final Text textTime = i18nui.newText("time");
        textProduct.setFont(new Font("System, Weight.Font: Bold", 20));
        textCount.setFont(new Font("System, Weight.Font: Bold", 20));
        textDate.setFont(new Font("System, Weight.Font: Bold", 20));
        textTime.setFont(new Font("System, Weight.Font: Bold", 20));

        // creates Spinner for the hour, minute, second.

        final Spinner<Integer> spinnerHour = new Spinner<>();
        final Spinner<Integer> spinnerMinute = new Spinner<>();
        final Spinner<Integer> spinnerSecond = new Spinner<>();
        spinnerHour.setPrefWidth(55);
        spinnerMinute.setPrefWidth(55);
        spinnerSecond.setPrefWidth(55);

        final LocalTime now = LocalTime.now();
        final int localHour = now.getHour();
        final int localMinute = now.getMinute();
        final int localSecond = now.getSecond();

        final SpinnerValueFactory<Integer> valuFactroyHour = new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 23);
        spinnerHour.setValueFactory(valuFactroyHour);
        spinnerHour.getValueFactory().setValue(localHour);

        final SpinnerValueFactory<Integer> valuFactroyMinute = new SpinnerValueFactory.IntegerSpinnerValueFactory(1,
                59);
        spinnerMinute.setValueFactory(valuFactroyMinute);
        spinnerMinute.getValueFactory().setValue(localMinute);

        final SpinnerValueFactory<Integer> valuFactroySecond = new SpinnerValueFactory.IntegerSpinnerValueFactory(1,
                59);
        spinnerSecond.setValueFactory(valuFactroySecond);
        spinnerSecond.getValueFactory().setValue(localSecond);

        // creates HBox and add the Spinners.

        final HBox hBoxTime = new HBox();
        hBoxTime.getChildren().addAll(spinnerHour, spinnerMinute, spinnerSecond);

        // adds all the elements to gridPane.

        gridPane.add(textProduct, 0, 0);
        gridPane.add(textCount, 0, 1);
        gridPane.add(textDate, 0, 2);
        gridPane.add(textTime, 0, 3);
        gridPane.add(choiceBox, 1, 0);
        gridPane.add(spinnerCount, 1, 1);
        gridPane.add(datePicker, 1, 2);
        gridPane.add(hBoxTime, 1, 3);

        updateData();

        choiceBox.setPrefSize(160, 30);

        // adjusts the spinner.

        spinnerCount.setPrefSize(160, 30);
        final SpinnerValueFactory<Integer> valueFactoryCount = new SpinnerValueFactory.IntegerSpinnerValueFactory(0,
                100, 1);
        spinnerCount.setValueFactory(valueFactoryCount);
        // spinnerCount.setEditable(true);
        datePicker.setValue(LocalDate.now());
        datePicker.setPrefSize(160, 30);

        // crates a HBox and Buttons, add Button in HBox.

        final HBox hBoxBottom = new HBox();
        final Button buttonAdd = i18nui.newButton("add");
        final Button buttonBack = i18nui.newButton("back");
        hBoxBottom.getChildren().addAll(buttonAdd, buttonBack);
        hBoxBottom.setSpacing(120);
        root.setBottom(hBoxBottom);
        hBoxBottom.setPadding(new Insets(0, 0, 40, 0));
        hBoxBottom.setAlignment(Pos.BOTTOM_CENTER);

        buttonAdd.setPrefSize(100, 30);
        buttonBack.setPrefSize(100, 30);

        // shows primaryStage.
        primaryStage.setScene(scene);
        primaryStage.show();

        /**
         * adds Products.
         */
        buttonAdd.setOnAction(event -> {

            // gets the local dateTime.
            final LocalDateTime localDateTime = datePicker.getValue().atTime(spinnerHour.getValue(),
                    spinnerMinute.getValue(), spinnerSecond.getValue());
            final ZonedDateTime zdt = ZonedDateTime.of(localDateTime, ZoneId.systemDefault());
            final long milis = zdt.toInstant().toEpochMilli();
            final Product choice = choiceBox.getValue();
            final int count = spinnerCount.getValue();
            final int id = choice.getId();
            final DBStatement dbStatement = new DBStatement();

            // tests if adding Products works.
            boolean success;

            try {

                dbStatement.insertData(milis, count, id);
                success = true;

            } catch (final SQLException ex) {
                success = false;

            }

            final Stage stageSuccess = new Stage();

            final BorderPane borderPaneSuccess = new BorderPane();
            final Scene sceneSuccess = new Scene(borderPaneSuccess, 250, 250);
            // Crates labels and Button.
            Label labelInfo = new Label();
            labelInfo = i18nui.newLabel("info");
            labelInfo.setFont(new Font("Arial", 18));
            labelInfo.setStyle("-fx-font-weight: Bold");

            Label labelMessage = new Label();
            labelMessage.setFont(new Font("Arial", 12));
            labelMessage.setStyle("-fx-font-weight: Bold");
            if (success) {
                labelMessage = i18nui.newLabel("product_was_added_successfully");
                labelMessage.setTextFill(Color.web("#158d27"));

            } else if (milis == milis) {
                labelMessage = i18nui.newLabel("you_cannot_add_at_the_same_time");
                labelMessage.setTextFill(Color.web("#ff0423"));
            }

            final Button buttonSuccess = i18nui.newButton("Ok");
            buttonSuccess.setPrefSize(80, 30);
            // Creates a VBox and add the element.
            final VBox vBoxSuccess = new VBox();
            vBoxSuccess.getChildren().addAll(labelInfo, labelMessage, buttonSuccess);
            borderPaneSuccess.setCenter(vBoxSuccess);
            vBoxSuccess.setAlignment(Pos.CENTER);
            vBoxSuccess.setSpacing(70);
            /*
             * closes the Info Windows.
             */
            buttonSuccess.setOnAction(event1 -> stageSuccess.close());

            stageSuccess.setScene(sceneSuccess);
            stageSuccess.initModality(Modality.APPLICATION_MODAL);
            stageSuccess.initOwner(primaryStage);
            stageSuccess.showAndWait();

        });

        // buttonBack.setOnMouseClicked(event -> {
        // final Button buttonTemp2 = (Button) event.getSource();
        // final Stage primaryStage2 = (Stage) buttonTemp2.getScene().getWindow();
        // primaryStage2.close();
        // final Stage stageTemp = new Stage();
        // final MainMenu mainMenu = new MainMenu();
        // try {
        // mainMenu.start(stageTemp);
        // } catch (final Exception ex) {
        // ex.printStackTrace();
        //
        // }
        //
        // stageTemp.show();
        //
        // });

        /**
         * goes back to main Menu.
         */
        buttonBack.setOnAction(event -> {
            final MainMenu mainMenu = new MainMenu();
            try {
                mainMenu.start(primaryStage);
            } catch (final Exception ex) {

                ex.printStackTrace();
            }
            primaryStage.show();

        });

    }

    /**
     * adds the Products to the choiceBox.
     */
    private void updateData() {
        final DBStatement dbStatement = new DBStatement();
        try {
            final ObservableList<Product> products = FXCollections.observableArrayList(dbStatement.getProducts());

            // final Map<String, Product> map = new HashMap<>();
            // products.forEach(product -> map.put(product.getName(), product));

            choiceBox.setConverter(new StringConverter<Product>() {

                @Override
                public String toString(final Product object) {
                    return object.getName();
                }

                @Override
                public Product fromString(final String name) {
                    for (final Product product : products) {
                        if (product.getName().equals(name)) {
                            return product;
                        }
                    }
                    return null;
                    // return map.get(name);
                }
            });

            // Sets the products as items for the choice box.
            choiceBox.setItems(products);

        } catch (final SQLException ex) {
            ex.printStackTrace();
        }
        choiceBox.getSelectionModel().select(0);
        choiceBox.setTooltip(i18nui.newTooltip("select_product"));

    }

}
