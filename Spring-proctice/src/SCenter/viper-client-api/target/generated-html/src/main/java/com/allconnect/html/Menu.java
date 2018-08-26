
package com.A.html;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
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
 *         &lt;element ref="{http://schemas.microsoft.com/intellisense/html-5}li"/>
 *         &lt;group ref="{http://schemas.microsoft.com/intellisense/html-5}flowContent"/>
 *       &lt;/choice>
 *       &lt;attGroup ref="{http://schemas.microsoft.com/intellisense/html-5}commonAttributeGroup"/>
 *       &lt;attribute name="type">
 *         &lt;simpleType>
 *           &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *             &lt;enumeration value="context"/>
 *             &lt;enumeration value="toolbar"/>
 *             &lt;enumeration value="list"/>
 *           &lt;/restriction>
 *         &lt;/simpleType>
 *       &lt;/attribute>
 *       &lt;attribute name="label" type="{http://www.w3.org/2001/XMLSchema}anySimpleType" />
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "li",
    "a",
    "area",
    "address",
    "article",
    "aside",
    "audio",
    "b",
    "blockquote",
    "bdo",
    "br",
    "button",
    "canvas",
    "cite",
    "code",
    "command",
    "datalist",
    "del",
    "details",
    "dfn",
    "dialog",
    "div",
    "dl",
    "em",
    "embed",
    "fieldset",
    "figure",
    "footer",
    "form",
    "h1",
    "h2",
    "h3",
    "h4",
    "h5",
    "h6",
    "header",
    "hgroup",
    "hr",
    "i",
    "iframe",
    "img",
    "input",
    "ins",
    "kbd",
    "keygen",
    "labelAttribute",
    "link",
    "menu",
    "meta",
    "meter",
    "nav",
    "noscript",
    "ol",
    "object",
    "output",
    "p",
    "pre",
    "progress",
    "q",
    "ruby",
    "samp",
    "script",
    "section",
    "select",
    "small",
    "span",
    "strong",
    "style2",
    "sub",
    "sup",
    "svg",
    "table",
    "textarea",
    "time",
    "ul",
    "var",
    "video"
})
@XmlRootElement(name = "menu")
public class Menu {

    protected Li li;
    protected A a;
    protected Area area;
    protected SimpleFlowContentElement address;
    protected Article article;
    protected SimpleFlowContentElement aside;
    protected Audio audio;
    protected B b;
    protected Blockquote blockquote;
    protected Bdo bdo;
    protected Br br;
    protected Button button;
    protected Canvas canvas;
    protected Cite cite;
    protected Code code;
    protected Command command;
    protected Datalist datalist;
    protected Del del;
    protected Details details;
    protected Dfn dfn;
    protected Dialog dialog;
    protected Div div;
    protected Dl dl;
    protected Em em;
    protected Embed embed;
    protected Fieldset fieldset;
    protected Figure figure;
    protected SimpleFlowContentElement footer;
    protected Form form;
    protected H1 h1;
    protected H2 h2;
    protected H3 h3;
    protected H4 h4;
    protected H5 h5;
    protected H6 h6;
    protected SimpleFlowContentElement header;
    protected Hgroup hgroup;
    protected Hr hr;
    protected I i;
    protected Iframe iframe;
    protected Img img;
    protected Input input;
    protected Ins ins;
    protected Kbd kbd;
    protected Keygen keygen;
    @XmlElement(name = "label")
    protected Label labelAttribute;
    protected Link link;
    protected Menu menu;
    protected Meta meta;
    protected Meter meter;
    protected SimpleFlowContentElement nav;
    protected Noscript noscript;
    protected Ol ol;
    protected Object object;
    protected Output output;
    protected P p;
    protected Pre pre;
    protected Progress progress;
    protected Q q;
    protected Ruby ruby;
    protected Samp samp;
    protected Script script;
    protected SimpleFlowContentElement section;
    protected Select select;
    protected Small small;
    protected Span span;
    protected Strong strong;
    protected Style2 style2;
    protected Sub sub;
    protected Sup sup;
    protected Svg svg;
    protected Table table;
    protected Textarea textarea;
    protected Time time;
    protected Ul ul;
    protected Var var;
    protected Video video;
    @XmlAttribute(name = "type")
    protected String type;
    @XmlAttribute(name = "label")
    @XmlSchemaType(name = "anySimpleType")
    protected String label;
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
     * Gets the value of the li property.
     * 
     * @return
     *     possible object is
     *     {@link Li }
     *     
     */
    public Li getLi() {
        return li;
    }

