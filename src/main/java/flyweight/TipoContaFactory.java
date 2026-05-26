package flyweight;

import java.util.HashMap;
import java.util.Map;


public class TipoContaFactory {

    private static final Map<String, TipoConta> cache = new HashMap<>();

    private TipoContaFactory() {}


    public static TipoConta getTipoConta(String tipo, double rendimentoAnual, String descricao) {
        TipoConta tipoConta = cache.get(tipo);
        if (tipoConta == null) {
            tipoConta = new TipoConta(tipo, rendimentoAnual, descricao);
            cache.put(tipo, tipoConta);
        }
        return tipoConta;
    }


    public static int getTotalTipos() {
        return cache.size();
    }

    public static void limparCache() {
        cache.clear();
    }
}
