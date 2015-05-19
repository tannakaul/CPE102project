import java.util.*;
import processing.core.*;

public class OrderedList
{
    List<ListItem> list; 
    
    public OrderedList()
    {
	list = new ArrayList<ListItem>();
    }
    public void insert(Action item,long ord)
    {
	int size = this.list.length;
	int idx = 0;
	while (idx < size && this.list[idx].ord < ord)
	    {
		idx += 1;
	    }
	this.list[idx] = ListItem(item,ord);
    }
    public void remove(Action item)
    {
	int size = this.list.length;
	int idx = 0;
	while (idx < size && this.list[idx].item != item)
	    {
		idx += 1;
	    }
	if (idx < size)
	    {
		List<ListItem> mylist = new List<ListItem>();
		this.list[idx+1] = myList;
	    }
    }
    public int head()
    {
	if (this.list)
	    {
		return this.list[0];
	    }
	else
	    {
		return null;
	    }
    }
    public List<Integer> pop()
    {
	if (this.list)
	    {
		return this.list.pop(0);
	    }
    }
}

	