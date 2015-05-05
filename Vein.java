class Vein
    extends EntityActions
{
    private String name;
    private int rate;
    private Point position;
    private int resource_distance;
    Vein(String name, int rate, Point position, int resource_distance)
    {
	super(name,position,rate,0,0,resource_distance);
	//this.rate=rate;
	//this.resource_distance=resource_distance;
	
    }
}