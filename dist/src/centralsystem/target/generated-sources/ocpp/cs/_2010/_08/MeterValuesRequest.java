
package ocpp.cs._2010._08;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;
import de.rwth.idsg.steve.ocpp.RequestType;


/**
 * Defines the MeterValues.req PDU
 * 
 * <p>Java-Klasse für MeterValuesRequest complex type.
 * 
 * <p>Das folgende Schemafragment gibt den erwarteten Content an, der in dieser Klasse enthalten ist.
 * 
 * <pre>
 * &lt;complexType name="MeterValuesRequest"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="connectorId" type="{http://www.w3.org/2001/XMLSchema}int"/&gt;
 *         &lt;element name="values" type="{urn://Ocpp/Cs/2010/08/}MeterValue" maxOccurs="unbounded" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "MeterValuesRequest", propOrder = {
    "connectorId",
    "values"
})
public class MeterValuesRequest
    implements RequestType
{

    protected int connectorId;
    protected List<MeterValue> values;

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
     * Gets the value of the values property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the values property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getValues().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link MeterValue }
     * 
     * 
     */
    public List<MeterValue> getValues() {
        if (values == null) {
            values = new ArrayList<MeterValue>();
        }
        return this.values;
    }

    public boolean isSetValues() {
        return ((this.values!= null)&&(!this.values.isEmpty()));
    }

    public void unsetValues() {
        this.values = null;
    }

    public MeterValuesRequest withConnectorId(int value) {
        setConnectorId(value);
        return this;
    }

    public MeterValuesRequest withValues(MeterValue... values) {
        if (values!= null) {
            for (MeterValue value: values) {
                getValues().add(value);
            }
        }
        return this;
    }

    public MeterValuesRequest withValues(Collection<MeterValue> values) {
        if (values!= null) {
            getValues().addAll(values);
        }
        return this;
    }

}
