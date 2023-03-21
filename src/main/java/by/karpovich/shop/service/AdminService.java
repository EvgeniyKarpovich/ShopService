package by.karpovich.shop.service;

import by.karpovich.shop.api.dto.notification.NotificationDto;
import by.karpovich.shop.exception.IncorrectAmount;
import by.karpovich.shop.exception.NotFoundModelException;
import by.karpovich.shop.jpa.entity.NotificationEntity;
import by.karpovich.shop.jpa.entity.StatusOrganization;
import by.karpovich.shop.jpa.entity.StatusUser;
import by.karpovich.shop.jpa.repository.OrganizationRepository;
import by.karpovich.shop.jpa.repository.ProductRepository;
import by.karpovich.shop.jpa.repository.UserRepository;
import by.karpovich.shop.mapping.NotificationMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class AdminService {

    private final UserRepository userRepository;
    private final OrganizationRepository organizationRepository;
    private final ProductRepository productRepository;
    private final NotificationService notificationService;
    private final NotificationMapper notificationMapper;

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
            throw new IncorrectAmount("Sum must be more 0");
        }
        if (userRepository.findById(userId).isPresent()) {
            userRepository.addBalance(userId, sum);
        } else {
            throw new NotFoundModelException(String.format("User with id = %s not found", userId));
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

    @Transactional
    public void setOrganizationStatus(Long organizationId, StatusOrganization status) {
        if (organizationRepository.findById(organizationId).isPresent()) {
            organizationRepository.setStatus(organizationId, status);
        } else {
            throw new NotFoundModelException(String.format("Organization with id = %s not found", organizationId));
        }
    }

    @Transactional
    public void sendNotification(Long userId, Long notificationId) {
        var notificationEntity = notificationService.findNotificationByIdWhichWillReturnModel(notificationId);
        var userEntity = userRepository.findById(userId).orElseThrow(
                () -> new NotFoundModelException(String.format("Organization with id = %s not found", userId)));

        List<NotificationEntity> notifications = userEntity.getNotifications();
        notifications.add(notificationEntity);
        userRepository.save(userEntity);
    }
}

