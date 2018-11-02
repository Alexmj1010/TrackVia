//_______________________________________________________________________________//
// This app script is used to calculate weighted averages for employees bonuses //
// In Conjunction with the user table and bonuses table.                       //
//____________________________________________________________________________//

// Use time category for improved date manipulation
import groovy.time.TimeCategory;
// Grab todays date
Date today1 = new Date();
// If the option to generate bonuses for the month has been selected, then run the code!
if((currentValues["Generate Bonus Records"] == "Yes") && (previousValues["Generate Bonus Records"] != "Yes")) {
    // Grab total revenue
    double totRev = currentValues["Total Revenue"] as double;
    // Grab baseline to beat
    double baseLine = currentValues["Baseline"] as double;
    // Calculate bonus pool using profit sharing percentage
    currentValues["Bonus Pool"] = (totRev - baseLine) * (currentValues["Profit Sharing Percentage"] as double);
    // Grab all bonusable employees
    List bonusUsers = getChildren("User Table", "Link to User Table - Stats Calculator") as List;
    // Initiate TotalProfitability to 0
    double kTP = 0.0 as double;
    // Initiate Total Reliability to 0
    double kTR = 0.0 as double;
    // Initiate Total Tenure to 0
    double kTT = 0.0 as double;
    // Iterate User Values for the month
    bonusUsers.each {
        kTP += it["Profitability"] as double; // SUM up total profitability
        kTR += it["Reliability"] as double; // SUM up total Reliatbility
        Date startDate = it["Start Date"] as Date;
        double duration = (today1 - startDate)/365;
        kTT += duration; // SUM up total Tenure
    }
    // Iterate Users again
    bonusUsers.each { // Calculate each users percentage of the above values
        Date startDate = it["Start Date"] as Date;
        double duration = (today1 - startDate)/365;
        double percTen = duration/kTT;
        double percPro = (it["Profitability"] as double)/kTP;
        double percRel = (it["Reliability"] as double)/kTR;
        // Create bonus record to hold that users monthly bonus
        addChild "Bonus Calculator", "Link to User Table - Stats Calculator", ['Link to User Table':it,
                                                            'Month':currentValues["Month"],
                                                            'Year':currentValues["Year"],
                                                            'Bonus Pool':currentValues["Bonus Pool"],
                                                            'System User':it["Application User"],
                                                            'Profitability':it["Profitability"],
                                                            'Reliability':it["Reliability"],
                                                            'Tenure':duration,
                                                            'Percent Tenure':percTen,
                                                            'Percent Profitability':percPro,
                                                            'Percent Reliability':percRel]
    }
    
    // After creating bonus records, return initial values to user
    bonusUsers.each {
        it["Profitability"] = 5;
        it["Reliability"] = 10;
        it["Link to User Table - Stats Calculator"] = null;
    }
    // Save some values on the user!
    currentValues["Keep Total Profitability"] = kTP;
    currentValues["Keep Total Reliability"] = kTR;
    currentValues["Keep Total Tenure"] = kTT;
}
