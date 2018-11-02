/*______________________________________________________________________________________
| This appscript set some default values.  Namely, values relevant to percent complete  \
|______________________________________________________________________________________*/

// For Percent complete fields when converted to site visit
currentValues["Total Completion Calc"] = 0;
// Track if it has been converted to a site visit or not
currentValues["SiteVisitGenerated"] = 0;

Map uniqIDRecord = loadRecord "Unique Identifier Generator","For Table","Quote Builder";
int uniqID = uniqIDRecord["UniqID"] as int;
currentValues["UniqID"] = uniqID;
uniqID = uniqID + 1;
uniqIDRecord["UniqID"] = uniqID;
