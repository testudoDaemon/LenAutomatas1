import javax.swing.*;
import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

public class analizadorLexico {

    public static final HashMap<String, Integer> identificadores = new HashMap<>();
    public static final HashMap<String, Integer> palabrasReservadas = new HashMap<>();
    public static final HashMap<String, Integer> operadoresAritmeticos = new HashMap<>();
    public static final HashMap<String, Integer> operadoresRelacionales = new HashMap<>();
    public static final HashMap<String, Integer> operadoresLogicos = new HashMap<>();
    public static final HashMap<String, Integer> caracteres = new HashMap<>();
    public static final HashMap<String, Integer> numEnteros = new HashMap<>();
    public static final HashMap<String, Integer> numReales = new HashMap<>();
    public static final HashMap<String, Integer> cadenaString = new HashMap<>();
    public static final HashMap<String, ArrayList<Integer>> palabrasDelArchivo = new HashMap<>();

    public static final ArrayList<Integer> valorTokens = new ArrayList<Integer>();
    public static final ArrayList<String> tokens = new ArrayList<>();
    public static final String tablaTokensSalida = "./TablaTokens.txt";
    public static final String tablaTokensSalidaErrores = "./TablaErrores.txt";

    public static final HashMap<Integer, Integer> posTablaSalida = new HashMap<>();

