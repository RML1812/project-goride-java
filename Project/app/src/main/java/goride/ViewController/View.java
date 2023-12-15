package goride.ViewController;

import goride.Model.*;
import goride.Strategy.PathfindingStrategy.*;
import java.util.Observable;
import java.util.Observer;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.ArrayList;
import java.util.List;
import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Separator;
import javafx.scene.control.SplitPane;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;

public class View implements Observer
{
    private final int WIDTH = 1380;
    private final int HEIGHT = 720;
    
    private final TextField txtXTiles;
    private final TextField txtYTiles;
    private final TextField txtTileSize;
  
    private final Separator separatorStats;
    private final Text txtStatsTitle;
    private final Text txtStatsPathCost;
    private final Text txtStatsPathCostValue;
    private final Text txtStatsPathPrice;
    private final Text txtStatsPathPriceValue;
    private final Text txtStatsRider;
    private final Text txtStatsRiderValue;
    private final Text txtStatsAsalTujuan;
    private final Text txtStatsAsalTujuanValue;


    private final Text txtHistoryTitle;
    private final Text txtHistoryValue;
    private String text = "";

    private final Text txtObstacles;
    private final Text txtRiders;
    private final TextField txtRidersSize;
    private final Button btnCheck;
    private final Button btnClear;
    private final Button btnExit;
    private final Button btnPurchase;
    private final Button btnAddWalls;
    private final Button btnAddRiders;
    private final Button btnCreateGrid;

    private final ComboBox cbNodeBox;
    
    // Grid
    private final VBox leftPane;
    private final ScrollPane rightPane;
    private final Pane parentGridPane;
    private Pane gridPane;
    
    // View-Model
    private final Grid model;
    private final Scene scene;
    
    // Attributes
    private final int padding = 2;
    private final String defaultXSize = "20";
    private final String defaultYSize = "20";
    private final String defaultTileSize = "20";
    private final double leftPanelSize = 0.20;
    private final String defaultRidersSize = "5";
    private final Font defaultFont = Font.font("Courier New", 14);
    private final String defaultHboxStyle = "-fx-padding: 10;" 
        + "-fx-border-style: solid inside;"
        + "-fx-border-width: 2;" + "-fx-border-insets: " + padding + ";"
        + "-fx-border-radius: 4;" + "-fx-border-color: lightgray;";
    
