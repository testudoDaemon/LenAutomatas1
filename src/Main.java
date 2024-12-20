import java.io.*;
import java.util.ArrayList;

public class Main {
    public static String nomArchivo = "./inputP4.txt";
    public static String tokensFile = "./TablaTokens.txt";

    public static void main(String[] args) {
        analizadorLexico analizador = new analizadorLexico();
        analizadorLexico.leerArchivo(nomArchivo);

    }
}