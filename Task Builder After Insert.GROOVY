/*___________________________________________________________________________________________________|
| This AppScript performs several tasks upon insertion of a new task builder record.                 |
| Grabs links, creates initial line item, tracks parts, and updates inventory values (BETA)          |
|__________________________________________________________________________________________________*/

    
// Kept from old inventory manager, not a useless piece of code!!
/*currentValues["Grand Parent Type"] = currentValues["Link to Task List"]["Parent Type"];
String commonName = "";*/


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


// Check for one of Four scenario's - Link to Quote, Link to SV, Link to CT, or Mixed SV and Quote
def getSiteVisitLink() {
    // Implicit Return
    Map link = currentValues["Link to Task List"]["Link to Site Visit"] as Map;
}
def getQuoteLink() {
    Map link = currentValues["Link to Task List"]["Link to Quote Builder"] as Map;
}
def getCounterLink() {
    Map link = currentValues["Link to Task List"]["Link to Counter Ticket"] as Map;
} // These functions are used below to quickly grab links when creating line items

def getPricingYear() {
    String pricingYear = "";
    if(getSiteVisitLink() != null) {
        pricingYear = currentValues["Link to Task List"]["Link to Site Visit"]["Pricing Year"];
    }
    if(getQuoteLink() != null) {
        pricingYear = currentValues["Link to Task List"]["Link to Quote Builder"]["Pricing Year"];
    }
    if(getCounterLink() != null) {
        pricingYear = currentValues["Link to Task List"]["Link to Counter Ticket"]["Pricing Year"];
    }
    return pricingYear;
}

