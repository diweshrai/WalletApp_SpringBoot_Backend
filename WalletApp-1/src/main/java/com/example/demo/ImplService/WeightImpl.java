package com.example.demo.ImplService;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.Dto.WeightDto;
import com.example.demo.Model.Slots;
import com.example.demo.Model.Weight;
import com.example.demo.Repo.SlotsRepo;
import com.example.demo.Repo.WeightRepo;
import com.example.demo.Service.WeightService;

@Service
public class WeightImpl implements WeightService {

	@Autowired
	WeightRepo wrepo;

	@Autowired
	SlotsRepo srepo;

	@Override
	public List<Weight> getallWeights() {

		return wrepo.findAll();
	};

	@Override
	public Weight insertWeight(Weight w) {
		// TODO Auto-generated method stub
		return wrepo.save(w);
	};

	@Override
	public String processAndInsertData(List<WeightDto> weightInputs) {

		List<Weight> weightList = new ArrayList<>();

		for (WeightDto input : weightInputs) {

			System.out.println(input);

			String slotName = input.getSlotname(); // Convert to lowercase for case-insensitive matching
			Slots slot = srepo.findBySlotname(slotName);

			if (slot != null) {
				Weight weight = new Weight();
				weight.setCustid(input.getCustid());
				weight.setSlots(slot.getSlotid());
				weight.setWeight(input.getWeight());

				weightList.add(weight);
			}
		}

		wrepo.saveAll(weightList);

		return "done";
	};

}
