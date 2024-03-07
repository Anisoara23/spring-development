package org.example.web;

import org.example.data.model.Customer;
import org.example.data.model.Order;
import org.example.data.repo.CustomerRepo;
import org.example.data.repo.OrderRepo;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.server.ResponseStatusException;

import java.security.Principal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicBoolean;

@Controller
@RequestMapping("customers")
public class CustomerController {

    private final CustomerRepo customerRepository;
    private final OrderRepo orderRepository;

    public CustomerController(CustomerRepo customerRepository, OrderRepo orderRepository) {
        this.customerRepository = customerRepository;
        this.orderRepository = orderRepository;
    }


    @GetMapping
    public String getAllUsers(Model model){
        Iterable<Customer> customersIterable = this.customerRepository.findAll();
        List<Customer> customers = new ArrayList<>();
        customersIterable.forEach(customers::add);
        customers.sort(new Comparator<Customer>() {
            @Override
            public int compare(Customer o1, Customer o2) {
                return o1.getName().compareTo(o2.getName());
            }
        });
        model.addAttribute("customers", customers);
        model.addAttribute("module", "customers");
        return "customers";
    }

    @GetMapping(path="/{id}")
    public String getUser(@PathVariable("id")long customerId, Principal principal, Model model){
        Optional<Customer> customer = this.customerRepository.findById(customerId);
        if (customer.isEmpty()) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "entity not found"
            );
        }
        model.addAttribute("customer", customer.get());
        List<Order> orders = new ArrayList<>();
        if (principal instanceof UsernamePasswordAuthenticationToken) {
            AtomicBoolean auth = new AtomicBoolean(false);
            Collection<GrantedAuthority> authorities = ((UsernamePasswordAuthenticationToken) principal).getAuthorities();
            authorities.forEach(authority -> {
                        if (authority.getAuthority().equals("ROLE_ADMIN")) {
                            auth.set(true);
                        }
                    }
            );
            if (auth.get()) {
                Iterable<Order> ordersIterable = this.orderRepository.findAllByCustomerId(customer.get().getId());
                ordersIterable.forEach(orders::add);
            }
        }
        model.addAttribute("orders", orders);
        model.addAttribute("module", "customers");
        return "detailed_customer";
    }
}
