import java.util.Arrays;
import java.util.List;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.*;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import org.junit.Test;

public class TestCases
{
   private static final double DELTA = 0.00001;

   @Test
   public void testEntity()
   {
       Point pt = new Point(1.0,2.0);
       Entity thing = new Entity("hello",pt);
       //assertEquals(thing.set_position(pt),null);
       assertEquals(thing.get_position(),pt);
       //assertEquals(thing.get_name(),"hello");
   }
    @Test
    public void testBackground()
    {
        Background thing = new Background("thing");
        assertEquals(thing.get_name(),"thing");
    }

    @Test
    public void testMinerNotFull()
    {
	Point pt = new Point(1.0,2.0);
	MinerNotFull thing = new MinerNotFull("thing",1,pt,1);
	assertEquals(thing.get_name(),"thing");
    }
    @Test
    public void testVein()
    {
        Point pt = new Point(1.0,2.0);
        Vein thing = new Vein("vein",1,pt,1);
        assertEquals(thing.get_position(),pt);
    }
    @Test
    public void testVeinrate()
    {
        Point pt = new Point(1.0,2.0);
        Vein thing = new Vein("vein",1,pt,1);
        assertEquals(thing.get_rate(),1);
    }
    @Test
    public void testOre()
    {
        Point pt = new Point(1.0,2.0);
        Ore thing = new Ore("ore",pt,1);
        assertEquals(thing.get_rate(),1);
    }

    @Test
    public void testBlacksmith()
    {
        Point pt = new Point(1.0,2.0);
        Ore thing = new Ore("ore",pt,1);
        assertEquals(thing.get_rate(),1);
    }

    
    
    
    
    
    @Test
    public void testwithinbounds()
    {
	int row = 5;
	int col = 5;
	int [][] twoDlist = new int[row][col];
	WorldModel wm = new WorldModel(row,col,twoDlist);
	Point pt = new Point(1,1);
	assertTrue(wm.within_bounds(pt));
	//assertTrue(wm.is_occupied(pt));
    }
    @Test
	public void testMinerFull()
    {
       	Point pt = new Point(1.0,2.0);
        Entity thing = new MinerFull("thing",1,pt,1);
        assertEquals(thing.get_name(),"thing");
    }
	

    
    @Test
    public void testWorldModelis_occupied()
    {
        int row = 5;
        int col = 5;
        int twoDlist[][] = new int[row][col];
        Point pt = new Point(1,1);
        Entity james = new Entity("james",pt);
        WorldModel wm = new WorldModel(row,col,twoDlist);
        assertTrue(wm.is_occupied(pt) == false);

    }
   

    @Test
	public void testnearest_entity()
    {
	int row = 5;
    int col = 5;
    int [][] twoDlist = new int[row][col];
    WorldModel wm = new WorldModel(row,col,twoDlist);

	List<Entity> elist = new LinkedList<Entity>();
	Point origpoint = new Point(5,5);
	Point ore1point = new Point(1,1);
	Point ore2point = new Point(4,5);
	Point ore3point = new Point(3,3);
	Entity ore1 = new Ore("ore",ore1point,1);
	Entity ore2 = new Ore("ore",ore2point,1);
	Entity ore3 = new Ore("ore",ore3point,1);
	elist.add(ore1);
	elist.add(ore2);
	elist.add(ore3);
	assertEquals(ore2,wm.nearest_entity(elist,origpoint));
    }



   /* @Test
	public void testfind_nearest()
    {
        int row = 5;
        int col = 5;
        int [][] twoDlist = new int[row][col];
        WorldModel wm = new WorldModel(row,col,twoDlist);
        
       	List<Entity> elist = new LinkedList<Entity>();
        Point origpoint = new Point(5,5);
        Point ore1point = new Point(1,1);
        Point ore2point = new Point(4,5);
        Point ore3point = new Point(3,3);
        Entity ore1 = new Ore("ore",ore1point,1);
        Entity ore2 = new Ore("ore",ore2point,1);
        Entity ore3 = new Ore("ore",ore3point,1);
        elist.add(ore1);
        elist.add(ore2);
        elist.add(ore3);
        assertEquals(ore2,wm.find_nearest(origpoint,Class.forName("Ore")));
 
    }
}

    @Test
    public void testremove_entity_at()
    {
        Point pt = new Point(1,1);
        int 2Dlist[][] = new int[5][5];
        WorldModel wm = new WorldModel(5,5,2Dlist);
        wm.remove_entity_at(pt);
        
	
	}
    */
    
    @Test
    public void testsign()
    {
        int row = 5;
        int col = 5;
        int [][] twoDlist = new int[row][col];
        WorldModel wm = new WorldModel(row,col,twoDlist);
        int x = 2;
        assertEquals(wm.sign(x),1);
    }
    
/*
    @Test
    public void testnext_position()
    {
        int row = 5;
        int col = 5;
        int [][] twoDlist = new int[row][col];
        WorldModel wm = new WorldModel(row,col,twoDlist);
        Point pt1 = new Point(6,6);
        Point pt2 = new Point(7,7);
        Point pt3 = new Point(5,6);
        assertEquals(pt3,wm.next_position(pt1,pt2));
        
    }
}
  */
    
    @Test
    public void testget_tile_occupant_NULL()
    {
        int row = 5;
        int col = 5;
        int [][] twoDlist = new int[row][col];
        WorldModel wm = new WorldModel(row,col,twoDlist);
        Point pt = new Point(10,10);
        assertEquals(wm.get_tile_occupant(pt),null);
    }
    
    @Test
    public void testget_entities()
    {
        int row = 5;
        int col = 5;
        int [][] twoDlist = new int[row][col];
        WorldModel wm = new WorldModel(row,col,twoDlist);
        assertEquals(wm.get_entities(),Collections.emptyList());
    }
    
  /*  @Test
    public void testblobnextposition()
    {
        int row = 5;
        int col = 5;
        int [][] twoDlist = new int[row][col];
        WorldModel wm = new WorldModel(row,col,twoDlist);

	Point entity_pt = new Point(1,1);
	Point dest_pt = new Point(3,3);
	assertEquals(wm.blob_next_position(entity_pt,dest_pt)
		     //calculate this later
    }

*/
    @Test
    public void testfind_open_around()
    {
        int row = 5;
        int col = 5;
        int [][] twoDlist = new int[row][col];
        WorldModel wm = new WorldModel(row,col,twoDlist);

        Point pt = new Point(1,1);
        int distance = -200;
        assertEquals(wm.find_open_around(pt,distance), null);
    }
    
}
