package flyweight;


public class Conta {

    private final String numeroConta;
    private final String titular;
    private double saldo;
    private final TipoConta tipoConta;   // referência ao flyweight

    public Conta(String numeroConta, String titular, double saldo, TipoConta tipoConta) {
        this.numeroConta = numeroConta;
        this.titular     = titular;
        this.saldo       = saldo;
        this.tipoConta   = tipoConta;
    }

    public String getNumeroConta() { return numeroConta; }
    public String getTitular()     { return titular; }
    public double getSaldo()       { return saldo; }
    public TipoConta getTipoConta(){ return tipoConta; }

    public void depositar(double valor) {
        if (valor > 0) saldo += valor;
    }

    public boolean sacar(double valor) {
        if (valor > 0 && valor <= saldo) {
            saldo -= valor;
            return true;
        }
        return false;
    }


    public String obterResumo() {
        return "Conta{" +
                "numero='" + numeroConta + '\'' +
                ", titular='" + titular + '\'' +
                ", tipo='" + tipoConta.getTipo() + '\'' +
                ", saldo=" + saldo +
                '}';
    }
}
