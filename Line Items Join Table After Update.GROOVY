// Instantiate some variables
String scTax = "TAX";
String cusTax = "TAX";
double taxRate = 0;

// Check if linked to Site Visit
if(currentValues["Link to Site Visit"]) {
    // Check if Location is SC
    if(currentValues["Link to Site Visit"]["Link to Location / Facility"]["State"] == "SC") {
        scTax = "NON";
    }
    // Check if Customer Is taxable for the line items
    if(currentValues["Link to Site Visit"]["Link to Customers"]["Taxable?"] == "NON") {
        cusTax = "NON";
        scTax = "NON";
    }
    taxRate = currentValues["Link to Site Visit"]["Link to Location / Facility"]["Link to Tax Rates"]["EstimatedCombinedRate"] as double;
}
// Check if linked to a quote and NOT a site visit
if((currentValues["Link to Quote Builder"] != null) && (currentValues["Link to Site Visit"] == null)) {
    // Check if Location is SC
    if(currentValues["Link to Quote Builder"]["Link to Location / Facility"]["State"] == "SC") {
        scTax = "NON";
    }
    // Check if Customer Is taxable for the line items
    if(currentValues["Link to Quote Builder"]["Link to Customers"]["Taxable?"] == "NON") {
        cusTax = "NON";
        scTax = "NON";
    }
    taxRate = currentValues["Link to Quote Builder"]["Link to Location / Facility"]["Link to Tax Rates"]["EstimatedCombinedRate"] as double;
}
// Check if Linked to a Counter Ticket 
if(currentValues["Link to Counter Ticket"]) {
    // Check if Customer Is taxable for the line items
    if(currentValues["Link to Counter Ticket"]["Link to Customers"]["Taxable?"] == "NON") {
        cusTax = "NON";
        scTax = "NON";
    }
    taxRate = 0.0725;
}


    
if((currentValues["Link to Task Builder"] != null) && (currentValues["Link to Task List"] != null)) {

    if((scTax == "NON") && (currentValues["Type"] == "Service")) {
        currentValues["Taxable"] = "NON";
    }
    
    if((currentValues["Link to Task Builder"]["Link to Product Database"]["Exclude From Tax Calculation"] == 1) && (currentValues["Type"] == "Product")) {
        currentValues["Taxable"] = "NON";
    }
    if(cusTax == "NON") {
        currentValues["Taxable"] = "NON";
    }
    if(currentValues["Taxable"] == "TAX") {
        currentValues["Tax"] = (((currentValues["Cost"] as double) * (currentValues["Quantity"] as double)).round(2) * taxRate).round(7);
    }
    currentValues["Total Cost"] = ((currentValues["Quantity"] as double) * (currentValues["Cost"] as double)).round(2);
    currentValues["Our Total Cost"] = (currentValues["Quantity"] as double) * (currentValues["Our Cost"] as double);
    currentValues["Profit Margin"] = (currentValues["Total Cost"] as double) - (currentValues["Our Total Cost"] as double);
    currentValues["Total With Tax"] = ((currentValues["Total Cost"] as double) + (currentValues["Tax"] as double)).round(2);
}

if((previousValues["Link to Task Builder"] != null) && (currentValues["Link to Task Builder"] == null)) {
    currentValues["Type"] = "Discard"
    currentValues["Total Cost"] = 0;
    currentValues["Our Total Cost"] = 0;
    currentValues["Profit Margin"] = 0;
    currentValues["Tax"] = 0;
    currentValues["Total With Tax"] = 0;
}

if((previousValues["Update Our Cost"] == null) && (currentValues["Update Our Cost"] == 1)) {
    currentValues["Our Cost"] = currentValues["Link to Task Builder"]["Adjusted Cost"];
    currentValues["Our Total Cost"] = (currentValues["Quantity"] as double) * (currentValues["Link to Task Builder"]["Adjusted Cost"] as double);
}
