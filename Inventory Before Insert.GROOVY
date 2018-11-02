/*____________________________________________________________________________/
/ This Script sets some default values for a newly created Inventory record   |
/ By setting them here, less code duplication...                              |
/____________________________________________________________________________*/

// If a record is not created with a value existing for To Order Quantity (Like a Non-Stock Item)
if(currentValues["Location_Quantity_To_Order"] == null) {
    currentValues["Location_Quantity_To_Order"] = 0; // Default 0
}

// If a record is not created with a value existing for Ordered Quantity (Like a Non-Stock Item)
if(currentValues["Location_Quantity_Ordered"] == null) {
    currentValues["Location_Quantity_Ordered"] = 0; // Default 0
}

// Maximum and Minimum Buffer always Initialize to Zero
currentValues["Maximum Buffer"] = 0;
currentValues["Minimum Buffer"] = 0;

// RGA should not have NULL values - initialize to No
currentValues["RGA"] = "No";

Map uniqIDRecord = loadRecord "Unique Identifier Generator","For Table","Inventory";
int uniqID = uniqIDRecord["UniqID"] as int;
currentValues["UniqID"] = uniqID;
uniqID = uniqID + 1;
uniqIDRecord["UniqID"] = uniqID;

