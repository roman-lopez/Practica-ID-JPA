package gei.id.tutelado;

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
public class P03_Maquinas_Clientes {/*

    private Logger log = LogManager.getLogger("gei.id.tutelado");

    private static GeneradorEjemplosTests generadorEjemplos = new GeneradorEjemplosTests();

    private static Configuracion cfg;
    private static PersonaDao clienteDao;
    private static PersonaDao empleadoDao;
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

    public void test01_RecuperacionClientes() {

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

    public void test02_RecuperacionEmpleados() {

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

    public void test03_RecuperacionMaquinas() {

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
    public void test01_AltaClientes() {

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
        Assert.assertNotNull(generadorEjemplos.c0.getIdPersona());
    }

    @Test
    public void test02_AltaEmpleados() {

        log.info("");
        log.info("Configurando situación de partida do test -----------------------------------------------------------------------");

        generadorEjemplos.crearEmpleadosSueltos();

        log.info("");
        log.info("Inicio del test --------------------------------------------------------------------------------------------------");
        log.info("Objetivo: Prueba de grabación en la BD de nuevo empleado (sin máquinas asociadas)\n");

        // Situación de partida:
        // c0 transitorio

        Assert.assertNull(generadorEjemplos.e0.getIdPersona());
        empleadoDao.almacena(generadorEjemplos.e0);
        Assert.assertNotNull(generadorEjemplos.e0.getIdPersona());
    }

    @Test
    public void test03_AltaMaquinas() {

        log.info("");
        log.info("Configurando situación de partida do test -----------------------------------------------------------------------");

        produtorDatos.creaUsuariosSoltos();
        produtorDatos.gravaUsuarios();
        produtorDatos.creaEntradasLogSoltas();

        log.info("");
        log.info("Inicio do test --------------------------------------------------------------------------------------------------");
        log.info("Obxectivo: Proba da gravación de entradas de log soltas\n"
                + "\t\t\t\t Casos contemplados:\n"
                + "\t\t\t\t a) Primeira entrada de log vinculada a un usuario\n"
                + "\t\t\t\t b) Nova entrada de log para un usuario con entradas previas\n");

        // Situación de partida:
        // u1 desligado
        // e1A, e1B transitorios

        produtorDatos.u1.engadirEntradaLog(produtorDatos.e1A);

        log.info("");
        log.info("Gravando primeira entrada de log dun usuario --------------------------------------------------------------------");
        Assert.assertNull(produtorDatos.e1A.getId());
        logDao.almacena(produtorDatos.e1A);
        Assert.assertNotNull(produtorDatos.e1A.getId());

        produtorDatos.u1.engadirEntradaLog(produtorDatos.e1B);

        log.info("");
        log.info("Gravando segunda entrada de log dun usuario ---------------------------------------------------------------------");
        Assert.assertNull(produtorDatos.e1B.getId());
        logDao.almacena(produtorDatos.e1B);
        Assert.assertNotNull(produtorDatos.e1B.getId());

    }




    @Test
    public void test03_Eliminacion() {

        log.info("");
        log.info("Configurando situación de partida do test -----------------------------------------------------------------------");

        produtorDatos.creaUsuariosSoltos();
        produtorDatos.gravaUsuarios();


        log.info("");
        log.info("Inicio do test --------------------------------------------------------------------------------------------------");
        log.info("Obxectivo: Proba de eliminación da BD de usuario sen entradas asociadas\n");

        // Situación de partida:
        // u0 desligado

        Assert.assertNotNull(usuDao.recuperaPorNif(produtorDatos.u0.getNif()));
        usuDao.elimina(produtorDatos.u0);
        Assert.assertNull(usuDao.recuperaPorNif(produtorDatos.u0.getNif()));
    }

    @Test
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
    }
*/
}