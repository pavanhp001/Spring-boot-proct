/* This code contains copyright information which is the proprietary property
 * of   Advanced Travel Solutions. No part of this code may be reproduced,
 * stored or transmitted in any form without the prior written permission of
 *   Advanced Travel Solutions.
 *
 * Copyright   Advanced Travel Solutions 2011
 * Confidential. All rights reserved.
 */
package abc.xyz.pts.bcs.common.business.lookup.bean;


public class CPRDataSourceBean implements Comparable<CPRDataSourceBean>  {

    private String cprDataSourceName;

    /**
     * @return the cprDataSourceName
     */
    public String getCprDataSourceName() {
        return cprDataSourceName;
    }

    /* (non-Javadoc)
     * @see java.lang.Object#hashCode()
     */
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result
                + ((cprDataSourceName == null) ? 0 : cprDataSourceName.hashCode());
        return result;
    }

    /* (non-Javadoc)
     * @see java.lang.Object#equals(java.lang.Object)
     */
    @Override
    public boolean equals(final Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (!(obj instanceof CPRDataSourceBean)) {
            return false;
        }
        final CPRDataSourceBean other = (CPRDataSourceBean) obj;
        if (cprDataSourceName == null) {
            if (other.cprDataSourceName != null) {
                return false;
            }
        } else if (!cprDataSourceName.equals(other.cprDataSourceName)) {
            return false;
        }
        return true;
    }

    /**
     * @param cprDataSourceName the dataSourceName to set
     */
    public void setDataSourceName(final String dataSourceName) {
        this.cprDataSourceName = dataSourceName;
    }

     @Override
    public int compareTo(final CPRDataSourceBean o) {
        final int result = this.getCprDataSourceName().compareTo(o.getCprDataSourceName());
        return result;
    }


}
