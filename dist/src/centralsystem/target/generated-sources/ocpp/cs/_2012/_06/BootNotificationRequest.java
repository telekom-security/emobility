
package ocpp.cs._2012._06;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import de.rwth.idsg.steve.ocpp.RequestType;


/**
 * Defines the BootNotification.req PDU
 * 
 * <p>Java-Klasse für BootNotificationRequest complex type.
 * 
 * <p>Das folgende Schemafragment gibt den erwarteten Content an, der in dieser Klasse enthalten ist.
 * 
 * <pre>
 * &lt;complexType name="BootNotificationRequest"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="chargePointVendor" type="{urn://Ocpp/Cs/2012/06/}ChargePointVendor"/&gt;
 *         &lt;element name="chargePointModel" type="{urn://Ocpp/Cs/2012/06/}ChargePointModel"/&gt;
 *         &lt;element name="chargePointSerialNumber" type="{urn://Ocpp/Cs/2012/06/}ChargePointSerialNumber" minOccurs="0"/&gt;
 *         &lt;element name="chargeBoxSerialNumber" type="{urn://Ocpp/Cs/2012/06/}ChargeBoxSerialNumber" minOccurs="0"/&gt;
 *         &lt;element name="firmwareVersion" type="{urn://Ocpp/Cs/2012/06/}FirmwareVersion" minOccurs="0"/&gt;
 *         &lt;element name="iccid" type="{urn://Ocpp/Cs/2012/06/}IccidString" minOccurs="0"/&gt;
 *         &lt;element name="imsi" type="{urn://Ocpp/Cs/2012/06/}ImsiString" minOccurs="0"/&gt;
 *         &lt;element name="meterType" type="{urn://Ocpp/Cs/2012/06/}MeterType" minOccurs="0"/&gt;
 *         &lt;element name="meterSerialNumber" type="{urn://Ocpp/Cs/2012/06/}MeterSerialNumber" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "BootNotificationRequest", propOrder = {
    "chargePointVendor",
    "chargePointModel",
    "chargePointSerialNumber",
    "chargeBoxSerialNumber",
    "firmwareVersion",
    "iccid",
    "imsi",
    "meterType",
    "meterSerialNumber"
})
public class BootNotificationRequest
    implements RequestType
{

    @XmlElement(required = true)
    protected String chargePointVendor;
    @XmlElement(required = true)
    protected String chargePointModel;
    protected String chargePointSerialNumber;
    protected String chargeBoxSerialNumber;
    protected String firmwareVersion;
    protected String iccid;
    protected String imsi;
    protected String meterType;
    protected String meterSerialNumber;

    /**
     * Ruft den Wert der chargePointVendor-Eigenschaft ab.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getChargePointVendor() {
        return chargePointVendor;
    }

    /**
     * Legt den Wert der chargePointVendor-Eigenschaft fest.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setChargePointVendor(String value) {
        this.chargePointVendor = value;
    }

    public boolean isSetChargePointVendor() {
        return (this.chargePointVendor!= null);
    }

    /**
     * Ruft den Wert der chargePointModel-Eigenschaft ab.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getChargePointModel() {
        return chargePointModel;
    }

    /**
     * Legt den Wert der chargePointModel-Eigenschaft fest.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setChargePointModel(String value) {
        this.chargePointModel = value;
    }

    public boolean isSetChargePointModel() {
        return (this.chargePointModel!= null);
    }

    /**
     * Ruft den Wert der chargePointSerialNumber-Eigenschaft ab.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getChargePointSerialNumber() {
        return chargePointSerialNumber;
    }

    /**
     * Legt den Wert der chargePointSerialNumber-Eigenschaft fest.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setChargePointSerialNumber(String value) {
        this.chargePointSerialNumber = value;
    }

    public boolean isSetChargePointSerialNumber() {
        return (this.chargePointSerialNumber!= null);
    }

    /**
     * Ruft den Wert der chargeBoxSerialNumber-Eigenschaft ab.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getChargeBoxSerialNumber() {
        return chargeBoxSerialNumber;
    }

    /**
     * Legt den Wert der chargeBoxSerialNumber-Eigenschaft fest.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setChargeBoxSerialNumber(String value) {
        this.chargeBoxSerialNumber = value;
    }

    public boolean isSetChargeBoxSerialNumber() {
        return (this.chargeBoxSerialNumber!= null);
    }

    /**
     * Ruft den Wert der firmwareVersion-Eigenschaft ab.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFirmwareVersion() {
        return firmwareVersion;
    }

    /**
     * Legt den Wert der firmwareVersion-Eigenschaft fest.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFirmwareVersion(String value) {
        this.firmwareVersion = value;
    }

    public boolean isSetFirmwareVersion() {
        return (this.firmwareVersion!= null);
    }

    /**
     * Ruft den Wert der iccid-Eigenschaft ab.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getIccid() {
        return iccid;
    }

    /**
     * Legt den Wert der iccid-Eigenschaft fest.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setIccid(String value) {
        this.iccid = value;
    }

    public boolean isSetIccid() {
        return (this.iccid!= null);
    }

    /**
     * Ruft den Wert der imsi-Eigenschaft ab.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getImsi() {
        return imsi;
    }

    /**
     * Legt den Wert der imsi-Eigenschaft fest.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setImsi(String value) {
        this.imsi = value;
    }

    public boolean isSetImsi() {
        return (this.imsi!= null);
    }

    /**
     * Ruft den Wert der meterType-Eigenschaft ab.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getMeterType() {
        return meterType;
    }

    /**
     * Legt den Wert der meterType-Eigenschaft fest.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setMeterType(String value) {
        this.meterType = value;
    }

    public boolean isSetMeterType() {
        return (this.meterType!= null);
    }

    /**
     * Ruft den Wert der meterSerialNumber-Eigenschaft ab.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getMeterSerialNumber() {
        return meterSerialNumber;
    }

    /**
     * Legt den Wert der meterSerialNumber-Eigenschaft fest.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setMeterSerialNumber(String value) {
        this.meterSerialNumber = value;
    }

    public boolean isSetMeterSerialNumber() {
        return (this.meterSerialNumber!= null);
    }

    public BootNotificationRequest withChargePointVendor(String value) {
        setChargePointVendor(value);
        return this;
    }

    public BootNotificationRequest withChargePointModel(String value) {
        setChargePointModel(value);
        return this;
    }

    public BootNotificationRequest withChargePointSerialNumber(String value) {
        setChargePointSerialNumber(value);
        return this;
    }

    public BootNotificationRequest withChargeBoxSerialNumber(String value) {
        setChargeBoxSerialNumber(value);
        return this;
    }

    public BootNotificationRequest withFirmwareVersion(String value) {
        setFirmwareVersion(value);
        return this;
    }

    public BootNotificationRequest withIccid(String value) {
        setIccid(value);
        return this;
    }

    public BootNotificationRequest withImsi(String value) {
        setImsi(value);
        return this;
    }

    public BootNotificationRequest withMeterType(String value) {
        setMeterType(value);
        return this;
    }

    public BootNotificationRequest withMeterSerialNumber(String value) {
        setMeterSerialNumber(value);
        return this;
    }

}
