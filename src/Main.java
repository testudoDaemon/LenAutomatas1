import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

public class Main {

    // se declara un ArrayList del tipo objeto Persona
    private static ArrayList<Persona> personas = new ArrayList<>();

    public static void main(String[] args) {
        try {
            crearArchivo();
            mostrarArchivo(personas);
        } catch (Exception e) {
            System.out.println(String.format("Excepcion %s", e));
        }
    }


    // metodo que crea el archivo .txt, además aqui se ingresan los datos en el array list del objeto Persona
    public static void crearArchivo() throws IOException {
        Scanner sc = new Scanner(System.in);
        boolean confirmar = false;
        //Escritura del archivo
        FileWriter escritor = new FileWriter("Practica1.txt");

        while (confirmar != true){
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

        Scanner sc = new Scanner(new File("Practica1.txt"));
        System.out.println("Nombres en el archivo: ");
        System.out.println(" "+"Nombre"+'\t'+'\t'+'\t'+"Apellido Paterno"+'\t'+'\t'+"Apellido Materno"+'\t'+'\t'+"Edad");

        while (sc.hasNextLine()) { // <-- para confirmar si hay alguna otra linea en la entrada del escaner
            String[] persona = sc.nextLine().split(" ");
        }
        sc.close();

        // Mostrar arrayList
        for (Persona persona: personas){
            // System.out.println("");
            System.out.println(persona.toString());

        }
    }
}