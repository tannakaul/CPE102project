import java.util.*;
import processing.core.*;

class MinerEntity
    extends EntityActions
{
    private String name;
    private Point position;
    private int rate;
    private int resource_count;
    private int resource_limit;
    private int resource_distance;
    private List<PImage> imgs;
    private PImage current_img;
    
    public MinerEntity(String name,List<PImage> imgs,Point position,int rate,int resource_count,
		       int resource_limit,int resource_distance, int animation_rate, PImage current_img, List<EntityActions> pending_actions)
    {
        super(name,imgs, position, rate,resource_count,
	      resource_limit, resource_distance,animation_rate, current_img, pending_actions);

    }
    public Entity try_transform_miner_not_full(WorldModel world)
    {
        if (this.resource_count<this.resource_limit)
        {
            return this;
        }
        else
        {
            Entity new_entity = MinerFull(this.get_name(),this.get_resource_limit(),this.get_position(),this.get_rate(),this.get_images(),this.get_animation_rate());
            return new_entity;
        }
    }
    public Entity try_transform_miner_full(WorldModel world)
    {
        Entity new_entity = MinerNotFull(this.get_name(),this.get_resource_limit(),this.get_position(),this.get_rate(),this.get_images(),this.get_animation_rate());
        return new_entity;
    }
    public boolean miner_to_ore(WorldModel world, Ore ore)
    {
        Point entity_pt = this.get_position();
        if (! (ore instanceof Ore))
        {
            return false;
        }
        Point ore_pt = ore.get_position();
        if (adjacent(entity_pt,ore_pt))
        {
            this.set_resource_count(1+entity.get_resource_count());
            world.remove_entity(ore);
            return true;
        }
        else
        {
            Point new_pt = world.next_position(entity_pt,ore_pt);
            return false;
        }
    }
    public boolean miner_to_smith(WorldModel world, Blacksmith smith)
    {
        Point entity_pt = this.get_position();
        if (! (smith instanceof Smith))
        {
            return false;
        }
        Point smith_pt = smith.get_position();
        if (adjacent(entity_pt,smith_pt))
        {
            smith.set_resource_count(smith.get_resource_count()+
                                     this.get_resource_count());
            this.set_resource_count(0);
            return true;
        }
        else
        {
            Point new_pt = world.next_position(entity_pt,smith_pt);
            return false;
        }
    }
    public Entity try_transform_miner(WorldModel world) 
    {
        
    }
    public Entity create_miner(String[] properties,HashMap<String,List<PImage>> i_store)
    {
        if (properties.length == 7)
        {
            Miner miner = entites.MinerNotFull(properties[1],(int)(properties[4]),Point((int)(properties[2]),(int)(properties[3])),(int)(properties[5]),get_images(i_store,properties[0]),(int)(properties[6]));
            return miner;
        }
        else
        {
            return null;
        }
    }

    

}























