package com.example.myapplication;

public class Product {
    int code;
    String name;
    double Price;
    int qdt;
    String marca;

    public Product(){};

    public Product(int code, String name, Double price, int qdt, String marca) {
        this.code = code;
        this.name = name;
        Price = price;
        this.qdt = qdt;
        this.marca = marca;
    }
    public Product( String name, Double price, int qdt, String marca) {
        this.name = name;
        Price = price;
        this.qdt = qdt;
        this.marca = marca;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getPrice() {
        return Price;
    }

    public void setPrice(Double price) {
        Price = price;
    }

    public int getQdt() {
        return qdt;
    }

    public void setQdt(int qdt) {
        this.qdt = qdt;
    }

    public String getMarca() {
        return marca;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }
}
