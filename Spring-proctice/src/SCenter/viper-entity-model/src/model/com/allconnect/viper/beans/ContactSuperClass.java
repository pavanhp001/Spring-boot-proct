package com.A.V.beans;

import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;

import org.hibernate.validator.Length;

import com.A.V.Constants;

/**
 *
 * @author ebaugh
 *
 */
@Inheritance(strategy=InheritanceType.TABLE_PER_CLASS)
public abstract class ContactSuperClass
{
    private String title;
    private String firstName;
    private String lastName;
    private String middleName;
    private String nameSuffix;
    private String gender;

    /**
     * @return the title
     */
    public String getTitle()
    {
        return title;
    }

    /**
     * @param title
     *            the title to set
     */
    public void setTitle( final String title )
    {
        this.title = title;
    }

    @Length( min = Constants.NAME_MIN_LENGTH, max = Constants.NAME_MAX_LENGTH )
    public String getFirstName()
    {
        return firstName;
    }

    public void setFirstName( final String firstName )
    {
        this.firstName = firstName;
    }

    public String getMiddleName()
    {
        return middleName;
    }

    public void setMiddleName( final String middleName )
    {
        this.middleName = middleName;
    }

    public String getLastName()
    {
        return lastName;
    }

    public void setLastName( final String lastName )
    {
        this.lastName = lastName;
    }

    public String getNameSuffix()
    {
        return nameSuffix;
    }

    public void setNameSuffix( final String nameSuffix )
    {
        this.nameSuffix = nameSuffix;
    }

    public String getGender()
    {
        return gender;
    }

    public void setGender( final String gender )
    {
        this.gender = gender;
    }
}
