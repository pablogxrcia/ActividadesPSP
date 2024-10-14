import java.util.Random;

class Coche implements Runnable {
    private String nombre;
    private int distanciaRecorrida;
    private static final int DISTANCIA_META = 100;
    private boolean hayObstaculo;
    private static boolean ganadorAnunciado = false;

    public Coche(String nombre) {
        this.nombre = nombre;
        this.distanciaRecorrida = 0;
        this.hayObstaculo = false;
    }

    @Override
    public void run() {
        Random random = new Random();

        while (distanciaRecorrida < DISTANCIA_META && !ganadorAnunciado) {
            // Simular obstáculo aleatorio (20% de probabilidad de obstáculo)
            if (random.nextInt(100) < 20) {
                hayObstaculo = true;
                System.out.println(nombre + " ha encontrado un obstáculo y pierde un turno.");
                try {
                    Thread.sleep(1000); // Obstáculo ralentiza al coche
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                hayObstaculo = false; // El coche se recupera del obstáculo
                continue; // Saltar el turno de avance
            }

            // Si no hay obstáculo, avanzar
            int avance = random.nextInt(10) + 1; // Avance aleatorio entre 1 y 10
            distanciaRecorrida += avance;
            if (distanciaRecorrida > DISTANCIA_META) {
                distanciaRecorrida = DISTANCIA_META; // No exceder la meta
            }

            // Mostrar progreso
            mostrarProgreso();

            // Verificar si ha llegado a la meta
            if (distanciaRecorrida >= DISTANCIA_META && !ganadorAnunciado) {
                ganadorAnunciado = true;
                System.out.println("¡" + nombre + " ha ganado la carrera!");
            }

            // Simular tiempo de avance
            try {
                Thread.sleep(500); // Simular tiempo entre avances
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private void mostrarProgreso() {
        StringBuilder pista = new StringBuilder("|");
        for (int i = 0; i < DISTANCIA_META; i += 5) {
            if (i < distanciaRecorrida) {
                pista.append("=");
            } else if (i == distanciaRecorrida) {
                pista.append("C"); // Indicar la posición del coche
            } else {
                pista.append("-");
            }
        }
        pista.append("|");
        System.out.println(nombre + ": " + pista + " (" + distanciaRecorrida + "/" + DISTANCIA_META + ")");
    }
}


