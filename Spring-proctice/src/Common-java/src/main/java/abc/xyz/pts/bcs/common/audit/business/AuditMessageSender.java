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

package abc.xyz.pts.bcs.common.audit.business;

import abc.xyz.pts.bcs.common.audit.messages.AuditEvent;

public interface AuditMessageSender {

    void send(AuditEvent ae) throws AuditException;

}
