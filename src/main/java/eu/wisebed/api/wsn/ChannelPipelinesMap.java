
package eu.wisebed.api.wsn;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for ChannelPipelinesMap complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ChannelPipelinesMap">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="nodeUrns" type="{http://www.w3.org/2001/XMLSchema}string" maxOccurs="unbounded"/>
 *         &lt;element name="channelHandlers" type="{urn:WSNService}ChannelHandlerConfiguration" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ChannelPipelinesMap", propOrder = {
    "nodeUrns",
    "channelHandlers"
})
public class ChannelPipelinesMap {

    @XmlElement(required = true)
    protected List<String> nodeUrns;
    protected List<ChannelHandlerConfiguration> channelHandlers;

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

    /**
     * Gets the value of the channelHandlers property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the channelHandlers property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getChannelHandlers().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link ChannelHandlerConfiguration }
     * 
     * 
     */
    public List<ChannelHandlerConfiguration> getChannelHandlers() {
        if (channelHandlers == null) {
            channelHandlers = new ArrayList<ChannelHandlerConfiguration>();
        }
        return this.channelHandlers;
    }

}
