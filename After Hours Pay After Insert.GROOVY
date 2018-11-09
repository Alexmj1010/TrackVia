/*______________________________________________________________________________________
| This Application Script is used to set user fields on after hours pay table           \
| It reads a string containing a list of technicians associated with the after hours     |
| Visit.  It then populates a system user field based on that string of users.           |
| This allows users to view their after hours pay from a dashboard view.                 |
|______________________________________________________________________________________*/
 
// Create String containing the names of all the techs assigned to the after hours site visit
String technicians = currentValues["Technician(s) Assigned"] as String;

//Test if user is "User Name"
if(technicians.contains("Lucas_Test")) {
    //If user, then set after hours pay user field to xvia application user.
    Map systemUser = loadRecord("Account Users", "Email", "wardl.mobile@gmail.com") as Map; // Map System User
    currentValues["Join_Lucas_Test"] = systemUser; // Populate System User Field
}

//Sam Gray
if(technicians.contains("Sam Gray")) {
    Map systemUser = loadRecord("Account Users", "Email", "sam@locdoc.net") as Map;
    currentValues["Join_Sam_Gray"] = systemUser;
}

// Derek Tyler
if(technicians.contains("Derek Tyler")) {
    Map systemUser = loadRecord("Account Users", "Email", "derek@locdoc.net") as Map;
    currentValues["Join_Derek_Tyler"] = systemUser;
}

// Dustin Fite
if(technicians.contains("Dustin Fite")) {
    Map systemUser = loadRecord("Account Users", "Email", "dustin@locdoc.net") as Map;
    currentValues["Join_Dustin_Fite"] = systemUser;
}

// Luke Hanson
if(technicians.contains("Luke Hanson")) {
    Map systemUser = loadRecord("Account Users", "Email", "luke@locdoc.net") as Map;
    currentValues["Join_Luke_Hanson"] = systemUser;
}

// Stephen Clubb
if(technicians.contains("Stephen Clubb")) {
    Map systemUser = loadRecord("Account Users", "Email", "stephen@locdoc.net") as Map;
    currentValues["Join_Stephen_Clubb"] = systemUser;
}

// Sergey Shamshur
if(technicians.contains("Sergey Shamshur")) {
    Map systemUser = loadRecord("Account Users", "Email", "sergey@locdoc.net") as Map;
    currentValues["Join_Sergey_Shamshur"] = systemUser;
}

// Christopher Baggett
if(technicians.contains("Christopher Baggett")) {
    Map systemUser = loadRecord("Account Users", "Email", "christopher@locdoc.net") as Map;
    currentValues["Join_Christopher_Baggett"] = systemUser;
}

// Josh McVickers
if(technicians.contains("Josh McVickers")) {
    Map systemUser = loadRecord("Account Users", "Email", "josh@locdoc.net") as Map;
    currentValues["Join_Josh_McVickers"] = systemUser;
}

// James Caul - NO LONGER ACTIVE
if(technicians.contains("James Caul")) {
    Map systemUser = loadRecord("Account Users", "Email", "james@locdoc.net") as Map;
    currentValues["Join_James_Caul"] = systemUser;
}

// Andrew Moody
if(technicians.contains("Andrew Moody")) {
    Map systemUser = loadRecord("Account Users", "Email", "andrew@locdoc.net") as Map;
    currentValues["Join_Andrew_Moody"] = systemUser;
}

// Andy Tucker
if(technicians.contains("Andy Tucker")) {
    Map systemUser = loadRecord("Account Users", "Email", "andy@locdoc.net") as Map;
    currentValues["Join_Andy_Tucker"] = systemUser;
}

// Leonard McConniel
if(technicians.contains("Leonard McConniel")) {
    Map systemUser = loadRecord("Account Users", "Email", "leonard@locdoc.net") as Map;
    currentValues["Join_Leonard_McConniel"] = systemUser;
}

// Mike Tyler
if(technicians.contains("Mike Tyler")) {
    Map systemUser = loadRecord("Account Users", "Email", "Mike@locdoc.net") as Map;
    currentValues["Join_Mike_Tyler"] = systemUser;
}

// Dylan Tyler
if(technicians.contains("Dylan Tyler")) {
    Map systemUser = loadRecord("Account Users", "Email", "dylan@locdoc.net") as Map;
    currentValues["Join_Dylan_Tyler"] = systemUser;
}

// Josh Smith
if(technicians.contains("Josh Smith")) {
    Map systemUser = loadRecord("Account Users", "Email", "josh.smith@locdoc.net") as Map;
    currentValues["Join_Josh_Smith"] = systemUser;
}

// Thomas Heavey
if(technicians.contains("Thomas Heavey")) {
    Map systemUser = loadRecord("Account Users", "Email", "thomas@locdoc.net") as Map;
    currentValues["Join_Thomas_Heavey"] = systemUser;
}

// Zach Phifer
if(technicians.contains("Zach Phifer")) {
    Map systemUser = loadRecord("Account Users", "Email", "zach@locdoc.net") as Map;
    currentValues["Join_Zach_Phifer"] = systemUser;
}

// Michael Childress
if(technicians.contains("Michael Childress")) {
    Map systemUser = loadRecord("Account Users", "Email", "michael@locdoc.net") as Map;
    currentValues["Join_Michael_Childress"] = systemUser;
}

// William Gleaves
if(technicians.contains("William Gleaves")) {
    Map systemUser = loadRecord("Account Users", "Email", "william@locdoc.net") as Map;
    currentValues["Join_William_Gleaves"] = systemUser;
}

// Jordan Boatwright
if(technicians.contains("Jordan Boatwright")) {
    Map systemUser = loadRecord("Account Users", "Email", "jordan@locdoc.net") as Map;
    currentValues["Join_Jordan_Boatwright"] = systemUser;
}
    
// Eric Spear
if(technicians.contains("Eric Spear")) {
    Map systemUser = loadRecord("Account Users", "Email", "eric@locdoc.net") as Map;
    currentValues["Join_Eric_Spear"] = systemUser;
}
    
// Scotty Hinson
if(technicians.contains("Scotty Hinson")) {
    Map systemUser = loadRecord("Account Users", "Email", "scotty@locdoc.net") as Map;
    currentValues["Join_Scotty_Hinson"] = systemUser;
    
}

