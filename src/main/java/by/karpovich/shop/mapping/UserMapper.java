package by.karpovich.shop.mapping;

import by.karpovich.shop.api.dto.user.UserDtoForFindAll;
import by.karpovich.shop.api.dto.user.UserFullDtoOut;
import by.karpovich.shop.jpa.entity.RoleEntity;
import by.karpovich.shop.jpa.entity.UserEntity;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class UserMapper {

    public UserDtoForFindAll mapUserDtoForFindAllFromModel(UserEntity model) {
        if (model == null) {
            return null;
        }

        UserDtoForFindAll dto = new UserDtoForFindAll();

        List<String> roles = model.getRoles().stream()
                .map(RoleEntity::getName)
                .collect(Collectors.toList());

        dto.setId(model.getId());
        dto.setUsername(model.getUsername());
        dto.setEmail(model.getEmail());
        dto.setRoles(roles);
        dto.setStatus(model.getStatusUser());

        return dto;
    }

    public List<UserDtoForFindAll> mapListUserDtoForFindAllFromListModel(List<UserEntity> users) {
        if (users == null) {
            return null;
        }

        List<UserDtoForFindAll> usersDto = new ArrayList<>();

        for (UserEntity model : users) {
            usersDto.add(mapUserDtoForFindAllFromModel(model));
        }
        return usersDto;
    }

    public UserFullDtoOut mapUserFullDtoFromModel(UserEntity model) {
        if (model == null) {
            return null;
        }

        UserFullDtoOut dto = new UserFullDtoOut();

        dto.setUsername(model.getUsername());
        dto.setEmail(model.getEmail());

        return dto;
    }
}
