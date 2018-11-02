/*____________________________________________________________________________________/
/ This Script contains code for updating the inventory identifier when sub-locations  |
/ are switched.  Also contained within is the automated restock code.                 |
/____________________________________________________________________________________*/

// Grab Inventory Location Identifier
def getInventoryLocationIdentifier(Map product, Map inventoryLocation) {
    String s1 = product["AppScript Item Number"];
    String s2 = inventoryLocation["Master Location Name"];
    String s3 = inventoryLocation["Sub Location Name"];
    String invID = s1 + " " + s2 + " - " + s3;
    return invID;
}

// REMOVED DEPRECATED CHUNK - SEE APPSCRIPT V2.1 for this code block that was removed!

// Sub - Location change updates Identifier (for use with other app scripts)
if((previousValues["Link to Inventory - Sub Location"] != currentValues["Link to Inventory - Sub Location"]) && currentValues["SPLIT Non-Stock Items"] != "Yes") {
    // Update Identifier
    currentValues["Identifier"] = getInventoryLocationIdentifier(currentValues["Link to Product Database"] as Map, currentValues["Link to Inventory - Sub Location"] as Map) as String;
    /*if(currentValues["Type"] == "Non-Stock Items") {
        List taskBuilders = getChildren("Task Builder", "Link to Inventory") as List;
        logger.error("I'm here1");
        taskBuilders.each {it["Link to Inventory - Sub Location"] = currentValues["Link to Inventory - Sub Location"];}
    }*/
}

// Automated Ordering App Script HERE
// If available drops below Minimum Buffer - Order Maximum Buffer - Available by generating a task builder with this QTY Product.
if((currentValues["Minimum Buffer"] as int) > 0) { // Do not run for Non-Stock Items
    
    // if (available + already ordered + about to be ordered) < minimum buffer --> Order!
    if(((currentValues["Location_Quantity_Available"] as int) + (currentValues["Location_Quantity_To_Order"] as int) + (currentValues["Location_Quantity_Ordered"] as int)) < (currentValues["Minimum Buffer"] as int)) {
        // Calculate Order Quantity for this buffer overflow
        int orderQTY = (currentValues["Maximum Buffer"] as int) - (currentValues["Location_Quantity_Available"] as int);
        // Grab current qty to Order
        int tbOrderQTY = currentValues["Location_Quantity_To_Order"] as int;
        // THIS CODE NEEDS TESTING WITH MULTIPLE ORDERS OVERLAPPING FROM MULTIPLE LOCATIONS
        currentValues["Location_Quantity_To_Order"] = orderQTY;
        // Amount to actualy order on the task builder
        orderQTY = orderQTY - tbOrderQTY;
        // NO SERVICE service (for headless task builder)
        Map service = loadRecord "Services","Service","NO SERVICE";
        // Create the "Headless" Task Builder for linking to PO
        Map cTB = addChild "Task Builder","Link to Inventory",['Automated Restock' : 1,
                                                     'Link to Inventory - Sub Location' : currentValues["Link to Inventory - Sub Location"],
                                                     'Link to Product Database' : currentValues["Link to Product Database"],
                                                     'Link to Services' : service,
                                                     'Testing' : "Yes",
                                                     'Quantity (Product)' : orderQTY,
                                                     'Quantity (Service)' : 0];
        // Create "Audit" record of inventory processes
        addChild "Inventory Manager","Link to Inventory",['Arguments' : "Auto Restock",
                                                          'Link to Task Builder' : cTB,
                                                          'Quantity' : orderQTY];
    }
}


// For updating buffers, Minimum Buffer always equals half of maximum buffer! Super easy (Round up!)
if(previousValues["Maximum Buffer"] != currentValues["Maximum Buffer"]) {
    currentValues["Minimum Buffer"] = (currentValues["Maximum Buffer"] as int) / 2;
}



// Multiple NON - STOCK Items of the same type need to be separated so they can be assigned to different BINS
if(currentValues["SPLIT Non-Stock Items"] == "Yes" && (previousValues["Link to Inventory - Sub Location"] != currentValues["Link to Inventory - Sub Location"])) {
    List childTaskBuilders = getChildren("Task Builder","Link to Inventory") as List;
    Map firstTaskBuilder = childTaskBuilders[0] as Map;
    
    addChild "Inventory Manager","Link to Inventory",['Link to Inventory - Sub Location' : currentValues["Link to Inventory - Sub Location"],
                                 'Link to Product Database' : currentValues["Link to Product Database"],
                                 'Quantity' : firstTaskBuilder["Quantity (Product)"],
                                 'Arguments' : "Unsell"] as Map;
                                 
    addChild "Inventory Manager","Link to Inventory",['Link to Inventory - Sub Location' : previousValues["Link to Inventory - Sub Location"],
                                 'Link to Product Database' : currentValues["Link to Product Database"],
                                 'Quantity' : firstTaskBuilder["Quantity (Product)"],
                                 'Arguments' : "Sell"];

    logger.error((firstTaskBuilder["More Info"] as String));
    currentValues["Link to Inventory - Sub Location"] = previousValues["Link to Inventory - Sub Location"];
    currentValues["SPLIT Non-Stock Items"] = null;
    
}






