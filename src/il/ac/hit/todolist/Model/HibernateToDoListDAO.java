package il.ac.hit.todolist.Model;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.AnnotationConfiguration;

/**
 * 
 * @author khaleel esa
 * @author ofir saban
 * the Hibernate Dao implements the Dao interface to execute the methods
 */
public class HibernateToDoListDAO implements IToDoListDAO {

	
	/**
	 * the singleton instance of this class 
	 */
	public static HibernateToDoListDAO instance = new HibernateToDoListDAO();
	SessionFactory factory = new AnnotationConfiguration().configure().buildSessionFactory();
	
	
	/**
	 * private default constructor to prevent instances of this class
	 */
	private HibernateToDoListDAO()
	{
	}
	
	
	/**
	 * the interface for using the HibernateToDoListDAO class
	 * @return the class instance
	 */
	public static HibernateToDoListDAO getInstance()
	{
		return instance;
	}
	
	
	/* (non-Javadoc)
	 * @see il.ac.hit.todolist.Model.IToDoListDAO#AddNewUser(il.ac.hit.todolist.Model.UserInfo)
	 * adds a new user to the database using session 
	 * first we check if there is a existing user by using IsUserExisiting method from the userinfo class
	 * if true we threw a error message if false we add the user to the database
	 */
	@Override
	public boolean AddNewUser(UserInfo newUser) throws ToDoListExeption {
		boolean result = false;
		Session session = null;
		Transaction transaction = null;
		
		if (!IsUserExisting(newUser))
		{
			try
			{
				session = factory.openSession();
				transaction = session.beginTransaction();
				session.save(newUser);
				transaction.commit();
				result = true;
			}
			catch(HibernateException exception)
			{
				rollbackTrasaction(transaction);
				throw new ToDoListExeption(exception.getMessage(), exception.getCause());
			} 
			finally
			{
				closeSession(session);
			}
		}
		return result;
	}

	
	/* (non-Javadoc)
	 * @see il.ac.hit.todolist.Model.IToDoListDAO#UpdateUserInfo(il.ac.hit.todolist.Model.UserInfo)
	 * we check if the user is existing by using the IsUserExisiting method from userinfo class
	 * if true we do update for the user in the session
	 * if false we threw an error message
	 */
	@Override
	public boolean UpdateUserInfo(UserInfo user) throws ToDoListExeption {
		boolean result = false;
		Session session = null;
		Transaction transaction = null;
		
		if (IsUserExisting(user))
		{
			try
			{
				session = factory.openSession();
				transaction = session.beginTransaction();
				session.update(user);
				transaction.commit();
				result = true;
			}
			catch(HibernateException exception)
			{
				rollbackTrasaction(transaction);
				throw new ToDoListExeption(exception.getMessage(), exception.getCause());
			} 
			finally
			{
				closeSession(session);
			}
		}
		return result;
	}
	
	/* (non-Javadoc)
	 * @see il.ac.hit.todolist.Model.IToDoListDAO#DeleteExistingUser(il.ac.hit.todolist.Model.UserInfo)
	 * Deletes an Existing User from the database using session.delete and also deletes all the user item
	 */
	@Override
	public boolean DeleteExistingUser(UserInfo user) throws ToDoListExeption {
		boolean result = false;
		Session session = null;
		Transaction transaction = null;
		
		{
			if (IsUserExisting(user))
			try 
			{
				session = factory.openSession();
				transaction = session.beginTransaction();
				
				List<ItemInfo> listOfItems = ReturnUserItems(user.getUserId());
				for (ItemInfo item : listOfItems) {
					session.delete(item);
				}
				session.delete(user);		
				transaction.commit();
		
				result = true;
			}
			catch(HibernateException exception)
			{
				rollbackTrasaction(transaction);
				throw new ToDoListExeption(exception.getMessage(), exception.getCause());
			} 
			finally
			{
				closeSession(session);
			}			
		}
		return result;
	}

