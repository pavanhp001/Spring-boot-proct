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
package abc.xyz.pts.bcs.wi.business.impl;

import abc.xyz.pts.bcs.wi.business.IIRSearchRequestFactory;
import abc.xyz.pts.bcs.wi.iir.search.IIRSearchRequest;
import abc.xyz.pts.bcs.wi.iir.search.IIRSearchRequestImpl;

/**
 * <p>IIR Search Request factory</p>
 * @author Kasi.Subramaniam
 *
 */
public class IIRSearchRequestFactoryImpl implements IIRSearchRequestFactory {

	public IIRSearchRequest getIIRSearchRequest() {
		return new IIRSearchRequestImpl();
	}
}
