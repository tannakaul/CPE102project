class EntityActions
    extends ScheduleEntity
{
    private String name;
    private Point position;
    private int rate;
    private int resource_count;
    private int resource_limit;
    private int resource_distance;
    public EntityActions(String name,Point position,int rate,int resource_count,
	int resource_limit,int resource_distance)
    {
        super(name, position, rate,resource_count,
	       resource_limit, resource_distance);
	//this.rate=rate;
	//this.resource_count =resource_count;
	//this.resource_limit = resource_limit;
      
    }






}