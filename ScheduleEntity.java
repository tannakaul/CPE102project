class ScheduleEntity
    extends NonStaticEntity
{
    private String name;
    private Point position;
    private int rate;
    private int resource_count;
    private int resource_limit;
    private int resource_distance;
    public ScheduleEntity(String name,Point position,int rate, int resource_count, int resource_limit,int resource_distance)

    {
	super(name,position,rate,resource_count,
	      resource_limit,resource_distance);
    }



}