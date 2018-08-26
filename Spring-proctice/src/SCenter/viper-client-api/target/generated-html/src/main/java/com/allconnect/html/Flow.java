
package com.A.html;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElementRef;
import javax.xml.bind.annotation.XmlElementRefs;
import javax.xml.bind.annotation.XmlMixed;
import javax.xml.bind.annotation.XmlType;


/**
 * 
 *       "Flow" mixes block and inline and is used for list items etc.
 *       
 * 
 * <p>Java class for Flow complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="Flow">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;choice maxOccurs="unbounded" minOccurs="0">
 *         &lt;group ref="{http://schemas.microsoft.com/intellisense/html-5}block"/>
 *         &lt;element ref="{http://schemas.microsoft.com/intellisense/html-5}form"/>
 *         &lt;group ref="{http://schemas.microsoft.com/intellisense/html-5}inline"/>
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
@XmlType(name = "Flow", propOrder = {
    "content"
})
public class Flow {

    @XmlElementRefs({
        @XmlElementRef(name = "input", namespace = "http://schemas.microsoft.com/intellisense/html-5", type = Input.class),
        @XmlElementRef(name = "address", namespace = "http://schemas.microsoft.com/intellisense/html-5", type = JAXBElement.class),
        @XmlElementRef(name = "h2", namespace = "http://schemas.microsoft.com/intellisense/html-5", type = H2 .class),
        @XmlElementRef(name = "textarea", namespace = "http://schemas.microsoft.com/intellisense/html-5", type = Textarea.class),
        @XmlElementRef(name = "script", namespace = "http://schemas.microsoft.com/intellisense/html-5", type = Script.class),
        @XmlElementRef(name = "div", namespace = "http://schemas.microsoft.com/intellisense/html-5", type = Div.class),
        @XmlElementRef(name = "h1", namespace = "http://schemas.microsoft.com/intellisense/html-5", type = H1 .class),
        @XmlElementRef(name = "sub", namespace = "http://schemas.microsoft.com/intellisense/html-5", type = Sub.class),
        @XmlElementRef(name = "img", namespace = "http://schemas.microsoft.com/intellisense/html-5", type = Img.class),
        @XmlElementRef(name = "h5", namespace = "http://schemas.microsoft.com/intellisense/html-5", type = H5 .class),
        @XmlElementRef(name = "table", namespace = "http://schemas.microsoft.com/intellisense/html-5", type = Table.class),
        @XmlElementRef(name = "bdo", namespace = "http://schemas.microsoft.com/intellisense/html-5", type = Bdo.class),
        @XmlElementRef(name = "strong", namespace = "http://schemas.microsoft.com/intellisense/html-5", type = Strong.class),
        @XmlElementRef(name = "em", namespace = "http://schemas.microsoft.com/intellisense/html-5", type = Em.class),
        @XmlElementRef(name = "code", namespace = "http://schemas.microsoft.com/intellisense/html-5", type = Code.class),
        @XmlElementRef(name = "del", namespace = "http://schemas.microsoft.com/intellisense/html-5", type = Del.class),
        @XmlElementRef(name = "label", namespace = "http://schemas.microsoft.com/intellisense/html-5", type = Label.class),
        @XmlElementRef(name = "h6", namespace = "http://schemas.microsoft.com/intellisense/html-5", type = H6 .class),
        @XmlElementRef(name = "p", namespace = "http://schemas.microsoft.com/intellisense/html-5", type = P.class),
        @XmlElementRef(name = "button", namespace = "http://schemas.microsoft.com/intellisense/html-5", type = Button.class),
        @XmlElementRef(name = "sup", namespace = "http://schemas.microsoft.com/intellisense/html-5", type = Sup.class),
        @XmlElementRef(name = "hr", namespace = "http://schemas.microsoft.com/intellisense/html-5", type = Hr.class),
        @XmlElementRef(name = "kbd", namespace = "http://schemas.microsoft.com/intellisense/html-5", type = Kbd.class),
        @XmlElementRef(name = "i", namespace = "http://schemas.microsoft.com/intellisense/html-5", type = I.class),
        @XmlElementRef(name = "noscript", namespace = "http://schemas.microsoft.com/intellisense/html-5", type = Noscript.class),
        @XmlElementRef(name = "small", namespace = "http://schemas.microsoft.com/intellisense/html-5", type = Small.class),
        @XmlElementRef(name = "map", namespace = "http://schemas.microsoft.com/intellisense/html-5", type = Map.class),
        @XmlElementRef(name = "dfn", namespace = "http://schemas.microsoft.com/intellisense/html-5", type = Dfn.class),
        @XmlElementRef(name = "abbr", namespace = "http://schemas.microsoft.com/intellisense/html-5", type = Abbr.class),
        @XmlElementRef(name = "ul", namespace = "http://schemas.microsoft.com/intellisense/html-5", type = Ul.class),
        @XmlElementRef(name = "blockquote", namespace = "http://schemas.microsoft.com/intellisense/html-5", type = Blockquote.class),
        @XmlElementRef(name = "form", namespace = "http://schemas.microsoft.com/intellisense/html-5", type = Form.class),
        @XmlElementRef(name = "object", namespace = "http://schemas.microsoft.com/intellisense/html-5", type = com.A.html.Object.class),
        @XmlElementRef(name = "pre", namespace = "http://schemas.microsoft.com/intellisense/html-5", type = Pre.class),
        @XmlElementRef(name = "cite", namespace = "http://schemas.microsoft.com/intellisense/html-5", type = Cite.class),
        @XmlElementRef(name = "ol", namespace = "http://schemas.microsoft.com/intellisense/html-5", type = Ol.class),
        @XmlElementRef(name = "ins", namespace = "http://schemas.microsoft.com/intellisense/html-5", type = Ins.class),
        @XmlElementRef(name = "tt", namespace = "http://schemas.microsoft.com/intellisense/html-5", type = Tt.class),
        @XmlElementRef(name = "a", namespace = "http://schemas.microsoft.com/intellisense/html-5", type = A.class),
        @XmlElementRef(name = "var", namespace = "http://schemas.microsoft.com/intellisense/html-5", type = Var.class),
        @XmlElementRef(name = "q", namespace = "http://schemas.microsoft.com/intellisense/html-5", type = Q.class),
        @XmlElementRef(name = "h3", namespace = "http://schemas.microsoft.com/intellisense/html-5", type = H3 .class),
        @XmlElementRef(name = "br", namespace = "http://schemas.microsoft.com/intellisense/html-5", type = Br.class),
        @XmlElementRef(name = "dl", namespace = "http://schemas.microsoft.com/intellisense/html-5", type = Dl.class),
        @XmlElementRef(name = "span", namespace = "http://schemas.microsoft.com/intellisense/html-5", type = Span.class),
        @XmlElementRef(name = "select", namespace = "http://schemas.microsoft.com/intellisense/html-5", type = Select.class),
        @XmlElementRef(name = "samp", namespace = "http://schemas.microsoft.com/intellisense/html-5", type = Samp.class),
        @XmlElementRef(name = "acronym", namespace = "http://schemas.microsoft.com/intellisense/html-5", type = Acronym.class),
        @XmlElementRef(name = "big", namespace = "http://schemas.microsoft.com/intellisense/html-5", type = Big.class),
        @XmlElementRef(name = "h4", namespace = "http://schemas.microsoft.com/intellisense/html-5", type = H4 .class),
        @XmlElementRef(name = "b", namespace = "http://schemas.microsoft.com/intellisense/html-5", type = B.class),
        @XmlElementRef(name = "fieldset", namespace = "http://schemas.microsoft.com/intellisense/html-5", type = Fieldset.class)
    })
    @XmlMixed
    protected List<java.lang.Object> content;

    /**
     * 
     *       "Flow" mixes block and inline and is used for list items etc.
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
     * {@link Input }
     * {@link String }
     * {@link JAXBElement }{@code <}{@link SimpleFlowContentElement }{@code >}
     * {@link H2 }
     * {@link Textarea }
     * {@link Script }
     * {@link Div }
     * {@link H1 }
     * {@link Sub }
     * {@link Img }
     * {@link H5 }
     * {@link Table }
     * {@link Bdo }
     * {@link Strong }
     * {@link Em }
     * {@link Code }
     * {@link Del }
     * {@link Label }
     * {@link H6 }
     * {@link P }
     * {@link Button }
     * {@link Sup }
     * {@link Hr }
     * {@link Kbd }
     * {@link I }
     * {@link Noscript }
     * {@link Small }
     * {@link Map }
     * {@link Dfn }
     * {@link Abbr }
     * {@link Ul }
     * {@link Blockquote }
     * {@link Form }
     * {@link com.A.html.Object }
     * {@link Pre }
     * {@link Cite }
     * {@link Ol }
     * {@link Ins }
     * {@link Tt }
     * {@link A }
     * {@link Var }
     * {@link Q }
     * {@link H3 }
     * {@link Br }
     * {@link Dl }
     * {@link Span }
     * {@link Select }
     * {@link Samp }
     * {@link H4 }
     * {@link Acronym }
     * {@link Big }
     * {@link Fieldset }
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
