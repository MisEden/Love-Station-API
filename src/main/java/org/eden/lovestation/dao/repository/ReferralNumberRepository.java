package org.eden.lovestation.dao.repository;

import org.eden.lovestation.dao.model.ReferralNumber;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ReferralNumberRepository extends JpaRepository<ReferralNumber, String> {

    Long countByIdContains(String prefix);

    void deleteByIdentityCard(String identityCard);

    Optional<ReferralNumber> findByIdentityCard(String identityCard);

    Boolean existsByIdentityCard(String identityCard);
}
