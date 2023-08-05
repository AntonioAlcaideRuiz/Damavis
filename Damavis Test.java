import java.util.*;

public class LaberintoConBarra {

    static class EstadoBarra {
        int x, y; // Posición actual
        boolean horizontal; // Orientación actual (horizontal = true, vertical = false)
        int movimientos; // Número de movimientos realizados para llegar a este estado

        public EstadoBarra(int x, int y, boolean horizontal, int movimientos) {
            this.x = x;
            this.y = y;
            this.horizontal = horizontal;
            this.movimientos = movimientos;
        }
    }

    // Función auxiliar para comprobar si el área 3x3 alrededor de la barra está
    // despejada para la rotación
    private static boolean estaDespejadoParaRotacion(List<List<Character>> laberinto, int x, int y) {
        for (int i = x - 1; i <= x + 1; i++) {
            for (int j = y - 1; j <= y + 1; j++) {
                if (i < 0 || i >= laberinto.size() || j < 0 || j >= laberinto.get(i).size()
                        || laberinto.get(i).get(j) == '#') {
                    return false;
                }
            }
        }
        return true;
    }

    public static int solucion(List<List<Character>> laberinto) {
        int n = laberinto.size();
        int m = laberinto.get(0).size();

        int[] dx = { -1, 0, 1, 0 };
        int[] dy = { 0, 1, 0, -1 };

        Queue<EstadoBarra> cola = new LinkedList<>();
        boolean[][][] visitado = new boolean[n][m][2];

        cola.add(new EstadoBarra(0, 0, true, 0));
        visitado[0][0][0] = true;
        visitado[0][0][1] = true;

        while (!cola.isEmpty()) {
            EstadoBarra actual = cola.poll();

            if (actual.x == n - 1 && actual.y == m - 1) {
                return actual.movimientos;
            }

            for (int i = 0; i < 5; i++) {
                int nuevaX = actual.x;
                int nuevaY = actual.y;
                boolean nuevaHorizontal = actual.horizontal;

                if (i == 0) { // Mover hacia arriba
                    nuevaX--;
                } else if (i == 1) { // Mover hacia la derecha
                    nuevaY++;
                } else if (i == 2) { // Mover hacia abajo
                    nuevaX++;
                } else if (i == 3) { // Mover hacia la izquierda
                    nuevaY--;
                } else if (i == 4) { // Rotar la orientación
                    if (actual.horizontal) {
                        // Comprobar si la rotación es posible
                        if (estaDespejadoParaRotacion(laberinto, actual.x, actual.y)) {
                            nuevaHorizontal = false;
                        } else {
                            continue; // No se puede rotar en la posición actual
                        }
                    } else {
                        nuevaHorizontal = true;
                    }
                }

                if (nuevaX >= 0 && nuevaX < n && nuevaY >= 0 && nuevaY < m &&
                        laberinto.get(nuevaX).get(nuevaY) == '.' &&
                        !visitado[nuevaX][nuevaY][nuevaHorizontal ? 0 : 1]) {

                    cola.add(new EstadoBarra(nuevaX, nuevaY, nuevaHorizontal, actual.movimientos + 1));
                    visitado[nuevaX][nuevaY][nuevaHorizontal ? 0 : 1] = true;
                }
            }
        }

        return -1;
    }

    public static void main(String[] args) { // Laberinto 4
        List<List<Character>> laberinto = new ArrayList<>();
        laberinto.add(Arrays.asList('.', '.', '.', '.', '.', '.', '.', '.', '.', '.'));
        laberinto.add(Arrays.asList('.', '#', '.', '.', '.', '.', '#', '.', '.', '.'));
        laberinto.add(Arrays.asList('.', '#', '.', '.', '.', '.', '.', '.', '.', '.'));
        laberinto.add(Arrays.asList('.', '.', '.', '.', '.', '.', '.', '.', '.', '.'));
        laberinto.add(Arrays.asList('.', '.', '.', '.', '.', '.', '.', '.', '.', '.'));
        laberinto.add(Arrays.asList('.', '#', '.', '.', '.', '.', '.', '.', '.', '.'));
        laberinto.add(Arrays.asList('.', '#', '.', '.', '.', '#', '.', '.', '.', '.'));
        laberinto.add(Arrays.asList('.', '.', '.', '.', '.', '.', '#', '.', '.', '.'));
        laberinto.add(Arrays.asList('.', '.', '.', '.', '.', '.', '.', '.', '.', '.'));
        laberinto.add(Arrays.asList('.', '.', '.', '.', '.', '.', '.', '.', '.', '.'));
        ;

        int resultado = solucion(laberinto);
        System.out.println("Número mínimo de movimientos requeridos: " + resultado);
    }
}
