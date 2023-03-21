package by.karpovich.shop.jpa.repository;

import by.karpovich.shop.jpa.entity.StatusUser;
import by.karpovich.shop.jpa.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long> {

    Optional<UserEntity> findByUsername(String username);

    Optional<UserEntity> findByEmail(String email);

    @Modifying(clearAutomatically = true, flushAutomatically = true)
    @Query("UPDATE UserEntity u " +
            " SET u.statusUser = :status " +
            " WHERE u.id = :userId")
    void setStatus(Long userId, StatusUser status);

    @Modifying(clearAutomatically = true, flushAutomatically = true)
    @Transactional
    @Query("UPDATE UserEntity u " +
            " SET u.balance = u.balance - :productPrice " +
            " WHERE u.id = :userId")
    void decrementBalance(Long userId, Double productPrice);

    @Modifying(clearAutomatically = true, flushAutomatically = true)
    @Query("UPDATE UserEntity u" +
            " SET u.balance =  u.balance + :bonusBalance" +
            " WHERE u.id = :userId"
    )
    void addBalance(Long userId, Double bonusBalance);
}