    /**
     * Sets the value of the li property.
     * 
     * @param value
     *     allowed object is
     *     {@link Li }
     *     
     */
    public void setLi(Li value) {
        this.li = value;
    }

    /**
     * Gets the value of the a property.
     * 
     * @return
     *     possible object is
     *     {@link A }
     *     
     */
    public A getA() {
        return a;
    }

    /**
     * Sets the value of the a property.
     * 
     * @param value
     *     allowed object is
     *     {@link A }
     *     
     */
    public void setA(A value) {
        this.a = value;
    }

    /**
     * Gets the value of the area property.
     * 
     * @return
     *     possible object is
     *     {@link Area }
     *     
     */
    public Area getArea() {
        return area;
    }

    /**
     * Sets the value of the area property.
     * 
     * @param value
     *     allowed object is
     *     {@link Area }
     *     
     */
    public void setArea(Area value) {
        this.area = value;
    }

    /**
     * Gets the value of the address property.
     * 
     * @return
     *     possible object is
     *     {@link SimpleFlowContentElement }
     *     
     */
    public SimpleFlowContentElement getAddress() {
        return address;
    }

    /**
     * Sets the value of the address property.
     * 
     * @param value
     *     allowed object is
     *     {@link SimpleFlowContentElement }
     *     
     */
    public void setAddress(SimpleFlowContentElement value) {
        this.address = value;
    }

    /**
     * Gets the value of the article property.
     * 
     * @return
     *     possible object is
     *     {@link Article }
     *     
     */
    public Article getArticle() {
        return article;
    }

    /**
     * Sets the value of the article property.
     * 
     * @param value
     *     allowed object is
     *     {@link Article }
     *     
     */
    public void setArticle(Article value) {
        this.article = value;
    }

    /**
     * Gets the value of the aside property.
     * 
     * @return
     *     possible object is
     *     {@link SimpleFlowContentElement }
     *     
     */
    public SimpleFlowContentElement getAside() {
        return aside;
    }

    /**
     * Sets the value of the aside property.
     * 
     * @param value
     *     allowed object is
     *     {@link SimpleFlowContentElement }
     *     
     */
    public void setAside(SimpleFlowContentElement value) {
        this.aside = value;
    }

    /**
     * Gets the value of the audio property.
     * 
     * @return
     *     possible object is
     *     {@link Audio }
     *     
     */
    public Audio getAudio() {
        return audio;
    }

    /**
     * Sets the value of the audio property.
     * 
     * @param value
     *     allowed object is
     *     {@link Audio }
     *     
     */
    public void setAudio(Audio value) {
        this.audio = value;
    }

    /**
     * Gets the value of the b property.
     * 
     * @return
     *     possible object is
     *     {@link B }
     *     
     */
    public B getB() {
        return b;
    }

    /**
     * Sets the value of the b property.
     * 
     * @param value
     *     allowed object is
     *     {@link B }
     *     
     */
    public void setB(B value) {
        this.b = value;
    }

    /**
     * Gets the value of the blockquote property.
     * 
     * @return
     *     possible object is
     *     {@link Blockquote }
     *     
     */
    public Blockquote getBlockquote() {
        return blockquote;
    }

    /**
     * Sets the value of the blockquote property.
     * 
     * @param value
     *     allowed object is
     *     {@link Blockquote }
     *     
     */
    public void setBlockquote(Blockquote value) {
        this.blockquote = value;
    }

    /**
     * Gets the value of the bdo property.
     * 
     * @return
     *     possible object is
     *     {@link Bdo }
     *     
     */
    public Bdo getBdo() {
        return bdo;
    }

    /**
     * Sets the value of the bdo property.
     * 
     * @param value
     *     allowed object is
     *     {@link Bdo }
     *     
     */
    public void setBdo(Bdo value) {
        this.bdo = value;
    }

    /**
     * Gets the value of the br property.
     * 
     * @return
     *     possible object is
     *     {@link Br }
     *     
     */
    public Br getBr() {
        return br;
    }

    /**
     * Sets the value of the br property.
     * 
     * @param value
     *     allowed object is
     *     {@link Br }
     *     
     */
    public void setBr(Br value) {
        this.br = value;
    }

