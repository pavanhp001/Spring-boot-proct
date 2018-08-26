package com.A.vm.util.converter.unmarshall;



public class UnmarshallCustomerDemographicInfo {

	/*private static final Logger logger = Logger.getLogger(UnmarshallCustomerDemographicInfo.class);
	
	*//**
	 * Method will copy basic demographic information about customer in to ConsumerBean. List of fields to be copied will
	 * be decided by CustomerDemographicInfoMapper class.
	 * @param customerTypeSrc
	 * @param consumerBean
	 * @param isUpdateRequest
	 *//*
	public static void copy( final CustomerType customerTypeSrc, final ConsumerBean consumerBean,
            boolean isUpdateRequest )
    {
		logger.info("copy()...");
		if ( consumerBean == null )
        {
            return;
        }

		//TODO Passing null as Constraint level, make sure it is not causing any issue
        DynamicBuilder<CustomerType, ConsumerBean> builder = new DynamicBuilder<CustomerType, ConsumerBean>( null );

        try
        {
        	logger.debug("copying XML data in ConsumerBean using dynamic builder...");
            builder.copyInstanceAttributes(  customerTypeSrc,consumerBean, CustomerDemographicInfoMapper.contact, isUpdateRequest );
            customCopy( builder, customerTypeSrc, consumerBean, isUpdateRequest );
            
        }
        catch ( Exception e )
        {
        	logger.error("Exception was thrown while copying data from XML to ConsumerBean...",e);
            e.printStackTrace();
            throw new IllegalArgumentException( "unable.to.unmarshall.customer" );
            
        }
    }

	*//**
	 * Method to copy custom fields like Gender
	 * @param builder
	 * @param customerTypeSrc
	 * @param dest
	 * @param isUpdateRequest
	 *//*
	private static void customCopy(
			DynamicBuilder<CustomerType, ConsumerBean> builder,
			CustomerType customerTypeSrc, ConsumerBean dest,
			 boolean isUpdateRequest) {
		
		
		if (customerTypeSrc == null) {
			return;
		}

		if (customerTypeSrc.getGender() != null) {
			dest.setGender(GenderEnum.get(customerTypeSrc.getGender().toString()));
		} else {
			dest.setGender(null);
		}
		
	}

*/}
