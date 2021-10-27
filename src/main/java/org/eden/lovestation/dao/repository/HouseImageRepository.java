package org.eden.lovestation.dao.repository;

import org.eden.lovestation.dao.model.HouseImage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HouseImageRepository extends JpaRepository<HouseImage, String> {
}
