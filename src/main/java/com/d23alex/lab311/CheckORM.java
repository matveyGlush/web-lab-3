package com.d23alex.lab311;

import jakarta.persistence.*;

import java.sql.Date;
import java.sql.Time;

@Entity
@Table(name = "checks", schema = "s336541", catalog = "studs")
@NamedQuery(name = "checks.allSortedByDate", query = "SELECT c FROM CheckORM c")
public class CheckORM {
    @Basic
    @Column(name = "x", nullable = true, precision = 0)
    private Double x;
    @Basic
    @Column(name = "y", nullable = true, precision = 0)
    private Double y;
    @Basic
    @Column(name = "r", nullable = true, precision = 0)
    private Double r;
    @Basic
    @Column(name = "area_contains_point", nullable = true)
    private Boolean areaContainsPoint;
    @Basic
    @Column(name = "request_date", nullable = true)
    private Date requestDate;
    @Basic
    @Column(name = "calculation_time", nullable = true)
    private Time calculationTime;
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id", nullable = false)
    private int id;

    public Double getX() {
        return x;
    }

    public void setX(Double x) {
        this.x = x;
    }

    public Double getY() {
        return y;
    }

    public void setY(Double y) {
        this.y = y;
    }

    public Double getR() {
        return r;
    }

    public void setR(Double r) {
        this.r = r;
    }

    public Boolean getAreaContainsPoint() {
        return areaContainsPoint;
    }

    public void setAreaContainsPoint(Boolean areaContainsPoint) {
        this.areaContainsPoint = areaContainsPoint;
    }

    public Date getRequestDate() {
        return requestDate;
    }

    public void setRequestDate(Date requestDate) {
        this.requestDate = requestDate;
    }

    public Time getCalculationTime() {
        return calculationTime;
    }

    public void setCalculationTime(Time calculationTime) {
        this.calculationTime = calculationTime;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
