/*______________________________________________________________________________________
| This App Script Calculates the cost when a product is added via MSRP.                 \
| If a product is added via Cost, it calculates MSRP.                                    |
| By Calculating these fields using app scripts, they can be referenced from line items  |
|______________________________________________________________________________________*/

// If product is added to database using the MSRP, Calculate cost and markup
if(currentValues["Cost or MSRP"] == "MSRP") {
    // Save a static copy of MSRP
    currentValues["AppScript MSRP"] = currentValues["MSRP (Input)"] as double;
    // Update 2018 MSRP
    currentValues["2018 MSRP"] = currentValues["MSRP (Input)"] as double;
    // Update 2017 MSRP
    currentValues["2017 MSRP"] =  currentValues["MSRP (Input)"] as double;
    // Calculate cost using msrp and discount
    currentValues["Price (Cost)"] = (currentValues["MSRP (Input)"] as double) - ((currentValues["MSRP (Input)"] as double)*(currentValues["Discount"] as double));
    // Calculate Mark Up using algebra
    currentValues["Price (M/U)"] = ((currentValues["AppScript MSRP"] as double) / (currentValues["Price (Cost)"] as double)) - 1;
}
// If product is added to database using the cost, calculate MSRP
if(currentValues["Cost or MSRP"] == "Cost") {
    // Grab Cost
    double priceCost = currentValues["Price (Cost)"] as double;
    // Grab Markup
    double priceMarkup = currentValues["Price (M/U)"] as double;
    // Calculate MSRP and place in static container
    currentValues["AppScript MSRP"] = (priceCost + (priceCost*priceMarkup)).round(2);
    currentValues["2017 MSRP"] = (priceCost + (priceCost*priceMarkup)).round(2);
    currentValues["2018 MSRP"] = (priceCost + (priceCost*priceMarkup)).round(2);

}

// Set a few default values
currentValues["Exclude From Tax Calculation"] = 0; // Admin field only

// Create product number from links in order to capture a static value
String appScriptItemNumber = "";
//logger.info(appScriptItemNumber.toString())

appScriptItemNumber += (currentValues["Link to Products - Loc-Doc Item Categories"]["Category ID"] as String);
//logger.info(appScriptItemNumber.toString())

appScriptItemNumber += (currentValues["Link to Products - Manufacturer Prefix"]["Manufacturer #"] as int);
//logger.info(appScriptItemNumber.toString())

appScriptItemNumber += (currentValues["Number"] as String);
//logger.info(appScriptItemNumber.toString())

// Save Product number in static container
currentValues["AppScript Item Number"] = appScriptItemNumber;
