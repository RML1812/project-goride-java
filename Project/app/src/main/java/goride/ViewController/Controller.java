package goride.ViewController;

import java.util.ArrayList;
import java.util.List;
import goride.Model.*;
import goride.Strategy.PathfindingStrategy.PathfindingStrategy;
import goride.Strategy.PathfindingStrategy.DijkstraStrategy;

public class Controller
{
    private final Grid model;
    private final View view;
    
    public Controller(Grid model, View view)
    {
        this.model = model;
        this.view = view;
        this.view.setTriggers(this);
        this.view.createGrid();
        
        this.model.addObserver(view);
    }
    
    public void doClearGrid()
    {
        this.model.clearGrid();
    }
    
    public void doChangeClickType(Tile.Type type)
    {   
        this.model.changeClickType(type);
    }
    
    public void doAddRandomWalls()
    {
        this.model.addRandomWalls();
    }

    public void doAddRiders(int many)
    {
        this.model.addRiders(many);
    }
    
    public void doToggleTileBorder(boolean setBorder)
    {
        this.model.addTileBorders(setBorder);
    }
    
    public boolean doShortestPathAlgorithm(Tile RIDER, Tile ROOT, Tile TARGET) throws InterruptedException
    {
        PathfindingStrategy pathfindingStrategy = new DijkstraStrategy();
        return this.model.executePathfinding(pathfindingStrategy, RIDER, ROOT, TARGET);
    }

    public void findNearestRider(Tile ROOT){
        int i = 0; int j = model.getList().bykRider() - 1;
        Tile rider = model.getList().getRider(i).getTile();
        DijkstraStrategy alg = new DijkstraStrategy();
        List<Tile> path = new ArrayList<>();

        while(i < j){
            if(alg.runPathfinder(model, path, rider, model.getRoot()) >=
                alg.runPathfinder(model, path, model.getList().getRider(i+1).getTile(), model.getRoot())){
                rider = model.getList().getRider(i+1).getTile();
            }
            i++;
        }

        model.setRider(rider);
    }

    public void doWalk(){
        this.model.Walk();
    }

    public String riderName(Tile rider){
        Rider a = model.getList().getRider(0);
        for(int i = 0; i < model.getList().bykRider(); i++){
            if(model.getList().getRider(i).getTile() == rider){
                a = model.getList().getRider(i);
                break;
            }
        }
        return a.getName();
    }
}
