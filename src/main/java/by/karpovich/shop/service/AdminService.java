package by.karpovich.shop.service;

import by.karpovich.shop.api.dto.notification.NotificationDtoForSend;
import by.karpovich.shop.exception.IncorrectAmountException;
import by.karpovich.shop.exception.NotFoundModelException;
import by.karpovich.shop.jpa.entity.NotificationEntity;
import by.karpovich.shop.jpa.entity.OrganizationEntity;
import by.karpovich.shop.jpa.entity.StatusOrganization;
import by.karpovich.shop.jpa.entity.StatusUser;
import by.karpovich.shop.jpa.repository.NotificationRepository;
import by.karpovich.shop.jpa.repository.OrganizationRepository;
import by.karpovich.shop.jpa.repository.ProductRepository;
import by.karpovich.shop.jpa.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class AdminService {

    private final UserRepository userRepository;
    private final OrganizationRepository organizationRepository;
    private final ProductRepository productRepository;
    private final NotificationRepository notificationRepository;
    private final UserService userService;

    @Transactional
    public void doProductValid(Long productId) {
        if (productRepository.findById(productId).isPresent()) {
            productRepository.doValidProduct(productId);
        } else {
            throw new NotFoundModelException(String.format("Product with id = %s not found", productId));
        }
    }

    @Transactional
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

    @Transactional
    public void setUserStatus(Long userId, StatusUser status) {
        if (userRepository.findById(userId).isPresent()) {
            userRepository.setStatus(userId, status);
        } else {
            throw new NotFoundModelException(String.format("User with id = %s not found", userId));
        }
    }

    public List<Long> findNotValidOrganization() {
        return organizationRepository.findAll()
                .stream().filter(status -> status.getStatus().equals(StatusOrganization.NOT_VALID))
                .map(OrganizationEntity::getId)
                .collect(Collectors.toList());
    }

    @Transactional
    public void setOrganizationStatus(Long organizationId, StatusOrganization status) {
        if (organizationRepository.findById(organizationId).isPresent()) {
            organizationRepository.setStatus(organizationId, status);
        } else {
            throw new NotFoundModelException(String.format("Organization with id = %s not found", organizationId));
        }
    }

    @Transactional
    public void sendNotification(NotificationDtoForSend dto) {
        NotificationEntity entity = new NotificationEntity();
        entity.setName(dto.getName());
        entity.setDateOfCreation(Instant.now());
        entity.setMessage(dto.getMessage());
        for (Long id : dto.getUsersId()) {
            entity.setUser(userService.findUserByIdWhichWillReturnModel(id));
        }
        notificationRepository.save(entity);
    }
}

