package net.sf.gilead.sample.domain;

import java.io.Serializable;
import java.util.Date;

import net.sf.gilead.pojo.java5.LightEntity;

/**
 * Message Java domain class for stateless pojo store
 * This class just has to inherit from LightEntity
 * @author bruno.marchesson
 *
 */
public class Message extends LightEntity implements Serializable
{
	/**
	 * Serialisation ID
	 */
	private static final long serialVersionUID = 3445339493203407152L;
	
	//	Fields    
    private int id;
    private int version;
    private String message;
    private Date date;
    
    private User author;
    
    // Properties
	/**
	 * @return the id
	 */
	public final int getId() {
		return id;
	}
	/**
	 * @param id the id to set
	 */
	public final void setId(int id) {
		this.id = id;
	}
	/**
	 * @return the version
	 */
	public int getVersion() {
		return version;
	}
	/**
	 * @param version the version to set
	 */
	public void setVersion(int version) {
		this.version = version;
	}
	/**
	 * @return the message
	 */
	public String getMessage() {
		return message;
	}
	/**
	 * @param message the message to set
	 */
	public void setMessage(String message) {
		this.message = message;
	}
	/**
	 * @return the timeStamp
	 */
	public Date getDate() {
		return date;
	}
	/**
	 * @param timeStamp the timeStamp to set
	 */
	public void setDate(Date timeStamp) {
		this.date = timeStamp;
	}
	/**
	 * @return the author
	 */
	public User getAuthor() {
		return author;
	}
	/**
	 * @param author the author to set
	 */
	public void setAuthor(User author) {
		this.author = (User) author;
	}
	
	/**
	 * Equality function
	 */
	public boolean equals(Object obj)
	{
		if (obj == null)
		{
			return false;
		}
		else if (this == obj)
		{
			return true;
		}
		
		// ID comparison
		Message other = (Message) obj;
		return (id == other.getId());
	}
}