package abc.xyz.pts.bcs.common.audit.integration;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;

public class MessageSenderTest {
    
    private ApplicationContext context;
    
    @Before
    public void setup(){
        //context = new FileSystemXmlApplicationContext("src/main/resources/audit-client-context.xml");
    }
    
    @Test
    public void testMessageSend() throws Exception{
        /*Random r = new Random();
        AuditMessageSender auditMessageSender = (AuditMessageSender) context.getBean("auditMessageSender");
        for(int i = 0; i < 600; i++){
            AuditEvent ae = new AuditEvent();
            ae.setUserId("Test user"+r.nextInt(10));
            ae.setCreatedDate(new Date());
            ae.setName(Event.PRINT.value());
            ae.setResponseStatus("SUCCESS");
            ae.setResponseDate(new Date(r.nextInt(250)));
            AuditEventParameter aep = new AuditEventParameter();
            aep.setName(Parameter.SCREEN_NAME.value());
            aep.setValue(Event.ACTIVE_ALERTS.value());
            ae.getParameters().add(aep);
            auditMessageSender.send(ae);
        }
        */
    }
    
    @After
    public void tearDown(){
        
    }
}
