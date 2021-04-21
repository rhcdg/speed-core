package net.steampunkfoundry.techdemo.docs.response;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@ToString
@Getter
@Setter
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "Error")
@NoArgsConstructor
public class ErrorResponse {

    @XmlElement(name = "Number")
    private String number;

    @XmlElement(name = "Source")
    private String source;

    @XmlElement(name = "Description")
    private String description;

    @XmlElement(name = "HelpFile")
    private String helpFile;

    @XmlElement(name = "HelpContext")
    private String helpContext;

}
