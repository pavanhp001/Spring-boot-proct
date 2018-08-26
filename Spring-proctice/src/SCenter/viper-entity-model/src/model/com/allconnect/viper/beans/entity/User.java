package com.A.V.beans.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.MapKey;
import javax.persistence.MapKeyColumn;

@Entity
@Table(name = "UAM_USER")
@SequenceGenerator(name = "userSequence", sequenceName = "USER_SEQUENCE")
public class User implements Serializable {

	
	/**
	 * 
	 */
	private static final long serialVersionUID = -3974000976846572315L;

	@Id
	@Column(name = "USER_ID")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "userSequence")
	private long userId;
	
	@Column(name = "USER_NAME")
	private String userName;
	
	@Column(name = "USER_LOGIN")
	private String userLogin;
	
	@Column(name = "USER_LEVEL")
	private Integer userLevel;
	
	@Column(name = "USER_ACTIVE")
	private String userActive;
	
	@Column(name = "USER_TYPE")
	private Integer userType;
	
	@Column(name = "USER_UPDATED")
	private Date userUpdatedDate;
	
    @ManyToMany
	@JoinTable(name = "UAM_USER_USER_RESOURCE", 
			joinColumns = @JoinColumn(name = "USER_ID"), 
			inverseJoinColumns = @JoinColumn(name = "USER_RESOURCE_ID"))
    private List<UserResource> userResources;

    
    @ManyToMany
	@JoinTable(name = "UAM_USER_ROLE", 
			joinColumns = @JoinColumn(name = "USER_ID"), 
			inverseJoinColumns = @JoinColumn(name = "ROLE_ID"))
    private List<Role> roles;
    
    @ElementCollection
    @JoinTable(name="UAM_USER_METADATA", joinColumns = @JoinColumn(name="UAM_USER_ID"))
    @MapKeyColumn(name = "name")
    @Column(name="value")
    @Cascade( { org.hibernate.annotations.CascadeType.ALL } )
	private Map<String, String> metadata;
    
    /**
	 * @return the userId
	 */
	public long getUserId() {
		return userId;
	}


	/**
	 * @return the userActive
	 */
	public String getUserActive() {
		return userActive;
	}

	/**
	 * @return the userLevel
	 */
	public Integer getUserLevel() {
		return userLevel;
	}

	/**
	 * @return the userLogin
	 */
	public String getUserLogin() {
		return userLogin;
	}

	/**
	 * @return the userName
	 */
	public String getUserName() {
		return userName;
	}


	/**
	 * @return the userType
	 */
	public Integer getUserType() {
		return userType;
	}

	/**
	 * @return the userUpdatedDate
	 */
	public Date getUserUpdatedDate() {
		return userUpdatedDate;
	}

	/**
	 * @param id
	 *            the id to set
	 */
	public void setUserId(long userId) {
		this.userId = userId;
	}


	/**
	 * @param userActive
	 *            the userActive to set
	 */
	public void setUserActive(String userActive) {
		this.userActive = userActive;
	}


	/**
	 * @param userLevel
	 *            the userLevel to set
	 */

	public void setUserLevel(Integer userLevel) {
		this.userLevel = userLevel;
	}

	/**
	 * @param userLogin
	 *            the userLogin to set
	 */
	public void setUserLogin(String userLogin) {
		this.userLogin = userLogin;
	}

	/**
	 * @param userName
	 *            the userName to set
	 */
	public void setUserName(String userName) {
		this.userName = userName;
	}


	/**
	 * @param userType
	 *            the userType to set
	 */

	public void setUserType(Integer userType) {
		this.userType = userType;
	}

	/**
	 * @param userUpdatedDate
	 *            the userUpdatedDate to set
	 */
	public void setUserUpdatedDate(Date userUpdatedDate) {
		this.userUpdatedDate = userUpdatedDate;
	}

	public List<Role> getRoles() {
		return roles;
	}

	public void setRoles(List<Role> roles) {
		this.roles = roles;
	}
	
	public List<UserResource> getUserResources() {
		return userResources;
	}


	public void setUserResources(List<UserResource> userResources) {
		this.userResources = userResources;
	}


	public Map<String, String> getMetadata() {
		return metadata;
	}

	public void setMetadata(Map<String, String> metadata) {
		this.metadata = metadata;
	}
	
}
