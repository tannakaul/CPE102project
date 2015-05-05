class MinerFull
    extends MinerEntity
{
    private String name;
    private int resource_limit;
    private Point position;
    private int rate;
    private double animation_rate;
    private double resource_count;
    MinerFull(String name, int resource_limit,Point position,
              int rate)
    {
        super(name,position,rate,resource_limit,resource_limit,0);
	//this.rate = rate;
	//this.resource_limit = resource_limit;
        this.resource_count = resource_limit;
	//this.animation_rate = animation_rate;
    }
}