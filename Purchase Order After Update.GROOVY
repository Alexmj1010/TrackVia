/*______________________________________________________________________________________
| This appscript updates task builder children of Purchase Order that the parts from    \
| A parts request have been ordered. (or from a quote)                                   |
|______________________________________________________________________________________*/

// Grab todays date
def date = new Date();

// If Purchase order changes to ordered status
if(currentValues["Status"] == "Ordered"  && previousValues["Status"] != "Ordered") {
    // Add child task builders to a list
    List taskBuilders = getChildren("Task Builder", "Link to Purchase Order") as List;
    // Update "AppScript PO Ordered_T" field
    taskBuilders.each {it["AppScript PO Ordered_T"] = 1}
    currentValues["Ordered Date"] = date;
}

// Detect change from null to Yes on field "Generate QR Codes" 
if((previousValues["Generate QR Codes"] == null) && (currentValues["Generate QR Codes"] == "Yes")) {
    int numSpaces = currentValues["Number of Used Labels"] as int;
    for(int i = 0; i < numSpaces; i++) {
        addChild "QR Codes", "Link to Purchase Order",['Item Number' : "-"];
    }
    List taskBuilders = getChildren("Task Builder", "Link to Purchase Order") as List;
    //List itemNumbers = [];
    taskBuilders.each {
        if(it["Link to Product Database"]["Common Name"] != "NO PRODUCT") {
            it["Generate QR Code"] = 1;
        }
    }
}

if(previousValues["Status"] != currentValues["Status"]) {
    List taskBuilders = getChildren("Task Builder", "Link to Purchase Order") as List;
    taskBuilders.each {it["PO Status"] = currentValues["Status"];}
}
