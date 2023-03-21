package by.karpovich.shop.jpa.repository;

import by.karpovich.shop.jpa.entity.NotificationEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NotificationRepository extends JpaRepository<NotificationEntity, Long> {

//    List<NotificationEntity> findAllByUserId(Long userId);
}
