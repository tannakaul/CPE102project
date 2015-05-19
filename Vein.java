import java.util.*;
import processing.core.*;
class Vein
    extends NonStaticEntity
{
    private String name;
    private int rate;
    private Point position;
    private int resource_distance;
    Vein(String name, int rate, Point position, int resource_distance)
    {
	super(name,0,position,rate,0,0,resource_distance);
	//this.rate=rate;
	//this.resource_distance=resource_distance;
	
    }
    public Entity create_vein(String[] properties, HashMap<String,List<PImage>> i_store)
    {
        if (properties.length == 7)
        {
            Vein vein = entites.Vein(properties[1],(int)(properties[4]),Point((int)(properties[2]),(int)(properties[3])),get_images(i_store,properties["vein"]),(int)(properties[4]));
            return vein;
        }
        else
        {
            return null;
        }
    }
}