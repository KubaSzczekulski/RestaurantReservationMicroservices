package com.example.restaurant.service;

import com.example.restaurant.entity.RestaurantEntity;
import com.example.restaurant.exception.RestaurantError;
import com.example.restaurant.exception.RestaurantException;
import com.example.restaurant.repository.RestaurantRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;

@Service
public class RestaurantService {

    private final RestaurantRepository restaurantRepository;
    @Autowired
    public RestaurantService(RestaurantRepository restaurantRepository) {
        this.restaurantRepository = restaurantRepository;
    }

    public RestaurantEntity getRestaurant(Long id){
        return restaurantRepository.findById(id).orElseThrow(()-> new RestaurantException(RestaurantError.RESTAURANT_NOT_FOUND));
    }

    public RestaurantEntity getRestaurant(String name){
        return restaurantRepository.findByRestaurantName(name).orElseThrow(()-> new RestaurantException(RestaurantError.RESTAURANT_NOT_FOUND));
    }

    public List<RestaurantEntity> getRestaurants(){
        return restaurantRepository.findAll();
    }
    @Transactional
    public RestaurantEntity addRestaurant(RestaurantEntity restaurantEntity){
        if (emailExists(restaurantEntity.getEmail()) ) {
            throw new RestaurantException(RestaurantError.EMAIL_ALREADY_EXISTS);
        }
        if (telephoneNumber(restaurantEntity.getTelephoneNumber()) ) {
            throw new RestaurantException(RestaurantError.TELEPHONE_ALREADY_EXISTS);
        }
        return restaurantRepository.save(restaurantEntity);
    }

    @Transactional
    public RestaurantEntity editRestaurant(Long id, RestaurantEntity restaurantEntity){
        return restaurantRepository.findById(id)
             .map(restaurantFromDb -> {
                 if (emailExistsExcludeId(restaurantEntity.getEmail(), id) ) {
                     throw new RestaurantException(RestaurantError.EMAIL_ALREADY_EXISTS);
                 }
                 if (telephoneNumberExistsExcludeId(restaurantEntity.getTelephoneNumber(), id) ) {
                     throw new RestaurantException(RestaurantError.TELEPHONE_ALREADY_EXISTS);
                 }
                    restaurantFromDb.setRestaurantName(restaurantEntity.getRestaurantName());
                    restaurantFromDb.setCityName(restaurantEntity.getCityName());
                    restaurantFromDb.setCityId(restaurantEntity.getCityId());
                    restaurantFromDb.setStreetName(restaurantEntity.getStreetName());
                    restaurantFromDb.setStreetNumber(restaurantEntity.getStreetNumber());
                    restaurantFromDb.setOpenRestaurant(restaurantEntity.getOpenRestaurant());
                    restaurantFromDb.setCloseRestaurant(restaurantEntity.getCloseRestaurant());
                    restaurantFromDb.setEmail(restaurantEntity.getEmail());
                    restaurantFromDb.setTelephoneNumber(restaurantEntity.getTelephoneNumber());
                    restaurantFromDb.setCuisineType(restaurantEntity.getCuisineType());
                    restaurantFromDb.setTableEntities(restaurantEntity.getTableEntities());

                    return restaurantRepository.save(restaurantFromDb);
                }).orElseThrow(RuntimeException::new);
    }

    @Transactional
    public RestaurantEntity patchRestaurant(Long id, RestaurantEntity restaurantEntity) {
        return restaurantRepository.findById(id)
                .map(existingRestaurant -> {
                    if (emailExistsExcludeId(restaurantEntity.getEmail(), id) ) {
                        throw new RestaurantException(RestaurantError.EMAIL_ALREADY_EXISTS);
                    }
                    if (telephoneNumberExistsExcludeId(restaurantEntity.getTelephoneNumber(),id) ) {
                        throw new RestaurantException(RestaurantError.TELEPHONE_ALREADY_EXISTS);
                    }

                    if (StringUtils.hasLength(restaurantEntity.getRestaurantName())) {
                        existingRestaurant.setRestaurantName(restaurantEntity.getRestaurantName());
                    }

                    if (StringUtils.hasLength(restaurantEntity.getCityName())) {
                        existingRestaurant.setCityName(restaurantEntity.getCityName());
                    }

                    if (StringUtils.hasLength(restaurantEntity.getCityId())) {
                        existingRestaurant.setCityId(restaurantEntity.getCityId());
                    }

                    if (StringUtils.hasLength(restaurantEntity.getStreetName())) {
                        existingRestaurant.setStreetName(restaurantEntity.getStreetName());
                    }
//                    poprawic pozniej xd
                    if (restaurantEntity.getStreetNumber() != null) {
                        existingRestaurant.setStreetNumber(restaurantEntity.getStreetNumber());
                    }

                    if (restaurantEntity.getOpenRestaurant() != null) {
                        existingRestaurant.setOpenRestaurant(restaurantEntity.getOpenRestaurant());
                    }

                    if (restaurantEntity.getCloseRestaurant() != null) {
                        existingRestaurant.setCloseRestaurant(restaurantEntity.getCloseRestaurant());
                    }

                    if (restaurantEntity.getEmail() != null) {
                        existingRestaurant.setEmail(restaurantEntity.getEmail());
                    }

                    if (restaurantEntity.getTelephoneNumber() != null) {
                        existingRestaurant.setTelephoneNumber(restaurantEntity.getTelephoneNumber());
                    }

                    if (restaurantEntity.getCuisineType() != null) {
                        existingRestaurant.setCuisineType(restaurantEntity.getCuisineType());
                    }

                    if (restaurantEntity.getTableEntities() != null) {
                        existingRestaurant.setTableEntities(restaurantEntity.getTableEntities());
                    }

                    return restaurantRepository.save(existingRestaurant);
                }).orElseThrow(() -> new RuntimeException("Restaurant not found."));
    }

    @Transactional
    public void deleteRestaurant(Long id){
        restaurantRepository.findById(id).orElseThrow(
                ()->new RestaurantException(RestaurantError.RESTAURANT_CANNOT_BE_DELETED)
        );
        restaurantRepository.deleteById(id);
    }


    private boolean emailExistsExcludeId(String email, Long id) {
        return restaurantRepository.findByEmailAndRestaurantIdNot(email, id).isPresent();
    }

    private boolean telephoneNumberExistsExcludeId(String telephoneNumber, Long id) {
        return restaurantRepository.findByTelephoneNumberAndRestaurantIdNot(telephoneNumber, id).isPresent();
    }

    private boolean emailExists(String email) {
        return restaurantRepository.findByEmail(email).isPresent();
    }

    private boolean telephoneNumber(String telephoneNumber) {
        return restaurantRepository.findByTelephoneNumber(telephoneNumber).isPresent();
    }


}
