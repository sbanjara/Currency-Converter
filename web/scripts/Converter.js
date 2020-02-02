
var Converter = ( function() {

    return {

        convert: function(rates) {
            
            /*
             * This function accepts the data object sent by the server
             * ("rates") as an argument.  It should: get the amount (in USD)
             * entered by the user in the "input" form field, iterate through
             * the currency exchange rates given in the data object, multiply
             * each rateby the given number of U.S. Dollars, and compute the
             * corresponding amount for each currency.  These amounts should be`1   2qesssss
             * shown in the "output" element of the page, along with the
             * currency codes, separated by colons and formatted to two decimal
             * places.  (See the screenshot given with this assignment.)
             */
            
            var input_t = document.getElementById("input").value;
            var in_code = document.getElementById("in_code").value;
            var out_code = document.getElementById("out_code").value;
            var data = rates["rates"];
            var amount = 1;
            var s = "";
            
            if(input_t.length > 0 && !isNaN(input_t)) {
                
                var input = parseInt(input_t);
                if(input > 0) {
                    
                    for(var rate in data) {
                        
                        if(rate === in_code) {
                            amount = amount/data[rate];
                        }
                     
                    }
                    
                    var convertedRate = data[out_code]*input*amount;
                    s += "<p>" + out_code + ": " + convertedRate.toFixed(2) + "</p>";                 

                    s += "<p>Based on " + rates["date"] + " Exchange Rates.</p>";
                    $('#output').html(s);
                    
                } 
                else {
                    $('#output').html("Invalid Entry!! Please enter a positive integer!!");
                    document.getElementById("input").value = "";
                }
                
            }
            else {
                $('#output').html("Invalid Entry!! Please enter a positive integer!!");
                document.getElementById("input").value = "";
            }
               
        },
        
        getConversion: function() {
            
            /*
             * This method should send an Ajax request to our API to get the
             * latest exchange rates.  Use "latest" as the URL and "json" as the
             * data type, so that the data will be automatically parsed to a
             * JavaScript object.  (In the sample code in the "HTTP Basics"
             * lecture notes, this object is called "response".  Then, invoke
             * the helper function "convert()" in the callback function to 
             * perform the conversion.  (If you are unclear about the purpose of
             * the "that" variable shown here, see Page 6 of the "Functions and
             * Objects" lecture notes.
             */
            
            var that = this;
            
            $.ajax({
                url: 'latest',
                method: 'GET',
                dataType: 'json',
                success: function(response) {
                    that.convert(response);                    
                }
            });
            
        },
        
        getAllconversion: function() {
            
            var that = this;
            
            $.ajax({
                
                url: 'latest',
                method: 'GET',
                dataType: 'json',
                success: function(response) {
                    
                    var input_t = document.getElementById("input_two").value;
                    var data = response["rates"];
                    var s = "";

                    if(input_t.length > 0 && !isNaN(input_t)) {

                        var input = parseInt(input_t);
                        if(input > 0) {

                            for(var rate in data) {
                                var convertedRate = data[rate]*input;
                                s += "<p>" + rate + ": " + convertedRate.toFixed(2) + "</p>";                 
                            }

                            s += "<p>Based on " + response["date"] + " Exchange Rates.</p>";
                            $('#output').html(s);

                        } 
                        else {
                            $('#output').html("Invalid Entry!! Please enter a positive integer!!");
                            document.getElementById("input").value = "";
                        }

                    }
                    else {
                        $('#output').html("Invalid Entry!! Please enter a positive integer!!");
                        document.getElementById("input").value = "";
                    }                   
                }
            });
            
        },
        
        init: function() {
            
            /* Output the current version of jQuery (for diagnostic purposes) */
            
            $('#output').html( "jQuery Version: " + $().jquery );
 
        }

    };

}());