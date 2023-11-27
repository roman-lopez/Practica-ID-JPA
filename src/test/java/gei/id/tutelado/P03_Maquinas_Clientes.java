package gei.id.tutelado;

import gei.id.tutelado.dao.cliente.ClienteDao;
import gei.id.tutelado.model.EntradaLog;
import org.junit.FixMethodOrder;
import org.junit.runners.MethodSorters;

import gei.id.tutelado.configuracion.Configuracion;
import gei.id.tutelado.configuracion.ConfiguracionJPA;
import gei.id.tutelado.dao.cliente.ClienteDaoJPA;
import gei.id.tutelado.dao.empleado.EmpleadoDaoJPA;
import gei.id.tutelado.dao.maquina.MaquinaDao;
import gei.id.tutelado.dao.maquina.MaquinaDaoJPA;
import gei.id.tutelado.dao.persona.PersonaDao;
import gei.id.tutelado.model.Cliente;
import gei.id.tutelado.model.Empleado;
import gei.id.tutelado.model.Maquina;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.*;
import org.junit.rules.TestRule;
import org.junit.rules.TestWatcher;
import org.junit.runner.Description;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class P03_Maquinas_Clientes {

    private Logger log = LogManager.getLogger("gei.id.tutelado");

    private static GeneradorEjemplosTests generadorEjemplos = new GeneradorEjemplosTests();

    private static Configuracion cfg;
    private static ClienteDao clienteDao;
    //private static PersonaDao empleadoDao;
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

        //empleadoDao = new EmpleadoDaoJPA();
        //empleadoDao.setup(cfg);

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

        Maquina m;

        log.info("");
        log.info("Configurando situación de partida del test -----------------------------------------------------------------------");

        //Hace falta crear los clientes para poder asignarle un valor al atributo propietario de las máquinas (obligatorio)
        generadorEjemplos.crearClientesConMaquinas();
        //Al grabar los clientes, gracias a la propagación en cascada, también grabamos sus máquinas
        generadorEjemplos.grabarClientes();

        log.info("");
        log.info("Inicio del test --------------------------------------------------------------------------------------------------");
        log.info("Objetivo: Prueba de recuperación desde la BD de máquina por código\n"
                + "\t\t\t\t Casos contemplados:\n"
                + "\t\t\t\t a) Recuperación por código existente\n"
                + "\t\t\t\t b) Recuperacion por código inexistente\n");

        // Situación de partida:
        // m0 desligada

        log.info("Probando recuperacion por código EXISTENTE --------------------------------------------------");

        m = maquinaDao.recuperaPorCodigo(generadorEjemplos.m0.getCodMaquina());
        Assert.assertEquals(generadorEjemplos.m0.getCodMaquina(),      m.getCodMaquina());
        Assert.assertEquals(generadorEjemplos.m0.getModelo(),     m.getModelo());
        Assert.assertEquals(generadorEjemplos.m0.getPropietario(), m.getPropietario());

        log.info("");
        log.info("Probando recuperacion por código INEXISTENTE -----------------------------------------------");

        m = maquinaDao.recuperaPorCodigo("iwbvyhuebvuwebvi");
        Assert.assertNull (m);

    }


    @Test
    public void test02_Alta() {


        log.info("");
        log.info("Configurando situación de partida del test -----------------------------------------------------------------------");

        generadorEjemplos.crearClientesSueltos();
        generadorEjemplos.grabarClientes();
        generadorEjemplos.crearMaquinasSueltas();

        log.info("");
        log.info("Inicio del test --------------------------------------------------------------------------------------------------");
        log.info("Objetivo: Prueba de grabación de maquinas sueltas\n"
                + "\t\t\t\t Casos contemplados:\n"
                + "\t\t\t\t a) Primera maquina vinculada a un cliente\n"
                + "\t\t\t\t b) Nueva maquina para un cliente con maquinas ya asignadas\n");

        // Situación de partida:
        // c0 desligado
        // m0, m1 transitorias

        generadorEjemplos.c0.agregarMaquina(generadorEjemplos.m0);

        log.info("");
        log.info("Grabando primera maquina de un cliente --------------------------------------------------------------------");
        Assert.assertNull(generadorEjemplos.m0.getIdMaquina());
        maquinaDao.almacena(generadorEjemplos.m0);
        Assert.assertNotNull(generadorEjemplos.m0.getIdMaquina());

        generadorEjemplos.c0.agregarMaquina(generadorEjemplos.m1);

        log.info("");
        log.info("Gravando segunda maquina de un cliente ---------------------------------------------------------------------");
        Assert.assertNull(generadorEjemplos.m1.getIdMaquina());
        maquinaDao.almacena(generadorEjemplos.m1);
        Assert.assertNotNull(generadorEjemplos.m1.getIdMaquina());

    }

    @Test
    public void test03_Eliminacion() {

        log.info("");
        log.info("Configurando situación de partida del test -----------------------------------------------------------------------");

        generadorEjemplos.crearClientesConMaquinas();
        generadorEjemplos.grabarClientes();

        log.info("");
        log.info("Inicio del test --------------------------------------------------------------------------------------------------");
        log.info("Objetivo: Prueba de eliminación de maquina suelta (asignada a un cliente)\n");

        // Situación de partida:
        // m0 desligado

        Assert.assertNotNull(maquinaDao.recuperaPorCodigo(generadorEjemplos.m0.getCodMaquina()));
        maquinaDao.elimina(generadorEjemplos.m0);
        Assert.assertNull(maquinaDao.recuperaPorCodigo(generadorEjemplos.m0.getCodMaquina()));

    }

    @Test
    public void test04_Modificacion() {

        Maquina m1, m2;
        String nuevoModelo;

        log.info("");
        log.info("Configurando situación de partida del test -----------------------------------------------------------------------");

        generadorEjemplos.crearClientesConMaquinas();
        generadorEjemplos.grabarClientes();

        log.info("");
        log.info("Inicio del test --------------------------------------------------------------------------------------------------");
        log.info("Objetivo: Prueba de modificación de la información de una maquina suelta\n");


        // Situación de partida:
        // m0 desligada

        nuevoModelo = new String ("Modelo nuevo");

        m1 = maquinaDao.recuperaPorCodigo(generadorEjemplos.m0.getCodMaquina());

        Assert.assertNotEquals(nuevoModelo, m1.getModelo());
        m1.setModelo(nuevoModelo);

        maquinaDao.modifica(m1);

        m2 = maquinaDao.recuperaPorCodigo(generadorEjemplos.m0.getCodMaquina());
        Assert.assertEquals (nuevoModelo, m2.getModelo());

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