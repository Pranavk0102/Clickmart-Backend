package com.clickmart.backend.features.address.service;

import com.clickmart.backend.exceptions.ResourceNotFoundException;

import com.clickmart.backend.config.SecurityUtils;
import com.clickmart.backend.entity.Address;
import com.clickmart.backend.entity.User;

import com.clickmart.backend.features.address.dto.AddressDTO;
import com.clickmart.backend.features.address.dto.AddressRequest;
import com.clickmart.backend.features.address.repository.AddressRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class AddressService {

    private final AddressRepository addressRepository;
    private final SecurityUtils securityUtils;

    @Autowired
    public AddressService(AddressRepository addressRepository,
                               SecurityUtils securityUtils) {
        this.addressRepository = addressRepository;
        this.securityUtils = securityUtils;
    }

    public List<AddressDTO> getMyAddresses() {
        List<Address> addresses = addressRepository.findByUserIdOrderByIsDefaultDescCreatedAtDesc(securityUtils.getCurrentUserId());
        List<AddressDTO> result = new ArrayList<>();
        for (Address address : addresses) {
            result.add(toDTO(address));
        }
        return result;
    }

    public AddressDTO addAddress(AddressRequest req) {
        User user = securityUtils.getCurrentUser();
        Address address = new Address();
        applyRequest(req, address);
        address.setUser(user);
        
        if (addressRepository.countByUserId(user.getId()) == 0) {
            address.setIsDefault(true);
        }
        return toDTO(addressRepository.save(address));
    }

    public AddressDTO updateAddress(Long id, AddressRequest req) {
        Long userId = securityUtils.getCurrentUserId();
        Address address = addressRepository.findByIdAndUserId(id, userId)
                .orElseThrow(() -> new ResourceNotFoundException("Address not found"));
        applyRequest(req, address);
        return toDTO(addressRepository.save(address));
    }

    public void deleteAddress(Long id) {
        Long userId = securityUtils.getCurrentUserId();
        Address address = addressRepository.findByIdAndUserId(id, userId)
                .orElseThrow(() -> new ResourceNotFoundException("Address not found"));
        addressRepository.delete(address);
        
        if (Boolean.TRUE.equals(address.getIsDefault())) {
            List<Address> addresses = addressRepository.findByUserIdOrderByIsDefaultDescCreatedAtDesc(userId);
            if (addresses != null && !addresses.isEmpty()) {
                Address newDefault = addresses.get(0);
                newDefault.setIsDefault(true);
                addressRepository.save(newDefault);
            }
        }
    }

    public AddressDTO setDefault(Long id) {
        Long userId = securityUtils.getCurrentUserId();
        Address target = addressRepository.findByIdAndUserId(id, userId)
                .orElseThrow(() -> new ResourceNotFoundException("Address not found"));
        
        addressRepository.findByUserIdOrderByIsDefaultDescCreatedAtDesc(userId)
                .forEach(a -> { a.setIsDefault(false); addressRepository.save(a); });
        target.setIsDefault(true);
        return toDTO(addressRepository.save(target));
    }

    private void applyRequest(AddressRequest req, Address a) {
        BeanUtils.copyProperties(req, a);
    }

    public AddressDTO toDTO(Address a) {
        AddressDTO dto = new AddressDTO();
        BeanUtils.copyProperties(a, dto);
        return dto;
    }
}
