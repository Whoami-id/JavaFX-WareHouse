
package aleon;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.Locale;

import i18n.I18nSupport;
import i18n.I18nUISupport;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Separator;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class MainMenu extends Application {

    private final I18nUISupport i18nui = I18nUISupport.getInstance();

    @Override
    public void start(final Stage primaryStage) throws Exception {

        /**
         * Changes the languages.
         */
        primaryStage.addEventHandler(KeyEvent.KEY_PRESSED, event -> {
            if (event.isMetaDown()
                    && (event.getText().equals("L") || event.isShiftDown() && event.getText().equalsIgnoreCase("l"))) {
                final I18nSupport i18n = i18nui.i18n();
                final List<Locale> locales = i18n.getSupportedLocales();
                final int idxCur = locales.indexOf(i18n.getLocale());
                if (idxCur == -1) {
                    return;
                }
                final int idxNext = (idxCur + 1) % locales.size();
                i18n.setLocale(locales.get(idxNext));
            }
        });

        // creates the Stage, Scene and BorderPane.
        final BorderPane root = new BorderPane();
        final Scene scene = new Scene(root, 530, 640);

        // creates Label and Separator for heading.
        final Label label = i18nui.newLabel("aleon_inventory");
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

        // Creates a VBox for main Buttons.
        final VBox vBoxCenter = new VBox();
        // vBox.setPadding(new Insets(100));

        // Creates the main Buttons.
        final Button buttonStocktaking = i18nui.newButton("stock_taking");
        final Button buttonManagement = i18nui.newButton("product_management");
        final Button buttonCSV = i18nui.newButton("save_as_csv");

        buttonStocktaking.setPrefSize(250, 40);
        buttonManagement.setPrefSize(250, 40);
        buttonCSV.setPrefSize(250, 40);

        buttonStocktaking.setFont(new Font("System", 18));
        buttonManagement.setFont(new Font("System", 18));
        buttonCSV.setFont(new Font("System", 18));

        // adds the main Buttons into vBoxCenter.
        vBoxCenter.getChildren().addAll(buttonStocktaking, buttonManagement, buttonCSV);
        root.setCenter(vBoxCenter);
        vBoxCenter.setAlignment(Pos.TOP_CENTER);
        vBoxCenter.setPadding(new Insets(100, 0, 0, 0));
        vBoxCenter.setSpacing(100);

        // Crates a HBox and Button for bottom.
        final HBox hBoxBottom = new HBox();
        final Button buttonCancel = i18nui.newButton("close");
        hBoxBottom.getChildren().add(buttonCancel);
        buttonCancel.setPrefSize(100, 30);
        root.setBottom(hBoxBottom);
        hBoxBottom.setAlignment(Pos.BOTTOM_RIGHT);
        hBoxBottom.setPadding(new Insets(40));

        // shows primayStage.
        primaryStage.setScene(scene);
        primaryStage.show();

        // buttonStocktaking.setOnMouseClicked(event -> {
        //
        // final Button buttonTemp = (Button) event.getSource();
        // final Stage primaryStage2 = (Stage) buttonTemp.getScene().getWindow();
        // primaryStage2.close();
        // final Stage stageTemp = new Stage();
        // final Stocktaking stocktaking = new Stocktaking();
        // try {
        // stocktaking.start(stageTemp);
        // } catch (final Exception ex) {
        // ex.printStackTrace();
        // }
        // stageTemp.show();
        //
        // });

        /**
         * changes to Stocktaking.
         */
        buttonStocktaking.setOnAction(event -> {
            final Stocktaking stocktaking = new Stocktaking();
            try {
                stocktaking.start(primaryStage);

            } catch (final Exception ex) {
                ex.printStackTrace();
                primaryStage.show();
            }

        });

        // buttonManagement.setOnMouseClicked(event -> {
        // final Button buttonTemp2 = (Button) event.getSource();
        // final Stage primaryStage3 = (Stage) buttonTemp2.getScene().getWindow();
        // primaryStage3.close();
        // final Stage stageTemp2 = new Stage();
        // final Management management = new Management();
        // try {
        // management.start(stageTemp2);
        // } catch (final Exception ex) {
        // ex.printStackTrace();
        // }
        // stageTemp2.show();
        //
        // });

        /**
         * changes to Management.
         */
        buttonManagement.setOnAction(event -> {
            final Management management = new Management();
            try {
                management.start(primaryStage);
            } catch (final Exception ex) {
                ex.printStackTrace();
            }

            primaryStage.show();
        });

        buttonCSV.setOnAction(event -> {
            // final CSVConnection csvConnection = new CSVConnection();
            final CSVConnection1 csvConnection1 = new CSVConnection1();
            // final CSVConnection2 csvConnection2 = new CSVConnection2();
            boolean success;
            try {
                csvConnection1.csvFormat();
                success = true;
            } catch (final SQLException | IOException ex) {
                ex.printStackTrace();
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
                labelMessage = i18nui.newLabel("saved_successfuly");
                labelMessage.setTextFill(Color.web("#158d27"));

            } else {
                labelMessage = i18nui.newLabel("it_can_not_save");
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

        /**
         * closes the Menu.
         */
        buttonCancel.setOnMouseClicked(event -> Platform.exit());

    }

    public static void main(final String[] args) {
        launch(args);

    }

}
