/*______________________________________________________________________________________
|  This AppScript sets some choice default values when a new product is added to DB     \
|______________________________________________________________________________________*/

currentValues["QBO_Account"] = "Parts/Sales";
currentValues["QBO_Type"] = "Noninventory";
currentValues["QBO_Taxable"] = "Yes";
currentValues["QBO_Expense Account"] = "Supplies - Cost of Goods Sold";
// Set default value of QBO_Ref_ID to 99999 to avoid null reference
currentValues["QBO_Ref_ID"] = "99999";
currentValues["Quantity_Reserved"] = 0;
currentValues["Quantity_On_Hand"] = 0;
currentValues["Quantity_Available"] = 0;
currentValues["Global Buffer Quantity"] = 0;
String numUp = "";
int pastVal = currentValues["Link to Products - Manufacturer Prefix"]["Next Number (Automated)"] as int;
numUp = numUp + pastVal;
currentValues["Number"] = numUp;
pastVal = pastVal + 1;

currentValues["Link to Products - Manufacturer Prefix"]["Next Number (Automated)"] = pastVal;
