package sprint_7.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Courier {
    private String login;
    private String password;
    private String firstName;
    private Integer id;
}
