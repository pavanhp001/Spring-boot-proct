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
package abc.xyz.pts.bcs.common.dao.support.query;

import java.io.Serializable;

/**
 * Defines the object that can be used in the context of order by clause.
 *
 * @author Kasi.Subramaniam
 *
 */
public class OrderBy implements Serializable {

    private static final long serialVersionUID = -9186737338383579138L;
    private final Table table;
    private final Field field;
    private final boolean isDescending;

    public OrderBy(final Table table, final Field field, final boolean isDescending) {
        this.table = table;
        this.field = field;
        this.isDescending = isDescending;
    }

    public Field getField() {
        return field;
    }

    public Table getTable() {
        return table;
    }

    public boolean isDescending() {
        return isDescending;
    }

    @Override
    public String toString() {
        return table.getValue() + "." + field.toString() + " "+(isDescending?"DESC":"ASC");
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((field == null) ? 0 : field.hashCode());
        result = prime * result + (isDescending ? 1231 : 1237);
        result = prime * result + ((table == null) ? 0 : table.hashCode());
        return result;
    }

    @Override
    public boolean equals(final Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final OrderBy other = (OrderBy) obj;
        if (field == null) {
            if (other.field != null) {
                return false;
            }
        } else if (!field.equals(other.field)) {
            return false;
        }
        if (isDescending != other.isDescending) {
            return false;
        }
        if (table == null) {
            if (other.table != null) {
                return false;
            }
        } else if (!table.equals(other.table)) {
            return false;
        }
        return true;
    }



}
