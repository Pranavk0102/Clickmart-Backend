package com.clickmart.backend.features.address.controller;

import com.clickmart.backend.dto.ApiResponse;
import com.clickmart.backend.features.address.dto.AddressDTO;
import com.clickmart.backend.features.address.dto.AddressRequest;
import com.clickmart.backend.features.address.service.AddressService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/addresses")
public class AddressController {

    private final AddressService addressService;

    @Autowired
    public AddressController(AddressService addressService) {
        this.addressService = addressService;
    }

    
    @GetMapping
    public ResponseEntity<ApiResponse<List<AddressDTO>>> getMyAddresses() {
        return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse<>(true, "Addresses fetched", addressService.getMyAddresses()));
    }

    
    @PostMapping
    public ResponseEntity<ApiResponse<AddressDTO>> addAddress(@Valid @RequestBody AddressRequest req) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new ApiResponse<>(true, "Address added", addressService.addAddress(req)));
    }

    
    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<AddressDTO>> updateAddress(
            @PathVariable("id") Long id, @Valid @RequestBody AddressRequest req) {
        return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse<>(true, "Address updated", addressService.updateAddress(id, req)));
    }

    
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteAddress(@PathVariable("id") Long id) {
        addressService.deleteAddress(id);
        return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse<>(true, "Address deleted", null));
    }

    
    @PatchMapping("/{id}/default")
    public ResponseEntity<ApiResponse<AddressDTO>> setDefault(@PathVariable("id") Long id) {
        return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse<>(true, "Default address updated", addressService.setDefault(id)));
    }
}
