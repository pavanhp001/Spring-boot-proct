
package com.A.html;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElementRef;
import javax.xml.bind.annotation.XmlElementRefs;
import javax.xml.bind.annotation.XmlMixed;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for anonymous complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType>
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;choice>
 *         &lt;group ref="{http://schemas.microsoft.com/intellisense/html-5}phrasingContent"/>
 *       &lt;/choice>
 *       &lt;attGroup ref="{http://schemas.microsoft.com/intellisense/html-5}commonAttributeGroup"/>
 *       &lt;attribute name="headers" type="{http://www.w3.org/2001/XMLSchema}anySimpleType" />
 *       &lt;attribute name="rowspan" type="{http://www.w3.org/2001/XMLSchema}integer" default="1" />
 *       &lt;attribute name="colspan" type="{http://www.w3.org/2001/XMLSchema}integer" default="1" />
 *       &lt;attribute name="scope">
 *         &lt;simpleType>
 *           &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *             &lt;enumeration value="col"/>
 *             &lt;enumeration value="colgroup"/>
 *             &lt;enumeration value="row"/>
 *             &lt;enumeration value="rowgroup"/>
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
@XmlRootElement(name = "th")
public class Th {

    @XmlElementRefs({
        @XmlElementRef(name = "input", namespace = "http://schemas.microsoft.com/intellisense/html-5", type = Input.class),
        @XmlElementRef(name = "keygen", namespace = "http://schemas.microsoft.com/intellisense/html-5", type = Keygen.class),
        @XmlElementRef(name = "i", namespace = "http://schemas.microsoft.com/intellisense/html-5", type = I.class),
        @XmlElementRef(name = "kbd", namespace = "http://schemas.microsoft.com/intellisense/html-5", type = Kbd.class),
        @XmlElementRef(name = "noscript", namespace = "http://schemas.microsoft.com/intellisense/html-5", type = Noscript.class),
        @XmlElementRef(name = "small", namespace = "http://schemas.microsoft.com/intellisense/html-5", type = Small.class),
        @XmlElementRef(name = "meta", namespace = "http://schemas.microsoft.com/intellisense/html-5", type = Meta.class),
        @XmlElementRef(name = "video", namespace = "http://schemas.microsoft.com/intellisense/html-5", type = Video.class),
        @XmlElementRef(name = "datalist", namespace = "http://schemas.microsoft.com/intellisense/html-5", type = Datalist.class),
        @XmlElementRef(name = "dfn", namespace = "http://schemas.microsoft.com/intellisense/html-5", type = Dfn.class),
        @XmlElementRef(name = "abbr", namespace = "http://schemas.microsoft.com/intellisense/html-5", type = Abbr.class),
        @XmlElementRef(name = "textarea", namespace = "http://schemas.microsoft.com/intellisense/html-5", type = Textarea.class),
        @XmlElementRef(name = "script", namespace = "http://schemas.microsoft.com/intellisense/html-5", type = Script.class),
        @XmlElementRef(name = "ruby", namespace = "http://schemas.microsoft.com/intellisense/html-5", type = Ruby.class),
        @XmlElementRef(name = "object", namespace = "http://schemas.microsoft.com/intellisense/html-5", type = com.A.html.Object.class),
        @XmlElementRef(name = "audio", namespace = "http://schemas.microsoft.com/intellisense/html-5", type = Audio.class),
        @XmlElementRef(name = "progress", namespace = "http://schemas.microsoft.com/intellisense/html-5", type = Progress.class),
        @XmlElementRef(name = "sub", namespace = "http://schemas.microsoft.com/intellisense/html-5", type = Sub.class),
        @XmlElementRef(name = "img", namespace = "http://schemas.microsoft.com/intellisense/html-5", type = Img.class),
        @XmlElementRef(name = "cite", namespace = "http://schemas.microsoft.com/intellisense/html-5", type = Cite.class),
        @XmlElementRef(name = "canvas", namespace = "http://schemas.microsoft.com/intellisense/html-5", type = Canvas.class),
        @XmlElementRef(name = "bdo", namespace = "http://schemas.microsoft.com/intellisense/html-5", type = Bdo.class),
        @XmlElementRef(name = "ins", namespace = "http://schemas.microsoft.com/intellisense/html-5", type = Ins.class),
        @XmlElementRef(name = "time", namespace = "http://schemas.microsoft.com/intellisense/html-5", type = Time.class),
        @XmlElementRef(name = "svg", namespace = "http://schemas.microsoft.com/intellisense/html-5", type = Svg.class),
        @XmlElementRef(name = "strong", namespace = "http://schemas.microsoft.com/intellisense/html-5", type = Strong.class),
        @XmlElementRef(name = "em", namespace = "http://schemas.microsoft.com/intellisense/html-5", type = Em.class),
        @XmlElementRef(name = "a", namespace = "http://schemas.microsoft.com/intellisense/html-5", type = A.class),
        @XmlElementRef(name = "meter", namespace = "http://schemas.microsoft.com/intellisense/html-5", type = Meter.class),
        @XmlElementRef(name = "var", namespace = "http://schemas.microsoft.com/intellisense/html-5", type = Var.class),
        @XmlElementRef(name = "q", namespace = "http://schemas.microsoft.com/intellisense/html-5", type = Q.class),
        @XmlElementRef(name = "del", namespace = "http://schemas.microsoft.com/intellisense/html-5", type = Del.class),
        @XmlElementRef(name = "code", namespace = "http://schemas.microsoft.com/intellisense/html-5", type = Code.class),
        @XmlElementRef(name = "embed", namespace = "http://schemas.microsoft.com/intellisense/html-5", type = Embed.class),
        @XmlElementRef(name = "br", namespace = "http://schemas.microsoft.com/intellisense/html-5", type = Br.class),
        @XmlElementRef(name = "span", namespace = "http://schemas.microsoft.com/intellisense/html-5", type = Span.class),
        @XmlElementRef(name = "label", namespace = "http://schemas.microsoft.com/intellisense/html-5", type = Label.class),
        @XmlElementRef(name = "iframe", namespace = "http://schemas.microsoft.com/intellisense/html-5", type = Iframe.class),
        @XmlElementRef(name = "select", namespace = "http://schemas.microsoft.com/intellisense/html-5", type = Select.class),
        @XmlElementRef(name = "link", namespace = "http://schemas.microsoft.com/intellisense/html-5", type = Link.class),
        @XmlElementRef(name = "area", namespace = "http://schemas.microsoft.com/intellisense/html-5", type = Area.class),
        @XmlElementRef(name = "samp", namespace = "http://schemas.microsoft.com/intellisense/html-5", type = Samp.class),
        @XmlElementRef(name = "button", namespace = "http://schemas.microsoft.com/intellisense/html-5", type = Button.class),
        @XmlElementRef(name = "output", namespace = "http://schemas.microsoft.com/intellisense/html-5", type = Output.class),
        @XmlElementRef(name = "sup", namespace = "http://schemas.microsoft.com/intellisense/html-5", type = Sup.class),
        @XmlElementRef(name = "b", namespace = "http://schemas.microsoft.com/intellisense/html-5", type = B.class),
        @XmlElementRef(name = "command", namespace = "http://schemas.microsoft.com/intellisense/html-5", type = Command.class)
    })
    @XmlMixed
    protected List<java.lang.Object> content;
    @XmlAttribute(name = "headers")
    @XmlSchemaType(name = "anySimpleType")
    protected String headers;
    @XmlAttribute(name = "rowspan")
    protected BigInteger rowspan;
    @XmlAttribute(name = "colspan")
    protected BigInteger colspan;
    @XmlAttribute(name = "scope")
    protected String scope;
    @XmlAttribute(name = "EnableTheming")
    protected Boolean enableTheming;
    @XmlAttribute(name = "EnableViewState")
    protected Boolean enableViewState;
    @XmlAttribute(name = "SkinID")
    protected String skinID;
    @XmlAttribute(name = "Visible")
    protected Boolean visible;
    @XmlAttribute(name = "OnDataBinding")
    @XmlSchemaType(name = "anySimpleType")
    protected String onDataBinding;
    @XmlAttribute(name = "OnDisposed")
    @XmlSchemaType(name = "anySimpleType")
    protected String onDisposed;
    @XmlAttribute(name = "OnInit")
    @XmlSchemaType(name = "anySimpleType")
    protected String onInit;
    @XmlAttribute(name = "OnLoad")
    @XmlSchemaType(name = "anySimpleType")
    protected String onLoad;
    @XmlAttribute(name = "OnPreRender")
    @XmlSchemaType(name = "anySimpleType")
    protected String onPreRender;
    @XmlAttribute(name = "OnUnload")
    @XmlSchemaType(name = "anySimpleType")
    protected String onUnload;
    @XmlAttribute(name = "onabort")
    @XmlSchemaType(name = "anySimpleType")
    protected String onabort;
    @XmlAttribute(name = "onblur")
    @XmlSchemaType(name = "anySimpleType")
    protected String onblur;
    @XmlAttribute(name = "oncanplay")
    @XmlSchemaType(name = "anySimpleType")
    protected String oncanplay;
    @XmlAttribute(name = "oncanplaythrough")
    @XmlSchemaType(name = "anySimpleType")
    protected String oncanplaythrough;
    @XmlAttribute(name = "onchange")
    @XmlSchemaType(name = "anySimpleType")
    protected String onchange;
    @XmlAttribute(name = "onclick")
    @XmlSchemaType(name = "anySimpleType")
    protected String onclick;
    @XmlAttribute(name = "oncontextmenu")
    @XmlSchemaType(name = "anySimpleType")
    protected String oncontextmenu;
    @XmlAttribute(name = "ondblclick")
    @XmlSchemaType(name = "anySimpleType")
    protected String ondblclick;
    @XmlAttribute(name = "ondrag")
    @XmlSchemaType(name = "anySimpleType")
    protected String ondrag;
    @XmlAttribute(name = "ondragenter")
    @XmlSchemaType(name = "anySimpleType")
    protected String ondragenter;
    @XmlAttribute(name = "ondragleave")
    @XmlSchemaType(name = "anySimpleType")
    protected String ondragleave;
    @XmlAttribute(name = "ondragover")
    @XmlSchemaType(name = "anySimpleType")
    protected String ondragover;
    @XmlAttribute(name = "ondragstart")
    @XmlSchemaType(name = "anySimpleType")
    protected String ondragstart;
    @XmlAttribute(name = "ondrop")
    @XmlSchemaType(name = "anySimpleType")
    protected String ondrop;
    @XmlAttribute(name = "ondurationchange")
    @XmlSchemaType(name = "anySimpleType")
    protected String ondurationchange;
    @XmlAttribute(name = "onemptied")
    @XmlSchemaType(name = "anySimpleType")
    protected String onemptied;
    @XmlAttribute(name = "onended")
    @XmlSchemaType(name = "anySimpleType")
    protected String onended;
    @XmlAttribute(name = "onerror")
    @XmlSchemaType(name = "anySimpleType")
    protected String onerror;
    @XmlAttribute(name = "onfocus")
    @XmlSchemaType(name = "anySimpleType")
    protected String onfocus;
    @XmlAttribute(name = "onformchange")
    @XmlSchemaType(name = "anySimpleType")
    protected String onformchange;
    @XmlAttribute(name = "onforminput")
    @XmlSchemaType(name = "anySimpleType")
    protected String onforminput;
    @XmlAttribute(name = "oninput")
    @XmlSchemaType(name = "anySimpleType")
    protected String oninput;
    @XmlAttribute(name = "oninvalid")
    @XmlSchemaType(name = "anySimpleType")
    protected String oninvalid;
    @XmlAttribute(name = "onkeydown")
    @XmlSchemaType(name = "anySimpleType")
    protected String onkeydown;
    @XmlAttribute(name = "onkeypress")
    @XmlSchemaType(name = "anySimpleType")
    protected String onkeypress;
    @XmlAttribute(name = "onkeyup")
    @XmlSchemaType(name = "anySimpleType")
    protected String onkeyup;
    @XmlAttribute(name = "onload")
    @XmlSchemaType(name = "anySimpleType")
    protected String onload;
    @XmlAttribute(name = "onloadeddata")
    @XmlSchemaType(name = "anySimpleType")
    protected String onloadeddata;
    @XmlAttribute(name = "onloadedmetadata")
    @XmlSchemaType(name = "anySimpleType")
    protected String onloadedmetadata;
    @XmlAttribute(name = "onloadstart")
    @XmlSchemaType(name = "anySimpleType")
    protected String onloadstart;
    @XmlAttribute(name = "onmousedown")
    @XmlSchemaType(name = "anySimpleType")
    protected String onmousedown;
    @XmlAttribute(name = "onmousemove")
    @XmlSchemaType(name = "anySimpleType")
    protected String onmousemove;
    @XmlAttribute(name = "onmouseout")
    @XmlSchemaType(name = "anySimpleType")
    protected String onmouseout;
    @XmlAttribute(name = "onmouseover")
    @XmlSchemaType(name = "anySimpleType")
    protected String onmouseover;
    @XmlAttribute(name = "onmouseup")
    @XmlSchemaType(name = "anySimpleType")
    protected String onmouseup;
    @XmlAttribute(name = "onmousewheel")
    @XmlSchemaType(name = "anySimpleType")
    protected String onmousewheel;
    @XmlAttribute(name = "onpause")
    @XmlSchemaType(name = "anySimpleType")
    protected String onpause;
    @XmlAttribute(name = "onplay")
    @XmlSchemaType(name = "anySimpleType")
    protected String onplay;
    @XmlAttribute(name = "onplaying")
    @XmlSchemaType(name = "anySimpleType")
    protected String onplaying;
    @XmlAttribute(name = "onprogress")
    @XmlSchemaType(name = "anySimpleType")
    protected String onprogress;
    @XmlAttribute(name = "onratechange")
    @XmlSchemaType(name = "anySimpleType")
    protected String onratechange;
    @XmlAttribute(name = "onreadystatechange")
    @XmlSchemaType(name = "anySimpleType")
    protected String onreadystatechange;
    @XmlAttribute(name = "onscroll")
    @XmlSchemaType(name = "anySimpleType")
    protected String onscroll;
    @XmlAttribute(name = "onseeked")
    @XmlSchemaType(name = "anySimpleType")
    protected String onseeked;
    @XmlAttribute(name = "onseeking")
    @XmlSchemaType(name = "anySimpleType")
    protected String onseeking;
    @XmlAttribute(name = "onselect")
    @XmlSchemaType(name = "anySimpleType")
    protected String onselect;
    @XmlAttribute(name = "onshow")
    @XmlSchemaType(name = "anySimpleType")
    protected String onshow;
    @XmlAttribute(name = "onstalled")
    @XmlSchemaType(name = "anySimpleType")
    protected String onstalled;
    @XmlAttribute(name = "onsubmit")
    @XmlSchemaType(name = "anySimpleType")
    protected String onsubmit;
    @XmlAttribute(name = "onsuspend")
    @XmlSchemaType(name = "anySimpleType")
    protected String onsuspend;
    @XmlAttribute(name = "ontimeupdate")
    @XmlSchemaType(name = "anySimpleType")
    protected String ontimeupdate;
    @XmlAttribute(name = "onvolumenchange")
    @XmlSchemaType(name = "anySimpleType")
    protected String onvolumenchange;
    @XmlAttribute(name = "onwaiting")
    @XmlSchemaType(name = "anySimpleType")
    protected String onwaiting;
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
     * {@link I }
     * {@link Keygen }
     * {@link Input }
     * {@link Noscript }
     * {@link Small }
     * {@link String }
     * {@link Meta }
     * {@link Datalist }
     * {@link Video }
     * {@link Dfn }
     * {@link Abbr }
     * {@link Textarea }
     * {@link Script }
     * {@link Ruby }
     * {@link com.A.html.Object }
     * {@link Audio }
     * {@link Progress }
     * {@link Sub }
     * {@link Img }
     * {@link Cite }
     * {@link Bdo }
     * {@link Canvas }
     * {@link Time }
     * {@link Ins }
     * {@link Svg }
     * {@link Strong }
     * {@link Em }
     * {@link A }
     * {@link Meter }
     * {@link Var }
     * {@link Q }
     * {@link Embed }
     * {@link Code }
     * {@link Del }
     * {@link Br }
     * {@link Span }
     * {@link Iframe }
     * {@link Label }
     * {@link Select }
     * {@link Link }
     * {@link Samp }
     * {@link Area }
     * {@link Output }
     * {@link Button }
     * {@link Command }
     * {@link B }
     * {@link Sup }
     * 
     * 
     */
    public List<java.lang.Object> getContent() {
        if (content == null) {
            content = new ArrayList<java.lang.Object>();
        }
        return this.content;
    }

