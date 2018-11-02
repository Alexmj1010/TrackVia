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
