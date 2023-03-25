package by.karpovich.shop.jpa.repository;

import by.karpovich.shop.jpa.entity.CommentEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentRepository extends JpaRepository<CommentEntity, Long> {

    List<CommentEntity> findByProductId(Long productId);
}
