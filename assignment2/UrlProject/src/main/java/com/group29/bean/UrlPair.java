package com.group29.bean;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Data;
import org.springframework.context.annotation.Bean;

/**
 * @author ryyyyyy
 * @create 2023-04-09 16:46
 * This is a class for Object Relational Mapping(ORM) with the database
 * contains two string field to store the original url and shortened url
 * one Long field (id) to mapping the id in database, which is set to be auto increment
 * seven other Long fields(mon to sun) to store the visiting numbers of the shortened url
 */
@Entity
public class UrlPair {
    @Column(name = "user_name")
    private String userName;
    @Column(name = "short_url")

    private String shortUrl;
    @Column(name = "long_url")
    private String longUrl;
    @Column(name = "mon")
    private Long mon;
    @Column(name = "tue")
    private Long tue;
    @Column(name = "wed")
    private Long wed;
    @Column(name = "thu")
    private Long thu;
    @Column(name = "fri")
    private Long fri;
    @Column(name = "sat")
    private Long sat;
    @Column(name = "sun")
    private Long sun;
    @Id
    @GeneratedValue
    private Long id;

    public UrlPair() {
        this.setMon(Long.valueOf(0));
        this.setTue(Long.valueOf(0));
        this.setWed(Long.valueOf(0));
        this.setThu(Long.valueOf(0));
        this.setFri(Long.valueOf(0));
        this.setSat(Long.valueOf(0));
        this.setSun(Long.valueOf(0));
    }

//    public UrlPair(String shortUrl, String longUrl) {
//        this.shortUrl = shortUrl;
//        this.longUrl = longUrl;
//    }

    public Long getMon() {
        return mon;
    }

    public Long getTue() {
        return tue;
    }

    public Long getWed() {
        return wed;
    }

    public Long getThu() {
        return thu;
    }

    public Long getFri() {
        return fri;
    }

    public Long getSat() {
        return sat;
    }

    public Long getSun() {
        return sun;
    }

    public void setMon(Long mon) {
        this.mon = mon;
    }

    public void setTue(Long tue) {
        this.tue = tue;
    }

    public void setWed(Long wed) {
        this.wed = wed;
    }

    public void setThu(Long thu) {
        this.thu = thu;
    }

    public void setFri(Long fri) {
        this.fri = fri;
    }

    public void setSat(Long sat) {
        this.sat = sat;
    }

    public void setSun(Long sun) {
        this.sun = sun;
    }

    public void setShortUrl(String shortUrl) {
        this.shortUrl = shortUrl;
    }

    public void setLongUrl(String longUrl) {
        this.longUrl = longUrl;
    }

    public String getShortUrl() {
        return shortUrl;
    }

    public String getLongUrl() {
        return longUrl;
    }

    @Override
    public String toString() {
        return "UrlPair{" +
                "userName='" + userName + '\'' +
                ", shortUrl='" + shortUrl + '\'' +
                ", longUrl='" + longUrl + '\'' +
                ", mon=" + mon +
                ", tue=" + tue +
                ", wed=" + wed +
                ", thu=" + thu +
                ", fri=" + fri +
                ", sat=" + sat +
                ", sun=" + sun +
                ", id=" + id +
                '}';
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }
}
