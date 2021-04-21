package net.steampunkfoundry.techdemo.docs.request;

import java.io.StringWriter;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.XmlType;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Getter
@Setter
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "AddressValidateRequest")
public class AddressValidateRequest {

    @XmlAttribute(name = "USERID", required = true)
    private String userId;

    /* Set this value to 1 to return all currently documented response fields. */
    @XmlElement(name = "Revision", required = true)
    private String revision = "1";

    @XmlElement(name = "Address", required = true)
    private Address address;

    @XmlTransient
    private Object o;

    public String toXml() throws JAXBException {
        JAXBContext jaxbContext = JAXBContext.newInstance(AddressValidateRequest.class);
        Marshaller marshaller = jaxbContext.createMarshaller();
        marshaller.setProperty(Marshaller.JAXB_FRAGMENT, Boolean.TRUE);
        StringWriter stringWriter = new StringWriter();
        marshaller.marshal(this, stringWriter);
        return stringWriter.toString();
    }

    @XmlAccessorType(XmlAccessType.FIELD)
    @Getter
    @Setter
    @NoArgsConstructor
    @XmlType(name = "", propOrder = {"firmName", "address1", "address2", "city", "state", "zip5", "zip4"})
    public static class Address {

        @XmlElement(name = "FirmName")
        private String firmName;

        @XmlElement(name = "Address1")
        private String address1;

        /* misnomer. this one is actually the optional line 2 (apartment, suite) */
        @XmlElement(name = "Address2")
        private String address2;

        @XmlElement(name = "City")
        private String city;

        @XmlElement(name = "State")
        private String state;

        @XmlElement(name = "Zip5")
        private String zip5;

        @XmlElement(name = "Zip4")
        private String zip4;

        public Address(String address1, String address2, String city, String state, String zip5, String zip4) {
            this.address1 = address1;
            this.address2 = address2;
            this.city = city;
            this.state = state;
            this.zip5 = zip5;
            this.zip4 = zip4;
        }
    }

}
