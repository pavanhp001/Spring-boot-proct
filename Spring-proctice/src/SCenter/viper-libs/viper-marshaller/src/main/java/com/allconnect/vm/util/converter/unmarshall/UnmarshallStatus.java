package com.A.vm.util.converter.unmarshall;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.A.V.beans.entity.StatusRecordBean;
import com.A.xml.v4.LineItemStatusType;
import com.A.xml.v4.OrderStatusHistoryType;
import com.A.xml.v4.OrderStatusWithTypeType;

/**
 * @author ebthomas
 *
 */
public final class UnmarshallStatus
{
    /**
     * Unmarshall Status.
     */
    private UnmarshallStatus()
    {
        super();
    }

    /**
     * @param orderStatusSrc
     *            Order Status
     * @return Domain Status Record Bean
     */
    public static StatusRecordBean copyStatusRecordBean( final OrderStatusWithTypeType orderStatusSrc )
    {

        StatusRecordBean dest = new StatusRecordBean();

        List<String> reasons = new ArrayList<String>();

        if ( ( orderStatusSrc != null ) && (  orderStatusSrc.getReasonArray()  != null ) )
        {

            for ( Integer value : orderStatusSrc.getReasonArray() )
            {
                reasons.add( String.valueOf( value ) );
            }
            dest.setAgentExternalId(orderStatusSrc.getAgentId());
            dest.setReasons( reasons );
            dest.setStatus( String.valueOf( orderStatusSrc.getStatus() ) );
            dest.setDateTimeStamp( orderStatusSrc.getDateTimeStamp() );
        }
        return dest;
    }

    /**
     * Method to create StatusRecordBean for LineItemStatus
     * @param lineItemTypeSource
     *            Order Status
     * @return Domain Status Record Bean
     */
    public static StatusRecordBean copyStatusRecordBean( final LineItemStatusType lineItemStatusSrc,String agentId )
    {

        StatusRecordBean dest = new StatusRecordBean();
        dest.setAgentExternalId( agentId );
        List<String> reasons = new ArrayList<String>();

        if ( ( lineItemStatusSrc != null ) && (  lineItemStatusSrc.getReasonArray()  != null ) )
        {

            for ( Integer value : lineItemStatusSrc.getReasonArray() )
            {
                reasons.add( String.valueOf( value ) );
            }
            dest.setReasons( reasons );
            dest.setStatus( String.valueOf( lineItemStatusSrc.getStatusCode() ) );
            dest.setDateTimeStamp( lineItemStatusSrc.getDateTimeStamp() );
        }
        return dest;
    }

    /**
     * @param historyContainer
     *            Source
     * @return Domain Object representing Status of the Order
     */
    public static List<StatusRecordBean> copyStatusRecordBeanList( final OrderStatusHistoryType historyContainer )
    {
        List<StatusRecordBean> statusRecordBeanList = new ArrayList<StatusRecordBean>();

        if ( historyContainer != null )
        {
            List<OrderStatusWithTypeType> orderStatusArray = Arrays.asList(historyContainer.getPreviousStatusArray());

            if ( orderStatusArray != null )
            {
                for ( OrderStatusWithTypeType orderStatus : orderStatusArray )
                {
                    statusRecordBeanList.add( copyStatusRecordBean( orderStatus ) );
                }
            }
        }

        return statusRecordBeanList;
    }

}
