package com.r3pi.assessment.checkout.controller;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.r3pi.assessment.checkout.CheckoutApplication;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = CheckoutApplication.class)
@SpringBootTest
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class CheckoutControllerTest {

  private MockMvc mockMvc;

  @Autowired
  private WebApplicationContext wac;

  @Before
  public void setup() {
    this.mockMvc = MockMvcBuilders.webAppContextSetup(wac).build();

  }

  private static String receiptEndpoint = "/api/v1/checkout/receipt";
  
  @Test
  public void test001_verifyReceiptSuccess() throws Exception {
    mockMvc
        .perform(MockMvcRequestBuilders.post(receiptEndpoint)
            .contentType(MediaType.APPLICATION_JSON)
            .content("{\"itemList\" : [{\"itemCode\": \"x\", \"name\": \"x\", \"price\": \"0.1\", \"quantity\": \"1\"}]}")
            .accept(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk()).andExpect(jsonPath("$").exists())
        .andExpect(jsonPath("$.itemRowList").isNotEmpty()).andDo(print());
  }

  @Test
  public void test002_verifyReceiptErrorNegativePrice() throws Exception {
    mockMvc
        .perform(MockMvcRequestBuilders.post(receiptEndpoint)
            .contentType(MediaType.APPLICATION_JSON)
            .content("{\"itemList\" : [{\"itemCode\": \"x\", \"name\": \"x\", \"price\": \"-0.1\", \"quantity\": \"1\"}]}")
            .accept(MediaType.APPLICATION_JSON))
        .andExpect(status().isBadRequest()).andExpect(jsonPath("$").exists())
        .andExpect(jsonPath("$.success").value(false))
        .andExpect(jsonPath("$.errorMessage").value("Item price must be positive."))
        .andDo(print());
  }

  @Test
  public void test003_verifyReceiptErrorNullPrice() throws Exception {
    mockMvc
        .perform(MockMvcRequestBuilders.post(receiptEndpoint)
            .contentType(MediaType.APPLICATION_JSON)
            .content("{\"itemList\" : [{\"itemCode\": \"x\", \"name\": \"x\", \"price\": null, \"quantity\": \"1\"}]}")
            .accept(MediaType.APPLICATION_JSON))
        .andExpect(status().isBadRequest()).andExpect(jsonPath("$").exists())
        .andExpect(jsonPath("$.success").value(false))
        .andExpect(jsonPath("$.errorMessage").value("Item price cannot be null."))
        .andDo(print());
  }

  @Test
  public void test004_verifyReceiptErrorMissingPrice() throws Exception {
    mockMvc
        .perform(MockMvcRequestBuilders.post(receiptEndpoint)
            .contentType(MediaType.APPLICATION_JSON)
            .content("{\"itemList\" : [{\"itemCode\": \"x\", \"name\": \"x\", \"quantity\": \"1\"}]}")
            .accept(MediaType.APPLICATION_JSON))
        .andExpect(status().isBadRequest()).andExpect(jsonPath("$").exists())
        .andExpect(jsonPath("$.success").value(false))
        .andExpect(jsonPath("$.errorMessage").value("Item price cannot be null."))
        .andDo(print());
  }
  
  @Test
  public void test005_verifyReceiptErrorEmptyName() throws Exception {
    mockMvc
        .perform(MockMvcRequestBuilders.post(receiptEndpoint)
            .contentType(MediaType.APPLICATION_JSON)
            .content("{\"itemList\" : [{\"itemCode\": \"x\", \"name\": \"\", \"price\": 0.1, \"quantity\": \"1\"}]}")
            .accept(MediaType.APPLICATION_JSON))
        .andExpect(status().isBadRequest()).andExpect(jsonPath("$").exists())
        .andExpect(jsonPath("$.success").value(false))
        .andExpect(jsonPath("$.errorMessage").value("Item name cannot be empty."))
        .andDo(print());
  }
  
  @Test
  public void test006_verifyReceiptErrorNullName() throws Exception {
    mockMvc
        .perform(MockMvcRequestBuilders.post(receiptEndpoint)
            .contentType(MediaType.APPLICATION_JSON)
            .content("{\"itemList\" : [{\"itemCode\": \"x\", \"name\": null, \"price\": 0.1, \"quantity\": \"1\"}]}")
            .accept(MediaType.APPLICATION_JSON))
        .andExpect(status().isBadRequest()).andExpect(jsonPath("$").exists())
        .andExpect(jsonPath("$.success").value(false))
        .andExpect(jsonPath("$.errorMessage").value("Item name cannot be empty."))
        .andDo(print());
  }
  
  @Test
  public void test007_verifyReceiptErrorEmptyCode() throws Exception {
    mockMvc
        .perform(MockMvcRequestBuilders.post(receiptEndpoint)
            .contentType(MediaType.APPLICATION_JSON)
            .content("{\"itemList\" : [{\"itemCode\": \"\", \"name\": \"x\", \"price\": 0.1, \"quantity\": \"1\"}]}")
            .accept(MediaType.APPLICATION_JSON))
        .andExpect(status().isBadRequest()).andExpect(jsonPath("$").exists())
        .andExpect(jsonPath("$.success").value(false))
        .andExpect(jsonPath("$.errorMessage").value("Item code cannot be empty."))
        .andDo(print());
  }
  
  @Test
  public void test008_verifyReceiptErrorNullCode() throws Exception {
    mockMvc
        .perform(MockMvcRequestBuilders.post(receiptEndpoint)
            .contentType(MediaType.APPLICATION_JSON)
            .content("{\"itemList\" : [{\"itemCode\": null, \"name\": \"x\", \"price\": 0.1, \"quantity\": \"1\"}]}")
            .accept(MediaType.APPLICATION_JSON))
        .andExpect(status().isBadRequest()).andExpect(jsonPath("$").exists())
        .andExpect(jsonPath("$.success").value(false))
        .andExpect(jsonPath("$.errorMessage").value("Item code cannot be empty."))
        .andDo(print());
  }
  
  @Test
  public void test009_verifyReceiptErrorNegativeQuantity() throws Exception {
    mockMvc
        .perform(MockMvcRequestBuilders.post(receiptEndpoint)
            .contentType(MediaType.APPLICATION_JSON)
            .content("{\"itemList\" : [{\"itemCode\": \"x\", \"name\": \"x\", \"price\": 0.1, \"quantity\": \"-1\"}]}")
            .accept(MediaType.APPLICATION_JSON))
        .andExpect(status().isBadRequest()).andExpect(jsonPath("$").exists())
        .andExpect(jsonPath("$.success").value(false))
        .andExpect(jsonPath("$.errorMessage").value("Item quantity must be at least 1."))
        .andDo(print());
  }
  
  @Test
  public void test010_verifyReceiptErrorZeroQuantity() throws Exception {
    mockMvc
        .perform(MockMvcRequestBuilders.post(receiptEndpoint)
            .contentType(MediaType.APPLICATION_JSON)
            .content("{\"itemList\" : [{\"itemCode\": \"x\", \"name\": \"x\", \"price\": 0.1, \"quantity\": \"0\"}]}")
            .accept(MediaType.APPLICATION_JSON))
        .andExpect(status().isBadRequest()).andExpect(jsonPath("$").exists())
        .andExpect(jsonPath("$.success").value(false))
        .andExpect(jsonPath("$.errorMessage").value("Item quantity must be at least 1."))
        .andDo(print());
  }

  @Test
  public void test011_verifyReceiptErrorNullQuantity() throws Exception {
    mockMvc
        .perform(MockMvcRequestBuilders.post(receiptEndpoint)
            .contentType(MediaType.APPLICATION_JSON)
            .content("{\"itemList\" : [{\"itemCode\": \"x\", \"name\": \"x\", \"price\": 0.1, \"quantity\": null}]}")
            .accept(MediaType.APPLICATION_JSON))
        .andExpect(status().isBadRequest()).andExpect(jsonPath("$").exists())
        .andExpect(jsonPath("$.success").value(false))
        .andExpect(jsonPath("$.errorMessage").value("Item quantity cannot be null."))
        .andDo(print());
  }

  @Test
  public void test012_verifyReceiptErrorMissingQuantity() throws Exception {
    mockMvc
        .perform(MockMvcRequestBuilders.post(receiptEndpoint)
            .contentType(MediaType.APPLICATION_JSON)
            .content("{\"itemList\" : [{\"itemCode\": \"x\", \"name\": \"x\", \"price\": 0.1}]}")
            .accept(MediaType.APPLICATION_JSON))
        .andExpect(status().isBadRequest()).andExpect(jsonPath("$").exists())
        .andExpect(jsonPath("$.success").value(false))
        .andExpect(jsonPath("$.errorMessage").value("Item quantity cannot be null."))
        .andDo(print());
  }
  
  @Test
  public void test013_verifyReceiptErrorMissingItemInCart() throws Exception {
    mockMvc
        .perform(MockMvcRequestBuilders.post(receiptEndpoint)
            .contentType(MediaType.APPLICATION_JSON)
            .content("{\"itemList\" : []}")
            .accept(MediaType.APPLICATION_JSON))
        .andExpect(status().isBadRequest()).andExpect(jsonPath("$").exists())
        .andExpect(jsonPath("$.success").value(false))
        .andExpect(jsonPath("$.errorMessage").value("Cart item list cannot be empty. Please pick at least one item."))
        .andDo(print());
  }
  
  @Test
  public void test014_verifyReceiptErrorNullItemList() throws Exception {
    mockMvc
        .perform(MockMvcRequestBuilders.post(receiptEndpoint)
            .contentType(MediaType.APPLICATION_JSON)
            .content("{\"itemList\" : null}")
            .accept(MediaType.APPLICATION_JSON))
        .andExpect(status().isBadRequest()).andExpect(jsonPath("$").exists())
        .andExpect(jsonPath("$.success").value(false))
        .andExpect(jsonPath("$.errorMessage").value("Cart item list cannot be empty. Please pick at least one item."))
        .andDo(print());
  }
  
  @Test
  public void test015_verifyReceiptErrorEmptyObject() throws Exception {
    mockMvc
        .perform(MockMvcRequestBuilders.post(receiptEndpoint)
            .contentType(MediaType.APPLICATION_JSON)
            .content("{}")
            .accept(MediaType.APPLICATION_JSON))
        .andExpect(status().isBadRequest()).andExpect(jsonPath("$").exists())
        .andExpect(jsonPath("$.success").value(false))
        .andExpect(jsonPath("$.errorMessage").value("Cart item list cannot be empty. Please pick at least one item."))
        .andDo(print());
  }
  
  @Test
  public void test016_verifyPromoCodeSuccess() throws Exception {
    mockMvc
        .perform(MockMvcRequestBuilders.post(receiptEndpoint)
            .contentType(MediaType.APPLICATION_JSON)
            .content("{\"itemList\" : [{\"itemCode\": \"x\", \"name\": \"x\", \"price\": \"0.1\", \"quantity\": \"1\"}], \"promoCode\" : \"HIRED\"}")
            .accept(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk()).andExpect(jsonPath("$").exists())
        .andExpect(jsonPath("$.total").value("0")).andDo(print());
  }

}