    /**
     * Gets the value of the button property.
     * 
     * @return
     *     possible object is
     *     {@link Button }
     *     
     */
    public Button getButton() {
        return button;
    }

    /**
     * Sets the value of the button property.
     * 
     * @param value
     *     allowed object is
     *     {@link Button }
     *     
     */
    public void setButton(Button value) {
        this.button = value;
    }

    /**
     * Gets the value of the canvas property.
     * 
     * @return
     *     possible object is
     *     {@link Canvas }
     *     
     */
    public Canvas getCanvas() {
        return canvas;
    }

    /**
     * Sets the value of the canvas property.
     * 
     * @param value
     *     allowed object is
     *     {@link Canvas }
     *     
     */
    public void setCanvas(Canvas value) {
        this.canvas = value;
    }

    /**
     * Gets the value of the cite property.
     * 
     * @return
     *     possible object is
     *     {@link Cite }
     *     
     */
    public Cite getCite() {
        return cite;
    }

    /**
     * Sets the value of the cite property.
     * 
     * @param value
     *     allowed object is
     *     {@link Cite }
     *     
     */
    public void setCite(Cite value) {
        this.cite = value;
    }

    /**
     * Gets the value of the code property.
     * 
     * @return
     *     possible object is
     *     {@link Code }
     *     
     */
    public Code getCode() {
        return code;
    }

    /**
     * Sets the value of the code property.
     * 
     * @param value
     *     allowed object is
     *     {@link Code }
     *     
     */
    public void setCode(Code value) {
        this.code = value;
    }

    /**
     * Gets the value of the command property.
     * 
     * @return
     *     possible object is
     *     {@link Command }
     *     
     */
    public Command getCommand() {
        return command;
    }

    /**
     * Sets the value of the command property.
     * 
     * @param value
     *     allowed object is
     *     {@link Command }
     *     
     */
    public void setCommand(Command value) {
        this.command = value;
    }

    /**
     * Gets the value of the datalist property.
     * 
     * @return
     *     possible object is
     *     {@link Datalist }
     *     
     */
    public Datalist getDatalist() {
        return datalist;
    }

    /**
     * Sets the value of the datalist property.
     * 
     * @param value
     *     allowed object is
     *     {@link Datalist }
     *     
     */
    public void setDatalist(Datalist value) {
        this.datalist = value;
    }

    /**
     * Gets the value of the del property.
     * 
     * @return
     *     possible object is
     *     {@link Del }
     *     
     */
    public Del getDel() {
        return del;
    }

    /**
     * Sets the value of the del property.
     * 
     * @param value
     *     allowed object is
     *     {@link Del }
     *     
     */
    public void setDel(Del value) {
        this.del = value;
    }

    /**
     * Gets the value of the details property.
     * 
     * @return
     *     possible object is
     *     {@link Details }
     *     
     */
    public Details getDetails() {
        return details;
    }

    /**
     * Sets the value of the details property.
     * 
     * @param value
     *     allowed object is
     *     {@link Details }
     *     
     */
    public void setDetails(Details value) {
        this.details = value;
    }

    /**
     * Gets the value of the dfn property.
     * 
     * @return
     *     possible object is
     *     {@link Dfn }
     *     
     */
    public Dfn getDfn() {
        return dfn;
    }

    /**
     * Sets the value of the dfn property.
     * 
     * @param value
     *     allowed object is
     *     {@link Dfn }
     *     
     */
    public void setDfn(Dfn value) {
        this.dfn = value;
    }

    /**
     * Gets the value of the dialog property.
     * 
     * @return
     *     possible object is
     *     {@link Dialog }
     *     
     */
    public Dialog getDialog() {
        return dialog;
    }

    /**
     * Sets the value of the dialog property.
     * 
     * @param value
     *     allowed object is
     *     {@link Dialog }
     *     
     */
    public void setDialog(Dialog value) {
        this.dialog = value;
    }

    /**
     * Gets the value of the div property.
     * 
     * @return
     *     possible object is
     *     {@link Div }
     *     
     */
    public Div getDiv() {
        return div;
    }

    /**
     * Sets the value of the div property.
     * 
     * @param value
     *     allowed object is
     *     {@link Div }
     *     
     */
    public void setDiv(Div value) {
        this.div = value;
    }

