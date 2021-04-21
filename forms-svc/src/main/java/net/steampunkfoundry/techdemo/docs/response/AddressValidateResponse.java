package net.steampunkfoundry.techdemo.docs.response;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.XmlType;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import net.steampunkfoundry.techdemo.docs.enums.ValidationResponseStatus;
import net.steampunkfoundry.techdemo.docs.request.AddressValidateRequest.Address;

@Getter
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {"address", "error"})
@XmlRootElement(name = "AddressValidateResponse")
@NoArgsConstructor
public class AddressValidateResponse {

    @XmlTransient
    @Setter
    private ValidationResponseStatus status = ValidationResponseStatus.FAIL;

    /* This may need to be a list */
    @XmlElement(name = "Address", required = true)
    protected AddressResponse address;

    @XmlElement(name = "Error", required = false)
    protected ErrorResponse error;

    @XmlAccessorType(XmlAccessType.FIELD)
    @Getter
    @Setter
    @NoArgsConstructor
    public static class AddressResponse {

        @XmlElement(name = "FirmName")
        private String firmName;

        @XmlElement(name = "Address1", required = false)
        private String address1;

        /* misnomer. this one the usps calls Address1 but is actually the optional line 2 (apartment, suite) */
        @XmlElement(name = "Address2")
        private String address2;

        /* To return abbreviations you must set <Revision>=1 */
        @XmlElement(name = "Address2Abbreviation")
        private String address2Abbreviation;

        @XmlElement(name = "City")
        private String city;

        @XmlElement(name = "CityAbbreviation")
        private String cityAbbreviation;

        @XmlElement(name = "State")
        private String state;

        @XmlElement(name = "Urbanization")
        private String urbanization;

        @XmlElement(name = "Zip5")
        private String zip5;

        @XmlElement(name = "Zip4")
        private String zip4;

        @XmlElement(name = "DeliveryPoint")
        private String deliveryPoint;

        @XmlElement(name = "CarrierRoute")
        private String carrierRoute;

        //enum
        @XmlElement(name = "Footnotes")
        private String footnotes;

        /* The DPV Confirmation Indicator is the primary method used by the USPS to determine whether an address
             was considered deliverable or undeliverable. */
        //enum
        @XmlElement(name = "DPVConfirmation")
        private String dpvConfirmation;

        /* CMRA Indicates a private business that acts as a mail-receiving agent for specific clients. “Y” Address was found in the CMRA table.
            “N” Address was not found in the CMRA table.
            Blank Address not presented to the hash table.
        */
        //enum, Y/N
        @XmlElement(name = "DPVCMRA")
        private String dpvcmra;

        /* DPV® Standardized Footnotes - EZ24x7Plus and Mail*STAR are required to express DPV results using USPS standard two character footnotes. */
        //enum
        @XmlElement(name = "DPVFootnotes")
        private String dpvFootnotes;

        /* Indicates whether address is a business or not */
        //enum
        @XmlElement(name = "Business")
        private String business; //enum  y/n

        /* Central Delivery is for all business office buildings, office complexes, and/or industrial/professional parks.
           This may include call windows, horizontal locked mail receptacles, cluster box units. */
        //enum y/n
        @XmlElement(name = "CentralDeliveryPoint")
        private String centralDeliveryPoint;

        /* Is the location not occupied. */
        //enum y/n
        @XmlElement(name = "Vacant")
        private String vacant;
    }
}
