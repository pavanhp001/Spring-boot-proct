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
package abc.xyz.pts.bcs.admin.model;

import java.io.Serializable;

public class UserRole implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = -1710804511092158993L;

    private String code;
    private String englishDesc;
    private String arabicDesc;
    
    
    
	public String getCode() {
		return code;
	}
	public void setCode(final String code) {
		this.code = code;
	}
	public String getEnglishDesc() {
		return englishDesc;
	}
	public void setEnglishDesc(final String englishDesc) {
		this.englishDesc = englishDesc;
	}
	public String getArabicDesc() {
		return arabicDesc;
	}
	public void setArabicDesc(final String arabicDesc) {
		this.arabicDesc = arabicDesc;
	}



}
