
package ocpp.cs._2012._06;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import de.rwth.idsg.steve.ocpp.ResponseType;


/**
 * Defines the StartTransaction.conf PDU
 * 
 * <p>Java-Klasse für StartTransactionResponse complex type.
 * 
 * <p>Das folgende Schemafragment gibt den erwarteten Content an, der in dieser Klasse enthalten ist.
 * 
 * <pre>
 * &lt;complexType name="StartTransactionResponse"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="transactionId" type="{http://www.w3.org/2001/XMLSchema}int"/&gt;
 *         &lt;element name="idTagInfo" type="{urn://Ocpp/Cs/2012/06/}IdTagInfo"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "StartTransactionResponse", propOrder = {
    "transactionId",
    "idTagInfo"
})
public class StartTransactionResponse
    implements ResponseType
{

    protected int transactionId;
    @XmlElement(required = true)
    protected IdTagInfo idTagInfo;

    /**
     * Ruft den Wert der transactionId-Eigenschaft ab.
     * 
     */
    public int getTransactionId() {
        return transactionId;
    }

    /**
     * Legt den Wert der transactionId-Eigenschaft fest.
     * 
     */
    public void setTransactionId(int value) {
        this.transactionId = value;
    }

    public boolean isSetTransactionId() {
        return true;
    }

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

    public StartTransactionResponse withTransactionId(int value) {
        setTransactionId(value);
        return this;
    }

    public StartTransactionResponse withIdTagInfo(IdTagInfo value) {
        setIdTagInfo(value);
        return this;
    }

}
