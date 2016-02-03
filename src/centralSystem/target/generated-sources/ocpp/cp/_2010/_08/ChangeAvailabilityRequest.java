
package ocpp.cp._2010._08;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import de.rwth.idsg.steve.ocpp.RequestType;


/**
 * Defines the ChangeAvailability.req PDU
 * 
 * <p>Java-Klasse für ChangeAvailabilityRequest complex type.
 * 
 * <p>Das folgende Schemafragment gibt den erwarteten Content an, der in dieser Klasse enthalten ist.
 * 
 * <pre>
 * &lt;complexType name="ChangeAvailabilityRequest"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="connectorId" type="{http://www.w3.org/2001/XMLSchema}int"/&gt;
 *         &lt;element name="type" type="{urn://Ocpp/Cp/2010/08/}AvailabilityType"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ChangeAvailabilityRequest", propOrder = {
    "connectorId",
    "type"
})
public class ChangeAvailabilityRequest
    implements RequestType
{

    protected int connectorId;
    @XmlElement(required = true)
    @XmlSchemaType(name = "string")
    protected AvailabilityType type;

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
     * Ruft den Wert der type-Eigenschaft ab.
     * 
     * @return
     *     possible object is
     *     {@link AvailabilityType }
     *     
     */
    public AvailabilityType getType() {
        return type;
    }

    /**
     * Legt den Wert der type-Eigenschaft fest.
     * 
     * @param value
     *     allowed object is
     *     {@link AvailabilityType }
     *     
     */
    public void setType(AvailabilityType value) {
        this.type = value;
    }

    public boolean isSetType() {
        return (this.type!= null);
    }

    public ChangeAvailabilityRequest withConnectorId(int value) {
        setConnectorId(value);
        return this;
    }

    public ChangeAvailabilityRequest withType(AvailabilityType value) {
        setType(value);
        return this;
    }

}
