/*import java.util.*;
import processing.core.*;
import java.util.function.LongConsumer;
class EntityActions
    extends ScheduleEntity

{
    private String name;
    private Point position;
    private int rate;
    private int resource_count;
    private int resource_limit;
    private int resource_distance;
    private List<EntityActions> pending_actions;
    public EntityActions(String name,List<PImage> imgs, Point position,int rate,int resource_count,
			 int resource_limit,int resource_distance, int animation_rate, PImage current_img, List<LongConsumer> pending_actions)
    {
        super(name, imgs,position, rate,resource_count,
	      resource_limit, resource_distance,animation_rate,current_img);
	this.pending_actions = pending_actions;
      
    }
    
    public void remove_pending_action(Action action)
    {
        if (this.hasAttribute("pending_actions"))
        {
            this.pending_actions.remove(action);
        }
    }
    public void add_pending_action(Action action)
    {
        if (this.hasAttribute("pending_actions"))
        {
            this.pending_actions.add(action);
        }
    }
    public ArrayList<Action> get_pending_actions()
    {
        if (this.hasAttribute("pending_actions"))
        {
            return this.pending_actions;
        }
        else
        {
            List<LongConsumer> blankL = new ArrayList<LongConsumer>();
            return blankL;
        }
    }
    public void  clear_pending_actions()
    {
        if (this.hasAttribute("pending_actions"))
        {
            List<LongConsumer> blank = new ArrayList<LongConsumer>();
            this.pending_actions = blank;
        }
    }
    public void schedule_action(WorldModel world, LongConsumer action,int time)
                                  {
                                      this.add_pending_action(action);
                                      world.new_schedule_action(action,time);
                                  }
    public static LongConsumer create_ore_blob_action(WorldModel world,HashMap<String,List<PImage>> i_store)

    {//help
        LongConsumer[] action = { null };
        action[0] = (int current_ticks) ->
        {
            this.remove_pending_action(action[0]);
            Point entity_pt = this.get_position();
            Vein vein = world.find_nearest(entity_pt,Vein);
            boolean found = this.blob_to_vein(world,vein);
            
            int next_time = current_ticks + this.get_rate();
            if (found)
            {
                Quake quake = world.create_quake(tiles[0],current_ticks,i_store);
                world.new_add_entity(quake);
                int next_time = current_ticks +this.get_rate()*2;
                
            }
            this.schedule_action(world,this.create_ore_blob_action(world,i_store),next_time);
            return tiles;
        };
        return action[0];
        
    }
    public static LongConsumer create_vein_action(WorldModel world,HashMap<String,List<PImage>> i_store)
 
    {
        LongConsumer[] action = { null };
        action[0] = (int current_ticks) ->
        {
            this.remove_pending_action(action[0]);
            boolean open_pt = world.find_open_around(this.get_position(),this.get_resource_distance());
            if (open_pt)
            {
                Ore ore = world.create_ore("ore - " + this.get_name() + " - "
                                           + str(current_ticks),open_pt,current_ticks,i_store);
                world.new_add_entity(this);
		
            }
	    this.schedule_action(world,this.create_vein_action(world,i_store),current_ticks+this.get_rate());
	    return tiles;
        };
	return action[0];
    }
    public static LongConsumer create_animation_action(WorldModel world, int repeat_count)
    {
        LongConsumer[] action = { null };
        action[0] = (int current_ticks) ->
        {
            this.remove_pending_action(action[0]);
            this.next_image();
            if (repeat_count != 1)
            {
                this.schedule_action(world,this.create_animation_action(
                                                                        world, max(repeat_count-1,0)),current_ticks + this.get_animation_rate());
                List[] myList = new ArrayList();
                myList.add(this.get_position());
                return myList;
            }
        };
        return action[0];
    }
    public static LongConsumer create_entity_death_action(WorldModel world)
    {
        LongConsumer[] action = { null };
        action[0] = (int current_ticks) ->
        {
            this.remove_pending_action(action);
            Point pt = new Point(this.get_position());
            world.remove_entity(this);
            List[] myList = new ArrayList();
            myList.add(pt);
            return myList;
        };
        return action[0];
    }
    public static LongConsumer create_ore_transform_action(WorldModel world,HashMap<String,List<PImage>> i_store)
    {
        LongConsumer[] action = { null };
        action[0]= (int current_ticks) ->
        {
            this.remove_pending_action(action[0]);
            Blob blob = world.create_blob(this.get_name() + "-- blob",this.get_position(),((int)(this.get_rate())/(int)(4)),current_ticks,i_store);
            world.remove_entity(this);
            world.new_add_entity(blob);
            List[] myList = new ArrayList();
            myList.add(blob.get_position());
            return myList;
        };
        return action[0];
        
    }


    public static LongConsumer create_miner_action(WorldModel world,HashMap<String,List<PImage>> i_store)
    {
        if (this instanceof MinerNotFull)
        {
            return this.create_miner_not_full_action(world,i_store);
        }
        else
        {
            return this.create_mine_full_action(world,i_store);
        }
    }
    public LongConsumer create_miner_not_full_action(WorldModel world,HashMap<String,List<PImage>> i_store)
    {
        LongConsumer[] action = { null };
        action[0] = (int current_ticks) ->
        {
            this.remove_pending_action(action[0]);
            entity_pt = this.get_position();
            Ore ore = world.find_nearest(entity_pt,Ore);
            boolean found = this.miner_to_ore(world,ore);
            Entity new_entity = this;
            if (found)
            {
                new_entity = this.try_transform_miner(world,
						      this.try_transform_miner_not_full);
            }
            new_entity.schedule_action(world,new_entity.create_miner_action(
                                       world,i_store),current_ticks+
                                       new_entity.get_rate());
            return tiles;
            
        };
        return action[0];
    }
    public static LongConsumer create_miner_full_action(WorldModel world,HashMap<String,List<PImage>> i_store)
    {
        LongConsumer[] action = { null };
        action[0] = (int current_ticks) ->
        {
            this.remove_pending_action(action[0]);
            entity_pt = this.get_position();
            Blacksmith smith = world.find_nearest(entity_pt,Blacksmith);
            boolean found = this.miner_to_smith(world,smith);
            Entity new_entity = this;
            if (found)
            {
                new_entity = this.try_transform_miner(world,
						      this.try_transform_miner_full);
            }
            new_entity.schedule_action(world,new_entity.create_miner_action(
                                    world,i_store),current_ticks+
                                       new_entity.get_rate());
            return tiles;
            
        };
        return action[0];
    }

}*/