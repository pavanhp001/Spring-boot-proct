/* This code contains copyright information which is the proprietary property
 * of   Advanced Travel Solutions. No part of this code may be reproduced,
 * stored or transmitted in any form without the prior written permission of
 *   Advanced Travel Solutions.
 *
 * Copyright   Advanced Travel Solutions 2011
 * Confidential. All rights reserved.
 */
package abc.xyz.pts.bcs.wi.dto;

import java.io.Serializable;

public class WatchListName implements Serializable {
    private static final long serialVersionUID = 6686519315095313535L;
    private String description;
    private String descriptionLang;

    private String code;

    public String getCode() {
        return code;
    }

    public void setCode(final String code) {
        this.code = code;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(final String description) {
        this.description = description;
    }

    public String getDescriptionLang() {
        return descriptionLang;
    }

    public void setDescriptionLang(final String descriptionLang) {
        this.descriptionLang = descriptionLang;
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append("WatchListName [description=");
        builder.append(description);
        builder.append(", descriptionLang=");
        builder.append(descriptionLang);
        builder.append(", code=");
        builder.append(code);
        builder.append("]");
        return builder.toString();
    }


}
