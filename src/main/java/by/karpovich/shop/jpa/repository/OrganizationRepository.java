package by.karpovich.shop.jpa.repository;

import by.karpovich.shop.jpa.entity.OrganizationEntity;
import by.karpovich.shop.jpa.entity.StatusOrganization;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface OrganizationRepository extends JpaRepository<OrganizationEntity, Long> {

    Optional<OrganizationEntity> findByName(String name);

    @Modifying(clearAutomatically = true, flushAutomatically = true)
    @Query("""
            UPDATE OrganizationEntity o 
            SET o.status = :status
            WHERE o.id = :organizationId
                     """)
    void setStatus(Long organizationId, StatusOrganization status);
}
