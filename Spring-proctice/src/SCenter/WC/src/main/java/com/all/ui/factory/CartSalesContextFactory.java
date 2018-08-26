package com.A.ui.factory;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import com.A.ui.constants.Constants;
import com.A.xml.v4.NameValuePairType;
import com.A.xml.v4.SalesContextEntityType;
import com.A.xml.v4.SalesContextType;

/**
 * @author Preetam
 *
 */
public enum CartSalesContextFactory {

	INSTANCE;

	/**
	 * 
	 */
	private com.A.xml.v4.ObjectFactory oFactory = new com.A.xml.v4.ObjectFactory();

	/**
	 * @param salesContextType
	 * @return
	 */
	public SalesContextType getStatusCompleteSalesContext(SalesContextType salesContextType)
	{
		return buildSalesContext(Constants.SYP, false, salesContextType);
	}


	/**
	 *  Builds the SalesContextEntityType with Source Name and Attributes as Map
	 * 
	 * 
	 * @param source
	 * @param attributesMap
	 * @param salesContextType
	 * @return SalesContextType
	 */
	public SalesContextType buildSalesContext( String source, boolean isWarmTransfer, SalesContextType salesContextType)
	{
		boolean isUpdateBy = false;
		boolean isSessionStatus = false;
		for (SalesContextEntityType salesContextEntityType : salesContextType.getEntity())
		{
			if (salesContextEntityType.getName().equalsIgnoreCase(Constants.SYP))
			{
				for (NameValuePairType nameValuePairType : salesContextEntityType.getAttribute())
				{
					if ((nameValuePairType.getName().equalsIgnoreCase(Constants.UPDATEDBY)))
					{
						nameValuePairType.setValue(Constants.CONCERT);
						isUpdateBy = true;
					}
					if ((nameValuePairType.getName().equalsIgnoreCase(Constants.SESSIONSTATUS)))
					{
						isSessionStatus = true;	
						if (!nameValuePairType.getValue().equalsIgnoreCase(Constants.COMPLETED)){
							nameValuePairType.setValue(Constants.COMPLETED);
						}
					}
				}
				if (!isUpdateBy){
					NameValuePairType nvpt = new NameValuePairType();
					nvpt.setName(Constants.UPDATEDBY);
					nvpt.setValue(Constants.CONCERT);
					salesContextEntityType.getAttribute().add(nvpt);
				}
				if (!isSessionStatus){
					NameValuePairType nvpt = new NameValuePairType();
					nvpt.setName(Constants.SESSIONSTATUS);
					nvpt.setValue(Constants.COMPLETED);
					salesContextEntityType.getAttribute().add(nvpt);
				}
				if (isWarmTransfer){
					NameValuePairType nvpt = new NameValuePairType();
					nvpt.setName(Constants.WARMTRANSFER);
					nvpt.setValue(Constants.TRUE);
					salesContextEntityType.getAttribute().add(nvpt);
				}
			}
		}
		return salesContextType;
	}

	public SalesContextType updateSalesContextforWarmTransfer(SalesContextType salesContextType)
	{
		return buildSalesContext(Constants.SYP, true, salesContextType);
	}

}
