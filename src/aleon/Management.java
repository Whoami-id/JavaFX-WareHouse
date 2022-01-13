
package aleon;

import java.sql.SQLException;
import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import i18n.I18nUISupport;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Separator;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class Management {
    private final I18nUISupport i18nui = I18nUISupport.getInstance();

    /**
     * includes the entire stage and start it
     *
     * @param primaryStage the entire Stage
     * @throws SQLException if some code cannot be executed
     */
    public void start(final Stage primaryStage) throws SQLException {

        // Creates a Stage, Scene and BorderPane.
        final BorderPane root = new BorderPane();
        final Scene scene = new Scene(root, 530, 640);

        // Creates label and Separator for heading.
        final Label label = i18nui.newLabel("products_in_the_warehouse");
        label.setFont(new Font("Arial", 20));
        label.setStyle("-fx-font-weight: Bold");

        final Separator separator = new Separator();
        separator.setPrefWidth(530);

        final VBox vBoxTop = new VBox();
        vBoxTop.getChildren().addAll(label, separator);
        root.setTop(vBoxTop);
        vBoxTop.setAlignment(Pos.TOP_CENTER);
        vBoxTop.setPadding(new Insets(30, 0, 0, 0));

        // Crates a tableView.
        final TableView<ProductStock> tableView = new TableView<>();
        // tableView.setPrefHeight(700);
        // tableView.setPrefWidth(2000);

        // Creates a VBox and add the tableView in to.
        final VBox vBoxCenter = new VBox();
        vBoxCenter.getChildren().addAll(tableView);
        root.setCenter(vBoxCenter);
        vBoxCenter.setPadding(new Insets(40, 0, 0, 0));

        // Creates Table columns.
        final TableColumn<ProductStock, String> name = i18nui.newTableColumn("name");
        final TableColumn<ProductStock, Integer> onStock = i18nui.newTableColumn("on_stock");
        final TableColumn<ProductStock, String> date = i18nui.newTableColumn("date_time");
        final TableColumn<ProductStock, Integer> min = i18nui.newTableColumn("minimal");
        final TableColumn<ProductStock, Integer> max = i18nui.newTableColumn("maximum");

        // sets Listener Property.

        name.prefWidthProperty().bind(primaryStage.widthProperty().divide(5));
        onStock.prefWidthProperty().bind(primaryStage.widthProperty().divide(5));
        date.prefWidthProperty().bind(primaryStage.widthProperty().divide(5));
        min.prefWidthProperty().bind(primaryStage.widthProperty().divide(5));
        max.prefWidthProperty().bind(primaryStage.widthProperty().divide(5));

        name.setMaxWidth(400);
        onStock.setMaxWidth(400);
        min.setMaxWidth(400);
        max.setMaxWidth(400);
        date.setMaxWidth(400);

        name.setMinWidth(80);
        onStock.setMinWidth(80);
        min.setMinWidth(80);
        max.setMinWidth(80);
        date.setMinWidth(140);

        // adds the columns in the tabelView.
        // tableView.getColumns().addAll(name, onStock, min, max, date);
        tableView.getColumns().addAll(Stream.of(name, onStock, min, max, date).collect(Collectors.toList()));

        name.setStyle("-fx-alignment: CENTER;");
        onStock.setStyle("-fx-alignment: CENTER;");
        date.setStyle("-fx-alignment: CENTER;");
        min.setStyle("-fx-alignment: CENTER;");
        max.setStyle("-fx-alignment: CENTER;");

        // Crates Buttons.
        final Button buttonBack = i18nui.newButton("back");
        final Button buttonAdd = i18nui.newButton("add_new_products");
        final Button buttonDelete = i18nui.newButton("delete");
        buttonBack.setPrefSize(130, 30);
        buttonAdd.setPrefSize(130, 30);
        buttonDelete.setPrefSize(130, 30);
        // Creates a HBox and add the Buttons in to.
        final HBox hBoxBottom = new HBox();
        hBoxBottom.getChildren().addAll(buttonAdd, buttonDelete, buttonBack);
        root.setBottom(hBoxBottom);
        hBoxBottom.setAlignment(Pos.BOTTOM_CENTER);
        hBoxBottom.setPadding(new Insets(40));
        hBoxBottom.setSpacing(60);

        // Creates an ScrollPane.
        // final ScrollPane scrollPane = new ScrollPane();
        // scrollPane.setContent(tableView);

        // vBoxCenter.getChildren().add(scrollPane);

        // shows primaryStage.
        primaryStage.setScene(scene);
        primaryStage.show();

        // buttonBack.setOnMouseClicked(event -> {
        // final Button buttonTemp = (Button) event.getSource();
        // final Stage primaryStage2 = (Stage) buttonTemp.getScene().getWindow();
        // primaryStage2.close();
        // final Stage stageTemp = new Stage();
        // final MainMenu menu = new MainMenu();
        // try {
        // menu.start(stageTemp);
        // } catch (final Exception ex) {
        // ex.printStackTrace();
        // }
        // stageTemp.show();
        //
        // });

        /**
         * goes back to main Menu.
         * //
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

        // buttonAdd.setOnAction(event -> {
        // final Button buttonTemp2 = (Button) event.getSource();
        // final Stage primaryStage3 = (Stage) buttonTemp2.getScene().getWindow();
        // primaryStage3.close();
        //
        // final Stage stageTemp2 = new Stage();
        // final NewProduct menu2 = new NewProduct();
        // try {
        // menu2.start(stageTemp2);
        // } catch (final Exception ex) {
        // ex.printStackTrace();
        //
        // }
        //
        // stageTemp2.show();
        //
        // });

        /**
         * adds new Products.
         *
         */
        buttonAdd.setOnAction(event -> {
            final NewProduct newProduct = new NewProduct();
            try {
                newProduct.start(primaryStage);
            } catch (final Exception ex) {
                ex.printStackTrace();
            }
            primaryStage.show();

        });

        /**
         * removes Products.
         */
        buttonDelete.setOnAction(e -> {
            final Product selectedItem = tableView.getSelectionModel().getSelectedItem();
            if (selectedItem == null) {
                return;
            }

            // creates Stag, Scene, label, Button.
            final Stage stageInfo = new Stage();
            final BorderPane rootInfo = new BorderPane();
            final Scene sceneInfo = new Scene(rootInfo, 250, 250);

            Label labelInfo = new Label();
            labelInfo = i18nui.newLabel("info");
            labelInfo.setFont(new Font("Arial", 18));
            labelInfo.setStyle("-fx-font-weight: Bold");

            Label labelMessage = new Label();
            labelMessage.setFont(new Font("Arial", 12));
            labelMessage.setStyle("-fx-font-weight: Bold");
            labelMessage = i18nui.newLabel("confirm_deletion");

            final Button buttonCancel = i18nui.newButton("cancel");
            buttonCancel.setPrefSize(80, 30);
            final Button buttonOk = i18nui.newButton("Ok");
            buttonOk.setPrefSize(80, 30);

            // creates HBox and VBox.
            final VBox vBoxInfo = new VBox();
            final HBox hBoxInfo = new HBox();
            hBoxInfo.getChildren().addAll(buttonCancel, buttonOk);
            vBoxInfo.getChildren().addAll(labelInfo, labelMessage, hBoxInfo);
            rootInfo.setCenter(vBoxInfo);
            vBoxInfo.setAlignment(Pos.CENTER);
            vBoxInfo.setSpacing(70);
            hBoxInfo.setAlignment(Pos.CENTER);
            hBoxInfo.setSpacing(30);

            /*
             * cancel the modification.
             */
            buttonCancel.setOnAction(event -> stageInfo.close());

            /*
             * confirms the modification.
             */
            buttonOk.setOnAction(event -> {

                final DBStatement dbStatement = new DBStatement();
                try {
                    dbStatement.deleteProduct(selectedItem);
                } catch (final SQLException ex) {
                    ex.printStackTrace();
                }
                tableView.getItems().remove(selectedItem);
                stageInfo.close();

            });

            stageInfo.setScene(sceneInfo);
            stageInfo.initModality(Modality.APPLICATION_MODAL);
            stageInfo.initOwner(primaryStage);
            stageInfo.showAndWait();

        });

        // fills columns with values.
        final DBStatement dbStatement = new DBStatement();

        name.setCellValueFactory(new PropertyValueFactory<ProductStock, String>("name"));
        onStock.setCellValueFactory(new PropertyValueFactory<ProductStock, Integer>("actualOnStock"));
        min.setCellValueFactory(new PropertyValueFactory<ProductStock, Integer>("buyIfLessThan"));
        max.setCellValueFactory(new PropertyValueFactory<ProductStock, Integer>("purchaseQuantity"));
        final DateTimeFormatter df = DateTimeFormatter.ofPattern("dd MMM yyyy hh:mm:ss");
        date.setCellValueFactory(cellData -> {
            final long millisFromEpoch = cellData.getValue().getDateTime();
            final Instant instant = Instant.ofEpochMilli(millisFromEpoch);
            final ZoneId zoneId = ZoneId.systemDefault();
            final ZonedDateTime zonedDateTime = instant.atZone(zoneId);

            return new ReadOnlyObjectWrapper<>(df.format(zonedDateTime));
        });

        final ObservableList<ProductStock> list = FXCollections.observableArrayList(dbStatement.showProduct());
        tableView.setItems(list);

    }

}
