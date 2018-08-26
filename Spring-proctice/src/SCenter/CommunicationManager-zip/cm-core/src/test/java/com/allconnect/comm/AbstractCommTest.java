package com.AL.comm;

public abstract class AbstractCommTest {
	
	public String getPayload()
    {
        StringBuilder sb = new StringBuilder();

        sb.append( "<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" " );
        sb.append( " xmlns:ord=\"http://verizon.com/orderingServices/\">  " );
        sb.append( "<soapenv:Header/> " );
        sb.append( "<soapenv:Body> " );
        sb.append( "<ord:createSession> " );
        sb.append( "<ord:client> " );
        sb.append( "<!--Optional:--> " );
        sb.append( "<ord:name>?</ord:name> " );
        sb.append( "<!--Optional:--> " );
        sb.append( "<ord:clientId>?</ord:clientId> " );
        sb.append( "<!--Optional:--> " );
        sb.append( "<ord:requestId>?</ord:requestId> " );
        sb.append( "</ord:client> " );
        sb.append( "<!--Optional:--> " );
        sb.append( "<ord:applicationId>?</ord:applicationId> " );
        sb.append( "<!--Optional:--> " );
        sb.append( "<ord:_accountType>?</ord:_accountType> " );
        sb.append( "<!--Optional:--> " );
        sb.append( "<ord:clientId>?</ord:clientId> " );
        sb.append( "<!--Optional:--> " );
        sb.append( "<ord:salesOffice>?</ord:salesOffice> " );
        sb.append( "<!--Optional:--> " );
        sb.append( "<ord:salesAgentId>?</ord:salesAgentId> " );
        sb.append( "</ord:createSession> " );
        sb.append( "</soapenv:Body> " );
        sb.append( "  </soapenv:Envelope> " );

        return sb.toString();
    }

}
