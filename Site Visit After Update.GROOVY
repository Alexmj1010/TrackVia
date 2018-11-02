/*___________________________________________________________________________________________________|
| This appscript records several dates and date time stamps.                                         |
| First, is a completed date, second is an invoiced date.                                            |
| Lastly, it captures job start and finsih time. good for useage with metrics and afterhours.        |
| This Script also Adds a trip charge to the line items table upon site visit completion, then       |
| Updates tax information for line items based off two variables - Location = SC and Product         |
| Datbase has "Exclude From Tax Calculation" as 0.                                                   |
|__________________________________________________________________________________________________*/
//import groovy.time.TimeCategory;



def runIt() {
    
// Define a date to be used in all following cases.
def date = new Date();
String auditTrail = "";


// This logic captures two dates.  One is a reugular date for grouping puproses,
// The other date is a date time stamp of when the site visit was completed.
// If status changes from anything other then complete to complete, capture date, and capture date and time.
if((previousValues["Status"] != "Completed" ) && (currentValues["Status"] == "Completed")) {
    currentValues["AppScriptCompleteDate"] = date; // Capture date finished.
    currentValues["Calc Job Finished"] = date; // Capture date and time finished.
    
    // Build Audit Trail
    String stringTemp = currentValues["Calc Job Finished"] as String;
    auditTrail = currentValues["Audit Trail"] as String;
    auditTrail += ("\n" + "Completed: " + stringTemp);
    currentValues["Audit Trail"] = auditTrail;
    
    // If After Hours Service Call, Set AppScript After Hours Pay value to 1
    String serviceCall = currentValues["Link to Service Call"].toString();
    if(serviceCall.contains("After Hours")) {
        currentValues["AppScript To After Hours Pay"] = 1;
    }
    
    // If Site Visit Status is Completed and "Tech Status" == "OnSite" change to "OffSite"
    if(currentValues["Tech Status"] == "Onsite") {
        currentValues["Tech Status"] = "Offsite";
    }
    
    
    /*______________________________________________/
    / INVENTORY CODE GOES HERE!!! UPON LAUNCH       |
    / Insert SELL Command Here! - once site visit   |
    / is completed, sell everything!                |
    /______________________________________________*/
    
    
    
    
    
//___________________LINE ITEMS CODE BELOW!____________________________________//
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
    
    
    // Grab all child line items and update them
    List lineItems = getChildren("Line Items Join Table","Link to Site Visit") as List;
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
        if((it["Link to Task List"] == null) && (it["Task Name"] != "Trip Charge")) {
            it["Type"] = "Discard";
            it["Total Cost"] = 0;
            it["Our Total Cost"] = 0;
            it["Profit Margin"] = 0;
            it["Tax"] = 0;
            it["Total With Tax"] = 0;
        } // Possibly Combine these??
        if((it["Link to Task Builder"] == null) && (it["Task Name"] != "Trip Charge")) { 
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
            // If scTax (From above) is NON and type is service, change line items to non taxable
            if((scTax == "NON") && (it["Type"] == "Service")) {
                it["Taxable"] = "NON";
            }
            // Check for those special products that are not taxed, namely, software
            if((it["Link to Task Builder"]["Link to Product Database"]["Exclude From Tax Calculation"] == 1) && (it["Type"] == "Product")) {
                it["Taxable"] = "NON"; // BUG If service is attached to same task builder as the product, then issue!
            }
            // If Customer is NON Taxable, update Line Items
            if(cusTax == "NON") {
                it["Taxable"] = "NON";
            }
            // If line item is taxable, calculate cost of this tax
            if(it["Taxable"] == "TAX") {
                it["Tax"] = ((((it["Cost"] as double) * (it["Quantity"] as double)).round(2)) * (currentValues["Link to Location / Facility"]["Link to Tax Rates"]["EstimatedCombinedRate"] as double)).round(7);
            }
            //Calculate remaining values
            it["Total Cost"] = ((it["Quantity"] as double) * (it["Cost"] as double)).round(2);
            it["Our Total Cost"] = (it["Quantity"] as double) * (it["Our Cost"] as double);
            it["Profit Margin"] = (it["Total Cost"] as double) - (it["Our Total Cost"] as double);
            it["Total With Tax"] = ((it["Total Cost"] as double) + (it["Tax"] as double)).round(2);
        }
    }
}
  
