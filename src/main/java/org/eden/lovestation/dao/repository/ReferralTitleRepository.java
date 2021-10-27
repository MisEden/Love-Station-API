package org.eden.lovestation.dao.repository;

import org.eden.lovestation.dao.model.ReferralTitle;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReferralTitleRepository extends JpaRepository<ReferralTitle, String> {
}
