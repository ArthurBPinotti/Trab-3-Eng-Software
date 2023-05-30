import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;
import java.util.ArrayList;
import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Suite de testes da classe GerenciadoraClientes")
class GerenciadoraClientesTest {

    private ArrayList<Cliente> lClientes;
    private GerenciadoraClientes gerenciadoraClientes;

    @BeforeEach
    void setUp() {
        this.lClientes = new ArrayList<Cliente>();
        this.lClientes.add(new Cliente(1, "Cliente 1", 40, "cliente1@gmail.com", 1, true));
        this.lClientes.add(new Cliente(2, "Cliente 2", 27, "cliente2@gmail.com", 2, false));
        this.lClientes.add(new Cliente(3, "Cliente 3", 36, "cliente3@gmail.com", 3, false));
        this.lClientes.add(new Cliente(4, "Cliente 4", 69, "cliente3@gmail.com", 4, true));

        this.gerenciadoraClientes = new GerenciadoraClientes(this.lClientes);
    }

    @AfterEach
    void tearDown() {
        this.lClientes = null;
        this.gerenciadoraClientes = null;
    }

    @Test
    @DisplayName("Testa getClientesDoBanco")
    void test_getClientesDoBanco() {

        assertEquals(this.gerenciadoraClientes.getClientesDoBanco(), this.lClientes);
    }

    @Test
    @DisplayName("Testa pesquisaCliente")
    void test_pesquisaCliente() {

        assertNotNull(this.gerenciadoraClientes.pesquisaCliente(1));
        assertEquals(this.lClientes.get(0), this.gerenciadoraClientes.pesquisaCliente(1));
    }

    @Test
    @DisplayName("Testa pesquisaCliente inválido")
    void test_pesquisaClienteInvalido() {
        assertNull(this.gerenciadoraClientes.pesquisaCliente(30));
    }

    @Test
    @DisplayName("Testa adicionaCliente")
    void test_adicionaCliente() {
        var newCliente = new Cliente(1, "Cliente 5", 30, "cliente5@gmail.com", 1, true);

        this.gerenciadoraClientes.adicionaCliente(newCliente);
        
        assertEquals(5, this.gerenciadoraClientes.getClientesDoBanco().size());
        assertEquals(newCliente, this.lClientes.get(4));
    }

    @DisplayName("Testa removeCliente")
    @MethodSource()
    void test_removeCliente() {
        assertTrue(this.gerenciadoraClientes.removeCliente(1));
    	assertEquals(4, this.gerenciadoraClientes.getClientesDoBanco().size());
    }

    @DisplayName("Testa removeCliente inválido")
    public void test_removeClienteInexistenteInvalido() 
    {
    	assertFalse(this.gerenciadoraClientes.removeCliente(40));
    	assertEquals(3, this.gerenciadoraClientes.getClientesDoBanco().size());
    }


    @DisplayName("Testa clienteAtivo")
    @MethodSource()
    void test_clienteAtivo() {

        assertTrue(this.gerenciadoraClientes.clienteAtivo(1));
    }

    @Test
    @DisplayName("Testa limpa")
    void test_limpa() {
        this.gerenciadoraClientes.limpa();
        assertTrue(this.gerenciadoraClientes.getClientesDoBanco().isEmpty());
    }

    @DisplayName("Testa validaIdade")
    @ParameterizedTest(name = "Idade: {0}")
    @ValueSource(ints = { 18, 35, 50, 65 })
    void test_validaIdade(int idade) throws IdadeNaoPermitidaException {

        assertTrue(this.gerenciadoraClientes.validaIdade(idade));
    }

    @DisplayName("Testa validaIdade inválida")
    @ParameterizedTest(name = "Idade: {0}")
    @ValueSource(ints = { 10, 17, 66, 80 })
    void test_validaIdadeInvalida(int idade) {
        Exception exception = assertThrows(
            IdadeNaoPermitidaException.class, () -> this.gerenciadoraClientes.validaIdade(idade)
        );
        assertEquals(IdadeNaoPermitidaException.MSG_IDADE_INVALIDA, exception.getMessage());
    }
}