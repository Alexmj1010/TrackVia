/*________________________________________________________________________________________/
/ App: Site Visits
/ Table: _Service Flow Support Table
/ Event: Before Insert
/_____________________________________________________________________________________*/

Map newContact = null;;
Map newPhoneNumber = currentValues["Link to Phone Numbers"] as Map;
Map newCustomer = currentValues["Link to Customers"] as Map;
Map newLocation = currentValues["Link to Location / Facility"] as Map;


if(currentValues["Contact Exists?"] == "No") { 
    newContact = save "Contacts",['First Name' : currentValues["First Name"],
                                  'Last Name' : currentValues["Last Name"],
                                  'Email' : currentValues["Email"]];
    newPhoneNumber = save "Phone Numbers",['Phone Number' : currentValues["Phone Number"],
                          'Type' : currentValues["Type"],
                          'Link to Contacts' : newContact];
    currentValues["Link to Phone Numbers"] = newPhoneNumber;
    currentValues["Link to Contacts"] = newContact;
}

if(currentValues["Contact Exists?"] == "Yes") {
    newPhoneNumber = currentValues["Link to Phone Numbers"] as Map;
    newContact = currentValues["Link to Phone Numbers"]["Link to Contacts"] as Map;
}

if(currentValues["Location Exists?"] == "No") {
    newLocation = save "Location / Facility",['Facility Name' : currentValues["Facility Name"],
                                              'Facility Address' : currentValues["Facility Address"],
                                              'City' : currentValues["City"],
                                              'State' : currentValues["State NEW"],
                                              'Link to Tax Rates' : currentValues["Link to Tax Rates"],
                                              'Property Type' : currentValues["Property Type"],
                                              'Facility Notes' : currentValues["Facility Notes"]];
    currentValues["Link to Location / Facility"] = newLocation;
}

if(currentValues["Location Exists?"] == "Yes") {
    newLocation = currentValues["Link to Location / Facility"] as Map;
}

if(currentValues["Customer Exists?"] == "No") {
    logger.info("I'm here at customer doesn't exist");
    newCustomer = save "Customers",['Business Name' : currentValues["Business Name"],
                                    'Customer Type' : currentValues["Customer Type"],
                                    'Invoice Reference Number (PO)' : currentValues["PO Number Required?"],
                                    'Taxable?' : currentValues["Taxable?"],
                                    'Account Status' : currentValues["Account Status"],
                                    'Customer Notes' : currentValues["Customer Notes"]];
    currentValues["Link to Customers"] = newCustomer;
}

if(currentValues["Customer Exists?"] == "Yes") {
    logger.error("I'm here at customer does exist");
    newCustomer = currentValues["Link to Customers"] as Map;
}


/*________________________________________________________________________________________/
/ App: Site Visits
/ Table: _Service Flow Support Table
/ Event: After Insert
/_____________________________________________________________________________________*/

currentValues["Link to Phone Numbers"]["Link to Contacts"]["Link to Customers"] = currentValues["Link to Customers"];
currentValues["Link to Location / Facility"]["Link to Customers"] = currentValues["Link to Customers"];

/*________________________________________________________________________________________/
/ App: Site Visits
/ Table: After Hours Pay
/ Event: After Insert
/_____________________________________________________________________________________*/

/*______________________________________________________________________________________
| This Application Script is used to set user fields on after hours pay table           \
| It reads a string containing a list of technicians associated with the after hours     |
| Visit.  It then populates a system user field based on that string of users.           |
| This allows users to view their after hours pay from a dashboard view.                 |
|______________________________________________________________________________________*/
 
// Create String containing the names of all the techs assigned to the after hours site visit
String technicians = currentValues["Technician(s) Assigned"] as String;

//Test if user is "User Name"
if(technicians.contains("Lucas_Test")) {
    //If user, then set after hours pay user field to xvia application user.
    Map systemUser = loadRecord("Account Users", "Email", "wardl.mobile@gmail.com") as Map; // Map System User
    currentValues["Join_Lucas_Test"] = systemUser; // Populate System User Field
}

//Sam Gray
if(technicians.contains("Sam Gray")) {
    Map systemUser = loadRecord("Account Users", "Email", "sam@locdoc.net") as Map;
    currentValues["Join_Sam_Gray"] = systemUser;
}

// Derek Tyler
if(technicians.contains("Derek Tyler")) {
    Map systemUser = loadRecord("Account Users", "Email", "derek@locdoc.net") as Map;
    currentValues["Join_Derek_Tyler"] = systemUser;
}

// Dustin Fite
if(technicians.contains("Dustin Fite")) {
    Map systemUser = loadRecord("Account Users", "Email", "dustin@locdoc.net") as Map;
    currentValues["Join_Dustin_Fite"] = systemUser;
}

// Luke Hanson
if(technicians.contains("Luke Hanson")) {
    Map systemUser = loadRecord("Account Users", "Email", "luke@locdoc.net") as Map;
    currentValues["Join_Luke_Hanson"] = systemUser;
}

// Stephen Clubb
if(technicians.contains("Stephen Clubb")) {
    Map systemUser = loadRecord("Account Users", "Email", "stephen@locdoc.net") as Map;
    currentValues["Join_Stephen_Clubb"] = systemUser;
}

// Sergey Shamshur
if(technicians.contains("Sergey Shamshur")) {
    Map systemUser = loadRecord("Account Users", "Email", "sergey@locdoc.net") as Map;
    currentValues["Join_Sergey_Shamshur"] = systemUser;
}

// Christopher Baggett
if(technicians.contains("Christopher Baggett")) {
    Map systemUser = loadRecord("Account Users", "Email", "christopher@locdoc.net") as Map;
    currentValues["Join_Christopher_Baggett"] = systemUser;
}

// Josh McVickers
if(technicians.contains("Josh McVickers")) {
    Map systemUser = loadRecord("Account Users", "Email", "josh@locdoc.net") as Map;
    currentValues["Join_Josh_McVickers"] = systemUser;
}

// James Caul - NO LONGER ACTIVE
if(technicians.contains("James Caul")) {
    Map systemUser = loadRecord("Account Users", "Email", "james@locdoc.net") as Map;
    currentValues["Join_James_Caul"] = systemUser;
}

// Andrew Moody
if(technicians.contains("Andrew Moody")) {
    Map systemUser = loadRecord("Account Users", "Email", "andrew@locdoc.net") as Map;
    currentValues["Join_Andrew_Moody"] = systemUser;
}

// Andy Tucker
if(technicians.contains("Andy Tucker")) {
    Map systemUser = loadRecord("Account Users", "Email", "andy@locdoc.net") as Map;
    currentValues["Join_Andy_Tucker"] = systemUser;
}

// Leonard McConniel
if(technicians.contains("Leonard McConniel")) {
    Map systemUser = loadRecord("Account Users", "Email", "leonard@locdoc.net") as Map;
    currentValues["Join_Leonard_McConniel"] = systemUser;
}

// Mike Tyler
if(technicians.contains("Mike Tyler")) {
    Map systemUser = loadRecord("Account Users", "Email", "Mike@locdoc.net") as Map;
    currentValues["Join_Mike_Tyler"] = systemUser;
}

// Dylan Tyler
if(technicians.contains("Dylan Tyler")) {
    Map systemUser = loadRecord("Account Users", "Email", "dylan@locdoc.net") as Map;
    currentValues["Join_Dylan_Tyler"] = systemUser;
}

// Josh Smith
if(technicians.contains("Josh Smith")) {
    Map systemUser = loadRecord("Account Users", "Email", "josh.smith@locdoc.net") as Map;
    currentValues["Join_Josh_Smith"] = systemUser;
}

// Thomas Heavey
if(technicians.contains("Thomas Heavey")) {
    Map systemUser = loadRecord("Account Users", "Email", "thomas@locdoc.net") as Map;
    currentValues["Join_Thomas_Heavey"] = systemUser;
}

// Zach Phifer
if(technicians.contains("Zach Phifer")) {
    Map systemUser = loadRecord("Account Users", "Email", "zach@locdoc.net") as Map;
    currentValues["Join_Zach_Phifer"] = systemUser;
}

// Michael Childress
if(technicians.contains("Michael Childress")) {
    Map systemUser = loadRecord("Account Users", "Email", "michael@locdoc.net") as Map;
    currentValues["Join_Michael_Childress"] = systemUser;
}

// William Gleaves
if(technicians.contains("William Gleaves")) {
    Map systemUser = loadRecord("Account Users", "Email", "william@locdoc.net") as Map;
    currentValues["Join_William_Gleaves"] = systemUser;
}

// Jordan Boatwright
if(technicians.contains("Jordan Boatwright")) {
    Map systemUser = loadRecord("Account Users", "Email", "jordan@locdoc.net") as Map;
    currentValues["Join_Jordan_Boatwright"] = systemUser;
}
    
// Eric Spear
if(technicians.contains("Eric Spear")) {
    Map systemUser = loadRecord("Account Users", "Email", "eric@locdoc.net") as Map;
    currentValues["Join_Eric_Spear"] = systemUser;
}
    
// Scotty Hinson
if(technicians.contains("Scotty Hinson")) {
    Map systemUser = loadRecord("Account Users", "Email", "scotty@locdoc.net") as Map;
    currentValues["Join_Scotty_Hinson"] = systemUser;
    
}

/*________________________________________________________________________________________/
/ App: Site Visits
/ Table: AppScript Assistant
/ Event: After Update
/_____________________________________________________________________________________*/

/*______________________________________________________________________________________
| This Table is only utilized for Application Script Tasks... It acts as                |
| an invisible parent and its sole use is to run scripts on child records.              |
|______________________________________________________________________________________*/

// ReOccurring Charges App Script Initializer - Allows the bulk running of scripts across children
if((currentValues["Run"] == "Yes") && (previousValues["Run"] == null)) { // So it does run multiple times
    // Get all the ReOccurring Charges Children into a list
    List childRecords = getChildren("Reoccurring Charges", "Link to AppScript Assistant") as List;
    // Process them one at a time
    childRecords.each {
        // Initialize Scripts on child records
        it["Run"] = "Yes";
    }
    // Return "Run" to the null position
    currentValues["Run"] = null;
}

/*________________________________________________________________________________________/
/ App: Site Visits
/ Table: Billing Record
/ Event: After Insert
/_____________________________________________________________________________________*/

/*________________________________________________________________________/
/ This script converts the ID provided from QBO for a customers TERMS    /
/ into the human readable "NET x" value - x is number of days           /
/_____________________________________________________________________*/

if(currentValues["QBO_Terms_ID"]) {
    // If QBO Terms ID is equal to 5 - set billing terms to Net 10
    if(currentValues["QBO_Terms_ID"] == 5) {
        currentValues["Billing Terms"] = "Net 10"
    }
    if(currentValues["QBO_Terms_ID"] == 6) {
        currentValues["Billing Terms"] = "Net 15"
    }
    if(currentValues["QBO_Terms_ID"] == 7) {
        currentValues["Billing Terms"] = "Net 30"
    }
    if(currentValues["QBO_Terms_ID"] == 8) {
        currentValues["Billing Terms"] = "Net 60"
    }
    if(currentValues["QBO_Terms_ID"] == 9) {
        currentValues["Billing Terms"] = "Net 30"
    }
    if(currentValues["QBO_Terms_ID"] == 10) {
        currentValues["Billing Terms"] = "Net 30"
    }
    if(currentValues["QBO_Terms_ID"] == 11) {
        currentValues["Billing Terms"] = "Net 10"
    }
    if(currentValues["QBO_Terms_ID"] == 12) {
        currentValues["Billing Terms"] = "Net 20"
    }
    if(currentValues["QBO_Terms_ID"] == 13) {
        currentValues["Billing Terms"] = "Net 45"
    }
    if(currentValues["QBO_Terms_ID"] == 14) {
        currentValues["Billing Terms"] = "Net 30"
    }
    if(currentValues["QBO_Terms_ID"] == 15) {
        currentValues["Billing Terms"] = "Net 10"
    }
    if(currentValues["QBO_Terms_ID"] == 16) {
        currentValues["Billing Terms"] = "Net 100"
    }
    
}

/*________________________________________________________________________________________/
/ App: Site Visits
/ Table: Combination Locks
/ Event: After Insert
/_____________________________________________________________________________________*/

/*______________________________________________________________________________________
| This app script runs every time a new combination lock is added to the table.         |
| Its purpose is to simply copy the record to the all bitting table.                    |
|______________________________________________________________________________________*/

addChild "All Bitting", "Link to Combination Locks", ['Bitting Type' : "Combination",
                                                    'Door' : currentValues["Door"],
                                                    'Suite' : currentValues["Suite"],
                                                    'Tech Link' : currentValues["Created By User"],
                                                    'Notes' : currentValues["Notes"],
                                                    'User Combination' : currentValues["User Combination"],
                                                    'Programming Combination' : currentValues["Programming Combination"],
                                                    'Link to Location / Facility' : currentValues["Link to Location / Facility"]];

/*________________________________________________________________________________________/
/ App: Site Visits
/ Table: Communication Notes
/ Event: After Insert
/_____________________________________________________________________________________*/

/*____________________________________________________________/
/ This Portion of the script manipulates site                 |
/ visit status by checking note types! (interactions module)  |
/____________________________________________________________*/

// Interaction Note - Changes status to Waiting to Schedule
if(currentValues["Link to Note Types"]["Note Type"] == "Need Help - to Waiting to Schedule") {
    currentValues["Link to Site Visit"]["Status"] = "Waiting to Schedule";
}

// Interaction Note - Changes status to Cancelled
if(currentValues["Link to Note Types"]["Note Type"] == "Cancel Site Visit") {
    currentValues["Link to Site Visit"]["Status"] = "Cancelled";
}

// Interaction Note - Changes status to Completed
if(currentValues["Link to Note Types"]["Note Type"] == "Assessment Complete - Quote Request") {
    currentValues["Link to Site Visit"]["Status"] = "Completed";
    Map noCharge = loadRecord "Service Call", ['Service Name' : "No Charge"] as Map;
    currentValues["Link to Site Visit"]["Link to Service Call"] = noCharge;
}

/*____________________________________________________________/
/ This Portion of the script provides upwards linking         |
/ to parents, grandparents, and great grandparent fields      |
/____________________________________________________________*/

// if linked to an opening, also link to parent loc / fac, and their parent customer
if(currentValues["Link to Locations Openings"]) {
    currentValues["Link to Location / Facility"] = currentValues["Link to Locations Openings"]["Link to Location / Facility"];
    currentValues["Link to Customers"] = currentValues["Link to Locations Openings"]["Link to Location / Facility"]["Link to Customers"];
}

// if linked to a location, then also link it to the location's customer
if(currentValues["Link to Location / Facility"]) {
    currentValues["Link to Customers"] = currentValues["Link to Location / Facility"]["Link to Customers"];
}

// if linked to a site visit, then link it to the site visits location and customer
if(currentValues["Link to Site Visit"]) {
    currentValues["Link to Customers"] = currentValues["Link to Site Visit"]["Link to Customers"];
    currentValues["Link to Location / Facility"] = currentValues["Link to Site Visit"]["Link to Location / Facility"];
}

// if linked to a quote, then also link it to the quotes location and customer
if(currentValues["Link to Quote Builder"]) {
    currentValues["Link to Customers"] = currentValues["Link to Quote Builder"]["Link to Customers"];
    currentValues["Link to Location / Facility"] = currentValues["Link to Quote Builder"]["Link to Location / Facility"];
}

// if linked to a counter ticket, then also link it to the counter tickets customer and location / facility
if(currentValues["Link to Counter Ticket"]) {
    currentValues["Link to Customers"] = currentValues["Link to Counter Ticket"]["Link to Customers"];
    currentValues["Link to Location / Facility"] = currentValues["Link to Counter Ticket"]["Link to Location / Facility"];
}

// if linked to a task, then much linking must be performed!!!!
if(currentValues["Link to Task List"]) {
    
    // if task has parent site visit, then link to site visit as well as site visits customer and location
    if(currentValues["Link to Task List"]["Link to Site Visit"]) {
        currentValues["Link to Site Visit"] = currentValues["Link to Task List"]["Link to Site Visit"];
        currentValues["Link to Location / Facility"] = currentValues["Link to Task List"]["Link to Site Visit"]["Link to Location / Facility"];
        currentValues["Link to Customers"] = currentValues["Link to Task List"]["Link to Site Visit"]["Link to Customers"];
    }
    
    // if task has parent quote, same as above.
    if(currentValues["Link to Task List"]["Link to Quote Builder"]) {
        currentValues["Link to Quote Builder"] = currentValues["Link to Task List"]["Link to Quote Builder"];
        currentValues["Link to Location / Facility"] = currentValues["Link to Task List"]["Link to Quote Builder"]["Link to Location / Facility"];
        currentValues["Link to Customers"] = currentValues["Link to Task List"]["Link to Quote Builder"]["Link to Customers"];
    }
    
    // if task has parent counter ticket, then same as above.
    if(currentValues["Link to Task List"]["Link to Counter Ticket"]) {
        currentValues["Link to Counter Ticket"] = currentValues["Link to Task List"]["Link to Counter Ticket"];
        currentValues["Link to Location / Facility"] = currentValues["Link to Task List"]["Link to Counter Ticket"]["Link to Location / Facility"];
        currentValues["Link to Customers"] = currentValues["Link to Task List"]["Link to Counter Ticket"]["Link to Customers"];
    }
    
    // task has parent opening, then link the opening!
    if(currentValues["Link to Task List"]["Link to Locations Openings"]) {
        currentValues["Link to Locations Openings"] = currentValues["Link to Task List"]["Link to Locations Openings"];
    }
}

/*________________________________________________________________________________________/
/ App: Site Visits
/ Table: Counter Ticket
/ Event: Before Insert
/_____________________________________________________________________________________*/

/*______________________________________________________________________________________
| Simply set a few default values using this app script.                                \
|______________________________________________________________________________________*/

currentValues["Total Completion Calc"] = 0;
currentValues["Current Completed"] = 0;
currentValues["Invoiced?"] = "No";

Map uniqIDRecord = loadRecord "Unique Identifier Generator","For Table","Counter Ticket";
int uniqID = uniqIDRecord["UniqID"] as int;
currentValues["UniqID"] = uniqID;
uniqID = uniqID + 1;
uniqIDRecord["UniqID"] = uniqID;

/*________________________________________________________________________________________/
/ App: Site Visits
/ Table: Counter Ticket
/ Event: After Update
/_____________________________________________________________________________________*/

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
    sellAllInventory();
    
    
    
}

// Capture date site visit is invoiced
if((previousValues["Invoiced?"] != "Yes") && (currentValues["Invoiced?"] == "Yes")) {
    currentValues["AppScriptInvoiceDate"] = date;
}

