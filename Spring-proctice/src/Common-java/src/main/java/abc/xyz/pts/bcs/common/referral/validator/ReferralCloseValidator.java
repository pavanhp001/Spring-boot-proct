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
package abc.xyz.pts.bcs.common.referral.validator;

import org.springframework.context.MessageSource;
import org.springframework.context.support.MessageSourceAccessor;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import abc.xyz.pts.bcs.common.referral.web.command.ReferralHitCommand;

public class ReferralCloseValidator implements Validator {

    private MessageSourceAccessor messageSourceAccessor;


    @Override
    @SuppressWarnings("rawtypes")
    public boolean supports(final Class clazz) {
        return (clazz.equals(ReferralCloseValidator.class));
    }

    @Override
    public void validate(final Object target, final Errors errors) {

        final ReferralHitCommand referralHitCommand = (ReferralHitCommand) target;

        if (referralHitCommand.hasAnyRuledInHit()) {
             errors.reject("close.referral.error", messageSourceAccessor.getMessage("close.referral.error"));
        }
    }


    public void setMessageSource(final MessageSource messageSource) {
        this.messageSourceAccessor = new MessageSourceAccessor(messageSource);
    }

}
