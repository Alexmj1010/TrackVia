/*______________________________________________________________________________________
| This App Script does three things: 1) Captures date completed, 2) When Invoice is     \
| is created, changed "Invoiced?" to "Yes", 3) When Sales Receipt is Created, change     |
| "Invoice?" to "Yes".  - Assists accounting when automated to QBO.                      |
|______________________________________________________________________________________*/

// Grab todays date
def date = new Date();

// Check if status changed from anything other than complete to complete / closed / finished
if((previousValues["Status"] != "Complete / Closed / Finished" ) && (currentValues["Status"] == "Complete / Closed / Finished")) {
    // Set completed date to todays date
    currentValues["Completed Date"] = date; // Capture date finished.
    /*______________________________________________/
    / INVENTORY CODE GOES HERE!!! UPON LAUNCH       |
    / Insert SELL Command Here! - once site visit   |
    / is completed, sell everything!                |
    /______________________________________________*/
    
    
    
    
}

// If Automate Checkbox is checked, change Invoice? to Yes
String invoiceString = currentValues["Automate"].toString();
if(invoiceString.contains("Yes")) {
    currentValues["Invoiced?"] = "Yes";
}

// If Automate Sales Receipt Checkbox is Checked, Change Invoiced? to Yes
String salesReceiptString = currentValues["Automate Sales Receipt"].toString();
if(salesReceiptString.contains("yes")) {
    currentValues["Invoiced?"] = "Yes";
}

/*______________________________________________________________________________________
| This App Script is part of the Line Items Update. Upon completion of a Counter Ticket \
| it will delete all child line items not attached to a task builder!                    |
|______________________________________________________________________________________*/

// Check if counter ticket is complete
if(((currentValues["Status"] == "Complete / Closed / Finished") && (previousValues["Status"] != "Complete / Closed / Finished")) || ((currentValues["Status"] == "On Shelf") && (previousValues["Status"] != "On Shelf"))) {
    // Add Counter Ticket Children - Line Items to a List
    List lineItems = getChildren("Line Items Join Table","Link to Counter Ticket") as List;
    lineItems.each { // Iterate
        //if Type is already Discarded from previous operation - task being cancelled!
        if(it["Type"] == "Discard") {
            it["Total Cost"] = 0;
            it["Our Total Cost"] = 0;
            it["Profit Margin"] = 0;
            it["Tax"] = 0;
            it["Total With Tax"] = 0;
        }
        //if either is blank change type to discard!
        if(it["Link to Task List"] == null) {
            it["Type"] = "Discard";
            it["Total Cost"] = 0;
            it["Our Total Cost"] = 0;
            it["Profit Margin"] = 0;
            it["Tax"] = 0;
            it["Total With Tax"] = 0;
        } // Possibly Combine these??
        if(it["Link to Task Builder"] == null) { 
            it["Type"] = "Discard";
            it["Total Cost"] = 0;
            it["Our Total Cost"] = 0;
            it["Profit Margin"] = 0;
            it["Tax"] = 0;
            it["Total With Tax"] = 0;
        }
        
        // If both links exist, then line item is legit and values need to be updated. 
        // this is similar to reconcile!
        if((it["Link to Task Builder"] != null) && (it["Link to Task List"] != null) && (it["Type"] != "Discard")) {
            // If QBO_ID is 99999, check for update.
            if(it["QBO_ID"] == "99999") { 
                it["QBO_ID"] = it["Link to Task Builder"]["Link to Product Database"]["QBO_Ref_ID"];
            }
            // Check for those special products that are not taxed, namely, software
            if((it["Link to Task Builder"]["Link to Product Database"]["Exclude From Tax Calculation"] == 1) && (it["Type"] == "Product")) {
                it["Taxable"] = "NON"; // BUG If service is attached to same task builder as the product, then issue!
            }
            // If line item is taxable, calculate cost of this tax
            if(it["Taxable"] == "TAX") {
                double taxRate = 0.0725;
                it["Tax"] = ((((it["Cost"] as double) * (it["Quantity"] as double)).round(2)) * taxRate).round(7);
            }
            //Calculate remaining values
            it["Total Cost"] = ((it["Quantity"] as double) * (it["Cost"] as double)).round(2);
            it["Our Total Cost"] = (it["Quantity"] as double) * (it["Our Cost"] as double);
            it["Profit Margin"] = (it["Total Cost"] as double) - (it["Our Total Cost"] as double);
            it["Total With Tax"] = ((it["Total Cost"] as double) + (it["Tax"] as double)).round(2);
        }
    }
}

//______________________________MORE LINE ITEMS CODE____________________________________________//
//             ___ WILL BE DEPRECATED UPON LINE ITEMS LAUNCH ____                              //
//           _____ CAN BE USED TO "RE DO" LINE ITEMS CALCULATION _                            //
//___________________________________________________________________________________________//

