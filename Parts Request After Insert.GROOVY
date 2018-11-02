/*______________________________________________________________________________________
| This app script checks parts requests table links to ensure it gets linked properly   \
| regardless of where the parts request is added from.  It also generates an "Empty"     |
| Task Builder that will inevitably hold the part that is requested.  Then it updates    |
| the grandparent ticket status, and if a site visit, number ordered.                    |
|______________________________________________________________________________________*/
                     
// Check if added from Task Builder (Bad news) - if so, link to task list
if(currentValues["Link to Task List"] == null) {
    // Create link if it does not exist
    currentValues["Link to Task List"] = currentValues["Link to Task Builder"]["Link to Task List"];
    // Shows that task builder has parts request child
    currentValues["Link to Task Builder"]["Parts Request"] = 1; 
}

// If Link to task list does exist (good news it was added from proper spot!)
if(currentValues["Link to Task List"]) {
    
    // Map "No Service" record from service table
    Map service = loadRecord "Services","Service","NO SERVICE";
    // Map "No Product" record from product database table
    Map product = loadRecord "Product Database","Common Name","NO PRODUCT";
    // Map "NO PRODUCT" record from Inventory
    Map invRec = loadRecord "Inventory","Identifier","LK00 Warehouse - _NO PRODUCT";
    // Create "Empty" Task builder with no product and no service
    Map tb = save "Task Builder",['Link to Task List' : currentValues["Link to Task List"],
                     'Link to Services' : service, // no service
                     'Link to Product Database' : product, // no product
                     'Link to Inventory' : invRec, // no product INV Place holder
                     'Quantity (Product)' : currentValues["Quantity"],
                     'Quantity (Service)' : 0,
                     'Parts Request' : 1] as Map; // denotes child parts request exists
    // makes the task builder just created the parent of this parts request
    currentValues["Link to Task Builder"] = tb;
    
    // If Grand Parent is Site Visit
    if(currentValues["Link to Task List"]["Link to Site Visit"]) { 
        // Change status to waiting
        currentValues["Link to Task List"]["Link to Site Visit"]["Status"] = "Waiting";
        // Change Tag to Purchasing
        currentValues["Link to Task List"]["Link to Site Visit"]["Tag"] = "Purchasing";
        // Update grandparent site visit's parts request percent ordered values
        int x = currentValues["Link to Task List"]["Link to Site Visit"]["AppScript Number Parts Request"] as int;
        currentValues["Link to Task List"]["Link to Site Visit"]["AppScript Number Parts Request"] = x + 1;
        
    // Change status of Counter Ticket if grandparent
    } else if(currentValues["Link to Task List"]["Link to Counter Ticket"] != null) { // If Grand Parent is Counter Ticket
        currentValues["Link to Task List"]["Link to Counter Ticket"]["Status"] = "Parts Ordered";
    }
}

