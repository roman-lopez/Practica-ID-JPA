package gei.id.tutelado;

import gei.id.tutelado.configuracion.Configuracion;
import gei.id.tutelado.configuracion.ConfiguracionJPA;

import gei.id.tutelado.dao.cliente.ClienteDao;
import gei.id.tutelado.dao.empleado.*;
import gei.id.tutelado.dao.maquina.*;
import gei.id.tutelado.GeneradorEjemplosTests;
import gei.id.tutelado.ejemplo.ProdutorDatosProba;
import gei.id.tutelado.model.EntradaLog;
import gei.id.tutelado.model.Maquina;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Rule;
import org.junit.runners.MethodSorters;
import org.junit.Test;
import org.junit.rules.TestRule;
import org.junit.rules.TestWatcher;
import org.junit.runner.Description;

import java.lang.Exception;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class P04_Consultas {

    private Logger log = LogManager.getLogger("gei.id.tutelado");

    private static GeneradorEjemplosTests generadorEjemplos = new GeneradorEjemplosTests();

    private static Configuracion cfg;
    private static EmpleadoDao empDao;
    private static MaquinaDao maqDao;

    @Rule
    public TestRule watcher = new TestWatcher() {
        protected void starting(Description description) {
            log.info("");
            log.info("%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%");
            log.info("Iniciando test: " + description.getMethodName());
            log.info("%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%");
        }
        protected void finished(Description description) {
            log.info("");
            log.info("-----------------------------------------------------------------------------------------------------------------------------------------");
            log.info("Finalizado test: " + description.getMethodName());
            log.info("-----------------------------------------------------------------------------------------------------------------------------------------");
        }
    };


    @BeforeClass
    public static void init() throws Exception {
        cfg = new ConfiguracionJPA();
        cfg.start();

        empDao = new EmpleadoDaoJPA();
        maqDao = new MaquinaDaoJPA();
        empDao.setup(cfg);
        maqDao.setup(cfg);

        generadorEjemplos = new GeneradorEjemplosTests();
        generadorEjemplos.Setup(cfg);
    }

    @AfterClass
    public static void endclose() throws Exception {
        cfg.endUp();
    }



    @Before
    public void setUp() throws Exception {
        log.info("");
        log.info("Limpando BD -----------------------------------------------------------------------------------------------------");
        generadorEjemplos.limpiarBD();
    }

    //@After
    //public void tearDown() throws Exception {
    //}


    // TEST CONSULTA ADICIONAL 1
    @Test
    public void test06_recuperaTodasPropietario() {

        List<Maquina> listaM;

        log.info("");
        log.info("Configurando situación de partida del test -----------------------------------------------------------------------");

        generadorEjemplos.crearClientesConMaquinas();
        generadorEjemplos.grabarClientes();

        log.info("");
        log.info("Inicio del test --------------------------------------------------------------------------------------------------");
        log.info("Objetivo: Prueba de la consulta Maquina.recuperaTodasPropietario\n");


        listaM = maqDao.recuperaTodasPropietario(generadorEjemplos.c0);
        Assert.assertEquals(2, listaM.size());
        Assert.assertEquals(generadorEjemplos.m0, listaM.get(0));
        Assert.assertEquals(generadorEjemplos.m1, listaM.get(1));

        listaM = maqDao.recuperaTodasPropietario(generadorEjemplos.c1);
        Assert.assertEquals(0, listaM.size());

    }

    // TEST CONSULTA ADICIONAL 2
    @Test
    public void test06_recuperaMaquinasAsignadas() {

        List<Object[]> resultado;

        log.info("");
        log.info("Configurando situación de partida del test -----------------------------------------------------------------------");

        generadorEjemplos.crearEmpleadosSueltos();
        generadorEjemplos.asignarMaquinasAEmpleados();
        generadorEjemplos.grabarEmpleados();

        log.info("");
        log.info("Inicio del test --------------------------------------------------------------------------------------------------");
        log.info("Objetivo: Prueba de la consulta Empleado.recuperaMaquinasAsignadas\n");


        resultado = empDao.recuperaMaquinasAsignadas();


        // Aserciones para cada elemento esperado en la lista
        // Primer elemento [e0][m0]
        Object[] primerElemento = resultado.get(0);
        Assert.assertEquals("e0", primerElemento[0]);
        Assert.assertEquals("m0", primerElemento[1]);

        // Segundo elemento [e1][m0]
        Object[] segundoElemento = resultado.get(1);
        Assert.assertEquals("e1", segundoElemento[0]);
        Assert.assertEquals("m0", segundoElemento[1]);

        // Tercer elemento [e1][m1]
        Object[] tercerElemento = resultado.get(2);
        Assert.assertEquals("e1", tercerElemento[0]);
        Assert.assertEquals("m1", tercerElemento[1]);

    }


    // TEST CONSULTA ADICIONAL 3

    @Test
    public void test06_recuperaMaquinasEmpresa() {

        List<Maquina> listaM;

        log.info("");
        log.info("Configurando situación de partida del test -----------------------------------------------------------------------");

        generadorEjemplos.crearClientesConMaquinas();
        generadorEjemplos.grabarClientes();

        log.info("");
        log.info("Inicio del test --------------------------------------------------------------------------------------------------");
        log.info("Objetivo: Prueba de la consulta Maquina.recuperaMaquinasEmpresa\n");


        listaM = maqDao.recuperaMaquinasEmpresa();
        Assert.assertEquals(0, listaM.size());

    }


    // TEST CONSULTA ADICIONAL 4
    @Test
    public void test06_numeroCobraMasDe1500() {

        Long resultado;

        log.info("");
        log.info("Configurando situación de partida del test -----------------------------------------------------------------------");

        generadorEjemplos.crearEmpleadosSueltos();
        generadorEjemplos.grabarEmpleados();

        log.info("");
        log.info("Inicio del test --------------------------------------------------------------------------------------------------");
        log.info("Prueba de la consulta Empleado.numeroCobraMasDe1500\n");


        resultado = empDao.numeroCobraMasDe1500();

        Assert.assertEquals(1, resultado.longValue());

        generadorEjemplos.e1.setSalario(2000.0);
        empDao.almacena(generadorEjemplos.e1);

        Assert.assertEquals(2, resultado.longValue());


    }

}