// When process admin is changed to yes, grab all the tasks, and change their process admin to yes
if((currentValues["Process ADMIN"] == "YES") && (previousValues["Process ADMIN"] != "YES")) {
    // Grab child task list records and put them in list
    List childRecords = getChildren("Task List","Link to Counter Ticket") as List;
    childRecords.each { // Iterate that list!
        it["Process ADMIN"] = "YES"; // Update Process admin to yes on all child records
    }
}
// Same as above, but undo...
if((currentValues["Process ADMIN"] == null) && (previousValues["Process ADMIN"] != null)) {
    List childRecords = getChildren("Task List","Link to Counter Ticket") as List;
    childRecords.each {
        it["Process ADMIN"] = null;
    }
}
// After line items have been "Processed" or "Gathered", use below to reconcile.
// ____ SEE code lines 35 - 113 ____ // // Duplicate Code Alert!!!!! Comments in code above, does same thing!
if((previousValues["Reconcile ADMIN"] != "YES" ) && (currentValues["Reconcile ADMIN"] == "YES")) {

    List lineItems = getChildren("Line Items Join Table","Link to Counter Ticket") as List;
    lineItems.each {
        if(it["Type"] == "Discard") {
            it["Total Cost"] = 0;
            it["Our Total Cost"] = 0;
            it["Profit Margin"] = 0;
            it["Tax"] = 0;
            it["Total With Tax"] = 0;
        }
        //logger.error(it["Link to Task List"].toString());
        if(it["Link to Task List"] == null) {
            it["Type"] = "Discard";
            it["Total Cost"] = 0;
            it["Our Total Cost"] = 0;
            it["Profit Margin"] = 0;
            it["Tax"] = 0;
            it["Total With Tax"] = 0;
        } // Possibly Combine these??
        if(it["Link to Task Builder"] == null) { 
            it["Type"] = "Discard";
            it["Total Cost"] = 0;
            it["Our Total Cost"] = 0;
            it["Profit Margin"] = 0;
            it["Tax"] = 0;
            it["Total With Tax"] = 0;
            //Map toDelete = it as Map;
            //delete toDelete;
        }
        if((it["Link to Task Builder"] != null) && (it["Link to Task List"] != null) && (it["Type"] != "Discard")) {
            if(it["QBO_ID"] == "99999") {
                it["QBO_ID"] = it["Link to Task Builder"]["Link to Product Database"]["QBO_Ref_ID"];
            }

            if((currentValues["Pricing Year"] != "Use Latest") && (it["Type"] == "Product") && (it["Special Pricing"] != "Yes")) {
                //logger.error("I'm here!");
                if(currentValues["Pricing Year"] == "2018") {
                    it["Cost"] = it["Link to Task Builder"]["Link to Product Database"]["2018 MSRP"];
                }
                if(currentValues["Pricing Year"] == "2017") {
                    it["Cost"] = it["Link to Task Builder"]["Link to Product Database"]["2017 MSRP"];
                    //logger.error("I'm here!");
                }
            }
            if((it["Link to Task Builder"]["Link to Product Database"]["Exclude From Tax Calculation"] == 1) && (it["Type"] == "Product")) {
                it["Taxable"] = "NON"; // BUG If service is attached to same task builder as the product, then issue!
            }
            if(it["Taxable"] == "TAX") {
                double taxRate = 0.0725;
                it["Tax"] = ((((it["Cost"] as double) * (it["Quantity"] as double)).round(2)) * taxRate).round(7);
            }
            it["Total Cost"] = ((it["Quantity"] as double) * (it["Cost"] as double)).round(2);
            it["Our Total Cost"] = (it["Quantity"] as double) * (it["Our Cost"] as double);
            it["Profit Margin"] = (it["Total Cost"] as double) - (it["Our Total Cost"] as double);
            it["Total With Tax"] = ((it["Total Cost"] as double) + (it["Tax"] as double)).round(2);
        }
    }

    
}

/*________________________________________________________________________________________________________|
/ Bulk inventory operations below.  These are not to be used by the general public, and only for admin    |
/ Processes.  These are behind the scenes operations purely used for "Legacy" Counter Tickets                 |
/_______________________________________________________________________________________________________*/

if((currentValues["Inventory Command"] == null) && (previousValues["Inventory Command"] != null)) {
    List childRecords = getChildren("Task List","Link to Counter Ticket") as List;
    childRecords.each {
        it["Inventory Command"] = null;
    }
}

if((previousValues["Inventory Command"] != "Cancel") && (currentValues["Inventory Command"] == "Cancel")) {
    cancelInventoryReserves();
}

if((previousValues["Inventory Command"] != "Sell") && (currentValues["Inventory Command"] == "Sell")) {
    sellAllInventory();
}

if((previousValues["Inventory Command"] != "Reserve") && (currentValues["Inventory Command"] == "Reserve")) {
    reserveAllInventory();
}

if((previousValues["Inventory Command"] != "Unsell") && (currentValues["Inventory Command"] == "Unsell")) {
    unSellInventory();
}



def cancelInventoryReserves() {
    List childRecords = getChildren("Task List", "Link to Counter Ticket") as List;
    childRecords.each {
        it["Inventory Command"] = "Cancel";
    }
}

def sellAllInventory() {
    List childRecords = getChildren("Task List", "Link to Counter Ticket") as List;
    childRecords.each {
        it["Inventory Command"] = "Sell";
    }
}

def reserveAllInventory() {
    List childRecords = getChildren("Task List", "Link to Counter Ticket") as List;
    childRecords.each {
        it["Inventory Command"] = "Reserve";
    }
}

def unSellInventory() {
    List childRecords = getChildren("Task List", "Link to Counter Ticket") as List;
    childRecords.each {
        it["Inventory Command"] = "Unsell"
    }
}

