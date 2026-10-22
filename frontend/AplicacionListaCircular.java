/*
 LEON GAMEZ FERNANDO
 CAMACHO GUERRA JACOBO
 GONZALEZ ALVARADO ALEXA MICHELLE
*/

import javax.swing.*;
import java.awt.*;

public class AplicacionListaCircular extends JFrame {

    private final ListaCircular<Proceso> lista;

    private JTextField txtId;
    private JTextField txtNombre;
    private JTextField txtRafaga;
    private JTextField txtPosicion;
    private JTextField txtBusqueda;

    private JLabel lblActual;
    private JTextArea areaEstado;

    public AplicacionListaCircular() {
        lista = new ListaCircular<>();
        initComponents();
        actualizarEstado();
    }

    private void initComponents() {
        setTitle("Lista Circular Doblemente Enlazada con Records");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(1000, 680);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(8, 8));

        add(crearPanelDatos(), BorderLayout.NORTH);
        add(crearPanelBotones(), BorderLayout.CENTER);
        add(crearPanelEstado(), BorderLayout.SOUTH);
    }

    private JPanel crearPanelDatos() {
        JPanel panel = new JPanel(new GridLayout(3, 4, 6, 6));
        panel.setBorder(BorderFactory.createTitledBorder("Datos del proceso y controles de posición/búsqueda"));

        txtId = new JTextField();
        txtNombre = new JTextField();
        txtRafaga = new JTextField();
        txtPosicion = new JTextField();
        txtBusqueda = new JTextField();

        panel.add(new JLabel("ID:"));
        panel.add(txtId);

        panel.add(new JLabel("Nombre:"));
        panel.add(txtNombre);

        panel.add(new JLabel("Ráfaga (ms):"));
        panel.add(txtRafaga);

        panel.add(new JLabel("Posición:"));
        panel.add(txtPosicion);

        panel.add(new JLabel("Buscar ID o nombre:"));
        panel.add(txtBusqueda);

        panel.add(new JLabel(""));
        panel.add(new JLabel(""));
        panel.add(new JLabel(""));

        return panel;
    }

    private JPanel crearPanelBotones() {
        JPanel panel = new JPanel(new GridLayout(5, 4, 6, 6));
        panel.setBorder(BorderFactory.createTitledBorder("Operaciones sobre la lista circular"));

        agregarBoton(panel, "Insertar Inicio");
        agregarBoton(panel, "Insertar Final");
        agregarBoton(panel, "Insertar Posición");

        agregarBoton(panel, "Remover Inicio");
        agregarBoton(panel, "Remover Final");
        agregarBoton(panel, "Remover Posición");

        agregarBoton(panel, "Mostrar Inicio");
        agregarBoton(panel, "Mostrar Final");
        agregarBoton(panel, "Mostrar Posición");

        agregarBoton(panel, "Buscar por Valor");
        agregarBoton(panel, "Buscar por Posición");

        agregarBoton(panel, "Siguiente");
        agregarBoton(panel, "Anterior");

        agregarBoton(panel, "Recorrer Adelante");
        agregarBoton(panel, "Recorrer Atrás");

        agregarBoton(panel, "Vaciar");
        agregarBoton(panel, "Actualizar");
        agregarBoton(panel, "Salir");

        panel.add(new JLabel(""));
        panel.add(new JLabel(""));

        return panel;
    }

    private void agregarBoton(JPanel panel, String texto) {
        JButton boton = new JButton(texto);
        boton.addActionListener(e -> ejecutarAccion(e.getActionCommand()));
        panel.add(boton);
    }

    private JPanel crearPanelEstado() {
        JPanel panel = new JPanel(new BorderLayout(5, 5));
        panel.setBorder(BorderFactory.createTitledBorder("Estado de la lista circular"));

        lblActual = new JLabel("Nodo actual: ninguno");
        areaEstado = new JTextArea(10, 50);
        areaEstado.setEditable(false);
        areaEstado.setFont(new Font("Consolas", Font.PLAIN, 12));

        JScrollPane scroll = new JScrollPane(areaEstado);

        panel.add(lblActual, BorderLayout.NORTH);
        panel.add(scroll, BorderLayout.CENTER);

        return panel;
    }

    private void ejecutarAccion(String comando) {
        try {
            switch (comando) {
                case "Insertar Inicio":
                    insertar(0);
                    break;

                case "Insertar Final":
                    insertar(1);
                    break;

                case "Insertar Posición":
                    insertar(2);
                    break;

                case "Remover Inicio":
                    remover(0);
                    break;

                case "Remover Final":
                    remover(1);
                    break;

                case "Remover Posición":
                    remover(2);
                    break;

                case "Mostrar Inicio":
                    mostrarInicio();
                    break;

                case "Mostrar Final":
                    mostrarFinal();
                    break;

                case "Mostrar Posición":
                    mostrarPosicion();
                    break;

                case "Buscar por Valor":
                    buscarPorValor();
                    break;

                case "Buscar por Posición":
                    buscarPorPosicion();
                    break;

                case "Siguiente":
                    lista.avanzarActual();
                    actualizarEstado();
                    break;

                case "Anterior":
                    lista.retrocederActual();
                    actualizarEstado();
                    break;

                case "Recorrer Adelante":
                    recorrerAdelante();
                    break;

                case "Recorrer Atrás":
                    recorrerAtras();
                    break;

                case "Vaciar":
                    vaciarLista();
                    break;

                case "Actualizar":
                    actualizarEstado();
                    break;

                case "Salir":
                    System.exit(0);
                    break;

                default:
                    break;
            }
        } catch (IllegalArgumentException | IllegalStateException | IndexOutOfBoundsException ex) {
            mostrarError(ex.getMessage());
        }
    }

    // ======================================================
    // Operaciones de inserción
    // ======================================================

    private void insertar(int tipo) {
        Proceso proceso = crearProceso();

        if (tipo == 0) {
            lista.insertarInicio(proceso);
        } else if (tipo == 1) {
            lista.insertarFinal(proceso);
        } else {
            int posicion = obtenerPosicion();
            lista.insertarPosicion(proceso, posicion);
        }

        limpiarCamposDeDatos();
        actualizarEstado();
    }

    // ======================================================
    // Operaciones de remoción
    // ======================================================

    private void remover(int tipo) {
        String mensaje;

        if (tipo == 0) {
            mensaje = "Se removió del inicio:\n" + lista.removerInicio().toString();
        } else if (tipo == 1) {
            mensaje = "Se removió del final:\n" + lista.removerFinal().toString();
        } else {
            int posicion = obtenerPosicion();
            mensaje = "Se removió de la posición " + posicion + ":\n" + lista.removerPosicion(posicion).toString();
        }

        actualizarEstado();
        mostrarInfo(mensaje);
    }

    // ======================================================
    // Muestras puntuales
    // ======================================================

    private void mostrarInicio() {
        lista.colocarActualEnInicio();
        mostrarInfo("Nodo inicial:\n" + lista.obtenerActual().toString());
        actualizarEstado();
    }

    private void mostrarFinal() {
        lista.colocarActualEnFinal();
        mostrarInfo("Nodo final:\n" + lista.obtenerActual().toString());
        actualizarEstado();
    }

    private void mostrarPosicion() {
        int posicion = obtenerPosicion();
        lista.colocarActualEnPosicion(posicion);
        mostrarInfo("Nodo en posición " + posicion + ":\n" + lista.obtenerActual().toString());
        actualizarEstado();
    }

    // ======================================================
    // Búsquedas
    // ======================================================

    private void buscarPorValor() {
        String valor = txtBusqueda.getText().trim();

        if (valor.isEmpty()) {
            throw new IllegalArgumentException("Ingrese un ID o nombre para buscar.");
        }

        int posicion = lista.buscarPosicionPorValor(valor, (proceso, texto) -> {
            String objetivo = texto.trim();
            return Integer.toString(proceso.id()).equalsIgnoreCase(objetivo)
                || proceso.nombre().equalsIgnoreCase(objetivo);
        });

        if (posicion == -1) {
            mostrarInfo("No se encontró el valor '" + valor + "' en la lista circular.");
        } else {
            lista.colocarActualEnPosicion(posicion);
            mostrarInfo("Valor encontrado en posición " + posicion + ":\n" + lista.obtenerActual().toString());
            actualizarEstado();
        }
    }

    private void buscarPorPosicion() {
        int posicion = obtenerPosicion();
        lista.colocarActualEnPosicion(posicion);
        mostrarInfo("Elemento encontrado en posición " + posicion + ":\n" + lista.obtenerActual().toString());
        actualizarEstado();
    }

    // ======================================================
    // Recorridos
    // ======================================================

    private void recorrerAdelante() {
        if (lista.estaVacia()) {
            areaEstado.setText("Lista circular vacía.");
            lblActual.setText("Nodo actual: ninguno");
        } else {
            areaEstado.setText("Recorrido directo:\n\n" + lista.recorrerAdelante());
            actualizarEtiquetaActual();
        }
    }

    private void recorrerAtras() {
        if (lista.estaVacia()) {
            areaEstado.setText("Lista circular vacía.");
            lblActual.setText("Nodo actual: ninguno");
        } else {
            areaEstado.setText("Recorrido inverso:\n\n" + lista.recorrerAtras());
            actualizarEtiquetaActual();
        }
    }

    // ======================================================
    // Vaciar y actualizar vista
    // ======================================================

    private void vaciarLista() {
        int opcion = JOptionPane.showConfirmDialog(
            this,
            "¿Está seguro de vaciar completamente la lista circular?",
            "Confirmar vaciado",
            JOptionPane.YES_NO_OPTION
        );

        if (opcion == JOptionPane.YES_OPTION) {
            lista.vaciar();
            actualizarEstado();
        }
    }

    private void actualizarEstado() {
        if (lista.estaVacia()) {
            areaEstado.setText("Lista circular vacía.");
            lblActual.setText("Nodo actual: ninguno");
            return;
        }

        String texto = "Recorrido directo:\n\n"
            + lista.recorrerAdelante()
            + "\nRecorrido inverso:\n\n"
            + lista.recorrerAtras();

        areaEstado.setText(texto);
        actualizarEtiquetaActual();
    }

    private void actualizarEtiquetaActual() {
        if (lista.estaVacia()) {
            lblActual.setText("Nodo actual: ninguno");
            return;
        }

        int posicionActual = lista.obtenerPosicionActual();
        lblActual.setText("Nodo actual (posición " + posicionActual + "): " + lista.obtenerActual().toString());
    }

    // ======================================================
    // Validaciones de entrada
    // ======================================================

    private Proceso crearProceso() {
        int id = leerEntero(txtId, "El ID");
        String nombre = txtNombre.getText().trim();
        int rafaga = leerEntero(txtRafaga, "La ráfaga");

        return new Proceso(id, nombre, rafaga);
    }

    private int obtenerPosicion() {
        return leerEntero(txtPosicion, "La posición");
    }

    private int leerEntero(JTextField campo, String nombreCampo) {
        String texto = campo.getText().trim();

        if (texto.isEmpty()) {
            throw new IllegalArgumentException(nombreCampo + " es obligatorio.");
        }

        try {
            return Integer.parseInt(texto);
        } catch (NumberFormatException ex) {
            throw new IllegalArgumentException(nombreCampo + " debe ser un número entero válido.");
        }
    }

    private void limpiarCamposDeDatos() {
        txtId.setText("");
        txtNombre.setText("");
        txtRafaga.setText("");
        txtId.requestFocus();
    }

    // ======================================================
    // Mensajes
    // ======================================================

    private void mostrarInfo(String mensaje) {
        JOptionPane.showMessageDialog(this, mensaje, "Información", JOptionPane.INFORMATION_MESSAGE);
    }

    private void mostrarError(String mensaje) {
        JOptionPane.showMessageDialog(this, mensaje, "Error", JOptionPane.ERROR_MESSAGE);
    }

    // ======================================================
    // Punto de entrada
    // ======================================================

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            AplicacionListaCircular ventana = new AplicacionListaCircular();
            ventana.setVisible(true);
        });
    }
}