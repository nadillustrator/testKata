package com.nadin;

import java.io.IOException;
import java.util.Scanner;

public class Main {
    static int a;
    static String sign;
    static int b;
    static String[] romanNumbers = {"I", "II", "III", "IV", "V", "VI", "VII", "VIII", "IX", "X"};
    static String[] arabicNumbers = {"1", "2", "3", "4", "5", "6", "7", "8", "9", "10"};
    static String[] signs = {"+", "-", "*", "/"};

    public static void main(String[] args) throws IOException {
        Scanner scan = new Scanner(System.in);
        String temp = scan.nextLine();
        System.out.println(calc(temp));
    }

    public static String calc(String input) throws IOException {
        int indexOfSign = getIndexOfSign(input);
        
        outOfCharacterRangeException(indexOfSign);
        
        String[] operands = getOperands(input, indexOfSign);

        int areBothArabicOrRoman = defineAndConvertNumberSystem(operands);

        differentNumbersSystemException(areBothArabicOrRoman);
        
        convertStringToInt(operands);

        outOfDiapasonFrom1To10Exception();

        int res = getArithmeticOperations();

        return isAnswerIsRoman(areBothArabicOrRoman, res);
    }

    private static int getIndexOfSign(String input) {
        int temp = -1;
        int indexOfSign = -1;
        for (int i = 0; i < signs.length; i++) {
            temp = input.indexOf(signs[i]);    //присваивает переменной temp индекс знака из строки
            if(temp >= 0){
                indexOfSign = i;               //присваивает переменной indexOfSign индекс соответствующего знака из массива signs
                i = signs.length;
            }
        }
        return indexOfSign;
    }

    private static void outOfCharacterRangeException(int indexOfSign) {
        if (indexOfSign == -1){
            throw new IndexOutOfBoundsException("Вы ввели недопустимый знак. Допустимые знаки: +, -, *, /");
        }
    }

    private static String[] getOperands(String input, int indexOfSign) {
        sign = signs[indexOfSign];
        String[] operands = input.split(String.valueOf("\\" + sign)); //экранирование для + и *. Иначе dangling meta character '+' near index 0 replaceall
        operands[0] = operands[0].trim();           //обрезка пробелов, если пользователь случайно ввел
        operands[1] = operands[1].trim();
        return operands;
    }

    private static int defineAndConvertNumberSystem(String[] operands) {
        int areBothArabicOrRoman = 0;    //если после выполнения цикла переменная = 0 - то арабские, если 2 - то римские
        for (int i = 0; i < operands.length; i++) {
            for (int j = 0; j < romanNumbers.length; j++) {           //перевод римских чисел в арабские
                if(operands[i].equals(romanNumbers[j])){
                    operands[i] = arabicNumbers[j];
                    areBothArabicOrRoman++;
                }
            }
        }
        return areBothArabicOrRoman;
    }

    private static void differentNumbersSystemException(int areBothArabicOrRoman) throws IOException {
        if(areBothArabicOrRoman ==1){
            throw new IOException("Вы ввели числа из разных систем счисления. Введите только арабские или только римские числа.");
        }
    }

    private static void convertStringToInt(String[] operands) {
        a = Integer.parseInt(operands[0]);
        b = Integer.parseInt(operands[1]);
    }

    private static void outOfDiapasonFrom1To10Exception() throws IOException {
        if(a<1 || b<1 || a>10 || b>10){           
            throw new IOException("Введенные значения не входят в допустимый диапазон от 1 до 10.");
        }
    }

    private static int getArithmeticOperations() {
        int res = 0;
        if(sign.equals("+")){
            res = a + b;
        }else if(sign.equals("-")){
            res = a - b;
        }else if(sign.equals("*")){
            res = a * b;
        }else if(sign.equals("/")){
            res = a / b;
        }
        return res;
    }

    public static String arabicToRoman(int num) throws IOException {
        if(num<=0){
            throw new IOException("В римской системе нет отрицательных чисел и 0");
        }
        String[] romanNumbers = new String[] {"C", "XC", "L", "XL", "X", "IX", "V", "IV", "I" };
        int[] arabicNumbers = new int[] {100, 90, 50, 40, 10, 9, 5, 4, 1};

        StringBuilder result = new StringBuilder();
        int count = 0;

        while(count < romanNumbers.length) {
            while(num >= arabicNumbers[count]) {
                int count2 = num / arabicNumbers[count];
                num = num % arabicNumbers[count];
                for(int i=0; i<count2; i++) {
                    result.append(romanNumbers[count]);
                }
            }
            count++;
        }
        return result.toString();
    }

    private static String isAnswerIsRoman(int areBothArabicOrRoman, int res) throws IOException {
        String result = null;
        if (areBothArabicOrRoman == 2){
            result = arabicToRoman(res);

        }else{
            result = "" + res;
        }
        return result;
    }

}
