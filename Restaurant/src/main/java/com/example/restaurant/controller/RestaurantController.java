package com.example.restaurant.controller;

import com.example.restaurant.entity.RestaurantEntity;
import com.example.restaurant.service.RestaurantService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/restaurant")
public class RestaurantController {

    private final RestaurantService restaurantService;

    public RestaurantController(RestaurantService restaurantService) {
        this.restaurantService = restaurantService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<RestaurantEntity> getRestaurant(@PathVariable Long id){
        RestaurantEntity restaurantEntity = restaurantService.getRestaurant(id);
        return ResponseEntity.ok(restaurantEntity);
    }

    @GetMapping("name/{name}")
    public ResponseEntity<RestaurantEntity> getRestaurantByName(@PathVariable String name){
        RestaurantEntity restaurantEntity = restaurantService.getRestaurant(name);
        return ResponseEntity.ok(restaurantEntity);
    }

    @GetMapping
    public ResponseEntity<List<RestaurantEntity>> getRestaurants(){
        List<RestaurantEntity> restaurants = restaurantService.getRestaurants();
        return ResponseEntity.ok(restaurants);
    }


    @PostMapping
    public ResponseEntity<RestaurantEntity> addRestaurant(@RequestBody @Valid RestaurantEntity restaurantEntity){
        RestaurantEntity addedRestaurant = restaurantService.addRestaurant(restaurantEntity);
        return ResponseEntity.ok(addedRestaurant);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteRestaurant(@PathVariable Long id){
        restaurantService.deleteRestaurant(id);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<RestaurantEntity> putRestaurant(@PathVariable Long id, @Valid @RequestBody RestaurantEntity restaurantEntity){
        RestaurantEntity editedRestaurant = restaurantService.editRestaurant(id, restaurantEntity);
        return ResponseEntity.ok(editedRestaurant);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<RestaurantEntity> patchRestaurant(@PathVariable Long id, @Valid @RequestBody RestaurantEntity restaurantEntity){
        RestaurantEntity patchedRestaurant = restaurantService.patchRestaurant(id, restaurantEntity);
        return ResponseEntity.ok(patchedRestaurant);
    }
}
