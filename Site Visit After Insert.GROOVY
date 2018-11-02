// After creating a site visit - add the service charge with line items total already calculated

if(currentValues["Link to Service Call"] != null) { // As long as it isn't no charge
    if(currentValues["Link to Service Call"]["Service Name"] != "No Charge") {
        // Create a place holder, default value is taxable
        String scTax = "TAX";
        String cusTax = "TAX";
        // Calculate trip charge tax for this site visit based on tax rate for location
        double taxAmount = ((currentValues["Link to Service Call"]["Cost"] as double) * (currentValues["Link to Location / Facility"]["Link to Tax Rates"]["EstimatedCombinedRate"] as double)).round(7);
        // If Location is SC, set scTax to NON and taxAmount to 0
        if(currentValues["Link to Location / Facility"]["State"] == "SC") {
            scTax = "NON"; // QBO value NON for not taxable and TAX for taxable
            taxAmount = 0; // use zero for tax in total cost calc...
        }
        
        // Check if Customer Is taxable for the trip charge
        if(currentValues["Link to Customers"]["Taxable?"] == "NON") {
            cusTax = "NON";
            scTax = "NON";
            taxAmount = 0;
        }
        addChild "Line Items Join Table","Link to Site Visit",['Type' : "Service",
                                              'Identifier' : currentValues["Link to Service Call"]["Service Name"],
                                              'Description' : currentValues["Link to Service Call"]["Service Name"],
                                              'Quantity' : 1,
                                              'Taxable' : scTax, // Grab if line item is taxable or not from above
                                              'QBO_ID' : currentValues["Link to Service Call"]["QBO_Ref_ID"],
                                              'Our Cost' : 0, // Trip charges don't not hold company cost
                                              'Task Name' : "Trip Charge",
                                              //'Margin' : currentValues["Link to Service Call"]["Cost"],
                                              //'Link to Task Builder' : _this,
                                              'Tax' : taxAmount, // Will be zero if SC
                                              'Total Cost' : currentValues["Link to Service Call"]["Cost"],
                                              'Our Total Cost' : 0,
                                              'Profit Margin' : currentValues["Link to Service Call"]["Cost"],
                                              'Total With Tax' : (taxAmount + (currentValues["Link to Service Call"]["Cost"] as double)).round(2),
                                              'Cost' : currentValues["Link to Service Call"]["Cost"]];
    }
}
