# seleniumHeadlessCermatiWebsite

Web scrapping of https://www.cermati.com/karir to get all of the position 
posted at the website and convert the information into JSON file. The program
will collect information of job title, location, job description ,qualification
(minimum & preferred) , and posted by (if listed). This program is created
based on Java with Selenium Webdriver and run with ChromeDriver (headless).
JSON formatters are used to create and structure JSON object using JSON simple
and Gson to help create well structured JSON object.

Note:
to run this program path to chrome driver might need to be adjusted depending
of the Operating System used. This program is created in Mac OS and for most
cases the chrome driver is located at /usr/local/bin/chromedriver. Some
adjustment need to be done if chrome driver is located at different location
or the program is run with different operating system.