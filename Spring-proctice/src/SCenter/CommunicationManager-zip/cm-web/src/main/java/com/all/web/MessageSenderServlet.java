package com.AL.web;

import java.io.IOException;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.QueueConnection;
import javax.jms.TextMessage;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.AL.comm.manager.CommunicationManager;
import com.AL.comm.manager.jms.util.JMSConfigManager;

/**
 * Servlet implementation class MessageSenderServlet
 */
public class MessageSenderServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	CommunicationManager<Message, MessageListener> commManager = JMSConfigManager.INSTANCE.createCommunicationManager("default");       
	//@EJB(mappedName="MessageProducer /local-com.AL.beans.MessageProducerLocal")
	//private MessageProducerLocal messageProducer;
    /**
     * @see HttpServlet#HttpServlet()
     */
    public MessageSenderServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//messageProducer.sendMessage();
		//messageProducer.publishMessage();
		//response.sendRedirect("index.jsp");
		publishMessage();
	}

	public void publishMessage() {
		try {
			
			System.out.println("CommuncationManager:........."+commManager);
			String msg= "Test Message .....";
			commManager.sendSync( "endpoint.verizon.in", msg, 10000 );
			System.out.println("Message sent from servlet :"+msg);
		} catch (JMSException e) {
			e.printStackTrace();
		}catch (Exception e) {
			e.printStackTrace();
		}
	}
}
