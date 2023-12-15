package goride;

import goride.Model.Grid;
import goride.ViewController.Controller;
import goride.ViewController.View;
import javafx.application.Application;
import javafx.stage.Stage;

public class Main extends Application
{
    private final String APP_TITLE = "Go Ride - Kelompok A04";
    
    public static void main(String[] args)
    {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception
    {
        View app = createApp();
        
        primaryStage.setScene(app.getScene());
        primaryStage.setTitle(APP_TITLE);
        primaryStage.show();
    }
    
    private View createApp()
    {
        Grid model = new Grid();
        View view = new View(model);
        Controller controller = new Controller(model, view);
        
        return view;
    }
    
}
