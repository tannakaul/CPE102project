class Obstacle
    extends EntityActions
{
    private String name;
    private Point position;
    Obstacle(String name, Point position)
    {
	super(name,position,0,0,0,0);
    }
}