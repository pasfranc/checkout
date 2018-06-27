package com.r3pi.assessment.checkout.service;

import java.math.BigDecimal;

import com.r3pi.assessment.checkout.model.Item;

public abstract class Promotion {
  
  protected String description;
  protected BigDecimal promotionalPrice;
  protected BigDecimal savedMoney;
  
  public abstract Promotion buildPromotion(Item item);
  
  protected BigDecimal calculateSavedMoney(Item item, BigDecimal promotionalPrice) {    

    BigDecimal itemPriceBasedOnQuantity = item.getPrice().multiply(new BigDecimal(item.getQuantity()));
    
    return itemPriceBasedOnQuantity.subtract(promotionalPrice);
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public BigDecimal getPromotionalPrice() {
    return promotionalPrice;
  }

  public void setPromotionalPrice(BigDecimal promotionalPrice) {
    this.promotionalPrice = promotionalPrice;
  }

  public BigDecimal getSavedMoney() {
    return savedMoney;
  }

  public void setSavedMoney(BigDecimal savedMoney) {
    this.savedMoney = savedMoney;
  }
  
}
