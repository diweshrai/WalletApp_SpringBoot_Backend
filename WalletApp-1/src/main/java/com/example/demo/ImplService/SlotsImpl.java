package com.example.demo.ImplService;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.Model.Slots;
import com.example.demo.Repo.SlotsRepo;
import com.example.demo.Service.SlotsService;

@Service
public class SlotsImpl implements SlotsService {

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
	public Slots findbySlotName(String slotName) {
		Slots s = srepo.findById(slotName).get();

		return s;
	}

}
