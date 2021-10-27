package org.eden.lovestation.dao.repository;

import org.eden.lovestation.dao.model.CheckinForm;
import org.eden.lovestation.dao.model.CheckinFormConfirm;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CheckinFormConfirmRepository extends JpaRepository<CheckinFormConfirm,String> {
    Optional<CheckinFormConfirm> findByCheckinForm(CheckinForm checkinForm);
}
