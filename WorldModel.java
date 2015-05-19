import java.util.*;
import java.io.*;
import java.lang.Math;
import processing.core.*;
public class WorldModel
{
    private Background[][] background;
    private Entity[][] occupancy;
    private int num_rows;
    private int num_cols;
    List<Entity> entities = new ArrayList<Entity>();
    List<Integer> action_queue = new ArrayList<Integer>();
    public WorldModel(int num_cols,int num_rows,Background[][] background)
    {
        this.num_cols = num_cols;
        this.num_rows = num_rows;
        this.background = background;
        this.occupancy = occupancy;
        background = new Background[num_rows][num_cols];
        occupancy = new Entity[num_rows][num_cols];
	
	this.entities = entities;
        this.action_queue = action_queue;
    }
    public boolean within_bounds(Point pt)
    {
        return (pt.get_x() >= 0 && pt.get_y() < this.num_cols && pt.get_y() >= 0
                && pt.get_y() < this.num_rows);
    }
    public boolean is_occupied(Point pt)
    {
        return (this.within_bounds(pt) &&
                (occupancy[(int)(pt.get_x())][(int)(pt.get_y())] != null));
    }
    public double dist(Point pt1,Point pt2)
    {
	return Math.sqrt((pt1.get_x()-pt2.get_x())*(pt1.get_x()-pt2.get_x())+
			 (pt1.get_y()-pt2.get_y())*(pt1.get_y()-pt2.get_y()));
    }
    public Entity nearest_entity(List<Entity> elist, Point pt)
    {
	Point point = new Point(0,0);
	Entity closest = new Entity("name",point);
	//closest = elist[0];
	for (Entity e : elist)
	    {
		if (dist(e.get_position(),pt)<
		    (dist(closest.get_position(),pt)))
		    {
			closest = e;
		    }
	    }
	return closest;
    }
		
    public Entity find_nearest(Point pt, Class type)
    {
	List<Entity> elist = new LinkedList<Entity>();
        for (Entity e : entities)
        {
            if (type.isInstance(e))
            {
		elist.add(e);
            }
        }
	return nearest_entity(elist,pt);
   
    }
    public void remove_entity_at(Point pt)
    {
        if (this.is_occupied(pt) == true)
        {
            
	    Entity entity = this.occupancy[(int)(pt.get_x())][(int)(pt.get_y())];
            entity.set_position(new Point(-1,-1));
            this.entities.remove(entity);
            this.occupancy[(int)(pt.get_x())][(int)(pt.get_y())] = null;
        }
    }
    public int sign(int x)
    {
        if (x < 0)
            return -1;
        else if (x > 0)
            return 1;
        else
            return 0;
    }
    public Point next_position(Point entity_pt, Point dest_pt)
    {
        
        int horiz = sign((int)(dest_pt.get_x() - entity_pt.get_x()));
        Point new_pt = new Point(entity_pt.get_x() + horiz, entity_pt.get_y());
        
        if (horiz == 0 || this.is_occupied(new_pt))
        {
            int vert = sign((int)(dest_pt.get_y() - entity_pt.get_y()));
            new_pt = new Point(entity_pt.get_x(),entity_pt.get_y()+vert);
            
            if (vert == 0 || this.is_occupied(new_pt))
            {
                new_pt = new Point(entity_pt.get_x(),entity_pt.get_y());
            }
        }
	return new_pt;
    }
    
    public Entity get_tile_occupant(Point pt)
    {
        if (this.within_bounds(pt))
        {
            return this.occupancy[(int)(pt.get_x())][(int)(pt.get_y())];
        }
	return null;
    }
    public List<Entity> get_entities()
    {
        return this.entities;
    }