    /**
     * Gets the value of the dl property.
     * 
     * @return
     *     possible object is
     *     {@link Dl }
     *     
     */
    public Dl getDl() {
        return dl;
    }

    /**
     * Sets the value of the dl property.
     * 
     * @param value
     *     allowed object is
     *     {@link Dl }
     *     
     */
    public void setDl(Dl value) {
        this.dl = value;
    }

    /**
     * Gets the value of the em property.
     * 
     * @return
     *     possible object is
     *     {@link Em }
     *     
     */
    public Em getEm() {
        return em;
    }

    /**
     * Sets the value of the em property.
     * 
     * @param value
     *     allowed object is
     *     {@link Em }
     *     
     */
    public void setEm(Em value) {
        this.em = value;
    }

    /**
     * Gets the value of the embed property.
     * 
     * @return
     *     possible object is
     *     {@link Embed }
     *     
     */
    public Embed getEmbed() {
        return embed;
    }

    /**
     * Sets the value of the embed property.
     * 
     * @param value
     *     allowed object is
     *     {@link Embed }
     *     
     */
    public void setEmbed(Embed value) {
        this.embed = value;
    }

    /**
     * Gets the value of the fieldset property.
     * 
     * @return
     *     possible object is
     *     {@link Fieldset }
     *     
     */
    public Fieldset getFieldset() {
        return fieldset;
    }

    /**
     * Sets the value of the fieldset property.
     * 
     * @param value
     *     allowed object is
     *     {@link Fieldset }
     *     
     */
    public void setFieldset(Fieldset value) {
        this.fieldset = value;
    }

    /**
     * Gets the value of the figure property.
     * 
     * @return
     *     possible object is
     *     {@link Figure }
     *     
     */
    public Figure getFigure() {
        return figure;
    }

    /**
     * Sets the value of the figure property.
     * 
     * @param value
     *     allowed object is
     *     {@link Figure }
     *     
     */
    public void setFigure(Figure value) {
        this.figure = value;
    }

    /**
     * Gets the value of the footer property.
     * 
     * @return
     *     possible object is
     *     {@link SimpleFlowContentElement }
     *     
     */
    public SimpleFlowContentElement getFooter() {
        return footer;
    }

    /**
     * Sets the value of the footer property.
     * 
     * @param value
     *     allowed object is
     *     {@link SimpleFlowContentElement }
     *     
     */
    public void setFooter(SimpleFlowContentElement value) {
        this.footer = value;
    }

    /**
     * Gets the value of the form property.
     * 
     * @return
     *     possible object is
     *     {@link Form }
     *     
     */
    public Form getForm() {
        return form;
    }

    /**
     * Sets the value of the form property.
     * 
     * @param value
     *     allowed object is
     *     {@link Form }
     *     
     */
    public void setForm(Form value) {
        this.form = value;
    }

    /**
     * Gets the value of the h1 property.
     * 
     * @return
     *     possible object is
     *     {@link H1 }
     *     
     */
    public H1 getH1() {
        return h1;
    }

    /**
     * Sets the value of the h1 property.
     * 
     * @param value
     *     allowed object is
     *     {@link H1 }
     *     
     */
    public void setH1(H1 value) {
        this.h1 = value;
    }

    /**
     * Gets the value of the h2 property.
     * 
     * @return
     *     possible object is
     *     {@link H2 }
     *     
     */
    public H2 getH2() {
        return h2;
    }

    /**
     * Sets the value of the h2 property.
     * 
     * @param value
     *     allowed object is
     *     {@link H2 }
     *     
     */
    public void setH2(H2 value) {
        this.h2 = value;
    }

    /**
     * Gets the value of the h3 property.
     * 
     * @return
     *     possible object is
     *     {@link H3 }
     *     
     */
    public H3 getH3() {
        return h3;
    }

    /**
     * Sets the value of the h3 property.
     * 
     * @param value
     *     allowed object is
     *     {@link H3 }
     *     
     */
    public void setH3(H3 value) {
        this.h3 = value;
    }

    /**
     * Gets the value of the h4 property.
     * 
     * @return
     *     possible object is
     *     {@link H4 }
     *     
     */
    public H4 getH4() {
        return h4;
    }

    /**
     * Sets the value of the h4 property.
     * 
     * @param value
     *     allowed object is
     *     {@link H4 }
     *     
     */
    public void setH4(H4 value) {
        this.h4 = value;
    }

