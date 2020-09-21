package com.company;

public class Main {

    public static void main(String[] args)  {

        String chromeDriverPath = "/usr/local/bin/chromedriver" ;
        solution sln = new solution(chromeDriverPath);

        sln.run();
    }
}
