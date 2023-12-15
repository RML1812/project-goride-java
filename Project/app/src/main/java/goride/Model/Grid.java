package goride.Model;

import goride.Strategy.PathfindingStrategy.PathfindingStrategy;
import goride.Util.Painter;
import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;
import java.util.Random;

public class Grid extends Observable implements Observer
{    

    private int x_size;
    private int y_size;
    
    private Tile[][] grid;
    Tile root, target, rider;
    List<Tile> path_rider = new ArrayList<>();
    List<Tile> path_tujuan = new ArrayList<>();

    private ListRider list;

    private Tile.Type clickType;
    
    private final Painter painter;
    
    public Grid()
    {
        this.root = null;
        this.target = null;
        this.rider = null;
        this.clickType = Tile.Type.USER;
        painter = Painter.getInstance();
    }
    
    public boolean executePathfinding(PathfindingStrategy pathfindingStrategy, Tile RIDER, Tile ROOT, Tile TARGET) throws InterruptedException
    {
        if(ROOT == null || TARGET == null) return false;
        this.painter.clearPath(this);
        
        pathfindingStrategy.algorithm(this, path_rider, RIDER, ROOT);
        pathfindingStrategy.algorithm(this, path_tujuan, ROOT, TARGET);
        
        return true;
    }

    public void Walk()
    {
        this.getRider().removeRider();
        final int x = target.getX();
        final int y = target.getY();
        this.painter.walkPath(path_rider, this);
        this.painter.walkPath(path_tujuan, this);
        Tile tile = grid[x][y];
        this.root = tile;
        tile.setAttributes(Tile.Type.USER, tile.getDefaultWeight());
    }
    
    public void gridInit(int x_tiles, int y_tiles, int tile_size)
    {
        this.x_size = x_tiles;
        this.y_size = y_tiles;
        this.grid = new Tile[x_tiles][y_tiles];
        
        // Initializes all tiles
        for(int y = 0; y < y_tiles; y++)
        {
            for(int x = 0; x < x_tiles; x++)
            {
                Tile tile = new Tile(x, y, tile_size); 
                tile.addObserver(this);
                grid[x][y] = tile;
            }
        }
    }
    
    public Tile[][] getGrid()
    {
        return this.grid;
    }
    
    public boolean isReady()
    {
        return !(root == null || target == null);
    }
    
    public List<Tile> getTiles()
    {
        List<Tile> tiles = new ArrayList<>();
        
        for(int y = 0; y < this.y_size; y++)
        {
            for(int x = 0; x < this.x_size; x++)
            {
                tiles.add(grid[x][y]);
            }
        }
        
        return tiles;
    }

    public ListRider getList(){
        return this.list;
    }

    public void clearGrid()
    {
        for(int y = 0; y < this.y_size; y++)
        {
            for(int x = 0; x < this.x_size; x++)
            {
                if(grid[x][y].getType() == Tile.Type.DRIVER){
                    grid[x][y].removeRider();
                }else{
                    grid[x][y].clearTile();
                }
            }
        }
    }
    
    public void addTileBorders(boolean setBorder)
    {
        for(int y = 0; y < this.y_size; y++)
        {
            for(int x = 0; x < this.x_size; x++)
            {
                grid[x][y].setTileStroke(setBorder);
            }
        }
    }

    public int getWallsAmount()
    {
        int totalWalls = 0;
        
        for(int y = 0; y < this.y_size; y++)
        {
            for(int x = 0; x < this.x_size; x++)
            {
                if(grid[x][y].isWall()) totalWalls++;
            }
        }
        
        return totalWalls;
    }
    
    public List<Tile> getTileNeighbors(Tile tile)
    {
        List<Tile> neighbors = new ArrayList<>();
        
        neighbors.add(this.getNorthTile(tile));
        neighbors.add(this.getSouthTile(tile));
        neighbors.add(this.getEastTile(tile));
        neighbors.add(this.getWestTile(tile));
        
        return neighbors;
    }
    
    public Tile getNorthTile(Tile tile)
    {
        return (tile.getY() - 1 >= 0) ? grid[tile.getX()][tile.getY() - 1] : null;
    }
    
    public Tile getSouthTile(Tile tile)
    {
        return (tile.getY() + 1 <= y_size - 1) ? grid[tile.getX()][tile.getY() + 1] : null;
    }
    
