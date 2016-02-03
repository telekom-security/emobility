
package ocpp.cp._2010._08;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import de.rwth.idsg.steve.ocpp.RequestType;


/**
 * Defines the Reset.req PDU
 * 
 * <p>Java-Klasse für ResetRequest complex type.
 * 
 * <p>Das folgende Schemafragment gibt den erwarteten Content an, der in dieser Klasse enthalten ist.
 * 
 * <pre>
 * &lt;complexType name="ResetRequest"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="type" type="{urn://Ocpp/Cp/2010/08/}ResetType"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ResetRequest", propOrder = {
    "type"
})
public class ResetRequest
    implements RequestType
{

    @XmlElement(required = true)
    @XmlSchemaType(name = "string")
    protected ResetType type;

    /**
     * Ruft den Wert der type-Eigenschaft ab.
     * 
     * @return
     *     possible object is
     *     {@link ResetType }
     *     
     */
    public ResetType getType() {
        return type;
    }

    /**
     * Legt den Wert der type-Eigenschaft fest.
     * 
     * @param value
     *     allowed object is
     *     {@link ResetType }
     *     
     */
    public void setType(ResetType value) {
        this.type = value;
    }

    public boolean isSetType() {
        return (this.type!= null);
    }

    public ResetRequest withType(ResetType value) {
        setType(value);
        return this;
    }

}