	/* (non-Javadoc)
	 * @see il.ac.hit.todolist.Model.IToDoListDAO#IsUserExisting(il.ac.hit.todolist.Model.UserInfo)
	 * checks if the user is existing in the database using method session.get 
	 * 
	 */
	@Override
	public boolean IsUserExisting(UserInfo user) throws ToDoListExeption {
		boolean exist = false;
		Session session = null;
		try
		{
			session = factory.openSession();
			Object object = session.get(user.getClass(), user.getUserId());
			exist = object != null;
		}
		catch(HibernateException exception)
		{
			throw new ToDoListExeption(exception.getMessage(), exception.getCause());
		} 
		finally
		{
			closeSession(session);
		}
		
		return exist;
	}


	/* (non-Javadoc)
	 * @see il.ac.hit.todolist.Model.IToDoListDAO#ReturnUserById(long)
	 * returns a user by his id
	 */
	@Override
	public UserInfo ReturnUserById(long userId) throws ToDoListExeption {
		Session session = null;
		UserInfo user = null;
		
		try
		{
			session = factory.openSession();
			user = (UserInfo)session.get(UserInfo.class, userId);
		}
		catch(HibernateException exception)
		{
			throw new ToDoListExeption(exception.getMessage(), exception.getCause());
		} 
		finally
		{
			closeSession(session);
		}
		
		return user;
	}
	
	
	/* (non-Javadoc)
	 * @see il.ac.hit.todolist.Model.IToDoListDAO#GetSignedUser(java.lang.String, java.lang.String)
	 */
	@Override
	public UserInfo GetSignedUser(String userName, String password) throws ToDoListExeption {
		
		UserInfo user = null;
		Session session = null;
		
		try
		{
			session = factory.openSession();
			Query query = session.createQuery("FROM UserInfo WHERE userName = :userName AND password = :password");
			query.setParameter("userName", userName);
			query.setParameter("password", password);
			user = (UserInfo)query.uniqueResult();
		}
		catch(HibernateException exception)
		{
			throw new ToDoListExeption(exception.getMessage(), exception.getCause());
		} 
		finally
		{
			closeSession(session);
		}
		
		return user;
	}

	                                      
	/////////////////////////////    Handling item info   ////////////////////////////////////////////////////////////////

	
	/* (non-Javadoc)
	 * @see il.ac.hit.todolist.model.IToDoListDAO#getUserItems(long)
	 * return a list of a given user items
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<ItemInfo> ReturnUserItems(long userId) throws ToDoListExeption {
		Session session = null;
		Transaction transaction = null;
		List<ItemInfo> listOfItems = new ArrayList<ItemInfo>();

		if (null != ReturnUserById(userId))
		{
			try
			{
				session = factory.openSession();
				transaction = session.beginTransaction();
				Query query = session.createQuery("FROM ItemInfo WHERE userOfItem = :userId");
				query.setParameter("userId", userId);
				listOfItems = query.list();
			} 
			catch(HibernateException exception)
			{
				rollbackTrasaction(transaction);
				throw new ToDoListExeption(exception.getMessage(), exception.getCause());
			} 
			finally
			{
				closeSession(session);
			}		
		}
		return listOfItems;

	}

	/* (non-Javadoc)
	 * @see il.ac.hit.todolist.model.IToDoListDAO#AddNewItem(il.ac.hit.todolist.Model.ItemInfo)
	 * adds new item to a given user
	 */
	@Override
	public boolean AddNewItem(ItemInfo newItem) throws ToDoListExeption {
		boolean result = false;
		Session session = null;
		Transaction transaction = null;
		
		if (!IsItemExisting(newItem) && (null != ReturnUserById(newItem.getUserOfItem())))
		{
			try
			{
				
				session = factory.openSession();
				transaction = session.beginTransaction();
				session.save(newItem);
				transaction.commit();
				result = true;
			}
			catch(HibernateException exception)
			{
				rollbackTrasaction(transaction);
				throw new ToDoListExeption(exception.getMessage(), exception.getCause());
			} 
			finally
			{
				closeSession(session);
			}
		}
		
		return result;
	}

	

