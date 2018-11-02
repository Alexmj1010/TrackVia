/*_______________________________________________________________
| Inventory Manager Code - Record Insert is the same as calling |
| a function with parameters.  The fields in the record are     |
| being used as parameters in the function calls below.         |
| Supports Add, Remove, Reserve, Cancel, and Sell               |
 \_____________________________________________________________*/

// Returns true if Product exists at inventory location
// Accepts String identifier - AppScript Item Number_Master Location Name_-_Sub Location Name
def productExistsAtInventoryLocation(String identifier) {
    Map findRecord;
    if(currentValues["UniqID"]) {
        findRecord = loadRecord "Inventory",['UniqID' : currentValues["UniqID"]] as Map;
    } else {
        findRecord = loadRecord "Inventory",['Identifier' : identifier] as Map;
    }
    //Map findRecord = loadRecord "Inventory",['Identifier' : identifier] as Map;
    /*if(findRecord == null) {
        findRecord = loadRecord "Inventory",['UniqID': currentValues["UniqID"]] as Map;
    }*/
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

// updates global quantities based off of provided argument (add, remove, reserve, cancel) - no move
// accepts product, quantity, and argument as input - no location for global
def updateGlobalQuantities(Map product, int quantity, String arg) {
    
    // If adding product to inventory run this script
    if(arg == "Add") {
        // Get Values
        int globalProductOnHand = product["Quantity_On_Hand"] as int;
        int globalProductAvailable = product["Quantity_Available"] as int;
        // Update Them
        product["Quantity_On_Hand"] = globalProductOnHand + quantity;
        product["Quantity_Available"] = globalProductAvailable + quantity;
    }
    
    // If removing product from inventory, update the global values
    if(arg == "Remove") {
        // Get Values
        int globalProductOnHand = product["Quantity_On_Hand"] as int;
        int globalProductAvailable = product["Quantity_Available"] as int;
        // Update Them
        product["Quantity_On_Hand"] = globalProductOnHand - quantity;
        product["Quantity_Available"] = globalProductAvailable - quantity;
    }
    
    // If Product is selected to be used, reserve it
    if(arg == "Reserve") {
        // Get Values
        int globalProductReserved = product["Quantity_Reserved"] as int;
        int globalProductAvailable = product["Quantity_Available"] as int;
        // Update Them
        product["Quantity_Reserved"] = globalProductReserved + quantity;
        product["Quantity_Available"] = globalProductAvailable - quantity;
    }
    
    // If Product is changed or cancelled, cancel Inventory Reservation
    if(arg == "Cancel") {
        // Get Values
        int globalProductReserved = product["Quantity_Reserved"] as int;
        int globalProductAvailable = product["Quantity_Available"] as int;
        // Update Them
        product["Quantity_Reserved"] = globalProductReserved - quantity;
        product["Quantity_Available"] = globalProductAvailable + quantity;
    }
    
    // If Product is sold, Update Reserved and on Hand
    if(arg == "Sell") {
        // Get Values 
        int globalProductReserved = product["Quantity_Reserved"] as int;
        int globalProductOnHand = product["Quantity_On_Hand"] as int;
        // Update Them
        product["Quantity_Reserved"] = globalProductReserved - quantity;
        product["Quantity_On_Hand"] = globalProductOnHand - quantity;
    }
    
}


// updates local quantities based off of provided argument (add, remove, reserve, cancel, move)
// accepts product, quantity, location, and argument as input
def updateLocalQuantities(Map product, int quantity, Map inventoryLocation, String arg) {
    
    // Build Identifier to be used to Map inventory record
    String _identifier = getInventoryLocationIdentifier(product, inventoryLocation);
    // Map inventory Record, if it doesn't exist, inventoryRecord will be null;
    Map inventoryRecord = productExistsAtInventoryLocation(_identifier) as Map;
    
    // If adding product to inventory run this script
    if(arg == "Add") {
        
        // If all goes well locally, update the global values as well
        updateGlobalQuantities(product, quantity, arg);
        
        // If inventory location already has some stock with this product, update it
        if(inventoryRecord) { 
            // Get Values
            int localQuantityOnHand = inventoryRecord["Location_Quantity_On_Hand"] as int;
            int localQuantityAvailable = inventoryRecord["Location_Quantity_Available"] as int;
            // Update Them
            inventoryRecord["Location_Quantity_On_Hand"] = localQuantityOnHand + quantity;
            inventoryRecord["Location_Quantity_Available"] = localQuantityAvailable + quantity;
            
            // Link the Inventory Record to this record for auditing purposes
            currentValues["Link to Inventory"] = inventoryRecord;
        
        // If inventory location does not contain this product, create it    
        } else { 
            Map invRec = save "Inventory",['Location User' : currentValues["Link to Inventory - Sub Location"]["Location User"],
                                      'Link to Product Database' : product,
                                      'Link to Inventory - Sub Location' : inventoryLocation,
                                      'Identifier' : _identifier,
                                      'Location_Quantity_On_Hand' : quantity,
                                      'Location_Quantity_Reserved' : 0,
                                      'Location_Quantity_Available' : quantity,
                                      'Maximum Buffer' : currentValues["Maximum Buffer"],
                                      'Minimum Buffer' : currentValues["Minimum Buffer"],
                                      'Type' : "Stock Item"];
                                      
            // Link the Inventory Record to this record for auditing purposes
            currentValues["Link to Inventory"] = invRec;
            logger.error("i'm here at Add");
            //currentValues["Link to Task builder"]["Link to Inventory"] = invRec;
        }
    }
    
    // If product does not exist at location, run this script
    if(arg == "Add DNE") {
        logger.error("I'm here1!");
        updateGlobalQuantities(product, quantity, "Add");
        // If inventory location already has some stock with this product, update it
        if(inventoryRecord) { 
            // Get Values
            logger.error("I'm here2!");

            int localQuantityOnHand = inventoryRecord["Location_Quantity_On_Hand"] as int;
            int localQuantityAvailable = inventoryRecord["Location_Quantity_Available"] as int;
            // Update Them
            inventoryRecord["Location_Quantity_On_Hand"] = localQuantityOnHand + quantity;
            inventoryRecord["Location_Quantity_Available"] = localQuantityAvailable + quantity;
            
            // Link the Inventory Record to this record for auditing purposes
            currentValues["Link to Inventory"] = inventoryRecord;
        
        // If inventory location does not contain this product, create it    
        } else { 
            logger.error("I'm here3!");
            Map invRec = save "Inventory",['Location User' : currentValues["Link to Inventory - Sub Location"]["Location User"],
                                      'Link to Product Database' : currentValues["Link to Product Database"],
                                      'Link to Inventory - Sub Location' : currentValues["Link to Inventory - Sub Location"],
                                      'Identifier' : _identifier,
                                      'Location_Quantity_On_Hand' : currentValues["Quantity"],
                                      'Location_Quantity_Reserved' : 0,
                                      'Location_Quantity_Available' : currentValues["Quantity"],
                                      'Type' : "Unknown Origin"];
                                      
            // Link the Inventory Record to this record for auditing purposes
            currentValues["Link to Inventory"] = invRec;
                                      
            // Difference between add DNE and regular add is the linking to task builder and resetting of overide.
            // This essentially forces an inventory location to be selected, regardless of if it exists or not...
            currentValues["Link to Task Builder"]["Link to Inventory"] = invRec;
            currentValues["Link to Task Builder"]["Overide"] = null;
        }
    }
    
    // If removing a product from inventory, run this script
    if(arg == "Remove") {
        
        // If inventory record exists at this location, then update it
        if(inventoryRecord) {
            // Grab Values
            int localQuantityOnHand = inventoryRecord["Location_Quantity_On_Hand"] as int;
            int localQuantityAvailable = inventoryRecord["Location_Quantity_Available"] as int;
            // Update Them
            inventoryRecord["Location_Quantity_On_Hand"] = localQuantityOnHand - quantity;
            inventoryRecord["Location_Quantity_Available"] = localQuantityAvailable - quantity;
            // Temporarily store new Values for Error Checking
            localQuantityOnHand = inventoryRecord["Location_Quantity_On_Hand"] as int;
            localQuantityAvailable = inventoryRecord["Location_Quantity_Available"] as int;
            
            //Update global values if all goes well locally
            updateGlobalQuantities(product, quantity, arg);
            
            // Link the Inventory Record to this record for auditing purposes
            currentValues["Link to Inventory"] = inventoryRecord;

            // If all stock is removed from the location, delete the empty record (does not delete "Negative" stock)
            if((localQuantityOnHand <= 0) || (localQuantityAvailable <= 0)) {
                // Build Error Message
                String errorMessage = "Update Local Quantities REMOVE function: Inventory Depleted at " + _identifier + " QTY: " + quantity;
                // Add to Error Log with code 03
                addChild "Error Log","Link to Inventory Manager", ['Error ID' : "Inventory Manager 03",
                              'Error Message' : errorMessage];
            }
            logger.error("i'm here at Remove");
            
        // If product does not exist at location, print error message to logger.
        } else {
            // Build Error Message
            String errorMessage = "Update Local Quantities REMOVE function: Product does not exist at Location " + _identifier + " QTY: " + quantity;
            // Add to Error Log with code 01
            addChild "Error Log","Link to Inventory Manager", ['Error ID' : "Inventory Manager 01",
                              'Error Message' : errorMessage];
        }
    }
    
    // If product is selected to be used, Reserve It 
    if(arg == "Reserve") {

        // If inventory record exists at this location, then put the parts on reserve
        if(inventoryRecord) {
            // Grab Values
            int localQuantityReserved = inventoryRecord["Location_Quantity_Reserved"] as int;
            int localQuantityAvailable = inventoryRecord["Location_Quantity_Available"] as int;
            // Update Them
            inventoryRecord["Location_Quantity_Reserved"] = localQuantityReserved + quantity;
            inventoryRecord["Location_Quantity_Available"] = localQuantityAvailable - quantity;
            // If Not in Stock part, add quantity Reserved to Quantity Ordered...
            if(inventoryRecord["Type"] == "Non-Stock Item") {
                int localQuantityToOrder = inventoryRecord["Location_Quantity_To_Order"] as int;
                inventoryRecord["Location_Quantity_To_Order"] = localQuantityToOrder + quantity;
            }
            
            // Temporariliy Store new Values for error checking
            localQuantityReserved = inventoryRecord["Location_Quantity_Reserved"] as int;
            localQuantityAvailable = inventoryRecord["Location_Quantity_Available"] as int;
            // Also grab the quantity on hand for additional error checking
            int localQuantityOnHand = inventoryRecord["Location_Quantity_On_Hand"] as int;
            
            // Update Global values if all goes well locally
            updateGlobalQuantities(product, quantity, arg);
            
            // Link the Inventory Record to this record for auditing purposes
            currentValues["Link to Inventory"] = inventoryRecord;
            
            // insert an error message here if Reserved amount exceeds on hand, or available drops below zero
            if((localQuantityReserved > localQuantityOnHand) || (localQuantityAvailable < 0)) {
                // Build Error Message
                String errorMessage = "Update Local Quantities RESERVE function: Inventory Depleted at " + _identifier + " QTY: " + quantity;
                // Add to Error Log with code 04
                addChild "Error Log","Link to Inventory Manager", ['Error ID' : "Inventory Manager 04",
                              'Error Message' : errorMessage];
            }
        } else {
            // Build Error Message
            String errorMessage = "Update Local Quantities RESERVE function: Product does not exist at Location " + _identifier + " QTY: " + quantity;
            // Add to Error Log with code 02
            addChild "Error Log","Link to Inventory Manager", ['Error ID' : "Inventory Manager 02",
                              'Error Message' : errorMessage];
        }
    }
    
    // If product is cancelled or changed, cancel inventory reserved
    if(arg == "Cancel") {

        // If inventory record exists at this location, then cancel the reservation
        if(inventoryRecord) {
            // Grab Values
            int localQuantityReserved = inventoryRecord["Location_Quantity_Reserved"] as int;
            int localQuantityAvailable = inventoryRecord["Location_Quantity_Available"] as int;
            // Update Them
            inventoryRecord["Location_Quantity_Reserved"] = localQuantityReserved - quantity;
            inventoryRecord["Location_Quantity_Available"] = localQuantityAvailable + quantity;
            
            // Cancel the QTY Ordered for NIS Inventory Records swithced out.
            if(inventoryRecord["Type"] == "Non-Stock Item") {
                int localQuantityOrdered = inventoryRecord["Location_Quantity_Ordered"] as int;
                inventoryRecord["Location_Quantity_Ordered"] = localQuantityOrdered - quantity;
            }
            
            // Update Global Values if all goes well locally
            updateGlobalQuantities(product, quantity, arg);
            
            // Link the Inventory Record to this record for auditing purposes
            currentValues["Link to Inventory"] = inventoryRecord;
            
        } else {
            // Build Error Message
            String errorMessage = "Update Local Quantities CANCEL function: Product does not exist at Location " + _identifier + " QTY: " + quantity;
            // Add to Error Log with code 05
            addChild "Error Log","Link to Inventory Manager", ['Error ID' : "Inventory Manager 05",
                              'Error Message' : errorMessage];
        }
    }
    
    // If product is sold (trigger still not decided upon), Update reserved and on hand
    if(arg == "Sell") {
        
        // If Inventory Record Exists at this location, the Sell the products
        if(inventoryRecord) {
            // Grab Values
            int localQuantityReserved = inventoryRecord["Location_Quantity_Reserved"] as int;
            int localQuantityOnHand = inventoryRecord["Location_Quantity_On_Hand"] as int;
            // Update Them
            inventoryRecord["Location_Quantity_Reserved"] = localQuantityReserved - quantity;
            inventoryRecord["Location_Quantity_On_Hand"] = localQuantityOnHand - quantity;
            
            // Update Global Values if all goes well locally
            updateGlobalQuantities(product, quantity, arg);
            
            // Link the Inventory Record to this record for auditing purposes
            currentValues["Link to Inventory"] = inventoryRecord;

        } else {
            // Build Error Message
            String errorMessage = "Update Local Quantities SELL function: Product does not exist at Location " + _identifier + " QTY: " + quantity;
            // Add to Error Log with code 06
            addChild "Error Log","Link to Inventory Manager", ['Error ID' : "Inventory Manager 06",
                              'Error Message' : errorMessage];
        }
    }
    
    // If product is unsold (triggered by NIS Split Function...), Update reserved and on hand
    if(arg == "Unsell") {
        // If Inventory Record Exists at this location, the Sell the products
        if(inventoryRecord) {
            // Grab Values
            int localQuantityReserved = inventoryRecord["Location_Quantity_Reserved"] as int;
            int localQuantityOnHand = inventoryRecord["Location_Quantity_On_Hand"] as int;
            // Update Them
            inventoryRecord["Location_Quantity_Reserved"] = localQuantityReserved + quantity;
            inventoryRecord["Location_Quantity_On_Hand"] = localQuantityOnHand + quantity;
            
            // Update Global Values if all goes well locally
            //updateGlobalQuantities(product, quantity, arg);
            
            // Link the Inventory Record to this record for auditing purposes
            currentValues["Link to Inventory"] = inventoryRecord;

        } else {
            Map invRec = save "Inventory",['Location User' : currentValues["Link to Inventory - Sub Location"]["Location User"],
                                      'Link to Product Database' : product,
                                      'Link to Inventory - Sub Location' : inventoryLocation,
                                      'Identifier' : _identifier,
                                      'Location_Quantity_On_Hand' : quantity,
                                      'Location_Quantity_Reserved' : quantity,
                                      'Location_Quantity_Available' : 0,
                                      'Maximum Buffer' : 0,
                                      'Minimum Buffer' : 0,
                                      'Type' : "Non-Stock Item"];
                                      
            // Link the Inventory Record to this record for auditing purposes
            currentValues["Link to Inventory"] = invRec;
            logger.error("i'm here at Unsell");
            //currentValues["Link to Task builder"]["Link to Inventory"] = invRec;
        }
    }
    
    // If product is not on hand and not in inventory, run this script
    if(arg == "Add NIS") {
        logger.error("I'm hereNIS1!");
        updateGlobalQuantities(product, quantity, "Reserve");
        // If inventory location already has some stock with this product, update it
        // This is handdled on task builder level using a look up - but if for what ever reason it makes it to here, handle it!
        if(inventoryRecord) { 
            // Get Values
            logger.error("I'm hereNIS2!");

            int localQuantityReserved = inventoryRecord["Location_Quantity_Reserved"] as int;
            int localQuantityAvailable = inventoryRecord["Location_Quantity_Available"] as int;
            //TO-DO Add Updates to TO_ORDER
            int localQuantityToOrder = inventoryRecord["Location_Quantity_To_Order"] as int;
            inventoryRecord["Location_Quantity_To_Order"] = localQuantityToOrder + quantity;
            // Update Them
            inventoryRecord["Location_Quantity_Reserved"] = localQuantityReserved + quantity;
            inventoryRecord["Location_Quantity_Available"] = localQuantityAvailable - quantity;
            
            // Link the Inventory Record to this record for auditing purposes
            currentValues["Link to Inventory"] = inventoryRecord;
        
        // If inventory location does not contain this product, create it    
        } else {
            logger.error("I'm hereNIS3!");
            // Qty available for a non-stock inventory record is exactly the negative of how many are currently reserved.
            int qtyAvail = -1 * (currentValues["Quantity"] as int)
            // Create Non-Stock Inventory Record (Will be set to Awaiting Arrival Sub-Location) - oh yea!
            Map invRec = save "Inventory",['Location User' : currentValues["Link to Inventory - Sub Location"]["Location User"],
                                      'Link to Product Database' : currentValues["Link to Product Database"],
                                      'Link to Inventory - Sub Location' : currentValues["Link to Inventory - Sub Location"],
                                      'Identifier' : _identifier,
                                      'Location_Quantity_On_Hand' : 0,
                                      'Location_Quantity_Reserved' : currentValues["Quantity"],
                                      'Location_Quantity_To_Order' : currentValues["Quantity"],
                                      'Location_Quantity_Available' : qtyAvail,
                                      'Type' : "Non-Stock Item"];
            // This step needs testing
            currentValues["Link to Task Builder"]["Link to Inventory"] = invRec; // attempt?
            currentValues["Link to Inventory"] = invRec;
            ///////MAYBE
            //currentValues["Link to Task Builder"]["UniqID"] = currentValues["Link to Inventory"]["UniqID"];
        }
    }
    
    // Update the Inventory record when parts are ordered (Non-Stock or Stock Items)
    if(arg == "Ordered") {
        // Make sure the record exists!!
        if(inventoryRecord) {
            // Grab current Quantity to Order and Quantity Ordered
            int qTO = inventoryRecord["Location_Quantity_To_Order"] as int;
            int qO = inventoryRecord["Location_Quantity_Ordered"] as int;
            // Update those values!
            qTO = qTO - quantity;
            qO = qO + quantity;
            // Map the new values to the record
            inventoryRecord["Location_Quantity_To_Order"] = qTO;
            inventoryRecord["Location_Quantity_Ordered"] = qO;
            // Make sure this "Audit" exists linked to inventory record...
            currentValues["Link to Inventory"] = inventoryRecord;
        }
        //*************************************************************
        // NEED Error checking here if Inventory record does not exist.
        // Also, if the record type is "Non-Stock Item" - change the sub-location to Arrived!
        if(inventoryRecord["Type"] == "Non-Stock Item") {
            Map orderedLocation = loadRecord "Inventory - Sub Location","Sub Location Name","Ordered";
            inventoryRecord["Link to Inventory - Sub Location"] = orderedLocation;
        }
    }
    
    // Update the Inventory record when ordered parts arrive! 
    if(arg == "Arrived") {
        // Make sure inventory record exists!
        if(inventoryRecord) {
            // Grab required values for the calulation
            int qO = inventoryRecord["Location_Quantity_Ordered"] as int;
            int qA = inventoryRecord["Location_Quantity_Available"] as int;
            int qH = inventoryRecord["Location_Quantity_On_Hand"] as int;
            // Compute!
            qO = qO - quantity; // Quantity order goes down minus qty arrived
            qA = qA + quantity; // Quantity available goes up plus qty arrived
            qH = qH + quantity; // same for qty on hand
            // Update records values with new data
            inventoryRecord["Location_Quantity_Ordered"] = qO;
            inventoryRecord["Location_Quantity_Available"] = qA;
            inventoryRecord["Location_Quantity_On_Hand"] = qH;
            // Maintain audit trail
            currentValues["Link to Inventory"] = inventoryRecord;
        }
        
        // Also, if the record type is "Non-Stock Item" - change the sub-location to Arrived!
        if(inventoryRecord["Type"] == "Non-Stock Item") {
            Map arrivedLocation = loadRecord "Inventory - Sub Location","Sub Location Name","Arrived";
            inventoryRecord["Link to Inventory - Sub Location"] = arrivedLocation;
        }
    }
    
}


/*___________________________________________________________________/
/ App Script Driver Below - Need to update global qtys eventually... /
/___________________________________________________________________*/

// From form "add product" - used for loading products into inventory
if(currentValues["Options"] == "Add Products") {
    updateLocalQuantities(currentValues["Link to Product Database"] as Map, currentValues["Quantity"] as int, currentValues["Link to Inventory - Sub Location"] as Map, "Add" as String);
}

// From form "Move Inventory" - move inventory from inventory location to new sub location
if(currentValues["Options"] == "Move Products") {
    updateLocalQuantities(currentValues["Link to Inventory"]["Link to Product Database"] as Map, currentValues["Quantity"] as int, currentValues["Link to Inventory"]["Link to Inventory - Sub Location"] as Map, "Remove" as String);
    updateLocalQuantities(currentValues["Link to Inventory"]["Link to Product Database"] as Map, currentValues["Quantity"] as int, currentValues["Link to Inventory - Sub Location"] as Map, "Add" as String);
}

// Restock products to existing location
if(currentValues["Options"] == "Restock Existing") {
    updateLocalQuantities(currentValues["Link to Inventory"]["Link to Product Database"] as Map, currentValues["Quantity"] as int, currentValues["Link to Inventory"]["Link to Inventory - Sub Location"] as Map, "Add" as String);

}

// If a record is created on this table with the argument "Add"
if(currentValues["Arguments"] == "Add") {
    updateLocalQuantities(currentValues["Link to Product Database"] as Map, currentValues["Quantity"] as int, currentValues["Link to Inventory - Sub Location"] as Map, "Add" as String);
}

// If a record is created on this table with the argument "Remove"
if(currentValues["Arguments"] == "Remove") {
    updateLocalQuantities(currentValues["Link to Product Database"] as Map, currentValues["Quantity"] as int, currentValues["Link to Inventory - Sub Location"] as Map, "Remove" as String);
}

// If a record is created on this table with the argument "Reserve"
if(currentValues["Arguments"] == "Reserve") {
    updateLocalQuantities(currentValues["Link to Product Database"] as Map, currentValues["Quantity"] as int, currentValues["Link to Inventory - Sub Location"] as Map, "Reserve" as String);
}

// If a record is created on this table with the argument "Cancel"
if(currentValues["Arguments"] == "Cancel") {
    updateLocalQuantities(currentValues["Link to Product Database"] as Map, currentValues["Quantity"] as int, currentValues["Link to Inventory - Sub Location"] as Map, "Cancel" as String);
}

// If a record is created on this table with the argument "Sell"
if(currentValues["Arguments"] == "Sell") {
    updateLocalQuantities(currentValues["Link to Product Database"] as Map, currentValues["Quantity"] as int, currentValues["Link to Inventory - Sub Location"] as Map, "Sell" as String);
}

// If a record is created on this table with the argument "Unsell"
if(currentValues["Arguments"] == "Unsell") {
    updateLocalQuantities(currentValues["Link to Product Database"] as Map, currentValues["Quantity"] as int, currentValues["Link to Inventory - Sub Location"] as Map, "Unsell" as String);
}

// If a record is created on this table with the argument "Add DNE" - does not exist at sub location (for tech vans primarily)
// This is used for parts in hand that are not in inventory (ERROR!!)
if(currentValues["Arguments"] == "Add DNE") {
    updateLocalQuantities(currentValues["Link to Product Database"] as Map, currentValues["Quantity"] as int, currentValues["Link to Inventory - Sub Location"] as Map, "Add DNE" as String);
}

// If a record is created on this table with the argument "Add NIS" - not in stock (for warehouse primarily)
// This is similar to above "Add DNE" but makes the distinction that part is not in hand or in inventory system
if(currentValues["Arguments"] == "Add NIS") {
    updateLocalQuantities(currentValues["Link to Product Database"] as Map, currentValues["Quantity"] as int, currentValues["Link to Inventory - Sub Location"] as Map, "Add NIS" as String);
}

// If parts are checked in from inventory record using "Parts Arrived" update the global quantites to reflect this fact as well!
if(currentValues["Arguments"] == "Move to BIN") {
    updateGlobalQuantities(currentValues["Link to Product Database"] as Map, currentValues["Quantity"] as int, "Add" as String);
}

// If parts are ordered via a PO, update quantities with argument Ordered
if(currentValues["Arguments"] == "Order") {
    updateLocalQuantities(currentValues["Link to Product Database"] as Map, currentValues["Quantity"] as int, currentValues["Link to Inventory - Sub Location"] as Map, "Ordered" as String);
}

// If parts arrive from a PO = Closed status, update quanties with argument "Arrived"
if(currentValues["Arguments"] == "Arrived") {
    updateLocalQuantities(currentValues["Link to Product Database"] as Map, currentValues["Quantity"] as int, currentValues["Link to Inventory - Sub Location"] as Map, "Arrived" as String);
}





