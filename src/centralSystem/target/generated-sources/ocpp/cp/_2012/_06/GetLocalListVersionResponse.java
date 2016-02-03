
package ocpp.cp._2012._06;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;
import de.rwth.idsg.steve.ocpp.ResponseType;


/**
 * Defines the GetLocalListVersion.conf PDU
 * 
 * <p>Java-Klasse für GetLocalListVersionResponse complex type.
 * 
 * <p>Das folgende Schemafragment gibt den erwarteten Content an, der in dieser Klasse enthalten ist.
 * 
 * <pre>
 * &lt;complexType name="GetLocalListVersionResponse"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="listVersion" type="{http://www.w3.org/2001/XMLSchema}int"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "GetLocalListVersionResponse", propOrder = {
    "listVersion"
})
public class GetLocalListVersionResponse
    implements ResponseType
{

    protected int listVersion;

    /**
     * Ruft den Wert der listVersion-Eigenschaft ab.
     * 
     */
    public int getListVersion() {
        return listVersion;
    }

    /**
     * Legt den Wert der listVersion-Eigenschaft fest.
     * 
     */
    public void setListVersion(int value) {
        this.listVersion = value;
    }

    public boolean isSetListVersion() {
        return true;
    }

    public GetLocalListVersionResponse withListVersion(int value) {
        setListVersion(value);
        return this;
    }

}