    /**
     * Gets the value of the h5 property.
     * 
     * @return
     *     possible object is
     *     {@link H5 }
     *     
     */
    public H5 getH5() {
        return h5;
    }

    /**
     * Sets the value of the h5 property.
     * 
     * @param value
     *     allowed object is
     *     {@link H5 }
     *     
     */
    public void setH5(H5 value) {
        this.h5 = value;
    }

    /**
     * Gets the value of the h6 property.
     * 
     * @return
     *     possible object is
     *     {@link H6 }
     *     
     */
    public H6 getH6() {
        return h6;
    }

    /**
     * Sets the value of the h6 property.
     * 
     * @param value
     *     allowed object is
     *     {@link H6 }
     *     
     */
    public void setH6(H6 value) {
        this.h6 = value;
    }

    /**
     * Gets the value of the header property.
     * 
     * @return
     *     possible object is
     *     {@link SimpleFlowContentElement }
     *     
     */
    public SimpleFlowContentElement getHeader() {
        return header;
    }

    /**
     * Sets the value of the header property.
     * 
     * @param value
     *     allowed object is
     *     {@link SimpleFlowContentElement }
     *     
     */
    public void setHeader(SimpleFlowContentElement value) {
        this.header = value;
    }

    /**
     * Gets the value of the hgroup property.
     * 
     * @return
     *     possible object is
     *     {@link Hgroup }
     *     
     */
    public Hgroup getHgroup() {
        return hgroup;
    }

    /**
     * Sets the value of the hgroup property.
     * 
     * @param value
     *     allowed object is
     *     {@link Hgroup }
     *     
     */
    public void setHgroup(Hgroup value) {
        this.hgroup = value;
    }

    /**
     * Gets the value of the hr property.
     * 
     * @return
     *     possible object is
     *     {@link Hr }
     *     
     */
    public Hr getHr() {
        return hr;
    }

    /**
     * Sets the value of the hr property.
     * 
     * @param value
     *     allowed object is
     *     {@link Hr }
     *     
     */
    public void setHr(Hr value) {
        this.hr = value;
    }

    /**
     * Gets the value of the i property.
     * 
     * @return
     *     possible object is
     *     {@link I }
     *     
     */
    public I getI() {
        return i;
    }

    /**
     * Sets the value of the i property.
     * 
     * @param value
     *     allowed object is
     *     {@link I }
     *     
     */
    public void setI(I value) {
        this.i = value;
    }

    /**
     * Gets the value of the iframe property.
     * 
     * @return
     *     possible object is
     *     {@link Iframe }
     *     
     */
    public Iframe getIframe() {
        return iframe;
    }

    /**
     * Sets the value of the iframe property.
     * 
     * @param value
     *     allowed object is
     *     {@link Iframe }
     *     
     */
    public void setIframe(Iframe value) {
        this.iframe = value;
    }

    /**
     * Gets the value of the img property.
     * 
     * @return
     *     possible object is
     *     {@link Img }
     *     
     */
    public Img getImg() {
        return img;
    }

    /**
     * Sets the value of the img property.
     * 
     * @param value
     *     allowed object is
     *     {@link Img }
     *     
     */
    public void setImg(Img value) {
        this.img = value;
    }

    /**
     * Gets the value of the input property.
     * 
     * @return
     *     possible object is
     *     {@link Input }
     *     
     */
    public Input getInput() {
        return input;
    }

    /**
     * Sets the value of the input property.
     * 
     * @param value
     *     allowed object is
     *     {@link Input }
     *     
     */
    public void setInput(Input value) {
        this.input = value;
    }

    /**
     * Gets the value of the ins property.
     * 
     * @return
     *     possible object is
     *     {@link Ins }
     *     
     */
    public Ins getIns() {
        return ins;
    }

    /**
     * Sets the value of the ins property.
     * 
     * @param value
     *     allowed object is
     *     {@link Ins }
     *     
     */
    public void setIns(Ins value) {
        this.ins = value;
    }

    /**
     * Gets the value of the kbd property.
     * 
     * @return
     *     possible object is
     *     {@link Kbd }
     *     
     */
    public Kbd getKbd() {
        return kbd;
    }

    /**
     * Sets the value of the kbd property.
     * 
     * @param value
     *     allowed object is
     *     {@link Kbd }
     *     
     */
    public void setKbd(Kbd value) {
        this.kbd = value;
    }

