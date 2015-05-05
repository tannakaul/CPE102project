class Blacksmith
    extends EntityActions
{
    private String name;
    private Point position;
    private int resource_limit;
    private int rate;
    private int resource_distance = 1;
    private int resource_count;
    
    Blacksmith(String name, Point position, int resource_limit, 
	       int rate, int resource_distance)
    {
	super(name, position,rate,0,resource_limit,resource_distance);
	//this.resource_limit = resource_limit;
	//this.resource_count = 0;
	//this.rate = rate;
	//this.resource_distance = resource_distance;
    }
}