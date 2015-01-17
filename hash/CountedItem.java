public class CountedItem<E>
{
    E item;
	int count = 1;
	
	public CountedItem(E item)
	{
		this.item = item;
	}
	
	public CountedItem()
	{
		this.item = null;
	}
	
	public void increment()
	{
		count++;
	}
	
	public void decrement()
	{
		count--;
	}
	
	public int getCount()
	{
		return count;
	}
	
	public E getItem()
	{
		return item;
	}
	
	public boolean equals(CountedItem<E> otherItem)
	{
		// compare the actual items, not the count
		return otherItem.getItem().equals(this.getItem());
	}
}