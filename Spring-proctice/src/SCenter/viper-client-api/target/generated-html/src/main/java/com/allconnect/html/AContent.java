
package com.A.html;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElementRef;
import javax.xml.bind.annotation.XmlElementRefs;
import javax.xml.bind.annotation.XmlMixed;
import javax.xml.bind.annotation.XmlType;


/**
 * 
 *       a elements use "Inline" excluding a
 *       
 * 
 * <p>Java class for a.content complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="a.content">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;choice maxOccurs="unbounded" minOccurs="0">
 *         &lt;group ref="{http://schemas.microsoft.com/intellisense/html-5}special"/>
 *         &lt;group ref="{http://schemas.microsoft.com/intellisense/html-5}fontstyle"/>
 *         &lt;group ref="{http://schemas.microsoft.com/intellisense/html-5}phrase"/>
 *         &lt;group ref="{http://schemas.microsoft.com/intellisense/html-5}inline.forms"/>
 *         &lt;group ref="{http://schemas.microsoft.com/intellisense/html-5}misc.inline"/>
 *       &lt;/choice>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "a.content", propOrder = {
    "content"
})
public class AContent {

    @XmlElementRefs({
        @XmlElementRef(name = "kbd", namespace = "http://schemas.microsoft.com/intellisense/html-5", type = Kbd.class),
        @XmlElementRef(name = "input", namespace = "http://schemas.microsoft.com/intellisense/html-5", type = Input.class),
        @XmlElementRef(name = "i", namespace = "http://schemas.microsoft.com/intellisense/html-5", type = I.class),
        @XmlElementRef(name = "small", namespace = "http://schemas.microsoft.com/intellisense/html-5", type = Small.class),
        @XmlElementRef(name = "map", namespace = "http://schemas.microsoft.com/intellisense/html-5", type = Map.class),
        @XmlElementRef(name = "dfn", namespace = "http://schemas.microsoft.com/intellisense/html-5", type = Dfn.class),
        @XmlElementRef(name = "abbr", namespace = "http://schemas.microsoft.com/intellisense/html-5", type = Abbr.class),
        @XmlElementRef(name = "textarea", namespace = "http://schemas.microsoft.com/intellisense/html-5", type = Textarea.class),
        @XmlElementRef(name = "script", namespace = "http://schemas.microsoft.com/intellisense/html-5", type = Script.class),
        @XmlElementRef(name = "object", namespace = "http://schemas.microsoft.com/intellisense/html-5", type = com.A.html.Object.class),
        @XmlElementRef(name = "sub", namespace = "http://schemas.microsoft.com/intellisense/html-5", type = Sub.class),
        @XmlElementRef(name = "img", namespace = "http://schemas.microsoft.com/intellisense/html-5", type = Img.class),
        @XmlElementRef(name = "cite", namespace = "http://schemas.microsoft.com/intellisense/html-5", type = Cite.class),
        @XmlElementRef(name = "bdo", namespace = "http://schemas.microsoft.com/intellisense/html-5", type = Bdo.class),
        @XmlElementRef(name = "ins", namespace = "http://schemas.microsoft.com/intellisense/html-5", type = Ins.class),
        @XmlElementRef(name = "strong", namespace = "http://schemas.microsoft.com/intellisense/html-5", type = Strong.class),
        @XmlElementRef(name = "tt", namespace = "http://schemas.microsoft.com/intellisense/html-5", type = Tt.class),
        @XmlElementRef(name = "em", namespace = "http://schemas.microsoft.com/intellisense/html-5", type = Em.class),
        @XmlElementRef(name = "var", namespace = "http://schemas.microsoft.com/intellisense/html-5", type = Var.class),
        @XmlElementRef(name = "q", namespace = "http://schemas.microsoft.com/intellisense/html-5", type = Q.class),
        @XmlElementRef(name = "code", namespace = "http://schemas.microsoft.com/intellisense/html-5", type = Code.class),
        @XmlElementRef(name = "del", namespace = "http://schemas.microsoft.com/intellisense/html-5", type = Del.class),
        @XmlElementRef(name = "br", namespace = "http://schemas.microsoft.com/intellisense/html-5", type = Br.class),
        @XmlElementRef(name = "span", namespace = "http://schemas.microsoft.com/intellisense/html-5", type = Span.class),
        @XmlElementRef(name = "label", namespace = "http://schemas.microsoft.com/intellisense/html-5", type = Label.class),
        @XmlElementRef(name = "select", namespace = "http://schemas.microsoft.com/intellisense/html-5", type = Select.class),
        @XmlElementRef(name = "samp", namespace = "http://schemas.microsoft.com/intellisense/html-5", type = Samp.class),
        @XmlElementRef(name = "button", namespace = "http://schemas.microsoft.com/intellisense/html-5", type = Button.class),
        @XmlElementRef(name = "acronym", namespace = "http://schemas.microsoft.com/intellisense/html-5", type = Acronym.class),
        @XmlElementRef(name = "big", namespace = "http://schemas.microsoft.com/intellisense/html-5", type = Big.class),
        @XmlElementRef(name = "b", namespace = "http://schemas.microsoft.com/intellisense/html-5", type = B.class),
        @XmlElementRef(name = "sup", namespace = "http://schemas.microsoft.com/intellisense/html-5", type = Sup.class)
    })
    @XmlMixed
    protected List<java.lang.Object> content;

    /**
     * 
     *       a elements use "Inline" excluding a
     *       Gets the value of the content property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the content property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getContent().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link Kbd }
     * {@link Input }
     * {@link I }
     * {@link Small }
     * {@link String }
     * {@link Map }
     * {@link Dfn }
     * {@link Abbr }
     * {@link Textarea }
     * {@link Script }
     * {@link com.A.html.Object }
     * {@link Sub }
     * {@link Img }
     * {@link Cite }
     * {@link Bdo }
     * {@link Ins }
     * {@link Strong }
     * {@link Tt }
     * {@link Em }
     * {@link Var }
     * {@link Q }
     * {@link Code }
     * {@link Del }
     * {@link Br }
     * {@link Span }
     * {@link Label }
     * {@link Select }
     * {@link Samp }
     * {@link Button }
     * {@link Big }
     * {@link Acronym }
     * {@link Sup }
     * {@link B }
     * 
     * 
     */
    public List<java.lang.Object> getContent() {
        if (content == null) {
            content = new ArrayList<java.lang.Object>();
        }
        return this.content;
    }

}
