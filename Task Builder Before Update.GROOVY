/*______________________________________________/
/ Inventory Management Code Below!              /
/ Detect changes to Product, Inventory, or QTY  /
/______________________________________________*/

// Returns true if Product exists at inventory location
// Accepts String identifier - AppScript Item Number_Master Location Name_-_Sub Location Name
def productExistsAtInventoryLocation(String identifier) {
    Map findRecord = loadRecord "Inventory",['Identifier' : identifier] as Map;
    return findRecord;
}

// Accepts two inputs, Map of Product and Sub Location
// Returns the unique ID used in the Inventory Manager table
def getInventoryLocationIdentifier(Map product, Map inventoryLocation) {
    String s1 = product["AppScript Item Number"];
    String s2 = inventoryLocation["Master Location Name"];
    String s3 = inventoryLocation["Sub Location Name"];
    String invID = s1 + " " + s2 + " - " + s3;
    return invID;
}

// Only run the script if record is added via Task Builder Test Inventory
if(currentValues["Link to Task List"]) {
    // Ensure that this is not a quote task builder
    if((currentValues["Testing"] == "Yes") && (currentValues["Link to Task List"]["Parent Type"] != 2)) {
        // Ensure it is not either of the two special cases (NIS or DNE)
        if((currentValues["Overide"] == null) && (currentValues["Order NIS"] == null)) {
            logger.error("i'm here before update - overide is no");
    
            // If Inventory Record is selected, grab the product database record and sub location and move on!
            if(currentValues["Link to Inventory"]) {
                currentValues["Link to Product Database"] = currentValues["Link to Inventory"]["Link to Product Database"];
                currentValues["Link to Inventory - Sub Location"] = currentValues["Link to Inventory"]["Link to Inventory - Sub Location"];
            }
        }
        // If inventory system is being overridden, likely this is a part of "Unknown Origin"
        if(currentValues["Overide"] == "Yes") {
            logger.error("i'm here before update - overide is yes");
    
            // If Link to Inventory is blank, then overide is in use - map the sub-location to Unknown origin
            currentValues["Link to Inventory"] = null;
            // Although this inventory record will have a type of "Unknown Origin" the sub location will denote this as well
            Map invSubLoc = loadRecord "Inventory - Sub Location",['Sub Location Name' : "UNKNOWN ORIGIN", // No Home!
                                                                   'Location User' : currentValues["Last User"]]; // Who done it?
            currentValues["Link to Inventory - Sub Location"] = invSubLoc;
            currentValues["Overide"] = null; // Reset the override function now that the part has been selected via link to inventory.
            
            // Handles an overide switch.  A.K.A. a previously selected inventory item (of any type IS, NIS, or DNE) has been overridden
            // and a new product selected (NOTE PRODUCT NOT INVENTORY ITEM), then run this script
            if(previousValues["Link to Product Database"] != currentValues["Link to Product Database"]) { // Detect Part Swap
                String _identifier = previousValues["Link to Inventory"]["Identifier"] as String; // Grap previous identifier
                // Product switch but quantity is the same
                if(previousValues["Quantity (Product)"] == currentValues["Quantity (Product)"]) {
                    // If no product is being switched out, cancel if for some reason... ?? REVISIT$
                    if(_identifier.contains("_NO PRODUCT")) {
                        addChild "Inventory Manager","Link to Task Builder",['Link to Inventory - Sub Location' : previousValues["Link to Inventory - Sub Location"],
                                                 'Link to Product Database' : previousValues["Link to Product Database"],
                                                 'Quantity' : currentValues["Quantity (Product)"],
                                                 'Arguments' : "Cancel"];
                    }
                    // Add the unknown origin part into inventory
                    addChild "Inventory Manager","Link to Task Builder",['Link to Inventory - Sub Location' : invSubLoc,
                                             'Link to Product Database' : currentValues["Link to Product Database"],
                                             'Quantity' : currentValues["Quantity (Product)"],
                                             'Arguments' : "Add DNE"];
                    currentValues["Already Ran"] = 1;
                    // Then select it
                    addChild "Inventory Manager","Link to Task Builder",['Link to Inventory - Sub Location' : invSubLoc,
                                             'Link to Product Database' : currentValues["Link to Product Database"],
                                             'Quantity' : currentValues["Quantity (Product)"],
                                             'Arguments' : "Reserve"];
                }
                // Same as above but accounting for different quantities.
                if(previousValues["Quantity (Product)"] != currentValues["Quantity (Product)"]) {
                    if(_identifier.contains("_NO PRODUCT")) { 
                        addChild "Inventory Manager","Link to Task Builder",['Link to Inventory - Sub Location' : previousValues["Link to Inventory - Sub Location"],
                                                 'Link to Product Database' : previousValues["Link to Product Database"],
                                                 'Quantity' : previousValues["Quantity (Product)"],
                                                 'Arguments' : "Cancel"];
                    }
                    addChild "Inventory Manager","Link to Task Builder",['Link to Inventory - Sub Location' : invSubLoc,
                                             'Link to Product Database' : currentValues["Link to Product Database"],
                                             'Quantity' : currentValues["Quantity (Product)"],
                                             'Arguments' : "Add DNE"];
                    currentValues["Already Ran"] = 1;
                    addChild "Inventory Manager","Link to Task Builder",['Link to Inventory - Sub Location' : invSubLoc,
                                             'Link to Product Database' : currentValues["Link to Product Database"],
                                             'Quantity' : currentValues["Quantity (Product)"],
                                             'Arguments' : "Reserve"];
                }
            }
        }
        
        /*______________________________________________________________________________________________________/
        / This is Parts Request Generated ORDER NIS a.k.a. sam will select if from inventory or a project part  |
        / This code block depends on previous conditionals above - be wary of this when unit testing!           |
        /______________________________________________________________________________________________________*/
        
        // Trigger is only available on Purchasing form
        if((currentValues["Order NIS"] == "Yes") && (currentValues["Link to Task List"]["Parent Type"] != 2)) {
            //logger.error("i'm here before update - overide is yes");
            // If Link to Inventory is blank, then overide is in use - map the sub-location to Unknown origin
            currentValues["Link to Inventory"] = null;
            // Awaiting Arrival is the new NIS location, now that types are in use. -makes more sense when viewing inventory records
            Map invSubLoc = loadRecord "Inventory - Sub Location",['Sub Location Name' : "Not Ordered"];
            currentValues["Link to Inventory - Sub Location"] = invSubLoc;
            currentValues["Order NIS"] = null; // Reset the ORDER NIS field to null
            
            // If products are switched out (which they will be if this came from a parts request...)
            if(previousValues["Link to Product Database"] != currentValues["Link to Product Database"]) {
                // Grab inventory identifier for use below
                String _identifier = previousValues["Link to Inventory"]["Identifier"] as String;
                
                // If the quantity has not changed, use this code block
                if(previousValues["Quantity (Product)"] == currentValues["Quantity (Product)"]) {
                    // This is the base case - a parts request has been submitted and originates with No Product
                    if(_identifier.contains("_NO PRODUCT")) { // Check if base case
                        // Can probably remove this from the code as it serves literally no purpose!
                        addChild "Inventory Manager","Link to Task Builder",['Link to Inventory - Sub Location' : previousValues["Link to Inventory - Sub Location"],
                                                 'Link to Product Database' : previousValues["Link to Product Database"],
                                                 'Quantity' : currentValues["Quantity (Product)"],
                                                 'Arguments' : "Cancel"];
                    }
                    // Add the Non-Stock Item to inventory sub-location "Awaiting Arrival"
                    addChild "Inventory Manager","Link to Task Builder",['Link to Inventory - Sub Location' : invSubLoc,
                                             'Link to Product Database' : currentValues["Link to Product Database"],
                                             'Quantity' : currentValues["Quantity (Product)"],
                                             'Arguments' : "Add NIS"];
                    currentValues["Already Ran"] = 1; // This will stop the script from running twice
                }
                
                // If the quantity changes, use this code block!
                if(previousValues["Quantity (Product)"] != currentValues["Quantity (Product)"]) {
                    // Test for base case - note, QTY should not change on base case, however... TRUST NO ONE!
                    if(_identifier.contains("_NO PRODUCT")) { 
                        addChild "Inventory Manager","Link to Task Builder",['Link to Inventory - Sub Location' : previousValues["Link to Inventory - Sub Location"],
                                                 'Link to Product Database' : previousValues["Link to Product Database"],
                                                 'Quantity' : previousValues["Quantity (Product)"],
                                                 'Arguments' : "Cancel"];
                    }
                    // Add the Non-Stock Item to inventory sub-location "Awaiting Arrival"
                    addChild "Inventory Manager","Link to Task Builder",['Link to Inventory - Sub Location' : invSubLoc,
                                             'Link to Product Database' : currentValues["Link to Product Database"],
                                             'Quantity' : currentValues["Quantity (Product)"],
                                             'Arguments' : "Add NIS"];
                    currentValues["Already Ran"] = 1; // Prevents script from running twice
                }
            }
        }
        
    }
}

