package itmo.blps.lab1.converters;

import itmo.blps.lab1.dto.UserDTO;
import itmo.blps.lab1.entity.User;

public class UserConverter {

    public static UserDTO toDTO(User user) {
        UserDTO dto = new UserDTO();
        dto.setId(user.getId());
        dto.setEmail(user.getEmail());
        dto.setName(user.getName());
        dto.setPhoneNumber(user.getPhoneNumber());
        dto.setRole(UserDTO.Role.valueOf(user.getRole().name()));
        return dto;
    }

    public static User fromDTO(UserDTO dto) {
        User user = new User();
        user.setId(dto.getId());
        user.setEmail(dto.getEmail());
        user.setName(dto.getName());
        user.setPhoneNumber(dto.getPhoneNumber());
        user.setRole(User.Role.valueOf(dto.getRole().name()));
        return user;
    }
}