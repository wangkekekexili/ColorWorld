import java.util.LinkedList;
import java.util.Collections;
import java.util.Comparator;

public class CountedLinkedList<E>
{
  private final Comparator<CountedItem<E>> comparator = new Comparator<CountedItem<E>>()
	{
		@Override
	    public int compare(final CountedItem<E> o1, final CountedItem<E> o2)
		{
			// compare the count of the items
	        return Integer.valueOf(o2.getCount()).compareTo(Integer.valueOf(o1.getCount()));
	    }
	};
	private boolean sorted = true;
	private LinkedList<CountedItem<E>> items = new LinkedList<CountedItem<E>>();
	
	public CountedLinkedList()
	{
	}
	
	public CountedItem<E> add(E item)
	{
		// set sorted as false and get the item
		sorted = false;
		CountedItem<E> currentItem = get(item);
		
		// if it already exists, just increment it
		if (currentItem != null)
			currentItem.increment();
		else
		{
			// else, add it
			currentItem = new CountedItem<E>(item);
	        items.add(currentItem);
		}
		
		return currentItem;
	}
	
	public CountedItem<E> remove(E item)
	{
		// loop through and find the item
		for (int i = 0; i < items.size(); i++)
		{
			CountedItem<E> listItem = items.get(i);
			if (listItem.getItem().equals(item))
			{
				// set sorted as false
				sorted = false;
				if (listItem.getCount() > 1)
				{
					// decrement it if it's greater than 1
					listItem.decrement();
					return listItem;
				}
				
				// else, remove it
				items.remove(i);
				break;
			}
		}
		
		return null;
	}
	
	public CountedItem<E> get(E item)
	{
		// loop through and find the item
		for (CountedItem<E> listItem : items)
			if (listItem.getItem().equals(item))
				return listItem;
		
		return null;
	}
	
	@SuppressWarnings("unchecked")
	public CountedItem<E>[] getSortedArray()
	{
		// if it's not already sorted, sort it
		if (!sorted)
		{
			Collections.sort(items, this.comparator);
			sorted = true;
		}
		
		// return it as an array
		CountedItem<E>[] array = items.toArray(items.toArray(new CountedItem[items.size()]));
		return array;
	}
}