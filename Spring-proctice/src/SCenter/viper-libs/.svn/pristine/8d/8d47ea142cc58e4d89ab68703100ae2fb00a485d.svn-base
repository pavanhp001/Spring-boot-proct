package com.A.vm.util.converter.marshall;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;

import com.A.V.beans.entity.SalesOrderContext;
import com.A.xml.v4.NameValuePairType;
import com.A.xml.v4.SalesContextEntityType;
import com.A.xml.v4.SalesContextType;

@Component
public class MarshallSalesOrderContext
{
	private static final Logger logger = Logger.getLogger( MarshallSalesOrderContext.class );

	/**
	 * A method to marshall Sales Order Context information from entity to XML.
	 * Sample eg.
	 *
	 * 	<v4:salesContext>
     * 		<v4:entity name="abc">
     *   		<v4:attribute name="name1" value="valu1"/>
     *   		<v4:attribute name="name2" value="valu2"/>
     * 		</v4:entity>
	 *		<v4:entity name="def">
     *   		<v4:attribute name="name3" value="valu3"/>
     * 		</v4:entity>
     *	</v4:salesContext>
	 * @param socList
	 * @return
	 */
	public SalesContextType build(final List<SalesOrderContext> socList)
	{
		logger.debug( "Marshalling Sales Order Context" );
		SalesContextType scType = SalesContextType.Factory.newInstance();
		if(socList != null && !socList.isEmpty())
		{


			String entity = "";
			int i = 0;
			SalesContextEntityType scEntityType = null;
			for(SalesOrderContext soc : socList)
			{
				if (soc != null) {
					if (soc.getOrderSource() != null) {
						scType.setOrderSource(Integer.parseInt(soc
								.getOrderSource()));
					}
					if (i == 0) {
						scEntityType = scType.addNewEntity();
						entity = soc.getEntityName();
						scEntityType.setName(entity);
						NameValuePairType nvPairType = scEntityType
								.addNewAttribute();
						nvPairType.setName(soc.getName());
						nvPairType.setValue(soc.getValue());
					} else if(soc != null && isEnityTypeExist(scType.getEntityList(), soc.getEntityName()) ){
						List<SalesContextEntityType> entityList = scType
								.getEntityList();
						scEntityType = getEnityType(entityList, soc.getEntityName());
						NameValuePairType nvPairType = scEntityType
								.addNewAttribute();
						nvPairType.setName(soc.getName());
						nvPairType.setValue(soc.getValue());
					}else if(soc != null){
						SalesContextEntityType scEntType = scType
								.addNewEntity();
						entity = soc.getEntityName();
						scEntType.setName(entity);
						NameValuePairType anotherNvPairType = scEntType
								.addNewAttribute();
						anotherNvPairType.setName(soc.getName());
						anotherNvPairType.setValue(soc.getValue());
					}
					i++;
				}
			}
		}
		return scType;
	}

	private SalesContextEntityType getEnityType(List<SalesContextEntityType> scEntityList, String entityName){
		for(SalesContextEntityType entType : scEntityList){
			if(entityName.equalsIgnoreCase(entType.getName())){
				return entType;
			}
		}
		return null;
	}

	private Boolean isEnityTypeExist(List<SalesContextEntityType> scEntityList, String entityName){
		for(SalesContextEntityType entType : scEntityList){
			if(entityName.equalsIgnoreCase(entType.getName())){
				return true;
			}
		}
		return false;
	}
}
