package com.example.demo.Service;

import java.util.List;

import com.example.demo.Model.Slots;

public interface SlotsService {

	public List<Slots> getAllSlots();

	public Slots insertSlot(Slots s);

	public Slots findbySlotName(String slotName);

}