// After creating a site visit - add the service charge with line items total already calculated

if(((previousValues["Link to Service Call"] == null) && (currentValues["Link to Service Call"] != null)) || ((previousValues["Link to Service Call"] != currentValues["Link to Service Call"]))) { // has changed from null to selected.
    if(previousValues["Link to Service Call"] != currentValues["Link to Service Call"]) {
        List lineItems = getChildren("Line Items Join Table","Link to Site Visit") as List;
        lineItems.each {
            if(it["Task Name"] == "Trip Charge") {
                it["Type"] = "Discard";
                it["Total Cost"] = 0;
                it["Our Total Cost"] = 0;
                it["Profit Margin"] = 0;
                it["Tax"] = 0;
                it["Total With Tax"] = 0;
            }
        }
    }
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
  
    
//__________________________END LINE ITEMS CODE___________________________________________________//    
    
// This logic captures the date invoice is completed.
if(previousValues["Invoiced?"] != "Yes") {
    if(currentValues["Invoiced?"] == "Yes") {
        currentValues["AppScriptInvoiceDate"] = date; // Capture Date invoiced.
    }
}

// Capture Date Discarded
if((previousValues["Invoiced?"] != "Discarded") && (currentValues["Invoiced?"] == "Discarded")) {
    currentValues["AppScriptDiscardedDate"] = date; // Capture date discarded.
}

// This Logic captures when the first task is started.
if((previousValues["Current Completed"] == 0 || previousValues["Current Completed"] == null) && currentValues["Current Completed"] != 0) {
    currentValues["Calc Job Started"] = date; //new Date();
}

//___________AUDIT TRAIL CODE BELOW_________________________________________//

// Capture date time of tech arriving onsite
if((previousValues["Tech Status"] == "Offsite") && (currentValues["Tech Status"] == "Onsite")) {
    //
    currentValues["Onsite Time"] = date;
    String stringTemp = currentValues["Onsite Time"] as String;
    auditTrail = currentValues["Audit Trail"] as String;
    auditTrail += ("\n" + "Onsite: " + stringTemp);
    currentValues["Audit Trail"] = auditTrail;
}

// Capture date time of tech leaving site
if((previousValues["Tech Status"] == "Onsite") && (currentValues["Tech Status"] == "Offsite")) {
    //
    currentValues["Offsite Time"] = date;
    String stringTemp = currentValues["Offsite Time"] as String;
    auditTrail = currentValues["Audit Trail"] as String;
    auditTrail += ("\n" + "Offsite: " + stringTemp);
    currentValues["Audit Trail"] = auditTrail;
    int i = currentValues["Times OnSite"] as int;
    i = i + 1;
    currentValues["Times OnSite"] = i;
}

/* DEPRECATED...
// If status goes from not assigned to assigned, capture date and add to audit trail
if((previousValues["Status"] != "Assigned") && (currentValues["Status"] == "Assigned")) {
    currentValues["Calc_Assigned"] = date; // Update Date
    String stringTemp = currentValues["Calc_Assigned"] as String; // Convert to string
    auditTrail = currentValues["Audit Trail"] as String; // Grab current audit trail
    auditTrail += ("\n" + "Assigned: " + stringTemp); // Append update
    currentValues["Audit Trail"] = auditTrail; // Save on site visit record
}*/
// If status goes from not scheduled to scheduled, capture date and add to audit trail
if((previousValues["Status"] != "Scheduled") && (currentValues["Status"] == "Scheduled")) {
    currentValues["Calc_Scheduled"] = date;
    String stringTemp = currentValues["Calc_Scheduled"] as String;
    auditTrail = currentValues["Audit Trail"] as String;
    auditTrail += ("\n" + "Scheduled: " + stringTemp);
    currentValues["Audit Trail"] = auditTrail;
}
// If status goes from not waiting to waiting, capture date and add to audit trail
if((previousValues["Status"] != "Waiting") && (currentValues["Status"] == "Waiting")) {
    currentValues["Calc_Waiting"] = date;
    String stringTemp = currentValues["Calc_Waiting"] as String;
    auditTrail = currentValues["Audit Trail"] as String;
    auditTrail += ("\n" + "Waiting: " + stringTemp);
    currentValues["Audit Trail"] = auditTrail;
}
// If status goes to waiting to schedule, capture date and add to audit trail
if((previousValues["Status"] != "Waiting to Schedule") && (currentValues["Status"] == "Waiting to Schedule")) {
    currentValues["Calc_WaitingToSchedule"] = date;
    String stringTemp = currentValues["Calc_WaitingToSchedule"] as String;
    auditTrail = currentValues["Audit Trail"] as String;
    auditTrail += ("\n" + "Waiting to Schedule: " + stringTemp);
    currentValues["Audit Trail"] = auditTrail;
    currentValues["Route"] = null;
}
// If status goes from not cancelled to cancelled, capture date and add to audit trail
if((previousValues["Status"] != "Cancelled") && (currentValues["Status"] == "Cancelled")) {
    currentValues["Calc_Cancelled"] = date;
    String stringTemp = currentValues["Calc_Cancelled"] as String;
    auditTrail = currentValues["Audit Trail"] as String;
    auditTrail += ("\n" + "Cancelled: " + stringTemp);
    currentValues["Audit Trail"] = auditTrail;
    List childTasks = getChildren("Task List","Link to Site Visit") as List;
    childTasks.each {
        it["Progress"] = "Cancelled";
    }
}

//___________END AUDIT TRAIL CODE_________________________________________//


// ToZapier QBO code, change invoiced? to yes
if(currentValues["BeginAutomation"] == "yes") {
    currentValues["Invoiced?"] = "Yes";
}
// Same as above but for sales receipt
String salesReceiptString = currentValues["Automate Sales Receipt"].toString();
if(salesReceiptString.contains("yes")) {
    currentValues["Invoiced?"] = "Yes";
}

// Change tag to ordered once all parts from parts requests are ordered
if(((currentValues["AppScript Number PO Ordered"] as int) > 0) && ((previousValues["AppScript Number PO Ordered"] as int) == 0)) {
    currentValues["Tag"] = "Ordered";
}

// Once tag parts arrived, update status and time block to be scheduled
String tag_String = currentValues["Tag"].toString();
String prev_tag_String = previousValues["Tag"].toString();
// Also holds value AppScript_PartsArrived to ensure it does not update contanstly.
if((currentValues["AppScript_PartsArrived"] != 1) && (tag_String.contains("Parts Arrived"))) {
    currentValues["Status"] = "Waiting to Schedule";
    currentValues["Time Block"] = "Not Set";
    // Causes bug, If second time going through waiting on purchasing, this does not run
    currentValues["AppScript_PartsArrived"] = 1;
}

//______________________________MORE LINE ITEMS CODE____________________________________________//
//             ___ WILL BE DEPRECATED UPON LINE ITEMS LAUNCH ____                              //
//           _____ CAN BE USED TO "RE DO" LINE ITEMS CALCULATION _                            //
//___________________________________________________________________________________________//

// When process admin is changed to yes, grab all the tasks, and change their process admin to yes
if((currentValues["Process ADMIN"] == "YES") && (previousValues["Process ADMIN"] != "YES")) {
    // Grab child task list records and put them in list
    List childRecords = getChildren("Task List","Link to Site Visit") as List;
    childRecords.each { // Iterate that list!
        it["Process ADMIN"] = "YES"; // Update Process admin to yes on all child records
    }
}
// Same as above, but undo...
if((currentValues["Process ADMIN"] == null) && (previousValues["Process ADMIN"] != null)) {
    List childRecords = getChildren("Task List","Link to Site Visit") as List;
    childRecords.each {
        it["Process ADMIN"] = null;
    }
}
// After line items have been "Processed" or "Gathered", use below to reconcile.
// ____ SEE code lines 35 - 113 ____ // // Duplicate Code Alert!!!!! Comments in code above, does same thing!
if((previousValues["Reconcile ADMIN"] != "YES" ) && (currentValues["Reconcile ADMIN"] == "YES")) {
    String scTax = "TAX";
    String cusTax = "TAX";
    double taxAmount = ((currentValues["Link to Service Call"]["Cost"] as double) * (currentValues["Link to Location / Facility"]["Link to Tax Rates"]["EstimatedCombinedRate"] as double)).round(7);
    if(currentValues["Link to Location / Facility"]["State"] == "SC") {
            scTax = "NON";
            taxAmount = 0;
    }
    // Check if Customer Is taxable for the trip charge
    if(currentValues["Link to Customers"]["Taxable?"] == "NON") {
        cusTax = "NON";
        scTax = "NON";
        taxAmount = 0;
    }
    //if(currentValues["Link to Service Call"]["Service Name"] != "No Charge") 
    if(currentValues["Link to Service Call"] != null) {
        addChild "Line Items Join Table","Link to Site Visit",['Type' : "Service",
                                      'Identifier' : currentValues["Link to Service Call"]["Service Name"],
                                      'Description' : currentValues["Link to Service Call"]["Service Name"],
                                      'Quantity' : 1,
                                      'Taxable' : scTax,
                                      'QBO_ID' : currentValues["Link to Service Call"]["QBO_Ref_ID"],
                                      'Our Cost' : 0,
                                      'Task Name' : "Trip Charge",
                                      //'Margin' : currentValues["Link to Service Call"]["Cost"],
                                      //'Link to Task Builder' : _this,
                                      'Tax' : taxAmount,
                                      'Total Cost' : currentValues["Link to Service Call"]["Cost"],
                                      'Our Total Cost' : 0,
                                      'Profit Margin' : currentValues["Link to Service Call"]["Cost"],
                                      'Total With Tax' : (taxAmount + (currentValues["Link to Service Call"]["Cost"] as double)).round(2),
                                      'Cost' : currentValues["Link to Service Call"]["Cost"]];
    }
    List lineItems = getChildren("Line Items Join Table","Link to Site Visit") as List;
    lineItems.each {
        if(it["Type"] == "Discard") {
            it["Total Cost"] = 0;
            it["Our Total Cost"] = 0;
            it["Profit Margin"] = 0;
            it["Tax"] = 0;
            it["Total With Tax"] = 0;
        }
        //logger.error(it["Link to Task List"].toString());
        if((it["Link to Task List"] == null) && (it["Task Name"] != "Trip Charge")) {
            it["Type"] = "Discard";
            it["Total Cost"] = 0;
            it["Our Total Cost"] = 0;
            it["Profit Margin"] = 0;
            it["Tax"] = 0;
            it["Total With Tax"] = 0;
        } // Possibly Combine these??
        if((it["Link to Task Builder"] == null) && (it["Task Name"] != "Trip Charge")) { 
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
            if((scTax == "NON") && (it["Type"] == "Service")) {
                it["Taxable"] = "NON";
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
            if(currentValues["Link to Customers"]["Taxable?"] == "NON") {
                it["Taxable"] = "NON";
            }
            if(it["Taxable"] == "TAX") {
                it["Tax"] = (((it["Cost"] as double) * (it["Quantity"] as double)).round(2) * (currentValues["Link to Location / Facility"]["Link to Tax Rates"]["EstimatedCombinedRate"] as double)).round(7);
            }
            it["Total Cost"] = ((it["Quantity"] as double) * (it["Cost"] as double)).round(2);
            it["Our Total Cost"] = (it["Quantity"] as double) * (it["Our Cost"] as double);
            it["Profit Margin"] = (it["Total Cost"] as double) - (it["Our Total Cost"] as double);
            it["Total With Tax"] = ((it["Total Cost"] as double) + (it["Tax"] as double)).round(2);
        }
    }

    
}

}

/*________________________________________________________________________________________________________|
/ Bulk inventory operations below.  These are not to be used by the general public, and only for admin    |
/ Processes.  These are behind the scenes operations purely used for "Legacy" Site Visits                 |
/_______________________________________________________________________________________________________*/

if((currentValues["Inventory Command"] == null) && (previousValues["Inventory Command"] != null)) {
    List childRecords = getChildren("Task List","Link to Site Visit") as List;
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
    List childRecords = getChildren("Task List", "Link to Site Visit") as List;
    childRecords.each {
        it["Inventory Command"] = "Cancel";
    }
}

def sellAllInventory() {
    List childRecords = getChildren("Task List", "Link to Site Visit") as List;
    childRecords.each {
        it["Inventory Command"] = "Sell";
    }
}

def reserveAllInventory() {
    List childRecords = getChildren("Task List", "Link to Site Visit") as List;
    childRecords.each {
        it["Inventory Command"] = "Reserve";
    }
}

def unSellInventory() {
    List childRecords = getChildren("Task List", "Link to Site Visit") as List;
    childRecords.each {
        it["Inventory Command"] = "Unsell"
    }
}



// Run it all!
runIt();
