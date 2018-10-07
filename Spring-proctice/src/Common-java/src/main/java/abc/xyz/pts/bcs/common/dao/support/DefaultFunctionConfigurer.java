/* **************************************************************************
 *                                                            *
 * **************************************************************************
 * This code contains copyright information which is the proprietary property
 * of   Application Solutions. No part of this code may be reproduced,
 * stored or transmitted in any form without the prior written permission of
 *   Application Solutions.
 *
 * Copyright   Application Solutions 2008
 * All rights reserved.
 */
package abc.xyz.pts.bcs.common.dao.support;

import org.springframework.jdbc.core.simple.SimpleJdbcCall;

public class DefaultFunctionConfigurer extends JdbcCallConfigurer<SimpleJdbcCall> {
    @Override
    public SimpleJdbcCall configure(final SimpleJdbcCall simpleJdbcCall) {
        simpleJdbcCall.setReturnValueRequired(true);
        simpleJdbcCall.compile();
        return simpleJdbcCall;
    }
}
