package com.example.demo.Repo;

import com.example.demo.Model.Slots;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SlotsRepo extends JpaRepository<Slots, String> {

    public Slots findBySlotname(String slotname);

}
