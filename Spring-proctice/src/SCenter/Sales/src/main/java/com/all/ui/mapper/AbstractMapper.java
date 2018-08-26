package com.AL.ui.mapper;



import java.math.BigInteger;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.AL.ui.domain.Order;
import com.AL.ui.domain.sales.Address;
import com.AL.ui.domain.sales.Agent;
import com.AL.ui.domain.sales.BooleanConstraint;
import com.AL.ui.domain.sales.Caller;
import com.AL.ui.domain.sales.Dwelling;
import com.AL.ui.domain.sales.IntegerConstraint;
import com.AL.ui.domain.sales.SalesContext;
import com.AL.ui.domain.sales.StringConstraint;
import com.AL.xml.di.v4.AddressType;
import com.AL.xml.di.v4.NameValuePairType;
import com.AL.xml.di.v4.SalesContextEntityType;
import com.AL.xml.di.v4.SalesContextType;
/*import com.AL.xml.v4.AddressType;
import com.AL.xml.v4.NameValuePairType;
import com.AL.xml.v4.SalesContextEntityType;
import com.AL.xml.v4.SalesContextType;
import com.AL.xml.v4.Order;*/
//import org.apache.commons.lang3.StringUtils;

public class AbstractMapper 
{
	protected static Log logger = LogFactory.getLog(AbstractMapper.class);

   static public NameValuePairType createNVP(String name, String value) {
	   if (StringUtils.isEmpty(value)) {
		   return null;
	   }
      NameValuePairType nvp = new NameValuePairType();
      nvp.setName(name);
      nvp.setValue(value);
      return nvp;
   }

   static public SalesContextEntityType createSalesContextEntity(String name) {
      SalesContextEntityType scet = new SalesContextEntityType();
      scet.setName(name);
      return scet;
   }

   static public SalesContextEntityType addNVP(SalesContextEntityType entity, NameValuePairType nvp) {
	  if (nvp != null) {
		  entity.getAttribute().add(nvp);
	  }
      return entity;
   }

   static public SalesContextEntityType createSalesContextEntity(String name, String nvpName, String nvpValue) {
      return addNVP(createSalesContextEntity(name), createNVP(nvpName, nvpValue));
   }

   static public SalesContextEntityType createSalesContextEntityOrder(SalesContext model) {
	  Order o = model.getOrder();
	  if (o == null) {
		  return null;
	  }
      SalesContextEntityType scet = createSalesContextEntity("orderSource");
      addNVP(scet, createNVP("orderSource.id", o.getExternalId()));
      addNVP(scet, createNVP("orderSource.channel", o.getChannel()));
      addNVP(scet, createNVP("orderSource.programType", o.getProgramType()));
      addNVP(scet, createNVP("orderSource.referrer", o.getReferrerId()));
      addNVP(scet, createNVP("orderSource.source", o.getSource()));
      return scet;
   }
   
   static public SalesContextEntityType createSalesContextEntityCustomerDwelling(SalesContext model) {
      Caller c = model.getCustomer();
	  if (c == null) {
		  return null;
	  }
	  Dwelling d = c.getPrimaryDwelling();
	  if (d == null) {
		  return null;
	  }

      SalesContextEntityType scet = createSalesContextEntity("dwelling");
      addNVP(scet, createNVP("dwelling.address.line1", d.getLine1()));
      addNVP(scet, createNVP("dwelling.address.line2", d.getLine2()));
      addNVP(scet, createNVP("dwelling.city", d.getCity()));
      addNVP(scet, createNVP("dwelling.state", d.getState()));
      addNVP(scet, createNVP("dwelling.zip", d.getZip()));
      addNVP(scet, createNVP("dwelling.dwellingType", d.getDwellingType()));
      return scet;
   }

   static public SalesContextEntityType createSalesContextEntitySalesAgent(SalesContext model) {
      Agent sa = model.getSalesAgent();
      if (sa == null) {
         return null;
      }
      SalesContextEntityType scet = createSalesContextEntity("agent");
      addNVP(scet, createNVP("agent.firstName", sa.getFirstName()));
      addNVP(scet, createNVP("agent.lastName", sa.getLastName()));
      addNVP(scet, createNVP("agent.id", sa.getExternalId()));
      addNVP(scet, createNVP("agent.score", sa.getScore()));
      addNVP(scet, createNVP("agent.capability", "advanced"));

      return scet;
   }
 
