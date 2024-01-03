package com.example.demo.Repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.Model.Slots;

public interface SlotsRepo extends JpaRepository<Slots, String> {

	public Slots findBySlotname(String slotname);

}
