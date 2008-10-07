package net.sf.gilead.sample.domain;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Version;

import net.sf.gilead.pojo.java5.LightEntity;


/**
 * Message domain class for Java5 Hibernate POJO
 * @author bruno.marchesson
 *
 */
@Entity
@Table(name="message")
public class Message extends LightEntity implements Serializable
{
	/**
	 * Serialisation ID
	 */
	private static final long serialVersionUID = -1067096371173906324L;
	
	//	Fields    
    private int id;
    private Integer version;
    private String message;
    private Date date;
    
    private User author;
    
    // Properties
	/* (non-Javadoc)
	 * @see net.sf.hibernate4gwt.testApplication.domain.IMessage#getId()
	 */
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    @Column(name="ID")
	public final int getId() {
		return id;
	}
	/* (non-Javadoc)
	 * @see net.sf.hibernate4gwt.testApplication.domain.IMessage#setId(java.lang.Integer)
	 */
	public final void setId(int id) {
		this.id = id;
	}
	/**
	 * @return the version
	 */
	@Version
	@Column(name="VERSION")
	public Integer getVersion() {
		return version;
	}
	/**
	 * @param version the version to set
	 */
	public void setVersion(Integer version) {
		this.version = version;
	}
	/* (non-Javadoc)
	 * @see net.sf.hibernate4gwt.testApplication.domain.IMessage#getMessage()
	 */
	@Column(name="MESSAGE", nullable=false, length=255)
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
	@Column(name="DATE", nullable=false)
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
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="USER_ID", nullable=false)
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
