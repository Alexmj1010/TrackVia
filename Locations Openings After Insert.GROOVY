/*______________________________________________________________________________________
|  This app script links new openings to a facilities complex, if it exisits            \
|______________________________________________________________________________________*/

// Check if complex exists for parent location.
if(currentValues["Link to Location / Facility"]) {
    if(currentValues["Link to Location / Facility"]["Link to Complex"] != null) {
        // Link opening to complex if it exists.
        currentValues["Link to Complex"] = currentValues["Link to Location / Facility"]["Link to Complex"];
    }
}

