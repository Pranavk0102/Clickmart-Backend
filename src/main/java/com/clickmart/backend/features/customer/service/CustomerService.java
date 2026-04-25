package com.clickmart.backend.features.customer.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.clickmart.backend.entity.User;
import com.clickmart.backend.enums.Role;
import com.clickmart.backend.exceptions.BadRequestException;
import com.clickmart.backend.exceptions.ResourceNotFoundException;
import com.clickmart.backend.features.auth.dto.UserDTO;
import com.clickmart.backend.features.auth.repository.UserRepository;
import com.clickmart.backend.features.customer.dto.AdminUpdateCustomerRequest;
import com.clickmart.backend.features.notification.service.NotificationService;

@Service
@Transactional
public class CustomerService {

    private final UserRepository userRepository;
    private final NotificationService notificationService;

    @Autowired
    public CustomerService(UserRepository userRepository,
                               NotificationService notificationService) {
        this.userRepository = userRepository;
        this.notificationService = notificationService;
    }

    @Transactional(readOnly = true)
    public Page<UserDTO> getAllCustomers(String query, int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("createdAt").descending());
        Page<User> customers = (query != null && !query.isBlank())
                ? userRepository.searchByRole(Role.CUSTOMER, query, pageable)
                : userRepository.findByRole(Role.CUSTOMER, pageable);
        return customers.map(this::toDTO);
    }

    @Transactional(readOnly = true)
    public UserDTO getCustomer(Long id) {
        User user = findCustomer(id);
        return toDTO(user);
    }

    public UserDTO updateCustomer(Long id, AdminUpdateCustomerRequest request) {
        User user = findCustomer(id);

        if (!user.getEmail().equalsIgnoreCase(request.getEmail()) && userRepository.existsByEmail(request.getEmail())) {
            throw new BadRequestException("Email already registered");
        }

        if (!user.getPhone().equals(request.getPhone()) && userRepository.existsByPhone(request.getPhone())) {
            throw new BadRequestException("Phone number already registered");
        }

        user.setFirstName(request.getFirstName());
        user.setLastName(request.getLastName());
        user.setEmail(request.getEmail());
        user.setPhone(request.getPhone());
        if (request.getActive() != null) {
            user.setActive(request.getActive());
        }

        return toDTO(userRepository.save(user));
    }

    public UserDTO toggleActive(Long id) {
        User user = findCustomer(id);
        user.setActive(!Boolean.TRUE.equals(user.getActive()));
        User saved = userRepository.save(user);
        notificationService.createNotification(saved,
                "Your account is now " + (Boolean.TRUE.equals(saved.getActive()) ? "active" : "inactive") + ".",
                "SYSTEM");
        return toDTO(saved);
    }

    private User findCustomer(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Customer not found"));
        if (user.getRole() != Role.CUSTOMER) {
            throw new ResourceNotFoundException("Customer not found");
        }
        return user;
    }

    private UserDTO toDTO(User user) {
        UserDTO dto = new UserDTO();
        dto.setId(user.getId());
        dto.setFirstName(user.getFirstName());
        dto.setLastName(user.getLastName());
        dto.setEmail(user.getEmail());
        dto.setPhone(user.getPhone());
        dto.setRole(user.getRole() != null ? user.getRole().name() : Role.CUSTOMER.name());
        dto.setActive(user.getActive());
        dto.setCreatedAt(user.getCreatedAt());
        return dto;
    }
}
