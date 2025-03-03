package br.com.project.sonora.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

public class UserDTO {
    private String userType;
    private String name;
    private String cpf;
    private String email;
    private String password;
    private String phone;

}
