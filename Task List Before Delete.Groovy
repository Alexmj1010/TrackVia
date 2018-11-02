/*___________________________________________________________________/
| This app script handles deletion of tasks.                         |
| When a task is deleted, the current completion weight needs to be  |
| subtracted from the parent current total completion.  Also, when   |
| a task is deleted, the total completion calc needs to go down 3.   |
|__________________________________________________________________*/

// Instantiate variable completedSoFar to current value of task completedness.
double completedSoFar = currentValues["CompletedSoFar"] as double;
// Instatiate remaining variables to zero.
double completed = 0;
double totalCompletion = 0; 

// Depending on parent type, perform calculated subtractions outline in header.

// If parent is a site visit.
if(currentValues["Link to Site Visit"]) {
    // Update completion percent
    completed = currentValues["Link to Site Visit"]["Current Completed"] as double;
    totalCompletion = currentValues["Link to Site Visit"]["Total Completion Calc"] as double;
    // Apply updates
    currentValues["Link to Site Visit"]["Total Completion Calc"] = totalCompletion - 3;
    currentValues["Link to Site Visit"]["Current Completed"] = completed - completedSoFar;

// If parent is a counter Ticket.
} else if(currentValues["Link to Counter Ticket"]) {
    // Update Completion percent
    completed = currentValues["Link to Counter Ticket"]["Current Completed"] as double;
    totalCompletion = currentValues["Link to Counter Ticket"]["Total Completion Calc"] as double;
    // Apply Updates
    currentValues["Link to Counter Ticket"]["Total Completion Calc"] = totalCompletion - 3;
    currentValues["Link to Counter Ticket"]["Current Completed"] = completed - completedSoFar;

// If parent is a Quote
} else if(currentValues["Link to Quote Builder"]) {
    totalCompletion = currentValues["Link to Quote Builder"]["Total Completion Calc"] as double;
    currentValues["Link to Quote Builder"]["Total Completion Calc"] = totalCompletion - 3;
}




