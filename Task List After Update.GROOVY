/*_______________________________________________________|
| This app script duplicates tasks.                      |
| It will duplicate the task, then read the taskbuilders |
| attached to the task and duplicate them onto the new   |
| task as well.                                          | 
|______________________________________________________*/

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
                                 'Adjust Markup' : it["Adjust Markup"]];
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
    currentValues["Link to Locations Openings"]["Cylinder Type"] = currentValues["Cylinder Type"];
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
    newBitting = save "Non Master Keys",['Link to Complex' : currentValues["Link to Site Visit"]["Link to Location / Facility"]["Link to Complex"],
                                         'Link to Location / Facility' : currentValues["Link to Site Visit"]["Link to Location / Facility"],
                                         'Link to Products - Keyway List' : currentValues["Link to Products - Keyway List"],
                                         'SKD Cuts (Bitting)' : currentValues["SKD Cuts"],
                                         'Control Key' : currentValues["Control"],
                                         'Master Key' : currentValues["Master"],
                                         'SubMaster' : currentValues["SubMaster"],
                                         'Keycode' : currentValues["Key Code"],
                                         'Suite' : currentValues["Suite Number"]];
    currentValues["Link to Locations Openings"]["Link to Non Master Keys"] = newBitting;
                            
}

// If upon update a task the user selects to create new general bitting using random 5 pin, run this script!
if((previousValues["New/Existing Bitting"] != "New") && (currentValues["New/Existing Bitting"] == "New") && (currentValues["Bitting Type"] == "General Bitting") && (currentValues["Add Type"] == "Choose Available - 5 Pin")) {
    Map newBitting = null;
    currentValues["SKD Cuts"] = currentValues["Link to Available 5 Pin Bitting"]["SKD Cuts"];
    newBitting = save "Non Master Keys",['Link to Complex' : currentValues["Link to Site Visit"]["Link to Location / Facility"]["Link to Complex"],
                                         'Link to Location / Facility' : currentValues["Link to Site Visit"]["Link to Location / Facility"],
                                         'Link to Products - Keyway List' : currentValues["Link to Products - Keyway List"],
                                         'SKD Cuts (Bitting)' : currentValues["SKD Cuts"],
                                         'Control Key' : currentValues["Control"],
                                         'Master Key' : currentValues["Master"],
                                         'SubMaster' : currentValues["SubMaster"],
                                         'Keycode' : currentValues["Key Code"],
                                         'Suite' : currentValues["Suite Number"]];
    currentValues["Link to Locations Openings"]["Link to Non Master Keys"] = newBitting;
}

// If upon update a task the user selects to create new general bitting using random 6 pin, run this script!
if((previousValues["New/Existing Bitting"] != "New") && (currentValues["New/Existing Bitting"] == "New") && (currentValues["Bitting Type"] == "General Bitting") && (currentValues["Add Type"] == "Choose Available - 6 Pin")) {
    Map newBitting = null;
    currentValues["SKD Cuts"] = currentValues["Link to Available 6 Pin Bitting"]["SKD Cuts"];
    newBitting = save "Non Master Keys",['Link to Complex' : currentValues["Link to Site Visit"]["Link to Location / Facility"]["Link to Complex"],
                                         'Link to Location / Facility' : currentValues["Link to Site Visit"]["Link to Location / Facility"],
                                         'Link to Products - Keyway List' : currentValues["Link to Products - Keyway List"],
                                         'SKD Cuts (Bitting)' : currentValues["SKD Cuts"],
                                         'Control Key' : currentValues["Control"],
                                         'Master Key' : currentValues["Master"],
                                         'SubMaster' : currentValues["SubMaster"],
                                         'Keycode' : currentValues["Key Code"],
                                         'Suite' : currentValues["Suite Number"]];
    currentValues["Link to Locations Openings"]["Link to Non Master Keys"] = newBitting;
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
if(currentValues["Link to Locations Openings"] && currentValues["Link to Non Master Keys"] && !previousValues["Link to Non Master Keys"]) {
    currentValues["Link to Locations Openings"]["Link to Non Master Keys"] = currentValues["Link to Non Master Keys"];
}

// If after updating a task the user selects to use existing master key bitting (bitting linked through location on opening)
if(currentValues["Link to Locations Openings"] && currentValues["Link to Master Key Codes"] && !previousValues["Link to Master Key Codes"]) {
    currentValues["Link to Locations Openings"]["Link to Master Key Codes"] = currentValues["Link to Master Key Codes"];
    save "All Bitting", ['Link to Master Key Codes' : currentValues["Link to Master Key Codes"],
                         'Link to Location / Facility' : currentValues["Link to Locations Openings"]["Link to Location / Facility"],
                         'Link to Products - Keyway List' : currentValues["Link to Master Key Codes"]["Link to System IDs"]["Link to Products - Keyway List"],
                         'Bitting Type' : "Master",
                         'Key Code' : currentValues["Link to Master Key Codes"]["Blind Code"],
                         'SKD Cuts' : currentValues["Link to Master Key Codes"]["Bitting"],
                         'System ID' : currentValues["Link to Master Key Codes"]["System ID"]];
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
if((previousValues["Quick Add Products / Services"] != "Yes") && (currentValues["Quick Add Products / Services"] == "Yes") && (currentValues["Testing"] != "Yes")) {
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
}

// If upon creation of a task quick add products and services have been chosen - TESTING 3.0 with inventory !! add them!
if((currentValues["Quick Add Products / Services"] == "Yes") && (currentValues["Testing"] == "Yes")) {
    addChild "Task Builder","Link to Task List",['Quantity (Product)' : currentValues["Quantity (Product)"],
                                          'Quantity (Service)' : currentValues["Quantity (Service)"],
                                          'Link to Inventory' : currentValues["Link to Inventory"],
                                          //'Link to Inventory - Sub Location' : currentValues["Link to Inventory"]["Link to Inventory - Sub Location"],
                                          //'Link to Product Database' : currentValues["Link to Inventory"]["Link to Product Database"],
                                          'Link to Services' : currentValues["Link to Services"],
                                          'Special Instructions' : currentValues["Task Update"],
                                          'Testing' : "Yes"];
    currentValues["Quantity (Product)"] = null;
    currentValues["Quantity (Service)"] = null;
    currentValues["Link to Product Database"] = null;
    currentValues["Link to Services"] = null;
    currentValues["Link to Inventory"] = null;
    currentValues["Task Update"] = null;
    currentValues["Quick Add Products / Services"] = "No";
    currentValues["Testing"] = null;
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





