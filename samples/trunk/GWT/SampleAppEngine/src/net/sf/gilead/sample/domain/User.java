package net.sf.gilead.sample.domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Version;

import org.datanucleus.jpa.annotations.Extension;

/**
 * User Domain class for JAVA5 server
 */
@Entity
@Table(name="user")
public class User implements Serializable
{
	/**
	 * Serialisation ID
	 */
	private static final long serialVersionUID = 1058354709157710766L;
	
	// Fields
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="ID")
	@Extension(vendorName="datanucleus", key="gae.encoded-pk", value="true")
	private String id;
    
	@Version
	@Column(name="VERSION")
	private Long version;
	
	@Column(name="LOGIN", nullable=false, length=45)
	private String login;
	
	@Column(name="FIRST_NAME", nullable=false, length=45)
	private String firstName;
	
	@Column(name="LAST_NAME", nullable=false, length=45)
	private String lastName;
	
	@Column(name="PASSWORD", length=45)
	private String password;
	
	@OneToMany(cascade = CascadeType.ALL)
	private List<Message> messageList;

	// Properties
	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}
	
	public Long getVersion() {
		return version;
	}

	public void setVersion(Long version) {
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
