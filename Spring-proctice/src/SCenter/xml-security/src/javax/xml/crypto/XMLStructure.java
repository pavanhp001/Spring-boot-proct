/*
 * Copyright 2005 The Apache Software Foundation.
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 *
 */
/*
 * Copyright 2005 Sun Microsystems, Inc. All rights reserved.
 */
/*
 * $Id: XMLStructure.java 375655 2006-02-07 18:35:54 +0000 (Tue, 07 Feb 2006) mullan $
 */
package javax.xml.crypto;

/**
 * A representation of an XML structure from any namespace. The purpose of 
 * this interface is to group (and provide type safety for) all 
 * representations of XML structures.
 *
 * @author Sean Mullan
 * @author JSR 105 Expert Group
 */
public interface XMLStructure {

    /**
     * Indicates whether a specified feature is supported.
     *
     * @param feature the feature name (as an absolute URI)
     * @return <code>true</code> if the specified feature is supported,
     *    <code>false</code> otherwise
     * @throws NullPointerException if <code>feature</code> is <code>null</code>
     */
    boolean isFeatureSupported(String feature);
}
