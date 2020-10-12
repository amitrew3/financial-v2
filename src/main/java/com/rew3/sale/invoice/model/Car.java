package com.rew3.sale.invoice.model;

import javax.validation.constraints.Max;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;
import javax.validation.constraints.Size;
import javax.validation.groups.Default;


public class Car {

//    @NotNull(groups={"firstscreen", "canbuy"},message ="Manu must be not null" )

    @NotNull(
            message = "Id must not be null",
            groups = {EditAction.class}
    )
    //@Null(groups = Default.class)
    private String id;



    @NotNull(message = "Manu must be not null")
    private String manufacturer;

    @NotNull
    @Size(min = 2, max = 14, message = "between 2 and 14 ")
    private String licensePlate;

    @Max(5)
    private int seatCount;

    public Car(String manufacturer, String licencePlate, int seatCount) {
        this.manufacturer = manufacturer;
        this.licensePlate = licencePlate;
        this.seatCount = seatCount;

    }
    public Car(String id,String manufacturer, String licencePlate, int seatCount) {
        this.id = id;
        this.manufacturer = manufacturer;
        this.licensePlate = licencePlate;
        this.seatCount = seatCount;

    }
}
