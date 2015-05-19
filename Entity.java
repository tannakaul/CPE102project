 import java.util.*;
import processing.core.*;
class Entity
{
    private String name;
    private Point position; 
    private List<PImage> imgs;
    private int current_img;
    Entity(String name, Point position,List<PImage> imgs, int current_img)
    {
	this.current_img = current_img;
	this.imgs = imgs;
        this.name = name;
        this.position = position;
    }
    public void set_position(Point point)
    {
        this.position = point;
	
	}
    public Point get_position()
    {
        return this.position;
    }
    public String get_name()
    {
        return this.name;
    }
    public List<PImage> get_images()
    {
	return this.imgs;
    }
    public PImage get_image()
    {
	return this.imgs[this.current_img];
    }
    public static Entity create_from_properties(String[] properties, HashMap<String,List<PImage>> i_store)
    {
        String key = properties[0];
        if (properties)
        {
            if (key == "miner")
            {
                return create_miner(properties,i_store);
            }
            else if (key == "vein")
            {
                return create_vein(properties,i_store);
            }
            else if (key == "ore")
            {
                return create_ore(properties,i_store);
            }
            else if (key == "blacksmith")
            {
                return create_blacksmith(properties,i_store);
            }
            else if (key == "obstacle")
            {
                return create_obstacle(properties,i_store);
            }
        }
        return null;
    }
}