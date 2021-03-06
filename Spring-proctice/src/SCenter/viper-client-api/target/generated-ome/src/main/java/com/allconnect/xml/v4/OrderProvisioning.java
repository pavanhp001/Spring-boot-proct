package com.A.xml.v4;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;
import javax.xml.bind.annotation.XmlSeeAlso;

/**
 * This class was generated by Apache CXF 2.6.0
 * 2018-03-21T19:20:50.681+05:30
 * Generated source version: 2.6.0
 * 
 */
@WebService(targetNamespace = "http://xml.A.com/v4/OrderProvisioning/", name = "orderProvisioning")
@XmlSeeAlso({ObjectFactory.class})
@SOAPBinding(parameterStyle = SOAPBinding.ParameterStyle.BARE)
public interface OrderProvisioning {

    @WebResult(name = "opResponse", targetNamespace = "http://xml.A.com/v4/OrderProvisioning/", partName = "parameters")
    @WebMethod(action = "http://www.example.org/orderProvisioning/NewOperation")
    public OpResponse processRequest(
        @WebParam(partName = "parameters", name = "opRequest", targetNamespace = "http://xml.A.com/v4/OrderProvisioning/")
        OpRequest parameters
    );
}
