package com.r3pi.assessment.checkout.service;

import static java.math.BigDecimal.ZERO;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.r3pi.assessment.checkout.model.Item;
import com.r3pi.assessment.checkout.model.ItemRow;
import com.r3pi.assessment.checkout.model.Receipt;

import static com.r3pi.assessment.checkout.constants.CheckoutConstants.OPEN_PARENTHESIS;
import static com.r3pi.assessment.checkout.constants.CheckoutConstants.CLOSE_PARENTHESIS;
import static com.r3pi.assessment.checkout.constants.CheckoutConstants.MULTIPLY;
import static com.r3pi.assessment.checkout.constants.CheckoutConstants.PROMOCODE;

import static java.util.Objects.nonNull;

/**
 * Service used to provide a receipt based on item list
 * considering eventual promotions on items.
 * 
 * @author PFrancavilla
 */
@Service
public class ReceiptService {

  @Autowired
  private PromotionFactory promotionFactory;

  /**
   * This method is used to generate a receipt object.
   * 
   * @author PFrancavilla
   */
  public Receipt buildReceipt(List<Item> itemList, String promoCode) {
   
    List<ItemRow> itemRowList = new ArrayList<>();
    BigDecimal total = ZERO;
    
    for (Item item:itemList) {
 
      Integer quantity = item.getQuantity();
      BigDecimal itemPriceToSum;
      BigDecimal rowPrice = item.getPrice().multiply(new BigDecimal(quantity));
      BigDecimal rowPromotionDiscount = null;
      String rowPromotionDescription = null;

      Optional<Promotion> promotionOnItem = retrievePromotionOnItem(item);

      if (promotionOnItem.isPresent()) {
        Promotion currentPromo = buildPromotion(item, promotionOnItem);
        itemPriceToSum = currentPromo.getPromotionalPrice();
       
        if (promotionHasSense(currentPromo)){
          rowPromotionDescription = currentPromo.getDescription();
          rowPromotionDiscount = currentPromo.getSavedMoney().negate();
        }

      } else {
        itemPriceToSum = rowPrice;
      }
      
      ItemRow itemRow = new ItemRow.ItemRowBuilder()
          .rowQuantity(quantity)
          .rowDescription(extractProductDescription(item))
          .rowPrice(rowPrice)
          .rowPromotionDiscount(rowPromotionDiscount)
          .rowPromotionDescription(rowPromotionDescription)
          .build();
      
      itemRowList.add(itemRow);
      total = total.add(itemPriceToSum);
      
    }
    
    return new Receipt.ReceiptBuilder().itemRowList(itemRowList).total(checkPromoCode(total, promoCode)).build();
  }

  /**
   * This method is calculatingPromotion based on item once we are sure a there is a promotion on this item
   * 
   * @author PFrancavilla
   */
  private Promotion buildPromotion(Item item, Optional<Promotion> promotionOnItem) {
    return promotionOnItem.get().buildPromotion(item);
  }

  /**
   * This method is used to retrieve current promotion for this item.
   * It makes use of promotionFactory and item key
   * 
   * @author PFrancavilla
   */
  private Optional<Promotion> retrievePromotionOnItem(Item item) {
    return promotionFactory.findStrategyForKey(item.getItemCode());
  }

  /**
   * This method is used to check if one promotion has sense
   * i.e. in a 3x2 promotion, the promotion is not applied for only one product
   * so we check if there is a real saving of money after promotion is applied.
   * 
   * @author PFrancavilla
   */
  private boolean promotionHasSense(Promotion currentPromo) {
    return currentPromo.getSavedMoney().compareTo(ZERO) > 0;
  }

  /**
   * This method is used to generate product description with quantity on receipt row.
   * 
   * @author PFrancavilla
   */
  private String extractProductDescription(Item item) {

    StringBuilder stringBuilder = new StringBuilder();
    stringBuilder.append(item.getName()).append(OPEN_PARENTHESIS).append(item.getQuantity())
        .append(MULTIPLY).append(item.getPrice()).append(CLOSE_PARENTHESIS);

    return stringBuilder.toString();
  }
  
  /**
   * This method is ... :)
   * 
   * @author PFrancavilla
   */
  private BigDecimal checkPromoCode(BigDecimal total, String promoCode) {
    return nonNull(promoCode) && PROMOCODE.equalsIgnoreCase(promoCode) ? ZERO : total;
  }

}
