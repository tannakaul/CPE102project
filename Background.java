//Background class, doesnt extend anything else, figure out if it implements
import java.util.*;
import processing.core.*;
public class Background extends PApplet
{
    private String name;
    private List<PImage> imgs = new LinkedList<PImage>();
    private int current_img;
    Background(String name, List<PImage> imgs)
    {
	
         this.name = name;
        this.imgs = imgs;
        this.current_img = 0;
    }
    public List<PImage> get_images()
    {
        return this.imgs;
    }
    public PImage get_image()
    {
        return this.imgs[this.current_img];
    }
    public void next_image()
    {
        this.current_img = (this.current_img+1)% (this.imgs).length;
    }
    public String get_name()
    {
	return this.name;
    }
}