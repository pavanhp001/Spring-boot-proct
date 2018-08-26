
package com.A.html;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElements;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for Block complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="Block">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;choice maxOccurs="unbounded" minOccurs="0">
 *         &lt;group ref="{http://schemas.microsoft.com/intellisense/html-5}block"/>
 *         &lt;element ref="{http://schemas.microsoft.com/intellisense/html-5}form"/>
 *         &lt;group ref="{http://schemas.microsoft.com/intellisense/html-5}misc"/>
 *       &lt;/choice>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Block", propOrder = {
    "pOrH1OrH2"
})
public class Block {

    @XmlElements({
        @XmlElement(name = "hr", type = Hr.class),
        @XmlElement(name = "div", type = Div.class),
        @XmlElement(name = "blockquote", type = Blockquote.class),
        @XmlElement(name = "p", type = P.class),
        @XmlElement(name = "table", type = Table.class),
        @XmlElement(name = "ins", type = Ins.class),
        @XmlElement(name = "h5", type = H5 .class),
        @XmlElement(name = "ol", type = Ol.class),
        @XmlElement(name = "script", type = Script.class),
        @XmlElement(name = "noscript", type = Noscript.class),
        @XmlElement(name = "h1", type = H1 .class),
        @XmlElement(name = "h3", type = H3 .class),
        @XmlElement(name = "pre", type = Pre.class),
        @XmlElement(name = "del", type = Del.class),
        @XmlElement(name = "address", type = SimpleFlowContentElement.class),
        @XmlElement(name = "h4", type = H4 .class),
        @XmlElement(name = "fieldset", type = Fieldset.class),
        @XmlElement(name = "form", type = Form.class),
        @XmlElement(name = "ul", type = Ul.class),
        @XmlElement(name = "h6", type = H6 .class),
        @XmlElement(name = "h2", type = H2 .class),
        @XmlElement(name = "dl", type = Dl.class)
    })
    protected List<java.lang.Object> pOrH1OrH2;

    /**
     * Gets the value of the pOrH1OrH2 property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the pOrH1OrH2 property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getPOrH1OrH2().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link Hr }
     * {@link Div }
     * {@link Blockquote }
     * {@link P }
     * {@link Table }
     * {@link Ins }
     * {@link H5 }
     * {@link Ol }
     * {@link Script }
     * {@link Noscript }
     * {@link H1 }
     * {@link H3 }
     * {@link Pre }
     * {@link Del }
     * {@link SimpleFlowContentElement }
     * {@link H4 }
     * {@link Fieldset }
     * {@link Form }
     * {@link Ul }
     * {@link H6 }
     * {@link H2 }
     * {@link Dl }
     * 
     * 
     */
    public List<java.lang.Object> getPOrH1OrH2() {
        if (pOrH1OrH2 == null) {
            pOrH1OrH2 = new ArrayList<java.lang.Object>();
        }
        return this.pOrH1OrH2;
    }

}
