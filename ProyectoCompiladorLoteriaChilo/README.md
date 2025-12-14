# Proyecto Compilador Lotería Chilo

Repositorio Maven multimódulo que implementa un juego de Lotería distribuido. El sistema se divide en clientes MVC que muestran la partida, un servidor de lógica que evalúa jugadas y administra rondas, un broker de eventos pub/sub y una capa de red compartida. Toda la comunicación se modela como eventos JSON que viajan por colas con observadores.

## Requisitos

- Java 17+
- Maven 3.9+

Compilación completa:

```bash
mvn clean install
```

La raíz empaca todos los módulos, de modo que puedes construir solo uno con `mvn -pl <modulo> -am package`.

## Mapa de módulos

| Módulo | Propósito |
| --- | --- |
| `Arrancador`, `ArrancadorLogica`, `ArrancadorBroker` | Aplicaciones `main` que orquestan el arranque del cliente MVC (`arrancadorMVC.ArrancadorMVC`), del servidor de lógica (`arrancadorLogica.ArrancadorLogica`) y del broker (`arrancadorBroker.ArrancadorBroker`). Cada ensamblador crea sus colas, wiring de red y registra los observadores necesarios. |
| `MVC`, `MVC_InicioJuego` | Capas de presentación Swing para la partida en curso y la fase de inicio (menú, lobby, selección de avatar). Mantienen modelos observables (`ModeloVistaJuego`, `ModeloVistaInicio`) y controladores (`ControlVista`, `ControladorInicio`). |
| `ModeloJuegoMVC` | Modelo compartido del cliente: maneja al jugador principal/secundarios, procesa eventos entrantes con una cadena (`receptor.ReceptorModelo`) y emite eventos hacia la red. |
| `LogicaJuego` | Reglas del servidor. `logicaJuego.LogicaDeJuego` gestiona cartas, rondas y validaciones mientras `modeloJuego.ModeloLogica` publica eventos de dominio y arma la cadena de procesadores de comandos entrantes. |
| `Broker` | Encapsula la cadena de responsabilidad que filtra suscripciones, desuscripciones y eventos genéricos, apoyándose en `suscripciones.gestorDeSuscripciones` y filtros específicos. |
| `CapaDeRed`, `ComunicacionEventos`, `ProcesadorEventos`, `Utilerias`, `Eventos` | Infrestructura transversal: colas con observadores, empaquetado/desempaquetado JSON (`HelperJSON`, `Empaquetador`, `Desempaquetador`), sender/dispatcher, network listener TCP y el DTO `Evento`. |
| `IEventClient`, `IRed`, `IComunicacionModelo`, `Interfaces` | Contratos que definen lo que cada capa expone (interfaces para eventos, red, entidades del modelo, controladores de vistas, etc.). |

## Flujo de eventos extremo a extremo

1. **Cliente MVC → Red**  
   - El ensamblador del cliente (`Arrancador/src/main/java/MVCEnsamblador/EnsambladorMVC.java`) crea el `ModeloJuego`, conecta controladores/vistas y arma la pila de red con `Empaquetador`, `ColaDePrioridad`, `EventSender` y `EnsambladorRed`.  
   - Cuando el usuario realiza una acción, el `ModeloJuego` construye un `IEvento`, lo envía al `Empaquetador` y este lo convierte en `EventoRed` para agregarlo a la cola de salida. `EventSender` observa la cola y delega al `Dispatcher`, quien finalmente usa el `Sender`/socket para transportarlo.

2. **Broker**  
   - `ArrancadorBroker` levanta el `EnsambladorBroker`, que crea sus colas entrada/salida y registra `EventListener` y `Desempaquetador`.  
   - El `Desempaquetador` transforma el JSON en `Evento` y lo entrega al `responsabilityChainBroker`, cuya cadena (`FiltroSuscripcion` → `FiltroDesuscripcion` → `FiltroGenericoEvento`) valida y reenvía a los clientes suscritos.

