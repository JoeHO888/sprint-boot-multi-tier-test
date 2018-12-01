package com.oocl.web.sampleWebApp.domain;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

@Entity
@Table(name = "parking_lot")
public class ParkingLot {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "parking_lot_id", length = 64, nullable = false)
    private String parkingLotId;

    @Column(name = "capacity",nullable = false)
    @Max(100)
    @Min(1)
    private Integer capacity;

    @Column(name = "available_position_count",nullable = false)
    @Min(0)
    private Integer availablePositionCount;

    @Column(name = "parking_boy_id", length = 64)
    private String parkingBoyId;

    public String getParkingBoyId() {
        return parkingBoyId;
    }

    public void setParkingBoyId(String parkingBoyId) {
        this.parkingBoyId = parkingBoyId;
    }

    public Integer getAvailablePositionCount() {
        return availablePositionCount;
    }

    public void setAvailablePositionCount(Integer availablePositionCount) {
        this.availablePositionCount = availablePositionCount;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getParkingLotId() {
        return parkingLotId;
    }

    public void setParkingLotId(String parkingLotId) {
        this.parkingLotId = parkingLotId;
    }

    public Integer getCapacity() {
        return capacity;
    }

    public void setCapacity(Integer capacity) {
        this.capacity = capacity;
    }


    protected ParkingLot() {}

    public ParkingLot(String parkingLotId) {
        this.parkingLotId = parkingLotId;
    }
}

