package il.ac.hit.todolist.Model;

/**
 * 
 * @author khaleel esa
 * @author ofir saban
 * userInfo class indecats the user of the todolist app
 */
public class UserInfo 
{
	
	/**
	 * the user id
	 */
	private long userId;
	/**
	 * the user name
	 */
	private String userName;
	/**
	 * the user last name
	 */
	private String lastName;
	/**
	 * the user password
	 */
	private String password;
	
	/**
	 * Default constructor
	 */
	public UserInfo()
	{
	}
	
	/**
	 * 
	 * @param userName
	 * @param lastName
	 * @param password
	 */
	public UserInfo(String userName,String lastName, String password)
	{
		setUserName(userName);
		setLastName(lastName);
		setPassword(password);
	}

	/**
	 * 
	 * @return user id
	 */
	public long getUserId() {
		return userId;
	}

	/**
	 * 
	 * @param userId
	 */
	public void setUserId(long userId) {
		if(userId != 0)
		this.userId = userId;
	}


	/**
	 * 
	 * @return the user name
	 */
	public String getUserName() {
		return userName;
	}


	/**
	 * 
	 * @param userName
	 */
	public void setUserName(String userName) {
		if(userName != null)
		this.userName = userName;
	}

	/**
	 * 
	 * @return the user last name
	 */
	public String getLastName() {
		return lastName;
	}


	/**
	 * 
	 * @param familyName
	 */
	public void setLastName(String familyName) {
		if(familyName != null)
		this.lastName = familyName;
	}

	/**
	 * 
	 * @return the user password
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * 
	 * @param password
	 */
	public void setPassword(String password) {
		if(password != null)
		this.password = password;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 * hashCode function to override the Java function.
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((userName == null) ? 0 : userName.hashCode());
		result = prime * result + ((lastName == null) ? 0 : lastName.hashCode());
		result = prime * result + ((password == null) ? 0 : password.hashCode());
		result = prime * result + (int) (userId ^ (userId >>> 32));
		
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
		if (!(obj instanceof UserInfo)) {
			return false;
		}
		UserInfo other = (UserInfo) obj;
		
		if (lastName == null) {
			if (other.lastName != null) {
				return false;
			}
		} else if (!lastName.equals(other.lastName)) {
			return false;
		}
		if (password == null) {
			if (other.password != null) {
				return false;
			}
		} else if (!password.equals(other.password)) {
			return false;
		}
		if (userId != other.userId) {
			return false;
		}
		if (userName == null) {
			if (other.userName != null) {
				return false;
			}
		} else if (!userName.equals(other.userName)) {
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
		return " (userId=" + userId + ", userName=" + userName + ",  lastName="
				+ lastName + ", password=" + password + ")";
	}
}

