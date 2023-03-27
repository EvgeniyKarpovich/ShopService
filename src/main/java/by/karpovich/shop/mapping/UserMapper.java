package by.karpovich.shop.mapping;

import by.karpovich.shop.api.dto.authentification.RegistrationForm;
import by.karpovich.shop.api.dto.user.UserDtoForFindAll;
import by.karpovich.shop.api.dto.user.UserForUpdate;
import by.karpovich.shop.api.dto.user.UserFullDtoOut;
import by.karpovich.shop.jpa.entity.RoleEntity;
import by.karpovich.shop.jpa.entity.StatusUser;
import by.karpovich.shop.jpa.entity.UserEntity;
import by.karpovich.shop.service.admin.AdminRoleServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class UserMapper {

    private static final String ROLE_USER = "ROLE_USER";
    private final BCryptPasswordEncoder passwordEncoder;
    private final AdminRoleServiceImpl roleService;

    public UserEntity mapEntityFromDtoForRegForm(RegistrationForm dto) {
        if (dto == null) {
            return null;
        }

        return UserEntity.builder()
                .username(dto.getUsername())
                .password(passwordEncoder.encode(dto.getPassword()))
                .email(dto.getEmail())
                .roles(roleService.findRoleByName(ROLE_USER))
                .statusUser(StatusUser.ACTIVE)
                .build();
    }

    public UserDtoForFindAll mapUserDtoForFindAllFromModel(UserEntity user) {
        if (user == null) {
            return null;
        }

        return UserDtoForFindAll.builder()
                .id(user.getId())
                .username(user.getUsername())
                .email(user.getEmail())
                .roles(getRolesFromUser(user))
                .status(user.getStatusUser())
                .build();
    }

    private List<String> getRolesFromUser(UserEntity entity) {
        return entity.getRoles().stream()
                .map(RoleEntity::getName)
                .collect(Collectors.toList());
    }

    public UserEntity mapEntityFromUpdateDto(UserForUpdate dto) {
        if (dto == null) {
            return null;
        }

        return UserEntity.builder()
                .username(dto.getUsername())
                .email(dto.getEmail())
                .build();
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

    public UserFullDtoOut mapUserFullDtoFromModel(UserEntity entity) {
        if (entity == null) {
            return null;
        }

        return UserFullDtoOut.builder()
                .username(entity.getUsername())
                .email(entity.getEmail())
                .balance(entity.getBalance())
                .build();
    }
}
