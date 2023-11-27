package gei.id.tutelado;

import gei.id.tutelado.configuracion.Configuracion;
import gei.id.tutelado.configuracion.ConfiguracionJPA;
//import gei.id.tutelado.dao.*;
import gei.id.tutelado.model.EntradaLog;
import gei.id.tutelado.model.Usuario;

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
public class Test06 {
/*
    private Logger log = LogManager.getLogger("gei.id.tutelado");

    private static ProdutorDatosProba produtorDatos = new ProdutorDatosProba();

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

        produtorDatos = new ProdutorDatosProba();
        produtorDatos.Setup(cfg);
    }

    @AfterClass
    public static void endclose() throws Exception {
        cfg.endUp();
    }



    @Before
    public void setUp() throws Exception {
        log.info("");
        log.info("Limpando BD -----------------------------------------------------------------------------------------------------");
        produtorDatos.limpaBD();
    }

    //@After
    //public void tearDown() throws Exception {
    //}


    @Test
    public void test06_numeroCobraMasDe1500() {

        Long resultado;

        log.info("");
        log.info("Configurando situación de partida del test -----------------------------------------------------------------------");

        // Escoger uno de estos 2 (al fin y al cabo no importa que tenga máquinas o no)
        //produtorDatos.creaEmpleadosConMaquinas();
        produtorDatos.creaEmpleadosSinMaquinas();
        produtorDatos.gravaEmpleados();

        log.info("");
        log.info("Inicio del test --------------------------------------------------------------------------------------------------");
        log.info("Prueba de la consulta Empleado.numeroCobraMasDe1500\n");


        resultado = empDao.numeroCobraMasDe1500();

        Assert.assertEquals(2, resultado.longValue());

    }

    @Test
    public void test06_recuperaMaquinasAsignadas() {

        List<Object[]> resultado;

        log.info("");
        log.info("Configurando situación de partida do test -----------------------------------------------------------------------");

        produtorDatos.creaEmpleadosConMaquinas();
        produtorDatos.creaEmpleadosSinMaquinas();
        produtorDatos.gravaEmpleados();

        log.info("");
        log.info("Inicio do test --------------------------------------------------------------------------------------------------");
        log.info("Obxectivo: Proba da consulta Empleado.recuperaMaquinasAsignadas\n");


        resultado = empDao.recuperaMaquinasAsignadas();


    }

 */

}