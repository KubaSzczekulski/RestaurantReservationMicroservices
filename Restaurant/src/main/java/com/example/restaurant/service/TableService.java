package com.example.restaurant.service;

import com.example.restaurant.entity.TableEntity;
import com.example.restaurant.exception.TableError;
import com.example.restaurant.exception.TableException;
import com.example.restaurant.myEnum.TableStatus;
import com.example.restaurant.repository.RestaurantRepository;
import com.example.restaurant.repository.TableRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class TableService {

    private final TableRepository tableRepository;
    private final RestaurantRepository restaurantRepository;

    @Autowired
    public TableService(TableRepository tableRepository, RestaurantRepository restaurantRepository) {
        this.tableRepository = tableRepository;
        this.restaurantRepository = restaurantRepository;
    }

    public TableEntity getTable(Long id) {
        return tableRepository.findById(id).orElseThrow(() -> new TableException(TableError.TABLE_NOT_FOUND));
    }

    public List<TableEntity> getTables() {
        return tableRepository.findAll();
    }

    @Transactional
    public TableEntity addTable(TableEntity tableEntity) {
        if (tableEntity.getRestaurantId() != null) {
            restaurantRepository.findById(tableEntity.getRestaurantId())
                    .ifPresent(restaurant -> tableEntity.setRestaurantEntity(restaurant));
        }
        return tableRepository.save(tableEntity);
    }

    @Transactional
    public TableEntity updateTable(Long id, TableEntity tableEntity) {
        return tableRepository.findById(id).map(existingTable -> {
            existingTable.setTableStatus(tableEntity.getTableStatus());
            existingTable.setSeatingType(tableEntity.getSeatingType());
            existingTable.setIsOutdoor(tableEntity.getIsOutdoor());
            existingTable.setRestaurantEntity(tableEntity.getRestaurantEntity());
            return tableRepository.save(existingTable);
        }).orElseThrow(() -> new TableException(TableError.TABLE_NOT_FOUND));
    }
    @Transactional
    public void deleteTable(Long id) {
        TableEntity table = tableRepository.findById(id).orElseThrow(() -> new TableException(TableError.TABLE_NOT_FOUND));
        tableRepository.delete(table);
    }
    @Transactional
    public TableEntity patchTable(Long id, TableEntity tableEntity) {
        return tableRepository.findById(id)
                .map(existingTable -> {
                    if (tableEntity.getTableStatus() != null) {
                        existingTable.setTableStatus(tableEntity.getTableStatus());
                    }

                    // Uwaga: Przyjmujemy, że seatingType <= 0 to wartość niepoprawna i nie będziemy aktualizować pola w takim przypadku.
                    if (tableEntity.getSeatingType()!=null) {
                        if (tableEntity.getSeatingType()<0) {
                            throw new RuntimeException();
                        }
                        existingTable.setSeatingType(tableEntity.getSeatingType());
                    }

                    // isOutdoor jest typu Boolean, więc może być null. Aktualizujemy tylko, jeśli wartość jest różna od null.
                    if (tableEntity.getIsOutdoor() != null) {
                        existingTable.setIsOutdoor(tableEntity.getIsOutdoor());
                    }

                    // Aktualizujemy pole restaurantEntity tylko jeśli nie jest null.
                    if (tableEntity.getRestaurantEntity() != null) {
                        existingTable.setRestaurantEntity(tableEntity.getRestaurantEntity());
                    }

                    return tableRepository.save(existingTable);
                }).orElseThrow(() -> new TableException(TableError.TABLE_NOT_FOUND));
    }

    public Boolean existsByTableIdAndTableStatus(Long tableId){
        return tableRepository.existsByTableIdAndTableStatus(tableId, TableStatus.AVAILABLE);
    }

}

