package goride.Model;

public class Rider
{
    private String name;
    private Tile tile;

    public Rider(String name, Tile tile)
    {
        this.name = name;
        this.tile = tile;
    }

    public void setTile(Tile tile)
    {
        this.tile = tile;
    }

    public Tile getTile()
    {
        return this.tile;
    }

    public String getName()
    {
        return this.name;
    }
}