package com.A.ui.domain;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

/**
 * @author mahesh
 *
 */
@Entity
@Table(name = "USER_RESOURCES")
@SequenceGenerator(name = "userResourceSequence", sequenceName = "USER_RESOURCES_SEQ")
public class UserResourceEntity implements Serializable  {

	private static final long serialVersionUID = 9832981267818L;

	@Id
	@Column(name = "ID")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "userResourceSequence")
	private long id;
	
	@Column(name = "NAME", nullable = false, length = 25)
	private String name;
	
	@Column(name = "DESCRIPTION", nullable = false, length = 100)
	private String description;
	
	@Column(name = "USER_LEVEL")
	private int userLevel;

	
	/**
	 * @return
	 */
	public long getId() {
		return id;
	}

	/**
	 * @param id
	 */
	public void setId(long id) {
		this.id = id;
	}

	/**
	 * @return
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return
	 */
	public int getUserLevel() {
		return userLevel;
	}

	/**
	 * @param userLevel
	 */
	public void setUserLevel(int userLevel) {
		this.userLevel = userLevel;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	
}
