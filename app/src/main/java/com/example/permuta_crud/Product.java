package com.example.permuta_crud;

class Product {
    String id;
    String name;
    String suplier_id;
    String suplier_name="";
 
    Product(String id, String name, String suplier_id,String suplier_name) {
        this.id = id;
        this.name = name;
        this.suplier_id = suplier_id;
        this.suplier_name = suplier_name;
    }
}