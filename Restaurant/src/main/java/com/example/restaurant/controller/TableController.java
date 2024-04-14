package com.example.restaurant.controller;

import com.example.restaurant.entity.TableEntity;
import com.example.restaurant.service.TableService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/table")
public class TableController {

    private final TableService tableService;

    @Autowired
    public TableController(TableService tableService) {
        this.tableService = tableService;
    }


    @PostMapping
    public ResponseEntity<TableEntity> createTable(@RequestBody TableEntity tableEntity) {
        TableEntity createdTable = tableService.addTable(tableEntity);
        return ResponseEntity.ok(createdTable);
    }

    @GetMapping("/{id}")
    public ResponseEntity<TableEntity> getTableById(@PathVariable Long id) {
        TableEntity table = tableService.getTable(id);
        return ResponseEntity.ok(table);
    }

    @GetMapping
    public ResponseEntity<List<TableEntity>> getAllTables() {
        List<TableEntity> tables = tableService.getTables();
        return ResponseEntity.ok(tables);
    }

    @PutMapping("/{id}")
    public ResponseEntity<TableEntity> updateTable(@PathVariable Long id, @RequestBody TableEntity tableEntity) {
        TableEntity updatedTable = tableService.updateTable(id, tableEntity);
        return ResponseEntity.ok(updatedTable);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<TableEntity> patchTable(@PathVariable Long id, @RequestBody TableEntity tableEntity) {
        TableEntity patchedTable = tableService.patchTable(id, tableEntity);
        return ResponseEntity.ok(patchedTable);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTable(@PathVariable Long id) {
        tableService.deleteTable(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/check/{tableId}")
    public Boolean checkTable(@PathVariable Long tableId){
        return tableService.existsByTableIdAndTableStatus(tableId);
    }
}
