package com.r3pi.assessment.checkout.model;

import java.math.BigDecimal;
import java.util.List;

public class Receipt {

  private final List<ItemRow> itemRowList;
  private final BigDecimal total;
  
  public Receipt(ReceiptBuilder builder){
    this.itemRowList = builder.itemRowList;
    this.total = builder.total;
  }

  public List<ItemRow> getItemRowList() {
    return itemRowList;
  }

  public BigDecimal getTotal() {
    return total;
  }
  
  public static class ReceiptBuilder {
    private List<ItemRow> itemRowList;
    private BigDecimal total;
    
    public ReceiptBuilder itemRowList(List<ItemRow> itemRowList){
      this.itemRowList = itemRowList;
      return this;
    }
    
    public ReceiptBuilder total(BigDecimal total){
      this.total = total;
      return this;
    }
    
    public Receipt build() {
      return new Receipt(this);
    }
  }  
}
