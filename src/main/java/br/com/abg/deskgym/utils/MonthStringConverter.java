package br.com.abg.deskgym.utils;

/**
 * Conversor de número de mês para nome de mês.
 * 
 * @author Alvaro
 */
public class MonthStringConverter {
    
    /**
     * Converte o valor inteiro para o nome do mês, contando o 13º.
     *
     * @param month
     *
     * @return
     */
    public String IntegerToStringWith13(final int month){
        String monthString = null;
           
        switch(month){
                case 1: monthString = "Janeiro";
                break;

                case 2: monthString = "Fevereiro";
                break;

                case 3: monthString = "Março";
                break;

                case 4: monthString = "Abril";
                break;

                case 5: monthString = "Maio";
                break;

                case 6: monthString = "Junho";
                break;

                case 7: monthString = "Julho";
                break;

                case 8: monthString = "Agosto";
                break;

                case 9: monthString = "Setembro";
                break;

                case 10: monthString = "Outubro";
                break;

                case 11: monthString = "Novembro";
                break;

                case 12: monthString = "Dezembro";
                break;
                
                case 13: monthString = "13º Salário";
                break;
            }    
        return monthString;
    }

    /**
     * Converte o valor inteiro para o nome do mês.
     *
     * @param month
     *
     * @return
     */
    public String IntegerToString(final int month){
        String monthString = null;
           
        switch(month){
                case 1: monthString = "Janeiro";
                break;

                case 2: monthString = "Fevereiro";
                break;

                case 3: monthString = "Março";
                break;

                case 4: monthString = "Abril";
                break;

                case 5: monthString = "Maio";
                break;

                case 6: monthString = "Junho";
                break;

                case 7: monthString = "Julho";
                break;

                case 8: monthString = "Agosto";
                break;

                case 9: monthString = "Setembro";
                break;

                case 10: monthString = "Outubro";
                break;

                case 11: monthString = "Novembro";
                break;

                case 12: monthString = "Dezembro";
                break;
            }    
        return monthString;
    }

    /**
     * Converte o nome do mês para o valor inteiro, contando o 13º.
     *
     * @param month
     *
     * @return
     */
    public int StringToIntegerWith13(final String month){
        int monthInteger = -1;

        switch(month){
            case "Janeiro": monthInteger = 1;
            break;
            
            case "Fevereiro": monthInteger = 2;
            break;
            
            case "Março": monthInteger = 3;
            break;
            
            case "Abril": monthInteger = 4;
            break;
            
            case "Maio": monthInteger = 5;
            break;
            
            case "Junho": monthInteger = 6;
            break;
            
            case "Julho": monthInteger = 7;
            break;
            
            case "Agosto": monthInteger = 8;
            break;
            
            case "Setembro": monthInteger = 9;
            break;
            
            case "Outubro": monthInteger = 10;
            break;
            
            case "Novembro": monthInteger = 11;
            break;
            
            case "Dezembro": monthInteger = 12;
            break;
            
            case "13º Salário": monthInteger = 13;
            break;
        }     
        return monthInteger;
    }

    /**
     * Converte o nome do mês para o valor inteiro.
     *
     * @param month
     *
     * @return
     */
    public int StringToInteger(final String month){
        int monthInteger = -1;

        switch(month){
            case "Janeiro": monthInteger = 1;
            break;
            
            case "Fevereiro": monthInteger = 2;
            break;
            
            case "Março": monthInteger = 3;
            break;
            
            case "Abril": monthInteger = 4;
            break;
            
            case "Maio": monthInteger = 5;
            break;
            
            case "Junho": monthInteger = 6;
            break;
            
            case "Julho": monthInteger = 7;
            break;
            
            case "Agosto": monthInteger = 8;
            break;
            
            case "Setembro": monthInteger = 9;
            break;
            
            case "Outubro": monthInteger = 10;
            break;
            
            case "Novembro": monthInteger = 11;
            break;
            
            case "Dezembro": monthInteger = 12;
            break;
        }     
        return monthInteger;
    }
}
