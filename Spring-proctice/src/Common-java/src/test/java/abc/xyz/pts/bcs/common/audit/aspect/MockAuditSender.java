package abc.xyz.pts.bcs.common.audit.aspect;

import java.util.ArrayList;
import java.util.List;

import abc.xyz.pts.bcs.common.audit.business.AuditException;
import abc.xyz.pts.bcs.common.audit.business.AuditMessageSender;
import abc.xyz.pts.bcs.common.audit.messages.AuditEvent;

public class MockAuditSender implements AuditMessageSender
{

	private List<AuditEvent> auditEvents = new ArrayList<AuditEvent>();

	public List<AuditEvent> getAuditEvents() {
		return auditEvents;
	}

	@Override
	public void send(final AuditEvent ae) throws AuditException {
		auditEvents.add(ae);
	}
	
}
