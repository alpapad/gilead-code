package net.sf.gilead.sample.domain;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Version;

import org.datanucleus.jpa.annotations.Extension;


/**
 * Message domain class for Java5 Hibernate POJO
 * @author bruno.marchesson
 *
 */
@Entity
@Table(name="message")
public class Message implements Serializable
{
	/**
	 * Serialisation ID
	 */
	private static final long serialVersionUID = -1067096371173906324L;
	
	//	Fields    
	@Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name="ID")
    @Extension(vendorName="datanucleus", key="gae.encoded-pk", value="true")
	private String id;
    
	@Version
	@Column(name="VERSION")
	private Long version;
    
	@Column(name="MESSAGE", nullable=false, length=255)
	private String message;
    
	@Column(name="DATE", nullable=false)
	private Date date;
    
	@ManyToOne(fetch=FetchType.LAZY, targetEntity=User.class)
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
	/**
	 * @return the version
	 */
	public Long getVersion() {
		return version;
	}
	/**
	 * @param version the version to set
	 */
	public void setVersion(Long version) {
		this.version = version;
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
}
