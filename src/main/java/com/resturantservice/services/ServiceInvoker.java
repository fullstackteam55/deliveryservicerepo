package com.resturantservice.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import com.resturantservice.entities.*;

@Service
public class ServiceInvoker {
    
    @Autowired
    private RestTemplate restTemplate;
    
    public Customer getCustomer(Long customerid) {
    	String url = "http://localhost:9095/customerservices/customers/"+customerid;
    	HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        return restTemplate.getForObject(url, Customer.class);
    }
    
    public List<Restaurant> browseRestaurants() {
    	String url = "http://localhost:9095/customerservices/customers/restaurants";
    	HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        ResponseEntity<List<Restaurant>> response = restTemplate.exchange(
                url,
                HttpMethod.GET,
                null, // Use this if there are no custom headers, otherwise use new HttpEntity<>(headers)
                new ParameterizedTypeReference<List<Restaurant>>() {}
            );

            return response.getBody();
    }
    
    public List<MenuItem> searchMenu(String keyword) {
    	String url = "http://localhost:9095/customerservices/customers/menus/search";
    	HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        ResponseEntity<List<MenuItem>> response = restTemplate.exchange(
                url,
                HttpMethod.GET,
                null, // Use this if there are no custom headers, otherwise use new HttpEntity<>(headers)
                new ParameterizedTypeReference<List<MenuItem>>() {}
            );

            return response.getBody();
    }
    
    public List<Orders> trackOrders(Long customerid) {
    	String url = "http://localhost:9095/customerservices/customers/orders/track/"+customerid;
    	HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        ResponseEntity<List<Orders>> response = restTemplate.exchange(
                url,
                HttpMethod.GET,
                null, // Use this if there are no custom headers, otherwise use new HttpEntity<>(headers)
                new ParameterizedTypeReference<List<Orders>>() {}
            );

            return response.getBody();
    }
    
    public List<Orders> getOrderHistory(Long customerid) {
    	String url = "http://localhost:9095/customerservices/customers/orders/history/"+customerid;
    	HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        ResponseEntity<List<Orders>> response = restTemplate.exchange(
                url,
                HttpMethod.GET,
                null, // Use this if there are no custom headers, otherwise use new HttpEntity<>(headers)
                new ParameterizedTypeReference<List<Orders>>() {}
            );

            return response.getBody();
    }
    
    public String login(User loginUser) {
    	String url = "http://localhost:9091/userservices/users/login";
    	HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<User> requestEntity = new HttpEntity<>(loginUser, headers);

        return restTemplate.postForObject(url, requestEntity, String.class);
    }
    
    public Orders placeOrder(Orders order) {
    	String url = "http://localhost:9095/customerservices/customers/orders";
    	HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<Orders> requestEntity = new HttpEntity<>(order, headers);

        return restTemplate.postForObject(url, requestEntity, Orders.class);
    }
}

