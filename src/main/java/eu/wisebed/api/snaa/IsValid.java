
package eu.wisebed.api.snaa;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import eu.wisebed.api.common.SecretAuthenticationKey;


/**
 * <p>Java class for isValid complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="isValid">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="secretAuthenticationKey" type="{urn:CommonTypes}secretAuthenticationKey"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "isValid", propOrder = {
    "secretAuthenticationKey"
})
public class IsValid {

    @XmlElement(required = true)
    protected SecretAuthenticationKey secretAuthenticationKey;

    /**
     * Gets the value of the secretAuthenticationKey property.
     * 
     * @return
     *     possible object is
     *     {@link SecretAuthenticationKey }
     *     
     */
    public SecretAuthenticationKey getSecretAuthenticationKey() {
        return secretAuthenticationKey;
    }

    /**
     * Sets the value of the secretAuthenticationKey property.
     * 
     * @param value
     *     allowed object is
     *     {@link SecretAuthenticationKey }
     *     
     */
    public void setSecretAuthenticationKey(SecretAuthenticationKey value) {
        this.secretAuthenticationKey = value;
    }

}
