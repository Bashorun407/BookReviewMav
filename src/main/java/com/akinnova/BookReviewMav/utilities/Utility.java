package com.akinnova.BookReviewMav.utilities;

import java.util.Random;

public class Utility {

    //Method to generate a book's Serial Number
    public static String generateBookSerialNumber(int len, String bookTitle) {

        String bookRegNumber = ""; //This will contain the book's registration number
        char[] numChar = new char[len]; //Array created to hold a maximum number accepted as len
        Random randomNumber = new Random();
        int x = 0; //Number to accept new random number generated

        for(int i = 0; i < len; i++){
            x = randomNumber.nextInt(9);
            numChar[i] = Integer.toString(x).toCharArray()[0]; //number generated is converted to character type
        }

        //The registration number will contain the first 3 characters of book's title which includes hyphen and numbers generated
        bookRegNumber = bookTitle.substring(0, 3).toUpperCase() + "-" + new String(numChar);

        return bookRegNumber.trim();

    }

    //Method to generate invoice code in Transaction Service layer
    public static String generateInvoiceCode(int len, String username){
        String invoiceCode = ""; //newly generated invoice-code will be stored in this variable
        char[] numChar = new char[len]; //Character array that will hold a maximum of 'len' characters
        Random random = new Random();
        int x = 0; //x will contain new random number generated

        for (int i = 0; i < len; i++){
            x = random.nextInt(1, 6); //random numbers generated will be from (1 - 6)
            numChar[i] = Integer.toString(x).toCharArray()[0];
        }
        //Invoice will be a combination of the first 3-characters of username with randomly generated digits
        invoiceCode = username.substring(0, 2) + new String(numChar);
        return invoiceCode;
    }

    //Method to generate unique identifier in Transaction Service layer
    public static String generateUniqueIdentifier(int len, String username){
        String invoiceCode = ""; //newly generated invoice-code will be stored in this variable
        char[] numChar = new char[len]; //Character array that will hold a maximum of 'len' characters
        Random random = new Random();
        int x = 0; //x will contain new random number generated

        for (int i = 0; i < len; i++){
            x = random.nextInt(1, 6); //random numbers generated will be from (1 - 6)
            numChar[i] = Integer.toString(x).toCharArray()[0];
        }
        //Invoice will be a combination of the first 3-characters of username with randomly generated digits
        invoiceCode = username.substring(0, 2) + new String(numChar);
        return invoiceCode;
    }

    //Method to generate Like in Review Service class
    public static int likeFunction(int like) {
        return like == 1 ? 1 : 0;
    }

    //Method to generate rate in Review Service class
//    public static int rateFunction(int rate){
//        if(rate >= 1  && rate <=5){
//            return rate;
//        }
//        //else return 0
//        else
//            return 0;
//    }

    //Compact form of rateFunction
    public static int rateFunc(int rate){
        return (rate >= 1 && rate <= 5) ? rate : 0;
    }
}
