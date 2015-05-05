class Ore
    extends EntityActions
{
    private String name;
    private Point position;
    private int rate;
    Ore(String name, Point position, int rate)
    {
	super(name, position,rate,0,0,0);
	//this.rate = rate;
    }
}