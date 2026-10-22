/*
 LEON GAMEZ FERNANDO
 CAMACHO GUERRA JACOBO
 GONZALEZ ALVARADO ALEXA MICHELLE
*/

public class ListaCircular<T> {

    private Nodo<T> primero;
    private Nodo<T> ultimo;
    private Nodo<T> actual;
    private int tamano;

    public ListaCircular() {
        this.primero = null;
        this.ultimo = null;
        this.actual = null;
        this.tamano = 0;
    }

    // ======================================================
    // Métodos auxiliares básicos
    // ======================================================

    public boolean estaVacia() {
        return tamano == 0;
    }

    public int getTamano() {
        return tamano;
    }

    public void vaciar() {
        primero = null;
        ultimo = null;
        actual = null;
        tamano = 0;
    }

    // ======================================================
    // Validaciones privadas
    // ======================================================

    private void validarNoVacia() {
        if (estaVacia()) {
            throw new IllegalStateException("La lista circular está vacía.");
        }
    }

    private void validarDatoNoNulo(T dato) {
        if (dato == null) {
            throw new IllegalArgumentException("El dato a insertar no puede ser nulo.");
        }
    }

    private void validarPosicionLectura(int posicion) {
        if (posicion < 0 || posicion >= tamano) {
            throw new IndexOutOfBoundsException(
                "La posición debe estar entre 0 y " + (tamano - 1) + ". Posición recibida: " + posicion
            );
        }
    }

    private void validarPosicionInsercion(int posicion) {
        if (posicion < 0 || posicion > tamano) {
            throw new IndexOutOfBoundsException(
                "La posición de inserción debe estar entre 0 y " + tamano + ". Posición recibida: " + posicion
            );
        }
    }

    // ======================================================
    // Búsqueda privada de nodo por posición
    // ======================================================

    private Nodo<T> obtenerNodo(int posicion) {
        validarPosicionLectura(posicion);

        Nodo<T> auxiliar;

        if (posicion <= tamano / 2) {
            auxiliar = primero;
            for (int i = 0; i < posicion; i++) {
                auxiliar = auxiliar.getSiguiente();
            }
        } else {
            auxiliar = ultimo;
            for (int i = tamano - 1; i > posicion; i--) {
                auxiliar = auxiliar.getAnterior();
            }
        }

        return auxiliar;
    }

    // ======================================================
    // Inserciones
    // ======================================================

    public void insertarInicio(T dato) {
        validarDatoNoNulo(dato);

        Nodo<T> nuevo = new Nodo<>(dato);

        if (estaVacia()) {
            nuevo.setSiguiente(nuevo);
            nuevo.setAnterior(nuevo);

            primero = nuevo;
            ultimo = nuevo;
            actual = nuevo;
        } else {
            nuevo.setSiguiente(primero);
            nuevo.setAnterior(ultimo);

            ultimo.setSiguiente(nuevo);
            primero.setAnterior(nuevo);

            primero = nuevo;

            if (actual == null) {
                actual = primero;
            }
        }

        tamano++;
    }

    public void insertarFinal(T dato) {
        validarDatoNoNulo(dato);

        if (estaVacia()) {
            insertarInicio(dato);
            return;
        }

        Nodo<T> nuevo = new Nodo<>(dato);

        nuevo.setSiguiente(primero);
        nuevo.setAnterior(ultimo);

        ultimo.setSiguiente(nuevo);
        primero.setAnterior(nuevo);

        ultimo = nuevo;

        if (actual == null) {
            actual = primero;
        }

        tamano++;
    }

    public void insertarPosicion(T dato, int posicion) {
        validarDatoNoNulo(dato);
        validarPosicionInsercion(posicion);

        if (posicion == 0) {
            insertarInicio(dato);
            return;
        }

        if (posicion == tamano) {
            insertarFinal(dato);
            return;
        }

        Nodo<T> nodoEnPosicion = obtenerNodo(posicion);
        Nodo<T> nodoAnterior = nodoEnPosicion.getAnterior();

        Nodo<T> nuevo = new Nodo<>(dato);

        nuevo.setSiguiente(nodoEnPosicion);
        nuevo.setAnterior(nodoAnterior);

        nodoAnterior.setSiguiente(nuevo);
        nodoEnPosicion.setAnterior(nuevo);

        if (actual == null) {
            actual = primero;
        }

        tamano++;
    }

    // ======================================================
    // Remociones
    // ======================================================

    public T removerInicio() {
        validarNoVacia();

        Nodo<T> nodoRemovido = primero;
        T datoRemovido = nodoRemovido.getDato();

        if (tamano == 1) {
            vaciar();
            return datoRemovido;
        }

        Nodo<T> nuevoPrimero = primero.getSiguiente();

        ultimo.setSiguiente(nuevoPrimero);
        nuevoPrimero.setAnterior(ultimo);

        if (actual == nodoRemovido) {
            actual = nuevoPrimero;
        }

        primero = nuevoPrimero;
        tamano--;

        nodoRemovido.setSiguiente(null);
        nodoRemovido.setAnterior(null);

        return datoRemovido;
    }

