import java.util.*;
import java.io.*; 
import processing.core.*;
class WorldView extends PApplet
{
    private int view_cols;
    private int view_rows;
    private PApplet screen;
    private WorldModel world;
    private int tile_width;
    private int tile_height;
    private int mouse_img;
    private Rectangle viewport;
    WorldView(int view_cols,int view_rows,PApplet screen, WorldModel world,int tile_width,int tile_height,int mouse_img)
    {
        viewport = new Rectangle(0,0,view_cols,view_rows);
        this.view_cols = view_cols;
        this.view_rows = view_rows;
        this.world = world;
        this.tile_height = tile_height;
        this.tile_width = tile_width;
        this.mouse_img = None;
	this.screen = screen;
    }
    public void draw_background()
    {
        for (int y=0;y<this.viewport.height;y++)
        {
            for (int x=0;x<this.viewport.width;x++)
            {
                Point w_pt = viewport_to_world(this.viewport,Point(x,y));
                int[] img = this.world.get_background_image(w_pt);
                image(img,x*this.tile_width,y*this.tile_height);
                
            }
        }
    }
    public void draw_entities()
    {
        for(Entity entity : this.world.entities)
        {
            if(this.viewport.collidepoint(entity.position.x,entity.position.y))
            {
                Point v_pt = world_to_viewport(this.viewport,entity.get_position());
                image(entity.get_image(),v_pt.get_x()*this.tile_width,v_pt.get_y()*this.tile_height);
            }
        }
    }
    public void draw_viewport()
    {
        this.draw_background();
        this.draw_entities();
    }
    public void update_view(int view_delta[][])
    {
    }
    // dont need right?? - public void update_view_tiles
    public int[][] create_mouse_surface(boolean occupied)
    {
        int[][] surface = new int[this.tile_width][this.tile_height];
        surface.set_alpha(MOUSE_HOVER_ALPHA);
        Color color = new Color(0,255,0);
        if (occupied)
        {
            color = new Color(255,0,0);
            
        }
        fill(color);
        if (this.mouse_img)
        {
            surface.blit(this.mouse_img,0);
        }
        return surface;
    }
    public void update_mouse_cursor()
    {
        return this.update_tile(this.mouse_pt,this.create_mouse_surface(this.world.is_occupied(viewport_to_world(this.viewport,this.mouse_pt))));
    }
    public void mouse_move(Point new_mouse_pt)
    {
        List<Integer> rects = Collections.emptyList();
        rects.add(this.update_tile(this.mouse_pt,this.get_tile_image(this.mouse_pt)));
        if (this.viewport.collidepoint(new_mouse_pt.get_x()+this.viewport.left,new_mouse_pt.get_y(),this.viewport.top))
        {
            this.mouse_pt = new_mouse_pt;
            
        }
        rects.add(this.update_mouse_cursor());
        
    }
    public void handle_mouse_motion()
    {
    }
    public void one_activity_loop(WorldModel world)
    {
    }
    
}


















