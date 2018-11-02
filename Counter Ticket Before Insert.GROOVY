/*______________________________________________________________________________________
| Simply set a few default values using this app script.                                \
|______________________________________________________________________________________*/

currentValues["Total Completion Calc"] = 0;
currentValues["Current Completed"] = 0;
currentValues["Invoiced?"] = "No";

Map uniqIDRecord = loadRecord "Unique Identifier Generator","For Table","Counter Ticket";
int uniqID = uniqIDRecord["UniqID"] as int;
currentValues["UniqID"] = uniqID;
uniqID = uniqID + 1;
uniqIDRecord["UniqID"] = uniqID;
