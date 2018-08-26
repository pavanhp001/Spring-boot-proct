package com.A.vm.util.converter.unmarshall;
 
import java.util.Calendar;
import java.util.Date;
import org.apache.xmlbeans.XmlObject;
import com.A.xml.v4.DateTimeOrTimeRangeType;
import com.A.xml.v4.EmptyElementType;
import com.A.xml.v4.AddressType;
import com.A.xml.v4.OrderStatusHistoryType;
import com.A.xml.v4.OrderStatusWithTypeType;
import com.A.xml.v4.OrderType;
 

/**
* @author ebthomas
* 
*/
public class UnmarshallBase<T>
{

   
   
   
   protected  static  UnmarshallValidationEnum getValidationLevel( final  OrderType ucOrderType )
   {
       return ( ucOrderType instanceof OrderType ) ? UnmarshallValidationEnum.unconstrained : UnmarshallValidationEnum.unconstrained;
   }

   protected  static UnmarshallValidationEnum getValidationLevel( final  AddressType ucAddressType )
   {
       return ( ucAddressType instanceof AddressType ) ? UnmarshallValidationEnum.unconstrained
               : UnmarshallValidationEnum.unconstrained;
   }

   
   protected static boolean isPresent( UnmarshallValidationEnum level, DateTimeOrTimeRangeType obj )
   {
       /*
        * Bypass this check for constrained entries.
        * 
        * has valid value to copy
        */
       //if ( UnmarshallValidationEnum.constrained.equals( level ) )
      // {
        //   return Boolean.TRUE;
      // }

       /*
        * ************************************* UNCONSTRAINED FROM THIS POINT FORWARD *************************************
        */

       /*
        * Missing Tag. Do not copy NULL value
        */
       if ( obj == null )
       {
           return Boolean.FALSE;
       }

       /*
        * DEFAULT VALUE
        */
       return Boolean.FALSE;
   }
   
   protected static boolean isEligibleForCopy( UnmarshallValidationEnum level, OrderStatusHistoryType obj )
   {
       /*
        * Bypass this check for constrained entries.
        * 
        * has valid value to copy
        */
       if ( UnmarshallValidationEnum.constrained.equals( level ) )
       {
           return Boolean.TRUE;
       }

       /*
        * ************************************* UNCONSTRAINED FROM THIS POINT FORWARD *************************************
        */

       /*
        * Missing Tag. Do not copy NULL value
        */
       if ( obj == null )
       {
           return Boolean.FALSE;
       }

       /*
        * DEFAULT VALUE
        */
       return Boolean.FALSE;
   }
   
   protected static boolean isEligibleForCopy( UnmarshallValidationEnum level, OrderStatusWithTypeType obj )
   {
       /*
        * Bypass this check for constrained entries.
        * 
        * has valid value to copy
        */
       /*if ( UnmarshallValidationEnum.constrained.equals( level ) )
       {
           return Boolean.TRUE;
       }*/

       /*
        * ************************************* UNCONSTRAINED FROM THIS POINT FORWARD *************************************
        */

       /*
        * Missing Tag. Do not copy NULL value
        */
       if ( obj == null )
       {
           return Boolean.FALSE;
       }

       /*
        * DEFAULT VALUE
        */
       return Boolean.FALSE;
   }
   
   
   protected static boolean isPresent( UnmarshallValidationEnum level, EmptyElementType obj )
   {
       /*
        * Bypass this check for constrained entries.
        * 
        * has valid value to copy
        */
       if ( UnmarshallValidationEnum.constrained.equals( level ) )
       {
           return Boolean.TRUE;
       }

       /*
        * ************************************* UNCONSTRAINED FROM THIS POINT FORWARD *************************************
        */

       /*
        * Missing Tag. Do not copy NULL value
        */
       if ( obj == null )
       {
           return Boolean.FALSE;
       }

       /*
        * DEFAULT VALUE
        */
       return Boolean.FALSE;
   }
   
   protected static boolean isPresent( UnmarshallValidationEnum level, Date obj )
   {
       /*
        * Bypass this check for constrained entries.
        * 
        * has valid value to copy
        */
       if ( UnmarshallValidationEnum.constrained.equals( level ) )
       {
           return Boolean.TRUE;
       }

       /*
        * ************************************* UNCONSTRAINED FROM THIS POINT FORWARD *************************************
        */

       /*
        * Missing Tag. Do not copy NULL value
        */
       if ( obj == null )
       {
           return Boolean.FALSE;
       }

       /*
        * DEFAULT VALUE
        */
       return Boolean.FALSE;
   }
   
   
   protected static boolean isPresent( UnmarshallValidationEnum level, Calendar obj )
   {
       /*
        * Bypass this check for constrained entries.
        * 
        * has valid value to copy..
        */
       if ( UnmarshallValidationEnum.constrained.equals( level ) )
       {
           return Boolean.TRUE;
       }

       /*
        * ************************************* UNCONSTRAINED FROM THIS POINT FORWARD *************************************
        */

       /*
        * Missing Tag. Do not copy NULL value
        */
       if ( obj == null )
       {
           return Boolean.FALSE;
       }

       /*
        * DEFAULT VALUE
        */
       return Boolean.FALSE;
   }

