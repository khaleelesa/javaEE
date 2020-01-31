package il.ac.hit.todolist.Controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import il.ac.hit.todolist.Model.HibernateToDoListDAO;
import il.ac.hit.todolist.Model.IToDoListDAO;
import il.ac.hit.todolist.Model.ItemInfo;
import il.ac.hit.todolist.Model.ToDoListExeption;
import il.ac.hit.todolist.Model.UserInfo;


/**
 * 
 * @author khaleel esa
 * @author ofir saban
 *Servlet implementation class ControllerApp
 */
@WebServlet("/controller/*")
public class ControllerApp extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 *connecting to the database via the IToDoListDAO interface 
	 */
	private IToDoListDAO dao = null;
	
	
	 /**
	  * the dispatcher to route to next URL 
	  */
	RequestDispatcher dispatcher = null;
	
	/**
	 * url for requested page
	 */
	String jspPage = "/index.jsp";
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ControllerApp() {
        super();
        dao = HibernateToDoListDAO.getInstance();
    }

	/**
	 *  doGet method to rout all post and get requests to be handled accordingly
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String path = request.getPathInfo();

		switch(path)
		{
		case "/index":
			request.getSession().invalidate();
			jspPage = "/index.jsp";
			break;
		case "/login":
			handleLoginPage(request, response);
			break;
		case "/signup":
			handleSignUpPage(request, response);
			break;
		case "/addItem":
			handleAddItemPage(request, response);
			break;
		case "/userPage":
			handleUserPage(request, response);
			break;
		case "/deleteItem":
			handleDeleteItem(request, response);
			break;
		case "/updateItem":
			handleUpdateItem(request, response);
			break;
		}
		dispatcher = getServletContext().getRequestDispatcher(jspPage);
		dispatcher.forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}
	

	/**
	 *  Handling the login page gets the user info checks if its right and forward to the user page
	 * @param request
	 * @param response
	 * @throws ServletException
	 * @throws IOException
	 */
	private void handleLoginPage(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		jspPage = "/login.jsp";
		String userName = request.getParameter("userName");
		String password = request.getParameter("password");
		
		if (userName != null || password != null)
		{
			if (userName.isEmpty() || password.isEmpty())
			{
				String message = "You forget to fill one of the planks";
				request.setAttribute("message", message);
			}
			else
			{
				try {
					UserInfo user = dao.GetSignedUser(userName, password);
					
					if (null != user)
					{
						request.getSession().setAttribute("userId", user.getUserId());
						handleUserPage(request, response);
					}
					else
					{
						request.setAttribute("message", " wrong userName or password try agian");
					}
				} catch (ToDoListExeption e) {
					e.printStackTrace();
					request.setAttribute("errorMessage", e.getMessage());
					jspPage = "/errorPage.jsp";
				}
			}
		}
	}
	

	/**
	 * Handling the sign up page for creating a new user 
	 * @param request
	 * @param response
	 */
	private void handleSignUpPage(HttpServletRequest request, HttpServletResponse response) {
		jspPage = "/signup.jsp";
		String userName = request.getParameter("userName");
		String lastName = request.getParameter("lastName");
		String password = request.getParameter("password");
		
		if (  userName != null || lastName != null ||  password != null)
		{
			if (userName.isEmpty() || lastName.isEmpty() ||  password.isEmpty())
			{
				String message = "you froget to fill some of the blanks try again";
				request.setAttribute("message", message);
			}
			else
			{
				try {
					UserInfo newUser = new UserInfo(userName, lastName, password);
					request.getSession().setAttribute("userId", newUser.getUserId());
					dao.AddNewUser(newUser);
					try {
						handleLoginPage(request, response);
					} catch (ServletException | IOException e) {
						e.printStackTrace();
					}
				} catch (ToDoListExeption e) {
					e.printStackTrace();
					request.setAttribute("errorMessage", e.getMessage());
					jspPage = "/errorPage.jsp";
				}
			}
		}
	}
	

	/**
	 *  show the user page, the items of the user and allows him to add/update/delete items
	 * @param request
	 * @param response
	 */
	private void handleUserPage(HttpServletRequest request, HttpServletResponse response) {
		try {
			Long userId = (Long)request.getSession().getAttribute("userId");
			List<ItemInfo> userItems = dao.ReturnUserItems(userId);
			request.setAttribute("userItems", userItems);
			UserInfo user = dao.ReturnUserById(userId);
			request.setAttribute("userName", user.getUserName());
			jspPage = "/userPage.jsp";
		} catch (ToDoListExeption e) {
			e.printStackTrace();
			request.setAttribute("errorMessage", e.getMessage());
			jspPage = "/errorPage.jsp";
		}
	}
	
	/**
	 *  method to handle adding items 
	 * @param request
	 * @param response
	 * @throws ServletException
	 * @throws IOException
	 */
	private void handleAddItemPage(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		String itemContent = request.getParameter("itemContent");
		jspPage = "/addItem.jsp";
		if (null != itemContent)
		{
			if (!itemContent.isEmpty())
			{
				Long userId = (Long)request.getSession().getAttribute("userId");
				ItemInfo newItem = new ItemInfo(userId, itemContent);
				try {
					dao.AddNewItem(newItem);
					handleUserPage(request, response);
				} catch (ToDoListExeption e) {
					e.printStackTrace();
					request.setAttribute("errorMessage", e.getMessage());
					jspPage = "/errorPage.jsp";
				}
			}
			else
			{
				request.setAttribute("message", "you need to write a item to add !!");
			}
		}
	}
	
	/**
	 * method to delete selected item
	 * @param request
	 * @param response
	 */
	private void handleDeleteItem(HttpServletRequest request, HttpServletResponse response) 
	{	jspPage = "/userPage.jsp";
	String strItemId = request.getParameter("itemId");
	
	if (null != strItemId)
	{
		if (!strItemId.isEmpty())
		{
			try {
				Long userId = (Long)request.getSession().getAttribute("userId");
				Long itemId = Long.parseLong(strItemId);
				ItemInfo item = dao.GetItem(userId, itemId);
				if (null != item)
				{
					dao.DeleteExistingItem(item);
				}
			} catch (ToDoListExeption e) {
				e.printStackTrace();
				request.setAttribute("errorMessage", e.getMessage());
				jspPage = "/errorPage.jsp";
			}
		}
	}
	handleUserPage(request, response);
	}
	
   
	/**
	 *  Method to update chosen item 
	 * @param request
	 * @param response
	 * @throws ServletException
	 * @throws IOException
	 * updates a given item by using the delete and the addNewItem methods 
	 */
	private void handleUpdateItem(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		jspPage = "/updateItem.jsp";
		String updateContent = request.getParameter("updateContent");
		String strItemId = request.getParameter("itemId");
		Long userId = (Long)request.getSession().getAttribute("userId");
		Long itemId = Long.parseLong(strItemId);
		int flag=2;
		String currinfo_forhint="";
	
		
		if (null != updateContent)
		{
	
				String itemstring = (String)request.getSession().getAttribute("info");
				System.out.println("%s"+updateContent);
				System.out.println("%s"+itemstring);

				

				try {
					Long userIdd = (Long)request.getSession().getAttribute("userId");
					Long itemIdd = Long.parseLong(strItemId);
					ItemInfo newItem = new ItemInfo(userId, updateContent);
					ItemInfo item = dao.GetItem(userIdd, itemIdd);
					System.out.println(""+updateContent);
					System.out.println(""+item.getinfo());
					currinfo_forhint = item.getinfo();
					
					if(!updateContent.isEmpty() && !updateContent.equals(item.getinfo()))
					{
						System.out.println("in the equals (should be zero) %d"+updateContent.equals(item.getinfo()));
						
					if (null != item)
					{

						System.out.println(""+updateContent);
						System.out.println(""+item.getinfo());
						dao.DeleteExistingItem(item);
						dao.AddNewItem(newItem);
						handleUserPage(request, response);
						
						
					}
					}
					else
					{
					flag=0;
					}
					
				} catch (ToDoListExeption e) {
					e.printStackTrace();
					request.setAttribute("errorMessage", e.getMessage());
					jspPage = "/errorPage.jsp";
				}
				
			
		}
			if(flag==0)
			{ 
				//System.out.println("in else %d"+flag);
				request.setAttribute("message", "you need to change your item or cancel");
				try {
					Long userIdd = (Long)request.getSession().getAttribute("userId");
					ItemInfo curItem = dao.GetItem(userIdd, itemId);
					String curContent = curItem.getinfo();
					request.setAttribute("itemContent", curContent);
					request.setAttribute("itemId", strItemId);
				} catch (ToDoListExeption e) {
					e.printStackTrace();
					request.setAttribute("errorMessage", e.getMessage());
					jspPage = "/errorPage.jsp";
				}
			}
		
		else
		{
			try {
				Long userIddd = (Long)request.getSession().getAttribute("userId");
				ItemInfo curItem = dao.GetItem(userIddd, itemId);
				String curContent = currinfo_forhint;
				request.setAttribute("itemContent", curContent);
				request.setAttribute("itemId", strItemId);
			} catch (ToDoListExeption e) {
				e.printStackTrace();
				request.setAttribute("errorMessage", e.getMessage());
				jspPage = "/errorPage.jsp";
			}
		}
		
	}

}
