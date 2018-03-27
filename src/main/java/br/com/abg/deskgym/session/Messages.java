package br.com.abg.deskgym.session;

import java.math.BigDecimal;
import java.util.Date;

import br.com.abg.deskgym.entity.ClassPayment;
import br.com.abg.deskgym.entity.ExtraTaxPayment;
import br.com.abg.deskgym.utils.Converter;
import br.com.abg.deskgym.utils.DateUtil;
import br.com.abg.deskgym.utils.Validator;

/**
 * Mensagens dos frames.
 * 
 * @author Alvaro
 */
public final class Messages {

	/**
	 * Mensagem que já existe.
	 */
	public static final String ALL = "Todos";

	/**
	 * Mensagem que já existe.
	 */
	public static final String ALREADY_EXISTS = "já existe";

	/**
	 * Mensagem de erro ao efetuar backup.
	 */
	public static final String BACKUP_ERROR = "Erro ao efetuar backup do banco de dados. Contate o administrador do sistema.";

	/**
	 * Mensagem que não foi encontrada.
	 */
	public static final String BEING_EDITED = "foi atualizado(a) anteriormente em outro local";

	/**
	 * Mensagem que não pode ser vazio.
	 */
	public static final String CANNOT_BE_EMPTY = "não pode ser vazio";

	/**
	 * Mensagem que não pode ser vazia.
	 */
	public static final String CANNOT_BE_EMPTY_FEMALE = "não pode ser vazia";

	/**
	 * Mensagem que não pode ser removido.
	 */
	public static final String CANNOT_BE_REMOVED = "não pode ser removido";

	/**
	 * Mensagem de clicar para atualizar.
	 */
	public  static final String CHANGED_BUT_CLICK_TO_PROCEED = "Status alterado. Você deve clicar em Salvar Alterações para concluir!";

	/**
	 * Mensagem de confirmação de deleção.
	 */
	public static final String CONFIRM_DELETE = "Deseja mesmo DELETAR? Este processo não pode ser desfeito.";

	/**
	 * Mensagem de confirmação de inativação de aluno/funcionário.
	 */
	public  static final String CONFIRM_INACTIVATE = "Deseja mesmo INATIVAR?";

	/**
	 * Mensagem de confirmação de deleção de aluno/funcionário.
	 */
	public  static final String CONFIRM_REMOVE_PERSON = "Deseja mesmo DELETAR?\nTODAS as ocorrências de pagamentos existentes serão apagadas.\nCONFIRMA?";

	/**
	 * Mensagem de confirmação de alterações.
	 */
	public static final String CONFIRM_SAVE_CHANGES = "Deseja mesmo salvar as alterações?";

	/**
	 * Mensagem para entrar em contato com o administrador do sistema.
	 */
	public static final String CONTACT_ADMIN = "Contate o administrador do sistema";

	/**
	 * Mensagem de edição de permissões do funcionário.
	 */
	public  static final String EDIT_EMPLOYEE_PERMISSIONS = "Edite os acessos para que o funcionário possa trabalhar no sistema!\n" +
															"Vá em Configurações e selecione quais menus ele poderá acessar.\n" +
															"Caso contrário ele não poderá entrar em nenhuma tela.";

	/**
	 * Mensagem de editado com sucesso.
	 */
	public static final String EMPLOYEE = "Funcionário(a)";

	/**
	 * Mensagem de editado com sucesso.
	 */
	public static final String EDITED_SUCCESSFULLY = "Editado com sucesso!";

	/**
	 * Mensagem de erro de pagamento após final da validade do plano.
	 */
	public static final String END_DATE_BEFORE_PAYMENT = "CUIDADO! CONFIRA OS DADOS A SEGUIR:\n\n"
														 + "A data de expiramento do plano é anterior a data de pagamento.\n"
														 + "Tem certeza que deseja prosseguir?";

	/**
	 * Mensagem de erro.
	 */
	public static final String ERROR = "ERRO";

	/**
	 * Mensagem de aula experimental.
	 */
	public static final String EXPERIMENTAL_CLASS = "Aula Experimental";

