package com.resturantservice;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import com.resturantservice.entities.DeliveryPersonnel;
import com.resturantservice.entities.Orders;
import com.resturantservice.entities.OrderStatus;
import com.resturantservice.services.DeliveryPersonnelService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/api/v1/deliverypersonnel")
@Tag(name = "Delivery Personnel API", description = "Endpoints for managing delivery and Delivery Personnel Functionalities")
public class DeliveryPersonnelController {

	@Autowired
    private DeliveryPersonnelService personnelService;

    // Register
    @PostMapping(value="/register", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Register Delivery Personnel", description = "Register Delivery Personnel.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully registered the Delivery Personnel."),
        @ApiResponse(responseCode = "400", description = "Invalid Delivery Personnel Data."),
        @ApiResponse(responseCode = "500", description = "Internal server error.")
    })
    public DeliveryPersonnel register(@RequestBody DeliveryPersonnel personnel) {
        return personnelService.register(personnel);
    }

    // Login
    @PostMapping(value="/login", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Login Delivery Personnel", description = "Login Delivery Personnel.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully logged in the Delivery Personnel."),
        @ApiResponse(responseCode = "403", description = "Forbidden."),
        @ApiResponse(responseCode = "500", description = "Internal server error.")
    })
    public String login(HttpServletRequest request) {
        return null;//personnelService.login(request);
    }

    // View Available Deliveries
    @GetMapping(value="/deliveries", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "View Available Deliveries", description = "View Available Deliveries.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully viewed available Deliveries."),
        @ApiResponse(responseCode = "500", description = "Internal server error.")
    })
    public List<Orders> viewAvailableDeliveries() {
        return personnelService.viewAvailableDeliveries();
    }

    // Accept Order
    @PostMapping(value="/accept/{orderId}", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Accept Order", description = "Accept Order.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully Accepted Order."),
        @ApiResponse(responseCode = "404", description = "OrderId Not Found."),
        @ApiResponse(responseCode = "500", description = "Internal server error.")
    })
    public Orders acceptOrder(@PathVariable Long orderId, @RequestParam Long personnelId) {
        return personnelService.acceptOrder(orderId, personnelId);
    }

    // Track Delivery Status
    @PatchMapping(value="/status/{orderId}", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Track Delivery Status", description = "Track Delivery Status.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully patched Track Delivery Status."),
        @ApiResponse(responseCode = "404", description = "OrderId Not Found."),
        @ApiResponse(responseCode = "500", description = "Internal server error.")
    })
    public Orders updateDeliveryStatus(@PathVariable Long orderId, @RequestParam OrderStatus status) {
        return personnelService.updateDeliveryStatus(orderId, status);
    }

    // Manage Availability
    @PatchMapping(value="/availability/{personnelId}", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Manage Availability", description = "Manage Availability.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully patched Manage Availability."),
        @ApiResponse(responseCode = "404", description = "personnelId Not Found."),
        @ApiResponse(responseCode = "500", description = "Internal server error.")
    })
    public DeliveryPersonnel updateAvailability(@PathVariable Long personnelId, @RequestParam boolean available) {
        return personnelService.updateAvailability(personnelId, available);
    }
}

