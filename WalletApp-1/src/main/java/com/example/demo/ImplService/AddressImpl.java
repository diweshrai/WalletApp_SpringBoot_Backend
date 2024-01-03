package com.example.demo.ImplService;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.Dto.CustomerDto;
import com.example.demo.Model.Address;
import com.example.demo.Repo.AddressRepo;
import com.example.demo.Service.AddressService;

@Service
public class AddressImpl implements AddressService {

	@Autowired
	AddressRepo arepo;

	@Override
	public CustomerDto addAddress(CustomerDto cd) {

		return null;
	}

	@Override
	public CustomerDto getAddress() {
		// TODO Auto-generated method stub
		return null;
	}

	@SuppressWarnings("unused")
	private Address DtoToAddress(CustomerDto cd) {
		Address ad = new Address();
		ad.setAddressLine1(cd.getAddressLine1());
		ad.setAdressLine2(cd.getAdressLine2());
		ad.setCity(cd.getCity());
		ad.setState(cd.getState());
		ad.setPincode(cd.getPincode());

		return ad;

	}

	@SuppressWarnings("unused")
	private CustomerDto addressToDto(Address sAd) {
		CustomerDto cd = new CustomerDto();
		cd.setAddressLine1(sAd.getAddressLine1());
		cd.setAdressLine2(sAd.getAdressLine2());
		cd.setCity(sAd.getCity());
		cd.setState(sAd.getState());
		cd.setPincode(sAd.getPincode());
		return cd;
	}

	@Override
	public List<Address> addline2isNUll() {

		return arepo.findByAdressLine2IsNull();

	};

	@Override
	public List<Address> addline2isNotNull() {

		return arepo.findByAdressLine2IsNotNull();

	};

}
