package net.steampunkfoundry.techdemo.docs.domain;

import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@ToString
public class SupportDocument implements Serializable {

    private String id;

    private String name;

    private String formType;

    private String dateSubmitted;

    private String status;

    private String url;

    public SupportDocument(String name) {
        this.name = name;
    }

}
