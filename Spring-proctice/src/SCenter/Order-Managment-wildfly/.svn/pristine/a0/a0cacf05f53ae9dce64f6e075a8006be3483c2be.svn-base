/**
 *
 */
package com.AL.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.AL.V.beans.entity.SalesOrder;
import com.AL.vm.service.MarshallService;
import com.AL.vm.util.converter.marshall.MarshallLineItem;
import com.AL.vm.util.converter.marshall.MarshallOrder;
import com.AL.vm.util.converter.unmarshall.UnmarshallLineItem;
import com.AL.xml.v4.OrderType;

/**
 * @author ebthomas
 * 
 */
@Component
public class MarshallServiceImpl implements MarshallService
{
    
  @Autowired
  private MarshallOrder<SalesOrder> marshallOrder;

  @Autowired
  private UnmarshallLineItem unmarshallLineItem;

  @Autowired
  private MarshallLineItem marshallLineItem;
  
  public void buildOrderType(final SalesOrder salesOrder, final OrderType orderType)
  {
   
  marshallOrder.build( salesOrder, orderType );
  }
  
  public OrderType buildOrderType(final SalesOrder salesOrder)
  {
  OrderType orderType = marshallOrder.build( salesOrder );
    return orderType;
  }
  
  
  

public MarshallOrder<SalesOrder> getMarshallOrder()
{
    return marshallOrder;
}

public void setMarshallOrder( MarshallOrder<SalesOrder> marshallOrder )
{
    this.marshallOrder = marshallOrder;
}

public UnmarshallLineItem getUnmarshallLineItem()
{
    return unmarshallLineItem;
}

public void setUnmarshallLineItem( UnmarshallLineItem unmarshallLineItem )
{
    this.unmarshallLineItem = unmarshallLineItem;
}

public MarshallLineItem getMarshallLineItem()
{
    return marshallLineItem;
}

public void setMarshallLineItem( MarshallLineItem marshallLineItem )
{
    this.marshallLineItem = marshallLineItem;
} 

}
