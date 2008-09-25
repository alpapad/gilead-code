package net.sf.gilead.test.domain.stateful;

import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import net.sf.gilead.test.domain.IMessage;
import net.sf.gilead.test.domain.IUser;

/**
 * Message Java 1.4 domain class for stateful pojo store
 * This class has no inheritance on hibernate4gwt, but must be Serializable for GWT RPC serialization
 * @author bruno.marchesson
 *
 */
public class Message implements Serializable, IMessage
{
	/**
	 * Serialization ID
	 */
	private static final long serialVersionUID = 3421537443957416948L;
	
	//	Fields    
    private int id;
    private Integer version;
    private String message;
    private Date date;
    
    private User author;
    
    private Map<String, Integer> keywords;
    
    // Properties
	/* (non-Javadoc)
	 * @see net.sf.gilead.testApplication.domain.IMessage#getId()
	 */
	public final int getId() {
		return id;
	}
	/* (non-Javadoc)
	 * @see net.sf.gilead.testApplication.domain.IMessage#setId(java.lang.Integer)
	 */
	public final void setId(int id) {
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
	/* (non-Javadoc)
	 * @see net.sf.gilead.testApplication.domain.IMessage#getMessage()
	 */
	public String getMessage() {
		return message;
	}
	/* (non-Javadoc)
	 * @see net.sf.gilead.testApplication.domain.IMessage#setMessage(java.lang.String)
	 */
	public void setMessage(String message) {
		this.message = message;
	}
	/* (non-Javadoc)
	 * @see net.sf.gilead.testApplication.domain.IMessage#getDate()
	 */
	public Date getDate() {
		return date;
	}
	/* (non-Javadoc)
	 * @see net.sf.gilead.testApplication.domain.IMessage#setDate(java.util.Date)
	 */
	public void setDate(Date timeStamp) {
		this.date = timeStamp;
	}
	
	/**
	 * @return the author
	 */
	public IUser getAuthor() {
		return author;
	}
	/**
	 * @param author the author to set
	 */
	public void setAuthor(User author) {
		this.author = author;
	}
	
	/**
	 * @see net.sf.gilead.test.domain.IMessage#getKeywords()
	 */
	private Map<String, Integer> getKeywords()
	{
		return keywords;
	}
	
	/**
	 * @see net.sf.gilead.test.domain.IMessage#setKeywords(java.util.Map)
	 */
	private void setKeywords(Map<String, Integer> keywords)
	{
		this.keywords = keywords;
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
	
	/**
	 * Add a keyword in the associated map
	 * @param key
	 * @param value
	 */
	public void addKeyword(String key, Integer value)
	{
		if (keywords == null)
		{
			keywords = new HashMap<String, Integer>();
		}
		keywords.put(key, value);
	}

	/*
	 * (non-Javadoc)
	 * @see net.sf.gilead.test.domain.IMessage#clearKeywords()
	 */
	public void clearKeywords()
	{
		if (keywords != null)
		{
			keywords.clear();
		}	
	}
	
	/*
	 * (non-Javadoc)
	 * @see net.sf.gilead.test.domain.IMessage#countKeywords()
	 */
	public int countKeywords()
	{
		if (keywords != null)
		{
			return keywords.size();
		}
		else
		{
			return 0;
		}
	}
}
