package com.A.V.beans.entity;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;


@Entity
@Table(name = "UAM_ROLE")
@SequenceGenerator(name = "roleSequence", sequenceName = "ROLE_SEQUENCE")
public class Role implements Serializable {

	
	/**
	 * 
	 */
	private static final long serialVersionUID = -6707931572025487540L;

	@Id
	@Column(name = "ROLE_ID")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "roleSequence")
	long roleId;
	
	@Column(name = "ROLE_NAME")
	String roleName;
	
	@Column(name = "ROLE_DESC")
	String roleDesc;
	
	@Column(name = "OWNER_EXTERNAL_ID")
	String ownerExternalId;

    @ManyToMany
	@JoinTable(name = "UAM_ROLE_RESOURCE", 
			joinColumns = @JoinColumn(name = "ROLE_ID"), 
			inverseJoinColumns = @JoinColumn(name = "RESOURCE_ID"))
    private List<Resource> resources;
    
	/**
	 * @return the roleId
	 */

	public long getRoleId() {
		return roleId;
	}

	/**
	 * @return the roleDesc
	 */
	
	public String getRoleDesc() {
		return roleDesc;
	}

	/**
	 * @return the roleName
	 */
	
	public String getRoleName() {
		return roleName;
	}

	/**
	 * @return the roleOwnerId
	 */
	
	public String getOwnerExternalId() {
		return ownerExternalId;
	}

	/**
	 * @param id
	 *            the id to set
	 */
	public void setRoleId(long roleId) {
		this.roleId = roleId;
	}

	/**
	 * @param roleDesc
	 *            the roleDesc to set
	 */
	public void setRoleDesc(String roleDesc) {
		this.roleDesc = roleDesc;
	}

	/**
	 * @param roleName
	 *            the roleName to set
	 */
	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

	/**
	 * @param roleOwnerId
	 *            the roleOwnerId to set
	 */
	public void setOwnerExternalId(String ownerExternalId) {
		this.ownerExternalId = ownerExternalId;
	}

	public List<Resource> getResources() {
		return resources;
	}

	public void setResources(List<Resource> resources) {
		this.resources = resources;
	}
	
	
}
