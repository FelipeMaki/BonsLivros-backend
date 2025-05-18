package br.com.bonslivros.BonsLivros.services;

public class validarcpf {
    // Função para validar o CPF
    public static boolean validarCpf(String cpf) {
    // Remove caracteres não numéricos
    cpf = cpf.replaceAll("[^\\d]", "");

    // Verifica se tem 11 dígitos ou se são todos iguais
    if (cpf.length() != 11 || cpf.chars().distinct().count() == 1) {
        return false;
    }

    try {
        // Calcula o 1º dígito verificador
        int soma = 0;
        for (int i = 0; i < 9; i++) {
            soma += Character.getNumericValue(cpf.charAt(i)) * (10 - i);
        }

        int digito1 = (soma % 11 < 2) ? 0 : 11 - (soma % 11);

        // Calcula o 2º dígito verificador
        soma = 0;
        for (int i = 0; i < 10; i++) {
            soma += Character.getNumericValue(cpf.charAt(i)) * (11 - i);
        }

        int digito2 = (soma % 11 < 2) ? 0 : 11 - (soma % 11);

        // Verifica se os dígitos calculados batem com os fornecidos
        return cpf.charAt(9) == Character.forDigit(digito1, 10) &&
               cpf.charAt(10) == Character.forDigit(digito2, 10);

    } catch (Exception e) {
        return false;
    }
}

}
