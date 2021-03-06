Map newContact = null;;
Map newPhoneNumber = currentValues["Link to Phone Numbers"] as Map;
Map newCustomer = currentValues["Link to Customers"] as Map;
Map newLocation = currentValues["Link to Location / Facility"] as Map;


if(currentValues["Contact Exists?"] == "No") { 
    newContact = save "Contacts",['First Name' : currentValues["First Name"],
                                  'Last Name' : currentValues["Last Name"],
                                  'Email' : currentValues["Email"]];
    newPhoneNumber = save "Phone Numbers",['Phone Number' : currentValues["Phone Number"],
                          'Type' : currentValues["Type"],
                          'Link to Contacts' : newContact];
    currentValues["Link to Phone Numbers"] = newPhoneNumber;
    currentValues["Link to Contacts"] = newContact;
}

if(currentValues["Contact Exists?"] == "Yes") {
    newPhoneNumber = currentValues["Link to Phone Numbers"] as Map;
    newContact = currentValues["Link to Phone Numbers"]["Link to Contacts"] as Map;
}

if(currentValues["Location Exists?"] == "No") {
    newLocation = save "Location / Facility",['Facility Name' : currentValues["Facility Name"],
                                              'Facility Address' : currentValues["Facility Address"],
                                              'City' : currentValues["City"],
                                              'State' : currentValues["State NEW"],
                                              'Link to Tax Rates' : currentValues["Link to Tax Rates"],
                                              'Property Type' : currentValues["Property Type"],
                                              'Facility Notes' : currentValues["Facility Notes"]];
    currentValues["Link to Location / Facility"] = newLocation;
}

if(currentValues["Location Exists?"] == "Yes") {
    newLocation = currentValues["Link to Location / Facility"] as Map;
}

if(currentValues["Customer Exists?"] == "No") {
    logger.info("I'm here at customer doesn't exist");
    newCustomer = save "Customers",['Business Name' : currentValues["Business Name"],
                                    'Customer Type' : currentValues["Customer Type"],
                                    'Invoice Reference Number (PO)' : currentValues["PO Number Required?"],
                                    'Taxable?' : currentValues["Taxable?"],
                                    'Account Status' : currentValues["Account Status"],
                                    'Customer Notes' : currentValues["Customer Notes"]];
    currentValues["Link to Customers"] = newCustomer;
}

if(currentValues["Customer Exists?"] == "Yes") {
    logger.error("I'm here at customer does exist");
    newCustomer = currentValues["Link to Customers"] as Map;
}
