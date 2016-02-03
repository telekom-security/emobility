
package ocpp.cs._2010._08;

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
 * Defines the StartTransaction.req PDU
 * 
 * <p>Java-Klasse für StartTransactionRequest complex type.
 * 
 * <p>Das folgende Schemafragment gibt den erwarteten Content an, der in dieser Klasse enthalten ist.
 * 
 * <pre>
 * &lt;complexType name="StartTransactionRequest"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="connectorId" type="{http://www.w3.org/2001/XMLSchema}int"/&gt;
 *         &lt;element name="idTag" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="timestamp" type="{http://www.w3.org/2001/XMLSchema}dateTime"/&gt;
 *         &lt;element name="meterStart" type="{http://www.w3.org/2001/XMLSchema}int"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "StartTransactionRequest", propOrder = {
    "connectorId",
    "idTag",
    "timestamp",
    "meterStart"
})
public class StartTransactionRequest
    implements RequestType
{

    protected int connectorId;
    @XmlElement(required = true)
    protected String idTag;
    @XmlElement(required = true, type = String.class)
    @XmlJavaTypeAdapter(JodaDateTimeConverter.class)
    @XmlSchemaType(name = "dateTime")
    protected DateTime timestamp;
    protected int meterStart;

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
     * Ruft den Wert der meterStart-Eigenschaft ab.
     * 
     */
    public int getMeterStart() {
        return meterStart;
    }

    /**
     * Legt den Wert der meterStart-Eigenschaft fest.
     * 
     */
    public void setMeterStart(int value) {
        this.meterStart = value;
    }

    public boolean isSetMeterStart() {
        return true;
    }

    public StartTransactionRequest withConnectorId(int value) {
        setConnectorId(value);
        return this;
    }

    public StartTransactionRequest withIdTag(String value) {
        setIdTag(value);
        return this;
    }

    public StartTransactionRequest withTimestamp(DateTime value) {
        setTimestamp(value);
        return this;
    }

    public StartTransactionRequest withMeterStart(int value) {
        setMeterStart(value);
        return this;
    }

}
