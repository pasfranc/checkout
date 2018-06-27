package com.r3pi.assessment.checkout.service;

import static com.r3pi.assessment.checkout.constants.CheckoutConstants.MULTIPLY;
import static com.r3pi.assessment.checkout.constants.CheckoutConstants.PROMO;
import static com.r3pi.assessment.checkout.constants.CheckoutConstants.HYPHENS;

import java.math.BigDecimal;

import com.r3pi.assessment.checkout.model.Item;

public class BuyXPayYPromotion extends Promotion {
  
  private Integer payNumber; 
  private Integer buyNumber;
  
  @SuppressWarnings("unused")
  private BuyXPayYPromotion(){}
    
  public BuyXPayYPromotion (Integer buyNumber, Integer payNumber){
    this.buyNumber = buyNumber;
    this.payNumber = payNumber;
    this.description = extractPromoDescription();
  }
      
  public Integer getPayNumber() {
    return payNumber;
  }

  public Integer getBuyNumber() {
    return buyNumber;
  }
  
  private BigDecimal calculatePromotionalPrice(Item item) {    

    Integer numberOfItemsToPay = ((item.getQuantity() / buyNumber ) * payNumber ) + item.getQuantity() % buyNumber;
    
    return item.getPrice().multiply(new BigDecimal(numberOfItemsToPay));
  }
  
  @Override
  public Promotion buildPromotion(Item item){
    
    this.promotionalPrice = calculatePromotionalPrice(item);
    this.savedMoney = calculateSavedMoney(item, this.promotionalPrice);
    
    return this;
  }
  
  private String extractPromoDescription() {

    StringBuilder stringBuilder = new StringBuilder();
    stringBuilder
      .append(HYPHENS)   
      .append(buyNumber)
      .append(MULTIPLY)
      .append(payNumber)
      .append(PROMO)
      .append(HYPHENS);

    return stringBuilder.toString();
  }

}
