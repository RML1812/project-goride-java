package goride.Strategy.PathfindingStrategy;

import goride.Model.*;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class DijkstraStrategy extends PathfindingStrategy
{
    public DijkstraStrategy()
    {
        super();
    }

    @Override
    public int runPathfinder(Grid grid, List<Tile> path, Tile ROOT, Tile TARGET)
    {
        HashMap<Tile, Tile> parents = new HashMap();
        HashMap<Tile, Integer> weights = new HashMap();
        path.clear();
        
        executeDijkstra(grid, parents, weights, ROOT, TARGET);
        
        int cost = weights.get(TARGET);
        
        Tile tile = TARGET;
        
        if(cost != Integer.MAX_VALUE)
        {
            do{
                path.add(0, tile);
                tile = parents.get(tile);
            } while (tile != ROOT);
            
            if(this.statistics != null){
                this.statistics.setPathFound(true, cost);
            }
        }
        
        return cost;
    }
    
    private void executeDijkstra(Grid grid,
                    HashMap<Tile, Tile> parents,
                    HashMap<Tile, Integer> weights,
                    Tile root, Tile target){
        
        // Init all tiles
        Set<Tile> unvisited = new HashSet();
        
        grid.getTiles().stream().filter((tile) -> !(tile.isWall())).map((tile) ->
        {
            unvisited.add(tile);
            return tile;
        }).map((tile) ->
        {
            weights.put(tile, Integer.MAX_VALUE);
            return tile;
        }).forEachOrdered((tile) ->
        {
            parents.put(tile, null);
        });
        weights.put(root, 0);
        
        // Compute weights
        while(!unvisited.isEmpty())
        {
            Tile lowCostTile = getMinWeight(unvisited, weights);
            
            // If we ever get a lower cost that equals infinity, it means we're stuck
            if(weights.get(lowCostTile) == Integer.MAX_VALUE)
                break;
            
            if(this.statistics != null){
                this.statistics.incrementVisited();
            }
            
            // Remove current tile from unvisited set
            unvisited.remove(lowCostTile);
            
            // Get neighbors
            List<Tile> neighbors = grid.getTileNeighbors(lowCostTile);
            
            for(Tile tile : neighbors)
            {
                if(unvisited.contains(tile))
                {
                    int weight = tile.getWeight() + weights.get(lowCostTile);
                    if(weights.get(tile) > weight)
                    {
                        weights.put(tile, weight);
                        parents.put(tile, lowCostTile);
                    }
                }
            }
        }
    }
    
    private Tile getMinWeight(Set<Tile> unvisited, HashMap<Tile, Integer> weights)
    {
        double minWeight = Integer.MAX_VALUE;
        Tile minWeightTile = null;
        
        for(Tile tile : unvisited)
        {
            if(weights.get(tile) <= minWeight)
            {
                minWeightTile = tile;
                minWeight = weights.get(tile);
            }
        }
        
        return minWeightTile;
    }
}