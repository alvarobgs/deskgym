package br.com.abg.deskgym.utils;

import java.time.LocalDate;


public class DateToStringConverter {
   
    /**
     * Converte a data de aniversário de string para localdate.
     *
     * @param birthDay
     *
     * @return
     */
    @Deprecated
    public static LocalDate convertBirthDay(final String birthDay){
        int dia = 0;
        int mes = 0;
        int ano = 0;
        
        //CONVERTE O DIA
        if(birthDay.charAt(2) == '/'){
            int dezena = Integer.parseInt(String.valueOf(birthDay.charAt(0))) * 10; 
            int unidade = Integer.parseInt(String.valueOf(birthDay.charAt(1)));
            dia = dezena + unidade;
        } else if (birthDay.charAt(1) == '/' ){
            dia = Integer.parseInt(String.valueOf(birthDay.charAt(0)));
        }
        
        //CONVERTE O MÊS
        for(int i=0;i<birthDay.length()-1;i++){
            if(birthDay.charAt(i) == '/') {
                if(birthDay.charAt(i+2) == '/'){
                    mes = Integer.parseInt(String.valueOf(birthDay.charAt(i+1)));
                    break;
                } else if (birthDay.charAt(i+3) == '/') {
                    int dezena = Integer.parseInt(String.valueOf(birthDay.charAt(i+1))) * 10;
                    int unidade = Integer.parseInt(String.valueOf(birthDay.charAt(i+2)));
                    mes = dezena + unidade;
                    break;
                }
            }
        }
        
        //CONVERTE O ANO
        int barras = 0;
        for(int i = 0;i<birthDay.length()-1;i++){
            if(birthDay.charAt(i) == '/'){
                barras++;
            }
            if(barras == 2){
                if(birthDay.length()-1 - i == 4){
                    int milhar = Integer.parseInt(String.valueOf(birthDay.charAt(i+1))) * 1000;
                    int centena = Integer.parseInt(String.valueOf(birthDay.charAt(i+2))) * 100;
                    int dezena =  Integer.parseInt(String.valueOf(birthDay.charAt(i+3))) * 10;       
                    int unidade = Integer.parseInt(String.valueOf(birthDay.charAt(i+4)));
                    ano = milhar + centena + dezena + unidade;
                    
                }
                else if (birthDay.length()-1 - i == 2) {
                    
                    int dezena = Integer.parseInt(String.valueOf(birthDay.charAt(i+1))) * 10;
                    int unidade = Integer.parseInt(String.valueOf(birthDay.charAt(i+2)));
                    ano = 1900 + dezena + unidade;
                }
                break;
            }
                
        }

        return LocalDate.of(ano, mes, dia);
    
    }
    

}
