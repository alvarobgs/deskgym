package br.com.abg.deskgym.enums;

/**
 * Enum para sexo.
 * 
 * @author Alvaro
 */
public enum Gender {

    /**
     * Indefinido.
     */
    UNDEFINED("Indefinido"),

    /**
     * Homem.
     */
    MALE("Masculino"),

    /**
     * Mulher.
     */
    FEMALE("Feminimo");

    /**
     * Enum em string.
     */
    private final String gender;
    
    /**
     * Construtor com o parâmetro.
     */
    private Gender(final String gender) {
        this.gender = gender;
    }
    
    @Override
    public String toString() {
        return this.gender;
    }

    /**
     * Retorna o enum de acordo com o valor em string
     *
     * @param genderString
     * @return
     */
    public static Gender toEnum(final String genderString) {
        switch (genderString) {
            case "Masculino":
                return Gender.MALE;
            case "Feminino":
                return Gender.FEMALE;
            default:
                return Gender.UNDEFINED;
        }
    }
}
