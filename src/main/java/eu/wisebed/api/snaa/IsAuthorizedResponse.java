
package eu.wisebed.api.snaa;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for isAuthorizedResponse complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="isAuthorizedResponse">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="resp" type="{http://testbed.wisebed.eu/api/snaa/v1/}AuthorizationResponse"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "isAuthorizedResponse", propOrder = {
    "resp"
})
public class IsAuthorizedResponse {

    @XmlElement(required = true)
    protected AuthorizationResponse resp;

    /**
     * Gets the value of the resp property.
     * 
     * @return
     *     possible object is
     *     {@link AuthorizationResponse }
     *     
     */
    public AuthorizationResponse getResp() {
        return resp;
    }

    /**
     * Sets the value of the resp property.
     * 
     * @param value
     *     allowed object is
     *     {@link AuthorizationResponse }
     *     
     */
    public void setResp(AuthorizationResponse value) {
        this.resp = value;
    }

}
