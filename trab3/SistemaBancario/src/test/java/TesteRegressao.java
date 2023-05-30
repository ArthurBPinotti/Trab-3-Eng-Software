
import org.junit.platform.suite.api.SelectClasses;
import org.junit.platform.suite.api.Suite;

@Suite
@SelectClasses({
    GerenciadoraClientesTest.class,
    GerenciadoraContasTest.class,
})
public class TesteRegressao {
    // Esta classe permanece vazia, sendo usada apenas como "organizadora" dos testes.
}
