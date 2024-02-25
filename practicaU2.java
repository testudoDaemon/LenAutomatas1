import javax.swing.JOptionPane;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Pattern;

public class practicaU2 {

    static String archivo = "./u2/archivoP2.txt";
    static FileWriter escritor;
    static List<String> archivoPalabras = new ArrayList<>();
    static List<String> archivoPalabrasValidas = new ArrayList<>();
    static List<String> archivoPalabrasNulas = new ArrayList<>();

    public static boolean entradaDatos() {
        try {
            File file = new File(archivo);
            if (!file.exists() || file.isDirectory()) {
                JOptionPane.showMessageDialog(null, "No se pudo abrir el archivo, porque no existe o no se encuentra");
                return false;
            }
            Scanner sc = new Scanner(file);
            while (sc.hasNextLine()) {
                String[] reg = sc.nextLine().split("[,\\s1]+");
                archivoPalabras.addAll(Arrays.asList(reg));
            }
            sc.close();
            JOptionPane.showMessageDialog(null, "Archivo leído");
            return true;
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "ERROR: " + e.getMessage());
        }

        return false;
    }

    public static void main(String[] args) {
        boolean salir = false;

        String[] menu = {"1. ER Regex", "2. Opción 2", "3. Opción 3", "4. Opción 4", "5. Salir"};

        while (!salir) {
            // Mostrar el menú y obtener la opción seleccionada
            char respuesta = ((String) JOptionPane.showInputDialog(null, "Elige una opcion", "Opciones",
                    JOptionPane.QUESTION_MESSAGE, null, menu, 0)).charAt(0);

            switch (respuesta) {
                case '1' -> {
                    if (entradaDatos()) {
                        try {
                            archivoPalabrasValidas.clear();
                            archivoPalabrasNulas.clear();

                            String expresionRegular = "(a+b*c)(5|3)*";
                            Pattern pattern = Pattern.compile(expresionRegular);

                            for (String palabra : archivoPalabras) {
                                boolean coincidencia = pattern.matcher(palabra).matches();
                                if (coincidencia) {
                                    archivoPalabrasValidas.add(palabra);
                                } else {
                                    archivoPalabrasNulas.add(palabra);
                                }
                            }

                            String mensajeValidas = "Palabras válidas:\n\n";
                            for (String palabra : archivoPalabrasValidas) {
                                mensajeValidas += palabra + "\n";
                            }

                            JOptionPane.showMessageDialog(null, mensajeValidas, "Palabras válidas", JOptionPane.INFORMATION_MESSAGE);

                            // Mostrar las palabras no válidas
                            String mensajeNoValidas = "Palabras no válidas:\n\n";
                            for (String palabra : archivoPalabrasNulas) {
                                mensajeNoValidas += palabra + "\n";
                            }

                            JOptionPane.showMessageDialog(null, mensajeNoValidas, "Palabras no válidas", JOptionPane.INFORMATION_MESSAGE);

                        } catch (NumberFormatException e) {
                            JOptionPane.showMessageDialog(null, "ERROR: Tiene que ingresar un valor entero!");
                        }
                    }

                }
                case '2' -> {
                    // Opción 2
                    break;
                }
                case '3' -> {

                    break;
                }
                case '4' -> {

                    break;
                }
                case '5' -> {
                    salir = true;
                    break;
                }
            }
        }
    }
}
