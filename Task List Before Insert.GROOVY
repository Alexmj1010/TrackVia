// Set Default Values for Inprogress and Completed so far (on task list)
currentValues["Progress"] = "Not Started";
currentValues["CompletedSoFar"] = 0;
currentValues["Duplicate How Many Times?"] = 0;

// If a product is not selected, pre populate field with NO PRODUCT
if(currentValues["Link to Product Database"] == null) {
    Map product = loadRecord "Product Database","Common Name","NO PRODUCT";
    currentValues["Link to Product Database"] = product;
}

// If a service is not selected, pre populate field with NO SERVICE
if(currentValues["Link to Services"] == null) {
    Map service = loadRecord "Services","Service","NO SERVICE";
    currentValues["Link to Services"] = service;
}

// If Inventory is not selected, pre populate field with NO PRODUCT
    /////ADD THIS///// BEFORE LAUNCH!!!

// If Quantity (Product) is left blank, pre populate 0
if(currentValues["Quantity (Product)"] == null) {
    currentValues["Quantity (Product)"] = 0;
}

// If Quantity (Service) is left blank, pre populate 0
if(currentValues["Quantity (Service)"] == null) {
    currentValues["Quantity (Service)"] = 0;
}

if(currentValues["Opening Name"]) {
    String str = currentValues["Opening Name"] as String;
    str = str.replace(",", "");
    currentValues["Opening Name"] = str;
}

if(currentValues["Link to Site Visit Tasks"]) {
    currentValues["Estimated Task Length"] = currentValues["Link to Site Visit Tasks"]["Estimated Completion Time (Minutes)"];
}
