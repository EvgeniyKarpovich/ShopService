package by.karpovich.shop.service.admin;

import by.karpovich.shop.api.dto.user.UserDtoForFindAll;
import by.karpovich.shop.api.dto.user.UserFullDtoOut;
import by.karpovich.shop.jpa.entity.StatusUser;

import java.util.List;

public interface AdminUserService {

    void addBalance(Long userId, Double bonusBalance);

    void setUserStatus(Long organizationId, StatusUser status);

    List<UserDtoForFindAll> findAllUsers();

    UserFullDtoOut findUserById(Long userid);
}
