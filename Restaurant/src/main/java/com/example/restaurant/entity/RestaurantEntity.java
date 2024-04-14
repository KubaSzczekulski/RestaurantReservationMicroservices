package com.example.restaurant.entity;

import com.example.restaurant.myEnum.CuisineType;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalTime;
import java.util.List;

@Data
@Entity
@Table(name = "restaurant")
@NoArgsConstructor
public class RestaurantEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "restaurant_id")
    private Long restaurantId;

    @Enumerated(EnumType.STRING)
    @Column(name = "cuisine_type")
    private CuisineType cuisineType;

    @Column(name = "name")
    private String restaurantName;


    @Column(name = "city_name")
    private String cityName;


    @Column(name = "city_id")
    private String cityId;


    @Column(name = "street_name")
    private String streetName;


    @Column(name = "street_number")
    private String streetNumber;
    @Column(name = "open_restaurant")
    private LocalTime openRestaurant;
    @Column(name = "close_restaurant")
    private LocalTime closeRestaurant;

    @Column(name = "email")
    @Email
    private String email;

    @Column(name = "telephone_number")
    private String telephoneNumber;

    @OneToMany(mappedBy = "restaurantEntity", cascade = {
            CascadeType.ALL
    })
    @JsonManagedReference
    private List<TableEntity> tableEntities;

    public RestaurantEntity(CuisineType cuisineType, String restaurantName, String cityName, String cityId, String streetName, String streetNumber, LocalTime openRestaurant, LocalTime closeRestaurant, String email, String telephoneNumber, List<TableEntity> tableEntities) {
        this.cuisineType = cuisineType;
        this.restaurantName = restaurantName;
        this.cityName = cityName;
        this.cityId = cityId;
        this.streetName = streetName;
        this.streetNumber = streetNumber;
        this.openRestaurant = openRestaurant;
        this.closeRestaurant = closeRestaurant;
        this.email = email;
        this.telephoneNumber = telephoneNumber;
        this.tableEntities = tableEntities;
    }
}
