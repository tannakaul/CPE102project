import java.util.*;
import processing.core.*;

class MinerNotFull
    extends MinerEntity
{
    private String name;
    private int resource_limit;
    private Point position;
    private double rate;
    private List<PImage> imgs;
    private int animation_rate;
    ArrayList<EntityActions> pending_actions;
    
    MinerNotFull(String name,int resource_limit,Point position,
                 int rate,List<PImage> imgs,int animation_rate)
    {
        super(name,position,rate,0,resource_limit,0,animation_rate,0,pending_actions);
        this.resource_count =0;
        this.current_img = 0;
	pending_actions = new ArrayList<EntityActions>();
        this.pending_actions = pending_actions;
        
	
        
    }
    
}