package com.r3pi.assessment.checkout.model;

import java.math.BigDecimal;

public class ItemRow {

  private Integer rowQuantity;
  private final String rowDescription;
  private BigDecimal rowPrice;
  private final String rowPromotionDescription;
  private BigDecimal rowPromotionDiscount;
  
  public ItemRow(ItemRowBuilder builder){
    this.rowQuantity = builder.rowQuantity;
    this.rowDescription = builder.rowDescription;
    this.rowPrice = builder.rowPrice;
    this.rowPromotionDescription = builder.rowPromotionDescription;
    this.rowPromotionDiscount = builder.rowPromotionDiscount;
  }

  public Integer getRowQuantity() {
    return rowQuantity;
  }

  public String getRowDescription() {
    return rowDescription;
  }

  public BigDecimal getRowPrice() {
    return rowPrice;
  }

  public String getRowPromotionDescription() {
    return rowPromotionDescription;
  }

  public BigDecimal getRowPromotionDiscount() {
    return rowPromotionDiscount;
  }
  
  public static class ItemRowBuilder {

    private Integer rowQuantity;
    private String rowDescription;
    private BigDecimal rowPrice;
    private String rowPromotionDescription;
    private BigDecimal rowPromotionDiscount;
    
    public ItemRowBuilder rowQuantity(Integer rowQuantity){
      this.rowQuantity = rowQuantity;
      return this;
    }
    
    public ItemRowBuilder rowDescription(String rowDescription){
      this.rowDescription = rowDescription;
      return this;
    }
    
    public ItemRowBuilder rowPrice(BigDecimal rowPrice){
      this.rowPrice = rowPrice;
      return this;
    }
    
    public ItemRowBuilder rowPromotionDescription(String rowPromotionDescription){
      this.rowPromotionDescription = rowPromotionDescription;
      return this;
    }
    
    public ItemRowBuilder rowPromotionDiscount(BigDecimal rowPromotionDiscount){
      this.rowPromotionDiscount = rowPromotionDiscount;
      return this;
    }
    
    public ItemRow build() {
      return new ItemRow(this);
    }
  }  

  
}