    /**
     * Mensagem de confirmação de data no login.
     */
    public static final String LOGIN_DATE_CONFIRM = "Já faz mais de 7 dias que você logou pela última vez no sistema." +
                                                    "\nVerifique a data do relógio do seu computador.\n" +
                                                    "Hoje é dia ";

    /**
     * Mensagem de erro de confirmação de data no login.
     */
    public  static final String LOGIN_DATE_CONFIRM_ERROR = "Este programa funciona de acordo com a data do seu computador.\n" +
                                                           "Se ela não estiver correta, todas as checagens de tempo não acontecerão como o esperado.\n" +
                                                           "Por favor, feche o programa, ajuste seu relógio e entre novamente!";

	/**
	 * Mensagem para erro de conversão de moeda.
	 */
	public static final String MONEY_CONVERT_ERROR = "Você deve digitar apenas números para os valores e preços. Não utilize ponto (.) nos números.\n" +
													 "Represente os centavos utilizando vírgula (,).\n";

	/**
	 * Mensagem que não foi encontrado.
	 */
	public static final String NOT_FOUND = "não foi encontrado";

	/**
	 * Mensagem que não foi encontrada.
	 */
	public static final String NOT_FOUND_FEMALE = "não foi encontrada";

	/**
	 * Mensagem de nenhum registro encontrado.
	 */
	public static final String NOTHING_FOUND = "Nenhum registro encontrado.";

	/**
	 * Mensagem de taxa de matrícula.
	 */
	public static final String REGISTRATION_TAX = "Taxa de Matrícula";

	/**
	 * Mensagem de removido com sucesso.
	 */
	public static final String REMOVED_SUCCESSFULLY = "Removido com sucesso!";

	/**
	 * Mensagem de salvo com sucesso.
	 */
	public static final String SAVE_SUCCESSFULLY = "Salvo com sucesso!";

	/**
	 * Mensagem de selecione.
	 */
	public  static final String SELECT = "Selecione...";

	/**
	 * Mensagem de selecione uma linha na tabela.
	 */
	public  static final String SELECT_ONE_ROW_ONLY_ERROR = "Selecione apenas uma linha na tabela.";

	/**
	 * Mensagem de aluno.
	 */
	public static final String STUDENT = "Aluno(a)";

	/**
	 * Mensagem de sucesso.
	 */
	public static final String SUCCESS = "SUCESSO";

	/**
	 * Mensagem de erro ao iniciar sistema.
	 */
	public static final String SYSTEM_START_ERROR = "ERRO AO INICIAR O SISTEMA";

	/**
	 * Mensagem de erro desconhecido.
	 */
	public static final String UNKNOWN_ERROR = "Erro desconhecido! Contate o administrador do sistema.";

	/**
	 * Mensagem de atenção.
	 */
	public static final String WARNING = "ATENÇÃO";

	/**
	 * Gera a mensagem com o número de caracteres do campo.
	 *
	 * @param size tamanho total do campo.
	 *
	 * @return
	 */
	public static String characterLengthMessage(final int size) {
		return " não pode ultrapassar " + String.valueOf(size) + " caracteres";
	}

	/**
	 * Gera a mensagem com o número de caracteres do campo.
	 *
	 * @param minSize tamanho mínimo do campo.
	 * @param maxSize tamanho máximo do campo.
	 *
	 * @return
	 */
	public static String characterLengthMessage(final int minSize, final int maxSize) {
		return " deve conter entre " + String.valueOf(minSize) + " e " + String.valueOf(maxSize) + " caracteres.";
	}

