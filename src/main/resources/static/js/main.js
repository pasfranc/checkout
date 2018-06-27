$(".quantity").on("keypress keyup blur",function (event) {
 $(this).val($(this).val().replace(/[^0-9\.]/g,''));
    if ((event.which != 46 || $(this).val().indexOf('.') != -1) && (event.which < 48 || event.which > 57)) {
        event.preventDefault();
    }
  });

$(".quantity").on("keypress keyup blur",function (event) {    
  $(this).val($(this).val().replace(/[^\d].+/, ""));
    if ((event.which < 48 || event.which > 57)) {
        event.preventDefault();
    }
  });

function increment(id){
  var inputValue = $('#'+id).val();
  inputValue++;
  changeValue(id,inputValue);
}

function decrement(id){
  var inputValue = $('#'+id).val();
  inputValue--;
  if (inputValue >= 0){
    changeValue(id,inputValue);
  }
}

function formatAsMoney(value){
  return parseFloat(Math.round(value * 100) / 100).toFixed(2);
}

function changeValue(id, inputValue){
  $('#'+id).val(inputValue);
}

function buildReceipt(){
  
  $('#receipt').hide();
  
  var requestBody = {itemList:[]};
  
  if ($('#appleQuantity').val() > 0){
    var apple = {
      "itemCode": "app001",
      "name": "Apple",
      "price": 0.25,
      "quantity": $('#appleQuantity').val()
    }
    requestBody.itemList.push(apple);
  }
  
  if ($('#bananaQuantity').val() > 0){
    var banana = {
      "itemCode": "ban001",
      "name": "Banana",
      "price": 0.30,
      "quantity": $('#bananaQuantity').val()
    }
    requestBody.itemList.push(banana);
  }
  
  if ($('#orangeQuantity').val() > 0){
    var orange = {
      "itemCode": "ora001",
      "name": "Orange",
      "price": 0.15,
      "quantity": $('#orangeQuantity').val()
    }
    requestBody.itemList.push(orange);
  }
  
  if ($('#papayaQuantity').val() > 0){
    var papaya = {
      "itemCode": "pap001",
      "name": "Papaya",
      "price": 0.50,
      "quantity": $('#papayaQuantity').val()
    }
    requestBody.itemList.push(papaya);
  }
  
  if($('#promoCode').val() !=''){
    requestBody.promoCode = $('#promoCode').val();
  }
  
  $.ajax({
    url: "/api/v1/checkout/receipt",
    type: "POST",
    data: JSON.stringify(requestBody),
    contentType: 'application/json',
    dataType: 'json', // lowercase is always preferered though jQuery does it, too.
    success: function(resp){
      
      $('#tbody').empty();
      var total = resp.total;
      if(total==0){
        $('#total').text('FREE PROMO CODE');
        $('#successDiv').show();
        setTimeout(function(){
          $('#successDiv').hide();
        }, 5000);
      } else {
        $('#total').text(formatAsMoney(resp.total) + ' CHF');
      }

      
      for (var rowIndex in resp.itemRowList){
        $('#tbody').append('<tr><td class="border-0">'+resp.itemRowList[rowIndex].rowDescription+'</td>'+
            '<td class="border-0 text-right">'+formatAsMoney(resp.itemRowList[rowIndex].rowPrice)+' CHF</td></tr>');
        
        if(resp.itemRowList[rowIndex].rowPromotionDescription != null){
          $('#tbody').append('<tr><td class="border-0">'+resp.itemRowList[rowIndex].rowPromotionDescription+'</td>'+
              '<td class="border-0 text-right">'+formatAsMoney(resp.itemRowList[rowIndex].rowPromotionDiscount)+' CHF</td></tr>');
        }
      }
      
      $('#receipt').show();
    },
    error: function(err){
      var error = jQuery.parseJSON( err.responseText );
      $('#errorDiv').show();
      $('#errorText').text(error.errorMessage);
      setTimeout(function(){
        $('#errorDiv').hide();
      }, 5000);
    }
  });

}
