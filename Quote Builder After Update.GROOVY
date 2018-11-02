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
