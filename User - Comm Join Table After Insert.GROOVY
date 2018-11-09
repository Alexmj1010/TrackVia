//____________________________________________________________//
// This application script is used to set user fields on the //
// Communication / TO-DO table using a multi-select.        //
//_________________________________________________________//

//Test if user is "User Name"
if(currentValues["Link to User Table"]["User Name"] == "Lucas_Test") {
    //If user, then set site visit user field to xvia application user.
    Map systemUser = loadRecord("Account Users", "Email", "wardl.mobile@gmail.com") as Map;
    currentValues["Link to Communication Notes"]["Join_Lucas_Test"] = systemUser;

// Derek Tyler
} else if(currentValues["Link to User Table"]["User Name"] == "Derek Tyler") {
    Map systemUser1 = loadRecord("Account Users", "Email", "derek@locdoc.net") as Map;
    currentValues["Link to Communication Notes"]["Join_Derek_Tyler"] = systemUser1;

// Dustin Fite
} else if(currentValues["Link to User Table"]["User Name"] == "Dustin Fite") {
    Map systemUser2 = loadRecord("Account Users", "Email", "dustin@locdoc.net") as Map;
    currentValues["Link to Communication Notes"]["Join_Dustin_Fite"] = systemUser2;

// Luke Hanson   
} else if(currentValues["Link to User Table"]["User Name"] == "Luke Hanson") {
    Map systemUser3 = loadRecord("Account Users", "Email", "luke@locdoc.net") as Map;
    currentValues["Link to Communication Notes"]["Join_Luke_Hanson"] = systemUser3;

// Stephen Clubb
} else if(currentValues["Link to User Table"]["User Name"] == "Stephen Clubb") {
    Map systemUser4 = loadRecord("Account Users", "Email", "stephen@locdoc.net") as Map;
    currentValues["Link to Communication Notes"]["Join_Stephen_Clubb"] = systemUser4;

// Sergey Shamshur    
} else if(currentValues["Link to User Table"]["User Name"] == "Sergey Shamshur") {
    Map systemUser5 = loadRecord("Account Users", "Email", "sergey@locdoc.net") as Map;
    currentValues["Link to Communication Notes"]["Join_Sergey_Shamshur"] = systemUser5;

// Christopher Baggett    
} else if(currentValues["Link to User Table"]["User Name"] == "Christopher Baggett") {
    Map systemUser6 = loadRecord("Account Users", "Email", "christopher@locdoc.net") as Map;
    currentValues["Link to Communication Notes"]["Join_Christopher_Baggett"] = systemUser6;

// Josh McVickers    
} else if(currentValues["Link to User Table"]["User Name"] == "Josh McVickers") {
    Map systemUser7 = loadRecord("Account Users", "Email", "josh@locdoc.net") as Map;
    currentValues["Link to Communication Notes"]["Join_Josh_McVickers"] = systemUser7;

// James Caul    
} else if(currentValues["Link to User Table"]["User Name"] == "James Caul") {
    Map systemUser8 = loadRecord("Account Users", "Email", "james@locdoc.net") as Map;
    currentValues["Link to Communication Notes"]["Join_James_Caul"] = systemUser8;

// Andrew Moody    
} else if(currentValues["Link to User Table"]["User Name"] == "Andrew Moody") {
    Map systemUser9 = loadRecord("Account Users", "Email", "andrew@locdoc.net") as Map;
    currentValues["Link to Communication Notes"]["Join_Andrew_Moody"] = systemUser9;

// Andy Tucker    
} else if(currentValues["Link to User Table"]["User Name"] == "Andy Tucker") {
    Map systemUser10 = loadRecord("Account Users", "Email", "andy@locdoc.net") as Map;
    currentValues["Link to Communication Notes"]["Join_Andy_Tucker"] = systemUser10;

// Leonard McConniel    
} else if(currentValues["Link to User Table"]["User Name"] == "Leonard McConniel") {
    Map systemUser11 = loadRecord("Account Users", "Email", "leonard@locdoc.net") as Map;
    currentValues["Link to Communication Notes"]["Join_Leonard_McConniel"] = systemUser11;

// Mike Tyler    
} else if(currentValues["Link to User Table"]["User Name"] == "Mike Tyler") {
    Map systemUser12 = loadRecord("Account Users", "Email", "Mike@locdoc.net") as Map;
    currentValues["Link to Communication Notes"]["Join_Mike_Tyler"] = systemUser12;

// Dylan Tyler    
} else if(currentValues["Link to User Table"]["User Name"] == "Dylan Tyler") {
    Map systemUser13 = loadRecord("Account Users", "Email", "dylan@locdoc.net") as Map;
    currentValues["Link to Communication Notes"]["Join_Dylan_Tyler"] = systemUser13;

// Josh Smith    
} else if(currentValues["Link to User Table"]["User Name"] == "Josh Smith") {
    Map systemUser14 = loadRecord("Account Users", "Email", "josh.smith@locdoc.net") as Map;
    currentValues["Link to Communication Notes"]["Join_Josh_Smith"] = systemUser14;

// Thomas Heavey    
} else if(currentValues["Link to User Table"]["User Name"] == "Thomas Heavey") {
    Map systemUser15 = loadRecord("Account Users", "Email", "thomas@locdoc.net") as Map;
    currentValues["Link to Communication Notes"]["Join_Thomas_Heavey"] = systemUser15;

// Sam Gray   
} else if(currentValues["Link to User Table"]["User Name"] == "Sam Gray") {
    Map systemUser16 = loadRecord("Account Users", "Email", "sam@locdoc.net") as Map;
    currentValues["Link to Communication Notes"]["Join_Sam_Gray"] = systemUser16;
    
// Zach Phifer    
} else if(currentValues["Link to User Table"]["User Name"] == "Zach Phifer") {
    Map systemUser17 = loadRecord("Account Users", "Email", "zach@locdoc.net") as Map;
    currentValues["Link to Communication Notes"]["Join_Zach_Phifer"] = systemUser17;
    
// Kevan Starr
} else if(currentValues["Link to User Table"]["User Name"] == "Kevan Starr") {
    Map systemUser18 = loadRecord("Account Users", "Email", "kevan@locdoc.net") as Map;
    currentValues["Link to Communication Notes"]["Join_Kevan_Starr"] = systemUser18

// Chad Lingafelt
} else if(currentValues["Link to User Table"]["User Name"] == "Chad Lingafelt") {
    Map systemUser19 = loadRecord("Account Users", "Email", "chad@locdoc.net") as Map;
    currentValues["Link to Communication Notes"]["Join_Chad_Lingafelt"] = systemUser19

// Chris Lowery
} else if(currentValues["Link to User Table"]["User Name"] == "Chris Lowery") {
    Map systemUser20 = loadRecord("Account Users", "Email", "chris@locdoc.net") as Map;
    currentValues["Link to Communication Notes"]["Join_Chris_Lowery"] = systemUser20

// Jessica Bennet
} else if(currentValues["Link to User Table"]["User Name"] == "Jessica Bennet") {
    Map systemUser21 = loadRecord("Account Users", "Email", "jessica.bennet@locdoc.net") as Map;
    currentValues["Link to Communication Notes"]["Join_Jessica_Bennet"] = systemUser21

// Allyson Tyler
} else if(currentValues["Link to User Table"]["User Name"] == "Allyson Tyler") {
    Map systemUser22 = loadRecord("Account Users", "Email", "allyson@locdoc.net") as Map;
    currentValues["Link to Communication Notes"]["Join_Allyson_Tyler"] = systemUser22

// McDouglas Perez
} else if(currentValues["Link to User Table"]["User Name"] == "McDouglas Perez") {
    Map systemUser23 = loadRecord("Account Users", "Email", "ar@locdoc.net") as Map;
    currentValues["Link to Communication Notes"]["Join_McDouglas_Perez"] = systemUser23

// Jennifer Lowery
} else if(currentValues["Link to User Table"]["User Name"] == "Jennifer Lowery") {
    Map systemUser24 = loadRecord("Account Users", "Email", "jennifer@locdoc.net") as Map;
    currentValues["Link to Communication Notes"]["Join_Jennifer_Lowery"] = systemUser24

// Levi Gray
} else if(currentValues["Link to User Table"]["User Name"] == "Levi Gray") {
    Map systemUser25 = loadRecord("Account Users", "Email", "levi@locdoc.net") as Map;
    currentValues["Link to Communication Notes"]["Join_Levi_Gray"] = systemUser25

// Nathan Hammer
} else if(currentValues["Link to User Table"]["User Name"] == "Nathan Hammer") {
    Map systemUser26 = loadRecord("Account Users", "Email", "Nathan@locdoc.net") as Map;
    currentValues["Link to Communication Notes"]["Join_Nathan_Hammer"] = systemUser26

// Michael Childress    
} else if(currentValues["Link to User Table"]["User Name"] == "Michael Childress") {
    Map systemUser27 = loadRecord("Account Users", "Email", "Michael@locdoc.net") as Map;
    currentValues["Link to Communication Notes"]["Join_Michael_Childress"] = systemUser27

// William Gleaves    
} else if(currentValues["Link to User Table"]["User Name"] == "William Gleaves") {
    Map systemUser28 = loadRecord("Account Users", "Email", "william@locdoc.net") as Map;
    currentValues["Link to Communication Notes"]["Join_William_Gleaves"] = systemUser28
    
// Jordan Boatwright    
} else if(currentValues["Link to User Table"]["User Name"] == "Jordan Boatwright") {
    Map systemUser29 = loadRecord("Account Users", "Email", "jordan@locdoc.net") as Map;
    currentValues["Link to Communication Notes"]["Join_Jordan_Boatwright"] = systemUser29
    
// Eric Spear    
} else if(currentValues["Link to User Table"]["User Name"] == "Eric Spear") {
    Map systemUser30 = loadRecord("Account Users", "Email", "eric@locdoc.net") as Map;
    currentValues["Link to Communication Notes"]["Join_Eric_Spear"] = systemUser30
    
// Scotty Hinson   
} else if(currentValues["Link to User Table"]["User Name"] == "Scotty Hinson") {
    Map systemUser31 = loadRecord("Account Users", "Email", "scotty@locdoc.net") as Map;
    currentValues["Link to Communication Notes"]["Join_Scotty_Hinson"] = systemUser31

    
    
} 










