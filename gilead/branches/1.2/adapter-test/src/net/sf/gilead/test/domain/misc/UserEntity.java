package net.sf.gilead.test.domain.misc;

/**
 * Implementation of IEntity, with additional setter
 * @author bruno.marchesson
 *
 */
public class UserEntity implements IEntity
{ 
	private Integer id; 
	 
	public Integer getId()
	{ 
		return id; 
	} 
	 
	public void setId(Integer id)
	{ 
		this.id = id; 
	} 
}