package com.company;

import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import static org.testng.Assert.*;

public class solutionTest {

    private solution sol;
    @BeforeTest
    public void beforeTest(){
        String chromeDriverPath = "/usr/local/bin/chromedriver" ;
        sol = new solution(chromeDriverPath);
        sol.collectDepartmentDrivers();
    }

    @AfterTest
    public void afterTest(){
        sol.createJSON();
        sol.quitWebDrivers();
    }

    @Test
    public void engineeringDepartment(){
        WebDriver wd = sol.departmentDrivers.get(0);
        sol.accessPosition(wd,1);
    }

    @Test
    public void marketingDepartment(){
        WebDriver wd = sol.departmentDrivers.get(1);
        sol.accessPosition(wd,2);
    }

    @Test
    public void productDepartment(){
        WebDriver wd = sol.departmentDrivers.get(2);
        sol.accessPosition(wd,3);
    }

    @Test
    public void businessDevelopmentDepartment(){
        WebDriver wd = sol.departmentDrivers.get(3);
        sol.accessPosition(wd,4);
    }

    @Test
    public void bussinessOperationDepartment(){
        WebDriver wd = sol.departmentDrivers.get(4);
        sol.accessPosition(wd,5);
    }

    @Test
    public void peopleOpsDepartment(){
        WebDriver wd = sol.departmentDrivers.get(5);
        sol.accessPosition(wd,6);
    }

}