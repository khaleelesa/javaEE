package il.ac.hit.todolist.Model;

/**
 * 
 * @author khaleel esa
 * @author ofir saban
 * Exception handling page 
 */
public class ToDoListExeption extends Exception 
{
	
	private static final long serialVersionUID = 1L;


	public ToDoListExeption(String message) {
		super(" Exception Of kind : " + message);
	}


	public ToDoListExeption(String message, Throwable cause)
	{		
		super("Exception Of kind : " + message, cause);
	}
}

