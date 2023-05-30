import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.ArrayList;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Suite de testes da classe GerenciadoraContas")
class GerenciadoraContasTest {

    private ArrayList<ContaCorrente> contasDoBanco;
    private GerenciadoraContas gerenciadoraContas;

    @BeforeEach
    void setUp() {
        this.contasDoBanco = new ArrayList<ContaCorrente>();

        this.contasDoBanco.add(new ContaCorrente(1, 23.22 , true ));
        this.contasDoBanco.add(new ContaCorrente(2, 69.69 , false));
        this.contasDoBanco.add(new ContaCorrente(3, 420.1, false ));
        this.contasDoBanco.add(new ContaCorrente(4, 132.01  , true));

        this.gerenciadoraContas = new GerenciadoraContas(this.contasDoBanco);
    }

    @AfterEach
    void tearDown() {
        this.contasDoBanco = null;
        this.gerenciadoraContas = null;
    }

    @Test
    @DisplayName("Teste para o método getContasDoBanco")
    void test_getContasDoBanco() {
        assertEquals(this.gerenciadoraContas.getContasDoBanco(), this.contasDoBanco);
    }

    @Test
    @DisplayName("Teste para o método pesquisaConta com conta válida")
    void test_pesquisaConta_comContaValida() {
        assertNotNull(this.gerenciadoraContas.pesquisaConta(2));
        assertEquals(this.contasDoBanco.get(1), this.gerenciadoraContas.pesquisaConta(2));
    }

    @Test
    @DisplayName("Teste para o método pesquisaConta com conta inválida")
    void test_pesquisaConta_comContaInvalida() {
        assertNull(this.gerenciadoraContas.pesquisaConta(9));
    }

    @Test
    @DisplayName("Teste para o método adicionaConta")
    void test_adicionaConta() {
        var newConta = new ContaCorrente(5, 11.11, true);

        this.gerenciadoraContas.adicionaConta(newConta);

        assertEquals(5, this.contasDoBanco.size());
        assertEquals(newConta, this.contasDoBanco.get(4));
    }

    @DisplayName("Teste para o método removeConta")
    @ParameterizedTest(name = "idConta: {0}")
    @ValueSource      (ints = { 1, 2, 3, 4})
    void test_removeConta(int idConta) {

        assertTrue(this.gerenciadoraContas.removeConta(1));
    }

    @DisplayName("Teste para o método contaAtiva")
    @ParameterizedTest(name = "idConta: {0}")
    @ValueSource      (ints = { 1, 2, 3, 4 })
    void test_contaAtiva(int idConta) {
        assertTrue(this.gerenciadoraContas.contaAtiva(idConta));
    }

    private static Stream<Arguments> test_transfereValor() {
        return Stream.of(
            Arguments.arguments(true, 10.0, 13.219999999999999, 79.69),
            Arguments.arguments(true, 0.22, 23.0, 69.91),
            Arguments.arguments(false, 100.0, 11.11, 22.22)
        );
    }

    @DisplayName("Teste para o método transfereValor")
    @ParameterizedTest(name = "result: {0}, vlTransferencia: {1}, saldoOrigem: {2}, saldoDestino: {3}")
    @MethodSource()
    void test_transfereValor(boolean result, double vlTransferencia, double saldoOrigem, double saldoDestino) {


        assertEquals(this.gerenciadoraContas.transfereValor(1, vlTransferencia, 2), result);
        assertEquals(saldoOrigem, this.contasDoBanco.get(0).getSaldo(), 0.001);
        assertEquals(saldoDestino, this.contasDoBanco.get(1).getSaldo(), 0.001);
    }
}