/*_____________________________________________________|
/ QUOTE converted to SITE VISIT will run this auto     |
/ search feature to select inventory items.            |
/_____________________________________________________*/
if((previousValues["Grand Parent Type"] == 2) && (currentValues["Grand Parent Type"] != 2) && (currentValues["Testing"] == "Yes")) {
    // Map Sam as user in order to pull inventory from Warehouse
    Map systemUser = loadRecord("Account Users", "Email", "sam@locdoc.net") as Map;
    // If Part exists in warehouse, invRecord will exist, other wise invRecord = null
    Map invRecord = loadRecord "Inventory",['Link to Product Database' : currentValues["Link to Product Database"],
                                            'Location User' : systemUser];
     
    // If inventory record is found in warehouse, select it on task builder from quote and reserve those parts!!!                                       
    if(invRecord != null) {
        // Reserve Existing Inventory parts - will generate an auto restock task builder if minimum buffer goes below
        addChild "Inventory Manager","Link to Task Builder",['Link to Inventory - Sub Location' : invRecord["Link to Inventory - Sub Location"],
                                                             'Link to Product Database' : currentValues["Link to Product Database"],
                                                             'Quantity' : currentValues["Quantity (Product)"],
                                                             'Arguments' : "Reserve"];
        // Map the stuff that needs mapping and set "From quote" to 1 in order to distinquish that these TB's are "Special"
        currentValues["Link to Inventory - Sub Location"] = invRecord["Link to Inventory - Sub Location"];
        currentValues["Link to Inventory"] = invRecord;
        currentValues["From Quote"] = 1; // Currently not used for anything... currently.
    }
    
    // If the parts from quote task builder do not exist in inventory that create Not in Stock orders for those items.
    if(invRecord == null) {
        // Map sub location awaiting arrival - SIDE NOTE: Non-Stock Items have no home in the warehouse and therefore get an
        // Awaiting arrival sub location with a non-stock item type from the inventory manager. - Stock items will simply need to
        // placed in their sub location with the "Job Parts" going into a bin.
        Map invSubLoc = loadRecord "Inventory - Sub Location",['Sub Location Name' : "Not Ordered",
                                                               'Location User' : systemUser];
        addChild "Inventory Manager","Link to Task Builder",['Link to Inventory - Sub Location' : invSubLoc,
                                                             'Link to Product Database' : currentValues["Link to Product Database"],
                                                             'Quantity' : currentValues["Quantity (Product)"],
                                                             'Arguments' : "Add NIS"]; // Inventory manager handles the Non-Stock Item tag.
        currentValues["Already Ran"] = 1; // Prevents the auto updating script from running twice.  
        /*addChild "Inventory Manager","Link to Task Builder",['Link to Inventory - Sub Location' : invSubLoc,
                                                             'Link to Product Database' : currentValues["Link to Product Database"],
                                                             'Quantity' : currentValues["Quantity (Product)"],
                                                             'Arguments' : "Reserve"];*/
        currentValues["Link to Inventory - Sub Location"] = invSubLoc;
        currentValues["From Quote"] = 2; // Currently not used yet.
    }
}

