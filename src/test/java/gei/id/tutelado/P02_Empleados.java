package gei.id.tutelado;

import gei.id.tutelado.configuracion.Configuracion;
import gei.id.tutelado.configuracion.ConfiguracionJPA;
import gei.id.tutelado.dao.cliente.ClienteDao;
import gei.id.tutelado.dao.cliente.ClienteDaoJPA;
import gei.id.tutelado.dao.empleado.EmpleadoDao;
import gei.id.tutelado.dao.empleado.EmpleadoDaoJPA;
import gei.id.tutelado.dao.maquina.MaquinaDao;
import gei.id.tutelado.dao.maquina.MaquinaDaoJPA;
import gei.id.tutelado.dao.persona.PersonaDao;
import gei.id.tutelado.model.Empleado;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.*;
import org.junit.rules.TestRule;
import org.junit.rules.TestWatcher;
import org.junit.runner.Description;
import org.junit.runners.MethodSorters;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class P02_Empleados {

    private Logger log = LogManager.getLogger("gei.id.tutelado");

    private static GeneradorEjemplosTests generadorEjemplos = new GeneradorEjemplosTests();

    private static Configuracion cfg;
    private static ClienteDao clienteDao;
    private static EmpleadoDao empleadoDao;
    private static MaquinaDao maquinaDao;

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

        clienteDao = new ClienteDaoJPA();
        clienteDao.setup(cfg);

        empleadoDao = new EmpleadoDaoJPA();
        empleadoDao.setup(cfg);

        maquinaDao = new MaquinaDaoJPA();
        maquinaDao.setup(cfg);

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
        log.info("Limpiando BD --------------------------------------------------------------------------------------------");
        generadorEjemplos.limpiarBD();
    }

    @After
    public void tearDown() throws Exception {
    }

    public void test01_Recuperacion() {

        Empleado e;

        log.info("");
        log.info("Configurando situación de partida del test -----------------------------------------------------------------------");

        generadorEjemplos.crearEmpleadosSueltos();
        generadorEjemplos.grabarEmpleados();

        log.info("");
        log.info("Inicio del test --------------------------------------------------------------------------------------------------");
        log.info("Objetivo: Prueba de recuperación desde la BD de cliente (sin maquinas asociadas) por nif\n"
                + "\t\t\t\t Casos contemplados:\n"
                + "\t\t\t\t a) Recuperación por nif existente\n"
                + "\t\t\t\t b) Recuperacion por nif inexistente\n");

        // Situación de partida:
        // e0 desligado

        log.info("Probando recuperacion por nif EXISTENTE --------------------------------------------------");

        e = empleadoDao.recuperaPorNif(generadorEjemplos.e0.getNif());
        Assert.assertEquals(generadorEjemplos.e0.getNif(),      e.getNif());
        Assert.assertEquals(generadorEjemplos.e0.getNombrePila(),     e.getNombrePila());
        Assert.assertEquals(generadorEjemplos.e0.getApellidos(), e.getApellidos());
        Assert.assertEquals(generadorEjemplos.e0.getApellidos(), e.getApellidos());
        Assert.assertEquals(generadorEjemplos.e0.getFechaContratacion(), e.getFechaContratacion());
        Assert.assertEquals(generadorEjemplos.e0.getSalario(), e.getSalario());

        log.info("");
        log.info("Probando recuperacion por nif INEXISTENTE -----------------------------------------------");

        e = empleadoDao.recuperaPorNif("iwbvyhuebvuwebvi");
        Assert.assertNull (e);

    }

    @Test
    public void test02_Alta() {

        log.info("");
        log.info("Configurando situación de partida del test -----------------------------------------------------------------------");

        generadorEjemplos.crearEmpleadosSueltos();

        log.info("");
        log.info("Inicio del test --------------------------------------------------------------------------------------------------");
        log.info("Objetivo: Prueba de grabación en la BD de nuevo empleado (sin máquinas asociadas)\n");

        // Situación de partida:
        // e0 transitorio

        Assert.assertNull(generadorEjemplos.e0.getIdPersona());
        empleadoDao.almacena(generadorEjemplos.e0);
        Assert.assertNotNull(generadorEjemplos.e0.getIdPersona());
    }


    @Test
    public void test03_Eliminacion() {

        log.info("");
        log.info("Configurando situación de partida del test -----------------------------------------------------------------------");

        generadorEjemplos.crearEmpleadosSueltos();
        generadorEjemplos.grabarEmpleados();


        log.info("");
        log.info("Inicio del test --------------------------------------------------------------------------------------------------");
        log.info("Objetivo: Prueba de eliminación de la BD de usuario sin máquinas asociadas\n");

        // Situación de partida:
        // e0 desligado

        Assert.assertNotNull(empleadoDao.recuperaPorNif(generadorEjemplos.e0.getNif()));
        empleadoDao.elimina(generadorEjemplos.e0);
        Assert.assertNull(empleadoDao.recuperaPorNif(generadorEjemplos.e0.getNif()));
    }

    /*@Test
    public void test04_Modificacion() {

        Usuario u1, u2;
        String novoNome;

        log.info("");
        log.info("Configurando situación de partida do test -----------------------------------------------------------------------");

        produtorDatos.creaUsuariosSoltos();
        produtorDatos.gravaUsuarios();

        log.info("");
        log.info("Inicio do test --------------------------------------------------------------------------------------------------");
        log.info("Obxectivo: Proba de modificación da información básica dun usuario sen entradas de log\n");

        // Situación de partida:
        // u0 desligado

        novoNome = new String ("Nome novo");

        u1 = usuDao.recuperaPorNif(produtorDatos.u0.getNif());
        Assert.assertNotEquals(novoNome, u1.getNome());
        u1.setNome(novoNome);

        usuDao.modifica(u1);

        u2 = usuDao.recuperaPorNif(produtorDatos.u0.getNif());
        Assert.assertEquals (novoNome, u2.getNome());

    }

    @Test
    public void test09_Excepcions() {

        Boolean excepcion;

        log.info("");
        log.info("Configurando situación de partida do test -----------------------------------------------------------------------");

        produtorDatos.creaUsuariosSoltos();
        usuDao.almacena(produtorDatos.u0);

        log.info("");
        log.info("Inicio do test --------------------------------------------------------------------------------------------------");
        log.info("Obxectivo: Proba de violación de restricións not null e unique\n"
                + "\t\t\t\t Casos contemplados:\n"
                + "\t\t\t\t a) Gravación de usuario con nif duplicado\n"
                + "\t\t\t\t b) Gravación de usuario con nif nulo\n");

        // Situación de partida:
        // u0 desligado, u1 transitorio

        log.info("Probando gravacion de usuario con Nif duplicado -----------------------------------------------");
        produtorDatos.u1.setNif(produtorDatos.u0.getNif());
        try {
            usuDao.almacena(produtorDatos.u1);
            excepcion=false;
        } catch (Exception ex) {
            excepcion=true;
            log.info(ex.getClass().getName());
        }
        Assert.assertTrue(excepcion);

        // Nif nulo
        log.info("");
        log.info("Probando gravacion de usuario con Nif nulo ----------------------------------------------------");
        produtorDatos.u1.setNif(null);
        try {
            usuDao.almacena(produtorDatos.u1);
            excepcion=false;
        } catch (Exception ex) {
            excepcion=true;
            log.info(ex.getClass().getName());
        }
        Assert.assertTrue(excepcion);
    }*/

}