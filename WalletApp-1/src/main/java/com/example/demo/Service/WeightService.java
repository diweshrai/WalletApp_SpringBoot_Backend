package com.example.demo.Service;

import com.example.demo.Dto.WeightDto;
import com.example.demo.Model.Weight;

import java.util.List;

public interface WeightService {

    public List<Weight> getAllWeights();

    public Weight insertWeight(Weight w);

    public String processAndInsertData(List<WeightDto> weightInputs);
}
