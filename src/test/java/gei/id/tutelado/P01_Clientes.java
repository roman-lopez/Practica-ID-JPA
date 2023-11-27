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
import gei.id.tutelado.model.Cliente;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.*;
import org.junit.rules.TestRule;
import org.junit.rules.TestWatcher;
import org.junit.runner.Description;
import org.junit.runners.MethodSorters;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class P01_Clientes {

    private Logger log = LogManager.getLogger("gei.id.tutelado");

    private static GeneradorEjemplosTests generadorEjemplos = new GeneradorEjemplosTests();

    private static Configuracion cfg;
    private static ClienteDao clienteDao;
    //private static EmpleadoDao empleadoDao;
    //private static MaquinaDao maquinaDao;

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

        /*empleadoDao = new EmpleadoDaoJPA();
        empleadoDao.setup(cfg);

        maquinaDao = new MaquinaDaoJPA();
        maquinaDao.setup(cfg);*/

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

    @Test
    public void test01_Recuperacion() {

        Cliente c;

        log.info("");
        log.info("Configurando situación de partida del test -----------------------------------------------------------------------");

        generadorEjemplos.crearClientesSueltos();
        generadorEjemplos.grabarClientes();

        log.info("");
        log.info("Inicio del test --------------------------------------------------------------------------------------------------");
        log.info("Objetivo: Prueba de recuperación desde la BD de cliente (sin maquinas asociadas) por nif\n"
                + "\t\t\t\t Casos contemplados:\n"
                + "\t\t\t\t a) Recuperación por nif existente\n"
                + "\t\t\t\t b) Recuperacion por nif inexistente\n");

        // Situación de partida:
        // c0 desligado

        log.info("Probando recuperacion por nif EXISTENTE --------------------------------------------------");

        c = clienteDao.recuperaPorNif(generadorEjemplos.c0.getNif());
        Assert.assertEquals(generadorEjemplos.c0.getNif(),      c.getNif());
        Assert.assertEquals(generadorEjemplos.c0.getNombrePila(),     c.getNombrePila());
        Assert.assertEquals(generadorEjemplos.c0.getApellidos(), c.getApellidos());
        Assert.assertEquals(generadorEjemplos.c0.getApellidos(), c.getApellidos());
        Assert.assertEquals(generadorEjemplos.c0.getTipoCliente(), c.getTipoCliente());

        log.info("");
        log.info("Probando recuperacion por nif INEXISTENTE -----------------------------------------------");

        c = clienteDao.recuperaPorNif("iwbvyhuebvuwebvi");
        Assert.assertNull (c);

    }

    @Test
    public void test02_Alta() {

        log.info("");
        log.info("Configurando situación de partida do test -----------------------------------------------------------------------");

        generadorEjemplos.crearClientesSueltos();

        log.info("");
        log.info("Inicio del test --------------------------------------------------------------------------------------------------");
        log.info("Objetivo: Prueba de grabación en la BD de nuevo cliente (sin máquinas asociadas)\n");

        // Situación de partida:
        // c0 transitorio

        Assert.assertNull(generadorEjemplos.c0.getIdPersona());
        clienteDao.almacena(generadorEjemplos.c0);

        System.out.println(" sgf");
        Cliente cliente = clienteDao.recuperaPorNif(generadorEjemplos.c0.getNif());
        Assert.assertNotNull(cliente);
    }



    @Test
    public void test03_Eliminacion() {

        log.info("");
        log.info("Configurando situación de partida del test -----------------------------------------------------------------------");

        generadorEjemplos.crearClientesSueltos();
        generadorEjemplos.grabarClientes();


        log.info("");
        log.info("Inicio del test --------------------------------------------------------------------------------------------------");
        log.info("Objetivo: Prueba de eliminación de la BD de cliente sin entradas asociadas\n");

        // Situación de partida:
        // c0 desligado

        Assert.assertNotNull(clienteDao.recuperaPorNif(generadorEjemplos.c0.getNif()));
        clienteDao.elimina(generadorEjemplos.c0);
        Assert.assertNull(clienteDao.recuperaPorNif(generadorEjemplos.c0.getNif()));
    }

    @Test
    public void test04_Modificacion() {

        Cliente c1, c2;
        String nuevoNombre;

        log.info("");
        log.info("Configurando situación de partida del test -----------------------------------------------------------------------");

        generadorEjemplos.crearClientesSueltos();
        generadorEjemplos.grabarClientes();

        log.info("");
        log.info("Inicio del test --------------------------------------------------------------------------------------------------");
        log.info("Objetivo: Prueba de modificación de la información básica de un cliente sin máquinas\n");

        // Situación de partida:
        // c0 desligado

        nuevoNombre = new String ("Nombre nuevo");

        c1 = clienteDao.recuperaPorNif(generadorEjemplos.c0.getNif());
        Assert.assertNotEquals(nuevoNombre, c1.getNombrePila());
        c1.setNombrePila(nuevoNombre);

        clienteDao.modifica(c1);

        c2 = clienteDao.recuperaPorNif(generadorEjemplos.c0.getNif());
        Assert.assertEquals (nuevoNombre, c2.getNombrePila());

    }

    /*
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
    }
     */

}