    public View(Grid model)
    {
        this.model = model;
        this.parentGridPane = new Pane();
        this.gridPane = null;
       
        this.leftPane = new VBox();
        this.leftPane.setPadding(new Insets(padding, padding, padding, padding));
        this.leftPane.setSpacing(10);
                
       
        VBox vboxCreateGrid = new VBox(padding);
        vboxCreateGrid.setStyle(defaultHboxStyle);
        GridPane createPane = new GridPane();
        createPane.setHgap(5);
        createPane.setPadding(new Insets(padding, padding, padding, padding));
        createPane.add(new Text("X: "), 0, 0);
        txtXTiles = new TextField(defaultXSize);
        createPane.add(txtXTiles, 1, 0);
        createPane.add(new Text("Y: "), 2, 0);
        txtYTiles = new TextField(defaultYSize);
        createPane.add(txtYTiles, 3, 0);
        createPane.add(new Text("Size: "), 4, 0);
        txtTileSize = new TextField(defaultTileSize); 
        createPane.add(txtTileSize, 5, 0);
        HBox hboxCreateBtn = new HBox(padding);
        hboxCreateBtn.setAlignment(Pos.CENTER);
        btnCreateGrid = new Button("BUAT GRID BARU");
        hboxCreateBtn.getChildren().addAll(btnCreateGrid);
        vboxCreateGrid.getChildren().addAll(createPane, hboxCreateBtn);
        
        
        HBox hboxNodeBox = new HBox(padding);
        hboxNodeBox.setAlignment(Pos.CENTER);
        hboxNodeBox.setStyle(defaultHboxStyle);
        Text txtNodeBox = new Text("MODE PILIH: ");
        txtNodeBox.setFont(defaultFont);
        cbNodeBox = new ComboBox(FXCollections.observableArrayList(Tile.Type.values()));
        cbNodeBox.getItems().remove(Tile.Type.DRIVER);
        cbNodeBox.getItems().remove(Tile.Type.VISITED);
        cbNodeBox.getItems().remove(Tile.Type.PATH);
        cbNodeBox.getItems().remove(Tile.Type.HIGHLIGHT);
        cbNodeBox.getItems().remove(Tile.Type.VISITED_DENSE);
        cbNodeBox.getItems().remove(Tile.Type.VISITED_LIGHT);
        cbNodeBox.getItems().remove(Tile.Type.VISITED_MAX);
        cbNodeBox.getItems().remove(Tile.Type.VISITED_MEDIUM);
        cbNodeBox.getSelectionModel().selectFirst();
        hboxNodeBox.getChildren().addAll(txtNodeBox, cbNodeBox);
        
        
        VBox vboxObstacles = new VBox(padding);
        vboxObstacles.setStyle(defaultHboxStyle);
        vboxObstacles.setAlignment(Pos.CENTER);
        HBox hboxObstacles = new HBox(padding);
        hboxObstacles.setAlignment(Pos.CENTER);
        txtObstacles = new Text("REFRESH POHON: ");
        txtObstacles.setFont(defaultFont);
        btnAddWalls = new Button("REFRESH");
        HBox hboxRiders = new HBox(padding);
        hboxRiders.setAlignment(Pos.CENTER);
        txtRiders = new Text("REFRESH DRIVER: ");
        txtRiders.setFont(defaultFont);
        txtRidersSize = new TextField(defaultRidersSize);
        btnAddRiders = new Button("REFRESH");
        hboxObstacles.getChildren().addAll(txtObstacles, btnAddWalls, txtRiders, btnAddRiders, txtRidersSize);
        hboxRiders.getChildren().addAll(txtRiders, txtRidersSize);
        vboxObstacles.getChildren().addAll(hboxObstacles, hboxRiders, btnAddRiders);
        
       
        HBox hboxUtilBtns = new HBox(padding);
        hboxUtilBtns.setAlignment(Pos.CENTER);
        hboxUtilBtns.setStyle(defaultHboxStyle);
        btnClear = new Button("CLEAR");
        btnExit = new Button("EXIT");
        btnCheck = new Button("CHECK");
        hboxUtilBtns.getChildren().addAll(btnCheck, btnClear, btnExit);
        HBox hboxPurchaseBtns = new HBox(padding);
        hboxPurchaseBtns.setAlignment(Pos.CENTER);
        hboxPurchaseBtns.setStyle(defaultHboxStyle);
        btnPurchase = new Button("PURCHASE");
        hboxPurchaseBtns.getChildren().add(btnPurchase);
        
        separatorStats = new Separator();
        VBox vboxStats = new VBox(padding);
        vboxStats.setAlignment(Pos.CENTER_LEFT);
        vboxStats.setStyle(defaultHboxStyle);
        HBox hboxStatsTitle = new HBox(padding);
        hboxStatsTitle.setAlignment(Pos.CENTER);
        txtStatsTitle = new Text("RECEIPT");
        txtStatsTitle.setFont(Font.font(defaultFont.getName(), FontWeight.BOLD, 20));
        hboxStatsTitle.getChildren().add(txtStatsTitle);
        HBox hboxStatsPathCost = new HBox(padding);
        txtStatsPathCost = new Text("Jarak: ");
        txtStatsPathCostValue = new Text("");
        hboxStatsPathCost.getChildren().addAll(txtStatsPathCost, txtStatsPathCostValue);
        HBox hboxStatsPathPrice = new HBox(padding);
        txtStatsPathPrice = new Text("Harga: ");
        txtStatsPathPriceValue = new Text("");
        hboxStatsPathPrice.getChildren().addAll(txtStatsPathPrice, txtStatsPathPriceValue);
        HBox hboxStatsRider = new HBox(padding);
        txtStatsRider = new Text("Rider: ");
        txtStatsRiderValue = new Text("");
        hboxStatsRider.getChildren().addAll(txtStatsRider, txtStatsRiderValue);
        HBox hboxStatsAsalTujuan = new HBox(padding);
        txtStatsAsalTujuan = new Text("Asal -> Tujuan: ");
        txtStatsAsalTujuanValue = new Text("");
        hboxStatsAsalTujuan.getChildren().addAll(txtStatsAsalTujuan, txtStatsAsalTujuanValue);
        vboxStats.getChildren().addAll(hboxStatsTitle, separatorStats, hboxStatsPathCost, hboxStatsPathPrice, hboxStatsRider, hboxStatsAsalTujuan);
        
        leftPane.getChildren().addAll(vboxCreateGrid, hboxNodeBox, vboxObstacles, vboxStats, hboxUtilBtns, hboxPurchaseBtns);
        // EndRegion: RightPane

        // Start Region: RightPane
        this.rightPane = new ScrollPane();
        this.rightPane.setPadding(new Insets(padding, padding, padding, padding));
        this.rightPane.setFitToHeight(true);

        //History Pane
        VBox vboxHistory = new VBox(padding);
        vboxHistory.setAlignment(Pos.TOP_CENTER);
        vboxHistory.setStyle(defaultHboxStyle);
        HBox hboxHistoryTitle = new HBox(padding);
        hboxHistoryTitle.setAlignment(Pos.TOP_CENTER);
        txtHistoryTitle = new Text("HISTORY");
        txtHistoryTitle.setFont(Font.font(defaultFont.getName(), FontWeight.BOLD, 20));
        hboxHistoryTitle.getChildren().add(txtHistoryTitle);
        HBox hboxHistory = new HBox(padding);
        hboxHistory.setAlignment(Pos.TOP_LEFT);
        txtHistoryValue = new Text("");
        hboxHistory.getChildren().add(txtHistoryValue);
        vboxHistory.getChildren().addAll(hboxHistoryTitle, hboxHistory);

        rightPane.setContent(vboxHistory);

        //  Create scene
        this.scene = new Scene(initComponents(), WIDTH, HEIGHT);
    }
    
