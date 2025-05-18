package br.com.bonslivros.BonsLivros.services;

public class validarcpf {
    // Função para validar o CPF
    public static boolean validarCpf(String cpfLido) {
        String cpf = cpfLido.substring(0, 11);

        // Validando o primeiro digito
        int mult = 10;
        validarDigitoCpf(cpf, mult);

        // Validando o segundo digito
        mult = 11;
        validarDigitoCpf(cpf, mult);

        return cpfLido.equals(cpf);
    }

    // Função para validar os digitos do CPF
    private static String validarDigitoCpf(String cpf, int mult) {
        int soma = 0;

        for (int i = 0; i < cpf.length(); i++) {
            if (mult >= 2) {
                soma += Integer.parseInt(""+cpf.charAt(i)) * mult--;
            }
        }

        int resto = soma % 11;
        int digito = (resto < 2 ? 0 : 11 - resto);
        cpf += digito;

        return (cpf);
    }
}
