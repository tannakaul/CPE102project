class OreBlob
    extends EntityActions
{
    private String name;
    private Point position;
    private int rate;
    OreBlob(String name, Point position,int rate)
    {
	super(name,position,rate,0,0,0);
	//this.rate = rate;
	
    }
}