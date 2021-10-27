package org.eden.lovestation.dao.repository;

import org.eden.lovestation.dao.model.User;
import org.eden.lovestation.dto.projection.UserRegisterVerification;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, String>, JpaSpecificationExecutor<User> {
    Optional<User> findByIdAndVerified(String id, Boolean verified);

    Optional<User> findByIdAndVerifiedIsNotNull(String id);

    Optional<User> findByAccountAndVerified(String account, Boolean verified);

    Optional<User> findByIdentityCardAndVerified(String identityCard, Boolean verified);

    @Query(value = "SELECT chinese_name FROM users where chinese_name like N'%' + ?1 + '%'", nativeQuery = true)
    List<String> findAllChineseNameByFilter(String chineseNameFilter);

    Boolean existsByAccount(String account);

    Boolean existsByAccountAndVerifiedIsTrue(String account);

    Boolean existsByEmail(String email);

    Boolean existsByEmailAndVerifiedIsTrue(String email);

    Boolean existsByLineId(String lineId);

    Boolean existsByLineIdAndVerifiedIsTrue(String lineId);

    Boolean existsByIdentityCard(String identityCard);

    Boolean existsByIdentityCardAndVerifiedIsTrue(String identityCard);

    Boolean existsByIdentityCardAndVerified(String identityCard, Boolean verified);

    void deleteById(String id);

    Optional<User> findByEmailAndVerified(String email, Boolean verified);

    @Modifying
    @Query("update User u set u.password = ?1 where u.id = ?2")
    void updatePasswordById(String password, String id);

    Page<UserRegisterVerification> findAllByVerifiedIsNull(Pageable pageable);

    @Modifying
    @Query("update User u set u.verified = ?1 where u.id = ?2")
    void updateVerificationById(Boolean verification, String id);

    @Modifying
    @Query("update User u set u.lineId = ?1 where u.id = ?2")
    void updateLineIdById(String newLineId, String id);
}
