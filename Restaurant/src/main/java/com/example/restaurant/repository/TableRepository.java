package com.example.restaurant.repository;

import com.example.restaurant.entity.TableEntity;
import com.example.restaurant.myEnum.TableStatus;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TableRepository extends JpaRepository<TableEntity, Long> {
    Boolean existsByTableIdAndTableStatus(Long tableId, TableStatus tableStatus);
}
