package com.company;

import java.util.Scanner;

public class Main {

    public static void main(String[] args)  {

        Scanner scanner = new Scanner((System.in));
        String chromeDriverPath = "/usr/local/bin/chromedriver";
        System.out.println("Input chromeDriver path or press enter to use default ("+chromeDriverPath+") :");
        String inputDriverPath = scanner.nextLine();

        if(!inputDriverPath.equals(""))
            chromeDriverPath = inputDriverPath;

        solution sln = new solution(chromeDriverPath);

        sln.run();
    }
}
