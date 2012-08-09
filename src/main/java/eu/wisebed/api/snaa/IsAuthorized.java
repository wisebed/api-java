
package eu.wisebed.api.snaa;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import eu.wisebed.api.common.UsernameNodeUrnsMap;


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
 *         &lt;element name="usernameNodeUrnsMapList" type="{urn:CommonTypes}usernameNodeUrnsMap" maxOccurs="unbounded"/>
 *         &lt;element name="action" type="{http://testbed.wisebed.eu/api/snaa/v1/}Action"/>
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
    "usernameNodeUrnsMapList",
    "action"
})
public class IsAuthorized {

    @XmlElement(required = true)
    protected List<UsernameNodeUrnsMap> usernameNodeUrnsMapList;
    @XmlElement(required = true)
    protected Action action;

    /**
     * Gets the value of the usernameNodeUrnsMapList property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the usernameNodeUrnsMapList property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getUsernameNodeUrnsMapList().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link UsernameNodeUrnsMap }
     * 
     * 
     */
    public List<UsernameNodeUrnsMap> getUsernameNodeUrnsMapList() {
        if (usernameNodeUrnsMapList == null) {
            usernameNodeUrnsMapList = new ArrayList<UsernameNodeUrnsMap>();
        }
        return this.usernameNodeUrnsMapList;
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

}
