package com.A.V.beans.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.hibernate.annotations.ForeignKey;
//import org.jboss.seam.Component;

import com.A.V.interfaces.CommonBeanInterface;
//import com.A.V.interfaces.RealTimeCapabilitiesInterface;

/**
 * 
 * @author ebaugh
 *
 */
@Entity
@Table( name = "providerServiceabilityConf" )
public class ProviderServiceabilityConfBean implements CommonBeanInterface
    {
        
        /**
	 * 
	 */
	private static final long serialVersionUID = -2628159753872078438L;

		@Id
        @GeneratedValue( generator = "providerBeanSequence" )
        @SequenceGenerator( name = "providerBeanSequence", sequenceName = "PROVIDER_BEAN_SEQ" )
        private long id;

        @OneToOne
        @ForeignKey( name = "PROVIDER_SERV_CONF_FK01" )
        private BusinessParty businessParty;
        private String externalImplementation;

        @Override
        public long getId()
        {
            return id;
        }

        public void setId( final long id )
        {
            this.id = id;            
        }

        public void setBusinessParty( final BusinessParty businessParty )
        {
            this.businessParty = businessParty;
        }

        public BusinessParty getBusinessParty()
        {
            return businessParty;
        }
              
       /* public void setExternalImplementation( final Component externalImplementation )
        {
            this.externalImplementation = externalImplementation.getName();
        }
        
        *//**
         * For getting the mapped RealTime Serviceability class.
         * @return the matching real time serviceabilty class.
         *//*
        public RealTimeCapabilitiesInterface getExternalImplementation()
        {
            Object result = Component.getInstance( externalImplementation );
            
            return (RealTimeCapabilitiesInterface)result;
        }*/
}
