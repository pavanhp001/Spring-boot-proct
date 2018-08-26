package com.A.validation;

import java.util.List;

/**
 * @author ebthomas
 * 
 */

public interface Message
{
	
	 
    /**
     * @return type of this message
     */
    public enum Type
    {

        INFO( 0 ), WARNING( 1 ), ERROR( 2 ),  FATAL( 3 ), SYSTEM( 4 ), DEBUG(5), OUTCOME(6);

        private int code;

        /**
         * @param code code
         */
        private Type( final int code )
        {
            this.code = code;
        }

        public int getCode()
        {
            return code;
        }
    }

    /**
     * @return type of this message
     */
    Type getType();

    /**
     * @return key of this message
     */
    String getMessageKey();

    /**
     * @return code of this message
     */
    Long getMessageCode();

    /**
     * . objects in the context must be ordered from the least specific to most specific
     * 
     * @return list of objects in this message's context
     */
    List<Object> getContextOrdered();
}
