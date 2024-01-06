package com.example.demo.ImplService;

import com.example.demo.Model.Slots;
import com.example.demo.Repo.SlotsRepo;
import com.example.demo.Service.SlotsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SlotsServiceImplementation implements SlotsService {

    @Autowired
    SlotsRepo srepo;

    @Override
    public List<Slots> getAllSlots() {

        return srepo.findAll();
    }

    @Override
    public Slots insertSlot(Slots s) {

        return srepo.save(s);
    }

    @Override
    public Slots findBySlotName(String slotName) {
        Slots s = srepo.findById(slotName).get();

        return s;
    }

}
