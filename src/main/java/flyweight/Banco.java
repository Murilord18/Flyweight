package flyweight;

import java.util.ArrayList;
import java.util.List;


public class Banco {

    private final List<Conta> contas = new ArrayList<>();


    public void abrirConta(String numeroConta,
                           String titular,
                           double saldoInicial,
                           String tipo,
                           double rendimentoAnual,
                           String descricao) {

        TipoConta tipoConta = TipoContaFactory.getTipoConta(tipo, rendimentoAnual, descricao);
        Conta conta = new Conta(numeroConta, titular, saldoInicial, tipoConta);
        contas.add(conta);
    }


    public List<String> obterContas() {
        List<String> resumos = new ArrayList<>();
        for (Conta c : contas) {
            resumos.add(c.obterResumo());
        }
        return resumos;
    }


    public Conta buscarConta(String numeroConta) {
        for (Conta c : contas) {
            if (c.getNumeroConta().equals(numeroConta)) return c;
        }
        return null;
    }
}
