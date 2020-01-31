package il.ac.hit.todolist.Model;


/**
 * @author khaleel esa 
 * @author ofir  saban
 * Iteminfo class indicates the User Items for  the ToDoList app. 
 */

public class ItemInfo
{
	/**
	 * the id of the item 
	 */
	private long itemId;
	
	/**
	 * the user that the item belongs to
	 *  act also as the connection to the user in the data base
	 */
	private long userOfItem;
	
	/**
	 * the item's info
	 */
	private String info;
	
	
	/**
	 * Default Constructor
	 */
	public ItemInfo()
	{
	}
	
	
	/**
	 * 
	 * @param userOfItem
	 * @param info
	 */
	public ItemInfo(long userOfItem, String info) {
		super();
		setUserOfItem(userOfItem);
		setinfo(info);
	}





	public long getitemId() {
		return itemId;
	}


	public void setitemId(long itemId) {
		this.itemId = itemId;
	}


	public long getUserOfItem() {
		return userOfItem;
	}


	public void setUserOfItem(long userOfItem) {
		this.userOfItem = userOfItem;
	}


	public String getinfo() {
		return info;
	}


	public void setinfo(String info) {
		this.info = info;
	}


	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 * hashCode function to override the Java function.
	 */
	@Override
	public int hashCode() {
		final int prime = 31;     
		int result = 1;
		result = prime * result + ((info == null) ? 0 : info.hashCode());
		result = prime * result + (int) (itemId ^ (itemId >>> 32));
		result = prime * result + (int) (userOfItem ^ (userOfItem >>> 32));
		return result;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 * equals function to override the Java function.
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (!(obj instanceof ItemInfo)) {
			return false;
		}
		ItemInfo other = (ItemInfo) obj;
		if (info == null) {
			if (other.info != null) {
				return false;
			}
		} else if (!info.equals(other.info)) {
			return false;
		}
	
		if (itemId != other.itemId) {
			return false;
		}
		if (userOfItem != other.userOfItem) {
			return false;
		}
		return true;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 * toString function to override the Java function.
	 */
	@Override
	public String toString() {
		return "Item [itemId=" + itemId + ", userId=" + userOfItem + "info=" + info + "]";
	}

}