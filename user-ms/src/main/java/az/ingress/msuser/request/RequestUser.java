package az.ingress.msuser.request;


import az.ingress.model.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RequestUser {

    String nameSurname;
    String username;


    String password;
    private Role role;
}