/*______________________________________________________/
/ Purchase order updates below, normalize TB to PO      |
/ Triggers inventory functions!                         |
/______________________________________________________*/

// If a purchase order is detected to be linked, grab its status!
if((previousValues["Link to Purchase Order"] == null) && (currentValues["Link to Purchase Order"] != null)) {
    currentValues["PO Status"] = currentValues["Link to Purchase Order"]["Status"];
}

// If a PO status changes to Ordered, run Inventory scripts to update inventory items
if((previousValues["PO Status"] != "Ordered") && (currentValues["PO Status"] == "Ordered") && (currentValues["Testing"] == "Yes")) {
    addChild "Inventory Manager","Link to Task Builder",['Link to Inventory - Sub Location' : currentValues["Link to Inventory"]["Link to Inventory - Sub Location"],
                                                             'Link to Product Database' : currentValues["Link to Product Database"],
                                                             'Quantity' : currentValues["Quantity (Product)"],
                                                             'Arguments' : "Order",
                                                             'UniqID' : currentValues["Link to Inventory"]["UniqID"]];
}

// If a PO is closed (a.k.a.) arrived, update inventory records.  This setup also supports partial arrival by triggering on the TB
if((previousValues["PO Status"] != "Closed") && (currentValues["PO Status"] == "Closed") && (currentValues["Testing"] == "Yes")) {
    //logger.error(currentValues["Link to Inventory"]["Link to Inventory - Sub Location"].toString());
    addChild "Inventory Manager","Link to Task Builder",['Link to Inventory - Sub Location' : currentValues["Link to Inventory - Sub Location"],
                                                             'Link to Product Database' : currentValues["Link to Product Database"],
                                                             'Quantity' : currentValues["Quantity (Product)"],
                                                             'Arguments' : "Arrived",
                                                             'UniqID' : currentValues["Link to Inventory"]["UniqID"]];
}












