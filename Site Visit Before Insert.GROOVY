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

Map uniqIDRecord = loadRecord "Unique Identifier Generator","For Table","Site Visit";
int uniqID = uniqIDRecord["UniqID"] as int;
currentValues["UniqID"] = uniqID;
uniqID = uniqID + 1;
uniqIDRecord["UniqID"] = uniqID;
