package com.A.vm.util.converter.unmarshall;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;

import com.A.V.beans.entity.SalesOrderContext;
import com.A.xml.v4.NameValuePairType;
import com.A.xml.v4.SalesContextEntityType;
import com.A.xml.v4.SalesContextType;

@Component
public class UnmarshallSalesOrderContext
{
	private static final Logger logger = Logger.getLogger( UnmarshallSalesOrderContext.class );

	/**
	 * A build method to populate List of SalesOrderContext beans from XML
	 * @param scType
	 * @return
	 */
	public Set<SalesOrderContext> build(SalesContextType scType)
	{
		logger.debug( "Unmarshalling SalesOrderContext information" );
		Set<SalesOrderContext> socList = new HashSet<SalesOrderContext>();

		if(scType == null)
		{
			throw new IllegalArgumentException("SalesContextType can not be null");
		}

		List<SalesContextEntityType> scTypeList = scType.getEntityList();

		copySalesContextInfo( scType, socList, scTypeList );

		return socList;
	}

	/**
	 * A helper method to copy SalesContext information from XML to SalesOrderContext bean
	 * @param scType
	 * @param socList
	 * @param scTypeList
	 */
	private void copySalesContextInfo( SalesContextType scType, Set<SalesOrderContext> socList,
			List<SalesContextEntityType> scTypeList )
	{
		if(scTypeList != null && !scTypeList.isEmpty())
		{
			for(SalesContextEntityType sceType : scTypeList)
			{
				List<NameValuePairType> nvPairList = sceType.getAttributeList();
				if(nvPairList != null && !nvPairList.isEmpty())
				{
					for(NameValuePairType nvPairType : nvPairList)
					{
						SalesOrderContext soc = new SalesOrderContext();
						soc.setOrderSource( String.valueOf( scType.getOrderSource() ));
						soc.setEntityName(sceType.getName());
						soc.setName( nvPairType.getName() );
						soc.setValue( nvPairType.getValue() );
						socList.add( soc );
					}
				}
			}
		}
	}
}
