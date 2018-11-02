/*______________________________________________________________________________________
| This app script runs every time a new combination lock is added to the table.         |
| Its purpose is to simply copy the record to the all bitting table.                    |
|______________________________________________________________________________________*/

addChild "All Bitting", "Link to Combination Locks", ['Bitting Type' : "Combination",
                                                    'Door' : currentValues["Door"],
                                                    'Suite' : currentValues["Suite"],
                                                    'Tech Link' : currentValues["Created By User"],
                                                    'Notes' : currentValues["Notes"],
                                                    'User Combination' : currentValues["User Combination"],
                                                    'Programming Combination' : currentValues["Programming Combination"],
                                                    'Link to Location / Facility' : currentValues["Link to Location / Facility"]];