3. **Servidor de lógica**  
   - `ArrancadorLogica` arma la infraestructura de red, inyecta el `Empaquetador` en `ModeloLogica` y registra otro `Desempaquetador` que entrega eventos al modelo del servidor.  
   - `ModeloLogica` (host) construye una cadena de procesadores (`ProcesadorAsignarID`, `ProcesadorIniciarRonda`, etc.) que traducen comandos JSON a llamadas en `LogicaDeJuego`. Esta última notifica cambios (cartas cantadas, ganadores, ranking) regresando eventos por la misma ruta.

4. **Cliente MVC ← Red**  
   - En el cliente, `receptor.ReceptorModelo` recibe los `Evento` ya desempaquetados, identifica `TipoEvento` y recorre su propia cadena (`ProcesadorCartaCantada`, `ProcesadorCasillaSeleccionada`, `ProcesadorFinPartida`, etc.) para actualizar `ModeloJuego`, quien a su vez notifica a las vistas Swing correspondientes.

Todas las colas usan observadores (`colaGenerica.ObserverEntrada/Salida`) para desacoplar productores y consumidores sin hilos dedicados adicionales.

## Cómo ejecutar los componentes principales

1. **Broker**  
   ```bash
   mvn -pl ArrancadorBroker -am exec:java -Dexec.mainClass=arrancadorBroker.ArrancadorBroker
   ```
   Arranca el broker en el puerto 7000 y lo conecta con el dispatcher real (`EnsambladorBroker` configura colas y filtros).

2. **Servidor Host / lógica**  
   ```bash
   mvn -pl ArrancadorLogica -am exec:java -Dexec.mainClass=arrancadorLogica.ArrancadorLogica
   ```
   Levanta `EnsambladorLogica`, escucha en el puerto 5000 y reenvía eventos al broker (7000) en `127.0.0.1`.

3. **Cliente MVC**  
   ```bash
   mvn -pl Arrancador -am exec:java -Dexec.mainClass=arrancadorMVC.ArrancadorMVC
   ```
   Construye las pantallas de inicio y juego, se suscribe a `Juego-out` y solicita un ID al host.

Lanza múltiples clientes repitiendo el paso 3. Los puertos y IPs están parametrizados en cada ensamblador, por lo que puedes ajustarlos sin modificar la lógica central.

## Conceptos clave de la implementación

- **Cadenas de responsabilidad**: tanto el broker (`Broker/src/main/java/responsabilityChainBroker/responsabilityChainBroker.java`) como el servidor (`ModeloLogica`) y el cliente (`ReceptorModelo`) encadenan procesadores para aislar el manejo de cada tipo de evento.
- **Colas observables**: `Utilerias/src/main/java/colaGenerica/ColaDePrioridad.java` implementa un `BlockingQueue` genérico y notifica a los observadores cuando se insertan elementos, evitando sondeos activos.
- **Separación de capas**: Las interfaces viven en módulos dedicados (por ejemplo `IEventClient`, `IRed`, `IComunicacionModelo`) y son compartidas mediante Maven para mantener un contrato claro entre UI, lógica, broker y red.
- **Empaquetado JSON**: `ProcesadorEventos/src/main/java/Empaquetador/Empaquetador.java` y `Desempaquetador/Desempaquetador.java` encapsulan la traducción entre objetos Java (`IEvento`) y los mensajes JSON que viajan por TCP.

## Próximos pasos sugeridos

- Documentar los comandos disponibles en cada cadena (tipos de evento) y los payloads JSON para facilitar pruebas manuales.
- Añadir scripts de orquestación (por ejemplo, Docker compose o `make` targets) que arranquen broker, host y uno o más clientes con un solo comando.

Con este README ahora tienes una visión de alto nivel del flujo de datos, responsabilidades por módulo y comandos básicos para arrancar cada pieza del sistema.
