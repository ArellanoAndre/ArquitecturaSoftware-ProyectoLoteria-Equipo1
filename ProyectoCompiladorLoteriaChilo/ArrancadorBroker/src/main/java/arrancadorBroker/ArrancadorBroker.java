package arrancadorBroker;

import brokerEnsamblador.EnsambladorBroker;

public class ArrancadorBroker {

    public static void main(String[] args) throws Exception {

        EnsambladorBroker arrancador = new EnsambladorBroker(7777);
        arrancador.ensamblar();
    }
}
