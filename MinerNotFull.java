class MinerNotFull
    extends MinerEntity
{
    private String name;
    private int resource_limit;
    private Point position;
    private double rate;

    
    MinerNotFull(String name,int resource_limit,Point position,
                 int rate)
    {
        super(name,position,rate,0,resource_limit,0);
	//this.rate=rate;
	//this.resource_count =0;
	//this.resource_limit=resource_limit;
	
        
    }
    
}