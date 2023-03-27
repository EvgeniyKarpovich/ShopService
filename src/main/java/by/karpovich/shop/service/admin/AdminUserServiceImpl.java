package by.karpovich.shop.service.admin;

import by.karpovich.shop.api.dto.user.UserDtoForFindAll;
import by.karpovich.shop.api.dto.user.UserFullDtoOut;
import by.karpovich.shop.exception.IncorrectAmountException;
import by.karpovich.shop.exception.NotFoundModelException;
import by.karpovich.shop.jpa.entity.StatusUser;
import by.karpovich.shop.jpa.entity.UserEntity;
import by.karpovich.shop.jpa.repository.UserRepository;
import by.karpovich.shop.mapping.UserMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class AdminUserServiceImpl implements AdminUserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;

    @Override
    public void addBalance(Long userId, Double sum) {
        if (sum < 0) {
            throw new IncorrectAmountException("Sum must be more 0");
        }
        if (userRepository.findById(userId).isEmpty()) {
            throw new NotFoundModelException(String.format("User with id = %s not found", userId));
        } else {
            userRepository.addBalance(userId, sum);
        }
    }

    @Override
    @Transactional
    public void setUserStatus(Long userId, StatusUser status) {
        if (userRepository.findById(userId).isPresent()) {
            userRepository.setStatus(userId, status);
        } else {
            throw new NotFoundModelException(String.format("User with id = %s not found", userId));
        }
    }

    @Override
    public List<UserDtoForFindAll> findAllUsers() {
        List<UserEntity> usersModel = userRepository.findAll();

        log.info("method findAll - number of users found  = {} ", usersModel.size());
        return userMapper.mapListUserDtoForFindAllFromListModel(usersModel);
    }

    @Override
    public UserFullDtoOut findUserById(Long id) {
        var entity = userRepository.findById(id).orElseThrow(
                () -> new NotFoundModelException(String.format("User with id = %s not found", id)));

        log.info("method findById - the user found with id = {} ", entity.getId());
        return userMapper.mapUserFullDtoFromModel(entity);
    }
}
