package com.gideon.realtimetextcall.Repository;

import com.gideon.realtimetextcall.Domain.Room;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RoomRepository extends CrudRepository<Room, Long> {

    List<Room> findByName(String name);

}