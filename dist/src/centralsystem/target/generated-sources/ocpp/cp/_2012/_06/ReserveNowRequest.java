
package ocpp.cp._2012._06;

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
 * Defines the ReserveNow.req PDU
 * 
 * <p>Java-Klasse für ReserveNowRequest complex type.
 * 
 * <p>Das folgende Schemafragment gibt den erwarteten Content an, der in dieser Klasse enthalten ist.
 * 
 * <pre>
 * &lt;complexType name="ReserveNowRequest"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="connectorId" type="{http://www.w3.org/2001/XMLSchema}int"/&gt;
 *         &lt;element name="expiryDate" type="{http://www.w3.org/2001/XMLSchema}dateTime"/&gt;
 *         &lt;element name="idTag" type="{urn://Ocpp/Cp/2012/06/}IdToken"/&gt;
 *         &lt;element name="parentIdTag" type="{urn://Ocpp/Cp/2012/06/}IdToken" minOccurs="0"/&gt;
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
@XmlType(name = "ReserveNowRequest", propOrder = {
    "connectorId",
    "expiryDate",
    "idTag",
    "parentIdTag",
    "reservationId"
})
public class ReserveNowRequest
    implements RequestType
{

    protected int connectorId;
    @XmlElement(required = true, type = String.class)
    @XmlJavaTypeAdapter(JodaDateTimeConverter.class)
    @XmlSchemaType(name = "dateTime")
    protected DateTime expiryDate;
    @XmlElement(required = true)
    protected String idTag;
    protected String parentIdTag;
    protected int reservationId;

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
     * Ruft den Wert der expiryDate-Eigenschaft ab.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public DateTime getExpiryDate() {
        return expiryDate;
    }

    /**
     * Legt den Wert der expiryDate-Eigenschaft fest.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setExpiryDate(DateTime value) {
        this.expiryDate = value;
    }

    public boolean isSetExpiryDate() {
        return (this.expiryDate!= null);
    }

    /**
     * Ruft den Wert der idTag-Eigenschaft ab.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getIdTag() {
        return idTag;
    }

    /**
     * Legt den Wert der idTag-Eigenschaft fest.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setIdTag(String value) {
        this.idTag = value;
    }

    public boolean isSetIdTag() {
        return (this.idTag!= null);
    }

    /**
     * Ruft den Wert der parentIdTag-Eigenschaft ab.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getParentIdTag() {
        return parentIdTag;
    }

    /**
     * Legt den Wert der parentIdTag-Eigenschaft fest.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setParentIdTag(String value) {
        this.parentIdTag = value;
    }

    public boolean isSetParentIdTag() {
        return (this.parentIdTag!= null);
    }

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

    public ReserveNowRequest withConnectorId(int value) {
        setConnectorId(value);
        return this;
    }

    public ReserveNowRequest withExpiryDate(DateTime value) {
        setExpiryDate(value);
        return this;
    }

    public ReserveNowRequest withIdTag(String value) {
        setIdTag(value);
        return this;
    }

    public ReserveNowRequest withParentIdTag(String value) {
        setParentIdTag(value);
        return this;
    }

    public ReserveNowRequest withReservationId(int value) {
        setReservationId(value);
        return this;
    }

}
