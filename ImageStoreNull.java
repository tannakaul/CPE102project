public class ImageStoreNull extends PApplet
{

    public String DEFAULT_IMAGE_NAME = ("background_default");
    public Color DEFAULT_IMAGE_COLOR = color(128,128,128,0);
    private static final int COLOR_MASK = 0xffffff;


    public static PImage create_default_image(int tile_width,int tile_height)
    {
	
    public static Hashmap<String,List<PImage>> get_images_internal(Hashmap<String,List<PImage>> images, int key)
    {
	if (images.contains(key))
	    {
		return images[key];
	    }
	else
	    {
		return images[];
	    }
    }
    public static Hashmap<String,List<PImage>> get_images(Hashmap<String,List<PImage>> images,int key)
    {
        if(images.contains(key))
        {
            return images[key];
        }
        else
        {
            return images["background_default"];
        }
    }
    public static Hashmap<String,List<PImage>> load_images(int tile_width,int tile_height)
    {
	Hashmap<String,List<PImage>> images = new Hashmap<String,List<PImage>>();
	try
	    {
		Scanner myFile = new Scanner(new FileInputStream("imagelist"));
		while(myFile.hasNextLine())
		    {
			String [] words = myFile.nextLine().split("\\s");
			for (String word : words)
			    {
				this.process_image_line(images,words[0]);
			    }
			if (!(images.contains(DEFAULT_IMAGE_NAME)))
			    {
				Hashmap<String,List<PImage>> default_image = create_default_image(tile_width,tile_height);
				images[DEFAULT_IMAGE_NAME] = default_image;
			    }
		    }
		return images;
	    }
    }
    public static PImage setAlpha(PImage img, int maskColor, int alpha)
   {
      int alphaValue = alpha << 24;
      int nonAlpha = maskColor & COLOR_MASK;
      img.format = PApplet.ARGB;
      img.loadPixels();
      for (int i = 0; i < img.pixels.length; i++)
      {
         if ((img.pixels[i] & COLOR_MASK) == nonAlpha)
         {
            img.pixels[i] = alphaValue | nonAlpha;
         }
      }
      img.updatePixels();
      return img;
   }
    public static void process_image_line(Hashmap<String,List<PImage>> images, String line)
    {
	String [] attrs = line.split("\\s");
	if (attrs.length >= 2)
	    {
		String key = attrs[0];
		PImage img = loadImage(attrs[0]);
		if (img)
		    {
			PImage imgs = get_images_internal(images, key);
			imgs.add(img);
			images[key] = imgs;
			if (attrs.length == 6)
			    {
				int r = (int)(attrs[2]);
				int g = (int)(attrs[3]);
				int b = (int)(attrs[4]);
				int a = (int)(attrs[5]);
				fill(r,g,b,a);
			    }
		    }
	    }
    }

}