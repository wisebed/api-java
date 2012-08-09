
package eu.wisebed.api.common;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for usernameNodeUrnsMap complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="usernameNodeUrnsMap">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="username" type="{urn:CommonTypes}UsernameUrnPrefixPair"/>
 *         &lt;element name="nodeUrns" type="{http://www.w3.org/2001/XMLSchema}string" maxOccurs="unbounded"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "usernameNodeUrnsMap", propOrder = {
    "username",
    "nodeUrns"
})
public class UsernameNodeUrnsMap {

    @XmlElement(required = true)
    protected UsernameUrnPrefixPair username;
    @XmlElement(required = true)
    protected List<String> nodeUrns;

    /**
     * Gets the value of the username property.
     * 
     * @return
     *     possible object is
     *     {@link UsernameUrnPrefixPair }
     *     
     */
    public UsernameUrnPrefixPair getUsername() {
        return username;
    }

    /**
     * Sets the value of the username property.
     * 
     * @param value
     *     allowed object is
     *     {@link UsernameUrnPrefixPair }
     *     
     */
    public void setUsername(UsernameUrnPrefixPair value) {
        this.username = value;
    }

    /**
     * Gets the value of the nodeUrns property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the nodeUrns property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getNodeUrns().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link String }
     * 
     * 
     */
    public List<String> getNodeUrns() {
        if (nodeUrns == null) {
            nodeUrns = new ArrayList<String>();
        }
        return this.nodeUrns;
    }

}
