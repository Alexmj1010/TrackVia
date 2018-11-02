// Set some Default Values

currentValues["AppScript PO Ordered_T"] = 0;
currentValues["AppScript_From_Parts_Request"] = 0;
currentValues["PO Status"] = "NO PO";

/*______________________________________________/
/ Inventory Management Code Below!              /
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
    if((currentValues["Testing"] == "Yes") && (currentValues["Link to Task List"]["Parent Type"] != 2)) {
        if(currentValues["Overide"] == null) {
            // If Inventory Record is selected, grab the product database record and sub location and move on!
            if(currentValues["Link to Inventory"]) {
                currentValues["Link to Product Database"] = currentValues["Link to Inventory"]["Link to Product Database"];
                currentValues["Link to Inventory - Sub Location"] = currentValues["Link to Inventory"]["Link to Inventory - Sub Location"];
            }
        }
        if(currentValues["Overide"] == "Yes") {
            // If Link to Inventory is blank, then overide is in use - map the sub-location to Unknown origin
            currentValues["Link to Inventory"] = null;
            Map invSubLoc = loadRecord "Inventory - Sub Location",['Sub Location Name' : "UNKNOWN ORIGIN",
                                                                   'Location User' : currentValues["Created By User"]];
            currentValues["Link to Inventory - Sub Location"] = invSubLoc;
        }
    
    }
}



