package dev.wcs.nad.tariffmanager.adapter.rest;

import dev.wcs.nad.tariffmanager.adapter.rest.dto.customer.AddressDto;
import dev.wcs.nad.tariffmanager.adapter.rest.dto.customer.CreateCustomerDto;
import dev.wcs.nad.tariffmanager.adapter.rest.dto.customer.CustomerDto;
import dev.wcs.nad.tariffmanager.mapper.mapstruct.EntityToDtoMapper;
import dev.wcs.nad.tariffmanager.persistence.entity.Customer;
import dev.wcs.nad.tariffmanager.service.CustomerService;
import dev.wcs.nad.tariffmanager.mapper.simple.CustomerMapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
public class CustomerController {

    private final CustomerService customerService;

    private final CustomerMapper customerMapper;

    @Autowired
    // erreur sur le C de CustomerMapper customerMapper (symbole) pareil pour celui
    // du dessus cannot find symbol

    public CustomerController(CustomerService customerService, CustomerMapper customerMapper) {
        this.customerService = customerService;
        this.customerMapper = customerMapper;
    }

    @GetMapping("/api/customers")
    public List<CustomerDto> displayCustomers() {
        List<CustomerDto> customerDtos = new ArrayList<>();
        for (Customer customer : customerService.readAllCustomers()) {
            customerDtos.add(customerMapper.convertEntityToDto(customer));
        }
        return customerDtos;
    }

    @PostMapping("/api/customers")
    public ResponseEntity<CustomerDto> createCustomer(@RequestBody CreateCustomerDto createCustomerDto) {
        CustomerDto customerDto = customerMapper.convertEntityToDto(
                customerService.createCustomer(customerMapper.convertDtoToEntity(createCustomerDto)));
        return ResponseEntity.ok(customerDto);
    }

    @PutMapping("/api/customers/{id}")
    public ResponseEntity<CustomerDto> assignAddress(@PathVariable("id") Long customerId,
            @RequestBody AddressDto addressDto) {
        Customer customerEntity = customerService.assignAddress(customerId, customerMapper.mapAddressDto(addressDto));
        return ResponseEntity.ok(customerMapper.convertEntityToDto(customerEntity));
    }
}
