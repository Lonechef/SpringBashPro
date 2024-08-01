package com.advpro2.BatchProject.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "Walmart_Sales")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class WalMartdata {

    @Id
    @Column(name = "ID")
    private int id;

    @Column(name = "Weekly_Sales")
    private double weeklySales;

    @Column(name = "Temperature")
    private double temperature;

    @Column(name = "Fuel_Price")
    private double fuelPrice;

    @Column(name = "Unemployment")
    private double unemployment;

    @Column(name="Price_Inr")
    private double pricrinr;

    @Column(name="temp_Celcius")
    private double temp_celcius;
}
