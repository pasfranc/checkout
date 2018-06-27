package com.r3pi.assessment.checkout.service;

import static com.r3pi.assessment.checkout.constants.CheckoutConstants.PERCENTAGE;
import static com.r3pi.assessment.checkout.constants.CheckoutConstants.PROMO;
import static com.r3pi.assessment.checkout.constants.CheckoutConstants.HYPHENS;

import java.math.BigDecimal;

import com.r3pi.assessment.checkout.model.Item;

public class PercentageDiscountPromotion extends Promotion {
  
  private static final BigDecimal HUNDRED = new BigDecimal(100);

  private Integer percentageDiscount;
  
  @SuppressWarnings("unused")
  private PercentageDiscountPromotion(){}
  
  public PercentageDiscountPromotion (Integer percentageDiscount){
    this.percentageDiscount = percentageDiscount;
    this.description = extractPromoDescription();
  }
  
  public Integer getPercentageDiscount() {
    return percentageDiscount;
  }
  
  private BigDecimal calculatePromotionalPrice(Item item) {    

    BigDecimal totalToPay = item.getPrice().multiply(new BigDecimal(item.getQuantity()));
    BigDecimal discount = totalToPay.multiply(new BigDecimal(percentageDiscount)).divide(HUNDRED).setScale(2, BigDecimal.ROUND_DOWN);;
    
    return totalToPay.subtract(discount);
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
      .append(percentageDiscount)
      .append(PERCENTAGE)
      .append(PROMO)
      .append(HYPHENS);

    return stringBuilder.toString();
  }

}
