package by.karpovich.shop.service;

import by.karpovich.shop.api.dto.authentification.JwtResponse;
import by.karpovich.shop.api.dto.authentification.LoginForm;
import by.karpovich.shop.api.dto.authentification.RegistrationForm;
import by.karpovich.shop.api.dto.product.ProductDtoForFindAll;
import by.karpovich.shop.api.dto.user.UserDtoForFindAll;
import by.karpovich.shop.api.dto.user.UserForUpdate;
import by.karpovich.shop.api.dto.user.UserFullDtoOut;
import by.karpovich.shop.exception.DuplicateException;
import by.karpovich.shop.exception.NotFoundModelException;
import by.karpovich.shop.jpa.entity.ProductEntity;
import by.karpovich.shop.jpa.entity.UserEntity;
import by.karpovich.shop.jpa.repository.UserRepository;
import by.karpovich.shop.mapping.ProductMapper;
import by.karpovich.shop.mapping.UserMapper;
import by.karpovich.shop.security.JwtUtils;
import by.karpovich.shop.security.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final AuthenticationManager authenticationManager;
    private final JwtUtils jwtUtils;
    private final UserMapper userMapper;
    private final ProductMapper productMapper;

    @Transactional
    public void signUp(RegistrationForm dto) {
        validateAlreadyExists(dto);

        userRepository.save(userMapper.mapEntityFromDtoForRegForm(dto));
    }

    @Transactional
    public JwtResponse signIn(LoginForm loginForm) {
        String username = loginForm.getUsername();
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginForm.getUsername(), loginForm.getPassword()));

        UserEntity userByName = findByName(username);
        SecurityContextHolder.getContext().setAuthentication(authentication);
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        String jwt = jwtUtils.generateToken(userByName.getUsername(), userByName.getId());

        return JwtResponse.builder()
                .id(userDetails.getId())
                .username(userDetails.getUsername())
                .email(userDetails.getEmail())
                .roles(mapStringRolesFromUserDetails(userDetails))
                .type("Bearer")
                .token(jwt)
                .build();
    }

    public List<String> mapStringRolesFromUserDetails(UserDetailsImpl userDetails) {
        return userDetails.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toList());
    }

    public UserFullDtoOut findById(Long id) {
        var entity = userRepository.findById(id).orElseThrow(
                () -> new NotFoundModelException(String.format("User with id = %s not found", id)));

        log.info("method findById - the user found with id = {} ", entity.getId());
        return userMapper.mapUserFullDtoFromModel(entity);
    }

    @Transactional
    public void deleteById(Long id) {
        if (userRepository.findById(id).isEmpty()) {
            throw new NotFoundModelException(String.format("User with id = %s not found", id));
        }
        userRepository.deleteById(id);
    }

    public List<ProductDtoForFindAll> userProducts(String authorization) {
        Long userIdFromToken = getUserIdFromToken(authorization);
        List<ProductEntity> products = findUserByIdWhichWillReturnModel(userIdFromToken).getProducts();

        return productMapper.mapListDtoForFindAllFromListEntity(products);
    }

    public List<UserDtoForFindAll> findAll() {
        List<UserEntity> usersModel = userRepository.findAll();

        log.info("method findAll - number of users found  = {} ", usersModel.size());
        return userMapper.mapListUserDtoForFindAllFromListModel(usersModel);
    }

    @Transactional
    public UserFullDtoOut update(Long id, UserForUpdate dto) {

        Optional<UserEntity> userById = userRepository.findById(id);
        var user = userById.orElseThrow(
                () -> new NotFoundModelException(String.format("the country with id = %s not found", dto)));

        user.setId(id);
        user.setUsername(dto.getUsername());
        user.setEmail(dto.getEmail());
        var updatedUser = userRepository.save(user);

        log.info("method update - the user {} updated", updatedUser.getUsername());

        return userMapper.mapUserFullDtoFromModel(updatedUser);
    }

    public UserEntity findByName(String email) {
        Optional<UserEntity> userByName = userRepository.findByUsername(email);

        var entity = userByName.orElseThrow(
                () -> new NotFoundModelException(String.format("User with username = %s not found", email))
        );
        log.info("method findByName -  User with username = {} found", entity.getUsername());
        return entity;
    }

    public UserEntity findUserByIdWhichWillReturnModel(Long id) {
        return userRepository.findById(id).orElseThrow(
                () -> new NotFoundModelException("User with id = " + id + "not found"));
    }

    public Long getUserIdFromToken(String authorization) {
        String token = authorization.substring(7);
        String userIdFromJWT = jwtUtils.getUserIdFromJWT(token);
        return Long.parseLong(userIdFromJWT);
    }

    private void validateAlreadyExists(RegistrationForm dto) {
        Optional<UserEntity> entity = userRepository.findByEmail(dto.getEmail());

        if (entity.isPresent()) {
            throw new DuplicateException(String.format("User with email = %s already exist", dto.getEmail()));
        }
    }
}