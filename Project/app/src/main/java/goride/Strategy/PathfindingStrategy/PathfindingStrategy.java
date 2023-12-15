package goride.Strategy.PathfindingStrategy;

import goride.Model.Grid;
import goride.Model.PathfindingStatistics;
import goride.Model.Tile;
import goride.Util.Painter;
import java.util.List;

public abstract class PathfindingStrategy 
{
    protected Painter painter;
    protected PathfindingStatistics statistics;
    
    public PathfindingStrategy()
    {
        this.painter = Painter.getInstance();
    }
    
    public final int algorithm(Grid model, List<Tile> path, Tile ROOT, Tile TARGET)
    {
        long start = System.nanoTime();
        this.statistics = new PathfindingStatistics(model);
        this.statistics.setWallSize(model.getWallsAmount());
        
        int cost = this.runPathfinder(model, path, ROOT, TARGET);

        long end = System.nanoTime();
        this.statistics.setElapsedTime(end - start);
        this.statistics.updateObservers();
        this.painter.drawPath(path, model, ROOT, TARGET);
        
        System.out.println(this.statistics);
        
        return cost;
    }
    
    protected abstract int runPathfinder(Grid model, List<Tile> path, Tile ROOT, Tile TARGET);
}