
package aleon;

import java.sql.SQLException;

import i18n.I18nUISupport;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Separator;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class NewProduct {

    private final I18nUISupport i18nui = I18nUISupport.getInstance();

    /**
     * includes the entire stage and start it
     *
     * @param primaryStage the entire stage
     */
    public void start(final Stage primaryStage) {

        // Creates Stage, Scene and BorderPane.
        final BorderPane root = new BorderPane();
        final Scene scene = new Scene(root, 530, 640);

        // Creates Label and Separator for heading.
        final Label label = i18nui.newLabel("add_new_products");
        label.setFont(new Font("Arial", 20));
        label.setStyle("-fx-font-weight: Bold");

        final Separator separator = new Separator();
        separator.setPrefWidth(530);

        // Creates a VBox and add label and separator in to.
        final VBox vBoxTop = new VBox();
        vBoxTop.getChildren().addAll(label, separator);
        root.setTop(vBoxTop);
        vBoxTop.setAlignment(Pos.CENTER);
        vBoxTop.setPadding(new Insets(30, 0, 0, 0));

        // Creates a GridPana.
        final GridPane gridPane = new GridPane();
        root.setCenter(gridPane);
        gridPane.setAlignment(Pos.TOP_CENTER);
        gridPane.setPadding(new Insets(100, 0, 0, 0));
        gridPane.setVgap(60);
        gridPane.setHgap(110);

        // Crates texts.
        final Text textName = i18nui.newText("name");
        final Text textMinimumQuantity = i18nui.newText("maximum_quantity");
        final Text textMaximumQuantity = i18nui.newText("minimal_quantity");
        textName.setFont(new Font("System, Weight.Font: Bold", 20));
        textMinimumQuantity.setFont(new Font("System, Weight.Font: Bold", 20));
        textMaximumQuantity.setFont(new Font("System, Weight.Font: Bold", 20));

        // Creates TextField.
        final TextField textFieldName = new TextField();
        textFieldName.setPrefSize(150, 30);

        // Creates Spinner for Quantity.
        final Spinner<Integer> spinnerMinimumQuantitye = new Spinner<>();
        final Spinner<Integer> spinnerMaximumQuantity = new Spinner<>();
        spinnerMinimumQuantitye.setPrefSize(150, 30);
        spinnerMaximumQuantity.setPrefSize(150, 30);
        spinnerMinimumQuantitye.setEditable(true);
        spinnerMaximumQuantity.setEditable(true);

        final SpinnerValueFactory<Integer> valueFactroyMinimum = new SpinnerValueFactory.IntegerSpinnerValueFactory(1,
                100);
        spinnerMinimumQuantitye.setValueFactory(valueFactroyMinimum);

        final SpinnerValueFactory<Integer> valueFctoryMaximum = new SpinnerValueFactory.IntegerSpinnerValueFactory(1,
                100);
        spinnerMaximumQuantity.setValueFactory(valueFctoryMaximum);

        // Creates Buttons.
        final Button buttonBack = i18nui.newButton("back");
        final Button buttonAdd = i18nui.newButton("add");
        buttonBack.setPrefSize(100, 30);
        buttonAdd.setPrefSize(100, 30);

        // Creates a HBox and add the buttons in to.
        final HBox hBoxBottom = new HBox();
        hBoxBottom.getChildren().addAll(buttonAdd, buttonBack);
        root.setBottom(hBoxBottom);
        hBoxBottom.setAlignment(Pos.BOTTOM_CENTER);
        hBoxBottom.setPadding(new Insets(40));
        hBoxBottom.setSpacing(150);

        // Adds all elements in to gridPane.
        gridPane.add(textName, 0, 0);
        gridPane.add(textMinimumQuantity, 0, 1);
        gridPane.add(textMaximumQuantity, 0, 2);
        gridPane.add(textFieldName, 1, 0);
        gridPane.add(spinnerMinimumQuantitye, 1, 1);
        gridPane.add(spinnerMaximumQuantity, 1, 2);

        // buttonBack.setOnAction(event -> {
        // final Button buttonTemp = (Button) event.getSource();
        // final Stage primaryStage2 = (Stage) buttonTemp.getScene().getWindow();
        // primaryStage2.close();
        //
        // final Stage stageTemp = new Stage();
        // final Management management = new Management();
        //
        // try {
        // management.start(stageTemp);
        // } catch (final Exception ex) {
        // ex.printStackTrace();
        // }
        //
        // stageTemp.show();
        //
        // });

        /**
         * goes back to Management.
         */
        buttonBack.setOnAction(event -> {
            final Management management = new Management();
            try {
                management.start(primaryStage);
            } catch (final Exception ex) {
                ex.printStackTrace();
            }

            primaryStage.show();

        });

        /**
         * Adds new Products.
         */
        buttonAdd.setOnAction(event -> {
            final String name = textFieldName.getText();
            final int max = spinnerMinimumQuantitye.getValue();
            final int min = spinnerMaximumQuantity.getValue();

            final DBStatement dbStatement = new DBStatement();
            // tests if adding Products works.
            boolean success;
            try {
                dbStatement.insrtNewProduct(name, max, min);

                success = true;

            } catch (final SQLException ex) {
                success = false;
            }
            final Stage stageSuccess = new Stage();
            final BorderPane borderPaneSuccess = new BorderPane();
            final Scene sceneSuccess = new Scene(borderPaneSuccess, 250, 250);
            // Crates labels and Button.
            final Label labelInfo = new Label();
            labelInfo.setText("Info");
            labelInfo.setFont(new Font("Arial", 18));
            labelInfo.setStyle("-fx-font-weight: Bold");

            Label labelMessage = new Label();
            labelMessage.setFont(new Font("Arial", 12));
            labelMessage.setStyle("-fx-font-weight: Bold");
            if (success) {
                labelMessage = i18nui.newLabel("product_was_added_successfully");
                labelMessage.setTextFill(Color.web("#158d27"));

            } else {
                labelMessage = i18nui.newLabel("product_already_exists");
                labelMessage.setTextFill(Color.web("#ff0423"));
            }

            final Button buttonSuccess = new Button("OK");
            buttonSuccess.setPrefSize(80, 30);
            // Creates a VBox and add the element.
            final VBox vBoxSuccess = new VBox();
            vBoxSuccess.getChildren().addAll(labelInfo, labelMessage, buttonSuccess);
            borderPaneSuccess.setCenter(vBoxSuccess);
            vBoxSuccess.setAlignment(Pos.CENTER);
            vBoxSuccess.setSpacing(70);
            /*
             * closes the info window.
             */
            buttonSuccess.setOnAction(event1 -> {
                final Button buttonTemp = (Button) event1.getSource();
                final Stage stageTemp = (Stage) buttonTemp.getScene().getWindow();
                stageTemp.close();

            });

            stageSuccess.setScene(sceneSuccess);
            stageSuccess.initModality(Modality.APPLICATION_MODAL);
            stageSuccess.initOwner(primaryStage);
            stageSuccess.showAndWait();

        });

        // shows the primaryStage.
        primaryStage.setScene(scene);
        primaryStage.show();

    }

}
