package com.clickmart.backend.features.delivery.service;

import com.clickmart.backend.entity.DeliveryOption;
import com.clickmart.backend.exceptions.BadRequestException;
import com.clickmart.backend.exceptions.ResourceNotFoundException;
import com.clickmart.backend.features.delivery.dto.DeliveryOptionDTO;
import com.clickmart.backend.features.delivery.dto.DeliveryOptionRequest;
import com.clickmart.backend.features.delivery.repository.DeliveryOptionRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class DeliveryService {

    private final DeliveryOptionRepository deliveryOptionRepository;

    @Autowired
    public DeliveryService(DeliveryOptionRepository deliveryOptionRepository) {
        this.deliveryOptionRepository = deliveryOptionRepository;
    }

    @Transactional(readOnly = true)
    public List<DeliveryOptionDTO> getActiveOptions() {
        List<DeliveryOption> options = deliveryOptionRepository.findByActiveTrueOrderByPriceAsc();
        List<DeliveryOptionDTO> result = new ArrayList<>();
        for (DeliveryOption option : options) {
            result.add(toDTO(option));
        }
        return result;
    }

    @Transactional(readOnly = true)
    public List<DeliveryOptionDTO> getAllOptions() {
        List<DeliveryOption> options = deliveryOptionRepository.findAll();
        List<DeliveryOptionDTO> result = new ArrayList<>();
        for (DeliveryOption option : options) {
            result.add(toDTO(option));
        }
        return result;
    }

    public DeliveryOptionDTO create(DeliveryOptionRequest req) {
        if (deliveryOptionRepository.existsByNameIgnoreCase(req.getName()))
            throw new BadRequestException("A delivery option with this name already exists");
        DeliveryOption option = new DeliveryOption();
        applyRequest(req, option);
        return toDTO(deliveryOptionRepository.save(option));
    }

    public DeliveryOptionDTO update(Long id, DeliveryOptionRequest req) {
        DeliveryOption option = deliveryOptionRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Delivery option not found"));
        if (!option.getName().equalsIgnoreCase(req.getName())
                && deliveryOptionRepository.existsByNameIgnoreCase(req.getName()))
            throw new BadRequestException("A delivery option with this name already exists");
        applyRequest(req, option);
        return toDTO(deliveryOptionRepository.save(option));
    }

    public void delete(Long id) {
        DeliveryOption option = deliveryOptionRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Delivery option not found"));
        option.setActive(false);
        deliveryOptionRepository.save(option);
    }

    private void applyRequest(DeliveryOptionRequest req, DeliveryOption option) {
        option.setName(req.getName());
        option.setLabel(req.getLabel());
        option.setDescription(req.getDescription());
        option.setPrice(req.getPrice());
        if (req.getActive() != null) {
            option.setActive(req.getActive());
        }
    }

    private DeliveryOptionDTO toDTO(DeliveryOption option) {
        DeliveryOptionDTO dto = new DeliveryOptionDTO();
        BeanUtils.copyProperties(option, dto);
        return dto;
    }
}