    /*  public List<Integer> update_on_time(int ticks)
    {
        List<Integer> tiles = new ArrayList<Integer>();
        double next = this.action_queue.head();
        while (next && next.ord < ticks)
        {
            this.action_queue.pop();
            tiles.extend(next.item(ticks));
            next = this.action_queue.head();
        }
        return tiles;
	}*/
    public Point blob_next_position(Point entity_pt,Point dest_pt)
    {
        
        int horiz = sign((int)(dest_pt.get_x() - entity_pt.get_x()));
        Point new_pt = new Point(entity_pt.get_x() + horiz, entity_pt.get_y());
    
        if (horiz == 0 || this.is_occupied(new_pt) && !(this.get_tile_occupant(new_pt) instanceof Ore))
        {
            int vert = sign((int)(dest_pt.get_y() - entity_pt.get_y()));
            new_pt = new Point(entity_pt.get_x(),entity_pt.get_y()+vert);
            
            if (vert == 0 || this.is_occupied(new_pt) && !(this.get_tile_occupant(new_pt) instanceof Ore))

            {
                new_pt = new Point(entity_pt.get_x(),entity_pt.get_y());
            }
        }
	return new_pt;
    }
    public Point find_open_around(Point pt, int distance)
    {
        for (int dx = -distance; dx < (distance+1); dx++)
        {
            for (int dy = -distance; dy< (distance+1); dy++)
            {
                Point new_pt = new Point(pt.get_x() + dx,pt.get_y() + dy);
                
                if (this.within_bounds(new_pt) && ! this.is_occupied(new_pt))
                {
                    return new_pt;
                }
            }
        }
        return null;
    }
    public Entity create_blob(String name,Point pt,int rate, int ticks,HashMap<String,List<PImage>> i_store)
    {
        Entity blob = new OreBlob(name,pt,rate,get_images(i_store,"blob"),Random(1,3)*BLOB_ANIMATION_RATE_SCALE);
        blob.schedule_blob(ticks,i_store);
        return blob;
    }
    public Entity create_ore(String name,Point pt,int ticks,HashMap<String,List<PImage>> i_store)
    {
        Entity ore = new Ore(name,pt,get_images(i_store,"ore"),Random(20000,30000));
        ore.schedule_ore(this,ticks,i_store);
        return ore;
    }
    public Entity create_quake(Point pt,int ticks,HashMap<String,List<PImage>> i_store)
    {
        Entity quake = new Quake("quake",pt,get_images(i_store,"quake"), QUAKE_ANIMATION_RATE);
        quake.schedule_quake(this,ticks);
        return quake;
    }
    public Entity create_vein(String name,Point pt,int ticks,HashMap<String,List<PImage>> i_store)
    {
        Entity vein = new Vein("vein" + name,Random(VEIN_RATE_MIN,VEIN_RATE_MAX),pt,get_images(i_store,"vein"));
        return vein;
    }

    public void load_world(HashMap<String,List<PImage>> images,File file,boolean run)
    {
        for (String line : file)
        {
            File properties = line.split("\\s");
            if(properties)
		{
		    if (properties[PROPERTY_KEY] == BGND_KEY)
			{
			    this.add_background(properties,images);
			}
		    else
			{
			    this.add_entity(properties,images,run);
			}

		}
	}
    }
    public void add_background(List<String> properties,HashMap<String,List<PImage>> i_store)
    {
        if (properties.length >= BGND_NUM_PROPERTIES)
        {
            int[] col_properties = new int[BGND_COL];
            col_properties.add(BGND_ROW);
            Point pt = new Point((int)(col_properties[0]),(int)(col_properties[1]));
            String name = ("background");
            this.set_background(pt,Background(name,get_images(i_store,name)));
        }
    }
    public void add_entity(List<String> properties,HashMap<String,List<PImage>> i_store,boolean run)
    {
        Entity new_entity = create_from_properties(properties,i_store);
        if (new_entity)
        {
            this.new_add_entity(new_entity);
            if(run)
            {
                this.schedule_entity(i_store);
            }
        }
    }
    public void new_load_world(HashMap<String,List<PImage>> i_store, File filename)
    {
        //with open stuff
    }
    public void new_remove_entity(Entity entity)
    {
        for (IntToLongFunction action : entity.get_pending_actions())
        {
            this.unschedule_action(action);
        }
        entity.clear_pending_actions();
        this.remove_entity(entity);
    }
    public void new_schedule_action()
    {
    
    }
    public void schedule_animation(Entity entity, int repeat_count)
    {
        repeat_count = 0;
        entity.schedule_action(this,entity.create_animation_action(this,repeat_count),entity.get_animation_rate());
    }
    public void clear_pending_actions(Entity entity)
    {
        for (IntToLongFunction action : entity.get_pending_actions())
        {
            this.unschedule_action(action);
            
        }
        entity.clear_pending_actions();
    }

    public void remove_entity(Entity entity)
    {
        this.remove_entity_at(entity.get_position());
    }
    public void new_add_entity(Entity entity)
    {
        Point pt = entity.get_position();
        if (this.within_bounds(pt))
        {
            Enitity old_entity = this.get_cell(pt);
            
        }
    }
		    


    


}



























  