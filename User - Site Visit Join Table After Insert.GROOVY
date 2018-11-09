//____________________________________________________________//
// This application script is used to set user fields on the //
// Site Visit table using a multi-select.                   //
//_________________________________________________________//

currentValues["User Drop Down"] = currentValues["Link to User Table"]["User Name"];

//Test if user is "User Name"
if(currentValues["Link to User Table"]["User Name"] == "Lucas_Test") {
    //If user, then set site visit user field to xvia application user.
    Map systemUser = loadRecord("Account Users", "Email", "wardl.mobile@gmail.com") as Map;
    currentValues["Link to Site Visit"]["Join_Lucas_Test"] = systemUser;

// Derek Tyler
} else if(currentValues["Link to User Table"]["User Name"] == "Derek Tyler") {
    Map systemUser1 = loadRecord("Account Users", "Email", "derek@locdoc.net") as Map;
    currentValues["Link to Site Visit"]["Join_Derek_Tyler"] = systemUser1;

// Dustin Fite
} else if(currentValues["Link to User Table"]["User Name"] == "Dustin Fite") {
    Map systemUser2 = loadRecord("Account Users", "Email", "dustin@locdoc.net") as Map;
    currentValues["Link to Site Visit"]["Join_Dustin_Fite"] = systemUser2;

// Luke Hanson   
} else if(currentValues["Link to User Table"]["User Name"] == "Luke Hanson") {
    Map systemUser3 = loadRecord("Account Users", "Email", "luke@locdoc.net") as Map;
    currentValues["Link to Site Visit"]["Join_Luke_Hanson"] = systemUser3;

// Stephen Clubb
} else if(currentValues["Link to User Table"]["User Name"] == "Stephen Clubb") {
    Map systemUser4 = loadRecord("Account Users", "Email", "stephen@locdoc.net") as Map;
    currentValues["Link to Site Visit"]["Join_Stephen_Clubb"] = systemUser4;

// Sergey Shamshur    
} else if(currentValues["Link to User Table"]["User Name"] == "Sergey Shamshur") {
    Map systemUser5 = loadRecord("Account Users", "Email", "sergey@locdoc.net") as Map;
    currentValues["Link to Site Visit"]["Join_Sergey_Shamshur"] = systemUser5;

// Christopher Baggett    
} else if(currentValues["Link to User Table"]["User Name"] == "Christopher Baggett") {
    Map systemUser6 = loadRecord("Account Users", "Email", "christopher@locdoc.net") as Map;
    currentValues["Link to Site Visit"]["Join_Christopher_Baggett"] = systemUser6;

// Josh McVickers    
} else if(currentValues["Link to User Table"]["User Name"] == "Josh McVickers") {
    Map systemUser7 = loadRecord("Account Users", "Email", "josh@locdoc.net") as Map;
    currentValues["Link to Site Visit"]["Join_Josh_McVickers"] = systemUser7;

// James Caul    
} else if(currentValues["Link to User Table"]["User Name"] == "James Caul") {
    Map systemUser8 = loadRecord("Account Users", "Email", "james@locdoc.net") as Map;
    currentValues["Link to Site Visit"]["Join_James_Caul"] = systemUser8;

// Andrew Moody    
} else if(currentValues["Link to User Table"]["User Name"] == "Andrew Moody") {
    Map systemUser9 = loadRecord("Account Users", "Email", "andrew@locdoc.net") as Map;
    currentValues["Link to Site Visit"]["Join_Andrew_Moody"] = systemUser9;

// Andy Tucker    
} else if(currentValues["Link to User Table"]["User Name"] == "Andy Tucker") {
    Map systemUser10 = loadRecord("Account Users", "Email", "andy@locdoc.net") as Map;
    currentValues["Link to Site Visit"]["Join_Andy_Tucker"] = systemUser10;

// Leonard McConniel    
} else if(currentValues["Link to User Table"]["User Name"] == "Leonard McConniel") {
    Map systemUser11 = loadRecord("Account Users", "Email", "leonard@locdoc.net") as Map;
    currentValues["Link to Site Visit"]["Join_Leonard_McConniel"] = systemUser11;

// Mike Tyler    
} else if(currentValues["Link to User Table"]["User Name"] == "Mike Tyler") {
    Map systemUser12 = loadRecord("Account Users", "Email", "Mike@locdoc.net") as Map;
    currentValues["Link to Site Visit"]["Join_Mike_Tyler"] = systemUser12;

// Dylan Tyler    
} else if(currentValues["Link to User Table"]["User Name"] == "Dylan Tyler") {
    Map systemUser13 = loadRecord("Account Users", "Email", "dylan@locdoc.net") as Map;
    currentValues["Link to Site Visit"]["Join_Dylan_Tyler"] = systemUser13;

// Josh Smith    
} else if(currentValues["Link to User Table"]["User Name"] == "Josh Smith") {
    Map systemUser14 = loadRecord("Account Users", "Email", "josh.smith@locdoc.net") as Map;
    currentValues["Link to Site Visit"]["Join_Josh_Smith"] = systemUser14;

// Thomas Heavey    
} else if(currentValues["Link to User Table"]["User Name"] == "Thomas Heavey") {
    Map systemUser15 = loadRecord("Account Users", "Email", "thomas@locdoc.net") as Map;
    currentValues["Link to Site Visit"]["Join_Thomas_Heavey"] = systemUser15;

// Sam Gray   
} else if(currentValues["Link to User Table"]["User Name"] == "Sam Gray") {
    Map systemUser16 = loadRecord("Account Users", "Email", "sam@locdoc.net") as Map;
    currentValues["Link to Site Visit"]["Join_Sam_Gray"] = systemUser16;
    
// Zach Phifer    
} else if(currentValues["Link to User Table"]["User Name"] == "Zach Phifer") {
    Map systemUser17 = loadRecord("Account Users", "Email", "zach@locdoc.net") as Map;
    currentValues["Link to Site Visit"]["Join_Zach_Phifer"] = systemUser17;
    
// Kevan Starr
} else if(currentValues["Link to User Table"]["User Name"] == "Kevan Starr") {
    Map systemUser18 = loadRecord("Account Users", "Email", "kevan@locdoc.net") as Map;
    currentValues["Link to Site Visit"]["Join_Kevan_Starr"] = systemUser18;
    
// Michael Childress    
} else if(currentValues["Link to User Table"]["User Name"] == "Michael Childress") {
    Map systemUser19 = loadRecord("Account Users", "Email", "michael@locdoc.net") as Map;
    currentValues["Link to Site Visit"]["Join_Michael_Childress"] = systemUser19;

// William Gleaves    
} else if(currentValues["Link to User Table"]["User Name"] == "William Gleaves") {
    Map systemUser20 = loadRecord("Account Users", "Email", "william@locdoc.net") as Map;
    currentValues["Link to Site Visit"]["Join_William_Gleaves"] = systemUser20;

// Jordan Boatwright    
} else if(currentValues["Link to User Table"]["User Name"] == "Jordan Boatwright") {
    Map systemUser21 = loadRecord("Account Users", "Email", "jordan@locdoc.net") as Map;
    currentValues["Link to Site Visit"]["Join_Jordan_Boatwright"] = systemUser21;
    
// Eric Spear    
} else if(currentValues["Link to User Table"]["User Name"] == "Eric Spear") {
    Map systemUser22 = loadRecord("Account Users", "Email", "eric@locdoc.net") as Map;
    currentValues["Link to Site Visit"]["Join_Eric_Spear"] = systemUser22;
    
// Scotty Hinson    
} else if(currentValues["Link to User Table"]["User Name"] == "Scotty Hinson") {
    Map systemUser23 = loadRecord("Account Users", "Email", "scotty@locdoc.net") as Map;
    currentValues["Link to Site Visit"]["Join_Scotty_Hinson"] = systemUser23;
}