   static public SalesContextEntityType createSalesContextEntityFlow(SalesContext model) {
      SalesContextEntityType scet = createSalesContextEntity("salesFlow");
      addNVP(scet, createNVP("salesFlow.dialogueType", model.getPhaseName()));
      return scet;
   }

   static protected void addSubcontext(SalesContextType context, SalesContextEntityType subcontext) {
	   if (subcontext == null) {
		   return;
	   }
 	  context.getEntity().add(subcontext);  
   }
   
   static public SalesContextEntityType createSalesContextEntityCustomer(SalesContext model) {
      Caller c = model.getCustomer();
      if (c == null) {
         return null;
      }
      SalesContextEntityType scet = createSalesContextEntity("consumer");
      addNVP(scet, createNVP("consumer.firstName", model.getCustomer().getFirstName()));
      addNVP(scet, createNVP("consumer.lastName", model.getCustomer().getLastName()));
      addNVP(scet, createNVP("consumer.creditScore", model.getCustomer().getCreditScore()));
      return scet;
   }
   
   static public AddressType createAddressType(Address address) {
	   AddressType retval = new AddressType();
	   if (address != null) {
		   retval.setCity(address.getCity());
		   retval.setCountry(address.getCountry());
		   retval.setPostalCode(address.getZip());
		   retval.setStateOrProvince(address.getState());
		   retval.setStreetName(address.getStreet());
	   }
	   return retval;
   }
   
   static public Address createAddress(AddressType addressType) {
	   Address retval = new Address();
	   if (addressType != null) {
		   retval.setCity(addressType.getCity());
		   retval.setCountry(addressType.getCountry());
		   retval.setState(addressType.getStateOrProvince());
		   retval.setStreet(addressType.getStreetNumber() + " " + addressType.getStreetName());
		   retval.setZip(addressType.getPostalCode());
	   }
	   return retval;
   }
   
   static public BooleanConstraint createBooleanConstraint(com.AL.xml.di.v4.DataConstraintType.BooleanConstraint xbc) {
	   BooleanConstraint bc = null;
	   if (xbc != null) {
		   bc = new BooleanConstraint();
		   bc.setDefaultValue(xbc.isDefault());
		   bc.setValue(xbc.getValue());				
	   }
	   return bc;
   }
 
   static public IntegerConstraint createIntegerConstraint(com.AL.xml.di.v4.DataConstraintType.IntegerConstraint xic)
   {
		IntegerConstraint ic = null;
		if (xic != null) {
			ic = new IntegerConstraint();
			BigInteger bi = xic.getValue();
			if (bi != null) {
				ic.addValue(bi.intValue());
				ic.setValue(bi.intValue());
			}
			bi = xic.getMaxValue();
			if (bi != null) {
				ic.setMaxValue(bi.intValue());
			}

			bi = xic.getMinValue();
			if (bi != null) {
				ic.setMinValue(bi.intValue());
			}

			ic.setUnit(xic.getUnit());
			ic.setUnlimited(xic.isUnlimited());

			bi = xic.getMinValue();
			if (bi != null) {
				ic.setMinValue(bi.intValue());
			}

			if (xic.getListOfValues() != null) {
				for (BigInteger bi2 : xic.getListOfValues().getValue()) {
					if (bi2 != null) {
						ic.addValue(bi2.intValue());
					}
				}
			}
		}
		return ic;	   
   }
   
   static public StringConstraint createStringConstraint(com.AL.xml.di.v4.DataConstraintType.StringConstraint xsc) {
	   StringConstraint sc = null;
		if (xsc != null) {
			sc = new StringConstraint();
			sc.setComparableValue(xsc.getComparableValue());
			sc.setDefaultValue(xsc.getDefault());
			BigInteger bi = xsc.getLength();
			if (bi != null) {
				sc.setLength(bi.intValue());
			}
			sc.setValue(xsc.getValue());
			if (xsc.getListOfValues() != null) {
				for (String s : xsc.getListOfValues().getValue()) {
					sc.addValue(s);
				}
			}
		}
		return sc;
   }
}