    /**
     * Gets the value of the keygen property.
     * 
     * @return
     *     possible object is
     *     {@link Keygen }
     *     
     */
    public Keygen getKeygen() {
        return keygen;
    }

    /**
     * Sets the value of the keygen property.
     * 
     * @param value
     *     allowed object is
     *     {@link Keygen }
     *     
     */
    public void setKeygen(Keygen value) {
        this.keygen = value;
    }

    /**
     * Gets the value of the labelAttribute property.
     * 
     * @return
     *     possible object is
     *     {@link Label }
     *     
     */
    public Label getLabelAttribute() {
        return labelAttribute;
    }

    /**
     * Sets the value of the labelAttribute property.
     * 
     * @param value
     *     allowed object is
     *     {@link Label }
     *     
     */
    public void setLabelAttribute(Label value) {
        this.labelAttribute = value;
    }

    /**
     * Gets the value of the link property.
     * 
     * @return
     *     possible object is
     *     {@link Link }
     *     
     */
    public Link getLink() {
        return link;
    }

    /**
     * Sets the value of the link property.
     * 
     * @param value
     *     allowed object is
     *     {@link Link }
     *     
     */
    public void setLink(Link value) {
        this.link = value;
    }

    /**
     * Gets the value of the menu property.
     * 
     * @return
     *     possible object is
     *     {@link Menu }
     *     
     */
    public Menu getMenu() {
        return menu;
    }

    /**
     * Sets the value of the menu property.
     * 
     * @param value
     *     allowed object is
     *     {@link Menu }
     *     
     */
    public void setMenu(Menu value) {
        this.menu = value;
    }

    /**
     * Gets the value of the meta property.
     * 
     * @return
     *     possible object is
     *     {@link Meta }
     *     
     */
    public Meta getMeta() {
        return meta;
    }

    /**
     * Sets the value of the meta property.
     * 
     * @param value
     *     allowed object is
     *     {@link Meta }
     *     
     */
    public void setMeta(Meta value) {
        this.meta = value;
    }

    /**
     * Gets the value of the meter property.
     * 
     * @return
     *     possible object is
     *     {@link Meter }
     *     
     */
    public Meter getMeter() {
        return meter;
    }

    /**
     * Sets the value of the meter property.
     * 
     * @param value
     *     allowed object is
     *     {@link Meter }
     *     
     */
    public void setMeter(Meter value) {
        this.meter = value;
    }

    /**
     * Gets the value of the nav property.
     * 
     * @return
     *     possible object is
     *     {@link SimpleFlowContentElement }
     *     
     */
    public SimpleFlowContentElement getNav() {
        return nav;
    }

    /**
     * Sets the value of the nav property.
     * 
     * @param value
     *     allowed object is
     *     {@link SimpleFlowContentElement }
     *     
     */
    public void setNav(SimpleFlowContentElement value) {
        this.nav = value;
    }

    /**
     * Gets the value of the noscript property.
     * 
     * @return
     *     possible object is
     *     {@link Noscript }
     *     
     */
    public Noscript getNoscript() {
        return noscript;
    }

    /**
     * Sets the value of the noscript property.
     * 
     * @param value
     *     allowed object is
     *     {@link Noscript }
     *     
     */
    public void setNoscript(Noscript value) {
        this.noscript = value;
    }

    /**
     * Gets the value of the ol property.
     * 
     * @return
     *     possible object is
     *     {@link Ol }
     *     
     */
    public Ol getOl() {
        return ol;
    }

    /**
     * Sets the value of the ol property.
     * 
     * @param value
     *     allowed object is
     *     {@link Ol }
     *     
     */
    public void setOl(Ol value) {
        this.ol = value;
    }

    /**
     * Gets the value of the object property.
     * 
     * @return
     *     possible object is
     *     {@link Object }
     *     
     */
    public Object getObject() {
        return object;
    }

    /**
     * Sets the value of the object property.
     * 
     * @param value
     *     allowed object is
     *     {@link Object }
     *     
     */
    public void setObject(Object value) {
        this.object = value;
    }

    /**
     * Gets the value of the output property.
     * 
     * @return
     *     possible object is
     *     {@link Output }
     *     
     */
    public Output getOutput() {
        return output;
    }

    /**
     * Sets the value of the output property.
     * 
     * @param value
     *     allowed object is
     *     {@link Output }
     *     
     */
    public void setOutput(Output value) {
        this.output = value;
    }

