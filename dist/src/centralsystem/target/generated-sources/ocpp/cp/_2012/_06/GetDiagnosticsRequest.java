
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
 * Defines the GetDiagnostics.req PDU
 * 
 * <p>Java-Klasse für GetDiagnosticsRequest complex type.
 * 
 * <p>Das folgende Schemafragment gibt den erwarteten Content an, der in dieser Klasse enthalten ist.
 * 
 * <pre>
 * &lt;complexType name="GetDiagnosticsRequest"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="location" type="{http://www.w3.org/2001/XMLSchema}anyURI"/&gt;
 *         &lt;element name="startTime" type="{http://www.w3.org/2001/XMLSchema}dateTime" minOccurs="0"/&gt;
 *         &lt;element name="stopTime" type="{http://www.w3.org/2001/XMLSchema}dateTime" minOccurs="0"/&gt;
 *         &lt;element name="retries" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/&gt;
 *         &lt;element name="retryInterval" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "GetDiagnosticsRequest", propOrder = {
    "location",
    "startTime",
    "stopTime",
    "retries",
    "retryInterval"
})
public class GetDiagnosticsRequest
    implements RequestType
{

    @XmlElement(required = true)
    @XmlSchemaType(name = "anyURI")
    protected String location;
    @XmlElement(type = String.class)
    @XmlJavaTypeAdapter(JodaDateTimeConverter.class)
    @XmlSchemaType(name = "dateTime")
    protected DateTime startTime;
    @XmlElement(type = String.class)
    @XmlJavaTypeAdapter(JodaDateTimeConverter.class)
    @XmlSchemaType(name = "dateTime")
    protected DateTime stopTime;
    protected Integer retries;
    protected Integer retryInterval;

    /**
     * Ruft den Wert der location-Eigenschaft ab.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getLocation() {
        return location;
    }

    /**
     * Legt den Wert der location-Eigenschaft fest.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setLocation(String value) {
        this.location = value;
    }

    public boolean isSetLocation() {
        return (this.location!= null);
    }

    /**
     * Ruft den Wert der startTime-Eigenschaft ab.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public DateTime getStartTime() {
        return startTime;
    }

    /**
     * Legt den Wert der startTime-Eigenschaft fest.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setStartTime(DateTime value) {
        this.startTime = value;
    }

    public boolean isSetStartTime() {
        return (this.startTime!= null);
    }

    /**
     * Ruft den Wert der stopTime-Eigenschaft ab.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public DateTime getStopTime() {
        return stopTime;
    }

    /**
     * Legt den Wert der stopTime-Eigenschaft fest.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setStopTime(DateTime value) {
        this.stopTime = value;
    }

    public boolean isSetStopTime() {
        return (this.stopTime!= null);
    }

    /**
     * Ruft den Wert der retries-Eigenschaft ab.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getRetries() {
        return retries;
    }

    /**
     * Legt den Wert der retries-Eigenschaft fest.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setRetries(Integer value) {
        this.retries = value;
    }

    public boolean isSetRetries() {
        return (this.retries!= null);
    }

    /**
     * Ruft den Wert der retryInterval-Eigenschaft ab.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getRetryInterval() {
        return retryInterval;
    }

    /**
     * Legt den Wert der retryInterval-Eigenschaft fest.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setRetryInterval(Integer value) {
        this.retryInterval = value;
    }

    public boolean isSetRetryInterval() {
        return (this.retryInterval!= null);
    }

    public GetDiagnosticsRequest withLocation(String value) {
        setLocation(value);
        return this;
    }

    public GetDiagnosticsRequest withStartTime(DateTime value) {
        setStartTime(value);
        return this;
    }

    public GetDiagnosticsRequest withStopTime(DateTime value) {
        setStopTime(value);
        return this;
    }

    public GetDiagnosticsRequest withRetries(Integer value) {
        setRetries(value);
        return this;
    }

    public GetDiagnosticsRequest withRetryInterval(Integer value) {
        setRetryInterval(value);
        return this;
    }

}
