/*_______________________________________________________|
| This app script does one thing.                        |
| Using business logic it updates the parent Ticket      |
| completed so far values to match the progress of the   |
| child task.                                            | 
|______________________________________________________*/

def runIt() {

//Instantiate Variables to either 0 or current values

//prevVal saves the previous "wait" of task completedness before updating
//This is used in cases where a task goes from weight 1 to 3 or 0 to 2 or any interval
//greater than 1.
double compVal = 0;
double prevVal = previousValues["CompletedSoFar"] as double;
double currentTotalCompleted = 0;

if(currentValues["Quick Add Products / Services"] == "Yes") {
if(currentValues["Link to Product Database"] == null) {
    Map product = loadRecord "Product Database","Common Name","NO PRODUCT";
    currentValues["Link to Product Database"] = product;
}
if(currentValues["Link to Services"] == null) {
    Map service = loadRecord "Services","Service","NO SERVICE";
    currentValues["Link to Services"] = service;
}
if(currentValues["Quantity (Product)"] == null) {
    currentValues["Quantity (Product)"] = 0;
}
if(currentValues["Quantity (Service)"] == null) {
    currentValues["Quantity (Service)"] = 0;
}
}



//Set weight of compVal based on progress selection.
if(currentValues["Progress"] == "Not Started") {
    compVal = 0; // value associated with "Not Started"
} else if(currentValues["Progress"] == "In Progress") {
    compVal = 1; // value associated with "In Progress"
} else if(currentValues["Progress"] == "Waiting") {
    compVal = 2; // value associated with "waiting"
} else if(currentValues["Progress"] == "Completed") {
    compVal = 3; // value associated with "Completed"
} else if(currentValues["Progress"] == "Cancelled") {
    compVal = 3;
}

//if parent is a site visit.
if(currentValues["Link to Site Visit"]) {
    //sets currentTotalCompleted to the combined weight of all completed tasks.
    currentTotalCompleted = currentValues["Link to Site Visit"]["Current Completed"] as double;
    //sets task level current completed to weight assigned by progress from above section.
    currentValues["CompletedSoFar"] = compVal;
    //updates parent ticket to new current completed total
    currentValues["Link to Site Visit"]["Current Completed"] = currentTotalCompleted - prevVal + compVal;

//if parent is a counter ticket.
} else if(currentValues["Link to Counter Ticket"]) {
    currentTotalCompleted = currentValues["Link to Counter Ticket"]["Current Completed"] as double;
    currentValues["CompletedSoFar"] = compVal;
    currentValues["Link to Counter Ticket"]["Current Completed"] = currentTotalCompleted - prevVal + compVal;
}
}


if(previousValues["Opening Name"] != currentValues["Opening Name"]) {
    String str = currentValues["Opening Name"] as String;
    str = str.replace(",", "");
    currentValues["Opening Name"] = str;
}

if(previousValues["Link to Site Visit Tasks"] != currentValues["Link to Site Visit Tasks"]) {
    currentValues["Estimated Task Length"] = currentValues["Link to Site Visit Tasks"]["Estimated Completion Time (Minutes)"];
}

runIt();





