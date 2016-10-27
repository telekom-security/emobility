
package ocpp.cs._2012._06;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.XmlValue;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import de.rwth.idsg.steve.utils.JodaDateTimeConverter;
import org.joda.time.DateTime;


/**
 * Defines single value of the meter-value-value
 * 
 * <p>Java-Klasse für MeterValue complex type.
 * 
 * <p>Das folgende Schemafragment gibt den erwarteten Content an, der in dieser Klasse enthalten ist.
 * 
 * <pre>
 * &lt;complexType name="MeterValue"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="timestamp" type="{http://www.w3.org/2001/XMLSchema}dateTime"/&gt;
 *         &lt;element name="value" maxOccurs="unbounded"&gt;
 *           &lt;complexType&gt;
 *             &lt;simpleContent&gt;
 *               &lt;extension base="&lt;http://www.w3.org/2001/XMLSchema&gt;string"&gt;
 *                 &lt;attribute name="context" type="{urn://Ocpp/Cs/2012/06/}ReadingContext" /&gt;
 *                 &lt;attribute name="format" type="{urn://Ocpp/Cs/2012/06/}ValueFormat" /&gt;
 *                 &lt;attribute name="measurand" type="{urn://Ocpp/Cs/2012/06/}Measurand" /&gt;
 *                 &lt;attribute name="location" type="{urn://Ocpp/Cs/2012/06/}Location" /&gt;
 *                 &lt;attribute name="unit" type="{urn://Ocpp/Cs/2012/06/}UnitOfMeasure" /&gt;
 *               &lt;/extension&gt;
 *             &lt;/simpleContent&gt;
 *           &lt;/complexType&gt;
 *         &lt;/element&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "MeterValue", propOrder = {
    "timestamp",
    "value"
})
public class MeterValue {

    @XmlElement(required = true, type = String.class)
    @XmlJavaTypeAdapter(JodaDateTimeConverter.class)
    @XmlSchemaType(name = "dateTime")
    protected DateTime timestamp;
    @XmlElement(required = true)
    protected List<MeterValue.Value> value;

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
     * Gets the value of the value property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the value property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getValue().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link MeterValue.Value }
     * 
     * 
     */
    public List<MeterValue.Value> getValue() {
        if (value == null) {
            value = new ArrayList<MeterValue.Value>();
        }
        return this.value;
    }

    public boolean isSetValue() {
        return ((this.value!= null)&&(!this.value.isEmpty()));
    }

    public void unsetValue() {
        this.value = null;
    }

    public MeterValue withTimestamp(DateTime value) {
        setTimestamp(value);
        return this;
    }

    public MeterValue withValue(MeterValue.Value... values) {
        if (values!= null) {
            for (MeterValue.Value value: values) {
                getValue().add(value);
            }
        }
        return this;
    }

    public MeterValue withValue(Collection<MeterValue.Value> values) {
        if (values!= null) {
            getValue().addAll(values);
        }
        return this;
    }


    /**
     * <p>Java-Klasse für anonymous complex type.
     * 
     * <p>Das folgende Schemafragment gibt den erwarteten Content an, der in dieser Klasse enthalten ist.
     * 
     * <pre>
     * &lt;complexType&gt;
     *   &lt;simpleContent&gt;
     *     &lt;extension base="&lt;http://www.w3.org/2001/XMLSchema&gt;string"&gt;
     *       &lt;attribute name="context" type="{urn://Ocpp/Cs/2012/06/}ReadingContext" /&gt;
     *       &lt;attribute name="format" type="{urn://Ocpp/Cs/2012/06/}ValueFormat" /&gt;
     *       &lt;attribute name="measurand" type="{urn://Ocpp/Cs/2012/06/}Measurand" /&gt;
     *       &lt;attribute name="location" type="{urn://Ocpp/Cs/2012/06/}Location" /&gt;
     *       &lt;attribute name="unit" type="{urn://Ocpp/Cs/2012/06/}UnitOfMeasure" /&gt;
     *     &lt;/extension&gt;
     *   &lt;/simpleContent&gt;
     * &lt;/complexType&gt;
     * </pre>
     * 
     * 
     */
    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "", propOrder = {
        "value"
    })
    public static class Value {

        @XmlValue
        protected String value;
        @XmlAttribute(name = "context")
        protected ReadingContext context;
        @XmlAttribute(name = "format")
        protected ValueFormat format;
        @XmlAttribute(name = "measurand")
        protected Measurand measurand;
        @XmlAttribute(name = "location")
        protected Location location;
        @XmlAttribute(name = "unit")
        protected UnitOfMeasure unit;

        /**
         * Ruft den Wert der value-Eigenschaft ab.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getValue() {
            return value;
        }

        /**
         * Legt den Wert der value-Eigenschaft fest.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setValue(String value) {
            this.value = value;
        }

        public boolean isSetValue() {
            return (this.value!= null);
        }

        /**
         * Ruft den Wert der context-Eigenschaft ab.
         * 
         * @return
         *     possible object is
         *     {@link ReadingContext }
         *     
         */
        public ReadingContext getContext() {
            return context;
        }

        /**
         * Legt den Wert der context-Eigenschaft fest.
         * 
         * @param value
         *     allowed object is
         *     {@link ReadingContext }
         *     
         */
        public void setContext(ReadingContext value) {
            this.context = value;
        }

        public boolean isSetContext() {
            return (this.context!= null);
        }

        /**
         * Ruft den Wert der format-Eigenschaft ab.
         * 
         * @return
         *     possible object is
         *     {@link ValueFormat }
         *     
         */
        public ValueFormat getFormat() {
            return format;
        }

        /**
         * Legt den Wert der format-Eigenschaft fest.
         * 
         * @param value
         *     allowed object is
         *     {@link ValueFormat }
         *     
         */
        public void setFormat(ValueFormat value) {
            this.format = value;
        }

        public boolean isSetFormat() {
            return (this.format!= null);
        }

        /**
         * Ruft den Wert der measurand-Eigenschaft ab.
         * 
         * @return
         *     possible object is
         *     {@link Measurand }
         *     
         */
        public Measurand getMeasurand() {
            return measurand;
        }

        /**
         * Legt den Wert der measurand-Eigenschaft fest.
         * 
         * @param value
         *     allowed object is
         *     {@link Measurand }
         *     
         */
        public void setMeasurand(Measurand value) {
            this.measurand = value;
        }

        public boolean isSetMeasurand() {
            return (this.measurand!= null);
        }

        /**
         * Ruft den Wert der location-Eigenschaft ab.
         * 
         * @return
         *     possible object is
         *     {@link Location }
         *     
         */
        public Location getLocation() {
            return location;
        }

        /**
         * Legt den Wert der location-Eigenschaft fest.
         * 
         * @param value
         *     allowed object is
         *     {@link Location }
         *     
         */
        public void setLocation(Location value) {
            this.location = value;
        }

        public boolean isSetLocation() {
            return (this.location!= null);
        }

        /**
         * Ruft den Wert der unit-Eigenschaft ab.
         * 
         * @return
         *     possible object is
         *     {@link UnitOfMeasure }
         *     
         */
        public UnitOfMeasure getUnit() {
            return unit;
        }

        /**
         * Legt den Wert der unit-Eigenschaft fest.
         * 
         * @param value
         *     allowed object is
         *     {@link UnitOfMeasure }
         *     
         */
        public void setUnit(UnitOfMeasure value) {
            this.unit = value;
        }

        public boolean isSetUnit() {
            return (this.unit!= null);
        }

        public MeterValue.Value withValue(String value) {
            setValue(value);
            return this;
        }

        public MeterValue.Value withContext(ReadingContext value) {
            setContext(value);
            return this;
        }

        public MeterValue.Value withFormat(ValueFormat value) {
            setFormat(value);
            return this;
        }

        public MeterValue.Value withMeasurand(Measurand value) {
            setMeasurand(value);
            return this;
        }

        public MeterValue.Value withLocation(Location value) {
            setLocation(value);
            return this;
        }

        public MeterValue.Value withUnit(UnitOfMeasure value) {
            setUnit(value);
            return this;
        }

    }

}
