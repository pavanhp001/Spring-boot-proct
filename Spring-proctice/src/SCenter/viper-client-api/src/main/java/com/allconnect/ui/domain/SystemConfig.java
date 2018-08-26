package com.A.ui.domain;

import java.util.Calendar;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name = "SYS_CONFIG")
public class SystemConfig {

	private static final long serialVersionUID = 9832981267818L;

	@Id
	@GeneratedValue(generator = "SystemConfigSequence")
	@SequenceGenerator(name = "SystemConfigSequence", sequenceName = "SYS_CONFIG_SEQ")
	private long id;

	@Column(name = "NAME", nullable = false, length = 25)
	private String name;

	@Column(name = "VALUE", nullable = true, length = 50)
	private String value;

	@Column(name = "CREATE_DATE", nullable = true)
	private Calendar createDate;
	
	@Column(name = "DATA_TYPE")
	private int dataType;
	

	@Column(name = "CONTEXT", nullable = true, length = 25)
	private String context;

	/**
	 * {@inheritDoc}
	 */

	public final long getId() {
		return id;
	}

	/**
	 * {@inheritDoc}
	 */

	public final void setId(final long id) {
		this.id = id;
	}

	/**
	 * @param name
	 *            the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param value
	 *            the value to set
	 */
	public void setValue(String value) {
		this.value = value;
	}

	/**
	 * @return the value
	 */
	public String getValue() {
		return value;
	}

	/**
	 * @param createDate
	 *            the createDate to set
	 */
	public void setCreateDate(Calendar createDate) {
		this.createDate = createDate;
	}

	/**
	 * @return the createDate
	 */
	public Calendar getCreateDate() {
		return createDate;
	}

	/**
	 * @param context
	 *            the context to set
	 */
	public void setContext(String context) {
		this.context = context;
	}

	/**
	 * @return the context
	 */
	public String getContext() {
		return context;
	}

	public int getDataType() {
		return dataType;
	}

	public void setDataType(int dataType) {
		this.dataType = dataType;
	}
	
	

}
