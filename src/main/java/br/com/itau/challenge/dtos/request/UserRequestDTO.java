package br.com.itau.challenge.dtos.request;

import lombok.Data;
import org.hibernate.validator.constraints.br.CPF;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
public class UserRequestDTO {

    @NotBlank
    private String name;

    @Email(message = "O e-mail informado não é válido!")
    @NotBlank
    private String email;

    @CPF(message = "O CPF informado não é válido!")
    @NotBlank
    private String cpf;

    @NotBlank
    @Size(min = 6, max = 8)
    private String password;
}
