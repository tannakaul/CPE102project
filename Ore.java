class Ore
//finish this
    extends NonStaticEntity
{
    private String name;
    private Point position;
    private int rate;
    Ore(String name, Point position, int rate)
    {
	super(name,0, position,rate,0,0,0,0,0);
	//this.rate = rate;
    }
    public Entity create_ore(String[] properties, HashMap<String,List<PImage>> i_store)
    {
        if (properties.length == 7)
        {
            Ore ore= Ore(properties[1],Point((int)(properties[2]),(int)(properties[3])),get_images(i_store,properties["ore"]),(int)(properties[4]));
            return ore;
        }
        else
        {
            return null;
        }
    }
}