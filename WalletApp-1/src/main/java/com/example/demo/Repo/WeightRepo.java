package com.example.demo.Repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.Model.Weight;

public interface WeightRepo extends JpaRepository<Weight, Integer> {

}
