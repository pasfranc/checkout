package com.r3pi.assessment.checkout.controller;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.r3pi.assessment.checkout.model.Cart;
import com.r3pi.assessment.checkout.model.Receipt;
import com.r3pi.assessment.checkout.service.ReceiptService;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

/**
 * This class is used to expose a rest endpoint to provide a receipt based on a cart
 * containing item list with quantities.
 * 
 * @author PFrancavilla
 */
@RequestMapping(value = "/api/v1/checkout")
@RestController
public class CheckoutController {
  
  @Autowired
  private ReceiptService receiptBuilderService;
  
  private final Logger logger = LoggerFactory.getLogger(this.getClass());

  @ApiOperation(value = "Calculate total and build receipt", notes = "This method will calculate checkout total and build receipt object", response = Receipt.class, tags = {
      "checkout-ui",})
  @ApiResponses(value = {@ApiResponse(code = 200, message = "Receipt", response = Receipt.class)})
  @RequestMapping(value = "/receipt", method = RequestMethod.POST, produces = "application/json")
  @ResponseBody
  public ResponseEntity<Receipt> buildReceipt(
      @ApiParam(value = "Cart object", required = true) @Valid @RequestBody(required = true) Cart cart) {
    
    logger.debug(".buildReceipt() /receipt Request: {}", cart);
    
    Receipt receipt = receiptBuilderService.buildReceipt(cart.getItemList(), cart.getPromoCode());

    logger.debug(".buildReceipt() /receipt Response: {}", receipt);
    
    return new ResponseEntity<>(receipt, HttpStatus.OK);
  }
}
