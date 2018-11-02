/*______________________________________________________________________________________
|  This app script links new bitting to a facilities complex, if it exists            \
|______________________________________________________________________________________*/

// Check if parent facility has a complex
if(currentValues["Link to Location / Facility"]["Link to Complex"] != null) {
    // If it does have a complex, link the bitting to the complex
    currentValues["Link to Complex"] = currentValues["Link to Location / Facility"]["Link to Complex"];
}

addChild "All Bitting", "Link to Non Master Keys", ['Bitting Type' : "General",
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
                                                    
                                                    
