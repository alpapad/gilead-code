package net.sf.gilead.sample.domain;

import java.io.Serializable;
import java.util.Date;

import javax.jdo.annotations.Extension;
import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.IdentityType;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;


/**
 * Message domain class for Java5 Hibernate POJO
 * @author bruno.marchesson
 *
 */
@PersistenceCapable(identityType=IdentityType.APPLICATION, detachable="true")
public class Message implements Serializable
{
	/**
	 * Serialisation ID
	 */
	private static final long serialVersionUID = -1067096371173906324L;
	
	//	Fields    
	@PrimaryKey
	@Persistent(valueStrategy=IdGeneratorStrategy.IDENTITY)
	@Extension(vendorName="datanucleus", key="gae.encoded-pk", value="true")
	private String id;
    
	@Persistent
	private String message;
    
	// Detached Date bug workaround (http://code.google.com/p/datanucleus-appengine/issues/detail?id=30&q=Date)
	@Persistent
	@Extension(vendorName = "datanucleus", key = "is-second-class", value="false")
	private Date date;
    
	@Persistent
	private User author;
    
    // Properties
	/* (non-Javadoc)
	 * @see net.sf.hibernate4gwt.testApplication.domain.IMessage#getId()
	 */
    public final String getId() {
		return id;
	}
	/* (non-Javadoc)
	 * @see net.sf.hibernate4gwt.testApplication.domain.IMessage#setId(java.lang.Integer)
	 */
	public final void setId(String id) {
		this.id = id;
	}
	
	/* (non-Javadoc)
	 * @see net.sf.hibernate4gwt.testApplication.domain.IMessage#getMessage()
	 */
	public String getMessage() {
		return message;
	}
	/* (non-Javadoc)
	 * @see net.sf.hibernate4gwt.testApplication.domain.IMessage#setMessage(java.lang.String)
	 */
	public void setMessage(String message) {
		this.message = message;
	}
	/* (non-Javadoc)
	 * @see net.sf.hibernate4gwt.testApplication.domain.IMessage#getDate()
	 */
	public Date getDate() {
		return date;
	}
	/* (non-Javadoc)
	 * @see net.sf.hibernate4gwt.testApplication.domain.IMessage#setDate(java.util.Date)
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
		if ((obj == null) ||
			(obj instanceof Message == false))
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
	
	public String toString()
	{
		return this.message + "[" + this.id + "]";
	}
}
