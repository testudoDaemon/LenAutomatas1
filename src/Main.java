import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

public class Main {

    // se declara un ArrayList del tipo objeto Persona
    static ArrayList<Persona> personas = new ArrayList<>();
    static File practica = new File("practica.txt");
    static FileWriter escritor;

    public static void main(String[] args) {
        try {
            crearArchivo();
            mostrarArchivo(personas);
        } catch (Exception e) {
            System.out.printf("Excepcion %s%n", e);
        }
    }
    // metodo que crea el archivo .txt, además aqui se ingresan los datos en el array list del objeto Persona
    public static void crearArchivo() throws IOException {
        if (practica.exists()) {
            if (practica.length() == 0) {
                escritor = new FileWriter(practica);
                ingresarDatos();
            } else {
                escritor = new FileWriter(practica, true); // Abre el archivo en modo de añadir al final
                Scanner sc = new Scanner(practica);
                while (sc.hasNextLine()) {
                    String[] reg = sc.nextLine().split(" ");
                    personas.add(new Persona(reg[0], reg[1], reg[2], reg[3]));
                }
                sc.close();
                mostrarArchivo(personas);
                // Preguntar al usuario si quiere agregar más personas
                sc = new Scanner(System.in);
                System.out.println("¿Quieres registrar a otra persona? s/n ");
                String s = sc.nextLine();
                if (s.equalsIgnoreCase("s")) {
                    ingresarDatos();
                }
            }
        } else {
            practica.createNewFile();
            escritor = new FileWriter(practica);
            ingresarDatos();
        }
    }


    public static void ingresarDatos() throws IOException {
        boolean confirmar = false;
        Scanner sc = new Scanner(System.in);
        while (!confirmar){
            System.out.println("Escribe un nombre");
            String nombre = sc.nextLine();
            System.out.println("Escribe apellido paterno");
            String apeP = sc.nextLine();
            System.out.println("Escribe apellido materno");
            String apeM = sc.nextLine();
            System.out.println("Escribe la edad");
            String edad = sc.nextLine();

            escritor.write(nombre+ " " +apeP+ " " +apeM+ " " +edad+ "\n");

            personas.add(new Persona(nombre, apeP, apeM, edad));

            // condicion para preguntar al usuario si se quiere ingresar más datos
            System.out.println("¿Quieres registrara a otra persona? s/n ");
            String s = sc.nextLine();
            if(s.equalsIgnoreCase("s")){
                confirmar = false;
            }else{
                confirmar = true;
            }
        }
        escritor.close();
    }

    // metodo para que se muestre el archivo creado, el metodo lee el archivo, además le da un formato de "tabla"
    public static void mostrarArchivo(ArrayList<Persona> personas) throws FileNotFoundException {
        System.out.println(" Nombres en el archivo ");
        System.out.println(" "+"Nombre"+'\t'+"|"+'\t'+"Apellido Paterno"+'\t'+"|"+'\t'+"Apellido Materno"+'\t'+"|"+'\t'+"Edad");
        // Mostrar arrayList
        for (Persona persona: personas){
            System.out.println(persona.toString());
        }
    }
}