    /**
     * Gets the value of the headers property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getHeaders() {
        return headers;
    }

    /**
     * Sets the value of the headers property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setHeaders(String value) {
        this.headers = value;
    }

    /**
     * Gets the value of the rowspan property.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger getRowspan() {
        if (rowspan == null) {
            return new BigInteger("1");
        } else {
            return rowspan;
        }
    }

    /**
     * Sets the value of the rowspan property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setRowspan(BigInteger value) {
        this.rowspan = value;
    }

    /**
     * Gets the value of the colspan property.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger getColspan() {
        if (colspan == null) {
            return new BigInteger("1");
        } else {
            return colspan;
        }
    }

    /**
     * Sets the value of the colspan property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setColspan(BigInteger value) {
        this.colspan = value;
    }

    /**
     * Gets the value of the scope property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getScope() {
        return scope;
    }

    /**
     * Sets the value of the scope property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setScope(String value) {
        this.scope = value;
    }

    /**
     * Gets the value of the enableTheming property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public boolean isEnableTheming() {
        if (enableTheming == null) {
            return true;
        } else {
            return enableTheming;
        }
    }

    /**
     * Sets the value of the enableTheming property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setEnableTheming(Boolean value) {
        this.enableTheming = value;
    }

    /**
     * Gets the value of the enableViewState property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public boolean isEnableViewState() {
        if (enableViewState == null) {
            return true;
        } else {
            return enableViewState;
        }
    }

    /**
     * Sets the value of the enableViewState property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setEnableViewState(Boolean value) {
        this.enableViewState = value;
    }

    /**
     * Gets the value of the skinID property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSkinID() {
        if (skinID == null) {
            return "";
        } else {
            return skinID;
        }
    }

    /**
     * Sets the value of the skinID property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSkinID(String value) {
        this.skinID = value;
    }

    /**
     * Gets the value of the visible property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public boolean isVisible() {
        if (visible == null) {
            return true;
        } else {
            return visible;
        }
    }

    /**
     * Sets the value of the visible property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setVisible(Boolean value) {
        this.visible = value;
    }

    /**
     * Gets the value of the onDataBinding property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getOnDataBinding() {
        return onDataBinding;
    }

    /**
     * Sets the value of the onDataBinding property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setOnDataBinding(String value) {
        this.onDataBinding = value;
    }

    /**
     * Gets the value of the onDisposed property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getOnDisposed() {
        return onDisposed;
    }

    /**
     * Sets the value of the onDisposed property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setOnDisposed(String value) {
        this.onDisposed = value;
    }

    /**
     * Gets the value of the onInit property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getOnInit() {
        return onInit;
    }

    /**
     * Sets the value of the onInit property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setOnInit(String value) {
        this.onInit = value;
    }

    /**
     * Gets the value of the onLoad property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getOnLoad() {
        return onLoad;
    }

    /**
     * Sets the value of the onLoad property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setOnLoad(String value) {
        this.onLoad = value;
    }

    /**
     * Gets the value of the onPreRender property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getOnPreRender() {
        return onPreRender;
    }

    /**
     * Sets the value of the onPreRender property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setOnPreRender(String value) {
        this.onPreRender = value;
    }

    /**
     * Gets the value of the onUnload property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getOnUnload() {
        return onUnload;
    }

    /**
     * Sets the value of the onUnload property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setOnUnload(String value) {
        this.onUnload = value;
    }

    /**
     * Gets the value of the onabort property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getOnabort() {
        return onabort;
    }

    /**
     * Sets the value of the onabort property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setOnabort(String value) {
        this.onabort = value;
    }

    /**
     * Gets the value of the onblur property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getOnblur() {
        return onblur;
    }

    /**
     * Sets the value of the onblur property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setOnblur(String value) {
        this.onblur = value;
    }

    /**
     * Gets the value of the oncanplay property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getOncanplay() {
        return oncanplay;
    }

    /**
     * Sets the value of the oncanplay property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setOncanplay(String value) {
        this.oncanplay = value;
    }

    /**
     * Gets the value of the oncanplaythrough property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getOncanplaythrough() {
        return oncanplaythrough;
    }

    /**
     * Sets the value of the oncanplaythrough property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setOncanplaythrough(String value) {
        this.oncanplaythrough = value;
    }

    /**
     * Gets the value of the onchange property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getOnchange() {
        return onchange;
    }

    /**
     * Sets the value of the onchange property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setOnchange(String value) {
        this.onchange = value;
    }

    /**
     * Gets the value of the onclick property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getOnclick() {
        return onclick;
    }

    /**
     * Sets the value of the onclick property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setOnclick(String value) {
        this.onclick = value;
    }

    /**
     * Gets the value of the oncontextmenu property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getOncontextmenu() {
        return oncontextmenu;
    }

    /**
     * Sets the value of the oncontextmenu property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setOncontextmenu(String value) {
        this.oncontextmenu = value;
    }

    /**
     * Gets the value of the ondblclick property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getOndblclick() {
        return ondblclick;
    }

    /**
     * Sets the value of the ondblclick property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setOndblclick(String value) {
        this.ondblclick = value;
    }

    /**
     * Gets the value of the ondrag property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getOndrag() {
        return ondrag;
    }

    /**
     * Sets the value of the ondrag property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setOndrag(String value) {
        this.ondrag = value;
    }

    /**
     * Gets the value of the ondragenter property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getOndragenter() {
        return ondragenter;
    }

    /**
     * Sets the value of the ondragenter property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setOndragenter(String value) {
        this.ondragenter = value;
    }

    /**
     * Gets the value of the ondragleave property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getOndragleave() {
        return ondragleave;
    }

    /**
     * Sets the value of the ondragleave property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setOndragleave(String value) {
        this.ondragleave = value;
    }

    /**
     * Gets the value of the ondragover property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getOndragover() {
        return ondragover;
    }

    /**
     * Sets the value of the ondragover property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setOndragover(String value) {
        this.ondragover = value;
    }

    /**
     * Gets the value of the ondragstart property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getOndragstart() {
        return ondragstart;
    }

    /**
     * Sets the value of the ondragstart property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setOndragstart(String value) {
        this.ondragstart = value;
    }

    /**
     * Gets the value of the ondrop property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getOndrop() {
        return ondrop;
    }

    /**
     * Sets the value of the ondrop property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setOndrop(String value) {
        this.ondrop = value;
    }

    /**
     * Gets the value of the ondurationchange property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getOndurationchange() {
        return ondurationchange;
    }

    /**
     * Sets the value of the ondurationchange property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setOndurationchange(String value) {
        this.ondurationchange = value;
    }

    /**
     * Gets the value of the onemptied property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getOnemptied() {
        return onemptied;
    }

    /**
     * Sets the value of the onemptied property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setOnemptied(String value) {
        this.onemptied = value;
    }

    /**
     * Gets the value of the onended property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getOnended() {
        return onended;
    }

    /**
     * Sets the value of the onended property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setOnended(String value) {
        this.onended = value;
    }

    /**
     * Gets the value of the onerror property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getOnerror() {
        return onerror;
    }

    /**
     * Sets the value of the onerror property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setOnerror(String value) {
        this.onerror = value;
    }

    /**
     * Gets the value of the onfocus property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getOnfocus() {
        return onfocus;
    }

    /**
     * Sets the value of the onfocus property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setOnfocus(String value) {
        this.onfocus = value;
    }

    /**
     * Gets the value of the onformchange property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getOnformchange() {
        return onformchange;
    }

    /**
     * Sets the value of the onformchange property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setOnformchange(String value) {
        this.onformchange = value;
    }

    /**
     * Gets the value of the onforminput property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getOnforminput() {
        return onforminput;
    }

    /**
     * Sets the value of the onforminput property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setOnforminput(String value) {
        this.onforminput = value;
    }

    /**
     * Gets the value of the oninput property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getOninput() {
        return oninput;
    }

    /**
     * Sets the value of the oninput property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setOninput(String value) {
        this.oninput = value;
    }

    /**
     * Gets the value of the oninvalid property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getOninvalid() {
        return oninvalid;
    }

    /**
     * Sets the value of the oninvalid property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setOninvalid(String value) {
        this.oninvalid = value;
    }

    /**
     * Gets the value of the onkeydown property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getOnkeydown() {
        return onkeydown;
    }

    /**
     * Sets the value of the onkeydown property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setOnkeydown(String value) {
        this.onkeydown = value;
    }

    /**
     * Gets the value of the onkeypress property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getOnkeypress() {
        return onkeypress;
    }

    /**
     * Sets the value of the onkeypress property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setOnkeypress(String value) {
        this.onkeypress = value;
    }

    /**
     * Gets the value of the onkeyup property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getOnkeyup() {
        return onkeyup;
    }

    /**
     * Sets the value of the onkeyup property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setOnkeyup(String value) {
        this.onkeyup = value;
    }

    /**
     * Gets the value of the onload property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getOnload() {
        return onload;
    }

    /**
     * Sets the value of the onload property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setOnload(String value) {
        this.onload = value;
    }

    /**
     * Gets the value of the onloadeddata property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getOnloadeddata() {
        return onloadeddata;
    }

    /**
     * Sets the value of the onloadeddata property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setOnloadeddata(String value) {
        this.onloadeddata = value;
    }

    /**
     * Gets the value of the onloadedmetadata property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getOnloadedmetadata() {
        return onloadedmetadata;
    }

    /**
     * Sets the value of the onloadedmetadata property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setOnloadedmetadata(String value) {
        this.onloadedmetadata = value;
    }

    /**
     * Gets the value of the onloadstart property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getOnloadstart() {
        return onloadstart;
    }

    /**
     * Sets the value of the onloadstart property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setOnloadstart(String value) {
        this.onloadstart = value;
    }

    /**
     * Gets the value of the onmousedown property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getOnmousedown() {
        return onmousedown;
    }

    /**
     * Sets the value of the onmousedown property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setOnmousedown(String value) {
        this.onmousedown = value;
    }

    /**
     * Gets the value of the onmousemove property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getOnmousemove() {
        return onmousemove;
    }

    /**
     * Sets the value of the onmousemove property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setOnmousemove(String value) {
        this.onmousemove = value;
    }

    /**
     * Gets the value of the onmouseout property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getOnmouseout() {
        return onmouseout;
    }

    /**
     * Sets the value of the onmouseout property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setOnmouseout(String value) {
        this.onmouseout = value;
    }

    /**
     * Gets the value of the onmouseover property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getOnmouseover() {
        return onmouseover;
    }

    /**
     * Sets the value of the onmouseover property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setOnmouseover(String value) {
        this.onmouseover = value;
    }

    /**
     * Gets the value of the onmouseup property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getOnmouseup() {
        return onmouseup;
    }

    /**
     * Sets the value of the onmouseup property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setOnmouseup(String value) {
        this.onmouseup = value;
    }

    /**
     * Gets the value of the onmousewheel property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getOnmousewheel() {
        return onmousewheel;
    }

    /**
     * Sets the value of the onmousewheel property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setOnmousewheel(String value) {
        this.onmousewheel = value;
    }

    /**
     * Gets the value of the onpause property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getOnpause() {
        return onpause;
    }

    /**
     * Sets the value of the onpause property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setOnpause(String value) {
        this.onpause = value;
    }

    /**
     * Gets the value of the onplay property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getOnplay() {
        return onplay;
    }

    /**
     * Sets the value of the onplay property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setOnplay(String value) {
        this.onplay = value;
    }

    /**
     * Gets the value of the onplaying property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getOnplaying() {
        return onplaying;
    }

    /**
     * Sets the value of the onplaying property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setOnplaying(String value) {
        this.onplaying = value;
    }

    /**
     * Gets the value of the onprogress property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getOnprogress() {
        return onprogress;
    }

    /**
     * Sets the value of the onprogress property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setOnprogress(String value) {
        this.onprogress = value;
    }

    /**
     * Gets the value of the onratechange property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getOnratechange() {
        return onratechange;
    }

    /**
     * Sets the value of the onratechange property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setOnratechange(String value) {
        this.onratechange = value;
    }

    /**
     * Gets the value of the onreadystatechange property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getOnreadystatechange() {
        return onreadystatechange;
    }

    /**
     * Sets the value of the onreadystatechange property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setOnreadystatechange(String value) {
        this.onreadystatechange = value;
    }

    /**
     * Gets the value of the onscroll property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getOnscroll() {
        return onscroll;
    }

    /**
     * Sets the value of the onscroll property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setOnscroll(String value) {
        this.onscroll = value;
    }

    /**
     * Gets the value of the onseeked property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getOnseeked() {
        return onseeked;
    }

    /**
     * Sets the value of the onseeked property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setOnseeked(String value) {
        this.onseeked = value;
    }

    /**
     * Gets the value of the onseeking property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getOnseeking() {
        return onseeking;
    }

    /**
     * Sets the value of the onseeking property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setOnseeking(String value) {
        this.onseeking = value;
    }

    /**
     * Gets the value of the onselect property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getOnselect() {
        return onselect;
    }

    /**
     * Sets the value of the onselect property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setOnselect(String value) {
        this.onselect = value;
    }

    /**
     * Gets the value of the onshow property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getOnshow() {
        return onshow;
    }

    /**
     * Sets the value of the onshow property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setOnshow(String value) {
        this.onshow = value;
    }

    /**
     * Gets the value of the onstalled property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getOnstalled() {
        return onstalled;
    }

    /**
     * Sets the value of the onstalled property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setOnstalled(String value) {
        this.onstalled = value;
    }

    /**
     * Gets the value of the onsubmit property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getOnsubmit() {
        return onsubmit;
    }

    /**
     * Sets the value of the onsubmit property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setOnsubmit(String value) {
        this.onsubmit = value;
    }

    /**
     * Gets the value of the onsuspend property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getOnsuspend() {
        return onsuspend;
    }

    /**
     * Sets the value of the onsuspend property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setOnsuspend(String value) {
        this.onsuspend = value;
    }

    /**
     * Gets the value of the ontimeupdate property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getOntimeupdate() {
        return ontimeupdate;
    }

    /**
     * Sets the value of the ontimeupdate property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setOntimeupdate(String value) {
        this.ontimeupdate = value;
    }

    /**
     * Gets the value of the onvolumenchange property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getOnvolumenchange() {
        return onvolumenchange;
    }

    /**
     * Sets the value of the onvolumenchange property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setOnvolumenchange(String value) {
        this.onvolumenchange = value;
    }

    /**
     * Gets the value of the onwaiting property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getOnwaiting() {
        return onwaiting;
    }

    /**
     * Sets the value of the onwaiting property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setOnwaiting(String value) {
        this.onwaiting = value;
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
