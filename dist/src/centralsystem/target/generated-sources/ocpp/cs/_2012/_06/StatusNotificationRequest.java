
package ocpp.cs._2012._06;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import de.rwth.idsg.steve.ocpp.RequestType;
import de.rwth.idsg.steve.utils.JodaDateTimeConverter;
import org.joda.time.DateTime;


/**
 * Defines the StatusNotification.req PDU
 * 
 * <p>Java-Klasse für StatusNotificationRequest complex type.
 * 
 * <p>Das folgende Schemafragment gibt den erwarteten Content an, der in dieser Klasse enthalten ist.
 * 
 * <pre>
 * &lt;complexType name="StatusNotificationRequest"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="connectorId" type="{http://www.w3.org/2001/XMLSchema}int"/&gt;
 *         &lt;element name="status" type="{urn://Ocpp/Cs/2012/06/}ChargePointStatus"/&gt;
 *         &lt;element name="errorCode" type="{urn://Ocpp/Cs/2012/06/}ChargePointErrorCode"/&gt;
 *         &lt;element name="info" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="timestamp" type="{http://www.w3.org/2001/XMLSchema}dateTime" minOccurs="0"/&gt;
 *         &lt;element name="vendorId" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="vendorErrorCode" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "StatusNotificationRequest", propOrder = {
    "connectorId",
    "status",
    "errorCode",
    "info",
    "timestamp",
    "vendorId",
    "vendorErrorCode"
})
public class StatusNotificationRequest
    implements RequestType
{

    protected int connectorId;
    @XmlElement(required = true)
    @XmlSchemaType(name = "string")
    protected ChargePointStatus status;
    @XmlElement(required = true)
    @XmlSchemaType(name = "string")
    protected ChargePointErrorCode errorCode;
    protected String info;
    @XmlElement(type = String.class)
    @XmlJavaTypeAdapter(JodaDateTimeConverter.class)
    @XmlSchemaType(name = "dateTime")
    protected DateTime timestamp;
    protected String vendorId;
    protected String vendorErrorCode;

    /**
     * Ruft den Wert der connectorId-Eigenschaft ab.
     * 
     */
    public int getConnectorId() {
        return connectorId;
    }

    /**
     * Legt den Wert der connectorId-Eigenschaft fest.
     * 
     */
    public void setConnectorId(int value) {
        this.connectorId = value;
    }

    public boolean isSetConnectorId() {
        return true;
    }

    /**
     * Ruft den Wert der status-Eigenschaft ab.
     * 
     * @return
     *     possible object is
     *     {@link ChargePointStatus }
     *     
     */
    public ChargePointStatus getStatus() {
        return status;
    }

    /**
     * Legt den Wert der status-Eigenschaft fest.
     * 
     * @param value
     *     allowed object is
     *     {@link ChargePointStatus }
     *     
     */
    public void setStatus(ChargePointStatus value) {
        this.status = value;
    }

    public boolean isSetStatus() {
        return (this.status!= null);
    }

    /**
     * Ruft den Wert der errorCode-Eigenschaft ab.
     * 
     * @return
     *     possible object is
     *     {@link ChargePointErrorCode }
     *     
     */
    public ChargePointErrorCode getErrorCode() {
        return errorCode;
    }

    /**
     * Legt den Wert der errorCode-Eigenschaft fest.
     * 
     * @param value
     *     allowed object is
     *     {@link ChargePointErrorCode }
     *     
     */
    public void setErrorCode(ChargePointErrorCode value) {
        this.errorCode = value;
    }

    public boolean isSetErrorCode() {
        return (this.errorCode!= null);
    }

    /**
     * Ruft den Wert der info-Eigenschaft ab.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getInfo() {
        return info;
    }

    /**
     * Legt den Wert der info-Eigenschaft fest.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setInfo(String value) {
        this.info = value;
    }

    public boolean isSetInfo() {
        return (this.info!= null);
    }

    /**
     * Ruft den Wert der timestamp-Eigenschaft ab.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public DateTime getTimestamp() {
        return timestamp;
    }

    /**
     * Legt den Wert der timestamp-Eigenschaft fest.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTimestamp(DateTime value) {
        this.timestamp = value;
    }

    public boolean isSetTimestamp() {
        return (this.timestamp!= null);
    }

    /**
     * Ruft den Wert der vendorId-Eigenschaft ab.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getVendorId() {
        return vendorId;
    }

    /**
     * Legt den Wert der vendorId-Eigenschaft fest.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setVendorId(String value) {
        this.vendorId = value;
    }

    public boolean isSetVendorId() {
        return (this.vendorId!= null);
    }

    /**
     * Ruft den Wert der vendorErrorCode-Eigenschaft ab.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getVendorErrorCode() {
        return vendorErrorCode;
    }

    /**
     * Legt den Wert der vendorErrorCode-Eigenschaft fest.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setVendorErrorCode(String value) {
        this.vendorErrorCode = value;
    }

    public boolean isSetVendorErrorCode() {
        return (this.vendorErrorCode!= null);
    }

    public StatusNotificationRequest withConnectorId(int value) {
        setConnectorId(value);
        return this;
    }

    public StatusNotificationRequest withStatus(ChargePointStatus value) {
        setStatus(value);
        return this;
    }

    public StatusNotificationRequest withErrorCode(ChargePointErrorCode value) {
        setErrorCode(value);
        return this;
    }

    public StatusNotificationRequest withInfo(String value) {
        setInfo(value);
        return this;
    }

    public StatusNotificationRequest withTimestamp(DateTime value) {
        setTimestamp(value);
        return this;
    }

    public StatusNotificationRequest withVendorId(String value) {
        setVendorId(value);
        return this;
    }

    public StatusNotificationRequest withVendorErrorCode(String value) {
        setVendorErrorCode(value);
        return this;
    }

}
