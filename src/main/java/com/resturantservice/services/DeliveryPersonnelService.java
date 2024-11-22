/**
 * 
 */
package com.resturantservice.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.resturantservice.entities.DeliveryPersonnel;
import com.resturantservice.entities.Orders;
import com.resturantservice.entities.User;
import com.resturantservice.entities.OrderStatus;
import com.resturantservice.repos.DeliveryPersonnelRepository;
import com.resturantservice.repos.OrderRepository;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

@Service
public class DeliveryPersonnelService {
    private final DeliveryPersonnelRepository personnelRepository;
    private final OrderRepository orderRepository;
    
    @Autowired
    private ServiceInvoker serviceInvoker;

    public DeliveryPersonnelService(DeliveryPersonnelRepository personnelRepository,
                                    OrderRepository orderRepository) {
        this.personnelRepository = personnelRepository;
        this.orderRepository = orderRepository;
    }

    // Register
    public DeliveryPersonnel register(DeliveryPersonnel personnel) {
        return personnelRepository.save(personnel);
    }

    // Login
    public String login(String username, String password) {
    	User loginUser = new User();
    	loginUser.setUsername(username);
    	loginUser.setPassword(password);
    	
    	return serviceInvoker.login(loginUser);
    }

    // View Available Deliveries
    public List<Orders> viewAvailableDeliveries() {
        return orderRepository.findByStatus("available");
    }

    // Accept Order
    public Orders acceptOrder(Long orderId, Long personnelId) {
        Orders order = orderRepository.findById(orderId)
                .orElseThrow(() -> new IllegalArgumentException("Order not found"));

        if (!OrderStatus.AVAILABLE.equals(order.getStatus())) {
            throw new IllegalStateException("Order is no longer available for delivery");
        }

        DeliveryPersonnel personnel = personnelRepository.findById(personnelId)
                .orElseThrow(() -> new IllegalArgumentException("Delivery personnel not found"));

        order.setDeliverypersonnel(personnel);
        order.setStatus(OrderStatus.ACCEPTED);
        return orderRepository.save(order);
    }

    // Track Delivery Status
    public Orders updateDeliveryStatus(Long orderId, OrderStatus status) {
        Orders order = orderRepository.findById(orderId)
                .orElseThrow(() -> new IllegalArgumentException("Order not found"));

        order.setStatus(status);
        return orderRepository.save(order);
    }

    // Manage Availability
    public DeliveryPersonnel updateAvailability(Long personnelId, boolean available) {
        DeliveryPersonnel personnel = personnelRepository.findById(personnelId)
                .orElseThrow(() -> new IllegalArgumentException("Delivery personnel not found"));

        personnel.setAvailable(available);
        return personnelRepository.save(personnel);
    }
}

