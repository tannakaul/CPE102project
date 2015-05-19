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
    public Entity create_blacksmith(String[] properties, HashMap<String,List<PImage>> i_store)
    {
        if (properties.length == 7)
        {
            Blacksmith blacksmith = Blacksmith(properties[1],Point((int)(properties[2]),(int)(properties[3])),get_images(i_store,properties["blacksmith"]),(int)(properties[4]),(int)(properties[5]),(int)(properties[6]));
            return blacksmith;
        }
        else
        {
            return null;
        }
    }
    
}