package com.A.vm.util.converter.unmarshall;

import com.A.V.beans.entity.EMailContactChannel;
import com.A.V.beans.entity.PhoneContactChannel;
import com.A.vm.util.converter.DynamicBuilder;
import com.A.vm.util.converter.mapper.ContactMapper;
import com.A.xml.v4.EMailAddressType;
import com.A.xml.v4.PhoneNumberType;

/**
 * @author ebthomas
 *
 */
public final class UnmarshallContactItem
{
    /**
     * unable to instantiate.
     */
    private UnmarshallContactItem()
    {
        super();
    }

    /**
     * @param addr
     *            email address that identifies a EMailContactChannel
     * @return gets the EMailContactChannel identified by the email address
     */
    public static EMailContactChannel getEmailValue( final EMailAddressType emailAddressSource)
    {

        EMailContactChannel emailContactChannelDestination = new EMailContactChannel();
        if(null != emailAddressSource){
        	return getEmailValue( emailContactChannelDestination, emailAddressSource );
        }else{
        	return emailContactChannelDestination;
        }

    }

    /**
     * @param number
     *            number that identifies a PhoneContactChannel
     * @return gets the PhoneContactChannel identified by the number
     */
    public static PhoneContactChannel getPhoneValue(final PhoneNumberType phoneNumberSource,
            boolean isUpdateRequest )
    {
        PhoneContactChannel phoneContactChannelDestination = new PhoneContactChannel();
        if(null != phoneNumberSource){
        	return getPhoneValue( phoneContactChannelDestination, phoneNumberSource, isUpdateRequest );
        }else{
        	return phoneContactChannelDestination;
        }
    }


    /**
     * @param number
     *            number that identifies a PhoneContactChannel
     * @return gets the PhoneContactChannel identified by the number
     */
    public static PhoneContactChannel getPhoneValue( final PhoneContactChannel phoneContactChannelDestination,
            final PhoneNumberType phoneNumberSource, boolean isUpdateRequest )
    {
        DynamicBuilder<PhoneNumberType, PhoneContactChannel> builder = new DynamicBuilder<PhoneNumberType, PhoneContactChannel>(
                null );

        try
        {
            builder.copyInstanceAttributes( phoneNumberSource, phoneContactChannelDestination,  ContactMapper.contactFields,isUpdateRequest );
        }
        catch ( Exception e )
        {
            e.printStackTrace();
            throw new IllegalArgumentException( "unable.to.unmarshall.phone.number" );
        }

        phoneContactChannelDestination.setPreferenceOrder( phoneNumberSource.getOrder() );
        return phoneContactChannelDestination;
    }



    /**
     * @param addr
     *            email address that identifies a EMailContactChannel
     * @return gets the EMailContactChannel identified by the email address
     */
    public static EMailContactChannel getEmailValue( final EMailContactChannel emailContactChannelDestination,
            final EMailAddressType emailAddressSource )
    {

        DynamicBuilder<EMailAddressType, EMailContactChannel> builder = new DynamicBuilder<EMailAddressType, EMailContactChannel>(
                null );

        try
        {
            builder.copyInstanceAttributes( emailAddressSource, emailContactChannelDestination,  ContactMapper.contactFields, false );
        }
        catch ( Exception e )
        {
            e.printStackTrace();
            throw new IllegalArgumentException( "unable.to.unmarshall.contact.email" );
        }
        emailContactChannelDestination.setPreferenceOrder( emailAddressSource.getOrder() );
        return emailContactChannelDestination;
    }


}
