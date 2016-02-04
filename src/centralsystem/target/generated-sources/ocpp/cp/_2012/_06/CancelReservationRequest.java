
package ocpp.cp._2012._06;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;
import de.rwth.idsg.steve.ocpp.RequestType;


/**
 * Defines the CancelReservation.req PDU
 * 
 * <p>Java-Klasse für CancelReservationRequest complex type.
 * 
 * <p>Das folgende Schemafragment gibt den erwarteten Content an, der in dieser Klasse enthalten ist.
 * 
 * <pre>
 * &lt;complexType name="CancelReservationRequest"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="reservationId" type="{http://www.w3.org/2001/XMLSchema}int"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "CancelReservationRequest", propOrder = {
    "reservationId"
})
public class CancelReservationRequest
    implements RequestType
{

    protected int reservationId;

    /**
     * Ruft den Wert der reservationId-Eigenschaft ab.
     * 
     */
    public int getReservationId() {
        return reservationId;
    }

    /**
     * Legt den Wert der reservationId-Eigenschaft fest.
     * 
     */
    public void setReservationId(int value) {
        this.reservationId = value;
    }

    public boolean isSetReservationId() {
        return true;
    }

    public CancelReservationRequest withReservationId(int value) {
        setReservationId(value);
        return this;
    }

}
