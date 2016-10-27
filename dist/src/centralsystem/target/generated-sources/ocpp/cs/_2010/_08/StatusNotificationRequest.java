
package ocpp.cs._2010._08;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import de.rwth.idsg.steve.ocpp.RequestType;


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
 *         &lt;element name="status" type="{urn://Ocpp/Cs/2010/08/}ChargePointStatus"/&gt;
 *         &lt;element name="errorCode" type="{urn://Ocpp/Cs/2010/08/}ChargePointErrorCode"/&gt;
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
    "errorCode"
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

}
