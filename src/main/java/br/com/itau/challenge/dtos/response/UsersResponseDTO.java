package br.com.itau.challenge.dtos.response;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class UsersResponseDTO {

    private List<UserResponseDTO> users = new ArrayList<>();

    public void addUser(UserResponseDTO userDTO) {
        this.users.add(userDTO);
    }
}
