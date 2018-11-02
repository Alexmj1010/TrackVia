if((currentValues["BIN Ready?"] == "Yes") && (currentValues["Link to Inventory - Sub Location"] != null)) {
    List childRecords = getChildren("Inventory", "Link to Inventory - Sub Location") as List;
    childRecords.each {
        it["Link to Inventory - Sub Location"] = currentValues["Link to Inventory - Sub Location"];
    }
    currentValues["Link to Inventory - Sub Location"] = null;
    currentValues["BIN Ready?"] = "No";
}
