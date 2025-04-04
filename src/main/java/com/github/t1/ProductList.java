package com.github.t1;

import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;

import java.util.List;

import static jakarta.xml.bind.annotation.XmlAccessType.FIELD;

/// Workaround for [issue#33865](https://github.com/quarkusio/quarkus/issues/33865)
@XmlRootElement(name = "products")
@XmlAccessorType(FIELD)
public class ProductList {
    @XmlElement(name = "product")
    List<Product> products;

    public ProductList() {}

    public ProductList(List<Product> products) {this.products = products;}
}