    public void setTriggers(Controller controller)
    {

        cbNodeBox.setOnAction((event) -> 
        {
            FXCollections.observableArrayList(Tile.Type.values()).stream().filter((item) -> (cbNodeBox.getValue().toString().equals(item.toString()))).forEachOrdered((item) ->
            {
                controller.doChangeClickType(item);
            });
        });
        
        btnClear.setOnAction((event) ->
        {
            controller.doClearGrid();
        });
        
    
        btnExit.setOnAction((event) -> 
        {
            System.exit(0);
        });
        
        btnPurchase.setOnAction((event) ->
        {
            controller.doWalk();
            this.txtHistoryValue.setText(
                this.txtHistoryValue.getText() + "\n" +
                "Jarak: " + this.txtStatsPathCostValue.getText() + "\n" +
                "Harga: " + this.txtStatsPathPriceValue.getText() + "\n" +
                "Rider: " + this.txtStatsRiderValue.getText() + "\n" +
                "Asal -> Tujuan: " + this.txtStatsAsalTujuanValue.getText() + "\n"
            );
        });
        
        btnAddWalls.setOnAction((event) -> 
        {
            controller.doAddRandomWalls();
        });
         
        btnAddRiders.setOnAction((event) -> 
        {
            controller.doAddRiders(Integer.valueOf(txtRidersSize.getText()));
        });
        
        // Initialized the grid
        btnCreateGrid.setOnAction((event) ->
        {
            int x = Integer.valueOf(txtXTiles.getText());
            x = (x % 2 == 0) ? x - 1 : x; 
            int y = Integer.valueOf(txtYTiles.getText());
            y = (y % 2 == 0) ? y - 1 : y;
            int size = Integer.valueOf(txtTileSize.getText());
            
            if(parentGridPane.getChildren().contains(gridPane))
                parentGridPane.getChildren().remove(gridPane);
            
            // Initializes the grid
            model.gridInit(x, y, size);
            this.fillGrid(model.getGrid());
        });
        
        btnCheck.setOnAction((event) -> 
        {
            boolean success;
                try 
                {
                    controller.findNearestRider(model.getRoot());
                    success = controller.doShortestPathAlgorithm(model.getRider(), model.getRoot(), model.getTarget());
                    this.txtStatsRiderValue.setText(controller.riderName(model.getRider()));
                    this.txtStatsAsalTujuanValue.setText("(" + model.getRoot().getX() + ", " + model.getRoot().getY() +") -> ("
                                                          + model.getTarget().getX() + ", " + model.getTarget().getY() + ")");
                } 
                catch (InterruptedException ex) 
                {
                    Logger.getLogger(View.class.getName()).log(Level.SEVERE, null, ex);
                }
        });
    }
    
    public Scene getScene()
    {
        return this.scene;
    }
    
    public void createGrid()
    {
        btnCreateGrid.fire();
    }
    
    private SplitPane initComponents()
    {
        VBox root = new VBox();
        
       
        root.getChildren().add(this.parentGridPane);
        
        SplitPane splitPane = new SplitPane();
        splitPane.getItems().addAll(this.leftPane, root, this.rightPane);
        
        this.leftPane.maxWidthProperty().bind(splitPane.widthProperty().multiply(this.leftPanelSize));
        this.leftPane.minWidthProperty().bind(splitPane.widthProperty().multiply(this.leftPanelSize));
        this.rightPane.maxWidthProperty().bind(splitPane.widthProperty().multiply(this.leftPanelSize));
        this.rightPane.minWidthProperty().bind(splitPane.widthProperty().multiply(this.leftPanelSize));

        return splitPane;
    }
    
    private void fillGrid(Tile[][] tiles)
    {
        this.gridPane = new Pane();
        for(Tile[] row : tiles)
        {
            for(Tile tile: row)
            {
                gridPane.getChildren().add(tile.getStackPane());
            }
        }
        this.parentGridPane.getChildren().add(gridPane);
    }

    @Override
    public void update(Observable o, Object arg)
    {
        // If it gets updated by the grid
        if(o instanceof Grid)
        {
            if(arg instanceof PathfindingStatistics)
            {
                PathfindingStatistics stats = (PathfindingStatistics)arg;
                
                this.txtStatsPathCostValue.setText(String.valueOf(stats.getPathCost()) + " km");
                this.txtStatsPathPriceValue.setText("Rp " + String.valueOf((stats.getPathCost())*5000));
            }
        }
    }
}
