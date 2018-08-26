package com.A.V.beans.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.hibernate.annotations.ForeignKey;
import org.hibernate.validator.NotNull;

import com.A.V.beans.CapabilitiesRecord;
import com.A.V.interfaces.CommonBeanInterface;

/**
 * 
 * @author ebaugh
 *
 */
@Entity
@Table( name = "zipOnlyServiceabilityElement" )
public class ZipOnlyServiceabilityElementBean extends CapabilitiesRecord implements CommonBeanInterface
{

        /**
	 * 
	 */
	private static final long serialVersionUID = 563060260101703928L;

		@Id
        @GeneratedValue( generator = "zipOnlySrvElementBeanSequence" )
        @SequenceGenerator( name = "zipOnlySrvElementBeanSequence", sequenceName = "ZIPONLY_SRV_ELEMENT_BEAN_SEQ" )
        private long id;

        @OneToOne
        @NotNull
        @ForeignKey( name = "FK_PROVIDER" )
        private BusinessParty provider;

        private String postalCode5;

        @Override
        public long getId()
        {
            return id;
        }

        public void setId( final long id )
        {
            this.id = id;
        }

        public BusinessParty getProvider()
        {
            return provider;
        }

        public void setProvider( final BusinessParty provider )
        {
            this.provider = provider;
        }

        public String getPostalCode5()
        {
            return postalCode5;
        }

        public void setPostalCode5( final String postalCode5 )
        {
            this.postalCode5 = postalCode5;
        }

    }
