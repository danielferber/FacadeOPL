import infra.exception.ExceptionService;
import br.com.danielferber.ilogtoys.ilog.NoSolutionException;
import br.com.danielferber.ilogtoys.cplex.ConfigurationCplex;
import br.com.danielferber.ilogtoys.opl.ConfiguracaoOPL;
import br.com.danielferber.ilogtoys.opl.FacadeOPL;
import br.com.danielferber.ilogtoys.opl.OplModelException;
import br.com.danielferber.ilogtoys.modelo.ProvedorModeloString;
import br.com.danielferber.ilogtoys.opl.ProvedorModelo;

import java.io.File;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;


/**
 * Verifica se um modelo incorreto é acusado de forma adequada.
 */
public class ModelTest {
	private ConfiguracaoOPL configuracaoOpl;
	private ConfigurationCplex configuracaoCplex;

	@Before
	public void loadConfiguration() {
		File homeDir = new File(System.getProperty("user.dir"));
		configuracaoOpl = new ConfiguracaoOPL("teste", homeDir);
		configuracaoCplex = new ConfigurationCplex("teste", homeDir);
	}

	@Test
	public void modeloInvalido1Test() {
		String modeloInvalido1 =
				"tuple ponto {" +
						"	float x; " +
						"	float y;" +
						"}" +
						"range espaco = 1..2" + // aqui faltou terminar com ponto-e-virgula.
						"ponto pontos[espaco] = [ <0,3>, <3,0> ];" +
						"ponto referencia = <1,1>;" +
						"dvar float pesos[espaco];" +
						"dexpr float x = sum(i in espaco) pesos[i] * pontos[i].x;" +
						"dexpr float y = sum(i in espaco) pesos[i] * pontos[i].y;" +
						"minimize (x - referencia.x)^2 + (y - referencia.y)^2;" +
						"subject to {" +
						"  sum(i in espaco) pesos[i] == 1.0;" +
						"  x >= 0;" +
						"  y >= 0;" +
						"}";

		ProvedorModelo provedorModelo1 = new ProvedorModeloString("nome", modeloInvalido1);

		FacadeOPL facadeOPL = new FacadeOPL(configuracaoOpl, configuracaoCplex, provedorModelo1, null, null);
		try {
			facadeOPL.executar();
			Assert.fail("Model should be accused as invalid.");
		} catch (OplModelException e) {
			ExceptionService.reportException(System.err, e);
		} catch (NoSolutionException e) {
			e.printStackTrace();
		}
	}

	@Test
	public void modeloInvalido2Test() {
		String modeloInvalido1 =
				"tuple ponto {" +
						"	float x; " +
						"	float y;" +
						"}" +
						"range espaco = 1..2;" +
						"ponto pontos[espaco] = [ <0,3>, <3,0> ];" +
						"ponto referencia = <1,1>;" +
						"dvar float pesos[espaco];" +
						"dexpr float x = sum(i in espaco) pesos[i] * pontos[i].x;" +
						"dexpr float y = sum(i in espaco) pesos[i] * pontos[i].y;" +
						"minimize (x - referencia.x)^2 + (y - referencia.y)^2;" +
						"subject to {" +
						"  sum(i in espaco) pesos[i] == 1.0;" +
						"  wrubbles >= 0;" + // aqui tem uma variável nada a ver
						"  y >= 0;" +
						"}";

		ProvedorModelo provedorModelo1 = new ProvedorModeloString("nome", modeloInvalido1);

		FacadeOPL facadeOPL = new FacadeOPL(configuracaoOpl, configuracaoCplex, provedorModelo1, null, null);
		try {
			facadeOPL.executar();
			Assert.fail("Model should be accused as invalid.");
		} catch (OplModelException e) {
			ExceptionService.reportException(System.err, e);
		} catch (NoSolutionException e) {
			e.printStackTrace();
		}
	}

	@Test
	public void modeloInvalido3Test() {
		String modeloInvalido1 =
				"tuple ponto {" +
						"	float x; " +
						"	float y;" +
						"}" +
						"range espaco = 1..2;" +
						"ponto pontos[espaco] = [ <0,3>, <3,0>, <4,0> ];" + // numero de elementos não é compatível com definição do índice
						"ponto referencia = <1,1>;" +
						"dvar float pesos[espaco];" +
						"dexpr float x = sum(i in espaco) pesos[i] * pontos[i].x;" +
						"dexpr float y = sum(i in espaco) pesos[i] * pontos[i].y;" +
						"minimize (x - referencia.x)^2 + (y - referencia.y)^2;" +
						"subject to {" +
						"  sum(i in espaco) pesos[i] == 1.0;" +
						"  x >= 0;" +
						"  y >= 0;" +
						"}";

		ProvedorModelo provedorModelo1 = new ProvedorModeloString("nome", modeloInvalido1);

		FacadeOPL facadeOPL = new FacadeOPL(configuracaoOpl, configuracaoCplex, provedorModelo1, null, null);
		try {
			facadeOPL.executar();
			Assert.fail("Model should be accused as invalid.");
		} catch (OplModelException e) {
			ExceptionService.reportException(System.err, e);
		} catch (NoSolutionException e) {
			e.printStackTrace();
		}
	}
}