    public Tile getWestTile(Tile tile)
    {
        return (tile.getX() - 1 >= 0) ? grid[tile.getX() - 1][tile.getY()] : null;
    }
    
    public Tile getEastTile(Tile tile)
    {
        return (tile.getX() + 1  <= x_size - 1) ? grid[tile.getX() + 1][tile.getY()] : null;
    }
    
    public boolean isOnNorth(Tile tile, Tile compare)
    {
        if(tile.getX() != compare.getX()) 
            return false;
        
        return tile.getY() < compare.getY();
    }
    
    public boolean isOnSouth(Tile tile, Tile compare)
    {
        if(tile.getX() != compare.getX()) 
            return false;
        
        return tile.getY() > compare.getY();
    }
    
    public boolean isOnWest(Tile tile, Tile compare)
    {
        if(tile.getY() != compare.getY()) 
            return false;
        
        return tile.getX() < compare.getX();
    }
    
    public boolean isOnEast(Tile tile, Tile compare)
    {
        if(tile.getY() != compare.getY()) 
            return false;
        
        return tile.getX() > compare.getX();
    }
    
    public int getYSize()
    {
        return this.y_size;
    }
    
    public int getXSize()
    {
        return this.x_size;
    }
    
    public void changeClickType(Tile.Type type)
    {
        this.clickType = type;
    }
    
    public Tile getRoot()
    {
        return this.root;
    }
    
    public Tile getTarget()
    {
        return this.target;
    }

    public Tile getRider()
    {
        return this.rider;
    }

    public void setRoot(Tile root)
    {
        this.root = root;
    }

    public void setTarget(Tile target)
    {
        this.target = target;
    }

    public void setRider(Tile rider)
    {
        this.rider = rider;
    }
    
    public void addRandomWalls()
    {
        Random random = new Random();
        Tile tile;
        
        for(int y = 0; y < this.y_size; y++)
        {
            for(int x = 0; x < this.x_size; x++)
            {
                tile = grid[x][y];
                if(tile.getType() == Tile.Type.POHON)
                    tile.setAttributes(Tile.Type.KOSONG, tile.getWeight());
                
                if((random.nextInt(6) == 1))
                    tile.setAttributes(Tile.Type.POHON, tile.getWeight());
            }
        }
    }

    public void addRiders(int many)
    {
        Random random = new Random();
        this.list = new ListRider(many);
        Tile tile;
        int q = 0;

        for(int a = 0; a < this.y_size; a++)
        {
            for(int b = 0; b < this.x_size; b++)
            {
                tile = grid[a][b];
                if(tile.getType() == Tile.Type.DRIVER)
                    tile.removeRider();
            }
        }
        
        while(q < many){
            for(int y = 0; y < this.y_size && q < many; y++)
            {
                for(int x = 0; x < this.x_size && q < many; x++)
                {
                    tile = grid[x][y];
                    if(random.nextInt(20) == 1){
                        if(tile.getType() == Tile.Type.POHON){
                            break;
                        }else{
                            tile.setAttributes(Tile.Type.DRIVER, tile.getWeight());
                            this.list.setRider(q, tile.setRider(q));
                            q++;
                        } 
                    }
                }
            }
        }
    }
    
    @Override
    public void update(Observable o, Object arg)
    {
        if(o instanceof Tile)
        {
            Tile tile = (Tile)o;
            
            if(tile.isWall())
            {
                if(this.clickType == Tile.Type.KOSONG)
                    tile.setAttributes(clickType, tile.getDefaultWeight());
                return;
            }
            
            switch(this.clickType)
            {
                case USER: case TUJUAN:

                    if(clickType == Tile.Type.USER) 
                    {
                        if(this.root != null) root.clearTile();
                        this.root = tile;
                    }
                    else if(clickType == Tile.Type.TUJUAN) 
                    {
                        if(this.target != null) target.clearTile();
                        this.target = tile;
                    }
                    
                    tile.setAttributes(clickType, tile.getDefaultWeight());
                    
                    break;
                
                default:
                    tile.setAttributes(clickType, tile.getWeight());
                    break;
            }
        }
        
        if(o instanceof PathfindingStatistics)
        {
            PathfindingStatistics stats = (PathfindingStatistics)o;
            setChanged();
            notifyObservers(stats);
        }
    }
}