   protected   boolean isEligibleForCopy( UnmarshallValidationEnum level, com.A.xml.v4.GenderDocument.Gender.Enum obj )
   {
       /*
        * Bypass this check for constrained entries.
        * 
        * has valid value to copy
        */
       if ( UnmarshallValidationEnum.constrained.equals( level ) )
       {
           return Boolean.TRUE;
       }

       /*
        * ************************************* UNCONSTRAINED FROM THIS POINT FORWARD *************************************
        */

       /*
        * Missing Tag. Do not copy NULL value
        */
       if ( obj == null )
       {
           return Boolean.FALSE;
       }

       /*
        * DEFAULT VALUE
        */
       return Boolean.FALSE;
   }
   
   

   protected static boolean isEligibleForCopy( UnmarshallValidationEnum level, String obj )
   {
         return Boolean.TRUE;
//       /*
//        * Bypass this check for constrained entries.
//        * 
//        * has valid value to copy
//        */
//       if ( UnmarshallValidationEnum.constrained.equals( level ) )
//       {
//           return Boolean.TRUE;
//       }
//
//       /*
//        * ************************************* UNCONSTRAINED FROM THIS POINT FORWARD *************************************
//        */
//
//       /*
//        * Missing Tag. Do not copy NULL value
//        */
//       if ( obj == null )
//       {
//           return Boolean.FALSE;
//       }
//
//       /*
//        * 
//        * clear out current value with blank
//        */
//
//       if ( ( obj != null ) && ( ( obj.isEmpty() ) ) )
//       {
//           return Boolean.TRUE;
//       }
//
//       /*
//        * DEFAULT VALUE
//        */
//       return Boolean.FALSE;
   }

   protected static boolean isPresent( UnmarshallValidationEnum level, Integer obj )
   {
       /*
        * Bypass this check for constrained entries.
        * 
        * has valid value to copy
        */
       if ( UnmarshallValidationEnum.constrained.equals( level ) )
       {
           return Boolean.TRUE;
       }

       /*
        * ************************************* UNCONSTRAINED FROM THIS POINT FORWARD *************************************
        */

       /*
        * Missing Tag. Do not copy NULL value
        */
       if ( obj == null )
       {
           return Boolean.FALSE;
       }

       /*
        * 
        * clear out current value with blank
        */

       if ( ( obj != null ) )
       {
           return Boolean.TRUE;
       }

       /*
        * DEFAULT VALUE
        */
       return Boolean.FALSE;
   }
   
   
   
   
  

   protected static boolean isPresent( UnmarshallValidationEnum level, Boolean obj )
   {
       /*
        * Bypass this check for constrained entries.
        * 
        * has valid value to copy
        */
       if ( UnmarshallValidationEnum.constrained.equals( level ) )
       {
           return Boolean.TRUE;
       }

       /*
        * ************************************* UNCONSTRAINED FROM THIS POINT FORWARD *************************************
        */

       /*
        * Missing Tag. Do not copy NULL value
        */
       if ( obj == null )
       {
           return Boolean.FALSE;
       }

       /*
        * DEFAULT VALUE
        */
       return Boolean.FALSE;
   }

   protected   boolean isEligibleForCopy( UnmarshallValidationEnum level, XmlObject obj )
   {
       /*
        * Bypass this check for constrained entries.
        * 
        * has valid value to copy
        */
       /*if ( UnmarshallValidationEnum.constrained.equals( level ) )
       {
          return Boolean.TRUE;
       }*/

       /*
        * ************************************* UNCONSTRAINED FROM THIS POINT FORWARD *************************************
        */

       /*
        * Missing Tag. Do not copy NULL value
        */
       if ( obj == null )
       {
           return Boolean.FALSE;
       }

       /*
        * 
        * clear out current value with blank
        */

       if ( ( obj != null ) && ( obj.isNil() ) )
       {
           return Boolean.TRUE;
       }

       /*
        * DEFAULT VALUE
        */
       return Boolean.FALSE;
   }

}

