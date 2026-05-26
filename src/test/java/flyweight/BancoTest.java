package flyweight;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;


class BancoTest {

    @BeforeEach
    void setUp() {
        // Garante que cada teste começa com o cache vazio
        TipoContaFactory.limparCache();
    }

    
    // 1. Verificar resumo das contas abertas

    @Test
    void deveRetornarResumosDasContas() {
        Banco banco = new Banco();
        banco.abrirConta("0001", "Alice",  1500.0, "Corrente",     0.005, "Conta corrente sem rendimento fixo");
        banco.abrirConta("0002", "Bruno",  3000.0, "Poupança",     0.06,  "Conta poupança com rendimento mensal");
        banco.abrirConta("0003", "Carla",  1500.0, "Corrente",     0.005, "Conta corrente sem rendimento fixo");
        banco.abrirConta("0004", "Daniel", 5000.0, "Investimento", 0.12,  "Conta de investimento CDB");

        List<String> esperado = Arrays.asList(
                "Conta{numero='0001', titular='Alice', tipo='Corrente', saldo=1500.0}",
                "Conta{numero='0002', titular='Bruno', tipo='Poupança', saldo=3000.0}",
                "Conta{numero='0003', titular='Carla', tipo='Corrente', saldo=1500.0}",
                "Conta{numero='0004', titular='Daniel', tipo='Investimento', saldo=5000.0}"
        );

        assertEquals(esperado, banco.obterContas());
    }


    // 2. Verificar que o flyweight funciona — menos tipos que contas

    @Test
    void deveCompartilharTiposDeContaEntreMultiplasContas() {
        Banco banco = new Banco();
        banco.abrirConta("0001", "Alice",  1500.0, "Corrente", 0.005, "Conta corrente");
        banco.abrirConta("0002", "Bruno",  3000.0, "Poupança",  0.06,  "Conta poupança");
        banco.abrirConta("0003", "Carla",  2000.0, "Corrente", 0.005, "Conta corrente");
        banco.abrirConta("0004", "Daniel", 5000.0, "Corrente", 0.005, "Conta corrente");

        // 4 contas, mas apenas 2 tipos distintos no pool
        assertEquals(2, TipoContaFactory.getTotalTipos());
    }

    
    // 3. Instâncias de TipoConta idêntico devem ser o mesmo objeto
    
    @Test
    void deveRetornarMesmaInstanciaDeTipoContaParaMesmoTipo() {
        TipoConta t1 = TipoContaFactory.getTipoConta("Corrente", 0.005, "Conta corrente");
        TipoConta t2 = TipoContaFactory.getTipoConta("Corrente", 0.005, "Conta corrente");

        assertSame(t1, t2, "Tipos iguais devem compartilhar a mesma instância (flyweight)");
    }

    
    // 4. Tipos diferentes devem gerar instâncias distintas
    
    @Test
    void deveRetornarInstanciasDiferentesParaTiposDiferentes() {
        TipoConta corrente   = TipoContaFactory.getTipoConta("Corrente",     0.005, "Conta corrente");
        TipoConta poupanca   = TipoContaFactory.getTipoConta("Poupança",     0.06,  "Conta poupança");
        TipoConta investimento = TipoContaFactory.getTipoConta("Investimento", 0.12, "CDB");

        assertNotSame(corrente, poupanca);
        assertNotSame(poupanca, investimento);
        assertEquals(3, TipoContaFactory.getTotalTipos());
    }

    
    // 5. Depósito atualiza saldo corretamente
    
    @Test
    void deveAtualizarSaldoAposDeposito() {
        Banco banco = new Banco();
        banco.abrirConta("0001", "Alice", 1000.0, "Corrente", 0.005, "Conta corrente");

        Conta conta = banco.buscarConta("0001");
        assertNotNull(conta);
        conta.depositar(500.0);

        assertEquals(1500.0, conta.getSaldo());
    }

    
    // 6. Saque bem-sucedido
    
    @Test
    void deveRealizarSaqueComSucesso() {
        Banco banco = new Banco();
        banco.abrirConta("0002", "Bruno", 2000.0, "Poupança", 0.06, "Conta poupança");

        Conta conta = banco.buscarConta("0002");
        boolean resultado = conta.sacar(800.0);

        assertTrue(resultado);
        assertEquals(1200.0, conta.getSaldo());
    }

    
    // 7. Saque com saldo insuficiente deve falhar
    
    @Test
    void deveRecusarSaqueComSaldoInsuficiente() {
        Banco banco = new Banco();
        banco.abrirConta("0003", "Carla", 300.0, "Corrente", 0.005, "Conta corrente");

        Conta conta = banco.buscarConta("0003");
        boolean resultado = conta.sacar(500.0);

        assertFalse(resultado);
        assertEquals(300.0, conta.getSaldo());
    }

    
    // 8. TipoConta retorna atributos intrínsecos corretamente
    
    @Test
    void deveRetornarAtributosDoTipoConta() {
        TipoConta tipo = TipoContaFactory.getTipoConta("Investimento", 0.12, "CDB pré-fixado");

        assertEquals("Investimento", tipo.getTipo());
        assertEquals(0.12, tipo.getRendimentoAnual());
        assertEquals("CDB pré-fixado", tipo.getDescricao());
    }
}
