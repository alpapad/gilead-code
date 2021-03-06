package net.sf.gilead.test.domain.legacy;

import net.sf.gilead.test.domain.interfaces.IEmployee;

/**
 * Employee class.
 * Subclass of User
 * @author bruno.marchesson
 *
 */
public class Employee extends User implements IEmployee
{
    /**
	 * Serialization ID
	 */
	private static final long serialVersionUID = -2294737766711898873L;

	// Fields
	private String email;

	// Properties
	/* (non-Javadoc)
	 * @see net.sf.gilead.test.domain.proxy.IEmployee#getEmail()
	 */
	public String getEmail() {
		return email;
	}

	/* (non-Javadoc)
	 * @see net.sf.gilead.test.domain.proxy.IEmployee#setEmail(java.lang.String)
	 */
	public void setEmail(String email) {
		this.email = email;
	}
}