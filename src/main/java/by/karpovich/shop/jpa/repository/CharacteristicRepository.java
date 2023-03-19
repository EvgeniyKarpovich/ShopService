package by.karpovich.shop.jpa.repository;

import by.karpovich.shop.jpa.entity.CharacteristicEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CharacteristicRepository extends JpaRepository<CharacteristicEntity, Long> {
}
