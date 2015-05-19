import java.util.*;
import processing.core.*;

class ScheduleEntity
    extends NonStaticEntity
{
    private String name;
    private Point position;
    private int rate;
    private int resource_count;
    private int resource_limit;
    private int resource_distance;
    private List<PImage> imgs;
    private PImage current_img;
    public ScheduleEntity(String name,List<PImage> imgs,Point position,int rate, int resource_count, int resource_limit,int resource_distance, int animation_rate, PImage current_img)

    {
	super(name,imgs, position,rate,resource_count,
	      resource_limit,resource_distance, animation_rate,current_img);
    }

    public void schedule_entity(WorldModel world,HashMap<String,List<PImage>> i_store)
    {
        if (this instanceof MinerNotFull)
        {
            this.schedule_miner(world,0,i_store);
        }
        else if (this instanceof Vein)
        {
            this.schedule_vein(world,0,i_store);
        }
        else if (this instanceof Ore)
        {
            this.schedule_ore(world,0,i_store);
        }
    }
    public void schedule_vein(WorldModel world, int ticks, HashMap<String,List<PImage>> i_store)
    {
        this.schedule_action(world,this.create_vein_action(world,i_store),ticks+this.get_rate);
    }
    public void schedule_quake(WorldModel world, int ticks)
    {
        world.schedule_animation(this,QUAKE_STEPS);
        this.schedule_action(world,this.create_entity_death_action(world),ticks+QUAKE_DURATION);
    }
    public void schedule_ore(WorldModel world,int ticks,HashMap<String,List<PImage>> i_store)
    {
        this.schedule_Action(world,this.create_ore_transform_action(world,i_store),ticks+this.get_rate());
    }
    public void schedule_miner(WorldModel world,int ticks,HashMap<String,List<PImage>> i_store)
    {
        this.schedule_action(world,this.create_miner_action(world,i_store),ticks+this.get_rate());
        world.schedule_animation(this);
    }
    public void schedule_blob(WorldModel world,int ticks, HashMap<String,List<PImage>> i_store)
    {
        this.schedule_action(world,this.create_ore_blob_action(world,i_store),ticks+this.get_rate());
        world.schedule_animation(this);
    }
    
}


































