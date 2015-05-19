import java.util.*;
import processing.core.*;

public class Main extends PApplet
{
    public boolean RUN_AFTER_LOAD = true;
    public String IMAGE_LIST_FILE_NAME = "imagelist";
    public String WORLD_FILE = "gaia.sav";
    public int WORLD_WIDTH_SCALE = 2;
    public int WORLD_HEIGHT_SCALE = 2;
    public int SCREEN_WIDTH = 640;
    public int SCREEN_HEIGHT = 480;
    public int TILE_WIDTH = 32;
    public int TILE_HEIGHT = 32;
    public PImage screen;
    public WorldModel world;
    public WorldView view;
    
    public Background create_default_background(PImage img)
    {
	return new Background("background_default",img);
    }
    public void main_load_world(WorldModel world, Hashmap<String,List<PImage>> i_store, File filename)
    {
    }
    public void setup()
    {
	size(SCREEN_WIDTH,SCREEN_HEIGHT);
	Background(100,100,100)
	Hashmap<String,List<PImage>> i_store = load_images(IMAGE_LIST_FILE_NAME,TILE_WIDTH,TILE_HEIGHT);
	
	int num_cols = (int)(SCREEN_WIDTH)/(int)(TILE_WIDTH*WORLD_WIDTH_SCALE);
	int num_rows = (int)(SCREEN_HEIGHT)/(int)(TILE_HEIGHT*WORLD_HEIGHT_SCALE);
	
	world = WorldModel(num_rows,num_cols,default_background);
	view = WorldView((int)(SCREEN_WIDTH)/(int)(TILE_WIDTH),(int)(SCREEN_HEIGHT)/(int)(TILE_HEIGHT),screen,world,TILE_WIDTH,TILE_HEIGHT);
	
	load_world(world,i_store,WORLD_FILE,RUN_AFTER_LOAD);
	
	next_time = System.currentTimeMillis() + 100;

    }
    public void next_image()
    {
	for (Entity e : this.world.get_entities)
	    {
		e.next_image();
    }
    
    public void draw()
    {
	long time = System.currentTimeMillis();
	if (time>=next_time)
	    {
		next_images();
		next_time = time+100;
		this.world.update_on_time(time);
	    }
	this.view.draw_viewport();
    }
    public void keyPressed()
    {
	int dx = 0;
	int dy = 0;
	switch (key)
	    {
	    case 'w':
		{
		dy = -1;
		break;
		}
	    case 's':
		{
		dy = 1;
		break;
		}
	    case 'd':
		{
		    dx = 1;
		    break;
		}
	    case 'a':
		{
		    dx = -1;
		    break;
		}
	    }
	view.update_view(dx,dy);
    }
    
    public static void main(String[] args)
    {
	PApplet.main("Main");
	
    }
}
    
    