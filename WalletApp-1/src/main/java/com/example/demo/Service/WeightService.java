package com.example.demo.Service;

import java.util.List;

import com.example.demo.Dto.WeightDto;
import com.example.demo.Model.Weight;

public interface WeightService {

	public List<Weight> getallWeights();

	public Weight insertWeight(Weight w);

	public String processAndInsertData(List<WeightDto> weightInputs);
}
