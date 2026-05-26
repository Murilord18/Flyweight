package flyweight;


public class TipoConta {

    private final String tipo;
    private final double rendimentoAnual;
    private final String descricao;

    public TipoConta(String tipo, double rendimentoAnual, String descricao) {
        this.tipo = tipo;
        this.rendimentoAnual = rendimentoAnual;
        this.descricao = descricao;
    }

    public String getTipo() {
        return tipo;
    }

    public double getRendimentoAnual() {
        return rendimentoAnual;
    }

    public String getDescricao() {
        return descricao;
    }

    @Override
    public String toString() {
        return "TipoConta{" +
                "tipo='" + tipo + '\'' +
                ", rendimentoAnual=" + rendimentoAnual +
                ", descricao='" + descricao + '\'' +
                '}';
    }
}
