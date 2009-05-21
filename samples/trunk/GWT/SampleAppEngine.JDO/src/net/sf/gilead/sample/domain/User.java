package net.sf.gilead.sample.domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.jdo.annotations.Extension;
import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.IdentityType;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;

/**
 * User Domain class for JAVA5 server
 */
@PersistenceCapable(identityType=IdentityType.APPLICATION, detachable="true")
public class User implements Serializable
{
	/**
	 * Serialisation ID
	 */
	private static final long serialVersionUID = 1058354709157710766L;
	
	// Fields
	@PrimaryKey
	@Persistent(valueStrategy=IdGeneratorStrategy.IDENTITY)
	@Extension(vendorName="datanucleus", key="gae.encoded-pk", value="true")
	private String id;
    
	@Persistent
	private String login;
	
	@Persistent
	private String firstName;
	
	@Persistent
	private String lastName;
	
	@Persistent
	private String password;
	
	@Persistent(mappedBy="author")
	private List<Message> messageList;

	// Properties
	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}
	
	public String getLogin() {
		return this.login;
	}

	public void setLogin(String surname) {
		this.login = surname;
	}

	public String getFirstName() {
		return this.firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return this.lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	
	public String getPassword() {
		return this.password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	/**
	 * @return the messageList
	 */
	public List<Message> getMessageList() {
		return messageList;
	}

	/**
	 * @param messageList the messageList to set
	 */
	public void setMessageList(List<Message> messageList) {
		this.messageList = messageList;
	}

	/*
	 * (non-Javadoc)
	 * @see net.sf.hibernate4gwt.testApplication.domain.IUser#addMessage(net.sf.hibernate4gwt.testApplication.domain.IMessage)
	 */
	public void addMessage(Message message)
	{
		message.setAuthor(this);
		
		// Create message list if needed
		if (messageList == null)
		{
			messageList = new ArrayList<Message>();
		}
		messageList.add((Message)message);
	}

	/*
	 * (non-Javadoc)
	 * @see net.sf.hibernate4gwt.testApplication.domain.IUser#removeMessage(net.sf.hibernate4gwt.testApplication.domain.IMessage)
	 */
	public void removeMessage(Message message)
	{
		messageList.remove(message);
	}
	
	/**
	 * Equality function
	 */
	public boolean equals(Object obj)
	{
		if ((obj == null) ||
			(obj instanceof User == false))
		{
			return false;
		}
		else if (this == obj)
		{
			return true;
		}
		
		// ID comparison
		User other = (User) obj;
		return (id == other.getId());
	}
	
	public String toString()
	{
		return this.login + "[" + this.id + "]";
	}
}
