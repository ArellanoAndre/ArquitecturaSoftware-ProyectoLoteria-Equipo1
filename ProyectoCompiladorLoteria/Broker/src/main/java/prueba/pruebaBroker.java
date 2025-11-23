import Broker.Broker;
import Broker.Suscripcion;
import assembler.AssemblerBroker;
import java.util.Arrays;

public class pruebaBroker {

    //Odio las pruebas, sorry si esta bien evidente que viene directo del bestie
    public static void main(String[] args) {

        System.out.println("=== INICIANDO PRUEBA DEL BROKER ===");

        // 1️⃣ Crear el assembler (con colas, listener, desempaquetador, etc.)
        AssemblerBroker assembler = new AssemblerBroker();

        // 2️⃣ Obtener broker real
        Broker broker = assembler.getBroker();

        // 3️⃣ Crear suscriptores de prueba
        Suscripcion s1 = new Suscripcion("localhost", 9001);
        Suscripcion s2 = new Suscripcion("localhost", 9002);
        Suscripcion s3 = new Suscripcion("localhost", 9003);
        Suscripcion s4 = new Suscripcion("localhost", 9004);

        // 4️⃣ Registrar listas de prueba
        Arrays.asList(s1, s2).forEach(s -> broker.registrarSuscripcion("Loteria", s));
        Arrays.asList(s3, s4).forEach(s -> broker.registrarSuscripcion("Deportes", s));


        // 5️⃣ Eliminar uno existente
        broker.eliminarSuscripcion("Loteria", s2);

        // 6️⃣ Eliminar uno inexistente
        broker.eliminarSuscripcion("Loteria", new Suscripcion("localhost", 9999));

        // 7️⃣ Eliminar último → borrar tópico
        broker.eliminarSuscripcion("Deportes", s3);
        broker.eliminarSuscripcion("Deportes", s4);

        // 8️⃣ Tópico inexistente
        broker.eliminarSuscripcion("Memes", s1);

        System.out.println("✅ PRUEBA FINALIZADA EXITOSAMENTE ✅");
    }
    

    // 🔍 Método para ver qué tiene el broker adentro
    private static void imprimirEstado(Broker broker) {
        System.out.println("\n===== ESTADO ACTUAL DEL BROKER =====");

        if (broker.obtenerSuscriptores("Deportes").isEmpty()) {
            System.out.println("🚫 No hay tópicos registrados.");
            return;
        }

        System.out.println("====================================\n");
    }
}
