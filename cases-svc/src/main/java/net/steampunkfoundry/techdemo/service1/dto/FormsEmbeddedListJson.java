package net.steampunkfoundry.techdemo.service1.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.util.List;
import lombok.Getter;
import lombok.Setter;

@JsonIgnoreProperties(ignoreUnknown = true)
@Getter
@Setter
public class FormsEmbeddedListJson {

    List<Form> forms;
}
