// if linked to a task, then much linking must be performed!!!!
if(currentValues["Link to Task List"]) {
    
    // if task has parent site visit, then link to site visit as well as site visits customer and location
    if(currentValues["Link to Task List"]["Link to Site Visit"]) {
        currentValues["Link to Site Visit"] = currentValues["Link to Task List"]["Link to Site Visit"];
        currentValues["Link to Location / Facility"] = currentValues["Link to Task List"]["Link to Site Visit"]["Link to Location / Facility"];
        currentValues["Link to Customers"] = currentValues["Link to Task List"]["Link to Site Visit"]["Link to Customers"];
    }
    
    // if task has parent quote, same as above.
    if(currentValues["Link to Task List"]["Link to Quote Builder"]) {
        currentValues["Link to Quote Builder"] = currentValues["Link to Task List"]["Link to Quote Builder"];
        currentValues["Link to Location / Facility"] = currentValues["Link to Task List"]["Link to Quote Builder"]["Link to Location / Facility"];
        currentValues["Link to Customers"] = currentValues["Link to Task List"]["Link to Quote Builder"]["Link to Customers"];
    }
    
    // if task has parent counter ticket, then same as above.
    if(currentValues["Link to Task List"]["Link to Counter Ticket"]) {
        currentValues["Link to Counter Ticket"] = currentValues["Link to Task List"]["Link to Counter Ticket"];
        currentValues["Link to Location / Facility"] = currentValues["Link to Task List"]["Link to Counter Ticket"]["Link to Location / Facility"];
        currentValues["Link to Customers"] = currentValues["Link to Task List"]["Link to Counter Ticket"]["Link to Customers"];
    }
}

// if linked to a location, then also link it to the location's customer
if(currentValues["Link to Location / Facility"]) {
    currentValues["Link to Customers"] = currentValues["Link to Location / Facility"]["Link to Customers"];
}

// if linked to a site visit, then link it to the site visits location and customer
if(currentValues["Link to Site Visit"]) {
    currentValues["Link to Customers"] = currentValues["Link to Site Visit"]["Link to Customers"];
    currentValues["Link to Location / Facility"] = currentValues["Link to Site Visit"]["Link to Location / Facility"];
}

// if linked to a quote, then also link it to the quotes location and customer
if(currentValues["Link to Quote Builder"]) {
    currentValues["Link to Customers"] = currentValues["Link to Quote Builder"]["Link to Customers"];
    currentValues["Link to Location / Facility"] = currentValues["Link to Quote Builder"]["Link to Location / Facility"];
}

// if linked to a counter ticket, then also link it to the counter tickets customer and location / facility
if(currentValues["Link to Counter Ticket"]) {
    currentValues["Link to Customers"] = currentValues["Link to Counter Ticket"]["Link to Customers"];
    currentValues["Link to Location / Facility"] = currentValues["Link to Counter Ticket"]["Link to Location / Facility"];
}
