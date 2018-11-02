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
