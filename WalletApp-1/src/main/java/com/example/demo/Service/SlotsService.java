package com.example.demo.Service;

import com.example.demo.Model.Slots;

import java.util.List;

public interface SlotsService {

    public List<Slots> getAllSlots();

    public Slots insertSlot(Slots s);

    public Slots findBySlotName(String slotName);

}