// Capture date site visit is discarded (by accounting)
if((previousValues["Invoiced?"] != "Discard") && (currentValues["Invoiced?"] == "Discard")) {
    currentValues["AppScriptDiscardedDate"] = date;
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
                if(currentValues["Pricing Year"] == "Mid 2018") {
                    it["Cost"] = it["Link to Task Builder"]["Link to Product Database"]["Mid 2018 MSRP"];
                }
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

/*________________________________________________________________________________________/
/ App: Site Visits
/ Table: Customers
/ Event: Before Insert
/_____________________________________________________________________________________*/

if(!currentValues["Invoice Reference Number (PO)"]) {
    currentValues["Invoice Reference Number (PO)"] = "No";
}

/*________________________________________________________________________________________/
/ App: Site Visits
/ Table: Inventory
/ Event: Before Insert
/_____________________________________________________________________________________*/

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

/*________________________________________________________________________________________/
/ App: Site Visits
/ Table: Inventory
/ Event: Before Update
/_____________________________________________________________________________________*/

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
    List taskBuilders = getChildren("Task Builder", "Link to Inventory") as List;
    if(taskBuilders != null) {
        Map getSiteVisit = taskBuilders[0] as Map;
        currentValues["Link to Inventory - Sub Location"]["Link to Site Visit"] = getSiteVisit["Link to Task List"]["Link to Site Visit"];
    }
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
    if(((currentValues["Location_Quantity_Available"] as int) + (currentValues["Location_Quantity_To_Order"] as int) + (currentValues["Location_Quantity_Ordered"] as int)) <= (currentValues["Minimum Buffer"] as int)) {
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

/*________________________________________________________________________________________/
/ App: Site Visits
/ Table: Inventory - Sub Location
/ Event: After Insert
/_____________________________________________________________________________________*/

currentValues["Master Location Name"] = currentValues["Link to Inventory - Master Location"]["Master Location Name"];

/*________________________________________________________________________________________/
/ App: Site Visits
/ Table: Inventory - Sub Location
/ Event: Before Update
/_____________________________________________________________________________________*/

if((currentValues["BIN Ready?"] == "Yes") && (currentValues["Link to Inventory - Sub Location"] != null)) {
    List childRecords = getChildren("Inventory", "Link to Inventory - Sub Location") as List;
    childRecords.each {
        it["Link to Inventory - Sub Location"] = currentValues["Link to Inventory - Sub Location"];
    }
    currentValues["Link to Inventory - Sub Location"] = null;
    currentValues["BIN Ready?"] = "No";
    currentValues["Link to Site Visit"] = null;
}

/*________________________________________________________________________________________/
/ App: Site Visits
/ Table: Inventory Manager
/ Event: After Insert
/_____________________________________________________________________________________*/

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
    String arg = currentValues["Arguments"] as String;
    if(currentValues["UniqID"]) {
        findRecord = loadRecord "Inventory",['UniqID' : currentValues["UniqID"]] as Map;
        logger.error("Using UniqID " + arg + " " + identifier);
    } else {
        findRecord = loadRecord "Inventory",['Identifier' : identifier] as Map;
        logger.error("Not Using UniqID " + arg + " " + identifier);
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
        //updateGlobalQuantities(product, quantity, arg);
        
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
            //logger.error("i'm here at Add");
            //currentValues["Link to Task builder"]["Link to Inventory"] = invRec;
        }
    }
    
    // If product does not exist at location, run this script
    if(arg == "Add DNE") {
        //logger.error("I'm here1!");
        //updateGlobalQuantities(product, quantity, "Add");
        // If inventory location already has some stock with this product, update it
        if(inventoryRecord) { 
            // Get Values
            //logger.error("I'm here2!");

            int localQuantityOnHand = inventoryRecord["Location_Quantity_On_Hand"] as int;
            int localQuantityAvailable = inventoryRecord["Location_Quantity_Available"] as int;
            // Update Them
            inventoryRecord["Location_Quantity_On_Hand"] = localQuantityOnHand + quantity;
            inventoryRecord["Location_Quantity_Available"] = localQuantityAvailable + quantity;
            
            // Link the Inventory Record to this record for auditing purposes
            currentValues["Link to Inventory"] = inventoryRecord;
        
        // If inventory location does not contain this product, create it    
        } else { 
            //logger.error("I'm here3!");
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
            //updateGlobalQuantities(product, quantity, arg);
            
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
            //logger.error("i'm here at Remove");
            
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
            //updateGlobalQuantities(product, quantity, arg);
            
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
            //updateGlobalQuantities(product, quantity, arg);
            
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
            //updateGlobalQuantities(product, quantity, arg);
            
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
            //logger.error("i'm here at Unsell");
            //currentValues["Link to Task builder"]["Link to Inventory"] = invRec;
        }
    }
    
    // If product is not on hand and not in inventory, run this script
    if(arg == "Add NIS") {
        //logger.error("I'm hereNIS1!");
        //updateGlobalQuantities(product, quantity, "Reserve");
        // If inventory location already has some stock with this product, update it
        // This is handdled on task builder level using a look up - but if for what ever reason it makes it to here, handle it!
        if(inventoryRecord) { 
            // Get Values
            //logger.error("I'm hereNIS2!");

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
            //logger.error("I'm hereNIS3!");
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

// Transfer Reserved Parts from Location to Location (For use with assign to BIN and Reserved Stock to BIN)
if(currentValues["Options"] == "Transfer Products") {
    updateLocalQuantities(currentValues["Link to Inventory"]["Link to Product Database"] as Map, currentValues["Quantity"] as int, currentValues["Link to Inventory"]["Link to Inventory - Sub Location"] as Map, "Sell" as String);
    updateLocalQuantities(currentValues["Link to Inventory"]["Link to Product Database"] as Map, currentValues["Quantity"] as int, currentValues["Link to Inventory - Sub Location"] as Map, "Add" as String);
    updateLocalQuantities(currentValues["Link to Inventory"]["Link to Product Database"] as Map, currentValues["Quantity"] as int, currentValues["Link to Inventory - Sub Location"] as Map, "Reserve" as String);
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
    //updateGlobalQuantities(currentValues["Link to Product Database"] as Map, currentValues["Quantity"] as int, "Add" as String);
}

// If parts are ordered via a PO, update quantities with argument Ordered
if(currentValues["Arguments"] == "Order") {
    updateLocalQuantities(currentValues["Link to Product Database"] as Map, currentValues["Quantity"] as int, currentValues["Link to Inventory - Sub Location"] as Map, "Ordered" as String);
}

// If parts arrive from a PO = Closed status, update quanties with argument "Arrived"
if(currentValues["Arguments"] == "Arrived") {
    updateLocalQuantities(currentValues["Link to Product Database"] as Map, currentValues["Quantity"] as int, currentValues["Link to Inventory - Sub Location"] as Map, "Arrived" as String);
}

/*________________________________________________________________________________________/
/ App: Site Visits
/ Table: Inventory Manager
/ Event: Before Insert
/_____________________________________________________________________________________*/

// Remove command may come with minimal input, and it implies that links are already in existence
if(currentValues["Arguments"] == "Remove") {
    currentValues["Link to Product Database"] = currentValues["Link to Inventory"]["Link to Product Database"];
    currentValues["Link to Inventory - Sub Location"] = currentValues["Link to Inventory"]["Link to Inventory - Sub Location"];
}

/*________________________________________________________________________________________/
/ App: Site Visits
/ Table: Line Items Join Table
/ Event: After Insert
/_____________________________________________________________________________________*/

/*______________________________________________________________________________________
|  This app script sets all null fields to 0 after record insert                        \
|  Sometimes records are created with these values pre loaded, this only sets defaults   |
|  When the fields are null                                                              |
|______________________________________________________________________________________*/

// Check total cost field for a value, if null, set to zero
if(currentValues["Total Cost"] == null) {
    currentValues["Total Cost"] = 0;
}
// Check Our Total Cost field for a value, if null, set to zero
if(currentValues["Our Total Cost"] == null) {
    currentValues["Our Total Cost"] = 0;
}
// Check Profit Margin for a value, if null, set to zero
if(currentValues["Profit Margin"] == null) {
    currentValues["Profit Margin"] = 0;
}
// Check Total With Tax for a value, if null, set to zero
if(currentValues["Total With Tax"] == null) {
    currentValues["Total With Tax"] = 0;
}

if(currentValues["Tax"] == null) {
    currentValues["Tax"] = 0;
}


// Instantiate some variables
String scTax = "TAX";
String cusTax = "TAX";
double taxRate = 0;

// Check if linked to Site Visit
if(currentValues["Link to Site Visit"]) {
    // Check if Location is SC
    if(currentValues["Link to Site Visit"]["Link to Location / Facility"]["State"] == "SC") {
        scTax = "NON";
    }
    // Check if Customer Is taxable for the line items
    if(currentValues["Link to Site Visit"]["Link to Customers"]["Taxable?"] == "NON") {
        cusTax = "NON";
        scTax = "NON";
    }
    taxRate = currentValues["Link to Site Visit"]["Link to Location / Facility"]["Link to Tax Rates"]["EstimatedCombinedRate"] as double;
}
// Check if linked to a quote and NOT a site visit
if((currentValues["Link to Quote Builder"] != null) && (currentValues["Link to Site Visit"] == null)) {
    // Check if Location is SC
    if(currentValues["Link to Quote Builder"]["Link to Location / Facility"]["State"] == "SC") {
        scTax = "NON";
    }
    // Check if Customer Is taxable for the line items
    if(currentValues["Link to Quote Builder"]["Link to Customers"]["Taxable?"] == "NON") {
        cusTax = "NON";
        scTax = "NON";
    }
    taxRate = currentValues["Link to Quote Builder"]["Link to Location / Facility"]["Link to Tax Rates"]["EstimatedCombinedRate"] as double;
}
// Check if Linked to a Counter Ticket 
if(currentValues["Link to Counter Ticket"]) {
    
    // Check if Customer Is taxable for the line items
    if(currentValues["Link to Counter Ticket"]["Link to Customers"]["Taxable?"] == "NON") {
        cusTax = "NON";
        scTax = "NON";
    }
    taxRate = 0.0725;
}


    
if((currentValues["Link to Task Builder"] != null) && (currentValues["Link to Task List"] != null)) {

    if((scTax == "NON") && (currentValues["Type"] == "Service")) {
        currentValues["Taxable"] = "NON";
    }
    
    if((currentValues["Link to Task Builder"]["Link to Product Database"]["Exclude From Tax Calculation"] == 1) && (currentValues["Type"] == "Product")) {
        currentValues["Taxable"] = "NON";
    }
    if(cusTax == "NON") {
        currentValues["Taxable"] = "NON";
    }
    if(currentValues["Taxable"] == "TAX") {
        currentValues["Tax"] = (((currentValues["Cost"] as double) * (currentValues["Quantity"] as double)).round(2) * taxRate).round(7);
    }
    currentValues["Total Cost"] = ((currentValues["Quantity"] as double) * (currentValues["Cost"] as double)).round(2);
    currentValues["Our Total Cost"] = (currentValues["Quantity"] as double) * (currentValues["Our Cost"] as double);
    currentValues["Profit Margin"] = (currentValues["Total Cost"] as double) - (currentValues["Our Total Cost"] as double);
    currentValues["Total With Tax"] = ((currentValues["Total Cost"] as double) + (currentValues["Tax"] as double)).round(2);
}

/*________________________________________________________________________________________/
/ App: Site Visits
/ Table: Line Items Join Table
/ Event: After Update
/_____________________________________________________________________________________*/

// Instantiate some variables
String scTax = "TAX";
String cusTax = "TAX";
double taxRate = 0;

// Check if linked to Site Visit
if(currentValues["Link to Site Visit"]) {
    // Check if Location is SC
    if(currentValues["Link to Site Visit"]["Link to Location / Facility"]["State"] == "SC") {
        scTax = "NON";
    }
    // Check if Customer Is taxable for the line items
    if(currentValues["Link to Site Visit"]["Link to Customers"]["Taxable?"] == "NON") {
        cusTax = "NON";
        scTax = "NON";
    }
    taxRate = currentValues["Link to Site Visit"]["Link to Location / Facility"]["Link to Tax Rates"]["EstimatedCombinedRate"] as double;
}
// Check if linked to a quote and NOT a site visit
if((currentValues["Link to Quote Builder"] != null) && (currentValues["Link to Site Visit"] == null)) {
    // Check if Location is SC
    if(currentValues["Link to Quote Builder"]["Link to Location / Facility"]["State"] == "SC") {
        scTax = "NON";
    }
    // Check if Customer Is taxable for the line items
    if(currentValues["Link to Quote Builder"]["Link to Customers"]["Taxable?"] == "NON") {
        cusTax = "NON";
        scTax = "NON";
    }
    taxRate = currentValues["Link to Quote Builder"]["Link to Location / Facility"]["Link to Tax Rates"]["EstimatedCombinedRate"] as double;
}
// Check if Linked to a Counter Ticket 
if(currentValues["Link to Counter Ticket"]) {
    // Check if Customer Is taxable for the line items
    if(currentValues["Link to Counter Ticket"]["Link to Customers"]["Taxable?"] == "NON") {
        cusTax = "NON";
        scTax = "NON";
    }
    taxRate = 0.0725;
}


    
if((currentValues["Link to Task Builder"] != null) && (currentValues["Link to Task List"] != null)) {

    if((scTax == "NON") && (currentValues["Type"] == "Service")) {
        currentValues["Taxable"] = "NON";
    }
    
    if((currentValues["Link to Task Builder"]["Link to Product Database"]["Exclude From Tax Calculation"] == 1) && (currentValues["Type"] == "Product")) {
        currentValues["Taxable"] = "NON";
    }
    if(cusTax == "NON") {
        currentValues["Taxable"] = "NON";
    }
    if(currentValues["Taxable"] == "TAX") {
        currentValues["Tax"] = (((currentValues["Cost"] as double) * (currentValues["Quantity"] as double)).round(2) * taxRate).round(7);
    }
    currentValues["Total Cost"] = ((currentValues["Quantity"] as double) * (currentValues["Cost"] as double)).round(2);
    currentValues["Our Total Cost"] = (currentValues["Quantity"] as double) * (currentValues["Our Cost"] as double);
    currentValues["Profit Margin"] = (currentValues["Total Cost"] as double) - (currentValues["Our Total Cost"] as double);
    currentValues["Total With Tax"] = ((currentValues["Total Cost"] as double) + (currentValues["Tax"] as double)).round(2);
}

if((previousValues["Link to Task Builder"] != null) && (currentValues["Link to Task Builder"] == null)) {
    currentValues["Type"] = "Discard"
    currentValues["Total Cost"] = 0;
    currentValues["Our Total Cost"] = 0;
    currentValues["Profit Margin"] = 0;
    currentValues["Tax"] = 0;
    currentValues["Total With Tax"] = 0;
}

if((previousValues["Update Our Cost"] == null) && (currentValues["Update Our Cost"] == 1)) {
    currentValues["Our Cost"] = currentValues["Link to Task Builder"]["Adjusted Cost"];
    currentValues["Our Total Cost"] = (currentValues["Quantity"] as double) * (currentValues["Link to Task Builder"]["Adjusted Cost"] as double);
}

/*________________________________________________________________________________________/
/ App: Site Visits
/ Table: Line Items Join Table
/ Event: Before Update
/_____________________________________________________________________________________*/

if(currentValues["Type"] == "Discard") {
    currentValues["Cost"] = 0;
    currentValues["Our Cost"] = 0;
    currentValues["Tax"] = 0;
    currentValues["Total Cost"] = 0;
    currentValues["Our Total Cost"] = 0;
    currentValues["Profit Margin"] = 0;
    currentValues["Total With Tax"] = 0;
}

/*________________________________________________________________________________________/
/ App: Site Visits
/ Table: Locations Openings
/ Event: After Insert
/_____________________________________________________________________________________*/

/*______________________________________________________________________________________
|  This app script links new openings to a facilities complex, if it exisits            \
|______________________________________________________________________________________*/

// Check if complex exists for parent location.
if(currentValues["Link to Location / Facility"]) {
    if(currentValues["Link to Location / Facility"]["Link to Complex"] != null) {
        // Link opening to complex if it exists.
        currentValues["Link to Complex"] = currentValues["Link to Location / Facility"]["Link to Complex"];
    }
    
    if(currentValues["Cylinder Type"] == null) {
        currentValues["Cylinder Type"] = currentValues["Link to Location / Facility"]["Default Cylinder Type"];
    }
    
    if(currentValues["Lock Type"] == null) {
        currentValues["Lock Type"] = currentValues["Link to Location / Facility"]["Default Lock Type"];
    }
    
    if(currentValues["Lock Manufacturer"] == null) {
        currentValues["Lock Manufacturer"] = currentValues["Link to Location / Facility"]["Default Lock Manufacturer"];
    }
    
    
}

/*________________________________________________________________________________________/
/ App: Site Visits
/ Table: Locations Openings
/ Event: Before Update
/_____________________________________________________________________________________*/

/*______________________________________________________________________________________
|  Allows for the copying of previously used openings hardware profiles                 \
|______________________________________________________________________________________*/

if(currentValues["Link to Locations Openings"]) {
    currentValues["Door Type"] = currentValues["Link to Locations Openings"]["Door Type"];
    currentValues["Door Type Notes"] = currentValues["Link to Locations Openings"]["Door Type Notes"];
    currentValues["Swing"] = currentValues["Link to Locations Openings"]["Swing"];
    currentValues["Cylinder Type"] = currentValues["Link to Locations Openings"]["Cylinder Type"];
    currentValues["Cylinder Manufacturer"] = currentValues["Link to Locations Openings"]["Cylinder Manufacturer"];
    currentValues["Cylinder Notes"] = currentValues["Link to Locations Openings"]["Cylinder Notes"];
    currentValues["Lock Type"] = currentValues["Link to Locations Openings"]["Lock Type"];
    currentValues["Lock Manufacturer"] = currentValues["Link to Locations Openings"]["Lock Manufacturer"];
    currentValues["Lock Notes"] = currentValues["Link to Locations Openings"]["Lock Notes"];
    currentValues["Closer"] = currentValues["Link to Locations Openings"]["Closer"];
    currentValues["Closer Manufacturer"] = currentValues["Link to Locations Openings"]["Closer Manufacturer"];
    currentValues["Closer Notes"] = currentValues["Link to Locations Openings"]["Closer Notes"];
    currentValues["Hinges"] = currentValues["Link to Locations Openings"]["Hinges"];
    currentValues["Hinge Manufacturer"] = currentValues["Link to Locations Openings"]["Hinge Manufacturer"];
    currentValues["Hinge Notes"] = currentValues["Link to Locations Openings"]["Hinge Notes"];
    currentValues["Existing Opening Notes"] = currentValues["Link to Locations Openings"]["Existing Opening Notes"];
    currentValues["Opening Notes"] = currentValues["Link to Locations Openings"]["Opening Notes"];
    currentValues["Link to Locations Openings"] = null;
}

/*______________________________________________________________________________________
|  Captures previously linked openings on the Generated System Record                  \
|______________________________________________________________________________________*/

if((previousValues["Link to Generated System"] != currentValues["Link to Generated System"]) && previousValues["Link to Generated System"] != null) {
    if(currentValues["Barcode ID"]) {
        String previousOpenings = "";
        logger.error("I'm here");
        String stringTemp = new BigDecimal(currentValues["Barcode ID"] as int).toPlainString()
        //stringTemp = currentValues["Barcode ID"] as String;
        if(previousValues["Link to Generated System"]["Previous Openings"]) {
            previousOpenings = previousValues["Link to Generated System"]["Previous Openings"] as String;
        }
        previousOpenings += ("P" + stringTemp + " ");
        logger.error(previousOpenings);
        previousValues["Link to Generated System"]["Previous Openings"] = previousOpenings;
        
    }
}

/*________________________________________________________________________________________/
/ App: Site Visits
/ Table: Margin Calculator
/ Event: Before Insert
/_____________________________________________________________________________________*/

currentValues["Adjusted Total"] = (currentValues["Quantity"] as double) * (currentValues["Adjusted Cost"] as double) * (1 + (currentValues["Adjusted Markup"] as double));
double temp = (currentValues["Quantity"] as double) * (currentValues["Adjusted Cost"] as double);
currentValues["Calculated Adjusted Margin"] = (currentValues["Adjusted Total"] as double) - temp;
currentValues["Original Total"] = (currentValues["Quantity"] as double) * (currentValues["Link to Product Database"]["AppScript MSRP"] as double);
//currentValues["Original Margin"] = (currentValues["Original Total"] as double) - ((currentValues["Link to Product Database"]["Price (Net")

/*________________________________________________________________________________________/
/ App: Site Visits
/ Table: General Bitting
/ Event: After Insert
/_____________________________________________________________________________________*/

/*______________________________________________________________________________________
|  This app script links new bitting to a facilities complex, if it exists            \
|______________________________________________________________________________________*/

// Check if parent facility has a complex
if(currentValues["Link to Location / Facility"]["Link to Complex"] != null) {
    // If it does have a complex, link the bitting to the complex
    currentValues["Link to Complex"] = currentValues["Link to Location / Facility"]["Link to Complex"];
}

addChild "All Bitting", "Link to General Bitting", ['Bitting Type' : "General",
                                                    'SKD Cuts' : currentValues["SKD Cuts (Bitting)"],
                                                    'Control Key' : currentValues["Control Key"],
                                                    'Master Key' : currentValues["Master Key"],
                                                    'Sub Master' : currentValues["SubMaster"],
                                                    'Sub Master 2' : currentValues["SubMaster 2"],
                                                    'Door' : currentValues["Door"],
                                                    'Suite' : currentValues["Suite"],
                                                    'Tech Link' : currentValues["Created By User"],
                                                    'Key Code' : currentValues["Keycode"],
                                                    'Keys' : currentValues["Keys"],
                                                    'Cylinders' : currentValues["Cylinders"],
                                                    'Link to Products - Keyway List' : currentValues["Link to Products - Keyway List"],
                                                    'Link to Location / Facility' : currentValues["Link to Location / Facility"]];
                                                    
/*________________________________________________________________________________________/
/ App: Site Visits
/ Table: Parts Request
/ Event: After Insert
/_____________________________________________________________________________________*/

/*______________________________________________________________________________________
| This app script checks parts requests table links to ensure it gets linked properly   \
| regardless of where the parts request is added from.  It also generates an "Empty"     |
| Task Builder that will inevitably hold the part that is requested.  Then it updates    |
| the grandparent ticket status, and if a site visit, number ordered.                    |
|______________________________________________________________________________________*/
                     
// Check if added from Task Builder (Bad news) - if so, link to task list
if(currentValues["Link to Task List"] == null) {
    // Create link if it does not exist
    currentValues["Link to Task List"] = currentValues["Link to Task Builder"]["Link to Task List"];
    // Shows that task builder has parts request child
    currentValues["Link to Task Builder"]["Parts Request"] = 1; 
}

// If Link to task list does exist (good news it was added from proper spot!)
if(currentValues["Link to Task List"]) {
    int qtyProd = 0;
    if(currentValues["Quantity"]) {
        qtyProd = currentValues["Quantity"] as int;
    }
    // Map "No Service" record from service table
    Map service = loadRecord "Services","Service","NO SERVICE";
    // Map "No Product" record from product database table
    Map product = loadRecord "Product Database","Common Name","NO PRODUCT";
    // Map "NO PRODUCT" record from Inventory
    Map invRec = loadRecord "Inventory","Identifier","LK00 Warehouse - NO PRODUCT";
    // Create "Empty" Task builder with no product and no service
    Map tb = save "Task Builder",['Link to Task List' : currentValues["Link to Task List"],
                     'Link to Services' : service, // no service
                     'Link to Product Database' : product, // no product
                     'Link to Inventory' : invRec, // no product INV Place holder
                     'Quantity (Product)' : qtyProd,
                     'Quantity (Service)' : 0,
                     'Parts Request' : 1] as Map; // denotes child parts request exists
    // makes the task builder just created the parent of this parts request
    currentValues["Link to Task Builder"] = tb;
    
    // If Grand Parent is Site Visit
    if(currentValues["Link to Task List"]["Link to Site Visit"]) { 
        // Change status to waiting
        currentValues["Link to Task List"]["Link to Site Visit"]["Status"] = "Waiting";
        // Change Tag to Purchasing
        currentValues["Link to Task List"]["Link to Site Visit"]["Tag"] = "Purchasing";
        // Update grandparent site visit's parts request percent ordered values
        int x = currentValues["Link to Task List"]["Link to Site Visit"]["AppScript Number Parts Request"] as int;
        currentValues["Link to Task List"]["Link to Site Visit"]["AppScript Number Parts Request"] = x + 1;
        
    // Change status of Counter Ticket if grandparent
    } else if(currentValues["Link to Task List"]["Link to Counter Ticket"] != null) { // If Grand Parent is Counter Ticket
        currentValues["Link to Task List"]["Link to Counter Ticket"]["Status"] = "Parts Ordered";
    }
}


/*________________________________________________________________________________________/
/ App: Site Visits
/ Table: Parts Request
/ Event: After Update
/_____________________________________________________________________________________*/

/*______________________________________________________________________________________
|  This app script updates the site visit when the PO is ordered.  Allowing for         \
|  Calculation of percent parts ordered                                                  |
|______________________________________________________________________________________*/

// if link to task list exists and that task has a parent site visit
if(currentValues["Link to Task List"] && currentValues["Link to Task List"]["Link to Site Visit"]) {
    // If PO was just changed to ordered
    if((currentValues["AppScript PO Ordered"] == 1) && (previousValues["AppScript PO Ordered"] != 1)) {
        // Grab number of current parts ordered, then update that field by incrementing 1
        int x = currentValues["Link to Task List"]["Link to Site Visit"]["AppScript Number PO Ordered"] as int;
        currentValues["Link to Task List"]["Link to Site Visit"]["AppScript Number PO Ordered"] = x + 1;
    }
}

/*________________________________________________________________________________________/
/ App: Site Visits
/ Table: Parts Request
/ Event: Before Insert
/_____________________________________________________________________________________*/

// Set Default Values

currentValues["AppScript PO Ordered"] = 0;

/*________________________________________________________________________________________/
/ App: Site Visits
/ Table: Product Database
/ Event: Before Insert
/_____________________________________________________________________________________*/

/*______________________________________________________________________________________
|  This AppScript sets some choice default values when a new product is added to DB     \
|______________________________________________________________________________________*/

currentValues["QBO_Account"] = "Parts/Sales";
currentValues["QBO_Type"] = "Noninventory";
currentValues["QBO_Taxable"] = "Yes";
currentValues["QBO_Expense Account"] = "Supplies - Cost of Goods Sold";
// Set default value of QBO_Ref_ID to 99999 to avoid null reference
currentValues["QBO_Ref_ID"] = "99999";
currentValues["Quantity_Reserved"] = 0;
currentValues["Quantity_On_Hand"] = 0;
currentValues["Quantity_Available"] = 0;
currentValues["Global Buffer Quantity"] = 0;
String numUp = "";
int pastVal = currentValues["Link to Products - Manufacturer Prefix"]["Next Number (Automated)"] as int;
numUp = numUp + pastVal;
currentValues["Number"] = numUp;
pastVal = pastVal + 1;

currentValues["Link to Products - Manufacturer Prefix"]["Next Number (Automated)"] = pastVal;

/*________________________________________________________________________________________/
/ App: Site Visits
/ Table: Product Database
/ Event: After Insert
/_____________________________________________________________________________________*/

/*______________________________________________________________________________________
| This App Script Calculates the cost when a product is added via MSRP.                 \
| If a product is added via Cost, it calculates MSRP.                                    |
| By Calculating these fields using app scripts, they can be referenced from line items  |
|______________________________________________________________________________________*/

// If product is added to database using the MSRP, Calculate cost and markup
if(currentValues["Cost or MSRP"] == "MSRP") {
    // Save a static copy of MSRP
    currentValues["AppScript MSRP"] = currentValues["MSRP (Input)"] as double;
    // Update Mid 2018 MSRP
    currentValues["Mid 2018 MSRP"] = currentValues["MSRP (Input)"] as double;
    // Update 2018 MSRP
    currentValues["2018 MSRP"] = currentValues["MSRP (Input)"] as double;
    // Update 2017 MSRP
    currentValues["2017 MSRP"] =  currentValues["MSRP (Input)"] as double;
    // Calculate cost using msrp and discount
    currentValues["Price (Cost)"] = (currentValues["MSRP (Input)"] as double) - ((currentValues["MSRP (Input)"] as double)*(currentValues["Discount"] as double));
    // Calculate Mark Up using algebra
    currentValues["Price (M/U)"] = ((currentValues["AppScript MSRP"] as double) / (currentValues["Price (Cost)"] as double)) - 1;
}
// If product is added to database using the cost, calculate MSRP
if(currentValues["Cost or MSRP"] == "Cost") {
    // Grab Cost
    double priceCost = currentValues["Price (Cost)"] as double;
    // Grab Markup
    double priceMarkup = currentValues["Price (M/U)"] as double;
    // Calculate MSRP and place in static container
    currentValues["AppScript MSRP"] = (priceCost + (priceCost*priceMarkup)).round(2);
    currentValues["Mid 2018 MSRP"] = (priceCost + (priceCost*priceMarkup)).round(2);
    currentValues["2017 MSRP"] = (priceCost + (priceCost*priceMarkup)).round(2);
    currentValues["2018 MSRP"] = (priceCost + (priceCost*priceMarkup)).round(2);

}

// Set a few default values
currentValues["Exclude From Tax Calculation"] = 0; // Admin field only

// Create product number from links in order to capture a static value
String appScriptItemNumber = "";
//logger.info(appScriptItemNumber.toString())

appScriptItemNumber += (currentValues["Link to Products - Loc-Doc Item Categories"]["Category ID"] as String);
//logger.info(appScriptItemNumber.toString())

appScriptItemNumber += (currentValues["Link to Products - Manufacturer Prefix"]["Manufacturer #"] as int);
//logger.info(appScriptItemNumber.toString())

appScriptItemNumber += (currentValues["Number"] as String);
//logger.info(appScriptItemNumber.toString())

// Save Product number in static container
currentValues["AppScript Item Number"] = appScriptItemNumber;

/*________________________________________________________________________________________/
/ App: Site Visits
/ Table: Purchase Order
/ Event: After Update
/_____________________________________________________________________________________*/

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

/*________________________________________________________________________________________/
/ App: Site Visits
/ Table: Purchase Order
/ Event: Before Insert
/_____________________________________________________________________________________*/

currentValues["Number of Used Labels"] = 0;

/*________________________________________________________________________________________/
/ App: Site Visits
/ Table: Quote Builder
/ Event: After Update
/_____________________________________________________________________________________*/

/*______________________________________________________________________________________
| This appscript creates a site visit upon ToSiteVisit trigger from quote.  It also     \
| Transfers the line items to the sitevisit and captures the quote number                |
|______________________________________________________________________________________*/

// Grab todays date
def date = new Date();

// Check if quote has already been converted
if((currentValues["SiteVisitGenerated"] != 1) && (currentValues["Date Quote Converted To Site Visit"] == null)) {
    // Upon trigger "ToSiteVisit" set to "Yes"
    if((currentValues["ToSiteVisit"] == "Yes") && (previousValues["ToSiteVisit"] != "Yes")) {
        // Mark that quote has already been converted
        currentValues["SiteVisitGenerated"] = 1;
        Map newSiteVisit; // Empty map for Site visit to live in
        // Create new site visit and save in Map newSiteVisit for linking purposes
        newSiteVisit = save "Site Visit",['Link to Customers' : currentValues["Link to Customers"],
                              'Link to Location / Facility' : currentValues["Link to Location / Facility"],
                              'Link to Service Call' : currentValues["Link to Service Call"],
                              'Link to Phone Numbers' : currentValues["Link to Phone Numbers"],
                              'Status' : 'Waiting to Schedule',
                              'Time Block' : 'Not Set',
                              'Pricing Year' : currentValues["Pricing Year"],
                              'Total Completion Calc' : currentValues["Total Completion Calc"],
                              'Invoiced?' : "No",
                              'Quote Completed By' : currentValues["Created By User"]] as Map;
        // Save todays date on quote, when quote was converted to site visit
        currentValues["Date Quote Converted To Site Visit"] = date;
        // Grab all the quotes line items and put them in a list
        List lineItems = getChildren("Line Items Join Table","Link to Quote Builder") as List;
        //_______delete unused line items______// Iteration step
        lineItems.each {
            // Remove if not linked to quote
            if(it["Link to Quote Builder"] == null) { 
                Map toDelete = it as Map;
                delete toDelete;
            }
            // Link to site visit if it is linked to quote
            if(it["Link to Quote Builder"] != null) {
                it["Link to Site Visit"] = newSiteVisit;
            }
        }
    }
}

/*______________________________________________________________________________________
| This AppScript Tracks when follow ups are made consecutively                          \
|______________________________________________________________________________________*/

// If a follow up has been performed
if(currentValues["Follow Up Progress"]) {
    // Grab the values from the checkboxes and put them in a String
    String progressCheckbox = currentValues["Follow Up Progress"] as String;
    // In reverse order, read how many follow ups have been performed
    if(progressCheckbox.contains("Fifth Follow Up")){
        // Then capture the date of the follow up
        currentValues["Date Fifth Follow Up"] = date;
    } else if(progressCheckbox.contains("Fourth Follow Up")) {
        currentValues["Date Fourth Follow Up"] = date;
    } else if(progressCheckbox.contains("Third Follow Up")) {
        currentValues["Date Third Follow Up"] = date;
    } else if(progressCheckbox.contains("Second Follow Up")) {
        currentValues["Date Second Follow Up"] = date;
    } else if(progressCheckbox.contains("First Follow Up")) {
        currentValues["Date First Follow Up"] = date;
    }
}

/*________________________________________________________________________________________/
/ App: Site Visits
/ Table: Quote Builder
/ Event: Before Insert
/_____________________________________________________________________________________*/

/*______________________________________________________________________________________
| This appscript set some default values.  Namely, values relevant to percent complete  \
|______________________________________________________________________________________*/

// For Percent complete fields when converted to site visit
currentValues["Total Completion Calc"] = 0;
// Track if it has been converted to a site visit or not
currentValues["SiteVisitGenerated"] = 0;

Map uniqIDRecord = loadRecord "Unique Identifier Generator","For Table","Quote Builder";
int uniqID = uniqIDRecord["UniqID"] as int;
currentValues["UniqID"] = uniqID;
uniqID = uniqID + 1;
uniqIDRecord["UniqID"] = uniqID;

/*________________________________________________________________________________________/
/ App: Site Visits
/ Table: Reoccurring Charges
/ Event: After Update
/_____________________________________________________________________________________*/

/*______________________________________________________________________________________
| This appscript updates the dates on ReOccurring Charges table based on frequency.     \
| It also creates a child record that will be passed to Zapier as a line item.           |
|______________________________________________________________________________________*/

// Grab todays date and save it
Date tempToday = new Date();
// Grab date of next ReOccurring Charge
Date tempNextDate = currentValues["Next Date"] as Date;
// Grab Expiration Date of ReOccurring Charge
Date tempExpDate = currentValues["Last Month (Expires)"] as Date;

// This Function updates the next date field to the next reoccurring charge date
def updateDate() {
    // Grab todays date
    Date today = new Date();
    // Grab Next Date
    Date nextDate = currentValues["Next Date"] as Date;
    // Create an empty calendar object
    Calendar myCal = Calendar.getInstance();
    // Set Calendar object to next dates time
    myCal.setTime(nextDate);
    // If interval is monthly
    if(currentValues["Interval"] == "M") {
        // Add one month
        myCal.add(Calendar.MONTH, +1);
    }
    // If interval is Quarterly
    if(currentValues["Interval"] == "Q") {
        // Add three months
        myCal.add(Calendar.MONTH, +3);
    }
    // If interval is Half
    if(currentValues["Interval"] == "H") {
        // Add six months
        myCal.add(Calendar.MONTH, +6);
    }
    // If interval is Yearly
    if(currentValues["Interval"] == "Y") {
        // Add one year
        myCal.add(Calendar.YEAR, +1);
    }
    // Update Next Date to this new value
    nextDate = myCal.getTime();
    currentValues["Next Date"] = nextDate;
}

// Triggered from AppScript Assistant, Initiates child creation
if((currentValues["Run"] == "Yes") && (previousValues["Run"] == null)) {
    //If date is equal to THIS MONTH - Add child _RMR (Reoccurring Monthly Charges to QBO record)
    if(currentValues["Next Date"] && (currentValues["Interval"] != "Expired")) {
        if(tempToday.format('yyyyMM') == tempNextDate.format('yyyyMM')) {
            addChild "ToQBO Reoccurring Charges", "Link to Reoccurring Charges", ['Customer ID':currentValues["Link to Billing Record"]["QBO_Ref_ID"],
                                                                                  'Line Amount':currentValues["Amount"], // ADD GET AMOUNT
                                                                                  'Billing Email':"TEMPORARY EMAIL",
                                                                                  'Line Description':"TEMPORARY DESCRIPTION",
                                                                                  'Product ID':"99999",
                                                                                  'Taxable':"NON",
                                                                                  'Quantity':1,
                                                                                  'Discount Value':currentValues["Discount Value"],
                                                                                  'Unit Price':currentValues["Amount"]];
            if(tempExpDate.format('yyyyMM') == tempToday.format('yyyyMM')) {
                currentValues["Interval"] = "Expired";
            }
            if(tempExpDate.format('yyyyMM') != tempToday.format('yyyyMM')) {
                updateDate(); // Run Function to update next date   
            }
        }
    }
    // Set "Run" to null to avoid re-runs
    currentValues["Run"] = null;
}

/*________________________________________________________________________________________/
/ App: Site Visits
/ Table: Restricted Keys
/ Event: After Insert
/_____________________________________________________________________________________*/

addChild "All Bitting", "Link to Restricted Keys", ['Bitting Type' : "Restricted",
                                                    'SKD Cuts' : currentValues["Change Key"],
                                                    'Notes' : currentValues["Notes"],
                                                    'Angles' : currentValues["Angles"],
                                                    'Control Key' : currentValues["Control Key"],
                                                    'Master Key' : currentValues["Master Key"],
                                                    'Sub Master' : currentValues["SubMaster"],
                                                    'Sub Master 2' : currentValues["SubMaster-2"],
                                                    'Door' : currentValues["Door"],
                                                    'Suite' : currentValues["Suite"],
                                                    'Tech Link' : currentValues["Created By User"],
                                                    'Key Code' : currentValues["Key Code (Stamp)"],
                                                    'Keys' : currentValues["Keys"],
                                                    'Cylinders' : currentValues["Cylinders"],
                                                    'Link to Products - Keyway List' : currentValues["Link to Products - Keyway List"],
                                                    'Link to Location / Facility' : currentValues["Link to Location / Facility"]];

/*________________________________________________________________________________________/
/ App: Site Visits
/ Table: Site Visit
/ Event: Before Insert
/_____________________________________________________________________________________*/

/*______________________________________________________________________________________
| This appscript sets some default values                                               \
|______________________________________________________________________________________*/

// Total Completion Calc already holds a value, don't set to 0
if(currentValues["Total Completion Calc"]) {
    
} else {
    currentValues["Total Completion Calc"] = 0;
}

currentValues["Current Completed"] = 0;
currentValues["Invoiced?"] = "No";
currentValues["AppScript Number Parts Request"] = 0;
currentValues["AppScript Number PO Ordered"] = 0;
currentValues["Apply Deposit"] = 0;
currentValues["Tech Status"] = "Offsite";
currentValues["Times OnSite"] = 0;
currentValues["Total Time Onsite"] = 0;

if(currentValues["Link to Customers"]) {
    if(currentValues["Link to Customers"]["Customer Type"] == "National Service Provider") {
        currentValues["NSP Requirements"] = currentValues["Link to Customers"]["NSP Requirements"];    
    }
    
}

if(currentValues["Link to Location / Facility"]) {
    if(currentValues["Link to Location / Facility"]["Facility NTE"]) {
        currentValues["NTE"] = currentValues["Link to Location / Facility"]["Facility NTE"];
    }
}

/*________________________________________________________________________________________/
/ App: Site Visits
/ Table: Site Visit
/ Event: After Update
/_____________________________________________________________________________________*/

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
    sellAllInventory();
    
    
    
    
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
    
if(currentValues["Link to Service Call"]["Service Name"] == "Warranty Work") {
    List childRecords = getChildren("Task List", "Link to Site Visit") as List;
    childRecords.each {
        it["Warranty Site Visit"] = 1;
    }
}    
    
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
    Date today = new Date();
    Date updatedDates = new Date();
    Calendar myCal = Calendar.getInstance();
    myCal.setTime(today);
    myCal.next();
    updatedDates = myCal.getTime();
    currentValues["Date"] = updatedDates;


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
                if(currentValues["Pricing Year"] == "Mid 2018") {
                    it["Cost"] = it["Link to Task Builder"]["Link to Product Database"]["Mid 2018 MSRP"];
                }
                if(currentValues["Pricing Year"] == "2018") {
                    it["Cost"] = it["Link to Task Builder"]["Link to Product Database"]["2018 MSRP"];
                }
                if(currentValues["Pricing Year"] == "2017") {
                    it["Cost"] = it["Link to Task Builder"]["Link to Product Database"]["2017 MSRP"];
                    //logger.error("I'm here!");
                }
            }
            if((currentValues["Pricing Year"] != "Use Latest") && (it["Type"] == "Service") && (it["Special Pricing"] != "Yes")) {
                //logger.error("I'm here!");
                if(currentValues["Pricing Year"] == "Mid 2018") {
                    it["Cost"] = it["Link to Task Builder"]["Link to Services"]["Mid 2018 MSRP"];
                }
                if(currentValues["Pricing Year"] == "2018") {
                    it["Cost"] = it["Link to Task Builder"]["Link to Services"]["2018 MSRP"];
                }
                if(currentValues["Pricing Year"] == "2017") {
                    it["Cost"] = it["Link to Task Builder"]["Link to Services"]["2017 MSRP"];
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

/*________________________________________________________________________________________/
/ App: Site Visits
/ Table: Site Visit
/ Event: After Insert
/_____________________________________________________________________________________*/

// After creating a site visit - add the service charge with line items total already calculated

if(currentValues["Link to Service Call"] != null) { // As long as it isn't no charge
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

/*________________________________________________________________________________________/
/ App: Site Visits
/ Table: Site Visit
/ Event: Before Update
/_____________________________________________________________________________________*/

//
// Capture date time of tech arriving onsite
if((previousValues["Tech Status"] == "Offsite") && (currentValues["Tech Status"] == "Onsite") && ((currentValues["Times OnSite"] as int) > 0)) {
    String minutesOnSite = currentValues["Minutes Onsite"] as String;
    int i = currentValues["Total Time Onsite"] as int;
    i = i + (currentValues["Minutes Onsite"] as int);
    currentValues["Total Time Onsite"] = i;
    String auditTrail = currentValues["Audit Trail"] as String;
    auditTrail += ("\n" + "Trip " + (currentValues["Times OnSite"] as int) + " Time OnSite: " + minutesOnSite + " Minutes");
    currentValues["Audit Trail"] = auditTrail;
    
}

if((previousValues["Tech Status"] == "Onsite") && (currentValues["Tech Status"] == "Offsite")) {
    if(currentValues["Current Completed"] == currentValues["Total Completion Calc"]) {
        currentValues["Status"] = "Completed";
    }
}

if((previousValues["Payment Type"] != "Pending Payment") && (currentValues["Payment Type"] == "Pending Payment")) {
    currentValues["Pending Payment User"] = currentValues["Last User"];
}

if((previousValues["Delete ADMIN"] == null) && (currentValues["Delete ADMIN"] == "YES")) {
    logger.error("Im here");
    List lineItems = getChildren("Line Items Join Table", "Link to Site Visit") as List;
    lineItems.each { delete(it as Map); 
                     logger.error("I'm here 1");
    }
}

/*________________________________________________________________________________________/
/ App: Site Visits
/ Table: Task Builder
/ Event: After Insert
/_____________________________________________________________________________________*/

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
    logger.error(pricingYear);
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
        if(getPricingYear() == "Mid 2018") {
            tempcost = (currentValues["Link to Product Database"]["Mid 2018 MSRP"]) as double;
        }
        if(getPricingYear() == "Use Latest") {
            tempcost = (currentValues["Link to Product Database"]["AppScript MSRP"]) as double;
        }
        logger.error(tempcost.toString());
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
    //if(currentValues["Testing"] == "Yes") {
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

    //}
    
}



// As Long as a task builder service is not "No Service", add that line item
if(currentValues["Link to Services"]["Service"] != "NO SERVICE") {
    // Add Service Line Item using linked parent values
    // See comments on product child creation for explanations of values
    double tempServCost = 0.0;
    if(getPricingYear() == "2017") {
        tempServCost = (currentValues["Link to Services"]["2017 MSRP"]) as double;
    }
    if(getPricingYear() == "2018") {
        tempServCost = (currentValues["Link to Services"]["2018 MSRP"]) as double;
    }
    if(getPricingYear() == "Mid 2018") {
        tempServCost = (currentValues["Link to Services"]["Mid 2018 MSRP"]) as double;
    }
    if(getPricingYear() == "Use Latest") {
        tempServCost = (currentValues["Link to Services"]["AppScript Amount"]) as double;
    }
    
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
                                  'Cost' : tempServCost];
}


//________________Parts Tracking Code_________________DEPRECATED WILL BE REPLACED BY INVENTORY MANAGMENT / LINE ITEMS..._________/

// Early "Parts Tracking" Attempt - Soon to be trash.
if(currentValues["Link to Product Database"]) {
    save "Products Sold",['Link to Product Database' : currentValues["Link to Product Database"],
                              'Quantity Used' : currentValues["Quantity (Product)"],
                              'Who' : currentValues["Last User"]];
}
}

/*________________________________________________________________________________________/
/ App: Site Visits
/ Table: Task Builder
/ Event: After Update
/_____________________________________________________________________________________*/

/*__________________________________________________________________________________________________
| This App Script does several things: 1) Detects when parts have been switched out that were       \
| added by the purchasing manager. 2) Records Date of Quote to Site Visit Conversion, 3) Updates     |
| Site Visit when parts are ordered                                                                  |
|__________________________________________________________________________________________________*/

/*if(currentValues["Testing"] == "Yes") {
    logger.error("i'm here after update");
    logger.error(currentValues["Overide"].toString());
}*/

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
        if(getPricingYear() == "Mid 2018") {
            tempcost = (currentValues["Link to Product Database"]["Mid 2018 MSRP"]) as double;
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
    if((currentValues["Link to Inventory - Sub Location"]) && (currentValues["Already Ran"] != 1) && (currentValues["Link to Task List"]["Parent Type"] != 2)) {
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
    if((currentValues["Already Ran"] == 1)  && (currentValues["Link to Task List"]["Parent Type"] != 2) && (currentValues["Link to Inventory - Sub Location"])) {
        // Then Map the new Inventory record that was created during Inventory Manager After Insert after first iteration of DNE script error from this script
        currentValues["Link to Inventory"] = productExistsAtInventoryLocation(getInventoryLocationIdentifier(currentValues["Link to Product Database"] as Map, currentValues["Link to Inventory - Sub Location"] as Map) as String);
        currentValues["Already Ran"] = 0; // Reset already ran to 0
    }
}

//2) If Current Service changes to a different service, update child Line Item - Service
if(servCode == 3) {
    double tempServCost = 0.0;
    if(getPricingYear() == "2017") {
        tempServCost = (currentValues["Link to Services"]["2017 MSRP"]) as double;
    }
    if(getPricingYear() == "2018") {
        tempServCost = (currentValues["Link to Services"]["2018 MSRP"]) as double;
    }
    if(getPricingYear() == "Mid 2018") {
        tempServCost = (currentValues["Link to Services"]["Mid 2018 MSRP"]) as double;
    }
    if(getPricingYear() == "Use Latest") {
        tempServCost = (currentValues["Link to Services"]["AppScript Amount"]) as double;
    }
    List _lineItems = getChildren("Line Items Join Table", "Link to Task Builder") as List;
    _lineItems.each {
        if(it["Type"] == "Service") {
            it["Identifier"] = currentValues["Link to Services"]["AppScript Item Number"];
            it["Description"] = currentValues["Link to Services"]["Service"];
            it["QBO_ID"] = currentValues["Link to Services"]["QBO_Ref_ID"];
            it["Cost"] = tempServCost;
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
    if((prodCode == 0) && /*(currentValues["Testing"] == "Yes") &&*/ (currentValues["Link to Task List"]["Parent Type"] != 2)) {
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
        if(getPricingYear() == "Mid 2018") {
            tempcost = (currentValues["Link to Product Database"]["Mid 2018 MSRP"]) as double;
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
    if(/*(currentValues["Testing"] == "Yes") && */(currentValues["Already Ran"] != 1) && (currentValues["Link to Task List"]["Parent Type"] != 2)) {
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
    double tempServCost = 0.0;
    if(getPricingYear() == "2017") {
        tempServCost = (currentValues["Link to Services"]["2017 MSRP"]) as double;
    }
    if(getPricingYear() == "2018") {
        tempServCost = (currentValues["Link to Services"]["2018 MSRP"]) as double;
    }
    if(getPricingYear() == "Mid 2018") {
        tempServCost = (currentValues["Link to Services"]["Mid 2018 MSRP"]) as double;
    }
    if(getPricingYear() == "Use Latest") {
        tempServCost = (currentValues["Link to Services"]["AppScript Amount"]) as double;
    }
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
                                  'Cost' : tempServCost];
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
    if(/*(currentValues["Testing"] == "Yes") && */(currentValues["Already Ran"] != 1) && (currentValues["Link to Task List"]["Parent Type"] != 2)) {
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
            if(getPricingYear() == "Mid 2018") {
                tempcost = (currentValues["Link to Product Database"]["Mid 2018 MSRP"]) as double;
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
    if(/*(currentValues["Testing"] == "Yes") &&*/ (currentValues["Already Ran"] != 1) && (currentValues["Link to Task List"]["Parent Type"] != 2)) {
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
    if(/*(currentValues["Testing"] == "Yes") &&*/ (currentValues["Already Ran"] != 1) && (currentValues["Link to Task List"]["Parent Type"] != 2)) {
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
        if(getPricingYear() == "Mid 2018") {
            tempcost = (currentValues["Link to Product Database"]["Mid 2018 MSRP"]) as double;
        }
        if(getPricingYear() == "Use Latest") {
            tempcost = (currentValues["Link to Product Database"]["AppScript MSRP"]) as double;
        }
    }
    if((currentValues["No Charge"] == "For Product") || (currentValues["No Charge"] == "Both")) {
        tempcost = 0
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


double tempServCost = 0.0;
if(getPricingYear() == "2017") {
    tempServCost = (currentValues["Link to Services"]["2017 MSRP"]) as double;
}
if(getPricingYear() == "2018") {
    tempServCost = (currentValues["Link to Services"]["2018 MSRP"]) as double;
}
if(getPricingYear() == "Mid 2018") {
    tempServCost = (currentValues["Link to Services"]["Mid 2018 MSRP"]) as double;
}
if(getPricingYear() == "Use Latest") {
    tempServCost = (currentValues["Link to Services"]["AppScript Amount"]) as double;
}
if((currentValues["No Charge"] == "For Service") || (currentValues["No Charge"] == "Both")) {
        tempServCost = 0;
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
                                  'Cost' : tempServCost,
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
if((currentValues["Already Ran"] == 1)  && (currentValues["Link to Task List"]["Parent Type"] != 2) && (currentValues["From Quote"] == 2) /*&& (currentValues["Testing"] == "Yes")*/) {
    // Then Map the new Inventory record that was created during Inventory Manager After Insert after first iteration of DNE script error from this script
    logger.error("I'm here at the bottom of this crappy code block!");
    currentValues["Link to Inventory"] = productExistsAtInventoryLocation(getInventoryLocationIdentifier(currentValues["Link to Product Database"] as Map, currentValues["Link to Inventory - Sub Location"] as Map) as String);
    currentValues["Already Ran"] = 0; // Reset already ran to 0
    currentValues["From Quote"] = 1;
}

// If this is the second time the script is running
if((currentValues["Already Ran"] == 1)  && (currentValues["Link to Task List"]["Parent Type"] != 2) && /*(currentValues["Testing"] == "Yes") && */(currentValues["Order NIS"] == "Yes")) {
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
if((previousValues["PO Status"] != "Ordered") && (currentValues["PO Status"] == "Ordered") && (currentValues["Link to Inventory"])) {
    currentValues["Link to Inventory - Sub Location"] = currentValues["Link to Inventory"]["Link to Inventory - Sub Location"];
}

// If a PO is closed (a.k.a.) arrived, update inventory records.  This setup also supports partial arrival by triggering on the TB
if((previousValues["PO Status"] != "Closed") && (currentValues["PO Status"] == "Closed") && (currentValues["Link to Inventory"])) {
    currentValues["Link to Inventory - Sub Location"] = currentValues["Link to Inventory"]["Link to Inventory - Sub Location"];

}

// TEMP TEMP TEMP TEMP

if((currentValues["Already Ran"] == 1)  && (currentValues["Transfer Sub-Location"] == "YES")) {
        // Then Map the new Inventory record that was created during Inventory Manager After Insert after first iteration of DNE script error from this script
        currentValues["Link to Inventory"] = productExistsAtInventoryLocation(getInventoryLocationIdentifier(currentValues["Link to Product Database"] as Map, currentValues["Link to Inventory - Sub Location"] as Map) as String);
        currentValues["Already Ran"] = 0; // Reset already ran to 0
        currentValues["Transfer Sub-Location"] = null;
}


if(currentValues["No Charge"] == "For Product") {
    List _lineItems = getChildren("Line Items Join Table", "Link to Task Builder") as List;
    _lineItems.each {
        if(it["Type"] == "Product") {
            it["Cost"] = 0;
            it["Total Cost"] = 0;
            it["Tax"] = 0;
            it["Total With Tax"] = 0;
        }
    }
}

if(currentValues["No Charge"] == "For Service") {
    List _lineItems = getChildren("Line Items Join Table", "Link to Task Builder") as List;
    _lineItems.each {
        if(it["Type"] == "Service") {
            it["Cost"] = 0;
            it["Total Cost"] = 0;
            it["Tax"] = 0;
            it["Total With Tax"] = 0;
        }
    }
}

if(currentValues["No Charge"] == "Both") {
    List _lineItems = getChildren("Line Items Join Table", "Link to Task Builder") as List;
    _lineItems.each {
        it["Cost"] = 0;
        it["Total Cost"] = 0;
        it["Tax"] = 0;
        it["Total With Tax"] = 0;
        
    }
}

/*________________________________________________________________________________________/
/ App: Site Visits
/ Table: Task Builder
/ Event: Before Insert
/_____________________________________________________________________________________*/

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
    if(/*(currentValues["Testing"] == "Yes") &&*/ (currentValues["Link to Task List"]["Parent Type"] != 2)) {
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

/*________________________________________________________________________________________/
/ App: Site Visits
/ Table: Task Builder
/ Event: Before Update
/_____________________________________________________________________________________*/

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
    if(/*(currentValues["Testing"] == "Yes") && */(currentValues["Link to Task List"]["Parent Type"] != 2)) {
        // Ensure it is not either of the two special cases (NIS or DNE)
        if((currentValues["Overide"] == null) && (currentValues["Order NIS"] == null)) {
            logger.error("i'm here before update - overide is no");
    
            // If Inventory Record is selected, grab the product database record and sub location and move on!
            if(currentValues["Link to Inventory"]) {
                currentValues["Link to Product Database"] = currentValues["Link to Inventory"]["Link to Product Database"];
                if(currentValues["Transfer Sub-Location"] == null) {
                    currentValues["Link to Inventory - Sub Location"] = currentValues["Link to Inventory"]["Link to Inventory - Sub Location"];
                }
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
            
            
            //TEMP TEMP TEMP TEMP TEMP TEMP TEMP TEMP TEMP TEMP TEMP TEMP TEMP TEMP TEMP TEMP TEMP TEMP 
            /*
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
            */
            //TEMP TEMP TEMP TEMP TEMP TEMP TEMP TEMP TEMP TEMP TEMP TEMP TEMP TEMP TEMP TEMP TEMP TEMP 
            
            
            
            
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
if((previousValues["Grand Parent Type"] == 2) && (currentValues["Grand Parent Type"] != 2) /*&& (currentValues["Testing"] == "Yes")*/) {
    // Map Sam as user in order to pull inventory from Warehouse
    logger.error("I'm here before update - quote to site visit");
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
/ Transfer Selected Parts and qty's to new sub-location |
/______________________________________________________*/
if(currentValues["Transfer Sub-Location"] == "YES") {
    addChild "Inventory Manager","Link to Task Builder",['Link to Inventory - Sub Location' : currentValues["Link to Inventory"]["Link to Inventory - Sub Location"],
                                                             'Link to Product Database' : currentValues["Link to Product Database"],
                                                             'Quantity' : currentValues["Quantity (Product)"],
                                                             'Arguments' : "Sell"];
    addChild "Inventory Manager","Link to Task Builder",['Link to Inventory - Sub Location' : currentValues["Link to Inventory - Sub Location"],
                                                             'Link to Product Database' : currentValues["Link to Product Database"],
                                                             'Quantity' : currentValues["Quantity (Product)"],
                                                             'Arguments' : "Add"];
    addChild "Inventory Manager","Link to Task Builder",['Link to Inventory - Sub Location' : currentValues["Link to Inventory - Sub Location"],
                                                             'Link to Product Database' : currentValues["Link to Product Database"],
                                                             'Quantity' : currentValues["Quantity (Product)"],
                                                             'Arguments' : "Reserve"];
    //currentValues["Transfer Sub-Location"] = null;
    currentValues["Already Ran"] = 1;
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
if((previousValues["PO Status"] != "Ordered") && (currentValues["PO Status"] == "Ordered") /*&& (currentValues["Testing"] == "Yes")*/) {
    if(currentValues["Link to Inventory"]) {
        addChild "Inventory Manager","Link to Task Builder",['Link to Inventory - Sub Location' : currentValues["Link to Inventory"]["Link to Inventory - Sub Location"],
                                                             'Link to Product Database' : currentValues["Link to Product Database"],
                                                             'Quantity' : currentValues["Quantity (Product)"],
                                                             'Arguments' : "Order",
                                                             'UniqID' : currentValues["Link to Inventory"]["UniqID"]];
    }
}

// If a PO is closed (a.k.a.) arrived, update inventory records.  This setup also supports partial arrival by triggering on the TB
if((previousValues["PO Status"] != "Closed") && (currentValues["PO Status"] == "Closed") /*&& (currentValues["Testing"] == "Yes")*/) {
    //logger.error(currentValues["Link to Inventory"]["Link to Inventory - Sub Location"].toString());
    if(currentValues["Link to Inventory"]) {
        addChild "Inventory Manager","Link to Task Builder",['Link to Inventory - Sub Location' : currentValues["Link to Inventory - Sub Location"],
                                                             'Link to Product Database' : currentValues["Link to Product Database"],
                                                             'Quantity' : currentValues["Quantity (Product)"],
                                                             'Arguments' : "Arrived",
                                                             'UniqID' : currentValues["Link to Inventory"]["UniqID"]];
    }
}

/*________________________________________________________________________________________/
/ App: Site Visits
/ Table: Task Builder
/ Event: Before Delete
/_____________________________________________________________________________________*/

//
if(currentValues["Link to Inventory"]) {
    save "Inventory Manager",['Link to Inventory - Sub Location' : currentValues["Link to Inventory"]["Link to Inventory - Sub Location"],
                                                             'Link to Product Database' : currentValues["Link to Product Database"],
                                                             'Quantity' : currentValues["Quantity (Product)"],
                                                             'Arguments' : "Cancel",
                                                             /*'UniqID' : currentValues["Link to Inventory"]["UniqID"]*/];
}

/*________________________________________________________________________________________/
/ App: Site Visits
/ Table: Task Builder Documents
/ Event: After Insert
/_____________________________________________________________________________________*/

// if linked to a task, then much linking must be performed!!!!
if(currentValues["Link to Task List"]) {
    
    // if task has parent site visit, then link to site visit as well as site visits customer and location
    if(currentValues["Link to Task List"]["Link to Site Visit"]) {
        currentValues["Link to Site Visit"] = currentValues["Link to Task List"]["Link to Site Visit"];
        currentValues["Link to Location / Facility"] = currentValues["Link to Task List"]["Link to Site Visit"]["Link to Location / Facility"];
        currentValues["Link to Customers"] = currentValues["Link to Task List"]["Link to Site Visit"]["Link to Customers"];
    }
    
    // if task has parent quote, same as above.
    if(currentValues["Link to Task List"]["Link to Quote Builder"]) {
        currentValues["Link to Quote Builder"] = currentValues["Link to Task List"]["Link to Quote Builder"];
        currentValues["Link to Location / Facility"] = currentValues["Link to Task List"]["Link to Quote Builder"]["Link to Location / Facility"];
        currentValues["Link to Customers"] = currentValues["Link to Task List"]["Link to Quote Builder"]["Link to Customers"];
    }
    
    // if task has parent counter ticket, then same as above.
    if(currentValues["Link to Task List"]["Link to Counter Ticket"]) {
        currentValues["Link to Counter Ticket"] = currentValues["Link to Task List"]["Link to Counter Ticket"];
        currentValues["Link to Location / Facility"] = currentValues["Link to Task List"]["Link to Counter Ticket"]["Link to Location / Facility"];
        currentValues["Link to Customers"] = currentValues["Link to Task List"]["Link to Counter Ticket"]["Link to Customers"];
    }
}

// if linked to a location, then also link it to the location's customer
if(currentValues["Link to Location / Facility"]) {
    currentValues["Link to Customers"] = currentValues["Link to Location / Facility"]["Link to Customers"];
}

// if linked to a site visit, then link it to the site visits location and customer
if(currentValues["Link to Site Visit"]) {
    currentValues["Link to Customers"] = currentValues["Link to Site Visit"]["Link to Customers"];
    currentValues["Link to Location / Facility"] = currentValues["Link to Site Visit"]["Link to Location / Facility"];
}

// if linked to a quote, then also link it to the quotes location and customer
if(currentValues["Link to Quote Builder"]) {
    currentValues["Link to Customers"] = currentValues["Link to Quote Builder"]["Link to Customers"];
    currentValues["Link to Location / Facility"] = currentValues["Link to Quote Builder"]["Link to Location / Facility"];
}

// if linked to a counter ticket, then also link it to the counter tickets customer and location / facility
if(currentValues["Link to Counter Ticket"]) {
    currentValues["Link to Customers"] = currentValues["Link to Counter Ticket"]["Link to Customers"];
    currentValues["Link to Location / Facility"] = currentValues["Link to Counter Ticket"]["Link to Location / Facility"];
}

/*________________________________________________________________________________________/
/ App: Site Visits
/ Table: Task Builder Pictures
/ Event: After Insert
/_____________________________________________________________________________________*/

// if linked to a task, then much linking must be performed!!!!
if(currentValues["Link to Task List"]) {
    
    // if task has parent site visit, then link to site visit as well as site visits customer and location
    if(currentValues["Link to Task List"]["Link to Site Visit"]) {
        currentValues["Link to Site Visit"] = currentValues["Link to Task List"]["Link to Site Visit"];
        currentValues["Link to Location / Facility"] = currentValues["Link to Task List"]["Link to Site Visit"]["Link to Location / Facility"];
        currentValues["Link to Customers"] = currentValues["Link to Task List"]["Link to Site Visit"]["Link to Customers"];
    }
    
    // if task has parent quote, same as above.
    if(currentValues["Link to Task List"]["Link to Quote Builder"]) {
        currentValues["Link to Quote Builder"] = currentValues["Link to Task List"]["Link to Quote Builder"];
        currentValues["Link to Location / Facility"] = currentValues["Link to Task List"]["Link to Quote Builder"]["Link to Location / Facility"];
        currentValues["Link to Customers"] = currentValues["Link to Task List"]["Link to Quote Builder"]["Link to Customers"];
    }
    
    // if task has parent counter ticket, then same as above.
    if(currentValues["Link to Task List"]["Link to Counter Ticket"]) {
        currentValues["Link to Counter Ticket"] = currentValues["Link to Task List"]["Link to Counter Ticket"];
        currentValues["Link to Location / Facility"] = currentValues["Link to Task List"]["Link to Counter Ticket"]["Link to Location / Facility"];
        currentValues["Link to Customers"] = currentValues["Link to Task List"]["Link to Counter Ticket"]["Link to Customers"];
    }
}

// if linked to a location, then also link it to the location's customer
if(currentValues["Link to Location / Facility"]) {
    currentValues["Link to Customers"] = currentValues["Link to Location / Facility"]["Link to Customers"];
}

// if linked to a site visit, then link it to the site visits location and customer
if(currentValues["Link to Site Visit"]) {
    currentValues["Link to Customers"] = currentValues["Link to Site Visit"]["Link to Customers"];
    currentValues["Link to Location / Facility"] = currentValues["Link to Site Visit"]["Link to Location / Facility"];
}

// if linked to a quote, then also link it to the quotes location and customer
if(currentValues["Link to Quote Builder"]) {
    currentValues["Link to Customers"] = currentValues["Link to Quote Builder"]["Link to Customers"];
    currentValues["Link to Location / Facility"] = currentValues["Link to Quote Builder"]["Link to Location / Facility"];
}

// if linked to a counter ticket, then also link it to the counter tickets customer and location / facility
if(currentValues["Link to Counter Ticket"]) {
    currentValues["Link to Customers"] = currentValues["Link to Counter Ticket"]["Link to Customers"];
    currentValues["Link to Location / Facility"] = currentValues["Link to Counter Ticket"]["Link to Location / Facility"];
}

/*________________________________________________________________________________________/
/ App: Site Visits
/ Table: Task List
/ Event: After Insert
/_____________________________________________________________________________________*/

/*___________________________________________________________________________________________________|
| This app-script simply adds 3 (the integer associated with a completed task) to the parent ticket. |
| The parent can be a site visit, a counter ticket, or a project                                     |
| By totaling all the tasks added to a ticket, we get a total completion integer to work towards.    |
|__________________________________________________________________________________________________*/



//Place holder value for completed double.
double completed = 0;

//Check to see if this is a site visit or not
if(currentValues["Link to Site Visit"]) {
    //Calculate current total completion
    completed = currentValues["Link to Site Visit"]["Total Completion Calc"] as double;
    //Upon adding new task, add 3 to total completion.
    currentValues["Link to Site Visit"]["Total Completion Calc"] = completed + 3; // for site visit
    currentValues["Parent Type"] = 0;
    if(currentValues["Link to Site Visit"]["Link to Service Call"]["Service Name"] == "Warranty Work") {
        currentValues["Warranty Site Visit"] = 1;
    }
    
//Check to see if this is a counter visit or not
} else if(currentValues["Link to Counter Ticket"]) {
    completed = currentValues["Link to Counter Ticket"]["Total Completion Calc"] as double;
    currentValues["Link to Counter Ticket"]["Total Completion Calc"] = completed + 3; // for counter ticket
    currentValues["Parent Type"] = 1;
    
//Check to see if this is a Quote or not
} else if(currentValues["Link to Quote Builder"]) {
    completed = currentValues["Link to Quote Builder"]["Total Completion Calc"] as double;
    currentValues["Link to Quote Builder"]["Total Completion Calc"] = completed + 3;
    currentValues["Parent Type"] = 2;
}

/*___________________________________________________________________________________________________|
| Total opening overhaul update 2.0!  Moved several opening fields to the task.  Want to hold them   |
| both places, the task and opening.  If its a new opening, add the new fields to opening. If its    |
| an existing opening, add existing opening fields to the task, then display them! - lovely          |
|__________________________________________________________________________________________________*/

// If upon creation of a task the user selects to create a new opening, run this script after saving.
if((currentValues["New/Existing Opening"] == "New") && (currentValues["Parent Type"] == 0)) {
    currentValues["Link to Locations Openings"]["Opening Name"] = currentValues["Opening Name"];
    currentValues["Link to Locations Openings"]["Floor Number"] = currentValues["Floor Number"];
    currentValues["Link to Locations Openings"]["Suite #"] = currentValues["Suite Number"];
    
    if(currentValues["Cylinder Type"] == null) {
        if(currentValues["Link to Site Visit"]["Link to Location / Facility"]) {
            currentValues["Link to Locations Openings"]["Cylinder Type"] = currentValues["Link to Site Visit"]["Link to Location / Facility"]["Default Cylinder Type"];
            currentValues["Cylinder Type"] = currentValues["Link to Site Visit"]["Link to Location / Facility"]["Default Cylinder Type"];
        }
    }
    if(currentValues["Cylinder Type"] != null) {
        currentValues["Link to Locations Openings"]["Cylinder Type"] = currentValues["Cylinder Type"];
    }
    
    if(currentValues["Lock Type"] == null) {
        if(currentValues["Link to Site Visit"]["Link to Location / Facility"]) {
            currentValues["Link to Locations Openings"]["Lock Type"] = currentValues["Link to Site Visit"]["Link to Location / Facility"]["Default Lock Type"];
            currentValues["Lock Type"] = currentValues["Link to Site Visit"]["Link to Location / Facility"]["Default Lock Type"];
        }
    }
    if(currentValues["Lock Type"] != null) {
        currentValues["Link to Locations Openings"]["Lock Type"] = currentValues["Lock Type"];
    }
    
    if(currentValues["Lock Manufacturer"] == null) {
        if(currentValues["Link to Site Visit"]["Link to Location / Facility"]) {
            currentValues["Link to Locations Openings"]["Lock Manufacturer"] = currentValues["Link to Site Visit"]["Link to Location / Facility"]["Default Lock Manufacturer"];
            currentValues["Lock Manufacturer"] = currentValues["Link to Site Visit"]["Link to Location / Facility"]["Default Lock Manufacturer"];
        }
    }
    if(currentValues["Lock Manufacturer"] != null) {
        currentValues["Link to Locations Openings"]["Lock Manufacturer"] = currentValues["Lock Manufacturer"];
    }
    
    if(currentValues["Parent Type"] == 0) {
        currentValues["Link to Locations Openings"]["Link to Location / Facility"] = currentValues["Link to Site Visit"]["Link to Location / Facility"];
    }
    if(currentValues["Parent Type"] == 2) {
        currentValues["Link to Locations Openings"]["Link to Location / Facility"] = currentValues["Link to Quote Builder"]["Link to Location / Facility"];
    }
}

// If upon creation of a task the user selects to use an existing opening, run this script!
if((currentValues["New/Existing Opening"] == "Existing") && (currentValues["Parent Type"] == 0)) {
    currentValues["Opening Name"] = currentValues["Link to Locations Openings"]["Opening Name"];
    currentValues["Floor Number"] = currentValues["Link to Locations Openings"]["Floor Number"];
    currentValues["Suite Number"] = currentValues["Link to Locations Openings"]["Suite #"];
    currentValues["Cylinder Type"] = currentValues["Link to Locations Openings"]["Cylinder Type"];
    currentValues["Barcode ID"] = currentValues["Link to Locations Openings"]["Barcode ID"];
    currentValues["AppScript_Use_Existing_Opening"] = 1;
}

// If upon creation of a task the user selects to create new general bitting, run this script!
if((currentValues["New/Existing Bitting"] == "New") && (currentValues["Bitting Type"] == "General Bitting") && (currentValues["Add Type"] == "Manual")) {
    Map newBitting = null;
    newBitting = save "General Bitting",['Link to Complex' : currentValues["Link to Site Visit"]["Link to Location / Facility"]["Link to Complex"],
                                         'Link to Location / Facility' : currentValues["Link to Site Visit"]["Link to Location / Facility"],
                                         'Link to Products - Keyway List' : currentValues["Link to Products - Keyway List"],
                                         'SKD Cuts (Bitting)' : currentValues["SKD Cuts"],
                                         'Control Key' : currentValues["Control"],
                                         'Master Key' : currentValues["Master"],
                                         'SubMaster' : currentValues["SubMaster"],
                                         'Keycode' : currentValues["Key Code"],
                                         'Suite' : currentValues["Suite Number"]];
    currentValues["Link to Locations Openings"]["Link to General Bitting"] = newBitting;
                            
}

// If upon creation of a task the user selects to create new general bitting using random 5 pin, run this script!
if((currentValues["New/Existing Bitting"] == "New") && (currentValues["Bitting Type"] == "General Bitting") && (currentValues["Add Type"] == "Choose Available - 5 Pin")) {
    Map newBitting = null;
    currentValues["SKD Cuts"] = currentValues["Link to Available 5 Pin Bitting"]["SKD Cuts"];
    newBitting = save "General Bitting",['Link to Complex' : currentValues["Link to Site Visit"]["Link to Location / Facility"]["Link to Complex"],
                                         'Link to Location / Facility' : currentValues["Link to Site Visit"]["Link to Location / Facility"],
                                         'Link to Products - Keyway List' : currentValues["Link to Products - Keyway List"],
                                         'SKD Cuts (Bitting)' : currentValues["SKD Cuts"],
                                         'Control Key' : currentValues["Control"],
                                         'Master Key' : currentValues["Master"],
                                         'SubMaster' : currentValues["SubMaster"],
                                         'Keycode' : currentValues["Key Code"],
                                         'Suite' : currentValues["Suite Number"]];
    currentValues["Link to Locations Openings"]["Link to General Bitting"] = newBitting;
}

// If upon creation of a task the user selects to create new general bitting using random 6 pin, run this script!
if((currentValues["New/Existing Bitting"] == "New") && (currentValues["Bitting Type"] == "General Bitting") && (currentValues["Add Type"] == "Choose Available - 6 Pin")) {
    Map newBitting = null;
    currentValues["SKD Cuts"] = currentValues["Link to Available 6 Pin Bitting"]["SKD Cuts"];
    newBitting = save "General Bitting",['Link to Complex' : currentValues["Link to Site Visit"]["Link to Location / Facility"]["Link to Complex"],
                                         'Link to Location / Facility' : currentValues["Link to Site Visit"]["Link to Location / Facility"],
                                         'Link to Products - Keyway List' : currentValues["Link to Products - Keyway List"],
                                         'SKD Cuts (Bitting)' : currentValues["SKD Cuts"],
                                         'Control Key' : currentValues["Control"],
                                         'Master Key' : currentValues["Master"],
                                         'SubMaster' : currentValues["SubMaster"],
                                         'Keycode' : currentValues["Key Code"],
                                         'Suite' : currentValues["Suite Number"]];
    currentValues["Link to Locations Openings"]["Link to General Bitting"] = newBitting;
                            
}

// If upon creation of a task the user selects to create new combination Lock, run this script!
if((currentValues["New/Existing Bitting"] == "New") && (currentValues["Bitting Type"] == "Combination Lock")) {
    Map newCombo = null;
    newCombo = save "Combination Locks",['Link to Location / Facility' : currentValues["Link to Site Visit"]["Link to Location / Facility"],
                                        'User Combination' : currentValues["User Combination"],
                                        'Programming Combination' : currentValues["Programming Combination"]];
    currentValues["Link to Locations Openings"]["Link to Combination Locks"] = newCombo;
}

// If upon creation of a task the user selects to use existing bitting (in this scope it will show all bitting because the opening is not linked to parent location)
if(currentValues["Link to Locations Openings"] && currentValues["Link to General Bitting"]) {
    currentValues["Link to Locations Openings"]["Link to General Bitting"] = currentValues["Link to General Bitting"];
}

// If upon creation of a task the user selects to create new general bitting, run this script!
if((currentValues["New/Existing Bitting"] == "New") && (currentValues["Bitting Type"] == "Restricted Key")) {
    Map newRestricted = null;
    newRestricted = save "Restricted Keys",['Link to Location / Facility' : currentValues["Link to Site Visit"]["Link to Location / Facility"],
                                         'Link to Products - Keyway List' : currentValues["Link to Products - Keyway List"],
                                         'Change Key' : currentValues["SKD Cuts"],
                                         'Control Key' : currentValues["Control"],
                                         'Master Key' : currentValues["Master"],
                                         'SubMaster' : currentValues["SubMaster"],
                                         'Key Code (Stamp)' : currentValues["Key Code"],
                                         'Suite' : currentValues["Suite Number"],
                                         'Angles' : currentValues["Angles"]];
    currentValues["Link to Locations Openings"]["Link to Restricted Keys"] = newRestricted;
                            
}

// If upon creation of a task quick add products and services have been chosen - add them!
/*if((currentValues["Quick Add Products / Services"] == "Yes") && (currentValues["Testing"] != "Yes")) {
    addChild "Task Builder","Link to Task List",['Quantity (Product)' : currentValues["Quantity (Product)"],
                                          'Quantity (Service)' : currentValues["Quantity (Service)"],
                                          'Link to Product Database' : currentValues["Link to Product Database"],
                                          'Link to Services' : currentValues["Link to Services"],
                                          'Special Instructions' : currentValues["Task Update"]];
    currentValues["Quantity (Product)"] = null;
    currentValues["Quantity (Service)"] = null;
    currentValues["Link to Product Database"] = null;
    currentValues["Link to Services"] = null;
    currentValues["Task Update"] = null;
    currentValues["Quick Add Products / Services"] = "No";
}*/


// If upon creation of a task quick add products and services have been chosen - TESTING 3.0 with inventory !! add them!
if((currentValues["Quick Add Products / Services"] == "Yes")/* && (currentValues["Testing"] == "Yes")*/) {
    addChild "Task Builder","Link to Task List",['Quantity (Product)' : currentValues["Quantity (Product)"],
                                          'Quantity (Service)' : currentValues["Quantity (Service)"],
                                          'Link to Inventory' : currentValues["Link to Inventory"],
                                          //'Link to Inventory - Sub Location' : currentValues["Link to Inventory"]["Link to Inventory - Sub Location"],
                                          //'Link to Product Database' : currentValues["Link to Inventory"]["Link to Product Database"],
                                          'Link to Services' : currentValues["Link to Services"],
                                          'Special Instructions' : currentValues["Task Update"],
                                          /*'Testing' : "Yes"*/];
    currentValues["Quantity (Product)"] = null;
    currentValues["Quantity (Service)"] = null;
    currentValues["Link to Product Database"] = null;
    currentValues["Link to Services"] = null;
    currentValues["Link to Inventory"] = null;
    currentValues["Task Update"] = null;
    currentValues["Quick Add Products / Services"] = "No";
    //currentValues["Testing"] = null;
}



// If upon creation of a task the user selects to create new Restricted Key, run this script!

/*________________________________________________________________________________________/
/ App: Site Visits
/ Table: Task List
/ Event: Before Delete
/_____________________________________________________________________________________*/

/*___________________________________________________________________/
| This app script handles deletion of tasks.                         |
| When a task is deleted, the current completion weight needs to be  |
| subtracted from the parent current total completion.  Also, when   |
| a task is deleted, the total completion calc needs to go down 3.   |
|__________________________________________________________________*/

// Instantiate variable completedSoFar to current value of task completedness.
double completedSoFar = currentValues["CompletedSoFar"] as double;
// Instatiate remaining variables to zero.
double completed = 0;
double totalCompletion = 0; 

// Depending on parent type, perform calculated subtractions outline in header.

// If parent is a site visit.
if(currentValues["Link to Site Visit"]) {
    // Update completion percent
    completed = currentValues["Link to Site Visit"]["Current Completed"] as double;
    totalCompletion = currentValues["Link to Site Visit"]["Total Completion Calc"] as double;
    // Apply updates
    currentValues["Link to Site Visit"]["Total Completion Calc"] = totalCompletion - 3;
    currentValues["Link to Site Visit"]["Current Completed"] = completed - completedSoFar;

// If parent is a counter Ticket.
} else if(currentValues["Link to Counter Ticket"]) {
    // Update Completion percent
    completed = currentValues["Link to Counter Ticket"]["Current Completed"] as double;
    totalCompletion = currentValues["Link to Counter Ticket"]["Total Completion Calc"] as double;
    // Apply Updates
    currentValues["Link to Counter Ticket"]["Total Completion Calc"] = totalCompletion - 3;
    currentValues["Link to Counter Ticket"]["Current Completed"] = completed - completedSoFar;

// If parent is a Quote
} else if(currentValues["Link to Quote Builder"]) {
    totalCompletion = currentValues["Link to Quote Builder"]["Total Completion Calc"] as double;
    currentValues["Link to Quote Builder"]["Total Completion Calc"] = totalCompletion - 3;
}

List childRecords = getChildren("Task Builder","Link to Task List") as List;
    childRecords.each {
        it["Inventory Command"] = "Cancel";
    }

/*________________________________________________________________________________________/
/ App: Site Visits
/ Table: Task List
/ Event: Before Update
/_____________________________________________________________________________________*/

/*_______________________________________________________|
| This app script does one thing.                        |
| Using business logic it updates the parent Ticket      |
| completed so far values to match the progress of the   |
| child task.                                            | 
|______________________________________________________*/

def runIt() {

//Instantiate Variables to either 0 or current values

//prevVal saves the previous "wait" of task completedness before updating
//This is used in cases where a task goes from weight 1 to 3 or 0 to 2 or any interval
//greater than 1.
double compVal = 0;
double prevVal = previousValues["CompletedSoFar"] as double;
double currentTotalCompleted = 0;

if(currentValues["Quick Add Products / Services"] == "Yes") {
if(currentValues["Link to Product Database"] == null) {
    Map product = loadRecord "Product Database","Common Name","NO PRODUCT";
    currentValues["Link to Product Database"] = product;
}
if(currentValues["Link to Services"] == null) {
    Map service = loadRecord "Services","Service","NO SERVICE";
    currentValues["Link to Services"] = service;
}
if(currentValues["Quantity (Product)"] == null) {
    currentValues["Quantity (Product)"] = 0;
}
if(currentValues["Quantity (Service)"] == null) {
    currentValues["Quantity (Service)"] = 0;
}
}



//Set weight of compVal based on progress selection.
if(currentValues["Progress"] == "Not Started") {
    compVal = 0; // value associated with "Not Started"
} else if(currentValues["Progress"] == "In Progress") {
    compVal = 1; // value associated with "In Progress"
} else if(currentValues["Progress"] == "Waiting") {
    compVal = 2; // value associated with "waiting"
} else if(currentValues["Progress"] == "Completed") {
    compVal = 3; // value associated with "Completed"
} else if(currentValues["Progress"] == "Cancelled") {
    compVal = 3;
}

//if parent is a site visit.
if(currentValues["Link to Site Visit"]) {
    //sets currentTotalCompleted to the combined weight of all completed tasks.
    currentTotalCompleted = currentValues["Link to Site Visit"]["Current Completed"] as double;
    //sets task level current completed to weight assigned by progress from above section.
    currentValues["CompletedSoFar"] = compVal;
    //updates parent ticket to new current completed total
    currentValues["Link to Site Visit"]["Current Completed"] = currentTotalCompleted - prevVal + compVal;

//if parent is a counter ticket.
} else if(currentValues["Link to Counter Ticket"]) {
    currentTotalCompleted = currentValues["Link to Counter Ticket"]["Current Completed"] as double;
    currentValues["CompletedSoFar"] = compVal;
    currentValues["Link to Counter Ticket"]["Current Completed"] = currentTotalCompleted - prevVal + compVal;
}
}


if(previousValues["Opening Name"] != currentValues["Opening Name"]) {
    String str = currentValues["Opening Name"] as String;
    str = str.replace(",", "");
    currentValues["Opening Name"] = str;
}

if(previousValues["Link to Site Visit Tasks"] != currentValues["Link to Site Visit Tasks"]) {
    currentValues["Estimated Task Length"] = currentValues["Link to Site Visit Tasks"]["Estimated Completion Time (Minutes)"];
}

runIt();

/*________________________________________________________________________________________/
/ App: Site Visits
/ Table: Task List
/ Event: Before Insert
/_____________________________________________________________________________________*/

// Set Default Values for Inprogress and Completed so far (on task list)
currentValues["Progress"] = "Not Started";
currentValues["CompletedSoFar"] = 0;
currentValues["Duplicate How Many Times?"] = 0;

// If a product is not selected, pre populate field with NO PRODUCT
if(currentValues["Link to Product Database"] == null) {
    Map product = loadRecord "Product Database","Common Name","NO PRODUCT";
    currentValues["Link to Product Database"] = product;
}

// If a service is not selected, pre populate field with NO SERVICE
if(currentValues["Link to Services"] == null) {
    Map service = loadRecord "Services","Service","NO SERVICE";
    currentValues["Link to Services"] = service;
}

// If Inventory is not selected, pre populate field with NO PRODUCT
    /////ADD THIS///// BEFORE LAUNCH!!!

// If Quantity (Product) is left blank, pre populate 0
if(currentValues["Quantity (Product)"] == null) {
    currentValues["Quantity (Product)"] = 0;
}

// If Quantity (Service) is left blank, pre populate 0
if(currentValues["Quantity (Service)"] == null) {
    currentValues["Quantity (Service)"] = 0;
}

if(currentValues["Opening Name"]) {
    String str = currentValues["Opening Name"] as String;
    str = str.replace(",", "");
    currentValues["Opening Name"] = str;
}

if(currentValues["Link to Site Visit Tasks"]) {
    currentValues["Estimated Task Length"] = currentValues["Link to Site Visit Tasks"]["Estimated Completion Time (Minutes)"];
}

/*________________________________________________________________________________________/
/ App: Site Visits
/ Table: Task List
/ Event: After Update
/_____________________________________________________________________________________*/

/*_______________________________________________________|
| This app script duplicates tasks.                      |
| It will duplicate the task, then read the taskbuilders |
| attached to the task and duplicate them onto the new   |
| task as well.                                          | 
|______________________________________________________*/

def date = new Date();
// How many times should it be duplicated?
if(currentValues["Duplicate How Many Times?"] != 0) {
    // Get Parent Site Visit for attaching new tasks.
    Map siteVisitParent = null;
    if(currentValues["Link to Site Visit"]) { siteVisitParent = currentValues["Link to Site Visit"] as Map; }
    else if(currentValues["Link to Counter Ticket"]) { siteVisitParent = currentValues["Link to Counter Ticket"] as Map; }
    else { siteVisitParent = currentValues["Link to Quote Builder"] as Map; }
    // Get task name for replication.
    Map taskName = currentValues["Link to Site Visit Tasks"] as Map;
    // Gather Task builders for replication.
    List taskBuilders = getChildren("Task Builder", "Link to Task List") as List;
    // load "Duplicate How many times" into an Int.
    int dupNum = currentValues["Duplicate How Many Times?"] as int;
    
    // Loop map and duplication code dupNum number of times.
    (1..dupNum).each {
        
        // Create new task linked to parent site visit.
        Map newTask = null;
        if(currentValues["Link to Site Visit"]) { 
            newTask = save "Task List",['Link to Site Visit' : siteVisitParent,
                                        'Link to Site Visit Tasks' : taskName] as Map; 
        } else if(currentValues["Link to Counter Ticket"]) { 
            newTask = save "Task List",['Link to Counter Ticket' : siteVisitParent,
                                        'Link to Site Visit Tasks' : taskName] as Map;
        } else { 
            newTask = save "Task List",['Link to Quote Builder' : siteVisitParent,
                                        'Link to Site Visit Tasks' : taskName] as Map; 
        }
        
        // Iterate Child TaskBuilders
        taskBuilders.each {
            // Create child task builders.
            save "Task Builder",['Link to Task List' : newTask, // this is the new duplicated task from above
                                 'Quantity (Product)' : it["Quantity (Product)"],
                                 'Link to Product Database' : it["Link to Product Database"],
                                 'Quantity (Service)' : it["Quantity (Service)"],
                                 'Link to Services' : it["Link to Services"],
                                 'Adjusted Cost' : it["Adjusted Cost"],
                                 'Adjust Markup' : it["Adjust Markup"],
                                 'Link to Inventory' : it["Link to Inventory"]];
        }
    }
    currentValues["Duplicate How Many Times?"] = 0; // reset duplicate number to 0
}                       

// Check Parent Type and reapply to task builders
if(currentValues["Link to Quote Builder"] && currentValues["Link to Site Visit"]) { 
    currentValues["Parent Type"] = 0;
    List childRecords = getChildren("Task Builder", "Link to Task List") as List;
    childRecords.each {it["Grand Parent Type"] = 0}
    
}
// Calculates Parent complete status and then updates task builders that grandparent is complete
if((previousValues["Progress"] != "Completed") && (currentValues["Progress"] == "Completed")) {
    currentValues["Task User"] = currentValues["Last User"];
    currentValues["AppScriptCompleteDate"] = date;
    List sellTaskBuilders = getChildren("Task Builder", "Link to Task List") as List;
    sellTaskBuilders.each {
        it["Parent Task Complete"] = 1;
    }
}
// If Task is cancelled, delete task builders - probably doesn't work...
if((previousValues["Progress"] != "Cancelled") && (currentValues["Progress"] == "Cancelled")) {
    List deleteTaskBuilders = getChildren("Task Builder", "Link to Task List") as List;
    deleteTaskBuilders.each {
        it["Parent Task Cancelled"] = 1;
    }
}
// If Task is cancelled, and then is uncancelled, update task builders accordingly
if((previousValues["Progress"] == "Cancelled") && (currentValues["Progress"] != "Cancelled")) {
    List deleteTaskBuilders = getChildren("Task Builder", "Link to Task List") as List;
    deleteTaskBuilders.each {
        it["Parent Task Cancelled"] = 0;
    }
}

if(currentValues["Warranty Site Visit"] == 1) {
    List childRecords = getChildren("Task Builder", "Link to Task List") as List;
    childRecords.each {it["Warranty Site Visit"] = 1}
}

// Apply Process Admin = Yes to all child task builders - used by line items table
if((currentValues["Process ADMIN"] == null) && (previousValues["Process ADMIN"] != null)) {
    List childRecords = getChildren("Task Builder","Link to Task List") as List;
    childRecords.each {
        it["Process ADMIN"] = null;
    }
}
// Undo previous operation
if((currentValues["Process ADMIN"] == "YES") && (previousValues["Process ADMIN"] != "YES")) {
    List childRecords = getChildren("Task Builder","Link to Task List") as List;
    childRecords.each {
        it["Process ADMIN"] = "YES";
    }
}

/*___________________________________________________________________________________________________|
| Total opening overhaul update 2.0!  Moved several opening fields to the task.  Want to hold them   |
| both places, the task and opening.  If its a new opening, add the new fields to opening. If its    |
| an existing opening, add existing opening fields to the task, then display them! - lovely          |
|__________________________________________________________________________________________________*/

// If upon updating a task the user selects to create a new opening, run this script after saving.
if(/*(previousValues["New/Existing Opening"] != "New") &&*/ (currentValues["New/Existing Opening"] == "New") && (currentValues["Parent Type"] != 1)) {
    currentValues["Link to Locations Openings"]["Opening Name"] = currentValues["Opening Name"];
    currentValues["Link to Locations Openings"]["Floor Number"] = currentValues["Floor Number"];
    currentValues["Link to Locations Openings"]["Suite #"] = currentValues["Suite Number"];
    
    if(currentValues["Cylinder Type"] == null) {
        if(currentValues["Link to Site Visit"]["Link to Location / Facility"]) {
            currentValues["Link to Locations Openings"]["Cylinder Type"] = currentValues["Link to Site Visit"]["Link to Location / Facility"]["Default Cylinder Type"];
            currentValues["Cylinder Type"] = currentValues["Link to Site Visit"]["Link to Location / Facility"]["Default Cylinder Type"];
        }
    }
    if(currentValues["Cylinder Type"] != null) {
        currentValues["Link to Locations Openings"]["Cylinder Type"] = currentValues["Cylinder Type"];
    }
    
    if(currentValues["Lock Type"] == null) {
        if(currentValues["Link to Site Visit"]["Link to Location / Facility"]) {
            currentValues["Link to Locations Openings"]["Lock Type"] = currentValues["Link to Site Visit"]["Link to Location / Facility"]["Default Lock Type"];
            currentValues["Lock Type"] = currentValues["Link to Site Visit"]["Link to Location / Facility"]["Default Lock Type"];
        }
    }
    if(currentValues["Lock Type"] != null) {
        currentValues["Link to Locations Openings"]["Lock Type"] = currentValues["Lock Type"];
    }
    
    if(currentValues["Lock Manufacturer"] == null) {
        if(currentValues["Link to Site Visit"]["Link to Location / Facility"]) {
            currentValues["Link to Locations Openings"]["Lock Manufacturer"] = currentValues["Link to Site Visit"]["Link to Location / Facility"]["Default Lock Manufacturer"];
            currentValues["Lock Manufacturer"] = currentValues["Link to Site Visit"]["Link to Location / Facility"]["Default Lock Manufacturer"];
        }
    }
    if(currentValues["Lock Manufacturer"] != null) {
        currentValues["Link to Locations Openings"]["Lock Manufacturer"] = currentValues["Lock Manufacturer"];
    }
    
    if(currentValues["Parent Type"] == 0) {
        currentValues["Link to Locations Openings"]["Link to Location / Facility"] = currentValues["Link to Site Visit"]["Link to Location / Facility"];
    }
    if(currentValues["Parent Type"] == 2) {
        currentValues["Link to Locations Openings"]["Link to Location / Facility"] = currentValues["Link to Quote Builder"]["Link to Location / Facility"];
    }
}

// If upon updating a task the user selects to use an existing opening, run this script!
if((previousValues["New/Existing Opening"] != "Existing") && (currentValues["New/Existing Opening"] == "Existing") && (currentValues["Parent Type"] == 0)) {
    currentValues["Opening Name"] = currentValues["Link to Locations Openings"]["Opening Name"];
    currentValues["Floor Number"] = currentValues["Link to Locations Openings"]["Floor Number"];
    currentValues["Suite Number"] = currentValues["Link to Locations Openings"]["Suite #"];
    currentValues["Cylinder Type"] = currentValues["Link to Locations Openings"]["Cylinder Type"];
    currentValues["Barcode ID"] = currentValues["Link to Locations Openings"]["Barcode ID"];
    currentValues["AppScript_Use_Existing_Opening"] = 1;
}

// If upon Update a task the user selects to create new general bitting, run this script!
if((previousValues["New/Existing Bitting"] != "New") && (currentValues["New/Existing Bitting"] == "New") && (currentValues["Bitting Type"] == "General Bitting") && (currentValues["Add Type"] == "Manual Add")) {
    Map newBitting = null;
    newBitting = save "General Bitting",['Link to Complex' : currentValues["Link to Site Visit"]["Link to Location / Facility"]["Link to Complex"],
                                         'Link to Location / Facility' : currentValues["Link to Site Visit"]["Link to Location / Facility"],
                                         'Link to Products - Keyway List' : currentValues["Link to Products - Keyway List"],
                                         'SKD Cuts (Bitting)' : currentValues["SKD Cuts"],
                                         'Control Key' : currentValues["Control"],
                                         'Master Key' : currentValues["Master"],
                                         'SubMaster' : currentValues["SubMaster"],
                                         'Keycode' : currentValues["Key Code"],
                                         'Suite' : currentValues["Suite Number"]];
    currentValues["Link to Locations Openings"]["Link to General Bitting"] = newBitting;
                            
}

// If upon update a task the user selects to create new general bitting using random 5 pin, run this script!
if((previousValues["New/Existing Bitting"] != "New") && (currentValues["New/Existing Bitting"] == "New") && (currentValues["Bitting Type"] == "General Bitting") && (currentValues["Add Type"] == "Choose Available - 5 Pin")) {
    Map newBitting = null;
    currentValues["SKD Cuts"] = currentValues["Link to Available 5 Pin Bitting"]["SKD Cuts"];
    newBitting = save "General Bitting",['Link to Complex' : currentValues["Link to Site Visit"]["Link to Location / Facility"]["Link to Complex"],
                                         'Link to Location / Facility' : currentValues["Link to Site Visit"]["Link to Location / Facility"],
                                         'Link to Products - Keyway List' : currentValues["Link to Products - Keyway List"],
                                         'SKD Cuts (Bitting)' : currentValues["SKD Cuts"],
                                         'Control Key' : currentValues["Control"],
                                         'Master Key' : currentValues["Master"],
                                         'SubMaster' : currentValues["SubMaster"],
                                         'Keycode' : currentValues["Key Code"],
                                         'Suite' : currentValues["Suite Number"]];
    currentValues["Link to Locations Openings"]["Link to General Bitting"] = newBitting;
}

// If upon update a task the user selects to create new general bitting using random 6 pin, run this script!
if((previousValues["New/Existing Bitting"] != "New") && (currentValues["New/Existing Bitting"] == "New") && (currentValues["Bitting Type"] == "General Bitting") && (currentValues["Add Type"] == "Choose Available - 6 Pin")) {
    Map newBitting = null;
    currentValues["SKD Cuts"] = currentValues["Link to Available 6 Pin Bitting"]["SKD Cuts"];
    newBitting = save "General Bitting",['Link to Complex' : currentValues["Link to Site Visit"]["Link to Location / Facility"]["Link to Complex"],
                                         'Link to Location / Facility' : currentValues["Link to Site Visit"]["Link to Location / Facility"],
                                         'Link to Products - Keyway List' : currentValues["Link to Products - Keyway List"],
                                         'SKD Cuts (Bitting)' : currentValues["SKD Cuts"],
                                         'Control Key' : currentValues["Control"],
                                         'Master Key' : currentValues["Master"],
                                         'SubMaster' : currentValues["SubMaster"],
                                         'Keycode' : currentValues["Key Code"],
                                         'Suite' : currentValues["Suite Number"]];
    currentValues["Link to Locations Openings"]["Link to General Bitting"] = newBitting;
}

// If upon update a task the user selects to create new combination Lock, run this script!
if((previousValues["New/Existing Bitting"] != "New") && (currentValues["New/Existing Bitting"] == "New") && (currentValues["Bitting Type"] == "Combination Lock")) {
    Map newCombo = null;
    newCombo = save "Combination Locks",['Link to Location / Facility' : currentValues["Link to Site Visit"]["Link to Location / Facility"],
                                        'User Combination' : currentValues["User Combination"],
                                        'Programming Combination' : currentValues["Programming Combination"]];
    currentValues["Link to Locations Openings"]["Link to Combination Locks"] = newCombo;
}

// If upon creation of a task the user selects to create new general bitting, run this script!
if((previousValues["New/Existing Bitting"] != "New") && (currentValues["New/Existing Bitting"] == "New") && (currentValues["Bitting Type"] == "Restricted Key")) {
    Map newRestricted = null;
    newRestricted = save "Restricted Keys",['Link to Location / Facility' : currentValues["Link to Site Visit"]["Link to Location / Facility"],
                                         'Link to Products - Keyway List' : currentValues["Link to Products - Keyway List"],
                                         'Change Key' : currentValues["SKD Cuts"],
                                         'Control Key' : currentValues["Control"],
                                         'Master Key' : currentValues["Master"],
                                         'SubMaster' : currentValues["SubMaster"],
                                         'Key Code (Stamp)' : currentValues["Key Code"],
                                         'Suite' : currentValues["Suite Number"],
                                         'Angles' : currentValues["Angles"]];
    currentValues["Link to Locations Openings"]["Link to Restricted Keys"] = newRestricted;
                            
}

// If after updating a task the user selects to use existing general bitting (bitting linked through location on opening)
if(currentValues["Link to Locations Openings"] && currentValues["Link to General Bitting"] && !previousValues["Link to General Bitting"]) {
    currentValues["Link to Locations Openings"]["Link to General Bitting"] = currentValues["Link to General Bitting"];
}

// If after updating a task the user selects to use existing master key bitting (bitting linked through location on opening)
if(currentValues["Link to Locations Openings"] && currentValues["Link to Generated System"] && !previousValues["Link to Generated System"]) {
    currentValues["Link to Locations Openings"]["Link to Generated System"] = currentValues["Link to Generated System"];
    save "All Bitting", ['Link to Generated System' : currentValues["Link to Generated System"],
                         'Link to Location / Facility' : currentValues["Link to Locations Openings"]["Link to Location / Facility"],
                         'Link to Products - Keyway List' : currentValues["Link to Generated System"]["Link to System IDs"]["Link to Products - Keyway List"],
                         'Bitting Type' : "Master",
                         'Key Code' : currentValues["Link to Generated System"]["Blind Code"],
                         'SKD Cuts' : currentValues["Link to Generated System"]["Bitting"],
                         'System ID' : currentValues["Link to Generated System"]["System ID"]];
}

// If after updating a task the user selects to use existing combination bitting (bitting linked through location on opening)
if(currentValues["Link to Locations Openings"] && currentValues["Link to Combination Locks"] && !previousValues["Link to Combination Locks"]) {
    currentValues["Link to Locations Openings"]["Link to Combination Locks"] = currentValues["Link to Combination Locks"];
}

// If after updating a task the user selects to use existing Restricted bitting (bitting linked through location on opening)
if(currentValues["Link to Locations Openings"] && currentValues["Link to Restricted Keys"] && !previousValues["Link to Restricted Keys"]) {
    currentValues["Link to Locations Openings"]["Link to Restricted Keys"] = currentValues["Link to Restricted Keys"];
}

// If updating a task quick add products and services have been chosen - add them!
/*if((previousValues["Quick Add Products / Services"] != "Yes") && (currentValues["Quick Add Products / Services"] == "Yes") && (currentValues["Testing"] != "Yes")) {
    addChild "Task Builder","Link to Task List",['Quantity (Product)' : currentValues["Quantity (Product)"],
                                          'Quantity (Service)' : currentValues["Quantity (Service)"],
                                          'Link to Product Database' : currentValues["Link to Product Database"],
                                          'Link to Services' : currentValues["Link to Services"],
                                          'Special Instructions' : currentValues["Task Update"]];
    currentValues["Quantity (Product)"] = null;
    currentValues["Quantity (Service)"] = null;
    currentValues["Link to Product Database"] = null;
    currentValues["Link to Services"] = null;
    currentValues["Task Update"] = null;
    currentValues["Quick Add Products / Services"] = "No";
}*/

// If upon creation of a task quick add products and services have been chosen - TESTING 3.0 with inventory !! add them!
if((currentValues["Quick Add Products / Services"] == "Yes") /*&& (currentValues["Testing"] == "Yes")*/) {
    addChild "Task Builder","Link to Task List",['Quantity (Product)' : currentValues["Quantity (Product)"],
                                          'Quantity (Service)' : currentValues["Quantity (Service)"],
                                          'Link to Inventory' : currentValues["Link to Inventory"],
                                          //'Link to Inventory - Sub Location' : currentValues["Link to Inventory"]["Link to Inventory - Sub Location"],
                                          //'Link to Product Database' : currentValues["Link to Inventory"]["Link to Product Database"],
                                          'Link to Services' : currentValues["Link to Services"],
                                          'Special Instructions' : currentValues["Task Update"],
                                          /*'Testing' : "Yes"*/];
    logger.error("I'm here second pass ran after insert");
    currentValues["Quantity (Product)"] = null;
    currentValues["Quantity (Service)"] = null;
    currentValues["Link to Product Database"] = null;
    currentValues["Link to Services"] = null;
    currentValues["Link to Inventory"] = null;
    currentValues["Task Update"] = null;
    currentValues["Quick Add Products / Services"] = "No";
    //currentValues["Testing"] = null;
}

if((previousValues["Barcode ID"] == null) && (currentValues["Barcode ID"] != null)) {
    currentValues["Link to Locations Openings"]["Barcode ID"] = currentValues["Barcode ID"];
}

/*________________________________________________________________________________________________________|
/ Bulk inventory operations below.  These are not to be used by the general public, and only for admin    |
/ Processes.  These are behind the scenes operations purely used for "Legacy" Site Visits                 |
/_______________________________________________________________________________________________________*/

if((currentValues["Inventory Command"] == null) && (previousValues["Inventory Command"] != null)) {
    List childRecords = getChildren("Task Builder","Link to Task List") as List;
    childRecords.each {
        it["Inventory Command"] = null;
    }
}

if((currentValues["Inventory Command"] == "Cancel") && (previousValues["Inventory Command"] != "Cancel")) {
    List childRecords = getChildren("Task Builder","Link to Task List") as List;
    childRecords.each {
        it["Inventory Command"] = "Cancel";
    }
}

if((currentValues["Inventory Command"] == "Sell") && (previousValues["Inventory Command"] != "Sell")) {
    List childRecords = getChildren("Task Builder","Link to Task List") as List;
    childRecords.each {
        it["Inventory Command"] = "Sell";
    }
}

if((currentValues["Inventory Command"] == "Reserve") && (previousValues["Inventory Command"] != "Reserve")) {
    List childRecords = getChildren("Task Builder","Link to Task List") as List;
    childRecords.each {
        it["Inventory Command"] = "Reserve";
    }
}

if((currentValues["Inventory Command"] == "Unsell") && (previousValues["Inventory Command"] != "Unsell")) {
    List childRecords = getChildren("Task Builder","Link to Task List") as List;
    childRecords.each {
        it["Inventory Command"] = "Unsell";
    }
}

/*________________________________________________________________________________________/
/ App: Site Visits
/ Table: User - Comm Join Table
/ Event: After Insert
/_____________________________________________________________________________________*/

//____________________________________________________________//
// This application script is used to set user fields on the //
// Communication / TO-DO table using a multi-select.        //
//_________________________________________________________//

//Test if user is "User Name"
if(currentValues["Link to User Table"]["User Name"] == "Lucas_Test") {
    //If user, then set site visit user field to xvia application user.
    Map systemUser = loadRecord("Account Users", "Email", "wardl.mobile@gmail.com") as Map;
    currentValues["Link to Communication Notes"]["Join_Lucas_Test"] = systemUser;

// Derek Tyler
} else if(currentValues["Link to User Table"]["User Name"] == "Derek Tyler") {
    Map systemUser1 = loadRecord("Account Users", "Email", "derek@locdoc.net") as Map;
    currentValues["Link to Communication Notes"]["Join_Derek_Tyler"] = systemUser1;

// Dustin Fite
} else if(currentValues["Link to User Table"]["User Name"] == "Dustin Fite") {
    Map systemUser2 = loadRecord("Account Users", "Email", "dustin@locdoc.net") as Map;
    currentValues["Link to Communication Notes"]["Join_Dustin_Fite"] = systemUser2;

// Luke Hanson   
} else if(currentValues["Link to User Table"]["User Name"] == "Luke Hanson") {
    Map systemUser3 = loadRecord("Account Users", "Email", "luke@locdoc.net") as Map;
    currentValues["Link to Communication Notes"]["Join_Luke_Hanson"] = systemUser3;

// Stephen Clubb
} else if(currentValues["Link to User Table"]["User Name"] == "Stephen Clubb") {
    Map systemUser4 = loadRecord("Account Users", "Email", "stephen@locdoc.net") as Map;
    currentValues["Link to Communication Notes"]["Join_Stephen_Clubb"] = systemUser4;

// Sergey Shamshur    
} else if(currentValues["Link to User Table"]["User Name"] == "Sergey Shamshur") {
    Map systemUser5 = loadRecord("Account Users", "Email", "sergey@locdoc.net") as Map;
    currentValues["Link to Communication Notes"]["Join_Sergey_Shamshur"] = systemUser5;

// Christopher Baggett    
} else if(currentValues["Link to User Table"]["User Name"] == "Christopher Baggett") {
    Map systemUser6 = loadRecord("Account Users", "Email", "christopher@locdoc.net") as Map;
    currentValues["Link to Communication Notes"]["Join_Christopher_Baggett"] = systemUser6;

// Josh McVickers    
} else if(currentValues["Link to User Table"]["User Name"] == "Josh McVickers") {
    Map systemUser7 = loadRecord("Account Users", "Email", "josh@locdoc.net") as Map;
    currentValues["Link to Communication Notes"]["Join_Josh_McVickers"] = systemUser7;

// James Caul    
} else if(currentValues["Link to User Table"]["User Name"] == "James Caul") {
    Map systemUser8 = loadRecord("Account Users", "Email", "james@locdoc.net") as Map;
    currentValues["Link to Communication Notes"]["Join_James_Caul"] = systemUser8;

// Andrew Moody    
} else if(currentValues["Link to User Table"]["User Name"] == "Andrew Moody") {
    Map systemUser9 = loadRecord("Account Users", "Email", "andrew@locdoc.net") as Map;
    currentValues["Link to Communication Notes"]["Join_Andrew_Moody"] = systemUser9;

// Andy Tucker    
} else if(currentValues["Link to User Table"]["User Name"] == "Andy Tucker") {
    Map systemUser10 = loadRecord("Account Users", "Email", "andy@locdoc.net") as Map;
    currentValues["Link to Communication Notes"]["Join_Andy_Tucker"] = systemUser10;

// Leonard McConniel    
} else if(currentValues["Link to User Table"]["User Name"] == "Leonard McConniel") {
    Map systemUser11 = loadRecord("Account Users", "Email", "leonard@locdoc.net") as Map;
    currentValues["Link to Communication Notes"]["Join_Leonard_McConniel"] = systemUser11;

// Mike Tyler    
} else if(currentValues["Link to User Table"]["User Name"] == "Mike Tyler") {
    Map systemUser12 = loadRecord("Account Users", "Email", "Mike@locdoc.net") as Map;
    currentValues["Link to Communication Notes"]["Join_Mike_Tyler"] = systemUser12;

// Dylan Tyler    
} else if(currentValues["Link to User Table"]["User Name"] == "Dylan Tyler") {
    Map systemUser13 = loadRecord("Account Users", "Email", "dylan@locdoc.net") as Map;
    currentValues["Link to Communication Notes"]["Join_Dylan_Tyler"] = systemUser13;

// Josh Smith    
} else if(currentValues["Link to User Table"]["User Name"] == "Josh Smith") {
    Map systemUser14 = loadRecord("Account Users", "Email", "josh.smith@locdoc.net") as Map;
    currentValues["Link to Communication Notes"]["Join_Josh_Smith"] = systemUser14;

// Thomas Heavey    
} else if(currentValues["Link to User Table"]["User Name"] == "Thomas Heavey") {
    Map systemUser15 = loadRecord("Account Users", "Email", "thomas@locdoc.net") as Map;
    currentValues["Link to Communication Notes"]["Join_Thomas_Heavey"] = systemUser15;

// Sam Gray   
} else if(currentValues["Link to User Table"]["User Name"] == "Sam Gray") {
    Map systemUser16 = loadRecord("Account Users", "Email", "sam@locdoc.net") as Map;
    currentValues["Link to Communication Notes"]["Join_Sam_Gray"] = systemUser16;
    
// Zach Phifer    
} else if(currentValues["Link to User Table"]["User Name"] == "Zach Phifer") {
    Map systemUser17 = loadRecord("Account Users", "Email", "zach@locdoc.net") as Map;
    currentValues["Link to Communication Notes"]["Join_Zach_Phifer"] = systemUser17;
    
// Kevan Starr
} else if(currentValues["Link to User Table"]["User Name"] == "Kevan Starr") {
    Map systemUser18 = loadRecord("Account Users", "Email", "kevan@locdoc.net") as Map;
    currentValues["Link to Communication Notes"]["Join_Kevan_Starr"] = systemUser18

// Chad Lingafelt
} else if(currentValues["Link to User Table"]["User Name"] == "Chad Lingafelt") {
    Map systemUser19 = loadRecord("Account Users", "Email", "chad@locdoc.net") as Map;
    currentValues["Link to Communication Notes"]["Join_Chad_Lingafelt"] = systemUser19

// Chris Lowery
} else if(currentValues["Link to User Table"]["User Name"] == "Chris Lowery") {
    Map systemUser20 = loadRecord("Account Users", "Email", "chris@locdoc.net") as Map;
    currentValues["Link to Communication Notes"]["Join_Chris_Lowery"] = systemUser20

// Jessica Bennet
} else if(currentValues["Link to User Table"]["User Name"] == "Jessica Bennet") {
    Map systemUser21 = loadRecord("Account Users", "Email", "jessica.bennet@locdoc.net") as Map;
    currentValues["Link to Communication Notes"]["Join_Jessica_Bennet"] = systemUser21

// Allyson Tyler
} else if(currentValues["Link to User Table"]["User Name"] == "Allyson Tyler") {
    Map systemUser22 = loadRecord("Account Users", "Email", "allyson@locdoc.net") as Map;
    currentValues["Link to Communication Notes"]["Join_Allyson_Tyler"] = systemUser22

// McDouglas Perez
} else if(currentValues["Link to User Table"]["User Name"] == "McDouglas Perez") {
    Map systemUser23 = loadRecord("Account Users", "Email", "ar@locdoc.net") as Map;
    currentValues["Link to Communication Notes"]["Join_McDouglas_Perez"] = systemUser23

// Jennifer Lowery
} else if(currentValues["Link to User Table"]["User Name"] == "Jennifer Lowery") {
    Map systemUser24 = loadRecord("Account Users", "Email", "jennifer@locdoc.net") as Map;
    currentValues["Link to Communication Notes"]["Join_Jennifer_Lowery"] = systemUser24

// Levi Gray
} else if(currentValues["Link to User Table"]["User Name"] == "Levi Gray") {
    Map systemUser25 = loadRecord("Account Users", "Email", "levi@locdoc.net") as Map;
    currentValues["Link to Communication Notes"]["Join_Levi_Gray"] = systemUser25

// Nathan Hammer
} else if(currentValues["Link to User Table"]["User Name"] == "Nathan Hammer") {
    Map systemUser26 = loadRecord("Account Users", "Email", "Nathan@locdoc.net") as Map;
    currentValues["Link to Communication Notes"]["Join_Nathan_Hammer"] = systemUser26

// Michael Childress    
} else if(currentValues["Link to User Table"]["User Name"] == "Michael Childress") {
    Map systemUser27 = loadRecord("Account Users", "Email", "Michael@locdoc.net") as Map;
    currentValues["Link to Communication Notes"]["Join_Michael_Childress"] = systemUser27

// William Gleaves    
} else if(currentValues["Link to User Table"]["User Name"] == "William Gleaves") {
    Map systemUser28 = loadRecord("Account Users", "Email", "william@locdoc.net") as Map;
    currentValues["Link to Communication Notes"]["Join_William_Gleaves"] = systemUser28
    
// Jordan Boatwright    
} else if(currentValues["Link to User Table"]["User Name"] == "Jordan Boatwright") {
    Map systemUser29 = loadRecord("Account Users", "Email", "jordan@locdoc.net") as Map;
    currentValues["Link to Communication Notes"]["Join_Jordan_Boatwright"] = systemUser29
    
// Eric Spear    
} else if(currentValues["Link to User Table"]["User Name"] == "Eric Spear") {
    Map systemUser30 = loadRecord("Account Users", "Email", "eric@locdoc.net") as Map;
    currentValues["Link to Communication Notes"]["Join_Eric_Spear"] = systemUser30
    
// Scotty Hinson   
} else if(currentValues["Link to User Table"]["User Name"] == "Scotty Hinson") {
    Map systemUser31 = loadRecord("Account Users", "Email", "scotty@locdoc.net") as Map;
    currentValues["Link to Communication Notes"]["Join_Scotty_Hinson"] = systemUser31

    
    
} 

/*________________________________________________________________________________________/
/ App: Site Visits
/ Table: User - Comm Join Table
/ Event: Before Delete
/_____________________________________________________________________________________*/

//_______________________________________________________________________________//
//This app script is the same as the after insert with slight modifications     //
//When a user is deleted from multi-select it removes the user specific field. //
//____________________________________________________________________________//

//Test if current user is added
if(currentValues["Link to User Table"]["User Name"] == "Lucas_Test") {
    //If current user equals "User name" then delete specialty field.
    currentValues["Link to Site Visit"]["Join_Lucas_Test"] = null;

// Dustin Fite    
} else if(currentValues["Link to User Table"]["User Name"] == "Dustin Fite") {
    currentValues["Link to Site Visit"]["Join_Dustin_Fite"] = null;

// Luke Hanson    
} else if(currentValues["Link to User Table"]["User Name"] == "Luke Hanson") {
    currentValues["Link to Site Visit"]["Join_Luke_Hanson"] = null;

// Stephen Clubb
} else if(currentValues["Link to User Table"]["User Name"] == "Stephen Clubb") {
    currentValues["Link to Site Visit"]["Join_Stephen_Clubb"] = null;

// Derek Tyler    
} else if(currentValues["Link to User Table"]["User Name"] == "Derek Tyler") {
    currentValues["Link to Communication Notes"]["Join_Derek_Tyler"] = null;

// Sergey Shamshur    
} else if(currentValues["Link to User Table"]["User Name"] == "Sergey Shamshur") {
    currentValues["Link to Communication Notes"]["Join_Sergey_Shamshur"] = null;

// Christopher Baggett    
} else if(currentValues["Link to User Table"]["User Name"] == "Christopher Baggett") {
    currentValues["Link to Communication Notes"]["Join_Christopher_Baggett"] = null;

// Josh McVickers    
} else if(currentValues["Link to User Table"]["User Name"] == "Josh McVickers") {
    currentValues["Link to Communication Notes"]["Join_Josh_McVickers"] = null;

// James Caul    
} else if(currentValues["Link to User Table"]["User Name"] == "James Caul") {
    currentValues["Link to Communication Notes"]["Join_James_Caul"] = null;

// Andrew Moody    
} else if(currentValues["Link to User Table"]["User Name"] == "Andrew Moody") {
    currentValues["Link to Communication Notes"]["Join_Andrew_Moody"] = null;

// Andy Tucker   
} else if(currentValues["Link to User Table"]["User Name"] == "Andy Tucker") {
    currentValues["Link to Communication Notes"]["Join_Andy_Tucker"] = null;

// Leonard McConniel    
} else if(currentValues["Link to User Table"]["User Name"] == "Leonard McConniel") {
    currentValues["Link to Communication Notes"]["Join_Leonard_McConniel"] = null;

// Mike Tyler    
} else if(currentValues["Link to User Table"]["User Name"] == "Mike Tyler") {
    currentValues["Link to Communication Notes"]["Join_Mike_Tyler"] = null;

// Dylan Tyler    
} else if(currentValues["Link to User Table"]["User Name"] == "Dylan Tyler") {
    currentValues["Link to Communication Notes"]["Join_Dylan_Tyler"] = null;

// Josh Smith   
} else if(currentValues["Link to User Table"]["User Name"] == "Josh Smith") {
    currentValues["Link to Communication Notes"]["Join_Josh_Smith"] = null;

// Thomas Heavey    
} else if(currentValues["Link to User Table"]["User Name"] == "Thomas Heavey") {
    currentValues["Link to Communication Notes"]["Join_Thomas_Heavey"] = null;
    
// Sam Gray
} else if(currentValues["Link to User Table"]["User Name"] == "Sam Gray") {
    currentValues["Link to Communication Notes"]["Join_Sam_Gray"] = null;
    
// Zach Phifer
} else if(currentValues["Link to User Table"]["User Name"] == "Zach Phifer") {
    currentValues["Link to Communication Notes"]["Join_Zach_Phifer"] = null;
    
// Kevan Starr 
} else if(currentValues["Link to User Table"]["User Name"] == "Kevan Starr") {
    currentValues["Link to Communication Notes"]["Join_Kevan_Starr"] = null;

// Chad Lingafelt
} else if(currentValues["Link to User Table"]["User Name"] == "Chad Lingafelt") {
    currentValues["Link to Communication Notes"]["Join_Chad_Lingafelt"] = null;

// Chris Lowery
} else if(currentValues["Link to User Table"]["User Name"] == "Chris Lowery") {
    currentValues["Link to Communication Notes"]["Join_Chris_Lowery"] = null;

// Jessica Bennet
} else if(currentValues["Link to User Table"]["User Name"] == "Jessica Bennet") {
    currentValues["Link to Communication Notes"]["Join_Jessica_Bennet"] = null;

// Allyson Tyler
} else if(currentValues["Link to User Table"]["User Name"] == "Allyson Tyler") {
    currentValues["Link to Communication Notes"]["Join_Allyson_Tyler"] = null;

// McDouglas Perez
} else if(currentValues["Link to User Table"]["User Name"] == "McDouglas Perez") {
    currentValues["Link to Communication Notes"]["Join_McDouglas_Perez"] = null;

// Jennifer Lowery
} else if(currentValues["Link to User Table"]["User Name"] == "Jennifer Lowery") {
    currentValues["Link to Communication Notes"]["Join_Jennifer_Lowery"] = null;

// Levi Gray
} else if(currentValues["Link to User Table"]["User Name"] == "Levi Gray") {
    currentValues["Link to Communication Notes"]["Join_Levi_Gray"] = null;

// Nathan Hammer
} else if(currentValues["Link to User Table"]["User Name"] == "Nathan Hammer") {
    currentValues["Link to Communication Notes"]["Join_Nathan_Hammer"] = null;

// Michael Childress
} else if(currentValues["Link to User Table"]["User Name"] == "Michael Childress") {
    currentValues["Link to Communication Notes"]["Join_Michael_Childress"] = null;

// William Gleaves
} else if(currentValues["Link to User Table"]["User Name"] == "William Gleaves") {
    currentValues["Link to Communication Notes"]["Join_William_Gleaves"] = null;

// Jordan Boatwright
} else if(currentValues["Link to User Table"]["User Name"] == "Jordan Boatwright") {
    currentValues["Link to Communication Notes"]["Join_Jordan_Boatwright"] = null;
    
// Eric Spear
} else if(currentValues["Link to User Table"]["User Name"] == "Eric Spear") {
    currentValues["Link to Communication Notes"]["Join_Eric_Spear"] = null;
    
// Scotty Hinson
} else if(currentValues["Link to User Table"]["User Name"] == "Scotty Hinson") {
    currentValues["Link to Communication Notes"]["Join_Scotty_Hinson"] = null;
    
}

/*________________________________________________________________________________________/
/ App: Site Visits
/ Table: User - Site Visit Join Table
/ Event: After Insert
/_____________________________________________________________________________________*/

//____________________________________________________________//
// This application script is used to set user fields on the //
// Site Visit table using a multi-select.                   //
//_________________________________________________________//

currentValues["User Drop Down"] = currentValues["Link to User Table"]["User Name"];

//Test if user is "User Name"
if(currentValues["Link to User Table"]["User Name"] == "Lucas_Test") {
    //If user, then set site visit user field to xvia application user.
    Map systemUser = loadRecord("Account Users", "Email", "wardl.mobile@gmail.com") as Map;
    currentValues["Link to Site Visit"]["Join_Lucas_Test"] = systemUser;

// Derek Tyler
} else if(currentValues["Link to User Table"]["User Name"] == "Derek Tyler") {
    Map systemUser1 = loadRecord("Account Users", "Email", "derek@locdoc.net") as Map;
    currentValues["Link to Site Visit"]["Join_Derek_Tyler"] = systemUser1;

// Dustin Fite
} else if(currentValues["Link to User Table"]["User Name"] == "Dustin Fite") {
    Map systemUser2 = loadRecord("Account Users", "Email", "dustin@locdoc.net") as Map;
    currentValues["Link to Site Visit"]["Join_Dustin_Fite"] = systemUser2;

// Luke Hanson   
} else if(currentValues["Link to User Table"]["User Name"] == "Luke Hanson") {
    Map systemUser3 = loadRecord("Account Users", "Email", "luke@locdoc.net") as Map;
    currentValues["Link to Site Visit"]["Join_Luke_Hanson"] = systemUser3;

// Stephen Clubb
} else if(currentValues["Link to User Table"]["User Name"] == "Stephen Clubb") {
    Map systemUser4 = loadRecord("Account Users", "Email", "stephen@locdoc.net") as Map;
    currentValues["Link to Site Visit"]["Join_Stephen_Clubb"] = systemUser4;

// Sergey Shamshur    
} else if(currentValues["Link to User Table"]["User Name"] == "Sergey Shamshur") {
    Map systemUser5 = loadRecord("Account Users", "Email", "sergey@locdoc.net") as Map;
    currentValues["Link to Site Visit"]["Join_Sergey_Shamshur"] = systemUser5;

// Christopher Baggett    
} else if(currentValues["Link to User Table"]["User Name"] == "Christopher Baggett") {
    Map systemUser6 = loadRecord("Account Users", "Email", "christopher@locdoc.net") as Map;
    currentValues["Link to Site Visit"]["Join_Christopher_Baggett"] = systemUser6;

// Josh McVickers    
} else if(currentValues["Link to User Table"]["User Name"] == "Josh McVickers") {
    Map systemUser7 = loadRecord("Account Users", "Email", "josh@locdoc.net") as Map;
    currentValues["Link to Site Visit"]["Join_Josh_McVickers"] = systemUser7;

// James Caul    
} else if(currentValues["Link to User Table"]["User Name"] == "James Caul") {
    Map systemUser8 = loadRecord("Account Users", "Email", "james@locdoc.net") as Map;
    currentValues["Link to Site Visit"]["Join_James_Caul"] = systemUser8;

// Andrew Moody    
} else if(currentValues["Link to User Table"]["User Name"] == "Andrew Moody") {
    Map systemUser9 = loadRecord("Account Users", "Email", "andrew@locdoc.net") as Map;
    currentValues["Link to Site Visit"]["Join_Andrew_Moody"] = systemUser9;

// Andy Tucker    
} else if(currentValues["Link to User Table"]["User Name"] == "Andy Tucker") {
    Map systemUser10 = loadRecord("Account Users", "Email", "andy@locdoc.net") as Map;
    currentValues["Link to Site Visit"]["Join_Andy_Tucker"] = systemUser10;

// Leonard McConniel    
} else if(currentValues["Link to User Table"]["User Name"] == "Leonard McConniel") {
    Map systemUser11 = loadRecord("Account Users", "Email", "leonard@locdoc.net") as Map;
    currentValues["Link to Site Visit"]["Join_Leonard_McConniel"] = systemUser11;

// Mike Tyler    
} else if(currentValues["Link to User Table"]["User Name"] == "Mike Tyler") {
    Map systemUser12 = loadRecord("Account Users", "Email", "Mike@locdoc.net") as Map;
    currentValues["Link to Site Visit"]["Join_Mike_Tyler"] = systemUser12;

// Dylan Tyler    
} else if(currentValues["Link to User Table"]["User Name"] == "Dylan Tyler") {
    Map systemUser13 = loadRecord("Account Users", "Email", "dylan@locdoc.net") as Map;
    currentValues["Link to Site Visit"]["Join_Dylan_Tyler"] = systemUser13;

// Josh Smith    
} else if(currentValues["Link to User Table"]["User Name"] == "Josh Smith") {
    Map systemUser14 = loadRecord("Account Users", "Email", "josh.smith@locdoc.net") as Map;
    currentValues["Link to Site Visit"]["Join_Josh_Smith"] = systemUser14;

// Thomas Heavey    
} else if(currentValues["Link to User Table"]["User Name"] == "Thomas Heavey") {
    Map systemUser15 = loadRecord("Account Users", "Email", "thomas@locdoc.net") as Map;
    currentValues["Link to Site Visit"]["Join_Thomas_Heavey"] = systemUser15;

// Sam Gray   
} else if(currentValues["Link to User Table"]["User Name"] == "Sam Gray") {
    Map systemUser16 = loadRecord("Account Users", "Email", "sam@locdoc.net") as Map;
    currentValues["Link to Site Visit"]["Join_Sam_Gray"] = systemUser16;
    
// Zach Phifer    
} else if(currentValues["Link to User Table"]["User Name"] == "Zach Phifer") {
    Map systemUser17 = loadRecord("Account Users", "Email", "zach@locdoc.net") as Map;
    currentValues["Link to Site Visit"]["Join_Zach_Phifer"] = systemUser17;
    
// Kevan Starr
} else if(currentValues["Link to User Table"]["User Name"] == "Kevan Starr") {
    Map systemUser18 = loadRecord("Account Users", "Email", "kevan@locdoc.net") as Map;
    currentValues["Link to Site Visit"]["Join_Kevan_Starr"] = systemUser18;
    
// Michael Childress    
} else if(currentValues["Link to User Table"]["User Name"] == "Michael Childress") {
    Map systemUser19 = loadRecord("Account Users", "Email", "michael@locdoc.net") as Map;
    currentValues["Link to Site Visit"]["Join_Michael_Childress"] = systemUser19;

// William Gleaves    
} else if(currentValues["Link to User Table"]["User Name"] == "William Gleaves") {
    Map systemUser20 = loadRecord("Account Users", "Email", "william@locdoc.net") as Map;
    currentValues["Link to Site Visit"]["Join_William_Gleaves"] = systemUser20;

// Jordan Boatwright    
} else if(currentValues["Link to User Table"]["User Name"] == "Jordan Boatwright") {
    Map systemUser21 = loadRecord("Account Users", "Email", "jordan@locdoc.net") as Map;
    currentValues["Link to Site Visit"]["Join_Jordan_Boatwright"] = systemUser21;
    
// Eric Spear    
} else if(currentValues["Link to User Table"]["User Name"] == "Eric Spear") {
    Map systemUser22 = loadRecord("Account Users", "Email", "eric@locdoc.net") as Map;
    currentValues["Link to Site Visit"]["Join_Eric_Spear"] = systemUser22;
    
// Scotty Hinson    
} else if(currentValues["Link to User Table"]["User Name"] == "Scotty Hinson") {
    Map systemUser23 = loadRecord("Account Users", "Email", "scotty@locdoc.net") as Map;
    currentValues["Link to Site Visit"]["Join_Scotty_Hinson"] = systemUser23;
}

/*________________________________________________________________________________________/
/ App: Site Visits
/ Table: User - Site Visit Join Table
/ Event: Before Delete
/_____________________________________________________________________________________*/

//_______________________________________________________________________________//
//This app script is the same as the after insert with slight modifications     //
//When a user is deleted from multi-select it removes the user specific field. //
//____________________________________________________________________________//

//currentValues["User Drop Down"] = null;

//Test if current user is added
if(currentValues["Link to User Table"]["User Name"] == "Lucas_Test") {
    //If current user equals "User name" then delete specialty field.
    currentValues["Link to Site Visit"]["Join_Lucas_Test"] = null;

// Dustin Fite    
} else if(currentValues["Link to User Table"]["User Name"] == "Dustin Fite") {
    currentValues["Link to Site Visit"]["Join_Dustin_Fite"] = null;

// Luke Hanson    
} else if(currentValues["Link to User Table"]["User Name"] == "Luke Hanson") {
    currentValues["Link to Site Visit"]["Join_Luke_Hanson"] = null;

// Stephen Clubb
} else if(currentValues["Link to User Table"]["User Name"] == "Stephen Clubb") {
    currentValues["Link to Site Visit"]["Join_Stephen_Clubb"] = null;

// Derek Tyler    
} else if(currentValues["Link to User Table"]["User Name"] == "Derek Tyler") {
    currentValues["Link to Site Visit"]["Join_Derek_Tyler"] = null;

// Sergey Shamshur    
} else if(currentValues["Link to User Table"]["User Name"] == "Sergey Shamshur") {
    currentValues["Link to Site Visit"]["Join_Sergey_Shamshur"] = null;

// Christopher Baggett    
} else if(currentValues["Link to User Table"]["User Name"] == "Christopher Baggett") {
    currentValues["Link to Site Visit"]["Join_Christopher_Baggett"] = null;

// Josh McVickers    
} else if(currentValues["Link to User Table"]["User Name"] == "Josh McVickers") {
    currentValues["Link to Site Visit"]["Join_Josh_McVickers"] = null;

// James Caul    
} else if(currentValues["Link to User Table"]["User Name"] == "James Caul") {
    currentValues["Link to Site Visit"]["Join_James_Caul"] = null;

// Andrew Moody    
} else if(currentValues["Link to User Table"]["User Name"] == "Andrew Moody") {
    currentValues["Link to Site Visit"]["Join_Andrew_Moody"] = null;

// Andy Tucker   
} else if(currentValues["Link to User Table"]["User Name"] == "Andy Tucker") {
    currentValues["Link to Site Visit"]["Join_Andy_Tucker"] = null;

// Leonard McConniel    
} else if(currentValues["Link to User Table"]["User Name"] == "Leonard McConniel") {
    currentValues["Link to Site Visit"]["Join_Leonard_McConniel"] = null;

// Mike Tyler    
} else if(currentValues["Link to User Table"]["User Name"] == "Mike Tyler") {
    currentValues["Link to Site Visit"]["Join_Mike_Tyler"] = null;

// Dylan Tyler    
} else if(currentValues["Link to User Table"]["User Name"] == "Dylan Tyler") {
    currentValues["Link to Site Visit"]["Join_Dylan_Tyler"] = null;

// Josh Smith   
} else if(currentValues["Link to User Table"]["User Name"] == "Josh Smith") {
    currentValues["Link to Site Visit"]["Join_Josh_Smith"] = null;

// Thomas Heavey    
} else if(currentValues["Link to User Table"]["User Name"] == "Thomas Heavey") {
    currentValues["Link to Site Visit"]["Join_Thomas_Heavey"] = null;
    
// Sam Gray
} else if(currentValues["Link to User Table"]["User Name"] == "Sam Gray") {
    currentValues["Link to Site Visit"]["Join_Sam_Gray"] = null;
    
// Zach Phifer
} else if(currentValues["Link to User Table"]["User Name"] == "Zach Phifer") {
    currentValues["Link to Site Visit"]["Join_Zach_Phifer"] = null;
    
// Kevan Starr 
} else if(currentValues["Link to User Table"]["User Name"] == "Kevan Starr") {
    currentValues["Link to Site Visit"]["Join_Kevan_Starr"] = null;

// Michael Childress    
} else if(currentValues["Link to User Table"]["User Name"] == "Michael Childress") {
    currentValues["Link to Site Visit"]["Join_Michael_Childress"] = null;
    
// William Gleaves   
} else if(currentValues["Link to User Table"]["User Name"] == "William Gleaves") {
    currentValues["Link to Site Visit"]["Join_William_Gleaves"] = null;

// Jordan Boatwright   
} else if(currentValues["Link to User Table"]["User Name"] == "Jordan Boatwright") {
    currentValues["Link to Site Visit"]["Join_Jordan_Boatwright"] = null;
    
// Eric Spear   
} else if(currentValues["Link to User Table"]["User Name"] == "Eric Spear") {
    currentValues["Link to Site Visit"]["Join_Eric_Spear"] = null;
    
// Scotty Hinson   
} else if(currentValues["Link to User Table"]["User Name"] == "Scotty Hinson") {
    currentValues["Link to Site Visit"]["Join_Scotty_Hinson"] = null;
    
}

/*________________________________________________________________________________________/
/ App: Bonus Calculator
/ Table: Generate Weekly Reports
/ Event: After Insert
/_____________________________________________________________________________________*/

List usersList = find("User Table Profiles", "Weekly Report", "Yes");
usersList.each {
    Map newReport = addChild "Weekly Report","Link to Generate Weekly Reports",["Date" : currentValues["Date"],
                                          "Application User" : it["Application User"]];
    newReport["Link to User Table"] = it;
}

/*________________________________________________________________________________________/
/ App: Bonus Calculator
/ Table: User Table - Stats Calculator
/ Event: After Update
/_____________________________________________________________________________________*/

//_______________________________________________________________________________//
// This app script is used to calculate weighted averages for employees bonuses //
// In Conjunction with the user table and bonuses table.                       //
//____________________________________________________________________________//

// Use time category for improved date manipulation
import groovy.time.TimeCategory;
// Grab todays date
Date today1 = new Date();
// If the option to generate bonuses for the month has been selected, then run the code!
if((currentValues["Generate Bonus Records"] == "Yes") && (previousValues["Generate Bonus Records"] != "Yes")) {
    // Grab total revenue
    double totRev = currentValues["Total Revenue"] as double;
    // Grab baseline to beat
    double baseLine = currentValues["Baseline"] as double;
    // Calculate bonus pool using profit sharing percentage
    currentValues["Bonus Pool"] = (totRev - baseLine) * (currentValues["Profit Sharing Percentage"] as double);
    // Grab all bonusable employees
    List bonusUsers = getChildren("User Table Profiles", "Link to User Table - Stats Calculator") as List;
    // Initiate TotalProfitability to 0
    double kTP = 0.0 as double;
    // Initiate Total Reliability to 0
    double kTR = 0.0 as double;
    // Initiate Total Tenure to 0
    double kTT = 0.0 as double;
    // Iterate User Values for the month
    bonusUsers.each {
        kTP += it["Profitability"] as double; // SUM up total profitability
        kTR += it["Reliability"] as double; // SUM up total Reliatbility
        Date startDate = it["Start Date"] as Date;
        double duration = (today1 - startDate)/365;
        kTT += duration; // SUM up total Tenure
    }
    // Iterate Users again
    bonusUsers.each { // Calculate each users percentage of the above values
        Date startDate = it["Start Date"] as Date;
        double duration = (today1 - startDate)/365;
        double percTen = duration/kTT;
        double percPro = (it["Profitability"] as double)/kTP;
        double percRel = (it["Reliability"] as double)/kTR;
        // Create bonus record to hold that users monthly bonus
        addChild "Bonus Calculator", "Link to User Table - Stats Calculator", ['Link to User Table Profiles':it,
                                                            'Month':currentValues["Month"],
                                                            'Year':currentValues["Year"],
                                                            'Bonus Pool':currentValues["Bonus Pool"],
                                                            'System User':it["Application User"],
                                                            'Profitability':it["Profitability"],
                                                            'Reliability':it["Reliability"],
                                                            'On Time & Punctual':it["On Time & Punctual"],
                                                            'Willingness to Help':it["Willingness to Help"],
                                                            'Follow Through':it["Follow Through"],
                                                            'Positive Communication':it["Positive Communication"],
                                                            'Independent Worker':it["Independent Worker"],
                                                            'Tenure':duration,
                                                            'Percent Tenure':percTen,
                                                            'Percent Profitability':percPro,
                                                            'Percent Reliability':percRel]
    }
    
    // After creating bonus records, return initial values to user
    bonusUsers.each {
        it["Profitability"] = 5;
        it["Reliability"] = 10;
        it["On Time & Punctual"] = 0;
        it["Willingness to Help"] = 0;
        it["Positive Communication"] = 0;
        it["Follow Through"] = 0;
        it["Independent Worker"] = 0;
        it["Link to User Table - Stats Calculator"] = null;
    }
    // Save some values on the user!
    currentValues["Keep Total Profitability"] = kTP;
    currentValues["Keep Total Reliability"] = kTR;
    currentValues["Keep Total Tenure"] = kTT;
}

/*________________________________________________________________________________________/
/ App: Bonus Calculator
/ Table: User Table Profiles
/ Event: Before Update
/_____________________________________________________________________________________*/

double onTimeAndPunctual = currentValues["On Time & Punctual"] as double;
double willToHelp = currentValues["Willingness to Help"] as double;
double follThrough = currentValues["Follow Through"] as double;
double posComm = currentValues["Positive Communication"] as double;
double indepWorker = currentValues["Independent Worker"] as double;
currentValues["Reliability"] = ((onTimeAndPunctual + willToHelp + follThrough + posComm + indepWorker) / 5.0) as double;
