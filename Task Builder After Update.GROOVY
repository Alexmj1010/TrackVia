/*__________________________________________________________________________________________________
| This App Script does several things: 1) Detects when parts have been switched out that were       \
| added by the purchasing manager. 2) Records Date of Quote to Site Visit Conversion, 3) Updates     |
| Site Visit when parts are ordered                                                                  |
|__________________________________________________________________________________________________*/

if(currentValues["Testing"] == "Yes") {
    logger.error("i'm here after update");
    logger.error(currentValues["Overide"].toString());
}

// Returns true if Product exists at inventory location
// Accepts String identifier - AppScript Item Number_Master Location Name_-_Sub Location Name
def productExistsAtInventoryLocation(String identifier) {
    //findRecord = loadRecord "Inventory",['UniqID': currentValues["UniqID"]] as Map;
    Map findRecord = loadRecord "Inventory",['Identifier' : identifier] as Map;
    if(findRecord == null) {
        findRecord = loadRecord "Inventory",['UniqID': currentValues["UniqID"]] as Map;
    }
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


//__________Detect When a Part added from a parts request has been switched out___________________________________________________//
// If not a part replacing "No Product" and definitely a switched part then execute.

if((previousValues["Link to Product Database"]["Common Name"] != "NO PRODUCT") && (currentValues["Link to Product Database"] != previousValues["Link to Product Database"])) {
    // Usual Audit Trail Crap
    String productAuditTrail = ""; // Instantiate
    // Store previous product in a String
    String prevProduct = previousValues["Link to Product Database"]["AppScript Item Number"] as String; 
    // Store new product in a String
    String currProduct = currentValues["Link to Product Database"]["AppScript Item Number"] as String;
    // Store Previous quantity in a string (maybe the same, no worries if it is)
    String prevQty = previousValues["Quantity (Product)"] as String;
    // Store current quantity in a string
    String currQty = currentValues["Quantity (Product)"] as String;
    // If audit trail "Detect Part Swap" is null, then initialize to empty string
    if(currentValues["Detect Part Swap"] == null) {
        productAuditTrail = " "; // Empty String place holder
    } else {
        // Grab current audit trail if not first incident...
        productAuditTrail = currentValues["Detect Part Swap"] as String;
    }
    // Append / Concatenate / Add, just store the data and be done with it!
    productAuditTrail += ("\n" + "Previoius Product: (" + prevQty + ") " + prevProduct + 
                          "\n" + "Current Product: (" + currQty + ") " + currProduct);
    // Then put on the task build record.
    currentValues["Detect Part Swap"] = productAuditTrail;
}


//________________ Records Date of Quote to SiteVisit Conversion...__________________________________________________________//
// Grab Todays Date
def date = new Date(); 

// If parent task is linked to a quote and a site visit then capture the date of first occurance.
if(currentValues["Link to Task List"]) {
    if(currentValues["Link to Task List"]["Link to Quote Builder"] && currentValues["Link to Task List"]["Link to Site Visit"]) {
        // This is where the code detects if this is the first time... If task was previously not linked to a site visit,
        // and now it is, then you know it was just created!
        if(previousValues["Link to Task List"]["Link to Site Visit"] == null) {
            currentValues["AppScriptDateQuoteConverted"] = date;
        }
    }
}

// If not just a floater... a.k.a. linked to a task list and site visit
if(currentValues["Link to Task List"] && currentValues["Link to Task List"]["Link to Site Visit"]) {
    // If PO has been changed to ordered.
    if((currentValues["AppScript PO Ordered_T"] == 1) && (previousValues["AppScript PO Ordered_T"] != 1)) {
        // Update number of the parts that have been ordered on the site visit level.
        int x = currentValues["Link to Task List"]["Link to Site Visit"]["AppScript Number PO Ordered"] as int;
        // Increment as the parts are ordered.  This is ran only once per task builder, however multiple 
        // Task builders may exists on a PO and therefore a site visit may be incremented...
        currentValues["Link to Task List"]["Link to Site Visit"]["AppScript Number PO Ordered"] = x + 1;
    }
}

// If purchasing progress has any value selected, run this script
if(currentValues["Purchasing Progress"]) {
    // Load checkbox values into a string (Current values)
    String purchasingCheckbox = currentValues["Purchasing Progress"] as String;
    // Load previous values into a string
    String prevPurchasingCheckbox = previousValues["Purchasing Progress"] as String;
    // If needs return site visit has been selected for the first time
    if(purchasingCheckbox.contains("Needs Return Site Visit") && !(prevPurchasingCheckbox.contains("Needs Return Site Visit"))) {
        currentValues["Link to Task List"]["Link to Site Visit"]["Status"] = "Waiting to Schedule"; // Update status
        currentValues["Link to Task List"]["Link to Site Visit"]["Tag"] = "Needs More Information"; // Update tag!
    }
}



/*_________________________________________________________________________________________________________//
| LINE ITEMS CODE BELOW - CONTAINS INVENTORY OPERATIONS INLINE WITH SCENARIOS FOR LINE ITEMS!               |
| This App Script updates Line Items in several different Scenarios...                                      |
| 1) Product Has been Changed, 2) Service has been Changed, 3) Quantity (Product) has Changed,              |
| 4) Quantity (Service) has Changed, 5) No Product --> Product, 6) No Service --> Service,                  |
| 7) Product --> No Product, 8) Service --> No Service, 9) Special Pricing Added                            |
| 10) Special Pricing Removed, 11) Special Pricing Changed, 12) Markup Changed                              |
| 1: Update, 2: Update, 3: Update, 4: Update, 5: Add, 6: Add, 7: Delete, 8: Delete,                         |
| 9: Update, 10: Update, 11: Update, 12: Update.                                                            |
//_________________________________________________________________________________________________________*/

// Grab them links if they exist!
def getSiteVisitLink() { // If site visit
    // Implicit Return
    if(currentValues["Link to Task List"]) {
        Map link = currentValues["Link to Task List"]["Link to Site Visit"] as Map;
    }
}
def getQuoteLink() { // If Quote
    if(currentValues["Link to Task List"]) {
        Map link = currentValues["Link to Task List"]["Link to Quote Builder"] as Map;
    }
}
def getCounterLink() { // If Counter Ticket
    if(currentValues["Link to Task List"]) {
        Map link = currentValues["Link to Task List"]["Link to Counter Ticket"] as Map;
    }
}

// Pricing Year Getter - Checks all possible links prioritizing Quote
def getPricingYear() {
    // Initialize Place Holder
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

// Use a place holder to determine case
int prodCode; // Initialize
if((previousValues["Link to Product Database"]["Common Name"]) == (currentValues["Link to Product Database"]["Common Name"])) {
    prodCode = 0; // No Change to Product
}
if((previousValues["Link to Product Database"]["Common Name"] == "NO PRODUCT") && 
   (currentValues["Link to Product Database"]["Common Name"] != "NO PRODUCT")) {
    prodCode = 1; // No Product to Product
}
if((previousValues["Link to Product Database"]["Common Name"] != "NO PRODUCT") && 
   (currentValues["Link to Product Database"]["Common Name"] == "NO PRODUCT")) {
    prodCode = 2; // Product to No Product
}
if((previousValues["Link to Product Database"] != currentValues["Link to Product Database"]) &&
   (previousValues["Link to Product Database"]["Common Name"] != "NO PRODUCT") &&
   (currentValues["Link to Product Database"]["Common Name"] != "NO PRODUCT")) {
    prodCode = 3; // Product to Product
}

// Same as above but for services
int servCode; // Initialize
if((previousValues["Link to Services"]["Service"]) == (currentValues["Link to Services"]["Service"])) {
    servCode = 0; // No Change to Service
}
if((previousValues["Link to Services"]["Service"] == "NO SERVICE") &&
   (currentValues["Link to Services"]["Service"] != "NO SERVICE")) {
    servCode = 1; // No Service to Service
}
if((previousValues["Link to Services"]["Service"] != "NO SERVICE") &&
   (currentValues["Link to Services"]["Service"] == "NO SERVICE")) {
    servCode = 2; // Service to No Service
}
if((previousValues["Link to Services"] != currentValues["Link to Services"]) &&
   (previousValues["Link to Services"]["Service"] != "NO SERVICE") &&
   (currentValues["Link to Services"]["Service"] != "NO SERVICE")) {
    servCode = 3; // Service to Service
}

// Grab Loc-Doc's Cost
def getOurCost() {
    // Start with zero just in case to avoid null pointer reference if cost not found
    double tempOurCost = 0.0;
    // If the value exists, grab it!
    if(currentValues["Link to Product Database"]["Price (Cost)"] != null) {
        tempOurCost = currentValues["Link to Product Database"]["Price (Cost)"] as double;
    }
    if(currentValues["Adjusted Cost"] != null) { // Special Pricing exists!
        //tempcost = (((currentValues["Adjusted Cost"] as double) * (1.0 + (currentValues["Adjust Markup"] as double))) as double).round(2);
        tempOurCost = currentValues["Adjusted Cost"] as double;
    }
    return tempOurCost; 
}

//_________"CASES" BELOW!______________//
//___WENT WITH SERIAL IF STATEMENTS___//
//___SEE GROOVY API FOR MORE INFO____// do your research before you change my code punk!
//___ON THIS DESIGN OVER CASE_______//
//___STATEMENTS!!!!!!!!____________//

//1) If Current Product changes to a different product, update child Line Item - Product
if(prodCode == 3) {
    // Initialized Yada yada...
    double tempcost = 0.0;
    double tempOurCost = 0.0;
    String tempQBO = 99999; // Avoid null pointer reference
    if(currentValues["Link to Product Database"]["QBO_Ref_ID"] != null) { // Grab QBO_Ref_ID if it exists
        tempQBO = currentValues["Link to Product Database"]["QBO_Ref_ID"]; // Else it will = 99999
    }
    if(currentValues["Link to Product Database"]["Price (Cost)"] != null) { // if product added via msrp...
        tempOurCost = currentValues["Link to Product Database"]["Price (Cost)"] as double;
    }
    if(currentValues["Link to Product Database"]["Price (Cost)"] == null) { // if product added by cost...
        tempOurCost = ((currentValues["Link to Product Database"]["MSRP (Input)"] as double) * (currentValues["Link to Product Database"]["Discount"] as double));
    }
    if(currentValues["Adjusted Cost"] != null) { // Special Pricing exists!
        tempcost = (((currentValues["Adjusted Cost"] as double) * (1.0 + (currentValues["Adjust Markup"] as double))) as double).round(2);
        tempOurCost = currentValues["Adjusted Cost"] as double;
    }
    if(currentValues["Adjusted Cost"] == null) { // No Special Pricing!
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
    
    // Apply updates to Line Items
    List _lineItems = getChildren("Line Items Join Table", "Link to Task Builder") as List;
    _lineItems.each { // at most two line items... one product, one service
        if(it["Type"] == "Product") {
            it["Identifier"] = currentValues["Link to Product Database"]["AppScript Item Number"];
            it["Description"] = currentValues["Link to Product Database"]["Common Name"];
            it["QBO_ID"] = tempQBO;
            it["Cost"] = tempcost;
            it["Our Cost"] = tempOurCost;
        }
    }
    
    /*___________________________/
    / INSERT INVENTORY CODE HERE /
    / CANCEL - Previous
    / RESERVE - Current
    /___________________________*/
    // If using new Inventory Form (Test) and also (Script has not already been ran)
    // "Already Ran" prevents the scripts from being run twice. - reason for running twice is record update from Inventory Manager table
    if((currentValues["Testing"] == "Yes") && (currentValues["Already Ran"] != 1) && (currentValues["Link to Task List"]["Parent Type"] != 2)) {
        // If Quantity is the same but product has changed
        if(previousValues["Quantity (Product)"] == currentValues["Quantity (Product)"]) {
            // First Cancel previous Product Reservation
            addChild "Inventory Manager","Link to Task Builder",['Link to Inventory - Sub Location' : previousValues["Link to Inventory - Sub Location"],
                                     'Link to Product Database' : previousValues["Link to Product Database"],
                                     'Quantity' : currentValues["Quantity (Product)"],
                                     'Arguments' : "Cancel"];
            // Then Reserve new product choice
            addChild "Inventory Manager","Link to Task Builder",['Link to Inventory - Sub Location' : currentValues["Link to Inventory - Sub Location"],
                                     'Link to Product Database' : currentValues["Link to Product Database"],
                                     'Quantity' : currentValues["Quantity (Product)"],
                                     'Arguments' : "Reserve"];
        }
        // If the quantity changes (and the product)
        if(previousValues["Quantity (Product)"] != currentValues["Quantity (Product)"]) {
            // Cancel previous reserve
            addChild "Inventory Manager","Link to Task Builder",['Link to Inventory - Sub Location' : previousValues["Link to Inventory - Sub Location"],
                                     'Link to Product Database' : previousValues["Link to Product Database"],
                                     'Quantity' : previousValues["Quantity (Product)"],
                                     'Arguments' : "Cancel"];
            // Reserve new product with updated quantity
            addChild "Inventory Manager","Link to Task Builder",['Link to Inventory - Sub Location' : currentValues["Link to Inventory - Sub Location"],
                                     'Link to Product Database' : currentValues["Link to Product Database"],
                                     'Quantity' : currentValues["Quantity (Product)"],
                                     'Arguments' : "Reserve"];
        }
        // If this is a Non Stock Item, Automate an RGA! (But only if the PO is Ordered or Closed!)
        if(currentValues["Link to Purchase Order"]) {
            if((previousValues["Link to Inventory"]["Minimum Buffer"] == 0) && (currentValues["Link to Purchase Order"]["Status"] != "Open")) {
                previousValues["Link to Inventory"]["RGA"] = "Yes";
            }
        }
        
    }
    // If this is the second time the script is running
    if((currentValues["Already Ran"] == 1)  && (currentValues["Link to Task List"]["Parent Type"] != 2)) {
        // Then Map the new Inventory record that was created during Inventory Manager After Insert after first iteration of DNE script error from this script
        currentValues["Link to Inventory"] = productExistsAtInventoryLocation(getInventoryLocationIdentifier(currentValues["Link to Product Database"] as Map, currentValues["Link to Inventory - Sub Location"] as Map) as String);
        currentValues["Already Ran"] = 0; // Reset already ran to 0
    }
}

//2) If Current Service changes to a different service, update child Line Item - Service
if(servCode == 3) {
    List _lineItems = getChildren("Line Items Join Table", "Link to Task Builder") as List;
    _lineItems.each {
        if(it["Type"] == "Service") {
            it["Identifier"] = currentValues["Link to Services"]["AppScript Item Number"];
            it["Description"] = currentValues["Link to Services"]["Service"];
            it["QBO_ID"] = currentValues["Link to Services"]["QBO_Ref_ID"];
            it["Cost"] = currentValues["Link to Services"]["AppScript Amount"];
            it["Our Cost"] = 0;
        }
    }
}

//3) If Quantity (Product) changes, update child Line Item - Product
    //Product Code is either 0 or 3 (No change to Product, or Product to Product)
if((previousValues["Quantity (Product)"] != currentValues["Quantity (Product)"]) && ((prodCode == 0) || (prodCode == 3))) {
    List _lineItems = getChildren("Line Items Join Table", "Link to Task Builder") as List;
    _lineItems.each {
        if(it["Type"] == "Product") {
            it["Quantity"] = currentValues["Quantity (Product)"];
        }
    }
    /*___________________________/
    / INSERT INVENTORY CODE HERE /
    / CANCEL - PREVIOUS QTY
    / RESERVE - CURRENT QTY
    /___________________________*/
    // ProdCode 0 is no change to product.  This handles to case of Quantity only changing
    if((prodCode == 0) && (currentValues["Testing"] == "Yes") && (currentValues["Link to Task List"]["Parent Type"] != 2)) {
        // Cancel previous reserve request
        addChild "Inventory Manager","Link to Task Builder",['Link to Inventory - Sub Location' : currentValues["Link to Inventory - Sub Location"],
                                     'Link to Product Database' : currentValues["Link to Product Database"],
                                     'Quantity' : previousValues["Quantity (Product)"],
                                     'Arguments' : "Cancel"];
        // Reserve new quantity instead
        addChild "Inventory Manager","Link to Task Builder",['Link to Inventory - Sub Location' : currentValues["Link to Inventory - Sub Location"],
                                     'Link to Product Database' : currentValues["Link to Product Database"],
                                     'Quantity' : currentValues["Quantity (Product)"],
                                     'Arguments' : "Reserve"];
    }
}

//4) If Quantity (Service) changes, update child Line Item - Service
    //Service Code is either 0 or 3 (No change to Service, or Service to Service)
if((previousValues["Quantity (Service)"] != currentValues["Quantity (Service)"]) && ((servCode == 0) || (servCode == 3))) {
    List _lineItems = getChildren("Line Items Join Table", "Link to Task Builder") as List;
    _lineItems.each {
        if(it["Type"] == "Service") {
            it["Quantity"] = currentValues["Quantity (Service)"];
        }
    }
}

//5) If "No Product" changes to a Product, add child Line Item - Product
if(prodCode == 1) {
    double tempcost = 0.0;
    String tempQBO = 99999;
    if(currentValues["Adjusted Cost"] != null) {
        tempcost = (((currentValues["Adjusted Cost"] as double) * (1.0 + (currentValues["Adjust Markup"] as double))) as double).round(2);
    }
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
    if(currentValues["Link to Product Database"]["QBO_Ref_ID"] != null) {
        tempQBO = currentValues["Link to Product Database"]["QBO_Ref_ID"];
    }
    
    addChild "Line Items Join Table","Link to Task Builder",['Type' : "Product",
                                  'Identifier' : currentValues["Link to Product Database"]["AppScript Item Number"],
                                  'Description' : currentValues["Link to Product Database"]["Common Name"],
                                  'Quantity' : currentValues["Quantity (Product)"],
                                  'Taxable' : "TAX",
                                  'QBO_ID' : tempQBO,
                                  'Link to Site Visit' : getSiteVisitLink(),
                                  'Link to Quote Builder' : getQuoteLink(),
                                  'Link to Counter Ticket' : getCounterLink(),
                                  'Link to Task List' : currentValues["Link to Task List"],
                                  'Our Cost' : getOurCost(),
                                  //'Link to Task Builder' : _this,
                                  'Cost' : tempcost];
                                  
    /*___________________________/
    / INVENTORY CODE HERE /
    / RESERVE - CURRENT
    /___________________________*/
    // This handles reserving the selected product.
    if((currentValues["Testing"] == "Yes") && (currentValues["Already Ran"] != 1) && (currentValues["Link to Task List"]["Parent Type"] != 2)) {
        // Then Reserve new product choice
        addChild "Inventory Manager","Link to Task Builder",['Link to Inventory - Sub Location' : currentValues["Link to Inventory - Sub Location"],
                                     'Link to Product Database' : currentValues["Link to Product Database"],
                                     'Quantity' : currentValues["Quantity (Product)"],
                                     'Arguments' : "Reserve"];
    }
    // If this is the second time the script is running
    //logger.error("I'm here outside the already ran block");
    //logger.error(currentValues["Already Ran"] as String);
    if((currentValues["Already Ran"] == 1)  && (currentValues["Link to Task List"]["Parent Type"] != 2)) {
        //logger.error("I'm here inside the already ran block");
        // Then Map the new Inventory record that was created during Inventory Manager After Insert after first iteration of DNE script error from this script
        currentValues["Link to Inventory"] = productExistsAtInventoryLocation(getInventoryLocationIdentifier(currentValues["Link to Product Database"] as Map, currentValues["Link to Inventory - Sub Location"] as Map) as String);
        currentValues["Already Ran"] = 0; // Reset already ran to 0
    }
}

//6) If "No Service" changes to a Service, add child Line Item - Service
if(servCode == 1) {
    // Build out line item "Service"
    addChild "Line Items Join Table","Link to Task Builder",['Type' : "Service",
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
                                  'Cost' : currentValues["Link to Services"]["AppScript Amount"]];
}

//7) If a Product is changed to "No Product", delete child Line Item - Product
if(prodCode == 2) {
    List _lineItems = getChildren("Line Items Join Table", "Link to Task Builder") as List;
    _lineItems.each {
        if(it["Type"] == "Product") {
            Map record = it as Map;
            delete record; // TODO - how to actually delete this!
        }
    }
    
    /*___________________________/
    / INVENTORY CODE HERE        /
    / CANCEL - PREVIOUS          /
    /___________________________*/
    if((currentValues["Testing"] == "Yes") && (currentValues["Already Ran"] != 1) && (currentValues["Link to Task List"]["Parent Type"] != 2)) {
        // Then Reserve new product choice
        addChild "Inventory Manager","Link to Task Builder",['Link to Inventory - Sub Location' : previousValues["Link to Inventory - Sub Location"],
                                     'Link to Product Database' : previousValues["Link to Product Database"],
                                     'Quantity' : previousValues["Quantity (Product)"],
                                     'Arguments' : "Cancel"];
        
        // If this is a Non Stock Item, Automate an RGA! (But only if the PO is Ordered or Closed!)
        if(currentValues["Link to Purchase Order"]) {
            // Check Inventory Type
            if((previousValues["Link to Inventory"]["Type"] == "Non-Stock Item") && (currentValues["Link to Purchase Order"]["Status"] != "Open")) {
                previousValues["Link to Inventory"]["RGA"] = "Yes";
            }
        }
    }
}

//8) If a Service is changed to "No Service", delete child Line Item - Service
if(servCode == 2) {
    List _lineItems = getChildren("Line Items Join Table", "Link to Task Builder") as List;
    _lineItems.each {
        if(it["Type"] == "Service") {
            Map record = it as Map;
            delete record; // TODO - how to delete this?
        }
    }
}

//9) If Special Pricing goes from blank to holding a value - Update child Line Item - Product
if((previousValues["Adjusted Cost"] == null) && (currentValues["Adjusted Cost"] != null)) {
    double tempcost = 0.0;
    // Calculate the Special Cost
    tempcost = (((currentValues["Adjusted Cost"] as double) * (1.0 + (currentValues["Adjust Markup"] as double))) as double).round(2);
    List _lineItems = getChildren("Line Items Join Table", "Link to Task Builder") as List;
    _lineItems.each { // Update each line item
        if(it["Type"] == "Product") {
            it["Cost"] = tempcost;
        }
    }
}

//10) If Special Pricing goes from holding a value to being blank - Update child Line Item - Product
if((previousValues["Adjusted Cost"] != null) && (currentValues["Adjusted Cost"] == null)) {
    List _lineItems = getChildren("Line Items Join Table", "Link to Task Builder") as List;
    _lineItems.each { // Remove Special Pricing - Grab pricing year etc...
        // Only on products not line items!
        if(it["Type"] == "Product") {
            double tempcost = 0.0;
            if(getPricingYear() == "2017") {
                tempcost = (currentValues["Link to Product Database"]["2017 MSRP"]) as double;
            }   
            if(getPricingYear() == "2018") {
                tempcost = (currentValues["Link to Product Database"]["2018 MSRP"]) as double;
            }
            if(getPricingYear() == "Use Latest") {
                tempcost = (currentValues["Link to Product Database"]["AppScript MSRP"]) as double;
            }
            // Set the cost
            it["Cost"] = tempcost;
            // Grab our cost
            it["Our Cost"] = getOurCost(); 
        }
    }
}

//11) If Special Pricing value changes, update child Line Item - Product
if((previousValues["Adjusted Cost"] != currentValues["Adjusted Cost"]) && (currentValues["Adjusted Cost"] != null) && (previousValues["Adjusted Cost"] != null)) {
    double tempcost = 0.0;
    // Grab the updated cost
    tempcost = (((currentValues["Adjusted Cost"] as double) * (1.0 + (currentValues["Adjust Markup"] as double))) as double).round(2);
    List _lineItems = getChildren("Line Items Join Table", "Link to Task Builder") as List;
    _lineItems.each { // Update it on the product line items for this task builder
        if(it["Type"] == "Product") {
            it["Cost"] = tempcost;
            it["Our Cost"] = currentValues["Adjusted Cost"] as double;
        }
    }
}

//12) If Markup Changes, Update Line Item - Product
if((currentValues["Adjusted Cost"] != null) && (previousValues["Adjust Markup"] != currentValues["Adjust Markup"])) {
    double tempcost = 0.0; // Same as above!
    tempcost = (((currentValues["Adjusted Cost"] as double) * (1.0 + (currentValues["Adjust Markup"] as double))) as double).round(2);
    List _lineItems = getChildren("Line Items Join Table", "Link to Task Builder") as List;
    _lineItems.each {
        if(it["Type"] == "Product") {
            it["Cost"] = tempcost;
        }
    }
}

//13) If Parent Task is Cancelled, Discard all line items
if((previousValues["Parent Task Cancelled"] != 1) && (currentValues["Parent Task Cancelled"] == 1)) {
    List _lineItems = getChildren("Line Items Join Table", "Link to Task Builder") as List;
    _lineItems.each { // Update "Both" line items on this task builder
        it["_Type"] = it["Type"];
        it["Type"] = "Discard";
    }
    
    /*___________________________/
    / INVENTORY CODE HERE        /
    / CANCEL - PREVIOUS          /
    /___________________________*/
    // Ensure this is not a quote and also that this is the first time the script has run.
    if((currentValues["Testing"] == "Yes") && (currentValues["Already Ran"] != 1) && (currentValues["Link to Task List"]["Parent Type"] != 2)) {
        // Then Reserve new product choice
        addChild "Inventory Manager","Link to Task Builder",['Link to Inventory - Sub Location' : currentValues["Link to Inventory - Sub Location"],
                                     'Link to Product Database' : currentValues["Link to Product Database"],
                                     'Quantity' : currentValues["Quantity (Product)"],
                                     'Arguments' : "Cancel"];
    }
}

//14) If Parent Task goes from Cancelled to not Cancelled, "UnDiscard"
if((previousValues["Parent Task Cancelled"] == 1) && (currentValues["Parent Task Cancelled"] != 1)) {
    List _lineItems = getChildren("Line Items Join Table", "Link to Task Builder") as List;
    _lineItems.each {
        // Grab the previous type!
        it["Type"] = it["_Type"];
    }
    
    /*___________________________/
    / INVENTORY CODE HERE        /
    / CANCEL - PREVIOUS          /
    /___________________________*/
    if((currentValues["Testing"] == "Yes") && (currentValues["Already Ran"] != 1) && (currentValues["Link to Task List"]["Parent Type"] != 2)) {
        // Then Reserve new product choice
        addChild "Inventory Manager","Link to Task Builder",['Link to Inventory - Sub Location' : currentValues["Link to Inventory - Sub Location"],
                                     'Link to Product Database' : currentValues["Link to Product Database"],
                                     'Quantity' : currentValues["Quantity (Product)"],
                                     'Arguments' : "Reserve"];
    }
}



//_______________________________________SPECIAL USE BY ADMIN ONLY_________________________//
// This App Script runs when the parent Site visit and Task have                            |
// Process Admin Checked.  It essentially runs the code that exists in after insert!        | 
// Used for adding line items that were deleted, or adding line items to legacy data...     |
//_________________________________________________________________________________________//


if((currentValues["Process ADMIN"] == "YES") && (previousValues["Process ADMIN"] != "YES")) {

// Check for one of Four scenario's - Link to Quote, Link to SV, Link to CT, or Mixed SV and Quote



if((currentValues["Link to Product Database"]["Common Name"] != "NO PRODUCT") && (currentValues["Link to Task List"]["Progress"] != "Cancelled")) {
    String specialPricing = "No";
    double tempcost = 0.0;
    double tempOurCost = 0.0;
    String tempQBO = 99999;
    if(currentValues["Link to Product Database"]["QBO_Ref_ID"] != null) {
        tempQBO = currentValues["Link to Product Database"]["QBO_Ref_ID"];
    }
    if(currentValues["Link to Product Database"]["Price (Cost)"] != null) {
        tempOurCost = currentValues["Link to Product Database"]["Price (Cost)"] as double;
    }
    if(currentValues["Link to Product Database"]["Price (Cost)"] == null) {
        tempOurCost = ((currentValues["Link to Product Database"]["MSRP (Input)"] as double) * (currentValues["Link to Product Database"]["Discount"] as double));
    }
    if(currentValues["Adjusted Cost"] != null) {
        tempcost = (((currentValues["Adjusted Cost"] as double) * (1.0 + (currentValues["Adjust Markup"] as double))) as double).round(2);
        tempOurCost = currentValues["Adjusted Cost"] as double;
        specialPricing = "Yes";
    }
    if(currentValues["Adjusted Cost"] == null) {
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
    Map lineItem = null;
    addChild "Line Items Join Table","Link to Task Builder",['Type' : "Product",
                                  'Identifier' : currentValues["Link to Product Database"]["AppScript Item Number"],
                                  'Description' : currentValues["Link to Product Database"]["Common Name"],
                                  'Quantity' : currentValues["Quantity (Product)"],
                                  'Taxable' : "TAX",
                                  'QBO_ID' : tempQBO,
                                  'Link to Site Visit' : getSiteVisitLink(),
                                  'Link to Quote Builder' : getQuoteLink(),
                                  'Link to Counter Ticket' : getCounterLink(),
                                  'Link to Task List' : currentValues["Link to Task List"],
                                  'Our Cost' : tempOurCost,
                                  'Task Name' : currentValues["Link to Task List"]["Link to Site Visit Tasks"]["Task Name"],
                                  'Task Details' : currentValues["Link to Task List"]["Task Details"],
                                  'Tax' : 0,
                                  'Cost' : tempcost,
                                  'Special Pricing' : specialPricing];
                                  
    /*___________________________/
    / INSERT INVENTORY CODE HERE /
    / RESERVE - ????? ONLY IF PREVIOUS VALUES CAN BE DELETED... (A.K.A. CANCELLED)
    /___________________________*/
}


if((currentValues["Link to Services"]["Service"] != "NO SERVICE") && (currentValues["Link to Task List"]["Progress"] != "Cancelled")) {
    Map lineItem = null;
    addChild "Line Items Join Table","Link to Task Builder",['Type' : "Service",
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
                                  'Cost' : currentValues["Link to Services"]["AppScript Amount"],
                                  'Special Pricing' : "No"];
}
/*if(currentValues["Link to Task List"]["Progress"] == "Cancelled") {
    List _lineItems = getChildren("Line Items Join Table", "Link to Task Builder") as List;
    _lineItems.each {
        it["Type"] = "Discard";
    }
}*/

}


/*________________________________________________________/
/ Handle inventory case of 2nd time script is running     /
/ If 2nd time, Update link to inventory                   /
/________________________________________________________*/
// If this is the second time the script is running
if((currentValues["Already Ran"] == 1)  && (currentValues["Link to Task List"]["Parent Type"] != 2) && (currentValues["From Quote"] == 2) && (currentValues["Testing"] == "Yes")) {
    // Then Map the new Inventory record that was created during Inventory Manager After Insert after first iteration of DNE script error from this script
    logger.error("I'm here at the bottom of this crappy code block!");
    //currentValues["Link to Inventory"] = productExistsAtInventoryLocation(getInventoryLocationIdentifier(currentValues["Link to Product Database"] as Map, currentValues["Link to Inventory - Sub Location"] as Map) as String);
    currentValues["Already Ran"] = 0; // Reset already ran to 0
    currentValues["From Quote"] = 1;
}

// If this is the second time the script is running
if((currentValues["Already Ran"] == 1)  && (currentValues["Link to Task List"]["Parent Type"] != 2) && (currentValues["Testing"] == "Yes") && (currentValues["Order NIS"] == "Yes")) {
    // Then Map the new Inventory record that was created during Inventory Manager After Insert after first iteration of DNE script error from this script
    logger.error("I'm here at the bottom of this crappy code block!");
    //currentValues["Link to Inventory"] = productExistsAtInventoryLocation(getInventoryLocationIdentifier(currentValues["Link to Product Database"] as Map, currentValues["Link to Inventory - Sub Location"] as Map) as String);
    currentValues["Already Ran"] = 0; // Reset already ran to 0
    currentValues["From Quote"] = 1;
}

/*________________________________________________________________________________________________________|
/ Bulk inventory operations below.  These are not to be used by the general public, and only for admin    |
/ Processes.  These are behind the scenes operations purely used for "Legacy" Site Visits                 |
/_______________________________________________________________________________________________________*/

// Cancel all inventory on this task builder
if((currentValues["Inventory Command"] == "Cancel") && (previousValues["Inventory Command"] != "Cancel")) {
    addChild "Inventory Manager","Link to Task Builder",['Link to Inventory - Sub Location' : currentValues["Link to Inventory - Sub Location"],
                                                         'Link to Product Database' : currentValues["Link to Product Database"],
                                                         'Quantity' : previousValues["Quantity (Product)"],
                                                         'Arguments' : "Cancel"];
}

// Sell all inventory on this task builer
if((currentValues["Inventory Command"] == "Sell") && (previousValues["Inventory Command"] != "Sell")) {
    addChild "Inventory Manager","Link to Task Builder",['Link to Inventory - Sub Location' : currentValues["Link to Inventory - Sub Location"],
                                                         'Link to Product Database' : currentValues["Link to Product Database"],
                                                         'Quantity' : previousValues["Quantity (Product)"],
                                                         'Arguments' : "Sell"];
}

// Reserve all inventory on this task builder
if((currentValues["Inventory Command"] == "Reserve") && (previousValues["Inventory Command"] != "Reserve")) {
    addChild "Inventory Manager","Link to Task Builder",['Link to Inventory - Sub Location' : currentValues["Link to Inventory - Sub Location"],
                                                         'Link to Product Database' : currentValues["Link to Product Database"],
                                                         'Quantity' : previousValues["Quantity (Product)"],
                                                         'Arguments' : "Reserve"];
}

// Unsell all inventory on this task builder
if((currentValues["Inventory Command"] == "Unsell") && (previousValues["Inventory Command"] != "Unsell")) {
    addChild "Inventory Manager","Link to Task Builder",['Link to Inventory - Sub Location' : currentValues["Link to Inventory - Sub Location"],
                                                         'Link to Product Database' : currentValues["Link to Product Database"],
                                                         'Quantity' : previousValues["Quantity (Product)"],
                                                         'Arguments' : "Add"];
}

/*________________________________________________________________________________________________________|
/ QR Code Generator below - Ran from table "Purchase Order" - change Generate QR Code from null to "Yes"  |
/ Pass link to PO as part of the record save.                                                             |
/_______________________________________________________________________________________________________*/

// Used to populate a table with QR Code data
if((previousValues["Generate QR Code"] == null) && (currentValues["Generate QR Code"] == 1)) {
    // Get QTY
    int qtyProd = currentValues["Quantity (Product)"] as int;
    // Iterate by QTY to generate 1 label per 1 product
    for(int i = 0; i < qtyProd; i++) {
        // Build label, add record to QR Code Table
        String s = currentValues["Link to Product Database"]["Inventory Label"] as String;
        save "QR Codes",['Item Number' : currentValues["Link to Product Database"]["AppScript Item Number"],
                         'Link to Purchase Order' : currentValues["Link to Purchase Order"],
                         'Label Date' : date,
                         'Label Name' : s];
    }
}



// If a PO status changes to Ordered, run Inventory scripts to update inventory items
if((previousValues["PO Status"] != "Ordered") && (currentValues["PO Status"] == "Ordered") && (currentValues["Testing"] == "Yes")) {
    currentValues["Link to Inventory - Sub Location"] = currentValues["Link to Inventory"]["Link to Inventory - Sub Location"];
}

// If a PO is closed (a.k.a.) arrived, update inventory records.  This setup also supports partial arrival by triggering on the TB
if((previousValues["PO Status"] != "Closed") && (currentValues["PO Status"] == "Closed") && (currentValues["Testing"] == "Yes")) {
    currentValues["Link to Inventory - Sub Location"] = currentValues["Link to Inventory"]["Link to Inventory - Sub Location"];

}







