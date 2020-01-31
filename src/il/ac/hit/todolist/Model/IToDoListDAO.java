package il.ac.hit.todolist.Model;

import java.util.List;

/**
 * 
 * @author khaleel esa
 * @author ofir saban
 * the Dao inteface for all the user/item methods that we execute in the HiparnateDao class
 */
public interface IToDoListDAO 
{
	/**
	 * 
	 * @param newUser
	 * @return
	 * @throws ToDoListExeption
	 */
	public boolean AddNewUser(UserInfo newUser) throws ToDoListExeption;
	
	/**
	 * 
	 * @param user
	 * @return
	 * @throws ToDoListExeption
	 */
	public boolean UpdateUserInfo(UserInfo user) throws ToDoListExeption;
	
	/**
	 * 
	 * @param user
	 * @return
	 * @throws ToDoListExeption
	 */
	public boolean DeleteExistingUser(UserInfo user) throws ToDoListExeption;
	
	/**
	 * 
	 * @param user
	 * @return
	 * @throws ToDoListExeption
	 */
	public boolean IsUserExisting(UserInfo user) throws ToDoListExeption;
	
	/**
	 * 
	 * @param userName
	 * @param password
	 * @return
	 * @throws ToDoListExeption
	 */
	public UserInfo GetSignedUser(String userName, String password) throws ToDoListExeption;
	
    /**
     * 
     * @param userId
     * @return
     * @throws ToDoListExeption
     */
	public UserInfo ReturnUserById(long userId) throws ToDoListExeption;
	
	/**
	 * 
	 * @param userId
	 * @return
	 * @throws ToDoListExeption
	 */
	public List<ItemInfo> ReturnUserItems(long userId) throws ToDoListExeption;
	
	////////////////////Handling item methods///////////////////////////////
	
	/**
	 * 
	 * @param newItem
	 * @return
	 * @throws ToDoListExeption
	 */
	public boolean AddNewItem(ItemInfo newItem) throws ToDoListExeption;
	
    /**
     * 
     * @param item
     * @return
     * @throws ToDoListExeption
     */
	public boolean UpdateExisitngItem(ItemInfo item) throws ToDoListExeption;
	
    /**
     * 
     * @param item
     * @return
     * @throws ToDoListExeption
     */
	public boolean DeleteExistingItem(ItemInfo item) throws ToDoListExeption;
	
	/**
	 * 
	 * @param item
	 * @return
	 * @throws ToDoListExeption
	 */
	public boolean IsItemExisting(ItemInfo item) throws ToDoListExeption;
	
	/**
	 * 
	 * @param userId
	 * @param itemId
	 * @return
	 * @throws ToDoListExeption
	 */
	public ItemInfo GetItem(long userId, long itemId) throws ToDoListExeption;
}
