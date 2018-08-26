
package com.A.html;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.XmlValue;


/**
 * <p>Java class for anonymous complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType>
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;attGroup ref="{http://schemas.microsoft.com/intellisense/html-5}coreAttributeGroup"/>
 *       &lt;attribute name="charset" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="type" use="required">
 *         &lt;simpleType>
 *           &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *             &lt;enumeration value="text/ecmascript"/>
 *             &lt;enumeration value="text/javascript"/>
 *             &lt;enumeration value="text/javascript1.0"/>
 *             &lt;enumeration value="text/javascript1.1"/>
 *             &lt;enumeration value="text/javascript1.2"/>
 *             &lt;enumeration value="text/javascript1.3"/>
 *             &lt;enumeration value="text/javascript1.4"/>
 *             &lt;enumeration value="text/javascript1.5"/>
 *             &lt;enumeration value="text/jscript"/>
 *             &lt;enumeration value="text/x-javascript"/>
 *             &lt;enumeration value="text/x-ecmascript"/>
 *             &lt;enumeration value="text/vbscript"/>
 *           &lt;/restriction>
 *         &lt;/simpleType>
 *       &lt;/attribute>
 *       &lt;attribute name="src" type="{http://www.w3.org/2001/XMLSchema}anyURI" />
 *       &lt;attribute name="defer">
 *         &lt;simpleType>
 *           &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *             &lt;enumeration value="defer"/>
 *           &lt;/restriction>
 *         &lt;/simpleType>
 *       &lt;/attribute>
 *       &lt;attribute name="async">
 *         &lt;simpleType>
 *           &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *             &lt;enumeration value="async"/>
 *           &lt;/restriction>
 *         &lt;/simpleType>
 *       &lt;/attribute>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "content"
})
@XmlRootElement(name = "script")
public class Script {

    @XmlValue
    protected String content;
    @XmlAttribute(name = "charset")
    protected String charset;
    @XmlAttribute(name = "type", required = true)
    protected String type;
    @XmlAttribute(name = "src")
    @XmlSchemaType(name = "anyURI")
    protected String src;
    @XmlAttribute(name = "defer")
    protected String defer;
    @XmlAttribute(name = "async")
    protected String async;
    @XmlAttribute(name = "runat")
    protected String runat;
    @XmlAttribute(name = "accesskey")
    @XmlSchemaType(name = "anySimpleType")
    protected String accesskey;
    @XmlAttribute(name = "class")
    @XmlSchemaType(name = "anySimpleType")
    protected String clazz;
    @XmlAttribute(name = "contenteditable")
    protected String contenteditable;
    @XmlAttribute(name = "contextmenu")
    @XmlSchemaType(name = "anySimpleType")
    protected String contextmenu;
    @XmlAttribute(name = "data-FolderName")
    @XmlSchemaType(name = "anySimpleType")
    protected String dataFolderName;
    @XmlAttribute(name = "data-MsgId")
    @XmlSchemaType(name = "anySimpleType")
    protected String dataMsgId;
    @XmlAttribute(name = "dir")
    protected String dir;
    @XmlAttribute(name = "draggable")
    protected String draggable;
    @XmlAttribute(name = "id")
    @XmlSchemaType(name = "anySimpleType")
    protected String id;
    @XmlAttribute(name = "item")
    @XmlSchemaType(name = "anySimpleType")
    protected String item;
    @XmlAttribute(name = "hidden")
    protected String hidden;
    @XmlAttribute(name = "lang")
    protected String lang;
    @XmlAttribute(name = "itemprop")
    @XmlSchemaType(name = "anySimpleType")
    protected String itemprop;
    @XmlAttribute(name = "role")
    @XmlSchemaType(name = "anySimpleType")
    protected String role;
    @XmlAttribute(name = "spellcheck")
    protected String spellcheck;
    @XmlAttribute(name = "style")
    @XmlSchemaType(name = "anySimpleType")
    protected String style;
    @XmlAttribute(name = "subject")
    @XmlSchemaType(name = "anySimpleType")
    protected String subject;
    @XmlAttribute(name = "tabIndex")
    @XmlSchemaType(name = "anySimpleType")
    protected String tabIndex;
    @XmlAttribute(name = "title1")
    @XmlSchemaType(name = "anySimpleType")
    protected String title1;

    /**
     * Gets the value of the content property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getContent() {
        return content;
    }

    /**
     * Sets the value of the content property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setContent(String value) {
        this.content = value;
    }

    /**
     * Gets the value of the charset property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCharset() {
        return charset;
    }

    /**
     * Sets the value of the charset property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCharset(String value) {
        this.charset = value;
    }

    /**
     * Gets the value of the type property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getType() {
        return type;
    }

    /**
     * Sets the value of the type property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setType(String value) {
        this.type = value;
    }

    /**
     * Gets the value of the src property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSrc() {
        return src;
    }

    /**
     * Sets the value of the src property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSrc(String value) {
        this.src = value;
    }

    /**
     * Gets the value of the defer property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDefer() {
        return defer;
    }

    /**
     * Sets the value of the defer property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDefer(String value) {
        this.defer = value;
    }

    /**
     * Gets the value of the async property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAsync() {
        return async;
    }

    /**
     * Sets the value of the async property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAsync(String value) {
        this.async = value;
    }

    /**
     * Gets the value of the runat property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getRunat() {
        return runat;
    }

    /**
     * Sets the value of the runat property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setRunat(String value) {
        this.runat = value;
    }

    /**
     * Gets the value of the accesskey property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAccesskey() {
        return accesskey;
    }

    /**
     * Sets the value of the accesskey property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAccesskey(String value) {
        this.accesskey = value;
    }

    /**
     * Gets the value of the clazz property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getClazz() {
        return clazz;
    }

    /**
     * Sets the value of the clazz property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setClazz(String value) {
        this.clazz = value;
    }

    /**
     * Gets the value of the contenteditable property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getContenteditable() {
        return contenteditable;
    }

    /**
     * Sets the value of the contenteditable property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setContenteditable(String value) {
        this.contenteditable = value;
    }

    /**
     * Gets the value of the contextmenu property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getContextmenu() {
        return contextmenu;
    }

    /**
     * Sets the value of the contextmenu property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setContextmenu(String value) {
        this.contextmenu = value;
    }

    /**
     * Gets the value of the dataFolderName property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDataFolderName() {
        return dataFolderName;
    }

    /**
     * Sets the value of the dataFolderName property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDataFolderName(String value) {
        this.dataFolderName = value;
    }

    /**
     * Gets the value of the dataMsgId property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDataMsgId() {
        return dataMsgId;
    }

    /**
     * Sets the value of the dataMsgId property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDataMsgId(String value) {
        this.dataMsgId = value;
    }

    /**
     * Gets the value of the dir property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDir() {
        if (dir == null) {
            return "ltr";
        } else {
            return dir;
        }
    }

    /**
     * Sets the value of the dir property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDir(String value) {
        this.dir = value;
    }

    /**
     * Gets the value of the draggable property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDraggable() {
        return draggable;
    }

    /**
     * Sets the value of the draggable property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDraggable(String value) {
        this.draggable = value;
    }

    /**
     * Gets the value of the id property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getId() {
        return id;
    }

    /**
     * Sets the value of the id property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setId(String value) {
        this.id = value;
    }

    /**
     * Gets the value of the item property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getItem() {
        return item;
    }

    /**
     * Sets the value of the item property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setItem(String value) {
        this.item = value;
    }

    /**
     * Gets the value of the hidden property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getHidden() {
        return hidden;
    }

    /**
     * Sets the value of the hidden property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setHidden(String value) {
        this.hidden = value;
    }

    /**
     * Gets the value of the lang property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getLang() {
        return lang;
    }

    /**
     * Sets the value of the lang property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setLang(String value) {
        this.lang = value;
    }

    /**
     * Gets the value of the itemprop property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getItemprop() {
        return itemprop;
    }

    /**
     * Sets the value of the itemprop property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setItemprop(String value) {
        this.itemprop = value;
    }

    /**
     * Gets the value of the role property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getRole() {
        return role;
    }

    /**
     * Sets the value of the role property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setRole(String value) {
        this.role = value;
    }

    /**
     * Gets the value of the spellcheck property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSpellcheck() {
        return spellcheck;
    }

    /**
     * Sets the value of the spellcheck property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSpellcheck(String value) {
        this.spellcheck = value;
    }

    /**
     * Gets the value of the style property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getStyle() {
        return style;
    }

    /**
     * Sets the value of the style property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setStyle(String value) {
        this.style = value;
    }

    /**
     * Gets the value of the subject property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSubject() {
        return subject;
    }

    /**
     * Sets the value of the subject property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSubject(String value) {
        this.subject = value;
    }

    /**
     * Gets the value of the tabIndex property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTabIndex() {
        return tabIndex;
    }

    /**
     * Sets the value of the tabIndex property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTabIndex(String value) {
        this.tabIndex = value;
    }

    /**
     * Gets the value of the title1 property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTitle1() {
        return title1;
    }

    /**
     * Sets the value of the title1 property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTitle1(String value) {
        this.title1 = value;
    }

}
