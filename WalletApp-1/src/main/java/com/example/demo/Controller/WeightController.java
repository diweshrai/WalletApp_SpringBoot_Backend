package com.example.demo.Controller;

import com.example.demo.Dto.WeightDto;
import com.example.demo.Model.Slots;
import com.example.demo.Model.Weight;
import com.example.demo.Service.SlotsService;
import com.example.demo.Service.WeightService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "*")
public class WeightController {

    @Autowired
    WeightService wserv;
    @Autowired
    SlotsService sserv;

    ;

    @GetMapping("/getAllWeight")
    public List<Weight> getallWeight() {
        return wserv.getAllWeights();
    }

    @PostMapping("/AddWeightBy")
    public String addbyWeight(@RequestBody List<WeightDto> w) {

        return wserv.processAndInsertData(w);

    }

//****************************************************************	

    @PostMapping("/addWeight")
    public Weight addWeight(@RequestBody Weight w) {
        return wserv.insertWeight(w);
    }

    @GetMapping("/getAllSlots")
    public List<Slots> getallSlots() {
        return sserv.getAllSlots();
    }

    ;

    @PostMapping("/addSlots")
    public Slots addSlots(@RequestBody Slots s) {
        return sserv.insertSlot(s);
    }

}
