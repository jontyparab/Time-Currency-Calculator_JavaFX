ALl the images for this project are used from [icons8.com](https://icons8.com)
All rights to their respective owners.

You are free to pull the project and modify it in any way you wish to fine tune it :)
1. To run the project you will require the following Libraries:
    i.  mysqljdbc Driver,
    ii. JavaFX Libraries

2.  (The name of the database is 'test' and the name of the table is countries by default)

    You can create your local database with the following schema:
    Enter all data yourself following this schema:
    
    CREATE TABLE `countries` (
     `ID` int(11) NOT NULL AUTO_INCREMENT,
     `Country` varchar(60) NOT NULL,
     `Capital` varchar(60) NOT NULL,
     `Flag` blob NOT NULL,
     `Currency` varchar(60) NOT NULL,
     PRIMARY KEY (`ID`)
    ) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=latin1
    ![](resources/DatabaseModel.png)

3. The flags displayed in the application should be fetched from the database and not from the img folder.
    ![](resources/Country.png)
4. The currency exchange values are stored in the Currency.java File. Updated values for the currency exchange rates can be made there.
    ![](resources/Currency.png)
    ![](resources/Time.png)
