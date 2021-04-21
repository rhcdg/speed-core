package net.steampunkfoundry.techdemo.service1.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@JsonIgnoreProperties(ignoreUnknown = true)
@Getter
@Setter
public class FormResponse {

    @JsonProperty("_embedded")
    private FormsEmbeddedListJson embedded;

}
