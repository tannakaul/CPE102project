public class Grid
{
    private int width;
    private int height;
    private int occupancy_value;
    private List<Integer> cells;
    public Grid(int width,int height,int occupancy_value)
    {
	this.width = width;
	this.height = height;
	cells = new ArrayList<Integer>();
	for (int row = 0; row< this.height; row++)
	    {
		this.cells.add(Collections.emptyList());
		for (int col =0;col<this.width;col++)
		    {
			this.cells[row].append(occupancy_value)
			    }
	    }
    }
    public void set_cell(Point pt, int value)
    {
	this.cells[pt.get_y()][pt.get_x()] = value;
    }
    public int[][] get_cell(Point pt)
    {
	return this.cells[pt.get_y()][pt.get_x()];
    }
}
	