    /**
     * Gets the value of the p property.
     * 
     * @return
     *     possible object is
     *     {@link P }
     *     
     */
    public P getP() {
        return p;
    }

    /**
     * Sets the value of the p property.
     * 
     * @param value
     *     allowed object is
     *     {@link P }
     *     
     */
    public void setP(P value) {
        this.p = value;
    }

    /**
     * Gets the value of the pre property.
     * 
     * @return
     *     possible object is
     *     {@link Pre }
     *     
     */
    public Pre getPre() {
        return pre;
    }

    /**
     * Sets the value of the pre property.
     * 
     * @param value
     *     allowed object is
     *     {@link Pre }
     *     
     */
    public void setPre(Pre value) {
        this.pre = value;
    }

    /**
     * Gets the value of the progress property.
     * 
     * @return
     *     possible object is
     *     {@link Progress }
     *     
     */
    public Progress getProgress() {
        return progress;
    }

    /**
     * Sets the value of the progress property.
     * 
     * @param value
     *     allowed object is
     *     {@link Progress }
     *     
     */
    public void setProgress(Progress value) {
        this.progress = value;
    }

    /**
     * Gets the value of the q property.
     * 
     * @return
     *     possible object is
     *     {@link Q }
     *     
     */
    public Q getQ() {
        return q;
    }

    /**
     * Sets the value of the q property.
     * 
     * @param value
     *     allowed object is
     *     {@link Q }
     *     
     */
    public void setQ(Q value) {
        this.q = value;
    }

    /**
     * Gets the value of the ruby property.
     * 
     * @return
     *     possible object is
     *     {@link Ruby }
     *     
     */
    public Ruby getRuby() {
        return ruby;
    }

    /**
     * Sets the value of the ruby property.
     * 
     * @param value
     *     allowed object is
     *     {@link Ruby }
     *     
     */
    public void setRuby(Ruby value) {
        this.ruby = value;
    }

    /**
     * Gets the value of the samp property.
     * 
     * @return
     *     possible object is
     *     {@link Samp }
     *     
     */
    public Samp getSamp() {
        return samp;
    }

    /**
     * Sets the value of the samp property.
     * 
     * @param value
     *     allowed object is
     *     {@link Samp }
     *     
     */
    public void setSamp(Samp value) {
        this.samp = value;
    }

    /**
     * Gets the value of the script property.
     * 
     * @return
     *     possible object is
     *     {@link Script }
     *     
     */
    public Script getScript() {
        return script;
    }

    /**
     * Sets the value of the script property.
     * 
     * @param value
     *     allowed object is
     *     {@link Script }
     *     
     */
    public void setScript(Script value) {
        this.script = value;
    }

    /**
     * Gets the value of the section property.
     * 
     * @return
     *     possible object is
     *     {@link SimpleFlowContentElement }
     *     
     */
    public SimpleFlowContentElement getSection() {
        return section;
    }

    /**
     * Sets the value of the section property.
     * 
     * @param value
     *     allowed object is
     *     {@link SimpleFlowContentElement }
     *     
     */
    public void setSection(SimpleFlowContentElement value) {
        this.section = value;
    }

    /**
     * Gets the value of the select property.
     * 
     * @return
     *     possible object is
     *     {@link Select }
     *     
     */
    public Select getSelect() {
        return select;
    }

    /**
     * Sets the value of the select property.
     * 
     * @param value
     *     allowed object is
     *     {@link Select }
     *     
     */
    public void setSelect(Select value) {
        this.select = value;
    }

    /**
     * Gets the value of the small property.
     * 
     * @return
     *     possible object is
     *     {@link Small }
     *     
     */
    public Small getSmall() {
        return small;
    }

    /**
     * Sets the value of the small property.
     * 
     * @param value
     *     allowed object is
     *     {@link Small }
     *     
     */
    public void setSmall(Small value) {
        this.small = value;
    }

    /**
     * Gets the value of the span property.
     * 
     * @return
     *     possible object is
     *     {@link Span }
     *     
     */
    public Span getSpan() {
        return span;
    }

    /**
     * Sets the value of the span property.
     * 
     * @param value
     *     allowed object is
     *     {@link Span }
     *     
     */
    public void setSpan(Span value) {
        this.span = value;
    }

