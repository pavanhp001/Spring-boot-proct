/**
 *
 */
package com.A.validation;

import java.util.Map;
import java.util.Set;

/**
 * @author ebthomas
 * 
 */
public interface ValidationReport {

	/**
	 * @return Message Map;
	 */
	public Map<Message.Type, Set<Message>> getMessagesMap();

	/**
	 * @return all messages in this report
	 */
	Set<Message> getMessages();

	/**
	 * @param type
	 *            type of message to get by type
	 * @return all messages of specified type in this report
	 */
	Set<Message> getMessagesByType(final Message.Type type);

	/**
	 * @param messageKey
	 *            message key
	 * @return true if this report contains message with specified key, false
	 *         otherwise
	 */
	Message getMessage(final String messageKey);

	/**
	 * @param messageKey
	 *            message key
	 * @return true if this report contains message with specified key, false
	 *         otherwise
	 */
	boolean contains(final String messageKey);

	/**
	 * @param type
	 *            type of message
	 * @return true if this report contains message with specified key, false
	 *         otherwise
	 */
	boolean hasMessages(final Message.Type type);

	/**
	 * @return true if this report contains message with specified key, false
	 *         otherwise
	 */
	boolean hasMessages();

	/**
	 * @return true if this report contains message with specified key, false
	 *         otherwise
	 */
	boolean hasFatal();

	/**
	 * @return true if this report contains message with specified key, false
	 *         otherwise
	 */
	boolean hasErrors();

	/**
	 * @param type
	 *            type of message
	 * @return true if this report contains message with specified key, false
	 *         otherwise
	 */
	boolean hasWarnings();

	/**
	 * @param type
	 *            type of message
	 * @return true if this report contains message with specified key, false
	 *         otherwise
	 */
	boolean hasInfo();

	/**
	 * @param type
	 *            type of message
	 * @return true if this report contains message with specified key, false
	 *         otherwise
	 */
	boolean contains(final Message.Type type);

	/**
	 * adds specified message to this report
	 */
	/**
	 * @param message
	 *            source
	 * @return boolean indicating success of process
	 */
	boolean addMessages(final Set<Message> message);

	/**
	 * adds specified message to this report
	 */
	/**
	 * @param message
	 *            source
	 * @return boolean indicating success of process
	 */
	boolean addMessage(final Message message);

	/**
	 * @param type
	 *            Message Type
	 * @param messageCode
	 *            Message Code
	 * @param messageKey
	 *            Message Key
	 * @param context
	 *            Context
	 * @return Newly Created Message
	 */
	Message addMessage(final Message.Type type, final Long messageCode,
			final String messageKey, final Object... context);
	
	Message addInfoMessage( final Long messageCode,
			final String messageKey );
	
	Message addErrorMessage( final Long messageCode,
			final String messageKey );

	Set<Message> getOutcome();

	Set<Message> getError();

	Set<Message> getFatal();

	Set<Message> getWarning();

	Set<Message> getDebug();

	Set<Message> getInfo();

	Set<Message> getSystem();
	 
}
