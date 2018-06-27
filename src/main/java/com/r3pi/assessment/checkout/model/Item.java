package com.r3pi.assessment.checkout.model;

import java.math.BigDecimal;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotEmpty;

public class Item {

  @NotEmpty
  private String itemCode;
  @NotEmpty
  private String name;
  @NotNull
  @Min(value = 0)
  private BigDecimal price;
  @NotNull
  @Min(value = 1)
  private Integer quantity;
  
  public String getName() {
    return name;
  }
  public void setName(String name) {
    this.name = name;
  }
  public BigDecimal getPrice() {
    return price;
  }
  public void setPrice(BigDecimal price) {
    this.price = price;
  }
  public Integer getQuantity() {
    return quantity;
  }
  public void setQuantity(Integer quantity) {
    this.quantity = quantity;
  }
  public String getItemCode() {
    return itemCode;
  }
  public void setItemCode(String itemCode) {
    this.itemCode = itemCode;
  }
  
}
