package goride.Model;

public class ListRider
{
    private Rider[] rider;

    public ListRider(int i)
    {
        this.rider = new Rider[i];
    }

    public void setRider(int i, Rider driver)
    {
        this.rider[i] = driver;
    }

    public Rider getRider(int i)
    {
        return rider[i];
    }

    public int bykRider()
    {
        return this.rider.length;
    }
}
