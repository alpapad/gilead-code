package net.sf.gilead.sample.domain;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import net.sf.gilead.annotations.ReadOnly;
import net.sf.gilead.annotations.ServerOnly;
import net.sf.gilead.pojo.gwt.LightEntity;


/**
 * User Domain class for stateless server
 */
public class User extends LightEntity implements Serializable
{
	/**
	 * Serialisation ID
	 */
	private static final long serialVersionUID = 1058354709157710766L;
	
	// Fields
	private Integer id;
	private Integer version;
	
	@ReadOnly
	private String login;
	private String firstName;
	private String lastName;
	
	@ServerOnly
	private String password;
	
	private Set<Message> messageList;
	
	// Properties
	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}
	
	public Integer getVersion() {
		return version;
	}

	public void setVersion(Integer version) {
		this.version = version;
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
	 * @return the message List
	 */
	public Set<Message> getMessageList() {
		return messageList;
	}

	/**
	 * @param messageList the message List to set
	 */
	public void setMessageList(Set<Message> messageList) {
		this.messageList = messageList;
	}

	/*
	 * (non-Javadoc)
	 * @see net.sf.gilead.testApplication.domain.IUser#addMessage(net.sf.gilead.testApplication.domain.IMessage)
	 */
	public void addMessage(Message message)
	{
		message.setAuthor(this);
		if (messageList == null)
		{
			messageList = new HashSet<Message>();
		}
		messageList.add(message);
	}

	/*
	 * (non-Javadoc)
	 * @see net.sf.gilead.testApplication.domain.IUser#removeMessage(net.sf.gilead.testApplication.domain.IMessage)
	 */
	public void removeMessage(Message message)
	{
		messageList.remove(message);
		message.setAuthor(null);
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
}
