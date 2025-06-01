package dtn.com.account_service.model;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Account {
    private int id;
    private String username;
    private String firstName;
    private String lastName;
}
