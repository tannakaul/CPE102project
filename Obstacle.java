class Obstacle
    extends EntityActions
{
    private String name;
    private Point position;
    Obstacle(String name, Point position)
    {
	super(name,position,0,0,0,0);
    }
    public Entity create_obstacle(String[] properties, Hashmap<String,List<PImage>> i_store)
    {
        if (properties.length == 7)
        {
            Obstacle obstacle = Obstacle(properties[1],Point((int)(properties[2]),(int)(properties[3])),get_images(i_store,properties["obstacle"]));
            return obstacle;
        }
        else
        {
            return null;
        }
    }
}