    public analizadorLexico() {
        inicializarMapas();
        try {
            FileWriter fw = new FileWriter(tablaTokensSalida);
            fw.write("Tabla de Token\n");
            fw.write("Tabla de Errores\n");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void inicializarMapas() {
        identificadores.put("[a-zA-Z]+[\\$]", -53);
        identificadores.put("[a-zA-Z]+[\\%]", -52);
        identificadores.put("[a-zA-Z]+[\\&]", -51);
        identificadores.put("[a-zA-Z]+[\\#]", -54);
        identificadores.put("[a-zA-Z]+[\\@]", -55);

        palabrasReservadas.put("^programa$", -1);
        palabrasReservadas.put("^inicio$", -2);
        palabrasReservadas.put("^fin$", -3);
        palabrasReservadas.put("^leer$", -4);
        palabrasReservadas.put("^escribir$", -5);
        palabrasReservadas.put("^si$", -6);
        palabrasReservadas.put("^sino$", -7);
        palabrasReservadas.put("^mientras$", -8);
        palabrasReservadas.put("^repetir$", -9);
        palabrasReservadas.put("^hasta$", -10);
        palabrasReservadas.put("^entero$", -11);
        palabrasReservadas.put("^real$", -12);
        palabrasReservadas.put("^cadena$", -13);
        palabrasReservadas.put("^logico$", -14);
        palabrasReservadas.put("^variables$", -15);
        palabrasReservadas.put("^entonces$", -16);
        palabrasReservadas.put("^hacer$", -17);

        operadoresAritmeticos.put("\\+", -24);
        operadoresAritmeticos.put("\\-", -25);
        operadoresAritmeticos.put("\\*", -21);
        operadoresAritmeticos.put("\\/", -22);
        operadoresAritmeticos.put("\\=", -26);

        operadoresRelacionales.put("\\<", -31);
        operadoresRelacionales.put("\\>", -33);
        operadoresRelacionales.put("\\<=", -32);
        operadoresRelacionales.put("\\>=", -34);
        operadoresRelacionales.put("\\==", -35);
        operadoresRelacionales.put("\\!=", -36);

        operadoresLogicos.put("&&", -41);
        operadoresLogicos.put("\\|\\|?", -42);
        operadoresLogicos.put("!", -43);

        cadenaString.put("\"[^\"]+\"", -63);

        operadoresLogicos.put("true", -64);
        operadoresLogicos.put("false", -65);

        caracteres.put("\\(", -73);
        caracteres.put("\\)", -74);
        caracteres.put(";", -75);
        caracteres.put(",", -76);

        numEnteros.put("^\\d+$", -61);
        numReales.put("^-?\\d+\\.\\d+$", -62);

        posTablaSalida.put(-51, -2);
        posTablaSalida.put(-52, -2);
        posTablaSalida.put(-53, -2);
        posTablaSalida.put(-54, -2);
        posTablaSalida.put(-55, -1);
    }
    public static ArrayList<Error> errores = new ArrayList<Error>();
    public static void leerArchivo(String nombreArchivo) {
        boolean classLexema = false;
        try (BufferedReader br = new BufferedReader(new FileReader(nombreArchivo))) {
            String linea;
            int numLinea = 0;
            while ((linea = br.readLine()) != null) {
                numLinea++;
                linea = linea.replaceAll("/\\.?\\*/", "").trim();
                classLexema = clasificacionToken(linea, numLinea);
            }
            tablaErrores(errores);
            if (errores.isEmpty()) {
                System.out.println("El archivo se leyó correctamente y no tiene errores.");
            } else {
                System.out.println("El archivo se leyó correctamente pero tiene errores.");
            }
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Error al leer el archivo");
        }
    }

    public static boolean clasificacionToken(String lexema, int numLinea) {
        boolean noClasificadoLexeme = false;
        String[] tokens = analizarLexemas(lexema);
        for (String token : tokens) {
            if (token.startsWith("//") || token.isEmpty() || token.matches("^/\\*.*?\\*/$")) {
                continue;
            }
            int valorToken = getValorToken(token);
            if (valorToken != 0) {
                escribirEnArchivo(token, valorToken, numLinea);
            } else {
                noClasificadoLexeme = true;
                errores.add(new Error(numLinea, token, "Lexema no clasificado"));
                escribirEnArchivoErrores(token, numLinea);
            }
        }
        return noClasificadoLexeme;
    }

    public static int getValorToken(String key) {
        HashMap<String, Integer> combinedMap = new HashMap<>();
        combinedMap.putAll(identificadores);
        combinedMap.putAll(palabrasReservadas);
        combinedMap.putAll(operadoresAritmeticos);
        combinedMap.putAll(operadoresRelacionales);
        combinedMap.putAll(operadoresLogicos);
        combinedMap.putAll(cadenaString);
        combinedMap.putAll(caracteres);
        combinedMap.putAll(numEnteros);
        combinedMap.putAll(numReales);

        for (String regex : combinedMap.keySet()) {
            if (Pattern.matches(regex, key)) {
                return combinedMap.get(regex);
            }
        }

        // Default value if key doesn't match any regex
        return 0; // Or whatever you want to return as default
    }

    public static String[] analizarLexemas(String lexema) {
        Pattern pattern = Pattern.compile("\".*?\"|//.*?//|:=|<=|>=|==|!=|\\|\\||\\||-?\\d+\\.\\d*|[-+*;,<>():!]|\\d+!|(\"[^\"]+\")|(^/\\*.*?\\*/$)|(/\"\\w/\")|\\b[a-zA-Z\\d_]+\\b[#@%&$?]*|\\.[^ \\t\\n\\r\\f\\v]+");
        Matcher matcher = pattern.matcher(lexema);
        ArrayList<String> tokens = new ArrayList<>();
        while (matcher.find()) {
            String cadena = matcher.group();
            tokens.add(cadena);
            // System.out.println(tokens);
        }
        return tokens.toArray(new String[0]);
    }

    public static void escribirEnArchivo(String token, int valorToken, int numLinea) {
        int posicionTabla = posTablaSalida.getOrDefault(valorToken, -1);

        try (FileWriter fw = new FileWriter(tablaTokensSalida, true);
             BufferedWriter bw = new BufferedWriter(fw);
             PrintWriter out = new PrintWriter(bw)) {
            out.printf("%s %d %d %d%n", token, valorToken, posicionTabla, numLinea);
        } catch (IOException e) {
            System.err.println("Error al escribir en el archivo" + e.getMessage());
        }
    }

    public static void escribirEnArchivoErrores(String token, int numLinea) {
        try (FileWriter fw = new FileWriter(tablaTokensSalidaErrores, true);
             BufferedWriter bw = new BufferedWriter(fw);
             PrintWriter out = new PrintWriter(bw)) {
            out.printf("Lexema no clasificado: %s, En la linea: %d%n", token, numLinea);
        } catch (IOException e) {
            System.err.println("Error al escribir en el archivo de errores" + e.getMessage());
        }
    }

    private static class Error {
        public int numLinea;
        public String lexema;
        public String msg;

        public Error(int numLinea, String lexema, String msg) {
            this.numLinea = numLinea;
            this.lexema = lexema;
            this.msg = msg;
        }
    }

    public static void tablaErrores(ArrayList<Error> errores) {
        Path path = Paths.get("tabla_errores.txt");

        try {
            // Si ya existe el archivo lo borra para evitar datos duplicados (en caso ya se haya corrido el programa antes)
            Files.deleteIfExists(path);
            Files.createFile(path);

            try (BufferedWriter writer = Files.newBufferedWriter(path, StandardOpenOption.APPEND)) {
                for (Error error : errores) {
                    String str = String.format("%d        %s        %s%n", error.numLinea, error.lexema, error.msg);
                    writer.write(str);
                }
            }
        } catch (IOException e) {
            System.err.println("Error al manejar el archivo de errores: " + e.getMessage());
        }
    }
}