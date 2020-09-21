package com.company;

import com.google.common.annotations.VisibleForTesting;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.json.simple.JSONObject;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import java.io.FileWriter;
import java.io.IOException;
import java.util.*;
import java.util.concurrent.TimeUnit;

public class solution {

    private ChromeOptions option;
    private WebDriver driver;
    private TimeUnit time = TimeUnit.SECONDS;
    protected List<WebDriver> departmentDrivers;
    protected List<String> departmentName;
    private Map<String,List<Position>> jobPositions;


    private String chromeDriverPath;
    private int departmentSize;
    public solution(String chromeDriverPath) {
        this.chromeDriverPath = chromeDriverPath;
        System.setProperty("webdriver.chrome.driver", chromeDriverPath);
        option = new ChromeOptions();
        //option.addArguments("--headless", "--disable-gpu", "--window-size=1920,1200","--ignore-certificate-errors");
        option.setHeadless(true);
        driver = new ChromeDriver(option);
        driver.get("https://www.cermati.com/karir");


        departmentDrivers = new ArrayList<>();
        departmentName = new ArrayList<>();
        jobPositions = new HashMap<>();


        departmentSize = 0;
    }

    private List<String> scrapeElement(List<WebElement> elements){
        List<String> result = new ArrayList<>();

        for(WebElement el : elements){
            String li = /*el.getAttribute("innerHTML");*/el.getText();
            result.add(li);
        }

        return result;
    }

    // helper function to scrape Position page
    protected Position scrapePosition(WebDriver wd){
        WebElement jobTitleElement = wd.findElement(By.xpath("/html/body/div[2]/div/div/div[1]/main/h1"));
        WebElement locationElement = wd.findElement(By.xpath("/html/body/div[2]/div/div/div[1]/main/ul/li[1]/span"));
        List<WebElement> descriptionElement = wd.findElements(By.xpath("//*[@id=\"st-jobDescription\"]/div[2]/ul/li"));
        List<WebElement> minimumQualificationElement = wd.findElements(By.xpath("//*[@id=\"st-qualifications\"]/div[2]/ul[1]/li"));
        List<WebElement> preferredQualificationElement = wd.findElements(By.xpath("//*[@id=\"st-qualifications\"]/div[2]/ul[2]/li"));
        List<WebElement> sideColumn = wd.findElements(By.xpath("/html/body/div[2]/div/div/div[2]/aside/div"));

        String jobTitle = jobTitleElement.getAttribute("innerHTML");
        String location = /*locationElement.getAttribute("innerHTML");*/locationElement.getText();
        List<String> description = scrapeElement(descriptionElement);
        List<String> qualification = scrapeElement(minimumQualificationElement);
        qualification.addAll(scrapeElement(preferredQualificationElement));
        String jobPoster = "";

        //print
        //System.out.println(sideColumn.size());

        if(sideColumn.size() >= 5){
            WebElement jobPosterElement = wd.findElement(By.xpath("/html/body/div[2]/div/div/div[2]/aside/div[2]/div/div[2]/div/h3"));
            jobPoster = jobPosterElement.getAttribute("innerHTML");
        }

        Position pos = new Position(jobTitle,location,description,qualification,jobPoster);
        return pos;
    }

    // Collect department information
    @VisibleForTesting
    protected void collectDepartmentDrivers() {
        List<WebElement> elements = driver.findElements(By.xpath("//*[@id=\"section-vacancies\"]/div/div[1]/ul/li"));

        for(int i = 1; i <= elements.size();i++){

            WebDriver deptDriver = new ChromeDriver(option);
            deptDriver.get("https://www.cermati.com/karir");
            departmentDrivers.add(deptDriver);

            WebElement el = elements.get(i-1);
            WebElement div = el.findElement(By.xpath("//li["+i+"]/a/div[2]/p"));
            String department = div.getAttribute("innerHTML");
            departmentName.add(department);

            List<Position> positions = new ArrayList<>();
            jobPositions.put(department,positions);

        }
        departmentSize = departmentDrivers.size();
    }

    //access each position of each department
    @VisibleForTesting
    protected void accessPosition(WebDriver wd, int idx) {
        WebElement el = wd.findElement(By.xpath("//*[@id=\"section-vacancies\"]/div/div[1]/ul/li["+idx+"]"));
        el.click();
       // time.sleep(3);

        List<WebElement> elements = driver.findElements(By.xpath("//*[@id=\"tab"+(idx-1)+"\"]/div[2]/div"));
        int elementsSize = elements.size();

        for(WebElement webEl : elements){
            webEl = webEl.findElement(By.tagName("a"));
            String url = webEl.getAttribute("href");
            System.out.println(url);
            System.out.println(idx);
            wd.get(url);

            Position position = scrapePosition(wd);
            String department = departmentName.get(idx-1);

            jobPositions.get(department).add(position);

        }
    }

    @VisibleForTesting
    protected void createJSON(){
        JSONObject json = new JSONObject();
        Gson structuredJson = new GsonBuilder().setPrettyPrinting().create();

        for(String dept : departmentName){
            List<Position> positionList = jobPositions.get(dept);
            List<JSONObject> positionJsonList = new ArrayList<>();

            for(Position pos : positionList){
                JSONObject curPos = new JSONObject();
                curPos.put("title", pos.getTitle());
                curPos.put("location", pos.getLocation());
                curPos.put("description", pos.getDescription());
                curPos.put("qualification", pos.getQualification());
                curPos.put("posted by", pos.getPosted_by());

                positionJsonList.add(curPos);
            }


            json.put(dept,positionJsonList);
        }




        try(FileWriter writer = new FileWriter("solution.json")){
            String jsonString = structuredJson.toJson(json);
            writer.write(jsonString);
            //writer.write(json.toJSONString());
            writer.flush();
        } catch(IOException e)
        {}
        System.out.println(json);
    }

    protected void quitWebDrivers(){
        driver.quit();
        for(WebDriver wd: departmentDrivers){
            wd.quit();
        }
    }
    protected void runDeptDriver() throws InterruptedException {

        for(int i = 1; i <= departmentSize;i++){
            WebDriver wd = departmentDrivers.get(i-1);
            //wd.get("https://www.cermati.com/karir");
            accessPosition(wd,i);
        }

    }
    public void run() throws InterruptedException {
        collectDepartmentDrivers();
        runDeptDriver();
        createJSON();
        quitWebDrivers();

    }

}
