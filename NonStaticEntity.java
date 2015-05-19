import java.util.List;
import java.util.ArrayList;
import java.util.*;
import processing.core.*;



class NonStaticEntity extends Entity
 

{
    private String name;
    private Point position;
    private int rate;
    private int resource_count;
    private int resource_limit;
    private int resource_distance;
    private List<PImage> imgs;
    private int current_img;
    private int animation_rate;
    
    public NonStaticEntity(String name,List<PImage> imgs, Point position,int rate, int resource_count, int resource_limit,int resource_distance, int animation_rate,int current_img)
    {
	super(name,position,imgs,current_img);
	this.rate = rate;
	this.imgs = imgs;
	this.resource_count = resource_count;
	this.resource_limit = resource_limit;
	this.resource_distance = resource_distance;
	this.animation_rate = animation_rate;
	this.current_img = current_img;
    }
    public int get_rate()
    {
	return this.rate;
    }
    public void set_resource_count(int n)
    {
	this.resource_count = n;
    }
    public int get_resource_count()
    {
	return this.resource_count;
    }
    public int get_resource_limit()
    {
	return this.resource_limit;
    }

    public int get_resource_distance()
    {
	return this.resource_distance;
    }
    public LinkedList<Entity> move_entity(WorldModel world, Point pt)
    {
        List tiles = new LinkedList<Entity>();
        if (world.within_bounds(pt) == true)
        {
            Point old_pt = this.get_position();
            this.set_cell(old_pt,null);
            tiles.add(old_pt);
            tiles.add(pt);
            this.set_position(pt);
        }
        return tiles;
    }
    public boolean blob_to_vein(WorldModel world,Vein vein)
    {
        //dont know what to do with the list of tuples,
        //becasue if i make a 2d array, they wont have the same type??
        //one will be boolean and one will be entity??
        Point entity_pt = this.get_position();
        if (! (vein instanceof Vein))
        {
            return false;
        }
        Point vein_pt = vein.get_position();
        if (adjacent(entity_pt,vein_pt))
        {
            world.remove_entity(vein);
            return true;
            
        }
        else
        {
            Point new_pt = world.blob_next_position(entity_pt,vein_pt);
            old_entity = world.get_tile_occupant(new_pt);
            if (old_entity instanceof Ore)
            {
                world.remove_entity(old_entity);
            }
            return false;
        }
        }
    }



    









