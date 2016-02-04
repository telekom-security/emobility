
package ocpp.cp._2012._06;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;
import de.rwth.idsg.steve.ocpp.RequestType;


/**
 * Defines the RemoteStopTransaction.req PDU
 * 
 * <p>Java-Klasse für RemoteStopTransactionRequest complex type.
 * 
 * <p>Das folgende Schemafragment gibt den erwarteten Content an, der in dieser Klasse enthalten ist.
 * 
 * <pre>
 * &lt;complexType name="RemoteStopTransactionRequest"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="transactionId" type="{http://www.w3.org/2001/XMLSchema}int"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "RemoteStopTransactionRequest", propOrder = {
    "transactionId"
})
public class RemoteStopTransactionRequest
    implements RequestType
{

    protected int transactionId;

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

    public RemoteStopTransactionRequest withTransactionId(int value) {
        setTransactionId(value);
        return this;
    }

}