    public T removerFinal() {
        validarNoVacia();

        Nodo<T> nodoRemovido = ultimo;
        T datoRemovido = nodoRemovido.getDato();

        if (tamano == 1) {
            vaciar();
            return datoRemovido;
        }

        Nodo<T> nuevoUltimo = ultimo.getAnterior();

        nuevoUltimo.setSiguiente(primero);
        primero.setAnterior(nuevoUltimo);

        if (actual == nodoRemovido) {
            actual = nuevoUltimo;
        }

        ultimo = nuevoUltimo;
        tamano--;

        nodoRemovido.setSiguiente(null);
        nodoRemovido.setAnterior(null);

        return datoRemovido;
    }

    public T removerPosicion(int posicion) {
        validarNoVacia();
        validarPosicionLectura(posicion);

        if (posicion == 0) {
            return removerInicio();
        }

        if (posicion == tamano - 1) {
            return removerFinal();
        }

        Nodo<T> nodoRemovido = obtenerNodo(posicion);
        T datoRemovido = nodoRemovido.getDato();

        Nodo<T> nodoAnterior = nodoRemovido.getAnterior();
        Nodo<T> nodoSiguiente = nodoRemovido.getSiguiente();

        nodoAnterior.setSiguiente(nodoSiguiente);
        nodoSiguiente.setAnterior(nodoAnterior);

        if (actual == nodoRemovido) {
            actual = nodoSiguiente;
        }

        tamano--;

        nodoRemovido.setSiguiente(null);
        nodoRemovido.setAnterior(null);

        return datoRemovido;
    }

    // ======================================================
    // Muestras puntuales
    // ======================================================

    public T mostrarInicio() {
        validarNoVacia();
        return primero.getDato();
    }

    public T mostrarFinal() {
        validarNoVacia();
        return ultimo.getDato();
    }

    public T mostrarPosicion(int posicion) {
        validarNoVacia();
        return obtenerNodo(posicion).getDato();
    }

    // ======================================================
    // Navegación circular obligatoria
    // ======================================================

    public T obtenerActual() {
        validarNoVacia();

        if (actual == null) {
            actual = primero;
        }

        return actual.getDato();
    }

    public T avanzarActual() {
        validarNoVacia();

        if (actual == null) {
            actual = primero;
        } else {
            actual = actual.getSiguiente();
        }

        return actual.getDato();
    }

    public T retrocederActual() {
        validarNoVacia();

        if (actual == null) {
            actual = primero;
        } else {
            actual = actual.getAnterior();
        }

        return actual.getDato();
    }

    public void colocarActualEnInicio() {
        validarNoVacia();
        actual = primero;
    }

    public void colocarActualEnFinal() {
        validarNoVacia();
        actual = ultimo;
    }

    public void colocarActualEnPosicion(int posicion) {
        validarNoVacia();
        actual = obtenerNodo(posicion);
    }

    public int obtenerPosicionActual() {
        validarNoVacia();

        if (actual == null) {
            actual = primero;
        }

        Nodo<T> auxiliar = primero;

        for (int i = 0; i < tamano; i++) {
            if (auxiliar == actual) {
                return i;
            }
            auxiliar = auxiliar.getSiguiente();
        }

        actual = primero;
        return 0;
    }

    // ======================================================
    // Búsquedas
    // ======================================================

    public int buscarPosicionPorValor(String valor, CriterioBusqueda<T> criterio) {
        validarNoVacia();

        if (valor == null || valor.trim().isEmpty()) {
            throw new IllegalArgumentException("El valor de búsqueda no puede estar vacío.");
        }

        if (criterio == null) {
            throw new IllegalArgumentException("El criterio de búsqueda no puede ser nulo.");
        }

        String valorBuscado = valor.trim();
        Nodo<T> auxiliar = primero;

        for (int i = 0; i < tamano; i++) {
            if (criterio.coincide(auxiliar.getDato(), valorBuscado)) {
                return i;
            }
            auxiliar = auxiliar.getSiguiente();
        }

        return -1;
    }

    public T buscarPorPosicion(int posicion) {
        validarNoVacia();
        return obtenerNodo(posicion).getDato();
    }

    // ======================================================
    // Recorridos
    // ======================================================

    public String recorrerAdelante() {
        if (estaVacia()) {
            return "Lista circular vacía.";
        }

        StringBuilder constructor = new StringBuilder();
        Nodo<T> auxiliar = primero;

        for (int i = 0; i < tamano; i++) {
            constructor.append("Posición ").append(i).append(": ")
                     .append(auxiliar.getDato().toString())
                     .append("\n");
            auxiliar = auxiliar.getSiguiente();
        }

        return constructor.toString();
    }

    public String recorrerAtras() {
        if (estaVacia()) {
            return "Lista circular vacía.";
        }

        StringBuilder constructor = new StringBuilder();
        Nodo<T> auxiliar = ultimo;

        for (int i = tamano - 1; i >= 0; i--) {
            constructor.append("Posición ").append(i).append(": ")
                     .append(auxiliar.getDato().toString())
                     .append("\n");
            auxiliar = auxiliar.getAnterior();
        }

        return constructor.toString();
    }
}