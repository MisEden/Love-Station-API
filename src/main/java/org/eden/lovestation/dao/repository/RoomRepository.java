package org.eden.lovestation.dao.repository;

import org.eden.lovestation.dao.model.House;
import org.eden.lovestation.dao.model.Room;
import org.eden.lovestation.dto.projection.HouseRoomId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RoomRepository extends JpaRepository<Room, String> {
    Optional<Room> findById(String id);

    List<Room> findAllByNumber(int number);

    @Query(value = "SELECT r.* FROM rooms r WHERE r.house_id = ?1 ORDER BY number", nativeQuery = true)
    List<HouseRoomId> findAllByHouse(String houseId);

    @Query(value = "SELECT r.* FROM rooms r WHERE r.house_id = ?1 AND r.disable = 0 ORDER BY number", nativeQuery = true)
    List<HouseRoomId> findAllByHouseWithoutDisabled(String houseId);

    @Query(value = "SELECT COUNT(r.id) FROM rooms r WHERE r.number = ?1", nativeQuery = true)
    int countByNumber(int number);

    Optional<Room> findByHouseAndNumber(House house, int roomNumber);
}
