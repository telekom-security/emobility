
package ocpp.cs._2012._06;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;
import de.rwth.idsg.steve.ocpp.ResponseType;


/**
 * Defines the StopTransaction.conf PDU
 * 
 * <p>Java-Klasse für StopTransactionResponse complex type.
 * 
 * <p>Das folgende Schemafragment gibt den erwarteten Content an, der in dieser Klasse enthalten ist.
 * 
 * <pre>
 * &lt;complexType name="StopTransactionResponse"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="idTagInfo" type="{urn://Ocpp/Cs/2012/06/}IdTagInfo" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "StopTransactionResponse", propOrder = {
    "idTagInfo"
})
public class StopTransactionResponse
    implements ResponseType
{

    protected IdTagInfo idTagInfo;

    /**
     * Ruft den Wert der idTagInfo-Eigenschaft ab.
     * 
     * @return
     *     possible object is
     *     {@link IdTagInfo }
     *     
     */
    public IdTagInfo getIdTagInfo() {
        return idTagInfo;
    }

    /**
     * Legt den Wert der idTagInfo-Eigenschaft fest.
     * 
     * @param value
     *     allowed object is
     *     {@link IdTagInfo }
     *     
     */
    public void setIdTagInfo(IdTagInfo value) {
        this.idTagInfo = value;
    }

    public boolean isSetIdTagInfo() {
        return (this.idTagInfo!= null);
    }

    public StopTransactionResponse withIdTagInfo(IdTagInfo value) {
        setIdTagInfo(value);
        return this;
    }

}
