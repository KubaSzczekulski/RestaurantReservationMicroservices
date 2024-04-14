package com.example.restaurant.entity;

import com.example.restaurant.myEnum.TableStatus;
import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@NoArgsConstructor
@Table(name = "table_table")
public class TableEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long tableId;

    @Enumerated(EnumType.STRING)
    @Column(name = "table_status")
    private TableStatus tableStatus;
    @Column(name = "seating_type")
    private Integer seatingType;

    @Column(name = "is_outdoor")
    private Boolean isOutdoor;

    @ManyToOne(cascade = {
            CascadeType.DETACH,
            CascadeType.MERGE,
            CascadeType.PERSIST,
            CascadeType.REFRESH
    })
    @JoinColumn(name = "restaurantId")
    @JsonBackReference
    private RestaurantEntity restaurantEntity;

    @Transient
    private Long restaurantId;

    public TableEntity(TableStatus tableStatus, int seatingType, boolean isOutdoor, RestaurantEntity restaurantEntity) {
        this.tableStatus = tableStatus;
        this.seatingType = seatingType;
        this.isOutdoor = isOutdoor;
        this.restaurantEntity = restaurantEntity;
    }

}
