package com.example.restaurant.repository;

import com.example.restaurant.entity.RestaurantEntity;
import com.example.restaurant.myEnum.TableStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RestaurantRepository extends JpaRepository<RestaurantEntity, Long> {
    Optional<RestaurantEntity> findByRestaurantName(String name);

    Optional<RestaurantEntity> findByEmail(String email);
    Optional<RestaurantEntity> findByTelephoneNumber(String telephoneNumber);
    Optional<RestaurantEntity> findByEmailAndRestaurantIdNot(String email, Long id);
    Optional<RestaurantEntity> findByTelephoneNumberAndRestaurantIdNot(String telephoneNumber, Long id);


}
