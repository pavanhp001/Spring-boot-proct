package abc.xyz.pts.bcs.common.audit.aspect;

import abc.xyz.pts.bcs.common.audit.annotation.AuditPropertyGroup;
import abc.xyz.pts.bcs.common.audit.messages.AuditEvent;

public class AbstractAuditAspectTestImpl extends AbstractAuditAspect {

	public void testCreateAdditionalParams(final Object command, final AuditEvent ae, final AuditPropertyGroup activePropertyGroup){
		createAdditionalParameters(command, ae, activePropertyGroup);
	}
}
