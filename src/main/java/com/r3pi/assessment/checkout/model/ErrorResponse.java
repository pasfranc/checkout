package com.r3pi.assessment.checkout.model;

public class ErrorResponse{
  
  private String errorMessage;
  private Boolean success = false;
  
  public ErrorResponse(String errorMessage){
    this.errorMessage = errorMessage;
  }

  public String getErrorMessage() {
    return errorMessage;
  }
  
  public Boolean getSuccess() {
    return success;
  }

  @Override
  public String toString() {
    StringBuilder builder = new StringBuilder();
    builder.append("ErrorResponse [errorMessage=");
    builder.append(errorMessage);
    builder.append(", getErrorMessage()=");
    builder.append(getErrorMessage());
    builder.append(", getSuccess()=");
    builder.append(getSuccess());
    builder.append(", getClass()=");
    builder.append(getClass());
    builder.append(", hashCode()=");
    builder.append(hashCode());
    builder.append(", toString()=");
    builder.append(super.toString());
    builder.append("]");
    return builder.toString();
  }

}
