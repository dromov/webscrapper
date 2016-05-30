package com.scrapper.model;

import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.sql.Date;

@Entity
@Table(name="inmates")
public class Inmate {

    @Id
    @Column(name="id")
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private int id;

    private String firstName;

    private String lastName;

    private String middleName;

    @Transient
    private String Jacket;
    @Transient
    private String suffix;
    @Transient
    private int arrestNo;

    @DateTimeFormat
    private Date bookDate;

    @DateTimeFormat
    private Date releaseDate;

    public Inmate(String firstName, String lastName, String middleName, String jacket, String suffix, int arrestNo, Date originalBookDateTime, Date finalReleaseDateTime) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.middleName = middleName;
        this.Jacket = jacket;
        this.suffix = suffix;
        this.arrestNo = arrestNo;
        this.bookDate = originalBookDateTime;
        this.releaseDate = finalReleaseDateTime;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getMiddleName() {
        return middleName;
    }

    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }

    public String getJacket() {
        return Jacket;
    }

    public void setJacket(String jacket) {
        Jacket = jacket;
    }

    public String getSuffix() {
        return suffix;
    }

    public void setSuffix(String suffix) {
        this.suffix = suffix;
    }

    public int getArrestNo() {
        return arrestNo;
    }

    public void setArrestNo(int arrestNo) {
        this.arrestNo = arrestNo;
    }

    public Date getBookDate() {
        return bookDate;
    }

    public void setBookDate(Date bookDate) {
        this.bookDate = bookDate;
    }

    public Date getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(Date releaseDate) {
        this.releaseDate = releaseDate;
    }

}