
var Converter = ( function() {

    return {

        convert: function(rates) {
            
            /*
             * This function accepts the data object sent by the server
             * ("rates") as an argument.
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
             * This method sends an AJAX request to the API to get the
             * latest exchange rates.  Uses "latest" as the URL and "json" as the
             * data type, so that the data will be automatically parsed to a
             * JavaScript object.
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