	/**
	 * Gera a mensagem de confirmação de pagamento de mensalidade.
	 *
	 * @param classPayment
	 *
	 * @return
	 */
	public static String classPaymentConfirmMessage(final ClassPayment classPayment, final ExtraTaxPayment extraTaxPayment) {
		BigDecimal total = classPayment.getPrice();
		final StringBuilder message = new StringBuilder();

		message.append("Confirme o pagamento:\n\n");
		message.append("MENSALIDADE\n");
		message.append("Aluno: ");
		message.append(classPayment.getStudent().getName());
		message.append("\nPlano: ");
		message.append(classPayment.getName());
		message.append("\nValor: R$ ");
		message.append(Converter.convertStringMoney(classPayment.getPrice()));
		message.append("\nForma de pagamento: ");
		message.append(classPayment.getPaymentMethod().toString());
		message.append("\n\nInício da vigência do plano: ");
		message.append(DateUtil.convertDateToString(classPayment.getStartDate(), true, true));
		message.append("\nFim da vigência do plano: ");
		message.append(DateUtil.convertDateToString(classPayment.getValidityDate(), true, true));
		message.append("\nData em que o pagamento foi recebido: ");
		message.append(DateUtil.convertDateToString(classPayment.getPaymentDate(), true, true));

		if (!Validator.isEmpty(extraTaxPayment)) {
			total = total.add(extraTaxPayment.getPrice());
			message.append("\n\nTAXA EXTRA\n");
			message.append("Descrição: ");
			message.append(extraTaxPayment.getDescription());
			message.append("\nValor: R$ ");
			message.append(Converter.convertStringMoney(extraTaxPayment.getPrice()));
		}

		message.append("\n\nTOTAL A RECEBER: R$ ");
		message.append(Converter.convertStringMoney(total));

		return message.toString();
	}

	/**
	 * Gera a mensagem que o valor deve ser maior
	 *
	 * @param value
	 *
	 * @return
	 */
	public static String valueBiggerMessage(final int value) {
		return " deve ser maior que " + String.valueOf(value) + ".";
	}

	/**
	 * Gera a mensagem que o valor deve ser menor
	 *
	 * @param value
	 *
	 * @return
	 */
	public static String valueSmallerMessage(final int value) {
		return " deve ser menor que " + String.valueOf(value) + ".";
	}

	/**
	 * Gera a mensagem que o valor deve estar entre
	 *
	 * @param minValue
	 * @param maxValue
	 *
	 * @return
	 */
	public static String valueBetweenMessage(final int minValue, final int maxValue) {
		return " deve estar entre " + String.valueOf(minValue) + " e " + String.valueOf(maxValue) + ".";
	}

	/**
	 * Gera a mensagem de data com intervalo muito grande.
	 *
	 * @param endDate
	 * @param interval
	 * @param expectedInterval
	 *
	 * @return
	 */
	public static String tooLargeDateInterval(final Date endDate, final int interval, final int expectedInterval) {
		final StringBuilder message = new StringBuilder();
		message.append("CUIDADO! CONFIRA OS DADOS A SEGUIR:\n\n");
		message.append("Com o vencimento do plano para o dia ");
		message.append(DateUtil.convertDateToString(endDate, false, true));
		message.append(", o período total de vigência será MAIOR do que a duração do plano selecionado.\n");
		message.append("\n\nDURAÇÃO TOTAL COM O VENCIMENTO SELECIONADO ");
		message.append(String.valueOf(interval));
		message.append(" dias");
		message.append("\nDURAÇÃO TOTAL QUE O PLANO DEVERIA TER: ");
		message.append(String.valueOf(expectedInterval));
		message.append(" dias");
		message.append("\n\nDeseja MESMO continuar?");
		message.append("Se não estiver de acordo, clique em NÃO e altere o dia de vencimento");

		return message.toString();
	}

	/**
	 * Gera a mensagem de data com intervalo muito pequeno.
	 *
	 * @param endDate
	 * @param interval
	 * @param expectedInterval
	 *
	 * @return
	 */
	public static String tooSmallDateInterval(final Date endDate, final int interval, final int expectedInterval) {
		final StringBuilder message = new StringBuilder();

		message.append("CUIDADO! CONFIRA OS DADOS A SEGUIR:\n\n");
		message.append("Com o vencimento do plano para o dia ");
		message.append(DateUtil.convertDateToString(endDate, false, true));
		message.append(", o período total de vigência será MENOR do que a duração do plano selecionado.\n");
		message.append("\n\nDURAÇÃO TOTAL COM O VENCIMENTO SELECIONADO ");
		message.append(String.valueOf(interval));
		message.append(" dias");
		message.append("\nDURAÇÃO TOTAL QUE O PLANO DEVERIA TER: ");
		message.append(String.valueOf(expectedInterval));
		message.append(" dias");
		message.append("\n\nDeseja MESMO continuar?");
		message.append("Se não estiver de acordo, clique em NÃO e altere o dia de vencimento");

		return message.toString();
	}
}