	/* (non-Javadoc)
	 * @see il.ac.hit.todolist.Model.IToDoListDAO#UpdateExisitngItem(il.ac.hit.todolist.Model.ItemInfo)
	 * updates the info of an existing item
	 */
	@Override
	public boolean UpdateExisitngItem(ItemInfo item) throws ToDoListExeption {
		boolean result = false;
		Session session = null;
		Transaction transaction = null;
		
		if (!IsItemExisting(item) && (null != ReturnUserById(item.getUserOfItem())))
		{
			try
			{
				session = factory.openSession();
				transaction = session.beginTransaction();
				session.save(item);
				transaction.commit();
				result = true;
			}
			catch(HibernateException exception)
			{
				rollbackTrasaction(transaction);
				throw new ToDoListExeption(exception.getMessage(), exception.getCause());
			} 
			finally
			{
				closeSession(session);
			}
		}
		
		return result;
	}

	/* (non-Javadoc)
	 * @see il.ac.hit.todolist.Model.IToDoListDAO#DeleteExistingItem(il.ac.hit.todolist.Model.ItemInfo)
	 * delete an existing item using the session delete method
	 */
	@Override
	public boolean DeleteExistingItem(ItemInfo item) throws ToDoListExeption {
		boolean result = false;
		Session session = null;
		Transaction transaction = null;
		
		if (IsItemExisting(item))
		{
			try
			{
				session = factory.openSession();
				transaction = session.beginTransaction();
				session.delete(item);
				transaction.commit();			
				result = true;
			}
			catch(HibernateException exception)
			{
				rollbackTrasaction(transaction);
				throw new ToDoListExeption(exception.getMessage(), exception.getCause());
			} 
			finally
			{
				closeSession(session);
			}
		}
		return result;
	}

	/* (non-Javadoc)
	 * @see il.ac.hit.todolist.Model.IToDoListDAO#IsItemExisting(il.ac.hit.todolist.Model.ItemInfo)
	 * checks if the item is existing 
	 * we use this method in  the update/delete item methods
	 */
	@Override
	public boolean IsItemExisting(ItemInfo item) throws ToDoListExeption {
		boolean exist = false;
		Session session = null;
		Object object = null;
		
		try
		{
			session = factory.openSession();
			object = session.get(item.getClass(), item.getitemId());
			exist = object != null;
		}
		catch(HibernateException exception)
		{
			throw new ToDoListExeption(exception.getMessage(), exception.getCause());
		} 
		finally
		{
			closeSession(session);
		}
		
		return exist;
	}

	/* (non-Javadoc)
	 * @see il.ac.hit.todolist.Model.IToDoListDAO#GetItem(il.ac.hit.todolist.Model.ItemInfo)
	 */
	@Override
	public ItemInfo GetItem(long userId, long itemId) throws ToDoListExeption {
		List<ItemInfo> listOfItems = instance.ReturnUserItems(userId);
		ItemInfo returnItem = null;
		for (ItemInfo item : listOfItems) 
		{
			if (item.getitemId() == itemId)
			{
				returnItem = item;
			}
		}
		
		return returnItem;
	}
	
	/**
	 * closing the given session
	 * @param session
	 * @throws ToDoListException
	 */
	private void closeSession(Session session) throws ToDoListExeption
	{
		if (null != session)
		{
			try
			{
				session.close();
			}
			catch (HibernateException exception)
			{
				throw new ToDoListExeption(exception.getMessage(), exception.getCause());
			}
		}
	}
	
	
	/**
	 * roll back the transaction if any Hibernate exception was thrown 
	 * @param transaction
	 */
	private void rollbackTrasaction(Transaction transaction)
	{
		if (null != transaction)
		{
			transaction.rollback();
		}
	}



}
