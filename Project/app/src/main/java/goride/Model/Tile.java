package goride.Model;

import java.awt.event.MouseListener;
import java.util.HashMap;
import java.util.Map;
import java.util.Observable;
import java.util.Random;
import java.util.Set;
import javafx.animation.FillTransition;
import javafx.scene.control.Tooltip;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.util.Duration;

public class Tile extends Observable
{
    public static enum Type
    {
        USER,
        TUJUAN,
        POHON,
        KOSONG,
        DRIVER,
        PATH,
        HIGHLIGHT,
        VISITED,
        VISITED_LIGHT,
        VISITED_MEDIUM,
        VISITED_DENSE,
        VISITED_MAX
    }
    
    private final Map<Type, Color> typeMap;
    private final Map<Integer, Color> weightMap;
    private final Map<Integer, Color> visitedMap;
    private final int[] WEIGHTS = { this.getDefaultWeight(), 3, 6, 9, 12};
    
    private final StackPane pane;
    
    private final int x;
    private final int y;
    
    private final Rectangle rectangle;
    
    private final int defaultWeight = 1;
    private int weight;
    private Type type;
    private final double tileGap = 0;
    private final int size;
    
    public Tile(int x, int y, int size)
    {
        pane = new StackPane();
        
        typeMap = new HashMap<>();
        typeMap.put(Type.USER, Color.YELLOW);
        typeMap.put(Type.TUJUAN, Color.PURPLE);
        typeMap.put(Type.KOSONG, Color.WHITE);
        typeMap.put(Type.POHON, Color.BLACK);
        typeMap.put(Type.DRIVER, Color.LIGHTGREY);
        typeMap.put(Type.PATH, Color.DEEPPINK);
        typeMap.put(Type.HIGHLIGHT, Color.RED);
        typeMap.put(Type.VISITED, Color.LIGHTGREEN);
        
        visitedMap = new HashMap<>();
        visitedMap.put(this.WEIGHTS[0], Color.PALEGREEN);
        visitedMap.put(this.WEIGHTS[1], Color.LIGHTGREEN);
        visitedMap.put(this.WEIGHTS[2], Color.SPRINGGREEN);
        visitedMap.put(this.WEIGHTS[3], Color.GREENYELLOW);
        visitedMap.put(this.WEIGHTS[4], Color.GREEN);
        
        weightMap = new HashMap<>();
        weightMap.put(this.WEIGHTS[0], Color.WHITE);
        weightMap.put(this.WEIGHTS[1], Color.LIGHTCYAN);
        weightMap.put(this.WEIGHTS[2], Color.AQUA);
        weightMap.put(this.WEIGHTS[3], Color.DEEPSKYBLUE);
        weightMap.put(this.WEIGHTS[4], Color.CORNFLOWERBLUE);
        
        this.x = x;
        this.y = y;
        
        this.weight = defaultWeight;
        this.type = Type.KOSONG;
        this.size = size;
        
        this.rectangle = new Rectangle(size - tileGap, size - tileGap);
        this.rectangle.setFill(Color.WHITE);
        this.setTileStroke(true);
        
        pane.getChildren().add(rectangle);
        pane.setTranslateX(x * size);
        pane.setTranslateY(y * size);
        
        updateTooltip(null);
        setEvents();
    }
    

    public StackPane getStackPane()
    {
        return this.pane;
    }
    
    public void setTileStroke(boolean setStroke)
    {
        this.rectangle.setStroke((setStroke) ? Color.LIGHTGRAY : null);
    }
    
    public void setAttributes(Type type, int weight)
    {   
        Color color;
        switch(type)
        {
            case VISITED:
                color = this.visitedMap.get(this.getWeight());
                break;
            case KOSONG:
                color = this.weightMap.get(weight);
                break;
            default:
                color = this.typeMap.get(type);
                break;
        }
        
        this.rectangle.setFill(color);
        this.type = type;
        this.weight = weight;
        updateTooltip(null);
    }

    public Rider setRider(int i){
        Rider rider = new Rider("Rider " + (i+1), this);
        Text num = new Text(Integer.toString((i+1)));
        num.setStyle(String.format("-fx-font: %d arial;", (size * 15) / 20));
        pane.getChildren().add(num);

        return rider;
    }

    public void removeRider(){
        pane.getChildren().remove(1);
        this.setAttributes(Tile.Type.KOSONG, this.getWeight());
    }

    public int getWeight()
    {
        return this.weight;
    }
    
    public int getX()
    {
        return this.x;
    }
    
    public int getY()
    {
        return this.y;
    }
    
    public void clearTile()
    {
        this.setAttributes(Type.KOSONG, defaultWeight);
    }

    public int getDefaultWeight()
    {
        return this.defaultWeight;
    }

    public Type getType()
    {
        return this.type;
    }
    
    public void randomizeWeight()
    {
        Set<Integer> keys = weightMap.keySet();
        Random random = new Random();
        
        this.setAttributes(Type.KOSONG,
                (int)keys.toArray()[random.nextInt(keys.size())]
                );
    }
    
    public boolean isWall()
    {
        return (this.type == Type.POHON);
    }

    private void updateTooltip(String text)
    {
        text = (text == null) ? this.toString() : text;
        Tooltip.install(pane, new Tooltip(text));
    }
    
    private void setEvents()
    {
        pane.addEventFilter(MouseEvent.MOUSE_PRESSED, (MouseEvent me) ->
        {
            // Notifies the @Grid
            setChanged();
            notifyObservers();
        });
    }

    @Override
    public String toString()
    {
        return String.format("%s | (%d,%d)", this.type, this.x, this.y);
    }
}