    /**
     * Gets the value of the strong property.
     * 
     * @return
     *     possible object is
     *     {@link Strong }
     *     
     */
    public Strong getStrong() {
        return strong;
    }

    /**
     * Sets the value of the strong property.
     * 
     * @param value
     *     allowed object is
     *     {@link Strong }
     *     
     */
    public void setStrong(Strong value) {
        this.strong = value;
    }

    /**
     * Gets the value of the style2 property.
     * 
     * @return
     *     possible object is
     *     {@link Style2 }
     *     
     */
    public Style2 getStyle2() {
        return style2;
    }

    /**
     * Sets the value of the style2 property.
     * 
     * @param value
     *     allowed object is
     *     {@link Style2 }
     *     
     */
    public void setStyle2(Style2 value) {
        this.style2 = value;
    }

    /**
     * Gets the value of the sub property.
     * 
     * @return
     *     possible object is
     *     {@link Sub }
     *     
     */
    public Sub getSub() {
        return sub;
    }

    /**
     * Sets the value of the sub property.
     * 
     * @param value
     *     allowed object is
     *     {@link Sub }
     *     
     */
    public void setSub(Sub value) {
        this.sub = value;
    }

    /**
     * Gets the value of the sup property.
     * 
     * @return
     *     possible object is
     *     {@link Sup }
     *     
     */
    public Sup getSup() {
        return sup;
    }

    /**
     * Sets the value of the sup property.
     * 
     * @param value
     *     allowed object is
     *     {@link Sup }
     *     
     */
    public void setSup(Sup value) {
        this.sup = value;
    }

    /**
     * Gets the value of the svg property.
     * 
     * @return
     *     possible object is
     *     {@link Svg }
     *     
     */
    public Svg getSvg() {
        return svg;
    }

    /**
     * Sets the value of the svg property.
     * 
     * @param value
     *     allowed object is
     *     {@link Svg }
     *     
     */
    public void setSvg(Svg value) {
        this.svg = value;
    }

    /**
     * Gets the value of the table property.
     * 
     * @return
     *     possible object is
     *     {@link Table }
     *     
     */
    public Table getTable() {
        return table;
    }

    /**
     * Sets the value of the table property.
     * 
     * @param value
     *     allowed object is
     *     {@link Table }
     *     
     */
    public void setTable(Table value) {
        this.table = value;
    }

    /**
     * Gets the value of the textarea property.
     * 
     * @return
     *     possible object is
     *     {@link Textarea }
     *     
     */
    public Textarea getTextarea() {
        return textarea;
    }

    /**
     * Sets the value of the textarea property.
     * 
     * @param value
     *     allowed object is
     *     {@link Textarea }
     *     
     */
    public void setTextarea(Textarea value) {
        this.textarea = value;
    }

    /**
     * Gets the value of the time property.
     * 
     * @return
     *     possible object is
     *     {@link Time }
     *     
     */
    public Time getTime() {
        return time;
    }

    /**
     * Sets the value of the time property.
     * 
     * @param value
     *     allowed object is
     *     {@link Time }
     *     
     */
    public void setTime(Time value) {
        this.time = value;
    }

    /**
     * Gets the value of the ul property.
     * 
     * @return
     *     possible object is
     *     {@link Ul }
     *     
     */
    public Ul getUl() {
        return ul;
    }

    /**
     * Sets the value of the ul property.
     * 
     * @param value
     *     allowed object is
     *     {@link Ul }
     *     
     */
    public void setUl(Ul value) {
        this.ul = value;
    }

    /**
     * Gets the value of the var property.
     * 
     * @return
     *     possible object is
     *     {@link Var }
     *     
     */
    public Var getVar() {
        return var;
    }

    /**
     * Sets the value of the var property.
     * 
     * @param value
     *     allowed object is
     *     {@link Var }
     *     
     */
    public void setVar(Var value) {
        this.var = value;
    }

    /**
     * Gets the value of the video property.
     * 
     * @return
     *     possible object is
     *     {@link Video }
     *     
     */
    public Video getVideo() {
        return video;
    }

    /**
     * Sets the value of the video property.
     * 
     * @param value
     *     allowed object is
     *     {@link Video }
     *     
     */
    public void setVideo(Video value) {
        this.video = value;
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
     * Gets the value of the label property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getLabel() {
        return label;
    }

    /**
     * Sets the value of the label property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setLabel(String value) {
        this.label = value;
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
