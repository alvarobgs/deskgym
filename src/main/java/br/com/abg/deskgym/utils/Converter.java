package br.com.abg.deskgym.utils;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Date;

import br.com.abg.deskgym.session.Messages;


public class Converter {
   
    /**
     * Converte um valor monetário em string para bigdecimal
	 *
	 * @param value
	 *
	 * @return
     */
    public static BigDecimal convertMoneyString(final String value) throws NumberFormatException {
    	if (!Validator.isEmpty(value)) {
			if (value.contains(".") && value.contains(",")) {
				throw new NumberFormatException(Messages.MONEY_CONVERT_ERROR);
			}

			if (value.split(".").length > 2) {
				throw new NumberFormatException(Messages.MONEY_CONVERT_ERROR);
			}

			if (value.contains(",")) {
				value.replace(",", ".");
			}
			final Integer valueInt = Integer.parseInt(value);

			return new BigDecimal(valueInt);
		}
		return null;
    }

	/**
	 * Converte um valor monetário em bigdecimal para string
	 *
	 * @param value
	 *
	 * @return
	 */
	public static String convertStringMoney(final BigDecimal value) {
		if (!Validator.isEmpty(value)) {
			final String converted = value.toString();
			converted.replace(",", "");
			converted.replace(".", ",");
			return converted;
		}
		return "";
	}
}
