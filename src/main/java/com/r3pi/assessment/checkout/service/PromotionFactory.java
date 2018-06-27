package com.r3pi.assessment.checkout.service;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import javax.annotation.PostConstruct;

import org.springframework.stereotype.Component;

/**
 * This class is used to configure and retrieve promotions based on item key
 * 
 * @author PFrancavilla
 */
@Component
public class PromotionFactory {
  
  private Map<String, Promotion> strategies;
    
  /**
   * This method is called at startup and it is initializing promotions
   * 
   * @author PFrancavilla
   */
  @PostConstruct
  public void init() {
    strategies = new HashMap<>();
    strategies.put("ban001", new BuyXPayYPromotion(3,2));
    strategies.put("ora001", new PercentageDiscountPromotion(40));
  }
  
  /**
   * This method is used to retrieve promotions based on item key
   * 
   * @author PFrancavilla
   */
  public Optional<Promotion> findStrategyForKey(String key) {
    return Optional.ofNullable(strategies.get(key));
  }

}
