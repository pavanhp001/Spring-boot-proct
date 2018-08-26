package com.A.V.beans;

import java.util.Calendar;
import java.util.GregorianCalendar;

import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;

import org.hibernate.validator.NotNull;

@Inheritance(strategy=InheritanceType.TABLE_PER_CLASS)
public abstract class ConsumerPointInTimeSuperClass {

	@NotNull
	private Calendar inEffect;
	private Calendar expiration;

	/*@Column(name = "CHANGEAUTHOR_ID")
	private String changeAuthor;

	private String changeType;*/

	public final Calendar getInEffect() {
		return inEffect;
	}

	/**
	 * @param inEffect
	 *            value to set
	 * @return boolean as to whether or not successful.
	 */
	public final boolean setInEffect(final Calendar inEffect) {
		// TODO We need to see what this does to other related addresses where
		// the expiration was this inEffect...
		this.inEffect = inEffect;
		return true;
	}

	public void setInEffect(GregorianCalendar inEffect) {
		this.inEffect = inEffect;
	}

	/**
	 * Get the expiration date of this entity. When no expiration is set, then
	 * return a date one year from the current moment.
	 * 
	 * @return either the associated expiration date of the entity, or -- if the
	 *         value is null -- return a time one year from the current moment.
	 */
	public final Calendar getExpiration() {
		if (expiration == null) {
			Calendar dateInFuture = Calendar.getInstance();
			dateInFuture.add(Calendar.YEAR, 1);
			return dateInFuture;
		}
		return expiration;
	}

	/**
	 * Returns the true value of expiration. Whereas getExpiration will add a
	 * year to the current date if null, this method will return null if its
	 * null.
	 * 
	 * @return Calendar expiration date.
	 */
	public final Calendar getTrueExpirationValue() {
		return expiration;
	}

	/**
	 * @param expiration
	 *            value to set
	 * @return boolean as to whether or not successful.
	 */
	public final boolean setExpiration(final Calendar expiration) {
		// You can set the expiration if it's not already been set.
		// Once you expire something, it's in for good.
		if (this.expiration == null) {
			this.expiration = expiration;
			return true;
		}
		return false;
	}

	public void setExpiration(GregorianCalendar expiration) {
		this.expiration = expiration;
	}

	/*public final String getChangeType() {
		return changeType;
	}

	public final void setChangeType(final String changeType) {
		this.changeType = changeType;
	}

	public String getChangeAuthor() {
		return changeAuthor;
	}

	public void setChangeAuthor(String changeAuthor) {
		this.changeAuthor = changeAuthor;
	}*/

	
}