if(currentValues["Link to Task List"]) {
    currentValues["Grand Parent Type"] = currentValues["Link to Task List"]["Parent Type"];
    String commonName = "";
// As long as task builder product is not "No Product" - add that line item!
if(currentValues["Link to Product Database"]["Common Name"] != "NO PRODUCT") {
    // Instantiate variables for use below
    double tempcost = 0.0;
    double tempOurCost = 0.0;
    // Set at 99999 to be used if QBO_Ref_ID is not found (a.k.a. a new product)
    String tempQBO = 99999; 
    
    // If Special Pricing has been added to this task builder, then calculate cost
    if(currentValues["Adjusted Cost"] != null) {
        // Temporarily store cost for later usage in creation of line item.
        tempcost = ((currentValues["Adjusted Cost"] as double) * (1.0 + (currentValues["Adjust Markup"] as double))) as double;
        tempOurCost = currentValues["Adjusted Cost"] as double;
    }
    // If QBO_Ref_ID exists for linked product, overwrite value of 99999
    if(currentValues["Link to Product Database"]["QBO_Ref_ID"] != null) {
        tempQBO = currentValues["Link to Product Database"]["QBO_Ref_ID"];
    }
    // If Product is added using Price(Cost) - simply grab that value
    if(currentValues["Link to Product Database"]["Price (Cost)"] != null) {
        tempOurCost = currentValues["Link to Product Database"]["Price (Cost)"] as double;
    }
    // If Product is added using MSRP and Discount, reverse calculate cost
    if(currentValues["Link to Product Database"]["Price (Cost)"] == null) {
        tempOurCost = ((currentValues["Link to Product Database"]["MSRP (Input)"] as double) * (currentValues["Link to Product Database"]["Discount"] as double));
    }
    
    
    // If no Special pricing, business as usual
    /////////////////////////INSERT PRICING YEAR CODE HERE!!!!/////////////////////////////
    if(currentValues["Adjusted Cost"] == null) {
        //logger.error(getPricingYear().toString());
        if(getPricingYear() == "2017") {
            tempcost = (currentValues["Link to Product Database"]["2017 MSRP"]) as double;
        }
        if(getPricingYear() == "2018") {
            tempcost = (currentValues["Link to Product Database"]["2018 MSRP"]) as double;
        }
        if(getPricingYear() == "Use Latest") {
            tempcost = (currentValues["Link to Product Database"]["AppScript MSRP"]) as double;
        }
    }
    
    
    // Add Product Line Item using values above and linked parent values
    addChild "Line Items Join Table","Link to Task Builder",['Type' : "Product",
                                  'Identifier' : currentValues["Link to Product Database"]["AppScript Item Number"], // PD Item Number
                                  'Description' : currentValues["Link to Product Database"]["Common Name"], // Product Name
                                  'Quantity' : currentValues["Quantity (Product)"], // Quantity
                                  'Taxable' : "TAX", // Start off as always taxable, will reconcile later
                                  'QBO_ID' : tempQBO, // Hopefully not 99999, will reconcile later
                                  'Link to Site Visit' : getSiteVisitLink(), // Grab SV Link if it exists
                                  'Link to Quote Builder' : getQuoteLink(), // Grab Quote Link if it exists
                                  'Link to Counter Ticket' : getCounterLink(), // Grab Counter Ticket Link if it Exists
                                  'Link to Task List' : currentValues["Link to Task List"], // Grab Task Link (Should always exists)
                                  'Our Cost' : tempOurCost, // Calculated above
                                  'Task Name' : currentValues["Link to Task List"]["Link to Site Visit Tasks"]["Task Name"],
                                  'Task Details' : currentValues["Link to Task List"]["Task Details"],
                                  'Tax' : 0, // Start off as 0, reconcile later
                                  //'Margin' : ((currentValues["Quantity (Product)"] as float) * (tempcost - tempOurCost)),
                                  //'Link to Task Builder' : _this,
                                  'Cost' : tempcost]; // Calculated Above
   
   
    /*___________________________/
    / Inventory Manager Code!!!  /
    /___________________________*/
    
    // Make sure test form is in use (not live yet)
    if(currentValues["Testing"] == "Yes") {
        // If overide is null, it holds that an Inventory record has been selected
        if((currentValues["Overide"] == null) && (currentValues["Link to Task List"]["Parent Type"] != 2)) {
            // Create child Inventory Manager record, which performs function calls in app scripts related to inventory table.
            addChild "Inventory Manager","Link to Task Builder",['Link to Inventory - Sub Location' : currentValues["Link to Inventory - Sub Location"],
                                 'Link to Product Database' : currentValues["Link to Product Database"],
                                 'Quantity' : currentValues["Quantity (Product)"],
                                 'Arguments' : "Reserve"];
        }
        // If Overide = yes, then add the products at the unknown origin location, afterwards reserve qty requested.
        if((currentValues["Overide"] == "Yes") && (currentValues["Link to Task List"]["Parent Type"] != 2)) {
            // Add to unknown origion
            addChild "Inventory Manager","Link to Task Builder",['Link to Inventory - Sub Location' : currentValues["Link to Inventory - Sub Location"],
                                 'Link to Product Database' : currentValues["Link to Product Database"],
                                 'Quantity' : currentValues["Quantity (Product)"],
                                 'Arguments' : "Add DNE"];
            // reserve from said unknown origin
            addChild "Inventory Manager","Link to Task Builder",['Link to Inventory - Sub Location' : currentValues["Link to Inventory - Sub Location"],
                                 'Link to Product Database' : currentValues["Link to Product Database"],
                                 'Quantity' : currentValues["Quantity (Product)"],
                                 'Arguments' : "Reserve"];
        }
        //currentValues["Overide"] = null;

    }
    
}

// As Long as a task builder service is not "No Service", add that line item
if(currentValues["Link to Services"]["Service"] != "NO SERVICE") {
    // Add Service Line Item using linked parent values
    // See comments on product child creation for explanations of values
    addChild "Line Items Join Table","Link to Task Builder",['Type' : "Service", // Main difference here ;)
                                  'Identifier' : currentValues["Link to Services"]["AppScript Item Number"],
                                  'Description' : currentValues["Link to Services"]["Service"],
                                  'Quantity' : currentValues["Quantity (Service)"],
                                  'Taxable' : "TAX",
                                  'QBO_ID' : currentValues["Link to Services"]["QBO_Ref_ID"],
                                  'Link to Site Visit' : getSiteVisitLink(),
                                  'Link to Quote Builder' : getQuoteLink(),
                                  'Link to Counter Ticket' : getCounterLink(),
                                  'Link to Task List' : currentValues["Link to Task List"],
                                  'Our Cost' : 0,
                                  'Task Name' : currentValues["Link to Task List"]["Link to Site Visit Tasks"]["Task Name"],
                                  'Task Details' : currentValues["Link to Task List"]["Task Details"],
                                  'Tax' : 0,
                                  //'Margin' : ((currentValues["Link to Services"]["AppScript Amount"] as float) * (currentValues["Quantity (Service)"] as float)),
                                  //'Link to Task Builder' : _this,
                                  'Cost' : currentValues["Link to Services"]["AppScript Amount"]];
}


//________________Parts Tracking Code_________________DEPRECATED WILL BE REPLACED BY INVENTORY MANAGMENT / LINE ITEMS..._________/

// Early "Parts Tracking" Attempt - Soon to be trash.
if(currentValues["Link to Product Database"]) {
    save "Products Sold",['Link to Product Database' : currentValues["Link to Product Database"],
                              'Quantity Used' : currentValues["Quantity (Product)"],
                              'Who' : currentValues["Last User"]];
}
}
