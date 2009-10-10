package net.sf.gilead.sample.domain;

import java.io.Serializable;
import java.util.Date;

/**
 * Message Java 1.4 domain class for dynamic proxy generation
 * This class just has to implements the Serializable interface
 * @author bruno.marchesson
 *
 */
public class Message implements Serializable
{
	/**
	 * Inner type.
	 * No  business meaning, just for debug
	 */
	public static class MessageType implements Serializable
	{ 
		private static final long serialVersionUID = 1L; 
		private String type; 
		
		public MessageType()
		{
		}
		
		public MessageType(String typeString)
		{ 
			this.type = typeString; 
		}
		
		public String getType() {
			return type;
		}
		public void setType(String type) {
			this.type = type;
		} 
	} 
		 
	
	/**
	 * Serialisation ID
	 */
	private static final long serialVersionUID = 3445339493203407152L;
	
	//	Fields    
    private Integer id;
    private Integer version;
    private String message;
    private Date date;
    
    private User author;
    
    public MessageType MESSAGE_TYPE_INFO = new MessageType("info"); 

    // Properties
	/**
	 * @return the id
	 */
	public final Integer getId() {
		return id;
	}
	/**
	 * @param id the id to set
	 */
	public final void setId(Integer id) {
		this.id = id;
	}
	/**
	 * @return the version
	 */
	public Integer getVersion() {
		return version;
	}
	/**
	 * @param version the version to set
	 */
	public void setVersion(Integer version) {
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
		return id.equals(other.getId());
	}
}