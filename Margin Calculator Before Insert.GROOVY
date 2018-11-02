currentValues["Adjusted Total"] = (currentValues["Quantity"] as double) * (currentValues["Adjusted Cost"] as double) * (1 + (currentValues["Adjusted Markup"] as double));
double temp = (currentValues["Quantity"] as double) * (currentValues["Adjusted Cost"] as double);
currentValues["Calculated Adjusted Margin"] = (currentValues["Adjusted Total"] as double) - temp;
currentValues["Original Total"] = (currentValues["Quantity"] as double) * (currentValues["Link to Product Database"]["AppScript MSRP"] as double);
//currentValues["Original Margin"] = (currentValues["Original Total"] as double) - ((currentValues["Link to Product Database"]["Price (Net")
