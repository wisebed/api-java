
package eu.wisebed.api.snaa;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for Action.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="Action">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;enumeration value="RS_DELETE_RESERVATION"/>
 *     &lt;enumeration value="RS_GET_RESERVATIONS"/>
 *     &lt;enumeration value="RS_MAKE_RESERVATION"/>
 *     &lt;enumeration value="SM_ARE_NODES_ALIVE"/>
 *     &lt;enumeration value="SM_FREE"/>
 *     &lt;enumeration value="WSN_ARE_NODES_ALIVE"/>
 *     &lt;enumeration value="WSN_DESTROY_VIRTUAL_LINK"/>
 *     &lt;enumeration value="WSN_DISABLE_NODE"/>
 *     &lt;enumeration value="WSN_DISABLE_PHYSICAL_LINK"/>
 *     &lt;enumeration value="WSN_ENABLE_NODE"/>
 *     &lt;enumeration value="WSN_ENABLE_PHYSICAL_LINK"/>
 *     &lt;enumeration value="WSN_FLASH_PROGRAMS"/>
 *     &lt;enumeration value="WSN_RESET_NODES"/>
 *     &lt;enumeration value="WSN_SEND"/>
 *     &lt;enumeration value="WSN_SET_CHANNEL_PIPELINE"/>
 *     &lt;enumeration value="WSN_SET_VIRTUAL_LINK"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "Action")
@XmlEnum
public enum Action {

    RS_DELETE_RESERVATION,
    RS_GET_RESERVATIONS,
    RS_MAKE_RESERVATION,
    SM_ARE_NODES_ALIVE,
    SM_FREE,
    WSN_ARE_NODES_ALIVE,
    WSN_DESTROY_VIRTUAL_LINK,
    WSN_DISABLE_NODE,
    WSN_DISABLE_PHYSICAL_LINK,
    WSN_ENABLE_NODE,
    WSN_ENABLE_PHYSICAL_LINK,
    WSN_FLASH_PROGRAMS,
    WSN_RESET_NODES,
    WSN_SEND,
    WSN_SET_CHANNEL_PIPELINE,
    WSN_SET_VIRTUAL_LINK;

    public String value() {
        return name();
    }

    public static Action fromValue(String v) {
        return valueOf(v);
    }

}
