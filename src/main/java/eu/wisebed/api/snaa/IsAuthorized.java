
package eu.wisebed.api.snaa;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import eu.wisebed.api.common.UsernameUrnPrefixPair;


/**
 * <p>Java class for isAuthorized complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="isAuthorized">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="usernames" type="{urn:CommonTypes}UsernameUrnPrefixPair" maxOccurs="unbounded"/>
 *         &lt;element name="action" type="{http://testbed.wisebed.eu/api/snaa/v1/}Action"/>
 *         &lt;element name="nodeUrns" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "isAuthorized", propOrder = {
    "usernames",
    "action",
    "nodeUrns"
})
public class IsAuthorized {

    @XmlElement(required = true)
    protected List<UsernameUrnPrefixPair> usernames;
    @XmlElement(required = true)
    protected Action action;
    @XmlElement(required = true)
    protected String nodeUrns;

    /**
     * Gets the value of the usernames property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the usernames property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getUsernames().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link UsernameUrnPrefixPair }
     * 
     * 
     */
    public List<UsernameUrnPrefixPair> getUsernames() {
        if (usernames == null) {
            usernames = new ArrayList<UsernameUrnPrefixPair>();
        }
        return this.usernames;
    }

    /**
     * Gets the value of the action property.
     * 
     * @return
     *     possible object is
     *     {@link Action }
     *     
     */
    public Action getAction() {
        return action;
    }

    /**
     * Sets the value of the action property.
     * 
     * @param value
     *     allowed object is
     *     {@link Action }
     *     
     */
    public void setAction(Action value) {
        this.action = value;
    }

    /**
     * Gets the value of the nodeUrns property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNodeUrns() {
        return nodeUrns;
    }

    /**
     * Sets the value of the nodeUrns property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNodeUrns(String value) {
        this.nodeUrns = value;
    }

}
