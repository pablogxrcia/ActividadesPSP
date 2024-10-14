public class CarreraCoche {
    public static void main(String[] args) {
        System.out.println("¡Inicia la carrera!");

        Thread coche1 = new Thread(new Coche("Coche 1"));
        Thread coche2 = new Thread(new Coche("Coche 2"));
        Thread coche3 = new Thread(new Coche("Coche 3"));

        coche1.start();
        coche2.start();
        coche3.start();

        // Esperar a que todos los coches terminen la carrera
        try {
            coche1.join();
            coche2.join();
            coche3.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("¡La carrera ha terminado!");
    }
}