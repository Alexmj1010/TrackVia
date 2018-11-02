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

