package br.com.abg.deskgym.utils;

import br.com.abg.deskgym.exceptions.CustomMessageException;

import java.util.Calendar;
import java.util.Date;


public class DateUtil {
   
    /**
     * Adiciona tempo em dias a uma data.
	 *
	 * @param date
	 * @param days
	 *
	 * @return
     */
    public static Date addDaysToDate(final Date date, final int days) {
		final Calendar c = Calendar.getInstance();

		c.setTime(date);
		c.add(Calendar.DATE, +1);

		return c.getTime();
    }
    
    /**
     * Retorna a quantidade de dias de diferença entre duas datas.
	 *
	 * @param endDate
	 * @param startDate
	 *
	 * @return
     */
    public static int getInterval(final Date startDate, final Date endDate) {
		final Calendar startCalendar = Calendar.getInstance();
		final Calendar endCalendar = Calendar.getInstance();

		startCalendar.setTime(startDate);
		endCalendar.setTime(endDate);

		return startCalendar.get(Calendar.DAY_OF_YEAR) - endCalendar.get(Calendar.DAY_OF_YEAR);
    }

	/**
	 * Conversor de data para string
	 *
	 * @param date
	 * @param dateOnly se é para retornar apenas a data sem o horário.
	 * @param useBars se pode utilizar barras ou traços na separação
	 *
	 * @return
	 */
	public static String convertDateToString(final Date date, final boolean dateOnly, final boolean useBars) {
		final StringBuilder dateToString = new StringBuilder();
		final Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);

		dateToString.append(calendar.get(Calendar.DAY_OF_MONTH));
		dateToString.append(useBars ? "/" : "-");
		dateToString.append(calendar.get(Calendar.MONTH));
		dateToString.append(useBars ? "/" : "-");
		dateToString.append(calendar.get(Calendar.YEAR));

		if (!dateOnly) {
			dateToString.append(" ");
			dateToString.append(DateUtil.getTime(date));
		}
		return dateToString.toString();
	}

	/**
	 * Início do dia.
	 *
	 * @param date
	 *
	 * @return
	 */
	public static Date startDayDate(final Date date) {
		final Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);

		calendar.set(Calendar.HOUR_OF_DAY, 0);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);

		return calendar.getTime();
	}

	/**
	 * Final do dia.
	 *
	 * @param date
	 *
	 * @return
	 */
	public static Date endDayDate(final Date date) {
		final Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);

		calendar.set(Calendar.HOUR_OF_DAY, 23);
		calendar.set(Calendar.MINUTE, 59);
		calendar.set(Calendar.SECOND, 59);

		return calendar.getTime();
	}


	/**
	 *
	 * @param date
	 * @return
	 */
	public static String getTime(final Date date) {
		final StringBuilder timeToString = new StringBuilder();
		final Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);

		if (calendar.get(Calendar.HOUR_OF_DAY) < 10) {
			timeToString.append("0");
		}
		timeToString.append(calendar.get(Calendar.HOUR_OF_DAY));

		timeToString.append(":");

		if (calendar.get(Calendar.MINUTE) < 10) {
			timeToString.append("0");
		}
		timeToString.append(calendar.get(Calendar.MINUTE));

		return timeToString.toString();
	}

	/**
	 * Valida e retorna a data com horário
	 *
	 * @param date
	 * @param time
	 *
	 * @return
	 */
	public static Date setTimeToDate(final Date date, final String time) throws CustomMessageException, NumberFormatException {
		if (Validator.isEmpty(time) ||
				!time.contains(":") ||
				time.length() != 5) {
			throw new CustomMessageException("Horário deve estar no formato HH:mm");
		}

		final String[] clock = time.split(":");
		final int hour = Integer.parseInt(clock[0]);
		final int minutes = Integer.parseInt(clock[1]);

		if (hour < 1 || hour > 24 || minutes < 0 || minutes > 59) {
			throw new CustomMessageException("Hora deve estar entre 01 e 24.\nMinutos devem estar entre 00 e 59");
		}

		final Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.set(Calendar.MINUTE, minutes);
		calendar.set(Calendar.HOUR_OF_DAY, hour);

		return calendar.getTime();
	}
}
