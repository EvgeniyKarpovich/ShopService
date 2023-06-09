package by.karpovich.shop.jpa.repository;

import by.karpovich.shop.jpa.entity.ProductEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<ProductEntity, Long> {

    @Modifying(clearAutomatically = true, flushAutomatically = true)
    @Query("""
            UPDATE ProductEntity p SET p.isValid = true WHERE p.id = :productId
            """)
    void doValidProduct(Long productId);

    @Query("""
            SELECT p from ProductEntity p
             where p.isValid = true
            """)
    List<ProductEntity> findAllValidProducts();

    @Modifying(clearAutomatically = true, flushAutomatically = true)
    @Transactional
    @Query("""
            UPDATE ProductEntity p 
            SET p.quantity = p.quantity - 1   
            WHERE p.id = :productId
               """)
    void decrementQuantityProduct(Long productId);

    @Modifying(clearAutomatically = true, flushAutomatically = true)
    @Transactional
    @Query("UPDATE ProductEntity p " +
            " SET p.quantity = p.quantity - 1 " +
            " WHERE p.id = :productId")
    void incrementQuantity(Long productId);

    Optional<ProductEntity> findByName(String name);

}
