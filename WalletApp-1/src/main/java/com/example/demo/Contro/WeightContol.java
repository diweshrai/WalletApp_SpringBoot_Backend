package com.example.demo.Contro;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.Dto.WeightDto;
import com.example.demo.Model.Slots;
import com.example.demo.Model.Weight;
import com.example.demo.Service.SlotsService;
import com.example.demo.Service.WeightService;

@RestController
@CrossOrigin(origins = "*")
public class WeightContol {

	@Autowired
	WeightService wserv;

	@GetMapping("/getAllWeight")
	public List<Weight> getallWeight() {
		return wserv.getallWeights();
	};

	@PostMapping("/AddWeightBy")
	public String addbyWeight(@RequestBody List<WeightDto> w) {

		return wserv.processAndInsertData(w);

	}

	@PostMapping("/addWeight")
	public Weight addWeight(@RequestBody Weight w) {
		return wserv.insertWeight(w);
	}

//****************************************************************	

	@Autowired
	SlotsService sserv;

	@GetMapping("/getAllSlots")
	public List<Slots> getallSlots() {
		return sserv.getAllSlots();
	};

	@PostMapping("/addSlots")
	public Slots addSlots(@RequestBody Slots s) {
		return sserv.insertSlot(s);
	}

}
