
/* global exchangerateurl */

var Converter = ( function() {

    return {
        
        convert: function(rates) {
            
            var input_t = document.getElementById("input").value;
            var in_code = document.getElementById("in_code").value;
            var out_code = document.getElementById("out_code").value;
            var data = rates["rates"];
            var s = "";
            
            if(input_t.length > 0 && !isNaN(input_t)) {
                
                var input = parseFloat(input_t);
                if(input > 0) {
                    
                    var convertedRate = data[out_code]*input;
                    
                    s += "<p>" + out_code + ": " + convertedRate.toFixed(2) + "</p>";                 
                    s += "<p>Based on " + rates["date"] + " exchange rates set by the Eurpoean Central Bank.</p>";
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
        
        getConversion: function(base_currency) {
            
            /*
             * This method sends an Ajax request to the exchange rates API to get
             * the latest exchange rates. Uses "latest" as the URL and "json" as
             * the data type, so that the data gets automatically parsed to a
             * JavaScript object. 
             */
            
            var exchangeratesurl = "https://api.exchangeratesapi.io/latest?base=";
            exchangeratesurl += base_currency;
            var that = this;
            
            $.ajax({
                url: exchangeratesurl,
                method: 'GET',
                dataType: 'json',
                success: function(response) {
                    console.log(response);
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