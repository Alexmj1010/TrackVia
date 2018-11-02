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
