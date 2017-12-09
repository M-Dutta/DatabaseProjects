LOAD DATA LOCAL 
    INFILE 'C:/Users/Brodie/Desktop/flight_data.text' INTO TABLE flights 
    COLUMNS TERMINATED BY ' ';  ## This should be your delimiter
     ## ...and if text is enclosed, specify here