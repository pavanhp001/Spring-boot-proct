/**
 *
 */
package com.AL.task.strategy;

import java.util.Comparator;
import com.AL.xml.v4.LineItemType;

/**
 * @author ebthomas
 * 
 */

class LineItemComparator implements Comparator<LineItemType>
{
    public static final LineItemComparator INSTANCE = new LineItemComparator();

    public int compare( LineItemType li2, LineItemType li1 )
    {
        if ( ( li1 == null ) || ( li2 == null ) )
        {
            return 0;
        }

        int li1Num = li1.getLineItemNumber();
        int li2Num = li2.getLineItemNumber();

        if ( li1Num > li2Num )
            return 1;
        else if ( li1Num < li2Num )
            return -1;
        else
            return 0;
    }

}
