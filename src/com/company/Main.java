package com.company;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import java.util.concurrent.TimeUnit;


public class Main {

    public static void main(String[] args) throws InterruptedException {
	// write your code here

        String chromeDriverPath = "/usr/local/bin/chromedriver" ;
        solution sln = new solution(chromeDriverPath);

        //WebElement el1 = driver.findElement(By.xpath("//*[@id=\"section-vacancies\"]/div/h2[1]"));
        //System.out.println(el1.getAttribute("innerHTML"));

        //sln.run();
        System.out.println("done");
    }
}
