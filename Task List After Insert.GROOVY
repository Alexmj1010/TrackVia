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
    currentValues["Link to Locations Openings"]["Cylinder Type"] = currentValues["Cylinder Type"];
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

// If upon creation of a task the user selects to create new general bitting using random 5 pin, run this script!
if((currentValues["New/Existing Bitting"] == "New") && (currentValues["Bitting Type"] == "General Bitting") && (currentValues["Add Type"] == "Choose Available - 5 Pin")) {
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

// If upon creation of a task the user selects to create new general bitting using random 6 pin, run this script!
if((currentValues["New/Existing Bitting"] == "New") && (currentValues["Bitting Type"] == "General Bitting") && (currentValues["Add Type"] == "Choose Available - 6 Pin")) {
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

// If upon creation of a task the user selects to create new combination Lock, run this script!
if((currentValues["New/Existing Bitting"] == "New") && (currentValues["Bitting Type"] == "Combination Lock")) {
    Map newCombo = null;
    newCombo = save "Combination Locks",['Link to Location / Facility' : currentValues["Link to Site Visit"]["Link to Location / Facility"],
                                        'User Combination' : currentValues["User Combination"],
                                        'Programming Combination' : currentValues["Programming Combination"]];
    currentValues["Link to Locations Openings"]["Link to Combination Locks"] = newCombo;
}

// If upon creation of a task the user selects to use existing bitting (in this scope it will show all bitting because the opening is not linked to parent location)
if(currentValues["Link to Locations Openings"] && currentValues["Link to Non Master Keys"]) {
    currentValues["Link to Locations Openings"]["Link to Non Master Keys"] = currentValues["Link to Non Master Keys"];
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
if((currentValues["Quick Add Products / Services"] == "Yes") && (currentValues["Testing"] != "Yes")) {
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
}



// If upon creation of a task the user selects to create new Restricted Key, run this script!







